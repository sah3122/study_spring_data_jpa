package me.study.springdatajpa.post;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @EntityGraph(value = "Comment.post", type = EntityGraph.EntityGraphType.FETCH)
    Optional<Comment> getById(Long id);
}
