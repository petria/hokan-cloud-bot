package org.freakz.hokan.cloud.bot.eureka.io.jpa.repository;

import org.freakz.hokan.cloud.bot.eureka.io.jpa.entity.Channel;
import org.freakz.hokan.cloud.bot.eureka.io.jpa.entity.User;
import org.freakz.hokan.cloud.bot.eureka.io.jpa.entity.UserChannel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Petri Airio on 7.4.2015.
 */
public interface UserChannelRepository extends JpaRepository<UserChannel, Long> {

    List<UserChannel> findByUser(User user);

    UserChannel findFirstByUserAndChannel(User user, Channel channel);

}
