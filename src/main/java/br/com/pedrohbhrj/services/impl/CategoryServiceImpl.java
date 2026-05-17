package br.com.pedrohbhrj.services.impl;

import br.com.pedrohbhrj.DTO.request.CategoryRequest;
import br.com.pedrohbhrj.DTO.request.CategoryUpdateRequest;
import br.com.pedrohbhrj.DTO.response.CategoryResponse;
import br.com.pedrohbhrj.exceptions.AlreadyExistsException;
import br.com.pedrohbhrj.exceptions.NotFoundException;
import br.com.pedrohbhrj.models.Category;
import br.com.pedrohbhrj.repository.CategoryRepository;
import br.com.pedrohbhrj.services.interf.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public CategoryResponse createCategory(CategoryRequest request) {

        if (categoryRepository.existsByNameIgnoreCase(request.name())) {
            throw new AlreadyExistsException("Already found resource with this name");
        }

        Category category = new Category();

        category.setName(request.name());

        Category categorySaved = categoryRepository.save(category);

        log.info("Category created successfully, with name: {}", categorySaved.getName());

        return new CategoryResponse(categorySaved.getId(), categorySaved.getName(), categorySaved.getParentId());
    }

    @Override
    @Transactional
    public CategoryResponse updateCategory(Long categoryId, CategoryUpdateRequest request) {

        if (categoryRepository.existsByNameIgnoreCase(request.name())) {
            throw new AlreadyExistsException("Already found resource with this name");
        }

        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new NotFoundException("Category not found"));

        if (request.name() != null) {
            category.setName(request.name());
        }

        category.setParentId(request.parentId());

        Category categorySaved = categoryRepository.save(category);

        log.info("Category updated successfully, with name: {}", categorySaved.getName());

        return new CategoryResponse(categorySaved.getId(), categorySaved.getName(), categorySaved.getParentId());
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponse findCategoryById(Long categoryId) {

        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new NotFoundException("Category not found"));

        log.info("Category found successfully");

        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getParentId()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> findAllCategories() {
        List<CategoryResponse> response = categoryRepository
                .findAll()
                .stream()
                .map(c -> new CategoryResponse(c.getId(), c.getName(), c.getParentId()))
                .toList();

        log.info("Categories found successfully");

        return response;
    }

    @Override
    @Transactional
    public void deleteCategory(Long categoryId) {

        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new NotFoundException("Category not found"));

        categoryRepository.delete(category);

        log.info("Category deleted successfully, with name: {}",category.getName());
    }
}
