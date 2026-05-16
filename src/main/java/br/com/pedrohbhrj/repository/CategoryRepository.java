package br.com.pedrohbhrj.repository;

import br.com.pedrohbhrj.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByNameContainingIgnoreCase(String name);
}
