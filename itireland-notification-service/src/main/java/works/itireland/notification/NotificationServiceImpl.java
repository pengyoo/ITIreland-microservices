package works.itireland.notification;


import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Service;
import works.itireland.clients.notification.NotificationRequest;
import works.itireland.clients.notification.NotificationResponse;
import works.itireland.clients.user.UserClient;
import works.itireland.clients.user.UserResponse;
import works.itireland.utils.BeanCopyUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService{
    private final NotificationRepository notificationRepository;
    private final UserClient userClient;
    private final EmailService emailService;
    @Override
    public NotificationResponse send(NotificationRequest request) {
        Notification notification = new Notification();
        BeanCopyUtils.copyNonNullProperties(request, notification);



        UserResponse user = userClient.findByUsername(request.getToUsername()).getData();
        notification.setToUserId(user.getId());
        notification.setToUserEmail(user.getEmail());
        notification.setToUsername(user.getUsername());
        notification.setCtime(LocalDateTime.now());

        // Send Notification
        notification = notificationRepository.save(notification);

        // Send Notification Email
//        try {
//            emailService.sendEmail(notification.getToUserEmail(), notification.getMessage(), notification.getMessage());
//        } catch (MessagingException e){
//            log.error("Sending Email Error: " + e.getMessage());
//        }
        NotificationResponse notificationResponse = getNotificationResponse(notification);
        return notificationResponse;
    }

    private static NotificationResponse getNotificationResponse(Notification notification) {
        NotificationResponse notificationResponse = new NotificationResponse();
        BeanCopyUtils.copyNonNullProperties(notification, notificationResponse);
        return notificationResponse;
    }

    @Override
    public List<NotificationResponse> findAll(Pageable pageable) {
        return notificationRepository.findAll(pageable).map(notification -> getNotificationResponse(notification)).toList();
    }

    @Override
    public NotificationResponse findById(Long id) {
        return notificationRepository
                .findById(id).map(notification -> getNotificationResponse(notification))
                .orElseThrow();
    }

    @Override
    public long count() {
        return notificationRepository.count();
    }

    @Override
    public void delete(Long id) {
        Notification notification = notificationRepository.findById(id).orElseThrow();
        notification.setState(-1);
        notificationRepository.save(notification);

    }
}
