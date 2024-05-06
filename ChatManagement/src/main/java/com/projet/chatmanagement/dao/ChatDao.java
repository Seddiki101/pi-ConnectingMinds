package com.projet.chatmanagement.dao;

import com.projet.chatmanagement.dto.chat.ChatPreview;
import com.projet.chatmanagement.entity.Chat;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatDao extends JpaRepository<Chat, Long> {

//    @Query(value = "SELECT * FROM chats c INNER JOIN chat_users cu ON c.chat_id = cu.chat_id WHERE cu.user_id = :userId", nativeQuery = true)
//    List<Chat> findAllChatsByUserId(@Param("userId") Long userId);
//@Query(value = "SELECT new ChatPreview(c.chatId, c.name) FROM Chat c JOIN c.memberIds m WHERE m = :userId", nativeQuery = false)
//List<ChatPreview> findAllChatsByUserId(@Param("userId") Long userId);

    //wsemaGPT query
@Query(value = "SELECT c.chat_id AS chatId, c.name AS chatName, " +
        "cu.user_id AS otherUserId, m.content AS lastMessageContent, m.timestamp AS lastMessageTimestamp " +
        "FROM chats c " +
        "JOIN chat_users cu ON c.chat_id = cu.chat_id AND cu.user_id != :userId " +
        "JOIN messages m ON m.chat_id = c.chat_id " +
        "WHERE m.message_id = (" +
        "  SELECT MAX(m2.message_id) " +
        "  FROM messages m2 " +
        "  WHERE m2.chat_id = c.chat_id" +
        ") AND EXISTS (" +
        "  SELECT 1 FROM chat_users cu2 WHERE cu2.chat_id = c.chat_id AND cu2.user_id = :userId" +
        ") " +
        "ORDER BY m.timestamp DESC", nativeQuery = true)
List<Object[]> findChatDataByUserId(@Param("userId") Long userId);


@Query(value ="SELECT user_id from chat_users  WHERE chat_id = :chatId AND user_id != :userId" ,nativeQuery = true)
Long findOtherMemberId(@Param("userId") Long userId, @Param("chatId") Long chatId);



    @Modifying
    @Transactional
    @Query("DELETE FROM Chat c WHERE c.chatId = :chatId")
    int deleteByChatId(Long chatId);

    /*
    @Query("SELECT c FROM Chat c WHERE :userId1 MEMBER OF c.memberIds AND :userId2 MEMBER OF c.memberIds AND SIZE(c.memberIds) = 2")
    Optional<Chat> findChatByUserIds(Long userId1, Long userId2);
    */
    @Query("SELECT c FROM Chat c WHERE (:userId1 MEMBER OF c.memberIds AND :userId2 MEMBER OF c.memberIds) OR (:userId2 MEMBER OF c.memberIds AND :userId1 MEMBER OF c.memberIds) AND SIZE(c.memberIds) = 2")
    Optional<Chat> findChatByUserIds(Long userId1, Long userId2);


}