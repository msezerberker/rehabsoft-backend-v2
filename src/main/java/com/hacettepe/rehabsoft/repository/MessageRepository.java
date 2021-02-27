package com.hacettepe.rehabsoft.repository;

import com.hacettepe.rehabsoft.entity.Message;
import com.hacettepe.rehabsoft.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Long> {

    @Query("select m from Message m where m.senderUser.id=?1 and m.receiverUser.id=?2 or m.senderUser.id=?2 and m.receiverUser.id=?1")
    List<Message> getHistory(Long senderId, Long receiverId);


/*
    @Transactional
    @Modifying
    @Query("update ExerciseVideo ev set ev.title =:title where ev.id =:id")
    void updateTitleById(@Param("title") String title, @Param("id") Long id);
*/
}
