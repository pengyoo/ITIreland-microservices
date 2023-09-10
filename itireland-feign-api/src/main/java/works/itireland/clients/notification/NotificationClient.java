package works.itireland.clients.notification;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import works.itireland.auth.AuthorizedRoles;
import works.itireland.clients.R;

import java.util.List;

@FeignClient(
        value = "notification-service",
        path = "/api/v1/notifications"
)
public interface NotificationClient {
    @PostMapping("/secure")
    public R<NotificationResponse> sendNotification(NotificationRequest request);
    @DeleteMapping("/secure/{id}")
    public R delete(@PathVariable(value = "id") Long id);

    @GetMapping("/count")
    public long count();

    @GetMapping("/{id}")
    public R<NotificationResponse> findById(@PathVariable(value = "id") Long id);

    @GetMapping
    public R<List<NotificationResponse>> findAll(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                 @RequestParam(value="pageSize", required = false, defaultValue = "10") int pageSize);
}
