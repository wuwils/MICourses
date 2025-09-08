package com.xiaomi.wusheng.work_0304.dao;

import com.xiaomi.wusheng.work_0304.model.KnowledgeBase;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface KnowledgeBaseMapper {

    @Select("SELECT * FROM knowledge_base ORDER BY update_time DESC")
    List<KnowledgeBase> findAll();

    @Select("<script>" +
            "SELECT id, question, answer, keywords, update_time as updateTime " +
            "FROM knowledge_base " +
            "WHERE 1=1 " +
            "<if test='question != null and question != \"\"'>AND question LIKE CONCAT('%', #{question}, '%') </if>" +
            "<if test='keywords != null and keywords != \"\"'>AND keywords LIKE CONCAT('%', #{keywords}, '%') </if>" +
            "ORDER BY update_time DESC" +
            "</script>")
    List<KnowledgeBase> search(String question, String keywords);
}
