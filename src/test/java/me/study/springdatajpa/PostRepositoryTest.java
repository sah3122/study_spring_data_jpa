package me.study.springdatajpa;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest // Repository bean만 생성
public class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Test
    @Rollback(false) // transaction 때문에 어차피 rollback될거라 hibernate가 insert쿼리를 날리지 않음. 쿼리를 보고 싶다면 해당 애노테이션을 사용해야함
    public void crudRepository() {
        // given
        Post post = new Post();
        post.setTitle("title");

        //when
        Post newPost = postRepository.save(post);
        //then
        assertThat(newPost.getId()).isNotNull();

        //when
        List<Post> posts = postRepository.findAll();
        //then
        assertThat(posts.size()).isEqualTo(1);
        assertThat(posts).contains(newPost);

        //when
        Page<Post> page = postRepository.findAll(PageRequest.of(0, 10));
        //then
        assertThat(page.getTotalElements()).isEqualTo(1);
        assertThat(page.getNumber()).isEqualTo(0);
        assertThat(page.getSize()).isEqualTo(10);
        assertThat(page.getNumberOfElements()).isEqualTo(1);

        // when
        postRepository.findByTitleContains("title", PageRequest.of(0, 10));

        //then
        assertThat(page.getTotalElements()).isEqualTo(1);
        assertThat(page.getNumber()).isEqualTo(0);
        assertThat(page.getSize()).isEqualTo(10);
        assertThat(page.getNumberOfElements()).isEqualTo(1);
    }
}