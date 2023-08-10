package works.itireland.post.post;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import works.itireland.auth.AuthUtils;
import works.itireland.auth.AuthorizedRoles;
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
    @AuthorizedRoles(roles = {"ROLE_USER", "ROLE_ADMIN"})
    public R<PostResponse> save(@RequestBody PostRequest postRequest, HttpServletRequest request){

        String username = AuthUtils.getUserName(request);
        UserResponse user = userClient.findByUsername(username).getData();
        postRequest.setUserId(user.getId());
        log.info("save post:" + postRequest);
        PostResponse postResponse =  postService.insert(postRequest);
        postResponse.setUser(user);
        return R.success(postResponse);
    }

    @GetMapping("/open/{postId}")
    public R findById(@PathVariable String postId) {

        return R.success(postService.findById(postId));

    }

    @GetMapping("/open")
    public R<List<PostResponse>> findAll(@RequestParam(required = false, defaultValue = "0") int page,
                     @RequestParam(required = false, defaultValue = "10") int pageSize){
        log.info("find posts by page:" + page +", pageSize:" +pageSize);
        Pageable pageable = PageRequest.of(page, pageSize);
        return R.success(postService.findAll(pageable).toList());
    }


    @GetMapping("/open/user/{userId}")
    public R<List<PostResponse>> findAllByUserId(@PathVariable Long userId, @RequestParam(required = false, defaultValue = "0") int page,
                                            @RequestParam(required = false, defaultValue = "10") int pageSize){
        log.info("find posts by userId and page:" + page +", pageSize:" +pageSize);
        Pageable pageable = PageRequest.of(page, pageSize);
        return R.success(postService.findAllByUserId(userId, pageable));
    }

    @GetMapping("/followings")
    @AuthorizedRoles(roles = {"ROLE_USER", "ROLE_ADMIN"})
    public R<List<PostResponse>> findFollowingsByUserId(HttpServletRequest request, @RequestParam(required = false, defaultValue = "0") int page,
                                              @RequestParam(required = false, defaultValue = "10") int pageSize){
        log.info("find followings by userId and page:" + page +", pageSize:" +pageSize);
        String username = AuthUtils.getUserName(request);
        UserResponse user = userClient.findByUsername(username).getData();
        Pageable pageable = PageRequest.of(page, pageSize);
        return R.success(postService.findFollowingsByUserId(user.getId(), pageable));
    }


    @DeleteMapping("/{postId}")
    @AuthorizedRoles(roles = {"ROLE_USER", "ROLE_ADMIN"})
    public R delete(HttpServletRequest request, @PathVariable String postId){
        String username = AuthUtils.getUserName(request);
        UserResponse user = userClient.findByUsername(username).getData();
        postService.delete(user.getId(), postId);
        return R.success(null);
    }

    @PostMapping("/upvote/{postId}")
    @AuthorizedRoles(roles = {"ROLE_USER", "ROLE_ADMIN"})
    public R upvote(HttpServletRequest request, @PathVariable String postId){
        String username = AuthUtils.getUserName(request);
        UserResponse user = userClient.findByUsername(username).getData();
        int upvotes = postService.upvote(user.getId(), postId);
        return R.success(upvotes);
    }

    @PostMapping("/unUpvote/{postId}")
    @AuthorizedRoles(roles = {"ROLE_USER", "ROLE_ADMIN"})
    public R unUpvote(HttpServletRequest request, @PathVariable String postId){
        String username = AuthUtils.getUserName(request);
        UserResponse user = userClient.findByUsername(username).getData();
        int upvotes = postService.unUpvote(user.getId(), postId);
        return R.success(upvotes);
    }

    @PostMapping("/downvote/{postId}")
    @AuthorizedRoles(roles = {"ROLE_USER", "ROLE_ADMIN"})
    public R downvote(HttpServletRequest request, @PathVariable String postId){
        String username = AuthUtils.getUserName(request);
        UserResponse user = userClient.findByUsername(username).getData();
        int downvotes = postService.downvote(user.getId(), postId);
        return R.success(downvotes);
    }

    @PostMapping("/unDownvote/{postId}")
    @AuthorizedRoles(roles = {"ROLE_USER", "ROLE_ADMIN"})
    public R unDownvote(HttpServletRequest request, @PathVariable String postId){
        String username = AuthUtils.getUserName(request);
        UserResponse user = userClient.findByUsername(username).getData();
        int downvotes = postService.unDownvote(user.getId(), postId);
        return R.success(downvotes);
    }



}
