package com.book.manager.service;

import cn.hutool.core.date.DateUtil;
import com.book.manager.dao.BorrowMapper;
import com.book.manager.entity.Book;
import com.book.manager.entity.Borrow;
import com.book.manager.repos.BorrowRepository;
import com.book.manager.util.consts.Constants;
import com.book.manager.util.vo.BackOut;
import com.book.manager.util.vo.BookOut;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description 借阅管理
 */
@Service
public class BorrowService {

    @Autowired
    private BorrowRepository borrowRepository;

    @Autowired
    private BorrowMapper borrowMapper;

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    /**
     * 添加
     * （添加事物）
     */
    @Transactional
    public boolean addBorrow(Borrow borrow) {
//        if(findBorrowByUserIdAndBookId(borrow.getUserId(), borrow.getBookId()).getId()!=null){
//            return false;
//        }
        borrow.setRet(1);
        return borrowMapper.addBorrow(borrow) > 0;
    }

    /**
     * user id查询所有借阅信息
     */
    public List<Borrow> findAllBorrowByUserId(Integer userId) {
        return null;
    }

    /**
     * user id查询所有 已借阅信息
     */
    public List<Borrow> findBorrowsByUserIdAndRet(Integer userId, Integer ret) {
        return null;
    }


    /**
     * 详情
     */
    public Borrow findById(Integer id) {

        return null;
    }

    /**
     * 编辑
     */
    public boolean updateBorrow(Borrow borrow) {
        return false;
    }


    /**
     * 编辑
     */
    public Borrow updateBorrowByRepo(Borrow borrow) {
        return null;
    }

    /**
     * s删除
     */
    public void deleteBorrow(Integer id) {

    }

    /**
     * 查询用户某一条借阅信息
     *
     * @param userId 用户id
     * @param bookId 图书id
     */
    public Borrow findBorrowByUserIdAndBookId(Integer userId, Integer bookId) {
        return borrowMapper.findBorrowByUserIdAndBookId(userId, bookId);
    }

    /**
     * 归还书籍, 使用事务保证 ACID
     *
     * @param userId 用户Id
     * @param bookId 书籍id
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean retBook(int userId, int bookId) {
        return borrowMapper.retBook(userId, bookId)>0;
    }

    public List<BackOut> findByUserId(Integer userId) {
        List<Borrow> borrows = borrowMapper.findByUserId(userId);
        List<BackOut> backOuts = new ArrayList<>();
        for (Borrow borrow : borrows) {
            if (borrow.getRet()==0){
                continue;
            }
            BackOut backOut = new BackOut();
            backOut.setBorrowTime(DateUtil.format(borrow.getCreateTime(), Constants.DATE_FORMAT));
            backOut.setEndTime(DateUtil.format(borrow.getEndTime(), Constants.DATE_FORMAT));
            backOut.setLate(borrow.getEndTime().before(new Date()) ? Constants.YES_STR : Constants.NO_STR);
            System.out.println(borrow.getEndTime()+"  "+new Date());
            BeanUtils.copyProperties(borrow, backOut);
            BookOut book = bookService.findBookById(borrow.getBookId());
            BeanUtils.copyProperties(book, backOut);
            backOuts.add(backOut);
        }
        return backOuts;
    }
}
