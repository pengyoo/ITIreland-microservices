package works.itireland.clients.notification;

import com.fasterxml.jackson.annotation.JsonFormat;
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

        private long id;
        private String message;
        @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
        private LocalDateTime ctime;

        // 0 unread, -1 read
        private int state;
        private String fromUsername;
        private Long toUserId;
        private String toUserEmail;
        private String toUsername;

        // 0: System, 1: User
        private int type;
}
