package works.itireland.post.post;


import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import works.itireland.clients.R;
import works.itireland.clients.post.PostRequest;
import works.itireland.clients.post.PostResponse;
import works.itireland.clients.user.UserClient;
import works.itireland.clients.user.UserResponse;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
@EnableFeignClients
@Slf4j
public class PostController {

    private final PostService postService;
    private final UserClient userClient;

    @PostMapping
    public R<PostResponse> save(@RequestBody PostRequest postRequest){

        log.info("save post:" + postRequest);
        PostResponse postResponse =  postService.insert(postRequest);
        UserResponse userResponse = userClient.find(postRequest.getUserId()).getData();
        postResponse.setUser(userResponse);
        return R.success(postResponse);
    }

    @GetMapping("/{postId}")
    public R findById(@PathVariable String postId) {

        return R.success(postService.findById(postId));

    }

    @GetMapping
    public R<List<PostResponse>> findAll(@RequestParam(required = false, defaultValue = "0") int page,
                     @RequestParam(required = false, defaultValue = "10") int pageSize){
        log.info("find posts by page:" + page +", pageSize:" +pageSize);
        Pageable pageable = PageRequest.of(page, pageSize);
        return R.success(postService.findAll(pageable).toList());
    }


    @GetMapping("/user/{userId}")
    public R<List<PostResponse>> findAllByUserId(@PathVariable Long userId, @RequestParam(required = false, defaultValue = "0") int page,
                                            @RequestParam(required = false, defaultValue = "10") int pageSize){
        log.info("find posts by userId and page:" + page +", pageSize:" +pageSize);
        Pageable pageable = PageRequest.of(page, pageSize);
        return R.success(postService.findAllByUserId(userId, pageable));
    }

    @GetMapping("/followings/{userId}")
    public R<List<PostResponse>> findFollowingsByUserId(@PathVariable Long userId, @RequestParam(required = false, defaultValue = "0") int page,
                                              @RequestParam(required = false, defaultValue = "10") int pageSize){
        log.info("find followings by userId and page:" + page +", pageSize:" +pageSize);
        Pageable pageable = PageRequest.of(page, pageSize);
        return R.success(postService.findFollowingsByUserId(userId, pageable));
    }


    @DeleteMapping("/{userId}/{postId}")
    public R delete(@PathVariable Long userId, @PathVariable String postId){
        postService.delete(userId, postId);
        return R.success(null);
    }

    @PostMapping("/upvote/{userId}/{postId}")
    public R upvote(@PathVariable Long userId, @PathVariable String postId){
        int upvotes = postService.upvote(userId, postId);
        return R.success(upvotes);
    }

    @PostMapping("/unUpvote/{userId}/{postId}")
    @RolesAllowed("USER")
    public R unUpvote(@PathVariable Long userId, @PathVariable String postId){
        int upvotes = postService.unUpvote(userId, postId);
        return R.success(upvotes);
    }



}
