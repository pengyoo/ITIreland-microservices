package works.itireland.notification;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import works.itireland.clients.notification.NotificationRequest;
import works.itireland.clients.notification.NotificationResponse;

@RestController
@RequestMapping("/api/v1/notification")
@AllArgsConstructor
@Slf4j
public class NotificationController {

    private final NotificationService notificationService;


    @PostMapping
    public NotificationResponse sendNotification(@RequestBody NotificationRequest request){
        log.info("send notification"+ request);
        return notificationService.send(request);
    }
}
