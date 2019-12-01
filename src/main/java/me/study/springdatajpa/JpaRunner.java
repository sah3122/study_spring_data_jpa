package me.study.springdatajpa;

import org.hibernate.Session;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
@Transactional
public class JpaRunner implements ApplicationRunner {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Account account = new Account();
        account.setUsername("dongchul");
        account.setPassword("jpa");

        Study study = new Study();
        study.setName("jpadata");

        account.addStudy(study);
/////////////////////////////////////
        Post post = new Post();
        post.setTitle("Spring Data JPA");

        Comment comment = new Comment();
        comment.setComment("ddd");
        post.addCommment(comment);

        Comment comment1 = new Comment();
        comment1.setComment("ddd");

        post.addCommment(comment1);

        Session session = entityManager.unwrap(Session.class); // hibernate의 가장 핵심적인 객체
        session.save(account);
        session.save(post);
        //entityManager.persist(account);

        Account dongchul = session.load(Account.class, account.getId());
        dongchul.setUsername("leedongchul"); // dirty checking
    }
}
