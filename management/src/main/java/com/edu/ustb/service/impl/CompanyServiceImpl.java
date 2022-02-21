package com.edu.ustb.service.impl;

import com.edu.ustb.dao.ArrangeDao;
import com.edu.ustb.dao.CompanyDao;
import com.edu.ustb.dao.UserDao;
import com.edu.ustb.dao.impl.ArrangeDaoImpl;
import com.edu.ustb.dao.impl.CompanyDaoImpl;
import com.edu.ustb.dao.impl.UserDaoImpl;
import com.edu.ustb.entities.Company;
import com.edu.ustb.entities.DepartmentInfo;
import com.edu.ustb.entities.TurnoutManager;
import com.edu.ustb.service.CompanyService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CompanyServiceImpl implements CompanyService {
    private CompanyDao companyDao = new CompanyDaoImpl();
    private UserDao userDao = new UserDaoImpl();
    private ArrangeDao arrangeDao = new ArrangeDaoImpl();

    @Override
    public List<Company> showCmpInfo() {
        return companyDao.showCmpInfo();
    }

    @Override
    public ArrayList<DepartmentInfo> showDepartmentInfo(String depno) {
        List<DepartmentInfo> list = companyDao.showDepartmentInfo(depno);
        //上面取出来的值没有放入该部门包括主管在内的员工总数，创建一个ArrayList另存
        ArrayList<DepartmentInfo> newList = new ArrayList<>();

        //获取部门员工总人数
        Integer totalUsers = userDao.countUsersByDep(depno);
        for (DepartmentInfo d : list) { //遍历原list集合
            d.setToltalusers(totalUsers);
            newList.add(d);
        }
        return newList;
    }

    @Override
    public int userCheckinByImg(int arrno, String checkfor, String recordTime) {

        //先根据排班号获取该项排班应该的上班下班间然后根据改时间判断打卡的时候是否迟到早退
        TurnoutManager t = arrangeDao.showArrByArrno(arrno);
        Timestamp start = t.getStart();
        Timestamp end = t.getEnd();
        Timestamp TrecordTime = Timestamp.valueOf(recordTime);

        //目前没有设置更多的打卡方式，先默认为人脸识别打卡
        int i = arrangeDao.updateChecktypeByarrno(arrno, "20");
        int j;
        if ("0".equals(checkfor)) { // 上班打卡，更新上班时间
            boolean islate = start.before(TrecordTime);
            j = arrangeDao.updateRlstartByarrno(arrno, recordTime, islate);
        } else { //下班打卡，更新下班时间
            boolean isearly = TrecordTime.before(end);
            j = arrangeDao.updateRlendByarrno(arrno, recordTime, isearly);
        }
        return (i == 1 && j == 1) ? 1 : 0;
    }
}
