package com.example.crud.repositories.blogs;

import com.example.crud.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    public Optional<Tag> findTagByTagName(String tagName);
}
