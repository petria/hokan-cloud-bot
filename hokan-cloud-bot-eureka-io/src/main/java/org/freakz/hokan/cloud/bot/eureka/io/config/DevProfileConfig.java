package org.freakz.hokan.cloud.bot.eureka.io.config;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan.cloud.bot.eureka.io.jpa.entity.IrcServerConfig;
import org.freakz.hokan.cloud.bot.eureka.io.jpa.entity.Network;
import org.freakz.hokan.cloud.bot.eureka.io.jpa.repository.IrcServerConfigRepository;
import org.freakz.hokan.cloud.bot.eureka.io.jpa.repository.NetworkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

import java.util.List;

@Configuration
@Component
@Profile("dev")
@Slf4j
public class DevProfileConfig implements CommandLineRunner {


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

        configRepository.save(config);

        List<IrcServerConfig> all = configRepository.findAll();

        int foo = 0;
    }


}
