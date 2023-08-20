package works.itireland.notification;


import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Service;
import works.itireland.clients.notification.NotificationRequest;
import works.itireland.clients.notification.NotificationResponse;

@Service
@AllArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService{
    private final NotificationRepository notificationRepository;
    private final EmailService emailService;
    @Override
    public NotificationResponse send(NotificationRequest request) {
        Notification notification = new Notification();
        BeanUtils.copyProperties(request, notification);

        // Send Notification within website
        notification = notificationRepository.save(notification);

        // Send Notification Email
        try {
            emailService.sendEmail(notification.getToUserEmail(), notification.getMessage(), notification.getMessage());
        } catch (MessagingException e){
            log.error("Sending Email Error: " + e.getMessage());
        }
        NotificationResponse notificationResponse = new NotificationResponse();
        BeanUtils.copyProperties(notification, notificationResponse);
        return notificationResponse;
    }
}
