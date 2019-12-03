package me.study.springdatajpa;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJdbcTest
public class CommentRepositoryTest {
    @Autowired
    CommentRepository commentRepository;

    @Test
    public void crud() throws IllegalAccessException {
        Comment comment = new Comment();
        comment.setComment("test");

        commentRepository.save(comment);

        List<Comment> all = commentRepository.findAll();
        assertThat(all.size()).isEqualTo(1);

        Optional<Comment> byId = commentRepository.findById(100l);
        assertThat(byId).isEmpty(); // 단일 값은 널 값이 나올수 있지만 리스트는 비어있는 리스트 객체가 리턴된다.
        Comment comment1 = byId.orElseThrow(IllegalAccessException::new);

        this.createComment(100, "test1");
        this.createComment(10, "test2");

        List<Comment> comments = commentRepository.findByCommentContainsIgnoreCase("test");

        assertThat(comments.size()).isEqualTo(1);

        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "likeCount"));
        // Page<Comment> commentPage = commentRepository.findByCommentContainsIgnore("test", pageRequest);
        try (Stream<Comment> commentPage = commentRepository.findByCommentContainsIgnore("test", pageRequest)){
            Comment firstComment = commentPage.findFirst().get();
            assertThat(firstComment.getLikeCount()).isEqualTo(100);
        }



    }

    private void createComment(int likeCount, String comment) {
        Comment newComment = new Comment();
        newComment.setComment(comment);
        newComment.setLikeCount(likeCount);

        commentRepository.save(newComment);
    }

}