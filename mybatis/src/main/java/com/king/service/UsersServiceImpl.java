package com.king.service;

import com.king.bean.User;
import com.king.bean.Users;
import com.king.mapper.UserMapper;
import com.king.mapper.UsersMapper;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @program: springboot
 * @description:
 * @author: King
 * @create: 2021-09-15 17:10
 */
@Service
public class UsersServiceImpl {
    @Autowired
    UsersMapper usersMapper;
    @Autowired
    SqlSessionFactory sqlSessionFactory;

    public int queryCursor(int limit) throws Exception {
        AtomicInteger i = new AtomicInteger();
        try (SqlSession sqlSession = sqlSessionFactory.openSession();) {
            sqlSession.getMapper(UsersMapper.class).scan(limit).forEach(o -> {
                i.getAndIncrement();
            });
        }
        return i.get();
    }

    public int queryLimit(int limit) throws Exception {
        AtomicInteger i = new AtomicInteger();
        try {
            usersMapper.selectList(limit).forEach(o -> {
                i.incrementAndGet();
            });
            return i.get();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }
}
