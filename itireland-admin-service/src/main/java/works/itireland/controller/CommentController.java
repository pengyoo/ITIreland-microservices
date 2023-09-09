package works.itireland.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import works.itireland.clients.R;
import works.itireland.clients.post.*;
import works.itireland.clients.user.UserResponse;
import works.itireland.exception.ApiRequestException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentClient commentClient;
    @GetMapping
    public ResponseEntity<List<CommentResponse>> findAll(@RequestParam(defaultValue = "0", required = false) Integer _start,
                                      @RequestParam(defaultValue = "10", required = false) Integer _end,
                                      @RequestParam(defaultValue = "ctime", required = false) String sort,
                                      HttpServletResponse response
    ){
        int pageSize = _end - _start;
        int page = _start / (pageSize - 1);

        R<List<CommentResponse>> result = commentClient.findAllComments(page, pageSize, sort);
        if (result == null){
            throw new ApiRequestException("No Data");
        }
        String count = String.valueOf(commentClient.count());
        response.addHeader("x-total-count", count);
        response.addHeader("Access-Control-Expose-Headers", "x-total-count");
        return new ResponseEntity<>(result.getData(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentResponse> findById(@PathVariable String id) {
        return new ResponseEntity<>(commentClient.getById(id).getData(), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CommentResponse> update(@RequestBody CommentRequest commentRequest) {
        return new ResponseEntity<>(commentClient.save(commentRequest).getData(), HttpStatus.OK);
    }

}
