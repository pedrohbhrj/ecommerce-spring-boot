package br.com.pedrohbhrj.services.interf;

import br.com.pedrohbhrj.DTO.request.CategoryRequest;
import br.com.pedrohbhrj.DTO.request.CategoryUpdateRequest;
import br.com.pedrohbhrj.DTO.response.CategoryResponse;

import java.util.List;

public interface CategoryService {

    CategoryResponse createCategory(CategoryRequest request);
    CategoryResponse updateCategory(Long categoryId,CategoryUpdateRequest request);
    CategoryResponse findCategoryById(Long categoryId);
    List<CategoryResponse> findAllCategories();
    void deleteCategory(Long categoryId);
}
