package pl.projekt.basicListToDo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "created")
    private Timestamp created = Timestamp.from(Instant.now());

    @Column(name = "deadline")
    private Timestamp deadline;

    @Column(name = "priority")
    private String priority;

    @Column(name = "done")
    private Boolean done;

    public void setCreated(LocalDateTime localDateTime) {
        this.created = Timestamp.valueOf(localDateTime);
    }

    public LocalDateTime getCreated() {
        return this.created.toLocalDateTime();
    }

    public void setDeadline(LocalDateTime localDateTime) {
        this.deadline = Timestamp.valueOf(localDateTime);
    }

    public LocalDateTime getDeadline() {
        return deadline != null ? deadline.toLocalDateTime() : null;
    }

    @PrePersist
    public void prePersist() {
        if (created == null) {
            created = Timestamp.from(Instant.now());
        }

        if (deadline == null) {
            deadline = Timestamp.from(Instant.now());
        }
    }

}
