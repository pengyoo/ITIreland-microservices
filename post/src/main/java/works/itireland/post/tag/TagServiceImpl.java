package works.itireland.post.tag;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import works.itireland.clients.tag.TagResponse;

import java.util.List;

@Service
@AllArgsConstructor
public class TagServiceImpl implements TagService{

    private final TagRepository tagRepository;
    public List<TagResponse> findAll(){
        return tagRepository.findAll().stream().map(tag ->
                TagResponse.builder()
                        .tag(tag.getTag())
                        .build())
                .toList();
    }
}
