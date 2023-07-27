package works.itireland.post.tag;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import works.itireland.clients.tag.TagResponse;

import java.util.List;

@RestController
@RequestMapping("/api/posts/tags")
@AllArgsConstructor
public class TagController {

    private final TagService tagService;
    @GetMapping
    public List<TagResponse> findAll() {
        return tagService.findAll();
    }
}
