package me.study.springdatajpa;

import org.hibernate.Session;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Component
@Transactional
public class JpaRunner implements ApplicationRunner {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        TypedQuery<Post> query = entityManager.createQuery("SELECT p FROM Post AS p", Post.class);
        List<Post> posts = query.getResultList();
        posts.forEach(System.out::println);

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Post> query1 = criteriaBuilder.createQuery(Post.class);
        Root<Post> root = query1.from(Post.class);
        query1.select(root);

        List<Post> posts1 = entityManager.createQuery(query1).getResultList();
        posts1.forEach(System.out::println);

        List resultList = entityManager.createNativeQuery("SELECT * FROM post", Post.class).getResultList();
    }

//    Account account = new Account();
//        account.setUsername("dongchul");
//        account.setPassword("jpa");
//
//    Study study = new Study();
//        study.setName("jpadata");
//
//        account.addStudy(study);
//    /////////////////////////////////////
//    Post post = new Post();
//        post.setTitle("Spring Data JPA");
//
//    Comment comment = new Comment();
//        comment.setComment("ddd");
//        post.addCommment(comment);
//
//    Comment comment1 = new Comment();
//        comment1.setComment("ddd");
//
//        post.addCommment(comment1);
//
//    Session session = entityManager.unwrap(Session.class); // hibernate의 가장 핵심적인 객체
//        session.save(account);
//        session.save(post);
//    //entityManager.persist(account);
//
//    Account dongchul = session.load(Account.class, account.getId());
//        dongchul.setUsername("leedongchul"); // dirty checking
}
