package me.study.springdatajpa.post;

import com.querydsl.core.types.Predicate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import(PostRepositoryTestConfig.class)
public class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    ApplicationContext applicationContext;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void event() {
        Post post = new Post();
        post.setTitle("event");
        PostPublishedEvent event = new PostPublishedEvent(post);

        applicationContext.publishEvent(event);
    }

    @Test
    public void crud() {
        Post post = new Post();
        post.setContent("test");
        postRepository.save(post.publish());

        //postRepository.findMyPost();

        //postRepository.delete(post);
        Predicate predicate = QPost.post.title.containsIgnoreCase("hibernate");
        Optional<Post> one = postRepository.findOne(predicate);
        assertThat(one).isEmpty();

    }

    @Test
    public void save() {
        Post post = new Post();
        post.setTitle("jpa");
        Post save = postRepository.save(post);//persist

        assertThat(entityManager.contains(post)).isTrue();
        assertThat(entityManager.contains(save)).isTrue();
        assertThat(save == post);

        Post updatePost = new Post();
        post.setId(post.getId());
        post.setTitle("hibernate");
        Post updatedPost = postRepository.save(updatePost);//merge 상태에는 파라미터로 전달한 객체를 영속화 하지 않는다. 항상 리턴 받는 객체를 사용하자.

        assertThat(entityManager.contains(updatedPost)).isTrue();
        assertThat(entityManager.contains(updatePost)).isFalse();
        assertThat(updatedPost == updatePost);

        List<Post> all = postRepository.findAll();
        assertThat(all.size()).isEqualTo(1);
    }

    @Test
    public void findByTitleStartWith() {
        Post post = new Post();
        post.setTitle("jpa");
        Post save = postRepository.save(post);//persist

        List<Post> all = postRepository.findByTitleStartsWith("jpa");
        assertThat(all.size()).isEqualTo(1);

    }

    @Test
    public void findByTitle() {
        Post post = new Post();
        post.setTitle("jpa");
        Post save = postRepository.save(post);//persist

        List<Post> all = postRepository.findByTitle("jpa", Sort.by("title"));
        assertThat(all.size()).isEqualTo(1);

    }

    @Test
    public void updateTitle() {
        Post post = new Post();
        post.setTitle("jpa");
        Post save = postRepository.save(post);//persist

        int count = postRepository.updateTitle("hibernate", save.getId());
        assertThat(count).isEqualTo(1);

        Optional<Post> byId = postRepository.findById(save.getId());
        assertThat(byId.get().getTitle()).isEqualTo("jpa");

    }

}