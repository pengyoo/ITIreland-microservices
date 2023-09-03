package works.itireland.post.tag;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import works.itireland.clients.R;
import works.itireland.clients.post.TagResponse;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tags")
@AllArgsConstructor
public class TagController {

    private final TagService tagService;
    @GetMapping("/")
    public R<List<TagResponse>> findAll() {
        return R.success(tagService.findAll());
    }
}
