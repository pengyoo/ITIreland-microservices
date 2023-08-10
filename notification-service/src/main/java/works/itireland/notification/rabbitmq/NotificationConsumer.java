package works.itireland.notification.rabbitmq;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import works.itireland.clients.notification.NotificationRequest;
import works.itireland.notification.NotificationService;

@Component
@Slf4j
@AllArgsConstructor
public class NotificationConsumer {

    private final NotificationService notificationService;

    @RabbitListener(queues = "${rabbitmq.queues.notification}")
    public void consume(NotificationRequest notificationRequest){
        log.info("received {} in {}", notificationRequest, "${rabbitmq.queues.notification}");
        notificationService.send(notificationRequest);

    }
}
