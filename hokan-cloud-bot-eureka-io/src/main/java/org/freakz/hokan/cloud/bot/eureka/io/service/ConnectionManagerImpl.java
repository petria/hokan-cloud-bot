package org.freakz.hokan.cloud.bot.eureka.io.service;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan.cloud.bot.common.jpa.entity.*;
import org.freakz.hokan.cloud.bot.common.jpa.repository.ChannelRepository;
import org.freakz.hokan.cloud.bot.common.jpa.repository.IrcServerConfigRepository;
import org.freakz.hokan.cloud.bot.common.model.event.RawIRCEvent;
import org.freakz.hokan.cloud.bot.common.model.event.ToIRCEvent;
import org.freakz.hokan.cloud.bot.common.model.io.IrcServerConfigModel;
import org.freakz.hokan.cloud.bot.eureka.io.client.IrcEngineClient;
import org.freakz.hokan.cloud.bot.eureka.io.ircengine.HokanCore;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ConnectionManagerImpl implements ConnectionManager, CommandLineRunner {

    private final IrcServerConfigRepository configRepository;
    private final ChannelRepository channelRepository;
    private final HokanCoreRuntimeService runtimeService;
    private final ModelMapper modelMapper;

    private final IrcEngineClient ircEngineClient;

    @Autowired
    public ConnectionManagerImpl(IrcServerConfigRepository configRepository, ChannelRepository channelRepository, ModelMapper modelMapper, IrcEngineClient ircEngineClient) {
        this.configRepository = configRepository;
        this.channelRepository = channelRepository;
        this.modelMapper = modelMapper;
        this.ircEngineClient = ircEngineClient;

        this.runtimeService = new HokanCoreRuntimeServiceImpl(this);
    }


    @Override
    public List<IrcServerConfigModel> getConfiguredIRCServers() {
        List<IrcServerConfig> configs = configRepository.findAll();
        return configs.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public boolean putOnline(String network) {
        IrcServerConfigModel dto = convertToDto(getConfig(network));
        if (dto != null) {
            return runtimeService.putOnline(dto);
        }
        return false;
    }

    @Override
    public boolean putOffline(String network) {
        IrcServerConfigModel dto = convertToDto(getConfig(network));
        if (dto != null) {
            return runtimeService.putOffline(dto);
        }
        return false;
    }

    @Override
    public boolean sendMessageToIRC(ToIRCEvent toIRCEvent) {
        HokanCore core = runtimeService.findTargetCore(toIRCEvent.getTarget());
        if (core != null) {
            return core.sendMessageToIRC(toIRCEvent);
        } else {
            log.error("No core to send message: {}", toIRCEvent.getTarget());
        }
        return false;
    }

    private IrcServerConfig getConfig(String network) {
        List<IrcServerConfig> configs = configRepository.findAll();
        for (IrcServerConfig config : configs) {
            if (config.getNetwork().getName().equalsIgnoreCase(network)) {
                return config;
            }
        }
        return null;
    }

    private IrcServerConfigModel convertToDto(IrcServerConfig ircServerConfig) {
        if (ircServerConfig == null) {
            return null;
        }
        IrcServerConfigModel dto = modelMapper.map(ircServerConfig, IrcServerConfigModel.class);
        dto.setNetwork(ircServerConfig.getNetwork().getName());
        return dto;
    }

    @Override
    public void run(String... args) throws Exception {
        log.debug("STARTUP: init");
        List<IrcServerConfig> configs = configRepository.findAll();
        for (IrcServerConfig config : configs) {
            if (config.getIrcServerConfigState() == IrcServerConfigState.CONNECTED) {
                log.debug("STARTUP: Connecting config: {}", config.toString());
                putOnline(config.getNetwork().getName());
//                joinNetworkChannels(config.getNetwork());
            }
        }
    }

    private void joinNetworkChannels(Network network) {
        List<Channel> networkChannels = channelRepository.findByNetwork(network);
        for (Channel channel : networkChannels) {
            if (channel.getChannelStartupState() == ChannelStartupState.JOIN) {
                log.debug("joining channel {}", channel.toString());
                runtimeService.networkJoinChannel(network.getName(), channel.getChannelName());
            }
        }
    }

    @Override
    public void coreConnected(HokanCore hokanCore) {
        IrcServerConfig config = getConfig(hokanCore.getNetwork());
        if (config != null) {
            joinNetworkChannels(config.getNetwork());
        } else {
            log.error("{}: no config found, cant't join channels", hokanCore.toString());
        }
    }

    @Override
    public void publishRawIRCEvent(RawIRCEvent event) {
        log.debug("Publish event from: {} - TYPE: {}", event.getSource(), event.getType());
        try {
            ircEngineClient.rawIRCEvent(event);
        } catch (Exception e) {
            log.error("publish failed!", e);
        }
    }
}
