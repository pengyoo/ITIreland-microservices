package works.itireland.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponse {

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
