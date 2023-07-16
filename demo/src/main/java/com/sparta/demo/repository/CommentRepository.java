package com.sparta.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.demo.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}