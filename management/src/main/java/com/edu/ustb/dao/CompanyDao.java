package com.edu.ustb.dao;

import com.edu.ustb.entities.Company;
import com.edu.ustb.entities.DepartmentInfo;

import java.util.ArrayList;
import java.util.List;

public interface CompanyDao {
    List<Company> showCmpInfo();

    //检索部门信息: 部门名称，主管，部门电话 (员工数量用UserDao里的countUsersByDep方法)
    List<DepartmentInfo> showDepartmentInfo(String depno);
}
