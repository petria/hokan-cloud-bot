package org.freakz.hokan.cloud.bot.eureka.io.jpa.repository;


import org.freakz.hokan.cloud.bot.eureka.io.jpa.entity.SearchReplace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Petri Airio on 31.8.2015.
 */
public interface SearchReplaceRepository extends JpaRepository<SearchReplace, Long> {

    List<SearchReplace> findByTheSearch(String search);

}
