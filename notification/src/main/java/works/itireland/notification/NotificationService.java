package works.itireland.notification;

import works.itireland.clients.notification.NotificationRequest;
import works.itireland.clients.notification.NotificationResponse;

public interface NotificationService {
    NotificationResponse send(NotificationRequest request);
}
