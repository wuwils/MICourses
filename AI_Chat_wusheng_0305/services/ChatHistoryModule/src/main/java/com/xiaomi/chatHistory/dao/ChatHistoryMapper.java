package com.xiaomi.chatHistory.dao;

import com.xiaomi.chatHistory.model.ChatHistory;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ChatHistoryMapper {

    @Insert("INSERT INTO chat_history (conversation_id, user_nickname, user_type, time, content) " +
            "VALUES (#{conversationId}, #{userNickname}, #{userType}, #{time}, #{content})")
    int insert(ChatHistory history);

    @Select("<script>" +
            "SELECT id, conversation_id as conversationId, user_nickname as userNickname, user_type as userType, time, content " +
            "FROM chat_history " +
            "WHERE 1=1 " +
            "<if test='conversationId != null'>AND conversation_id = #{conversationId} </if>" +
            "<if test='userNickname != null and userNickname != \"\"'>AND user_nickname LIKE CONCAT('%', #{userNickname}, '%') </if>" +
            "<if test='startTime != null and startTime != \"\"'>AND time &gt;= #{startTime} </if>" +
            "<if test='endTime != null and endTime != \"\"'>AND time &lt;= #{endTime} </if>" +
            "ORDER BY time DESC" +
            "</script>")
    List<ChatHistory> queryHistory(@Param("conversationId") Integer conversationId,
                                   @Param("userNickname") String userNickname,
                                   @Param("startTime") String startTime,
                                   @Param("endTime") String endTime);
}
