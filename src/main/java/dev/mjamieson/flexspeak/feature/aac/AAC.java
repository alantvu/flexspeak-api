package dev.mjamieson.flexspeak.feature.aac;

import dev.mjamieson.flexspeak.feature.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AAC {
    @Id
    @GeneratedValue(generator = "aac_id_seq")
    @SequenceGenerator(name = "aac_id_seq", sequenceName = "aac_id_seq", allocationSize = 1)
    private Integer id;

    private String sentence;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}