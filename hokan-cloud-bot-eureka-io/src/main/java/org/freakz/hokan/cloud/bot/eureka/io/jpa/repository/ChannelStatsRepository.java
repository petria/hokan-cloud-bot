package org.freakz.hokan.cloud.bot.eureka.io.jpa.repository;

import org.freakz.hokan.cloud.bot.eureka.io.jpa.entity.Channel;
import org.freakz.hokan.cloud.bot.eureka.io.jpa.entity.ChannelStats;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Petri Airio on 8.4.2015.
 * -
 */
public interface ChannelStatsRepository extends JpaRepository<ChannelStats, Long> {


    ChannelStats findFirstByChannel(Channel channel);

}
