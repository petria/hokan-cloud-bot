package org.freakz.hokan.cloud.bot.eureka.io.service;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan.cloud.bot.common.model.event.MessageToIRC;
import org.freakz.hokan.cloud.bot.common.model.io.IrcServerConfigModel;
import org.freakz.hokan.cloud.bot.eureka.io.ircengine.HokanCore;
import org.freakz.hokan.cloud.bot.eureka.io.jpa.entity.IrcServerConfig;
import org.freakz.hokan.cloud.bot.eureka.io.jpa.repository.IrcServerConfigRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ConnectionManagerImpl implements ConnectionManager {

    private final IrcServerConfigRepository configRepository;
    private final HokanCoreRuntimeService runtimeService;

    private final ModelMapper modelMapper;

    @Autowired
    public ConnectionManagerImpl(IrcServerConfigRepository configRepository, HokanCoreRuntimeService runtimeService, ModelMapper modelMapper) {
        this.configRepository = configRepository;
        this.runtimeService = runtimeService;
        this.modelMapper = modelMapper;
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
    public boolean sendMessageToIRC(MessageToIRC messageToIRC) {
        HokanCore core = runtimeService.findTargetCore(messageToIRC.getTarget());
        if (core != null) {
            return core.sendMessageToIRC(messageToIRC);
        } else {
            log.error("No core to send message: {}", messageToIRC.getTarget());
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

}
