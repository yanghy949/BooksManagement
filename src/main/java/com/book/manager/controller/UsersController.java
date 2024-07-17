package com.book.manager.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.book.manager.entity.Users;
import com.book.manager.service.UserService;
import com.book.manager.util.R;
import com.book.manager.util.consts.Constants;
import com.book.manager.util.consts.ConvertUtil;
import com.book.manager.util.http.CodeEnum;
import com.book.manager.util.vo.PageOut;
import com.book.manager.util.ro.PageIn;
import com.book.manager.util.vo.UserOut;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description 用户管理

 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("/user")
public class UsersController {

    @Autowired
    private UserService userService;
    @Autowired
    private HttpSession httpSession;

    @ApiOperation("用户列表")
    @PostMapping("/list")
    public R getUsers(@RequestBody PageIn pageIn) {

        return R.success(CodeEnum.SUCCESS,userService.getUsers(pageIn));
    }


    /**
     * 添加读者操作
     * @param users
     * @return
     */
    @ApiOperation("添加读者")
    @ResponseBody
    @PostMapping("/addReader")
    public R addReader(Users users) {
        if (users != null){
            if (userService.addUser(users)){
                return R.success(CodeEnum.SUCCESS);
            }
            return R.fail(CodeEnum.FAIL);
        }
        return R.fail(CodeEnum.PARAM_ERROR);
    }


    /**
     * 编辑用户操作
     * @param users
     * @return
     */
    @ApiOperation("编辑用户")
    @ResponseBody
    @PostMapping("/edit")
    public R modifyUsers(Users users) {
        if (users != null){
            if (userService.updateUser(users)){
                return R.success(CodeEnum.SUCCESS);
            }
            return R.fail(CodeEnum.FAIL);
        }
        return R.success(CodeEnum.PARAM_ERROR);
    }


    @ApiOperation("用户详情")
    @GetMapping("/detail")
    public R userDetail(Integer id) {
        if (id != null){
            return R.success(CodeEnum.SUCCESS,userService.findUserById(id));
        }
        return R.fail(CodeEnum.PARAM_ERROR);
    }

    @ApiOperation("删除用户")
    @GetMapping("/delete")
    public R delUsers(Integer id) {
        if (id != null){
            if(userService.deleteUser(id)){
                return R.success(CodeEnum.SUCCESS);
            }
            return R.fail(CodeEnum.FAIL);
        }

        return R.fail(CodeEnum.PARAM_ERROR);
    }

    @ApiOperation("获取当前用户登陆信息")
    @GetMapping(value = "/currUser")
    public R getCurrUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal!=null) {
            Map<String,Object> map = BeanUtil.beanToMap(principal);
            String username = (String) map.get("username");
            if (StrUtil.isNotBlank(username)) {
                Users users = userService.findByUsername(username);
                UserOut out = new UserOut();
                BeanUtils.copyProperties(users,out);
                out.setBirth(DateUtil.format(users.getBirthday(),Constants.DATE_FORMAT));
                Integer identity = users.getIdentity();
                String ident = "";
                if (identity == Constants.STUDENT) {
                    ident = Constants.STU_STR;
                }else if (identity == Constants.TEACHER) {
                    ident = Constants.TEA_STR;
                }else if (identity == Constants.OTHER) {
                    ident = Constants.OTHER_STR;
                }else if (identity == Constants.ADMIN) {
                    ident = Constants.ADMIN_STR;
                }
                out.setIdent(ident);
                return R.success(CodeEnum.SUCCESS,out);
            }
        }
        return R.fail(CodeEnum.USER_NOT_FOUND);

    }
}
