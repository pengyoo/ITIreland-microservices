package works.itireland;


import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import works.itireland.domain.Post;
import works.itireland.payload.PostResponse;
import works.itireland.user.UserClient;
import works.itireland.user.UserResponse;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
@EnableFeignClients
public class PostController {

    private final PostService postService;
    private final UserClient userClient;

    @PostMapping
    public PostResponse save(@RequestBody Post post){

        PostResponse postResponse =  postService.insert(post);
        UserResponse userResponse = userClient.find(post.getUserId());
        postResponse.setUser(userResponse);
        return postResponse;
    }
}
