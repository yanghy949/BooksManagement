package com.book.manager.controller;

import com.book.manager.entity.Book;
import com.book.manager.service.BookService;
import com.book.manager.util.R;
import com.book.manager.util.http.CodeEnum;
import com.book.manager.util.ro.PageIn;
import com.book.manager.util.vo.BookOut;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @Description 用户管理
 */
@Api(tags = "图书管理")
@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @ApiOperation("图书搜索列表")
    @PostMapping("/list")
    public R getBookList(@RequestBody PageIn pageIn) {

        return R.success(CodeEnum.SUCCESS, bookService.getBookList(pageIn));
    }

    /**
     * 添加图片操作
     *
     * @param book
     * @return
     */
    @ApiOperation("添加图书")
    @ResponseBody
    @PostMapping("/add")
    public R addBook(Book book) {
        if(book.getName()!=null&&book.getIsbn()!=null
            &&book.getAuthor()!=null&&book.getPrice()!=null
            &&book.getPublish()!=null&&book.getPublishTime()!=null
            &&book.getType()!=null&&book.getPages()!=null
            &&book.getSize()!=null&&book.getTranslate()!=null
            &&book.getPic()!=null
        ){
            if (bookService.addBook(book)) {
                return R.success(CodeEnum.SUCCESS);
            }else {
                return R.fail(CodeEnum.FAIL);
            }
        }else {
            return R.success(CodeEnum.SUCCESS);
        }
    }

    /**
     * 编辑图书
     *
     * @param book
     * @return
     */
    @ApiOperation("编辑图书")
    @ResponseBody
    @PostMapping("/edit")
    public R editBook(Book book) {
        if (book != null && book.getId() != null) {
            if (bookService.updateBook(book)) {
                return R.success(CodeEnum.SUCCESS);
            } else {
                return R.fail(CodeEnum.FAIL);
            }
        } else {
            return R.fail(CodeEnum.PARAM_ERROR);
        }
    }


    @ApiOperation("图书详情")
    @GetMapping("/detail")
    public R bookDetail(Integer id) {
        if (id != null) {
            BookOut book = bookService.findBookById(id);
            return R.success(CodeEnum.SUCCESS, book);
        } else {
            return R.fail(CodeEnum.PARAM_ERROR);
        }
    }

    @ApiOperation("图书详情 根据ISBN获取")
    @GetMapping("/detailByIsbn")
    public R bookDetailByIsbn(String isbn) {

        return R.success(CodeEnum.SUCCESS, null);
    }

    @ApiOperation("删除图书")
    @GetMapping("/delete")
    public R delBook(Integer id) {
        if (id != null) {
            if (bookService.deleteBook(id)) {
                return R.success(CodeEnum.SUCCESS);
            } else {
                return R.fail(CodeEnum.FAIL);
            }
        } else {
            return R.fail(CodeEnum.PARAM_ERROR);
        }
    }

}
