package com.edu.ustb.service.impl;

import com.edu.ustb.dao.ArrangeDao;
import com.edu.ustb.dao.UserDao;
import com.edu.ustb.dao.impl.ArrangeDaoImpl;
import com.edu.ustb.dao.impl.UserDaoImpl;
import com.edu.ustb.entities.LeaveApply;
import com.edu.ustb.entities.OverApply;
import com.edu.ustb.entities.TurnoutManager;
import com.edu.ustb.entities.TurnoutManagerFront;
import com.edu.ustb.service.ArrangeService;
import com.edu.ustb.utils.TimeUtils;
import com.sun.javafx.util.TempState;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

public class ArrangeServiceImpl implements ArrangeService {
    private ArrangeDao arrangeDao = new ArrangeDaoImpl();
    private UserDao userDao = new UserDaoImpl();

    @Override
    public List<TurnoutManagerFront> showAllTurnoutToManager() {
        List<TurnoutManager> list = arrangeDao.showAllTurnoutToManager();

        //在service层重新封装
        return TurnoutManagerFront.transferToFront(list);
    }

    @Override
    public List<TurnoutManagerFront> showUsersTurnoutByMonthToManager(String time) {
        List<TurnoutManager> list = arrangeDao.showUsersTurnoutByMonthToManager(time);

        //在service层重新封装
        return TurnoutManagerFront.transferToFront(list);
    }

    @Override
    public List<TurnoutManagerFront> showAUserTurnoutByMonthToManager(String empname, String time) {
        List<TurnoutManager> list = arrangeDao.showAUserTurnoutByMonthToManager(empname, time);

        //在service层重新封装
        return TurnoutManagerFront.transferToFront(list);
    }

    @Override
    public List<TurnoutManagerFront> showAUserTurnoutToManager(String empname) {
        List<TurnoutManager> list = arrangeDao.showAUserAllTurnoutToManager(empname);

        //在service层重新封装
        return TurnoutManagerFront.transferToFront(list);
    }

    @Override
    public int addNewArrangement(TurnoutManager turnoutManager) {
        //检查是否请假
        String empno = turnoutManager.getEmpno();
        Timestamp start = turnoutManager.getStart();
        Timestamp end = turnoutManager.getEnd();
        boolean isLeave = checkIsLeave(empno, start.toString(), end.toString());
        if (!isLeave) {
            turnoutManager.setChecktypename("30");//新增排班肯定是没打卡的，所以直接设置
            return arrangeDao.addNewArrangement(turnoutManager);
        } else {
            return 0;
        }
    }

    @Override
    public List<TurnoutManagerFront> showAllTurnoutToInte(String depno) {
        List<TurnoutManager> list = arrangeDao.showAllTurnoutToInte(depno);

        //在service层重新封装
        return TurnoutManagerFront.transferToFront(list);
    }

    @Override
    public List<TurnoutManagerFront> showUsersTurnoutByMonthToInte(String depno, String time) {
        List<TurnoutManager> list = arrangeDao.showUsersTurnoutByMonthToInte(depno, time);

        //在service层重新封装
        return TurnoutManagerFront.transferToFront(list);
    }

    @Override
    public List<TurnoutManagerFront> showAUserTurnoutByMonthToInte(String depno, String empname, String time) {
        List<TurnoutManager> list = arrangeDao.showAUserTurnoutByMonthToInte(depno, empname, time);

        //在service层重新封装
        return TurnoutManagerFront.transferToFront(list);
    }

    @Override
    public List<TurnoutManagerFront> showAUserTurnoutToInte(String depno, String empname) {
        List<TurnoutManager> list = arrangeDao.showAUserAllTurnoutToInte(depno, empname);

        //在service层重新封装
        return TurnoutManagerFront.transferToFront(list);
    }

    @Override
    public List<LeaveApply> showLeaveApplyByDep(String depno) {
        return arrangeDao.showLeaveApplyByDep(depno);
    }

    @Override
    public List<TurnoutManager> agreeLeaveApply(int leaveno) {
        //先获取批准了的请假申请信息（主要是为了获取时间， 查询请假期间内的排班用）
        LeaveApply leaveApply = arrangeDao.showAUserLeaveApply(leaveno);

        //再将leaveapply表内将相应的请假申请的isapply置为1（表示批准）
        int flag1 = arrangeDao.updateIsApplyLeave(leaveno, 1);


        //获取请假的起始时间
        String start = leaveApply.getStart().toString();
        String end = leaveApply.getEnd().toString();
        String empno = leaveApply.getEmpno();

        //获取受影响的，在请假时间内的该用户的排班
        List<TurnoutManager> influencedList = arrangeDao.showAUserTurnoutBetween(empno, start, end);

        //修改排班表arrangement中的isleava为1，表示该排班已经请假了
        int flag2 = arrangeDao.updateIsLeaveArrange(empno, start, end);

        return influencedList;
    }

    @Override
    public int disagreeLeaveApply(int leaveno) {
        //再将leaveapply表内将相应的请假申请的isapply置为2（表示驳回）
        int flag1 = arrangeDao.updateIsApplyLeave(leaveno, 2);
        return flag1;
    }

    @Override
    public int deleteArrByArrno(int arrno) {
        return arrangeDao.deleteArrByArrno(arrno);
    }

    @Override
    public int updateArrByArrno(TurnoutManager turnoutManager) {
        return arrangeDao.updateArrByArrno(turnoutManager);
    }

    @Override
    public int addAffectedArr(int arrno) {
        return arrangeDao.addAffectedArr(arrno);
    }

    @Override
    public int deleteAllAffectedArr() {
        return arrangeDao.deleteAllAffectedArr();
    }

    @Override
    public List<Integer> showAllAffectedArr() {
        return arrangeDao.showAllAffectedArr();
    }

    @Override
    public TurnoutManagerFront showArrByArrno(int arrno) {
        TurnoutManager turnoutManager = arrangeDao.showArrByArrno(arrno);
        TurnoutManagerFront turnoutManagerFront = new TurnoutManagerFront(turnoutManager);
        return turnoutManagerFront;
    }

    @Override
    public int deleteAffectedArrByArrno(int arrno) {
        return arrangeDao.deleteAffectedArrByArrno(arrno);
    }

    @Override
    public List<LeaveApply> showBackApplyByDep(String depno) {
        return arrangeDao.showBackApplyByDep(depno);
    }

    @Override
    public int agreeBackApply(int leaveno) {
        //将leaveapply表内将相应的请假申请的isapply置为4（表示已销假）
        return arrangeDao.updateIsApplyLeave(leaveno, 4);
    }

    @Override
    public List<OverApply> showOverApplyByDep(String depno) {
        return arrangeDao.showOverApplyByDep(depno);
    }

    @Override
    public int agreeOverApply(int overno) {
        int flag = 1;
        int i;
        // 1、将加班表中的isapply属性改成1，表示已经批准（0表示未批准，2表示拒绝批准）
        i = arrangeDao.updateIsApplyOver(overno, 1);
        if (i == 0) return 0;

        // 2、根据overno取出相应的加班申请信息并封装
        OverApply overApply = arrangeDao.showOverApplyByOverno(overno);
        String empno = overApply.getEmpno();
        Timestamp start = overApply.getStart();
        Timestamp end = overApply.getEnd();
        boolean isover = true; // 员工自己申请的肯定是加班，但不是临时加班（临时加班只有经理可以）
        boolean istemporary = false;
        // 封装
        TurnoutManager turnoutManager = new TurnoutManager();
        turnoutManager.setEmpno(empno);
        turnoutManager.setStart(start);
        turnoutManager.setEnd(end);
        turnoutManager.setIsover(isover);
        turnoutManager.setIstemporary(istemporary);

        // 3、到arrangement表中增加相应的排班，置isover为1，表示是加班，istemporary为0，表示非临时加班
        int i1 = this.addNewArrangement(turnoutManager);
        if (i1 == 0) return 0;
        else return 1;
    }

    @Override
    public int disagreeOverApply(int overno) {
        return arrangeDao.updateIsApplyOver(overno, 2);
    }

    @Override
    public List<LeaveApply> showLeaveApplyEmpno(String empno) {
        return arrangeDao.showLeaveApplyEmpno(empno);
    }

    @Override
    public int addNewLeaveApply(LeaveApply leaveApply) {
        return arrangeDao.addNewLeaveApply(leaveApply);
    }

    @Override
    public int deleteLeaveApplyByLeaveno(int leaveno) {
        return arrangeDao.deleteLeaveApplyByLeaveno(leaveno);
    }

    @Override
    public int backLeaveByLeaveno(int leaveno) {
        // isapply的值为3的时候表示申请销假
        return arrangeDao.updateIsApplyLeave(leaveno, 3);
    }

    @Override
    public List<OverApply> showOverApplyByEmpno(String empno) {
        return arrangeDao.showOverApplyByEmpno(empno);
    }

    @Override
    public int deleteOverApplyByOverno(int overno) {
        return arrangeDao.deleteOverApplyByOverno(overno);
    }

    @Override
    public int addNewOverApply(OverApply overApply) {
        return arrangeDao.addNewOverApply(overApply);
    }

    @Override
    public List<TurnoutManagerFront> showUserArrADay(String empno, String currentT) {
        String start = TimeUtils.getStartOfDay(currentT);
        String end = TimeUtils.getEndOfDay(currentT);
        List<TurnoutManager> list = arrangeDao.showAUserTurnoutBetween(empno, start, end);
        return TurnoutManagerFront.transferToFront(list);
    }

    @Override
    public int addTempArr(String empstart, String empend) {
        int i = 0;
        List<String> empnoList = userDao.getAllEmpno();
        for (String empno : empnoList) {
            //判断经历申请的加班期间是否有请假的，没有请假的才可以安排
            boolean isLeave = checkIsLeave(empno, empstart, empend);
            if (!isLeave) {
                TurnoutManager t = new TurnoutManager();
                t.setEmpno(empno);
                t.setStart(Timestamp.valueOf(empstart));
                t.setEnd(Timestamp.valueOf(empend));
                t.setIsover(false);
                t.setIstemporary(true);
                i = arrangeDao.addNewArrangement(t);
            }
        }
        return i;
    }

    @Override
    public int countUnapprovedByDep(String depno) {
        int i = arrangeDao.countUnapprovedLeaveByDep(depno);
        int j = arrangeDao.countUnapprovedOverByDep(depno);
        return i + j;
    }

    private boolean checkIsLeave(String empno, String empstart, String empend) {
        //所有被同意的请假
        List<LeaveApply> leaveList = arrangeDao.showApprovedLeaveByEmpno(empno);
        for (LeaveApply leaveApply : leaveList) {
            Timestamp Tempstart = Timestamp.valueOf(empstart);
            Timestamp Tempend = Timestamp.valueOf(empend);
            Timestamp start = leaveApply.getStart();
            Timestamp end = leaveApply.getEnd();

            boolean flag1 = Tempstart.before(start) && start.before(Tempend);
            boolean flag2 = start.before(Tempstart) && Tempend.before(end);
            boolean flag3 = Tempstart.before(end) && end.before(Tempend);
            if (flag1 || flag2 || flag3) {
                return true;
            }
        }
        return false;
    }

}
