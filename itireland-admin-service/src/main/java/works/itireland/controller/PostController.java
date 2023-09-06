package works.itireland.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import works.itireland.clients.post.PostClient;
import works.itireland.clients.post.PostRequest;
import works.itireland.clients.post.PostResponse;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostClient postClient;

    @PatchMapping
    public ResponseEntity<PostResponse> save(@RequestBody PostRequest postRequest, HttpServletRequest request) {
        return new ResponseEntity<PostResponse>(postClient.save(postRequest).getData(), HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> findById(@PathVariable String postId) {
        return new ResponseEntity<>(postClient.findById(postId).getData(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> findAll(@RequestParam(required = false, defaultValue = "0") int _start,
                                                      @RequestParam(required = false, defaultValue = "20") int _end){
        int pageSize = _end - _start + 1;
        int page = _start / (pageSize - 1);
        return new ResponseEntity<>(postClient.findAll(page, pageSize).getData(), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostResponse>> findAllByUserId(@RequestParam("userId") Long userId,@RequestParam(required = false, defaultValue = "0") int _start,
                                                      @RequestParam(required = false, defaultValue = "20") int _end){
        int pageSize = _end - _start + 1;
        int page = _start / (pageSize - 1);
        return new ResponseEntity<>(postClient.findAllByUserId(userId, page, pageSize).getData(), HttpStatus.OK);
    }
}
