package works.itireland.notification;


import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import works.itireland.auth.AuthUtils;
import works.itireland.auth.AuthorizedRoles;
import works.itireland.clients.R;
import works.itireland.clients.notification.NotificationRequest;
import works.itireland.clients.notification.NotificationResponse;
import works.itireland.clients.post.PostResponse;
import works.itireland.clients.user.UserResponse;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@AllArgsConstructor
@Slf4j
public class NotificationController {

    private final NotificationService notificationService;


    @PostMapping("/secure")
    @AuthorizedRoles(roles = {"ROLE_USER", "ROLE_ADMIN"})
    public R<NotificationResponse> sendNotification(@RequestBody NotificationRequest request){
        log.info("send notification"+ request);
        return R.success(notificationService.send(request));
    }

    @GetMapping
    public R<List<NotificationResponse>> findAll(@RequestParam(required = false, defaultValue = "0") int page,
                                         @RequestParam(required = false, defaultValue = "10") int pageSize){
        log.info("find notifications by page:" + page +", pageSize:" +pageSize);
        Pageable pageable = PageRequest.of(page, pageSize);
        return R.success(notificationService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public R<NotificationResponse> findById(@PathVariable Long id) {

        return R.success(notificationService.findById(id));

    }

    @GetMapping("/count")
    public long count() {
        return notificationService.count();
    }

    @DeleteMapping("/secure/{id}")
    @AuthorizedRoles(roles = {"ROLE_USER", "ROLE_ADMIN"})
    public R delete(@PathVariable Long id){
        notificationService.delete(id);
        return R.success(null);
    }
}
