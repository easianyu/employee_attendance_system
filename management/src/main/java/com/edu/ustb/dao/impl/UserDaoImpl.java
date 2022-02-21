package com.edu.ustb.dao.impl;

import com.edu.ustb.dao.UserDao;
import com.edu.ustb.entities.User;
import com.edu.ustb.utils.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository("userDao")
public class UserDaoImpl implements UserDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public List<User> showAllUsers() {
        List<User> list = null;
        String sql = "SELECT * FROM users";
        try {
            list = template.query(sql, new BeanPropertyRowMapper<User>(User.class));
        } catch (Exception e) {

        }
        return list;
    }

    @Override
    public int updateUserInfo(User user) {
        int num = 0;
        String sql = "update users set empname=?, emppassword=?,empposno=?,  " +
                "empemail=? , empphoneno=?, depno=? where empno=?";
        try {
            num = template.update(sql, user.getEmpname(),
                    user.getEmppassword(),
                    user.getEmpposno(),
                    user.getEmpemail(),
                    user.getEmpphoneno(),
                    user.getDepno(),
                    user.getEmpno());

        } catch (Exception e) {

        }
        return num;
    }

    @Override
    public User findByUsernoAndPassword(String empno, String emppassword) {
        User user = null;
        String sql = "SELECT * FROM users WHERE empno = ? and emppassword = ?";
        try {
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), empno, emppassword);

        } catch (Exception e) {
            System.out.println(e);
        }
        return user;
    }

    @Override
    public User findInfoByEmpno(String empno) {
        User user = null;
        String sql = "SELECT * FROM users WHERE empno = ?";
        try {
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), empno);
        } catch (Exception e) {

        }
        return user;
    }

    @Override
    public String findDepnameByDepno(String depno) {
        String posName = null;
        String sql = "SELECT depname FROM department WHERE depno=?";
        try {
            posName = template.queryForObject(sql, String.class, depno);
        } catch (Exception e) {

        }
        return posName;
    }

    @Override
    public String findEmppositionByEmpposition(String empposno) {
        String empPosition = null;
        String sql = "SELECT empposition FROM empposition WHERE empposno=?";
        try {
            empPosition = template.queryForObject(sql, String.class, empposno);
        } catch (Exception e) {
            System.out.println(e);
        }
        return empPosition;
    }

    @Override
    public Integer countAllUsers() {
        String sql = "SELECT COUNT(*) FROM users";
        Integer sum = 0;
        sum = template.queryForObject(sql, Integer.class);
        return sum;
    }

    @Override
    public int addANewUser(User user) {
        int num = 0;
        String sql = "INSERT INTO users(empno, empname, emppassword, empposno, empemail, empphoneno, depno) " +
                " VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            num = template.update(sql, user.getEmpno(),
                    user.getEmpname(),
                    user.getEmppassword(),
                    user.getEmpposno(),
                    user.getEmpemail(),
                    user.getEmpphoneno(),
                    user.getDepno());
        } catch (Exception e) {

        }
        return num;
    }

    @Override
    public int deleteAUser(String empno) {
        int num = 0;
        String sql = "delete from users where empno=?";
        try {
            num = template.update(sql, empno);
        } catch (Exception e) {

        }
        return num;
    }

    @Override
    public List<User> findUserByDepno(String depno) {
        List<User> list = null;
        String sql = "SELECT * FROM users WHERE depno=?";
        try {
            list = template.query(sql, new BeanPropertyRowMapper<User>(User.class), depno);
        } catch (Exception e) {

        }
        return list;
    }

    @Override
    public List<String> getAllEmpno() {
        List<String> list = null;
        String sql = "SELECT empno FROM users";
        try {
            list = template.queryForList(sql, String.class);
        } catch (Exception e) {

        }
        return list;
    }

    @Override
    public String findEmpposnoByName(String empposname) {
        String empposno = null;
        String sql = "SELECT empposno FROM empposition WHERE empposition=?";
        try {
            empposno = template.queryForObject(sql, String.class, empposname);
        } catch (Exception e) {
            System.out.println(e);
        }
        return empposno;
    }

    @Override
    public String findDepnoByDepname(String depname) {
        String depno = null;
        String sql = "SELECT depno FROM department WHERE depname=?";
        try {
            depno = template.queryForObject(sql, String.class, depname);
        } catch (Exception e) {
            System.out.println(e);
        }
        return depno;
    }

    @Override
    public Integer countUsersByDep(String depno) {
        String sql = "SELECT COUNT(*) FROM users WHERE depno=?";
        Integer sum = 0;
        sum = template.queryForObject(sql, Integer.class, depno);
        return sum;
    }
}
