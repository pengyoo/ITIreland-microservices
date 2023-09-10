package works.itireland.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import works.itireland.clients.notification.NotificationClient;
import works.itireland.clients.notification.NotificationRequest;
import works.itireland.clients.notification.NotificationResponse;
import works.itireland.clients.post.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationClient notificationClient;

    @PatchMapping("/{id}")
    public ResponseEntity<NotificationResponse> update(@PathVariable long id, @RequestBody NotificationRequest notificationRequest) {
        return new ResponseEntity<>(notificationClient.sendNotification(notificationRequest).getData(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationResponse> findById(@PathVariable long id) {
        return new ResponseEntity<>(notificationClient.findById(id).getData(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<NotificationResponse>> findAll(@RequestParam(required = false, defaultValue = "0") int _start,
                                                      @RequestParam(required = false, defaultValue = "20") int _end,
                                                      HttpServletResponse response){
        int pageSize = _end - _start;
        int page = _start / (pageSize - 1);
        String count = String.valueOf(notificationClient.count());
        response.addHeader("x-total-count", count);
        response.addHeader("Access-Control-Expose-Headers", "x-total-count");

        return new ResponseEntity<>(notificationClient.findAll(page, pageSize).getData(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<NotificationResponse> save(@RequestBody NotificationRequest notificationRequest) {
        return new ResponseEntity<>(notificationClient.sendNotification(notificationRequest).getData(), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id){
        notificationClient.delete(id);
    }

}
