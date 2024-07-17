package com.book.manager.service;

import cn.hutool.core.date.DateUtil;
import com.book.manager.dao.BookMapper;
import com.book.manager.entity.Book;
import com.book.manager.repos.BookRepository;
import com.book.manager.util.consts.Constants;
import com.book.manager.util.vo.BookOut;
import com.book.manager.util.vo.PageOut;
import com.book.manager.util.ro.PageIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 图书业务类
 */
@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookMapper bookMapper;



    public boolean addBook(Book book) {
        return bookMapper.addBook(book)>0;
    }

    /**
     * 编辑图书
     * @param book 图书对象
     * @return true or false
     */
    public boolean updateBook(Book book) {
        return bookMapper.updateBook(book) > 0;
    }

    /**
     * 图书详情
     * @param id 主键
     * @return 图书详情
     */
    public BookOut findBookById(Integer id) {
        Book book = bookMapper.findBookById(id);
        BookOut bookOut = new BookOut();

        bookOut.setId(book.getId());
        bookOut.setAuthor(book.getAuthor());
        bookOut.setName(book.getName());
        bookOut.setPages(book.getPages());
        bookOut.setPublish(book.getPublish());
        bookOut.setPrice(book.getPrice());
        bookOut.setPublishTime(DateUtil.format(book.getPublishTime(), Constants.DATE_FORMAT));
        bookOut.setSize(book.getSize());
        bookOut.setType(book.getType());
        bookOut.setPic(book.getPic());
        bookOut.setTranslate(book.getTranslate());
        bookOut.setIsbn(book.getIsbn());
        return bookOut;
    }

//    public Book findBook(Integer id) {
//
//        return null;
//    }

    /**
     * ISBN查询
     * @param isbn
     * @return
     */
    public BookOut findBookByIsbn(String isbn) {

        return null;
    }

    /**
     * 删除图书
     * @param id 主键
     * @return true or false
     */
    public boolean deleteBook(Integer id) {
        return bookMapper.deleteBook(id) >0;
    }


    /**
     * 图书搜索查询(mybatis 分页)
     * @param pageIn
     * @return
     */
    public PageOut getBookList(PageIn pageIn) {
        List<Book> books = bookMapper.findBookListByLike(pageIn.getKeyword());

        List<BookOut> booksOut = new ArrayList<>();
        for (Book book : books){
            BookOut bookOut = new BookOut();
            bookOut.setId(book.getId());
            bookOut.setAuthor(book.getAuthor());
            bookOut.setName(book.getName());
            bookOut.setPages(book.getPages());
            bookOut.setPublish(book.getPublish());
            bookOut.setPrice(book.getPrice());
            bookOut.setPublishTime(DateUtil.format(book.getPublishTime(),Constants.DATE_FORMAT));
            bookOut.setSize(book.getSize());
            bookOut.setType(book.getType());
            bookOut.setPic(book.getPic());
            bookOut.setTranslate(book.getTranslate());
            bookOut.setIsbn(book.getIsbn());
            booksOut.add(bookOut);
        }

        PageOut pageOut = new PageOut();
        pageOut.setList(booksOut);
        pageOut.setCurrPage(pageIn.getCurrPage());
        pageOut.setTotal(books.size());
        pageOut.setPageSize(pageIn.getPageSize());
        return pageOut;
    }

}
