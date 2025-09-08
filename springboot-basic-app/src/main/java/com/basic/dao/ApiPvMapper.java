package com.basic.dao;

import com.basic.model.ApiPv;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface ApiPvMapper {
    final String Table = "api_pv";
    final String Columns = "id, uri, pv, ctime, utime";

    // 防止 SQL 注入
    @Insert("insert into " + Table + " (uri, pv) values(#{uri}, #{pv})")
    int insert(@Param("uri") String uri, @Param("pv") int pv);

    @Insert("update " + Table + " set pv = pv + #{pv} where uri = #{uri}")
    int update(@Param("uri") String uri, @Param("pv") int pv);

    // 先尝试插入，如果存在则更新
    @Insert("insert into " + Table + " (uri, pv) values(#{uri}, #{pv})" +
            " on duplicate key update pv = pv + #{pv} ")
    int insertOrUpdate(@Param("uri") String uri, @Param("pv") int pv);

    @Select("select " + Columns + " from " + Table
        + " where uri=#{uri}")
    ApiPv getPv(@Param("uri") String uri);
}
