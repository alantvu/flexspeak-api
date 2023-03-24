package dev.mjamieson.flexspeak.feature.custom_word;

import dev.mjamieson.flexspeak.feature.user.User;
import io.hypersistence.utils.hibernate.type.basic.PostgreSQLEnumType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CustomWord {
    @Id
    @GeneratedValue(generator = "custom_word_id_seq")
    @SequenceGenerator(name = "custom_word_id_seq", sequenceName = "custom_word_id_seq", allocationSize = 1)
    private Integer id;

    private String wordToDisplay;

    private String wordToSpeak;

    private String imagePath;

    private Integer gridRow;

    private Integer gridColumn;

    @Enumerated(EnumType.STRING)
    @Type(PostgreSQLEnumType.class)
    private GridTitleEnum gridTitleEnum;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}