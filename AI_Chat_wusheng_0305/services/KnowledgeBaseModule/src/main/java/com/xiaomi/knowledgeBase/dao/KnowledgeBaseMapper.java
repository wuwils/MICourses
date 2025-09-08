package com.xiaomi.knowledgeBase.dao;

import com.xiaomi.knowledgeBase.model.KnowledgeBase;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface KnowledgeBaseMapper {

    @Select("SELECT id, question, answer, keywords, update_time as updateTime " +
            "FROM knowledge_base ORDER BY update_time DESC")
    List<KnowledgeBase> findAll();

    @Select("<script>" +
            "SELECT id, question, answer, keywords, update_time as updateTime " +
            "FROM knowledge_base " +
            "WHERE 1=1 " +
            "<if test='question != null and question != \"\"'>AND LOWER(question) LIKE CONCAT('%', LOWER(#{question}), '%') </if>" +
            "<if test='keywords != null and keywords != \"\"'>AND LOWER(keywords) LIKE CONCAT('%', LOWER(#{keywords}), '%') </if>" +
            "ORDER BY update_time DESC" +
            "</script>")
    List<KnowledgeBase> search(@Param("question") String question, @Param("keywords") String keywords);

    @Insert("INSERT INTO knowledge_base (question, answer, keywords) " +
            "VALUES (#{question}, #{answer}, #{keywords})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(KnowledgeBase kb);

    @Update("UPDATE knowledge_base SET question=#{question}, answer=#{answer}, keywords=#{keywords}, update_time=NOW() " +
            "WHERE id=#{id}")
    int update(KnowledgeBase kb);

    @Delete("DELETE FROM knowledge_base WHERE id=#{id}")
    int delete(Long id);
}
