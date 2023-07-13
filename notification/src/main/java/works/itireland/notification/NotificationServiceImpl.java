package works.itireland.notification;


import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService{
    private final NotificationRepository notificationRepository;
    @Override
    public NotificationResponse send(NotificationRequest request) {
        Notification notification = new Notification();
        BeanUtils.copyProperties(request, notification);
        notification = notificationRepository.save(notification);
        NotificationResponse notificationResponse = new NotificationResponse();
        BeanUtils.copyProperties(notification, notificationResponse);
        return notificationResponse;
    }
}
