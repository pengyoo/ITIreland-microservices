package works.itireland.notification;

import works.itireland.notification.NotificationRequest;
import works.itireland.notification.NotificationResponse;

public interface NotificationService {
    NotificationResponse send(NotificationRequest request);
}
