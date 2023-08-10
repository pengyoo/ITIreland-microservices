package works.itireland.post.category;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import works.itireland.clients.post.CategoryRequest;
import works.itireland.clients.post.CategoryResponse;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;
    @Override
    public CategoryResponse insert(CategoryRequest postRequest) {
        Category category = new Category(postRequest.getCategory(), postRequest.getState(), postRequest.getSort());
        category = categoryRepository.insert(category);
        return new CategoryResponse(category.getCategory(), category.getState(), category.getSort());
    }

    @Override
    public Page<CategoryResponse> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable).map((category ->
             CategoryResponse.builder()
                    .category(category.getCategory())
                    .state(category.getState())
                    .sort(category.getSort())
                    .build()
        ));
    }
}
