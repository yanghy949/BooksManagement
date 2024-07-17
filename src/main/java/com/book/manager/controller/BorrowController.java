package com.book.manager.controller;

import cn.hutool.core.date.DateUtil;
import com.book.manager.entity.Book;
import com.book.manager.entity.Borrow;
import com.book.manager.service.BookService;
import com.book.manager.service.BorrowService;
import com.book.manager.util.R;
import com.book.manager.util.consts.Constants;
import com.book.manager.util.http.CodeEnum;
import com.book.manager.util.ro.RetBookIn;
import com.book.manager.util.vo.BackOut;
import com.book.manager.util.vo.BookOut;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description 用户管理

 */
@Api(tags = "借阅管理")
@RestController
@RequestMapping("/borrow")
public class BorrowController {

    @Autowired
    private BorrowService borrowService;

    @Autowired
    private BookService bookService;

    @ApiOperation("借阅列表")
    @GetMapping("/list")
    public R getBorrowList(Integer userId) {
        return R.success(CodeEnum.SUCCESS,null);
    }

    @ApiOperation("借阅图书")
    @PostMapping("/add")
    public R addBorrow(@RequestBody Borrow borrow) {
        if(borrow!=null){
            if (borrowService.addBorrow(borrow)){
                return R.success(CodeEnum.SUCCESS,Constants.OK);
            }
            return R.fail(CodeEnum.BOOK_BORROWED);
        }
        return R.fail(CodeEnum.PARAM_ERROR);
    }

    @ApiOperation("编辑借阅")
    @PostMapping("/update")
    public R modifyBorrow(@RequestBody Borrow borrow) {
        return R.success(CodeEnum.SUCCESS,null);
    }


    @ApiOperation("借阅详情")
    @GetMapping("/detail")
    public R borrowDetail(Integer id) {
        return R.success(CodeEnum.SUCCESS,null);
    }

    @ApiOperation("删除归还记录")
    @GetMapping("/delete")
    public R delBorrow(Integer id) {

        return R.success(CodeEnum.SUCCESS);
    }


    @ApiOperation("已借阅列表")
    @GetMapping("/borrowed")
    public R borrowedList(Integer userId) {
        List<BackOut> backOuts = borrowService.findByUserId(userId);
        return R.success(CodeEnum.SUCCESS,backOuts);
    }

    @ApiOperation("归还书籍")
    @PostMapping("/ret")
    public R retBook(Integer userId, Integer bookId) {
        if (userId!=null&&bookId!=null){
            if(borrowService.retBook(userId,bookId)){
                return R.success(CodeEnum.SUCCESS);
            }
            return R.fail(CodeEnum.FAIL);
        }
        return R.fail(CodeEnum.PARAM_ERROR);
    }

}
