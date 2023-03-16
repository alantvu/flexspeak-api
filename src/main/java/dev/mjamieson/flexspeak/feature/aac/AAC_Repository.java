package dev.mjamieson.flexspeak.feature.aac;

import dev.mjamieson.flexspeak.feature.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AAC_Repository extends JpaRepository<AAC, Integer> {

    void deleteByUser(User user);
}
