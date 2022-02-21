package com.edu.ustb.service;

import com.edu.ustb.entities.User;

import java.util.List;

public interface UserService {
    List<User> showAllUsers();
    List<User> findUserByDepno(String depno);
    int updateUserInfo(User user);
    User findInfoByEmpno(String empno);

    User login(User user);
    String findDepnameByDepno(String depno);
    String findEmppositionByEmpposition(String Empposno);

    User userTransfer(User user);

    int countAllUsers();
    int addANewUser(User user);
    int deleteAUser(String empno);


}
