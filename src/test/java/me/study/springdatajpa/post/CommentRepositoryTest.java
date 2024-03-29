package me.study.springdatajpa.post;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import static me.study.springdatajpa.post.CommentSpecs.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CommentRepositoryTest {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;

    @Test
    public void getComment() {
        Post post = new Post();
        post.setTitle("jpa");
        Post save = postRepository.save(post);

        Comment comment = new Comment();
        comment.setComment("comment");
        comment.setPost(save);
        comment.setUp(10);
        comment.setDown(1);

        commentRepository.save(comment);

        commentRepository.findById(1L);
        commentRepository.findByPost_Id(save.getId(), CommentSummary.class).forEach(c -> {
            System.out.println(c.getVotes());
        });
    }

    @Test
    public void specs() {
        Page<Comment> page = commentRepository.findAll(isBest().and(isGood()), PageRequest.of(0, 10));
    }

    @Test
    public void qbe() {
        Comment prove = new Comment();
        prove.setBest(true);

        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAny().withIgnorePaths("up", "path");
        Example<Comment> example = Example.of(prove, exampleMatcher);

        commentRepository.findAll(example);
    }

}