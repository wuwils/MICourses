package com.xiaomi.wusheng.work_0304.dao;

import com.xiaomi.wusheng.work_0304.model.Conversation;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface ConversationMapper {
    @Insert("INSERT INTO conversation (start_time) VALUES (NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertConversation(Conversation conversation);
}
