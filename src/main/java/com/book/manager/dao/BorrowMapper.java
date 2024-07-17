package com.book.manager.dao;

import com.book.manager.entity.Borrow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Description 借阅管理

 */
@Mapper
@Component
public interface BorrowMapper {

    int addBorrow(Borrow borrow);

    int updateBorrow(Borrow borrow);

    List<Borrow> findByUserId(Integer id);


    Borrow findBorrowByUserIdAndBookId(@Param("userId") Integer userId, @Param("bookId") Integer bookId);

    int updateBor(Map<String, Object> map);

    int retBook(Integer userId, Integer bookId);

}