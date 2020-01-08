package org.freakz.hokan.cloud.bot.eureka.io.jpa.repository;


import org.freakz.hokan.cloud.bot.eureka.io.jpa.entity.DataValues;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Petri Airio on 31.8.2015.
 */
public interface DataValuesRepository extends JpaRepository<DataValues, Long> {

    DataValues findByNickAndChannelAndNetworkAndKeyName(String nick, String channel, String network, String keyName);

    List<DataValues> findAllByChannelAndNetworkAndKeyName(String channel, String network, String keyName);

}
