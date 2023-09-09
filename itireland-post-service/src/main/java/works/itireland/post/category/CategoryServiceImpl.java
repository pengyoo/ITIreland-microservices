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
        Category category = new Category(postRequest.getId(), postRequest.getState(), postRequest.getSort());
        category = categoryRepository.insert(category);
        return new CategoryResponse(category.getId(), category.getState(), category.getSort());
    }

    @Override
    public Page<CategoryResponse> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable).map((category ->
            getCategoryReponse(category)
        ));
    }

    @Override
    public CategoryResponse findById(String id) {
        return categoryRepository.findById(id).map(category -> getCategoryReponse(category)).orElseThrow();
    }

    @Override
    public long count() {
        return categoryRepository.count();
    }

    public CategoryResponse getCategoryReponse(Category category){
        return CategoryResponse.builder()
                .id(category.getId())
                .state(category.getState())
                .sort(category.getSort())
                .build();
    }
}
