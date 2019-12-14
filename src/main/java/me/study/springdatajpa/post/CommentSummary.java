package me.study.springdatajpa.post;

import org.springframework.beans.factory.annotation.Value;

/**
 * Class로 만들수도 있지만 코드가 길어진다.
 */
public interface CommentSummary {

    String getComment();

    int getUp();

    int getDown();

    default String getVotes() {
        return getUp() + " " + getDown();
    }

//    @Value("#{target.up + ' ' + target.down}")  //Open Projection
//    String getVotes();

}
