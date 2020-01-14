package org.freakz.hokan.cloud.bot.eureka.io.config;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan.cloud.bot.common.jpa.entity.*;
import org.freakz.hokan.cloud.bot.common.jpa.repository.ChannelRepository;
import org.freakz.hokan.cloud.bot.common.jpa.repository.IrcServerConfigRepository;
import org.freakz.hokan.cloud.bot.common.jpa.repository.NetworkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@Component
@Profile("dev")
@Slf4j
@Transactional
public class DevProfileConfig implements CommandLineRunner {

    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private IrcServerConfigRepository configRepository;

    @Autowired
    private NetworkRepository networkRepository;

    @Override
    public void run(String... args) throws Exception {
        log.debug("Run!");
        generateMemDB();
    }

    private void generateMemDB() {
        IrcServerConfig config = new IrcServerConfig();

        Network network = new Network("DevNET");
        network = networkRepository.save(network);

        config.setNetwork(network);
        config.setServer("localhost");
        config.setPort(1100);
        config.setIrcServerConfigState(IrcServerConfigState.CONNECTED);

        configRepository.save(config);

        Channel channel = new Channel(network, "#HokanCLOUD");
        channel.setChannelStartupState(ChannelStartupState.JOIN);
        channelRepository.save(channel);

        IrcServerConfig config2 = new IrcServerConfig();

        Network network2 = new Network("IrcNET");
        network2 = networkRepository.save(network2);

        config2.setNetwork(network2);
        config2.setServer("irc.atw-inter.net");
        config2.setPort(6667);
        config2.setIrcServerConfigState(IrcServerConfigState.CONNECTED);

        configRepository.save(config2);

        Channel channel2 = new Channel(network2, "#HokanCLOUD");
        channel2.setChannelStartupState(ChannelStartupState.JOIN);
        channelRepository.save(channel2);

        int foo = 0;
    }


}
