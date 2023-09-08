package works.itireland.user.controller;


import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import works.itireland.auth.AuthUtils;
import works.itireland.auth.AuthorizedRoles;
import works.itireland.clients.R;
import works.itireland.clients.user.UserLoginResponse;
import works.itireland.clients.user.UserRegisterRequest;
import works.itireland.clients.user.UserResponse;
import works.itireland.clients.user.UserUpdateRequest;
import works.itireland.user.dto.ImageUploadResponse;
import works.itireland.user.service.ImageService;
import works.itireland.user.service.UserService;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final ImageService imageService;

    @Hidden
    @PostMapping
    public R<UserResponse> register(@RequestBody UserRegisterRequest userRegisterRequest){
        log.info("register user {}", userRegisterRequest);
        UserResponse userResponse = userService.register(userRegisterRequest);
        return R.success(userResponse);
    }

    @Hidden
    @PostMapping("/update")
    public R<UserResponse> update(@RequestBody UserUpdateRequest userUpdateRequest){
        log.info("update user {}", userUpdateRequest);
        UserResponse userResponse = userService.update(userUpdateRequest);
        return R.success(userResponse);
    }

    @Hidden
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) throws Throwable {
        log.info("delete user {}", id);
        userService.delete(id);
    }

    @Hidden
    @PostMapping("/login")
    public R<UserLoginResponse> login(@RequestParam String username){
        log.info("find user by username: {}", username);
        return R.success(userService.login(username));
    }

    @GetMapping("/{userId}")
    public R<UserResponse> find(@PathVariable Long userId){
        log.info("find user by userId: {}", userId);
        return R.success(userService.find(userId));
    }

    @GetMapping("/findByUsername/{username}")
    public R<UserResponse> findByUsername(@PathVariable String username){
        log.info("find user by username: {}", username);
        return R.success(userService.findByUsername(username));
    }



    @GetMapping
    public R<List<UserResponse>> findAll(@RequestParam(required = false, defaultValue = "0") int page,
                                         @RequestParam(required = false, defaultValue = "10") int pageSize, HttpServletResponse httpResponse){
        log.info("find users by page: {}, pageSize: {}", page, pageSize);
        Pageable pageable = PageRequest.of(page, pageSize);
        long count = userService.count();
        httpResponse.addHeader("x-total-count", String.valueOf(count));
        httpResponse.addHeader("Access-Control-Expose-Headers", "x-total-count");
        return R.success(userService.findAll(pageable));
    }

    @GetMapping("/count")
    public Long count() {
        return userService.count();
    }

    @PostMapping(value = "/secure/profile-image-upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @AuthorizedRoles(roles = {"ROLE_USER", "ROLE_ADMIN"})
    public R profileImageUpload(
            @RequestParam("file") MultipartFile file,
            HttpServletRequest request) throws IOException {
        String username = AuthUtils.getUserName(request);
        String imageName = imageService.uploadProfileImage(username, file);
        String url = request.getContextPath() + "/images/%s/%s".formatted(username, imageName);
        return R.success(new ImageUploadResponse(username, imageName, url));
    }

    @GetMapping(
            value = "images/{username}/{imageName}",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public byte[] getPostImage(
            @PathVariable("username") String username,
            @PathVariable("imageName") String imageName) {
        return imageService.get(username, imageName);
    }

}
