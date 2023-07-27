package works.itireland.post.tag;

import works.itireland.clients.tag.TagResponse;

import java.util.List;

public interface TagService {
    List<TagResponse> findAll();
}
