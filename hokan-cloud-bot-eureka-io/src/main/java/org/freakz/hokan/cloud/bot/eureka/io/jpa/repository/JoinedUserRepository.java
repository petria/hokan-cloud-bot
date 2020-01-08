package org.freakz.hokan.cloud.bot.eureka.io.jpa.repository;

import org.freakz.hokan.cloud.bot.eureka.io.jpa.entity.Channel;
import org.freakz.hokan.cloud.bot.eureka.io.jpa.entity.JoinedUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Petri Airio on 1.4.2015.
 */
public interface JoinedUserRepository extends JpaRepository<JoinedUser, Long> {

    void deleteByChannel(Channel channel);

    List<JoinedUser> findByChannel(Channel channel);

}
