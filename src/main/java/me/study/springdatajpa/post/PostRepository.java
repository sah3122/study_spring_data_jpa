package me.study.springdatajpa.post;

import me.study.springdatajpa.MyRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>, QuerydslPredicateExecutor<Post> {
    List<Post> findByTitleStartsWith(String title);

    @Query("SELECT p FROM Post AS p WHERE p.title = :title")
    List<Post> findByTitle(@Param("title") String title, Sort sort);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Post p Set p.title = ?1 WHERE id = ?2")
    int updateTitle(String hibernate, Long id);
}
