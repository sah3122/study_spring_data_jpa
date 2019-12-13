package me.study.springdatajpa.post;

import me.study.springdatajpa.MyRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>, QuerydslPredicateExecutor<Post> {
    List<Post> findByTitleStartsWith(String title);

    @Query("SELECT p FROM Post AS p WHERE p.title = ?1")
    List<Post> findByTitle(String title, Sort sort);
}
