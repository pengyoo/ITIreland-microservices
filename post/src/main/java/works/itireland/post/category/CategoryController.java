package works.itireland.post.category;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import works.itireland.clients.post.CategoryRequest;
import works.itireland.clients.post.CategoryResponse;
import works.itireland.clients.user.UserClient;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;
    private final UserClient userClient;

    @PostMapping
    public CategoryResponse save(@RequestBody CategoryRequest categoryRequest){

        log.info("save category:" + categoryRequest);
        return categoryService.insert(categoryRequest);
    }

    @GetMapping
    public List<CategoryResponse> findAll(@RequestParam(required = false, defaultValue = "0") int page,
                                      @RequestParam(required = false, defaultValue = "10") int pageSize){
        Pageable pageable = PageRequest.of(page, pageSize);
        return categoryService.findAll(pageable).toList();
    }
}
