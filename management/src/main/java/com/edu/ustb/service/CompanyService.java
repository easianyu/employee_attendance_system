package com.edu.ustb.service;

import com.edu.ustb.entities.Company;
import com.edu.ustb.entities.DepartmentInfo;

import java.util.ArrayList;
import java.util.List;

public interface CompanyService {
    List<Company> showCmpInfo();

    ArrayList<DepartmentInfo> showDepartmentInfo(String depno);


    int userCheckinByImg(int arrno, String checkfor, String recordTime);
}
