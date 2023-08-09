package works.itireland.notification;


import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import works.itireland.clients.notification.NotificationRequest;
import works.itireland.clients.notification.NotificationResponse;

@Service
@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService{
    private final NotificationRepository notificationRepository;
    @Override
    public NotificationResponse send(NotificationRequest request) {
        Notification notification = new Notification();
        BeanUtils.copyProperties(request, notification);

        // Send Notification within website
        notification = notificationRepository.save(notification);
        //TODO Send Email Notification

        NotificationResponse notificationResponse = new NotificationResponse();
        BeanUtils.copyProperties(notification, notificationResponse);
        return notificationResponse;
    }
}
