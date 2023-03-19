package dev.mjamieson.flexspeak.feature.custom_word;

import dev.mjamieson.flexspeak.feature.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomWordRepository extends JpaRepository<CustomWord, Integer> {
    void deleteByUser(User user);
}
