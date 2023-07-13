package works.itireland.notification;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;
    private LocalDateTime ctime;

    // 0 unread, -1 read
    private int state;

    private String fromUsername;
    private Long toUserId;

    private String toUserEmail;

    // 0: System, 1: User
    private int type;
}
