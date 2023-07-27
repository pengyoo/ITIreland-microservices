package works.itireland.post.category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import works.itireland.clients.post.CategoryRequest;
import works.itireland.clients.post.CategoryResponse;
import works.itireland.clients.post.PostRequest;
import works.itireland.clients.post.PostResponse;

public interface CategoryService {
    CategoryResponse insert(CategoryRequest postRequest);

    Page<CategoryResponse> findAll(Pageable pageable);
}
