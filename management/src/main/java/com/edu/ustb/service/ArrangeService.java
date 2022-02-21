package com.edu.ustb.service;

import com.edu.ustb.entities.LeaveApply;
import com.edu.ustb.entities.OverApply;
import com.edu.ustb.entities.TurnoutManager;
import com.edu.ustb.entities.TurnoutManagerFront;

import java.util.List;

public interface ArrangeService {
    List<TurnoutManagerFront> showAllTurnoutToManager();
    List<TurnoutManagerFront> showUsersTurnoutByMonthToManager(String time);
    List<TurnoutManagerFront> showAUserTurnoutByMonthToManager(String empname, String time);
    List<TurnoutManagerFront> showAUserTurnoutToManager(String empname);
    int addNewArrangement(TurnoutManager turnoutManager);

    ////主管查自己部门的排班信息，实体类依旧用和经理一样的，只不过查的时候只查"所在的部门"
    List<TurnoutManagerFront> showAllTurnoutToInte(String depno);
    List<TurnoutManagerFront> showUsersTurnoutByMonthToInte(String depno, String time);
    List<TurnoutManagerFront> showAUserTurnoutByMonthToInte(String depno, String empname, String time);
    List<TurnoutManagerFront> showAUserTurnoutToInte(String depno, String empname);

    //主管获取没有被审批的请假
    List<LeaveApply> showLeaveApplyByDep(String depno);

    //同意请假后，改变请假申请表和排班表中受影响班次的状态为已请假，返回该受影响的班次
    List<TurnoutManager> agreeLeaveApply(int leaveno);

    int disagreeLeaveApply(int leaveno);

    int deleteArrByArrno(int arrno);

    int updateArrByArrno(TurnoutManager turnoutManager);

    int addAffectedArr(int arrno);

    int deleteAllAffectedArr();

    List<Integer> showAllAffectedArr();

    TurnoutManagerFront showArrByArrno(int arrno);

    int deleteAffectedArrByArrno(int arrno);

    List<LeaveApply> showBackApplyByDep(String depno);

    int agreeBackApply(int leaveno);

    List<OverApply> showOverApplyByDep(String depno);

    int agreeOverApply(int arrno);

    int disagreeOverApply(int overno);

    List<LeaveApply> showLeaveApplyEmpno(String empno);

    int addNewLeaveApply(LeaveApply leaveApply);

    int deleteLeaveApplyByLeaveno(int leaveno);

    int backLeaveByLeaveno(int leaveno);

    List<OverApply> showOverApplyByEmpno(String empno);

    int deleteOverApplyByOverno(int overno);

    int addNewOverApply(OverApply overApply);

    List<TurnoutManagerFront> showUserArrADay(String empno, String currentT);

    int addTempArr(String empstart, String empend);

    int countUnapprovedByDep(String depno);
}
