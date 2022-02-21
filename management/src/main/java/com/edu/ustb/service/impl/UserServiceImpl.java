package com.edu.ustb.service.impl;

import com.edu.ustb.dao.UserDao;
import com.edu.ustb.dao.impl.UserDaoImpl;
import com.edu.ustb.entities.User;
import com.edu.ustb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {
    //UserDao userDao = new UserDaoImpl();
//    @Autowired //自动按照类型注入
//    @Qualifier("userDao1") //要和Autowired同时使用
    //@Resource(name="userDao")
    //UserDao userDao = null;
    UserDao userDao = new UserDaoImpl();

    @Override
    public List<User> showAllUsers() {
        return userDao.showAllUsers();
    }

    @Override
    public List<User> findUserByDepno(String depno) {
        List<User> list = userDao.findUserByDepno(depno);
        List<User> newList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            User user = list.get(i);
            user = this.userTransfer(user);
            newList.add(i, user);
        }
        return newList;
    }

    @Override
    public int updateUserInfo(User user) {
        return userDao.updateUserInfo(user);
    }

    @Override
    public User findInfoByEmpno(String empno) {
        return userDao.findInfoByEmpno(empno);
    }

    @Override
    public User login(User user) {
        return userDao.findByUsernoAndPassword(user.getEmpno(), user.getEmppassword());
    }

    @Override
    public String findDepnameByDepno(String depno) {
        return userDao.findDepnameByDepno(depno);
    }

    @Override
    public String findEmppositionByEmpposition(String empposno) {
        return userDao.findEmppositionByEmpposition(empposno);
    }

    /**
     * 创建一个新的user对象,把职位和所属部门从编号转换成文字（原来是编号，不方便显示）
     */
    @Override
    public User userTransfer(User user) {
        User newUser = this.findInfoByEmpno(user.getEmpno());
        String depnameByDepno = this.findDepnameByDepno(user.getDepno());
        String emppositionByEmpposition = this.findEmppositionByEmpposition(user.getEmpposno());
        newUser.setDepno(depnameByDepno);
        newUser.setEmpposno(emppositionByEmpposition);
        return newUser;
    }

    @Override
    public int countAllUsers() {
        return userDao.countAllUsers();
    }

    @Override
    public int addANewUser(User user) {
        return userDao.addANewUser(user);
    }

    @Override
    public int deleteAUser(String empno) {
        return userDao.deleteAUser(empno);
    }
}
