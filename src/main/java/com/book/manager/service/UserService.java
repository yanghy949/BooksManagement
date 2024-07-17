package com.book.manager.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.book.manager.dao.UsersMapper;
import com.book.manager.entity.Users;
import com.book.manager.repos.UsersRepository;
import com.book.manager.util.consts.Constants;
import com.book.manager.util.ro.PageIn;
import com.book.manager.util.vo.PageOut;
import com.book.manager.util.vo.UserOut;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @Description 用户业务类
 */
@Service
public class UserService implements UserDetailsService{

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;


//    public Page<Users> getUsers(String keyword,Pageable pageable) {
//        return null;
//    }

    private String ident(Integer ident){
        switch (ident){
            case 0:
                return "学生";
            case 1:
                return "教师";
            case 2:
                return "校外人士";
            case 3:
                return "管理员";
        }
        return ident.toString();
    }
    /**
     * 登录 (使用SpringSecurity 此方法弃用)
     * @param username 用户名
     * @param password 密码
     */
    public Users login(String username,String password) {
       return null;
    }


    public boolean addUser(Users users) {
        return usersMapper.addUser(users)>0;
    }

    /**
     * 编辑用户
     * @param users 用户对象
     * @return true or false
     */
//    @Transactional(rollbackFor = Exception.ass)
    public boolean updateUser(Users users) {
        return usersMapper.updateUsers(users)>0;
    }

    /**
     * 用户详情
     * @param id 主键
     * @return 用户详情
     */
    public Users findUserById(Integer id) {

        return usersMapper.findUserById(id);
    }

    /**
     * 删除用户
     * @param id 主键
     * @return true or false
     */
    public boolean deleteUser(Integer id) {
        return usersMapper.delUser(id)>0;
    }


    /**
     * 用户搜索查询(mybatis 分页)
     * @param pageIn
     * @return
     */
    public PageInfo<Users> getUserList(PageIn pageIn) {


        return null;
    }

    /**
     * 用户鉴权
     * @param username 用户名
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 查找用户
        Users user = usersRepository.findByUsername(username);
        // 获得角色
        String role = String.valueOf(user.getIsAdmin());
        // 角色集合
        List<GrantedAuthority> authorities = new ArrayList<>();
        System.out.println(authorities);
        // 角色必须以`ROLE_`开头，数据库中没有，则在这里加
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        // 数据库密码是明文, 需要加密进行比对
        return new User(user.getUsername(), passwordEncoder.encode(user.getPassword()), authorities);

    }


    /**
     * 用户名查询用户信息
     * @param username 用户名
     */
    public Users findByUsername(String username) {
        return usersMapper.findUser(username);
    }
    public PageOut getUsers(PageIn pageIn) {
        List<Users> users = usersMapper.findListByLike(pageIn.getKeyword());
        PageOut pageOut = new PageOut();

        List<UserOut> userOuts = new ArrayList<>();
        for (Users user : users) {
            UserOut userOut = new UserOut();
            userOut.setId(user.getId());
            userOut.setIdent(user.getIdentity().toString());
            userOut.setBirth(DateUtil.format(user.getBirthday(), Constants.DATE_FORMAT));
            userOut.setAvatar(user.getAvatar());
            userOut.setSize(user.getSize());
            userOut.setIsAdmin(user.getIsAdmin());
            userOut.setEmail(user.getEmail());
            userOut.setNickname(user.getNickname());
            userOut.setTel(user.getTel());
            userOut.setAddress(user.getAddress());
            userOut.setUsername(user.getUsername());
            userOuts.add(userOut);
            userOut.setIdent(ident(user.getIdentity()));
        }

        pageOut.setList(userOuts);
        pageOut.setTotal(users.size());
        pageOut.setPageSize(pageIn.getPageSize());
        pageOut.setCurrPage(pageIn.getCurrPage());
        return pageOut;
    }
}
