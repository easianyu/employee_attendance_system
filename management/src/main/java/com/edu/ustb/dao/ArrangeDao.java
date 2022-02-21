package com.edu.ustb.dao;

import com.edu.ustb.entities.*;

import java.sql.Timestamp;
import java.util.List;

public interface ArrangeDao {
    List<TurnoutManager> showAllTurnoutToManager();
    List<TurnoutManager> showUsersTurnoutByMonthToManager(String time);
    List<TurnoutManager> showAUserTurnoutByMonthToManager(String empno, String time);
    List<TurnoutManager> showAUserAllTurnoutToManager(String empno);
    List<Arrangement> showAll();

    //新增排班
    int addNewArrangement(TurnoutManager turnoutManager);

    //主管查自己部门的排班信息，实体类依旧用和经理一样的，只不过查的时候只查"所在的部门"
    List<TurnoutManager> showAllTurnoutToInte(String depno);
    List<TurnoutManager> showUsersTurnoutByMonthToInte(String depno, String time);
    List<TurnoutManager> showAUserTurnoutByMonthToInte(String depno, String empno, String time);
    List<TurnoutManager> showAUserAllTurnoutToInte(String depno, String empno);

    //主管获取没有被审批的请假
    List<LeaveApply> showLeaveApplyByDep(String depno);

    //更新arrangement表中的某人的某个时间段排班的isleave为1， 表示该段排班请假了
    int updateIsLeaveArrange(String empno, String start, String end);

    //更新leaveapply表中的某条请假申请：0 未批准；1 被批准；2 被驳回
    int updateIsApplyLeave(int leaveno, int isApply);

    //获取某人请假的信息
    LeaveApply showAUserLeaveApply(int leaveno);


    int deleteArrByArrno(int arrno);

    int updateArrByArrno(TurnoutManager turnoutManager);

    List<TurnoutManager> showAUserTurnoutBetween(String empno, String start, String end);

    int addAffectedArr(int arrno);

    int deleteAllAffectedArr();

    List<Integer> showAllAffectedArr();

    TurnoutManager showArrByArrno(int arrno);

    int deleteAffectedArrByArrno(int arrno);

    List<LeaveApply> showBackApplyByDep(String depno);

    List<OverApply> showOverApplyByDep(String depno);

    //更新overapply表中的某条请假申请：0 未批准；1 被批准；2 被驳回
    int updateIsApplyOver(int overno, int isapply);

    OverApply showOverApplyByOverno(int overno);

    List<LeaveApply> showLeaveApplyEmpno(String empno);

    int addNewLeaveApply(LeaveApply leaveApply);

    int deleteLeaveApplyByLeaveno(int leaveno);

    List<OverApply> showOverApplyByEmpno(String empno);

    int deleteOverApplyByOverno(int overno);

    int addNewOverApply(OverApply overApply);

    int updateRlstartByarrno(int arrno, String recordTime, boolean islate);

    int updateRlendByarrno(int arrno, String recordTime, boolean isearly);

    int updateChecktypeByarrno(int arrno, String checktype);

    List<LeaveApply> showApprovedLeaveByEmpno(String empno);

    Integer countUnapprovedLeaveByDep(String depno);

    Integer countUnapprovedOverByDep(String depno);
}
