package me.study.springdatajpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.List;
import java.util.stream.Stream;

@RepositoryDefinition(domainClass = Comment.class, idClass = Long.class)
public interface CommentRepository extends MyRepository<Comment, Long> {

//    @Query(value = "SELECT c FROM Comment AS c", nativeQuery = true)
//    List<Comment> findByCommentContains(String keyword);

    // Pageable 권장 내부적으로 sort를 가지고 있음.
    Page<Comment> findByLikeCountGreaterThanAndPostOrdOrderByCreatedDesc(int likeCount, Post post, Pageable pageable);

    List<Comment> findByLikeCountGreaterThanAndPostOrdOrderByCreatedDesc(int likeCount, Post post, Sort sort);

    List<Comment> findByCommentContains(String keyword);

    List<Comment> findByCommentContainsIgnoreCase(String keyword);

    List<Comment> findByCommentContainsIgnoreCaseAndLikeCountGreaterThan(String keyword, int likeCount);

    //Page<Comment> findByCommentContainsIgnore(String keyword, Pageable pageable);

    Stream<Comment> findByCommentContainsIgnore(String keyword, Pageable pageable);
}
