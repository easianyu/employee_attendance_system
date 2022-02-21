import com.edu.ustb.dao.ArrangeDao;
import com.edu.ustb.dao.impl.ArrangeDaoImpl;
import com.edu.ustb.entities.*;
import com.edu.ustb.service.ArrangeService;
import com.edu.ustb.service.impl.ArrangeServiceImpl;
import com.edu.ustb.utils.TimeUtils;
import org.junit.Test;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ArrangeTest {





@Test
public void showAllDao() {
    ArrangeDao arrangeDao = new ArrangeDaoImpl();
    List<Arrangement> list = arrangeDao.showAll();
    for (Arrangement arr : list) {
        System.out.println(arr);
    }
}

@Test
public void showAllTurnoutToManagerDao() {
    ArrangeDao arrangeDao = new ArrangeDaoImpl();
    List<TurnoutManager> turnoutManagers = arrangeDao.showAllTurnoutToManager();
    System.out.println(turnoutManagers);
    for (TurnoutManager t : turnoutManagers) {
        System.out.println(t);
    }
}

@Test
public void showUsersTurnoutByMonthToManagerDao(){
    ArrangeDao arrangeDao = new ArrangeDaoImpl();
    String time = "2020-11-01 00:00:00";
    List<TurnoutManager> turnoutManagers = arrangeDao.showUsersTurnoutByMonthToManager(time);
    for(TurnoutManager t : turnoutManagers){
        System.out.println(t);
    }
}

@Test
public void showAUserTurnoutByMonthToManagerDao(){
    ArrangeDao arrangeDao = new ArrangeDaoImpl();
    String empno = "2001";
    String time = "2020-05-01 00:00:00";
    List<TurnoutManager> turnoutManagers = arrangeDao.showAUserTurnoutByMonthToManager(empno, time);
    for(TurnoutManager t : turnoutManagers){
        System.out.println(t);
        System.out.println(t.getArrno());
        System.out.println(t.getEmpno());
        System.out.println(t.getStart());
        System.out.println(t.getEnd());
        System.out.println(t.getRlstart());
        System.out.println(t.getRlend());
    }
}

@Test
public void showAUserAllTurnoutToManagerDao(){
    ArrangeDao arrangeDao = new ArrangeDaoImpl();
    String empno = "2001";
    List<TurnoutManager> turnoutManagers = arrangeDao.showAUserAllTurnoutToManager(empno);
    for(TurnoutManager t : turnoutManagers){
        System.out.println(t);
    }
}

@Test
public void addNewArrangementDao(){
    ArrangeDao arrangeDao = new ArrangeDaoImpl();
    TurnoutManager t1 = new TurnoutManager();
    t1.setEmpno("1001");
    t1.setStart(Timestamp.valueOf("2020-05-12 08:45:48"));
    t1.setEnd(Timestamp.valueOf("2020-05-12 17:45:48"));
    t1.setChecktypename("30");
    t1.setIsover(false);
    t1.setIstemporary(false);
    System.out.println(t1.getStart());
    System.out.println(t1.getEnd());
    int i = arrangeDao.addNewArrangement(t1);
    System.out.println(i == 1);
}

@Test
public void showAllTurnoutToInteDao(){
    ArrangeDao arrangeDao = new ArrangeDaoImpl();
    List<TurnoutManager> turnoutInte = arrangeDao.showAllTurnoutToInte("100");
    System.out.println(turnoutInte);
    for (TurnoutManager t : turnoutInte) {
        System.out.println(t);
    }
}

@Test
public void showLeaveApplyByDepDao(){
    ArrangeDao arrangeDao = new ArrangeDaoImpl();
    List<LeaveApply> list = arrangeDao.showLeaveApplyByDep("200");
    System.out.println(list);
    for (LeaveApply t : list) {
        System.out.println(t);
    }
}

@Test
public void updateIsapplyArrangeDao(){
    ArrangeDaoImpl arrangeDao = new ArrangeDaoImpl();
    int num = arrangeDao.updateIsLeaveArrange("1",
            "2020-05-01 00:00:00", "2020-06-01 00:00:00");
    System.out.println(num == 1);
}

@Test
public void updateIsApplyLeaveDao(){
    ArrangeDaoImpl arrangeDao = new ArrangeDaoImpl();
    int i = arrangeDao.updateIsApplyLeave(1000, 0);
    System.out.println(i);
}

@Test
public void showAUserLeaveApply(){
    ArrangeDaoImpl arrangeDao = new ArrangeDaoImpl();
    LeaveApply leaveApply = arrangeDao.showAUserLeaveApply(10000);
    System.out.println(leaveApply);
}

@Test
public void deleteArrByArrnoDao(){
    ArrangeDaoImpl arrangeDao = new ArrangeDaoImpl();
    int i = arrangeDao.deleteArrByArrno(1136);
    System.out.println(i == 1);
}

@Test
public void showAUserTurnoutBetweenDao(){
    ArrangeDaoImpl arrangeDao = new ArrangeDaoImpl();

    List<TurnoutManager> list = arrangeDao.showAUserTurnoutBetween(
            "2002", "2020-05-03 08:45:48", "2020-05-13 08:40:48");
    for (TurnoutManager turnoutManager : list) {
        System.out.println(turnoutManager);
    }

}

@Test
public void updateIsApplyOverDao(){
    ArrangeDaoImpl arrangeDao = new ArrangeDaoImpl();
    int i = arrangeDao.updateIsApplyOver(10009, 1);
    System.out.println(i == 1);
}

@Test
public void showOverApplyByOvernoDao(){
    ArrangeDaoImpl arrangeDao = new ArrangeDaoImpl();
    OverApply overApply = arrangeDao.showOverApplyByOverno(10009);
    System.out.println(overApply);
}

@Test
public void showApprovedLeaveByEmpnoDao(){
    ArrangeDaoImpl arrangeDao = new ArrangeDaoImpl();
    List<LeaveApply> list = arrangeDao.showApprovedLeaveByEmpno("2002");
    if (list != null) {
        for (LeaveApply li : list) {
            System.out.println(li);
        }
    }
}


@Test
public void countUnapprovedLeaveByDepDao(){
    ArrangeDaoImpl arrangeDao = new ArrangeDaoImpl();
    Integer sum = arrangeDao.countUnapprovedLeaveByDep("200");
    System.out.println(sum);
}

@Test
public void countUnapprovedOverByDepDao(){
    ArrangeDaoImpl arrangeDao = new ArrangeDaoImpl();
    Integer sum = arrangeDao.countUnapprovedOverByDep("200");
    System.out.println(sum);
}

/**
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

@Test
public void addNewArrangementService() {
    ArrangeService arrangeService = new ArrangeServiceImpl();
    TurnoutManager t1 = new TurnoutManager();
    t1.setEmpno("1");
    t1.setStart(Timestamp.valueOf("2020-05-13 08:45:48"));
    t1.setEnd(Timestamp.valueOf("2020-05-13 17:45:48"));
    int i = arrangeService.addNewArrangement(t1);
    System.out.println(i);
}


@Test
public void showAllTurnoutToManagerService() {
    ArrangeService arrangeService = new ArrangeServiceImpl();
    List<TurnoutManagerFront> turnoutManagers = arrangeService.showAllTurnoutToManager();
    try {
        for (TurnoutManagerFront t : turnoutManagers) {
            System.out.println(t);
        }
    } catch (Exception e) {
        System.out.println(e);
    }
}

@Test
public void showUsersTurnoutByMonthToManagerService(){
    ArrangeService arrangeService = new ArrangeServiceImpl();
    String time = "2020-06-01 00:00:00";
    List<TurnoutManagerFront> turnoutManagers = arrangeService.showUsersTurnoutByMonthToManager(time);
    try {
        for (TurnoutManagerFront t : turnoutManagers) {
            System.out.println(t);
        }
    } catch (Exception e) {
        System.out.println(e);
    }
}

@Test
public void showAUserAllTurnoutToManagerService(){
    ArrangeService arrangeService = new ArrangeServiceImpl();
    String empno = "2001";
    List<TurnoutManagerFront> turnoutManagerFront = arrangeService.showAUserTurnoutToManager(empno);
    for(TurnoutManagerFront t : turnoutManagerFront){
        System.out.println(t);
    }
}

@Test
public void showAUserTurnoutByMonthToManagerService(){
    ArrangeService arrangeService = new ArrangeServiceImpl();
    String empno = "2002";
    String time = "2020-05-01 00:00:00";
    List<TurnoutManagerFront> turnoutManagerFront =
            arrangeService.showAUserTurnoutByMonthToManager(empno, time);
    for(TurnoutManagerFront t : turnoutManagerFront){
        System.out.println(t);
    }
}


@Test
public void showAllTurnoutToInteService() {
    String depno = "200";
    ArrangeService arrangeService = new ArrangeServiceImpl();
    List<TurnoutManagerFront> turnoutManagers = arrangeService.showAllTurnoutToInte(depno);
    try {
        for (TurnoutManagerFront t : turnoutManagers) {
            System.out.println(t);
        }
    } catch (Exception e) {
        System.out.println(e);
    }
}

@Test
public void showUsersTurnoutByMonthToInteService(){
    String depno = "200";
    ArrangeService arrangeService = new ArrangeServiceImpl();
    String time = "2020-06-01 00:00:00";
    List<TurnoutManagerFront> turnoutManagers = arrangeService.showUsersTurnoutByMonthToInte(depno, time);
    try {
        for (TurnoutManagerFront t : turnoutManagers) {
            System.out.println(t);
        }
    } catch (Exception e) {
        System.out.println(e);
    }
}

@Test
public void showAUserAllTurnoutToInteService(){
    String depno = "200";

    ArrangeService arrangeService = new ArrangeServiceImpl();
    String empno = "2001";
    List<TurnoutManagerFront> turnoutManagerFront = arrangeService.showAUserTurnoutToInte(depno, empno);
    for(TurnoutManagerFront t : turnoutManagerFront){
        System.out.println(t);
    }
}

@Test
public void showAUserTurnoutByMonthToInteService(){
    String depno = "200";
    ArrangeService arrangeService = new ArrangeServiceImpl();
    String empno = "2001";
    String time = "2020-05-01 00:00:00";
    List<TurnoutManagerFront> turnoutManagerFront = arrangeService.showAUserTurnoutByMonthToInte(depno, empno, time);
    for(TurnoutManagerFront t : turnoutManagerFront){
        System.out.println(t);
    }
}

@Test
public void showLeaveApplyByDepService(){
    ArrangeService arrangeService  = new ArrangeServiceImpl();
    List<LeaveApply> list = arrangeService.showLeaveApplyByDep("200");
    for(LeaveApply l : list){
        System.out.println(l);
    }
}

@Test
public void agreeLeaveApplyService(){
    ArrangeServiceImpl arrangeService = new ArrangeServiceImpl();
    List<TurnoutManager> i = arrangeService.agreeLeaveApply(10026);
    System.out.println(i);
}

@Test
public void disagreeLeaveApplyService(){
    ArrangeServiceImpl arrangeService = new ArrangeServiceImpl();
    int i = arrangeService.disagreeLeaveApply(10026);
    System.out.println(i == 1);
}


@Test
public void deleteArrByArrnoService(){
    ArrangeServiceImpl arrangeService = new ArrangeServiceImpl();
    int i = arrangeService.deleteArrByArrno(1071);
    System.out.println(i == 1);
}

@Test
public void updateArrByArrnoService(){
    ArrangeServiceImpl arrangeService = new ArrangeServiceImpl();
    TurnoutManager turnoutManager = new TurnoutManager();
    turnoutManager.setArrno(1034);
    turnoutManager.setStart(Timestamp.valueOf("2020-08-05 08:17:00.0"));
    turnoutManager.setEnd(Timestamp.valueOf("2020-08-05 20:17:00.0"));
    turnoutManager.setIsover(false);
    int i = arrangeService.updateArrByArrno(turnoutManager);
    System.out.println(i == 1);
}

@Test
public void addAffectedArrService(){
    ArrangeServiceImpl arrangeService = new ArrangeServiceImpl();
    int i = arrangeService.addAffectedArr(3309);
    System.out.println(i == 1);
}

@Test
public void deleteAllAffectedArrService(){
    ArrangeServiceImpl arrangeService = new ArrangeServiceImpl();
    int i = arrangeService.deleteAllAffectedArr();
    System.out.println(i == 1);
}

@Test
public void showAllAffectedArrService(){
    ArrangeServiceImpl arrangeService = new ArrangeServiceImpl();
    List<Integer> list = arrangeService.showAllAffectedArr();
    for (Integer integer : list) {
        System.out.println(integer);
    }

}

@Test
public void showArrByArrnoService(){
    ArrangeServiceImpl arrangeService = new ArrangeServiceImpl();
    TurnoutManagerFront turnoutManagerFront = arrangeService.showArrByArrno(1121);
    System.out.println(turnoutManagerFront);
}

@Test
public void deleteAffectedArrByArrnoService(){
    ArrangeServiceImpl arrangeService = new ArrangeServiceImpl();
    int i = arrangeService.deleteAffectedArrByArrno(3309);
    System.out.println(i == 1);
}

@Test
public void showOverApplyByDepService(){
    ArrangeServiceImpl arrangeService = new ArrangeServiceImpl();
    List<OverApply> lis = arrangeService.showOverApplyByDep("200");
    for (OverApply li : lis) {
        System.out.println(li);
    }
}

@Test
public void showLeaveApplyEmpno(){
    ArrangeServiceImpl arrangeService = new ArrangeServiceImpl();
    List<LeaveApply> list = arrangeService.showLeaveApplyEmpno("2002");
    for (LeaveApply leaveApply : list) {
        System.out.println(leaveApply);
    }
}

@Test
public void addNewLeaveApplyService(){
    ArrangeServiceImpl arrangeService = new ArrangeServiceImpl();
    LeaveApply leaveApply = new LeaveApply();
    leaveApply.setEmpno("2002");
    leaveApply.setStart(Timestamp.valueOf("2020-08-05 08:17:00"));
    leaveApply.setEnd(Timestamp.valueOf("2020-08-05 20:17:00"));
    leaveApply.setReason("赶集去了");
    int i = arrangeService.addNewLeaveApply(leaveApply);
    System.out.println(i == 1);
}

@Test
public void deleteLeaveApplyByLeavenoService(){
    ArrangeServiceImpl arrangeService = new ArrangeServiceImpl();
    int i = arrangeService.deleteLeaveApplyByLeaveno(10030);
    System.out.println(i == 1);
}

@Test
public void showOverApplyByEmpnoService(){
    ArrangeServiceImpl arrangeService = new ArrangeServiceImpl();
    List<OverApply> overApplies = arrangeService.showOverApplyByEmpno("2002");
    for (OverApply overApply : overApplies) {
        System.out.println(overApply);
    }
}

@Test
public void showUserArrADayService(){
    ArrangeServiceImpl arrangeService = new ArrangeServiceImpl();
    List<TurnoutManagerFront> list = arrangeService.showUserArrADay("2002",
            (new Timestamp(System.currentTimeMillis()).toString()));
    for (TurnoutManagerFront t : list) {
        System.out.println(t);
    }
}





/**
 *
 *
 *
 *
 *
 *
 *
 *
 */





@Test
public void addTempArrService(){
    ArrangeServiceImpl arrangeService = new ArrangeServiceImpl();
    int i = arrangeService.addTempArr("2020-10-03 08:35:30", "2020-10-03 19:35:30");
    System.out.println(i);
}


@Test
public void testDate() {
    ArrangeService arrangeService = new ArrangeServiceImpl();
    List<TurnoutManagerFront> turnoutManagers = arrangeService.showAllTurnoutToManager();
    for (TurnoutManagerFront t : turnoutManagers) {
        System.out.println(t);
    }
}

@Test
public void testGetEndOfMonth(){
    String time_start0 = "2020-01-00 00:00:00";//一般情况
    System.out.println(TimeUtils.getEndOfMonth(time_start0));
    System.out.println("2020-02-01 00:00:00".equals(TimeUtils.getEndOfMonth(time_start0)));

    String time_start1 = "2020-09-00 00:00:00";//9月和10月临界点
    System.out.println(TimeUtils.getEndOfMonth(time_start1));
    System.out.println("2020-10-01 00:00:00".equals(TimeUtils.getEndOfMonth(time_start1)));

    String time_start2 = "2020-08-00 00:00:00";//一般情况
    System.out.println(TimeUtils.getEndOfMonth(time_start2));
    System.out.println("2020-02-01 00:00:00".equals(TimeUtils.getEndOfMonth(time_start0)));

    String time_start3 = "2020-12-00 00:00:00";//9月和10月临界点
    System.out.println(TimeUtils.getEndOfMonth(time_start3));
    System.out.println("2020-12-31 23:59:00".equals(TimeUtils.getEndOfMonth(time_start3)));
}

@Test
public void testList(){
    ArrayList<Object> objects = new ArrayList<>();
    for (Object object : objects) {
        System.out.println(object);
    }
}
}
