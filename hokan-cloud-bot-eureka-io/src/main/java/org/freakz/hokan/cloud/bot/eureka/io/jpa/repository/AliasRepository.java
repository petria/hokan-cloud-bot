package org.freakz.hokan.cloud.bot.eureka.io.jpa.repository;


import org.freakz.hokan.cloud.bot.eureka.io.jpa.entity.Alias;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Petri Airio on 31.8.2015.
 */
public interface AliasRepository extends JpaRepository<Alias, Long> {

    Alias findFirstByAlias(String alias);

}
