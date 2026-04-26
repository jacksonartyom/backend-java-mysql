package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.dto.request.CategoryRequest;
import com.example.demo.dto.response.CategoryResponse;
import com.example.demo.entity.Category;
import com.example.demo.mapper.CategoryMapper;
import com.example.demo.repository.CategoryRepository;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository repo;

    @Mock
    private CategoryMapper mapper;

    @InjectMocks
    private CategoryService service;

    @Test
    void shouldReturnAllCategories() {
        // given
        Category c1 = new Category();
        Category c2 = new Category();

        CategoryResponse r1 = new CategoryResponse(null, null, null);
        CategoryResponse r2 = new CategoryResponse(null, null, null);

        when(repo.findAll()).thenReturn(List.of(c1, c2));
        when(mapper.toResponse(c1)).thenReturn(r1);
        when(mapper.toResponse(c2)).thenReturn(r2);

        // when
        List<CategoryResponse> result = service.getAll();

        // then
        assertEquals(2, result.size());
        assertEquals(r1, result.get(0));
        assertEquals(r2, result.get(1));

        verify(repo).findAll();
        verify(mapper, times(2)).toResponse(any(Category.class));
    }

    @Test
    void shouldCreateCategorySuccessfully() {
        // given
        CategoryRequest req = new CategoryRequest();
        Category entity = new Category();
        Category saved = new Category();
        CategoryResponse response = new CategoryResponse(null, null, null);

        when(mapper.toEntity(req)).thenReturn(entity);
        when(repo.save(entity)).thenReturn(saved);
        when(mapper.toResponse(saved)).thenReturn(response);

        // when
        CategoryResponse result = service.create(req);

        // then
        assertNotNull(result);
        assertEquals(response, result);

        verify(mapper).toEntity(req);
        verify(repo).save(entity);
        verify(mapper).toResponse(saved);
    }

    @Test
    void shouldDeleteCategorySuccessfully() {
        // given
        String categoryId = "cat-001";

        when(repo.deleteByCategoryId(categoryId)).thenReturn(1L);

        // when
        service.delete(categoryId);

        // then
        verify(repo).deleteByCategoryId(categoryId);
    }

    @Test
    void shouldThrowExceptionWhenCategoryNotFound() {
        // given
        String categoryId = "cat-999";

        when(repo.deleteByCategoryId(categoryId)).thenReturn(0L);

        // when & then
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            service.delete(categoryId);
        });

        assertEquals("Category not found", ex.getMessage());

        verify(repo).deleteByCategoryId(categoryId);
    }
}