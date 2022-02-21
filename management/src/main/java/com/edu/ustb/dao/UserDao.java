package com.edu.ustb.dao;

import com.edu.ustb.entities.User;

import java.util.List;

public interface UserDao {
    List<User> showAllUsers();
    int updateUserInfo(User user);

    User findByUsernoAndPassword(String empno, String emppassword);

    User findInfoByEmpno(String empno);

    String findDepnameByDepno(String depno);
    String findEmppositionByEmpposition(String empposno);

    Integer countAllUsers();

    Integer countUsersByDep(String depno);

    int addANewUser(User user);

    int deleteAUser(String empno);

    List<User> findUserByDepno(String depno);

    List<String> getAllEmpno();

    String findEmpposnoByName(String empposname);

    String findDepnoByDepname(String depname);
}
