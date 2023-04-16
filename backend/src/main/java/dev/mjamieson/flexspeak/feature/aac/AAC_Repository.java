package dev.mjamieson.flexspeak.feature.aac;

import dev.mjamieson.flexspeak.feature.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AAC_Repository extends JpaRepository<AAC, Integer> {

    long countByUser(User user);

    void deleteByUser(User user);
}
