package works.itireland.notification;

import org.springframework.data.domain.ManagedTypes;
import org.springframework.data.domain.Pageable;
import works.itireland.clients.notification.NotificationRequest;
import works.itireland.clients.notification.NotificationResponse;
import works.itireland.clients.post.PostResponse;

import java.util.List;

public interface NotificationService {
    NotificationResponse send(NotificationRequest request);

    List<NotificationResponse> findAll(Pageable pageable);

    NotificationResponse findById(Long id);

    long count();

    void delete(Long id);
}
