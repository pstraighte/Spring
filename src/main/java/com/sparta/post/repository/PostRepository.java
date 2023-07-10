package com.sparta.post.repository;

import com.sparta.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PostRepository extends JpaRepository<Post, Long> {
    public List<Post> findAllByOrderByCreatedAtDesc();
}
