package com.book.manager.dao;

import com.book.manager.entity.Users;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Description
 */
@Mapper
@Component
public interface UsersMapper {

    /**
     * 模糊分页查询用户
     * @param keyword 关键字
     * @return
     */
    List<Users> findListByLike(String keyword);
    Users findUser(String username);
    int updateUsers(Users user);

    Users findUserById(Integer id);
    int addUser(Users users);
    int delUser(Integer id);
}
