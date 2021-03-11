package com.zn.wr.dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
/**
 * @author ：MY
 * @description：TODO
 * @date ：2021/3/11 9:57
 */
@Repository
public class contentDAO {
    @Autowired // 注入 spring jdbctemplate
    private JdbcTemplate jdbcTemplate;

    // 手动保存功能
    public boolean insert(String content) {
        try {
            // 向数据库插入新内容
            String sql = " INSERT INTO w_content(content,createtime,updatetime )VALUES (?,now(),now())";
            Object[] params = new Object[]{content};
            // jdbcTemplate.update 方法：用于执行新增、修改、删除等语句
            jdbcTemplate.update(sql, params);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 刷新最新数据
    public String search() {
        String content = null;
        try {
            // 向数据库查询内容
            String sql = "SELECT content from w_content  ORDER BY updatetime DESC LIMIT 1";
            // 查询单个对象一般使用 jdbcTemplate.queryForObject
            content = (String) jdbcTemplate.queryForObject(sql, java.lang.String.class);

        } catch (Exception e) {
            e.printStackTrace();

        }
        return content;
    }

    public Map<String, Object> query() {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        String content = null;
        try {
            String sql = "SELECT content from w_content  ORDER BY updatetime DESC LIMIT 1";
            content = (String) jdbcTemplate.queryForObject(sql, java.lang.String.class);
            modelMap.put("state", true);
            modelMap.put("initcontent", content);
        } catch (Exception e) {
            e.printStackTrace();
            content = "查询结果失败";
            modelMap.put("state", false);
            modelMap.put("initcontent", content);
        }
        return modelMap;
    }
}
