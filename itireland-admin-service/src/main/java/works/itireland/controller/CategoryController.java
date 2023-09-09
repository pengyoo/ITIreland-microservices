package works.itireland.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import works.itireland.clients.R;
import works.itireland.clients.post.*;
import works.itireland.exception.ApiRequestException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryClient categoryClient;
    @GetMapping
    public ResponseEntity<List<CategoryResponse>> findAll(@RequestParam(defaultValue = "0", required = false) Integer _start,
                                                          @RequestParam(defaultValue = "10", required = false) Integer _end,
                                                          @RequestParam(defaultValue = "ctime", required = false) String sort,
                                                          HttpServletResponse response
    ){
        int pageSize = _end - _start;
        int page = _start / (pageSize - 1);
        categoryClient.findAll(page, pageSize);
        String count = String.valueOf(categoryClient.count());
        response.addHeader("x-total-count", count);
        response.addHeader("Access-Control-Expose-Headers", "x-total-count");
        return new ResponseEntity<>(categoryClient.findAll(page, pageSize).getData(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> findById(@PathVariable String id) {
        return new ResponseEntity<>(categoryClient.get(id).getData(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> add(@RequestBody CategoryRequest categoryRequest) {
        return new ResponseEntity<>(categoryClient.save(categoryRequest).getData(), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CategoryResponse> update(@RequestBody CategoryRequest categoryRequest) {
        return new ResponseEntity<>(categoryClient.save(categoryRequest).getData(), HttpStatus.OK);
    }

}
