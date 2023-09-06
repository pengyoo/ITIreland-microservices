package works.itireland.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;
import works.itireland.clients.R;
import works.itireland.clients.user.UserClient;
import works.itireland.clients.user.UserRegisterRequest;
import works.itireland.clients.user.UserResponse;
import works.itireland.exception.ApiRequestException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/users")
@RequiredArgsConstructor
@RefreshScope
public class UserController {
    private final UserClient userClient;

    @GetMapping
    public List<UserResponse> findAll(@RequestParam(required = false, defaultValue = "0") int _start,
                                         @RequestParam(required = false, defaultValue = "20") int _end){

        int pageSize = _end - _start + 1;
        int page = _start / (pageSize - 1);

        R<List<UserResponse>> result = userClient.findAll(page, pageSize);
        if (result == null){
            throw new ApiRequestException("No Data");
        }
        return result.getData();
    }

    @GetMapping("/{userId}")
    public UserResponse find(@PathVariable Long userId){
        return userClient.find(userId).getData();
    }

    @PostMapping
    public UserResponse save(@RequestBody UserRegisterRequest userRegisterRequest){
        return userClient.register(userRegisterRequest).getData();
    }

    @PatchMapping
    public UserResponse patch(@RequestBody UserRegisterRequest userRegisterRequest){
        return userClient.register(userRegisterRequest).getData();
    }
}
