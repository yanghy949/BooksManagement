package com.book.manager.dao;

import com.book.manager.entity.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Description 图书

 */
@Mapper
@Component
public interface BookMapper {

    /**
     * 模糊分页查询用户
     * @param keyword 关键字
     * @return
     */
    List<Book> findBookListByLike(String keyword);

    /**
     * 编辑用户
     * @param book
     * @return
     */
    int updateBook(Book book);

    @Select("select * from book where id=#{id}")
    @Results({@Result(property = "publishTime", column = "publish_time")})
    Book findBookById(Integer id);

    int deleteBook(Integer id);

    int addBook(Book book);
}
