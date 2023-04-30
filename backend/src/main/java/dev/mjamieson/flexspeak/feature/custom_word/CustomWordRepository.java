package dev.mjamieson.flexspeak.feature.custom_word;

import dev.mjamieson.flexspeak.feature.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomWordRepository extends JpaRepository<CustomWord, Integer> {
    void deleteByUser(User user);
    List<CustomWord> findByUser(User user);
    CustomWord findByUserAndGridColumnAndGridRowAndGridTitleEnum(User user, Integer gridColumn, Integer gridRow, GridTitleEnum gridTitleEnum);
}
