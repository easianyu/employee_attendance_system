package com.edu.ustb.dao.impl;

import com.edu.ustb.dao.CompanyDao;
import com.edu.ustb.entities.Company;
import com.edu.ustb.entities.DepartmentInfo;
import com.edu.ustb.entities.TurnoutManager;
import com.edu.ustb.utils.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class CompanyDaoImpl implements CompanyDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public List<Company> showCmpInfo() {
        List<Company> list = null;
        String sql = "SELECT * FROM company";
        try {
            list = template.query(sql, new BeanPropertyRowMapper<Company>(Company.class));
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    @Override
    public List<DepartmentInfo> showDepartmentInfo(String depno) {
        List<DepartmentInfo> list = new ArrayList<>();
        String sql = "SELECT  d.depname, u.empname, d.depphone  FROM users u, department d " +
                "WHERE u.depno=d.depno AND u.depno=? AND empposno='20'";
        try {
            list = template.query(sql, new BeanPropertyRowMapper<DepartmentInfo>(DepartmentInfo.class), depno);
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }
}
