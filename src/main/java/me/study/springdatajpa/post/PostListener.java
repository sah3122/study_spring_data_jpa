package me.study.springdatajpa.post;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;

public class PostListener{

    @EventListener
    public void onApplicationEvent(PostPublishedEvent postPublishedEvent) {
        System.out.println(postPublishedEvent.getPost());
    }
}
