package org.freakz.hokan.cloud.bot.eureka.io.jpa.repository;


import org.freakz.hokan.cloud.bot.eureka.io.jpa.entity.Network;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Petri Airio on 19.2.2015.
 */
public interface NetworkRepository extends JpaRepository<Network, Long> {

    Network findByNetworkName(String networkName);

}
