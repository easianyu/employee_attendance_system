package com.edu.ustb.dao.impl;

import com.edu.ustb.dao.ArrangeDao;
import com.edu.ustb.entities.*;
import com.edu.ustb.utils.JDBCUtils;
import com.edu.ustb.utils.TimeUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class ArrangeDaoImpl implements ArrangeDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public List<TurnoutManager> showAllTurnoutToManager() {
        List<TurnoutManager> list = null;
        String sql = "SELECT a.arrno, a.empno, empname, d.depname, e.empposition, c.checktypename, " +
                " a.start, a.end, a.rlstart, a.rlend,isover,  islate, isearly, isleave, istemporary" +
                " FROM arrangement a, users u, checktype c, department d, empposition e" +
                " WHERE a.empno=u.empno AND a.checktypeno=c.checktypeno AND u.depno=d.depno AND e.empposno=u.empposno" +
                " ORDER BY a.start ASC ";
        try {
            list = template.query(sql, new BeanPropertyRowMapper<TurnoutManager>(TurnoutManager.class));
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    @Override
    public List<TurnoutManager> showUsersTurnoutByMonthToManager(String time) {
        List<TurnoutManager> list = null;
        String startTime = time;
        String endTime = TimeUtils.getEndOfMonth(time);
        String sql = "SELECT a.arrno, a.empno, empname, d.depname, e.empposition, c.checktypename, a.start, a.end, a.rlstart, a.rlend,isover,  islate, isearly, isleave, istemporary" +
                " FROM arrangement a, users u, checktype c, department d , empposition e" +
                " WHERE a.empno=u.empno AND a.checktypeno=c.checktypeno AND u.depno=d.depno AND e.empposno=u.empposno" +
                " AND UNIX_TIMESTAMP(a.start)  >= UNIX_TIMESTAMP(?)  " +
                " AND  UNIX_TIMESTAMP(a.start)  <= UNIX_TIMESTAMP(?)  " +
                " ORDER BY a.start ASC ";
        try {
            list = template.query(sql, new BeanPropertyRowMapper<TurnoutManager>(TurnoutManager.class), startTime, endTime);
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    @Override
    public List<TurnoutManager> showAUserTurnoutByMonthToManager(String empno, String time) {
        List<TurnoutManager> list = null;
        String startTime = time;
        String endTime = TimeUtils.getEndOfMonth(time);
        String sql = "SELECT a.arrno, a.empno, empname, d.depname, e.empposition, c.checktypename, a.start, a.end, a.rlstart, a.rlend,isover,  islate, isearly, isleave, istemporary" +
                " FROM arrangement a, users u, checktype c, department d , empposition e" +
                " WHERE a.empno=u.empno AND a.checktypeno=c.checktypeno AND u.depno=d.depno AND e.empposno=u.empposno" +
                " AND a.empno=?" +
                " AND UNIX_TIMESTAMP(a.start)  >= UNIX_TIMESTAMP(?)  " +
                " AND  UNIX_TIMESTAMP(a.start)  <= UNIX_TIMESTAMP(?)  " +
                " ORDER BY a.start ASC ";
        try {
            list = template.query(sql, new BeanPropertyRowMapper<TurnoutManager>(TurnoutManager.class), empno, startTime, endTime);
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    @Override
    public List<TurnoutManager> showAUserAllTurnoutToManager(String empno) {
        List<TurnoutManager> list = null;
        String sql = "SELECT a.arrno, a.empno, empname, d.depname, e.empposition, c.checktypename, a.start, a.end, a.rlstart, a.rlend,isover,  islate, isearly, isleave, istemporary" +
                " FROM arrangement a, users u, checktype c, department d, empposition e" +
                " WHERE a.empno=u.empno AND a.checktypeno=c.checktypeno AND u.depno=d.depno AND e.empposno=u.empposno " +
                " AND a.empno=?" +
                " ORDER BY a.start ASC ";
        try {
            list = template.query(sql, new BeanPropertyRowMapper<TurnoutManager>(TurnoutManager.class), empno);
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    @Override
    public List<Arrangement> showAll() {
        String sql = "SELECT * FROM arrangement";
        return template.query(sql, new BeanPropertyRowMapper<Arrangement>(Arrangement.class));
    }

    @Override
    public int addNewArrangement(TurnoutManager turnoutManager) {
        int num = 0;
        String sql = "INSERT INTO arrangement(empno, start, end, isover, istemporary) " +
                " VALUES (?, ?, ?, ?, ?)";
        try {
            num = template.update(sql, turnoutManager.getEmpno(),
                    turnoutManager.getStart(),
                    turnoutManager.getEnd(),
                    turnoutManager.getIsover(),
                    turnoutManager.getIstemporary());
        } catch (Exception e) {

        }
        return num;
    }

    @Override
    public List<TurnoutManager> showAllTurnoutToInte(String depno) {
        List<TurnoutManager> list = null;
        String sql = "SELECT a.arrno, a.empno, empname, d.depname, e.empposition, c.checktypename, " +
                " a.start, a.end, a.rlstart, a.rlend,isover,  islate, isearly, isleave, istemporary" +
                " FROM arrangement a, users u, checktype c, department d, empposition e" +
                " WHERE a.empno=u.empno AND a.checktypeno=c.checktypeno " +
                " AND u.depno=d.depno AND e.empposno=u.empposno AND u.depno=?" +
                " ORDER BY a.start ASC ";
        try {
            list = template.query(sql, new BeanPropertyRowMapper<TurnoutManager>(TurnoutManager.class), depno);
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    @Override
    public List<TurnoutManager> showUsersTurnoutByMonthToInte(String depno, String time) {
        List<TurnoutManager> list = null;
        String startTime = time;
        String endTime = TimeUtils.getEndOfMonth(time);
        String sql = "SELECT a.arrno, a.empno, empname, d.depname, e.empposition, c.checktypename, a.start, a.end, a.rlstart, a.rlend,isover,  islate, isearly, isleave, istemporary" +
                " FROM arrangement a, users u, checktype c, department d , empposition e" +
                " WHERE a.empno=u.empno AND a.checktypeno=c.checktypeno AND u.depno=d.depno AND e.empposno=u.empposno" +
                " AND UNIX_TIMESTAMP(a.start)  >= UNIX_TIMESTAMP(?)  " +
                " AND  UNIX_TIMESTAMP(a.start)  <= UNIX_TIMESTAMP(?)   AND u.depno=?" +
                " ORDER BY a.start ASC ";
        try {
            list = template.query(sql, new BeanPropertyRowMapper<TurnoutManager>(TurnoutManager.class), startTime, endTime, depno);
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    @Override
    public List<TurnoutManager> showAUserTurnoutByMonthToInte(String depno, String empno, String time) {
        List<TurnoutManager> list = null;
        String startTime = time;
        String endTime = TimeUtils.getEndOfMonth(time);
        String sql = "SELECT a.arrno, a.empno, empname, d.depname, e.empposition, c.checktypename, a.start, a.end, a.rlstart, a.rlend,isover,  islate, isearly, isleave, istemporary" +
                " FROM arrangement a, users u, checktype c, department d , empposition e" +
                " WHERE a.empno=u.empno AND a.checktypeno=c.checktypeno AND u.depno=d.depno AND e.empposno=u.empposno" +
                " AND a.empno=?" +
                " AND UNIX_TIMESTAMP(a.start)  >= UNIX_TIMESTAMP(?)  " +
                " AND  UNIX_TIMESTAMP(a.start)  <= UNIX_TIMESTAMP(?)   AND u.depno=?" +
                " ORDER BY a.start ASC ";
        try {
            list = template.query(sql, new BeanPropertyRowMapper<TurnoutManager>(TurnoutManager.class), empno, startTime, endTime, depno);
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    @Override
    public List<TurnoutManager> showAUserAllTurnoutToInte(String depno, String empno) {
        List<TurnoutManager> list = null;
        String sql = "SELECT a.arrno, a.empno, empname, d.depname, e.empposition, c.checktypename, a.start, a.end, a.rlstart, a.rlend,isover,  islate, isearly, isleave, istemporary" +
                " FROM arrangement a, users u, checktype c, department d, empposition e" +
                " WHERE a.empno=u.empno AND a.checktypeno=c.checktypeno AND u.depno=d.depno AND e.empposno=u.empposno " +
                " AND a.empno=?  AND u.depno=?" +
                " ORDER BY a.start ASC ";
        try {
            list = template.query(sql, new BeanPropertyRowMapper<TurnoutManager>(TurnoutManager.class), empno, depno);
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    @Override
    public List<LeaveApply> showLeaveApplyByDep(String depno) {
        List<LeaveApply> list = null;
        String sql = "SELECT l.leaveno, l.empno, u.empname, d.depname, l.start, l.end, l.reason, l.isapply " +
                " FROM leaveapply l, users u, department d " +
                " WHERE l.empno=u.empno AND u.depno=d.depno AND l.isapply=0 AND u.depno=?";
        try {
            list = template.query(sql, new BeanPropertyRowMapper<LeaveApply>(LeaveApply.class), depno);
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    @Override
    public int updateIsLeaveArrange(String empno, String start, String end) {
        int num = 0;
        String sql = "UPDATE arrangement set isleave=1" +
                " WHERE UNIX_TIMESTAMP(start)  >= UNIX_TIMESTAMP(?)  " +
                " AND  UNIX_TIMESTAMP(start)  <= UNIX_TIMESTAMP(?)" +
                " AND empno=?";
        try {
            num = template.update(sql, start, end, empno);
        } catch (Exception e) {
            System.out.println(e);
        }
        if (num >= 1) num = 1;
        return num;
    }

    @Override
    public int updateIsApplyLeave(int leaveno, int isApply) {
        int num = 0;
        String sql = "UPDATE leaveapply set isapply=?" +
                " WHERE leaveno=?";
        try {
            num = template.update(sql, isApply, leaveno);

        } catch (Exception e) {
            System.out.println(e);
        }
        return num;
    }

    @Override
    public LeaveApply showAUserLeaveApply(int leaveno) {
        LeaveApply leaveApply = null;
        String sql = "SELECT l.leaveno, l.empno, u.empname, d.depname, l.start, l.end, l.reason, l.isapply " +
                "FROM leaveapply l, users u, department d " +
                "WHERE l.empno=u.empno AND u.depno=d.depno AND l.leaveno=?";
        try {
            leaveApply = template.queryForObject(sql, new BeanPropertyRowMapper<LeaveApply>(LeaveApply.class), leaveno);
        } catch (Exception e) {
            System.out.println(e);
        }
        return leaveApply;
    }

    @Override
    public int deleteArrByArrno(int arrno) {
        int num = 0;
        String sql = "delete from arrangement where arrno=?";
        try {
            num = template.update(sql, arrno);
        } catch (Exception e) {

        }
        return num;
    }

    @Override
    public int updateArrByArrno(TurnoutManager turnoutManager) {
        int num = 0;
        String sql = "UPDATE arrangement set start=?, end=?, isover=?" +
                " WHERE arrno=?";
        try {
            num = template.update(sql, turnoutManager.getStart(),
                    turnoutManager.getEnd(),
                    turnoutManager.getIsover(),
                    turnoutManager.getArrno());

        } catch (Exception e) {
            System.out.println(e);
        }
        return num;
    }

    @Override
    public List<TurnoutManager> showAUserTurnoutBetween(String empno, String start, String end) {
        List<TurnoutManager> list = null;
        String sql = "SELECT a.arrno, a.empno, empname, d.depname, e.empposition, c.checktypename, a.start, a.end, a.rlstart, a.rlend,isover,  islate, isearly, isleave, istemporary" +
                " FROM arrangement a, users u, checktype c, department d , empposition e" +
                " WHERE a.empno=u.empno AND a.checktypeno=c.checktypeno AND u.depno=d.depno AND e.empposno=u.empposno" +
                " AND a.empno=?" +
                " AND UNIX_TIMESTAMP(a.start)  >= UNIX_TIMESTAMP(?)  " +
                " AND  UNIX_TIMESTAMP(a.start)  <= UNIX_TIMESTAMP(?) " +
                " ORDER BY a.start ASC ";
        try {
            list = template.query(sql, new BeanPropertyRowMapper<TurnoutManager>(TurnoutManager.class), empno, start, end);
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    @Override
    public int addAffectedArr(int arrno) {
        int num = 0;
        String sql = "INSERT INTO affectedarr(affectedno) VALUES(?)";
        try {
            num = template.update(sql, arrno);
        } catch (Exception e) {
            System.out.println(e);
        }
        return num;
    }

    @Override
    public int deleteAllAffectedArr() {
        int num = 0;
        String sql = "DELETE FROM affectedarr";
        try {
            num = template.update(sql);
        } catch (Exception e) {
            System.out.println(e);
        }
        return num;
    }

    @Override
    public List<Integer> showAllAffectedArr() {
        List<Integer> list = null;
        String sql = "SELECT * FROM affectedarr";
        try {
            list = template.queryForList(sql, Integer.class);
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    @Override
    public TurnoutManager showArrByArrno(int arrno) {
        TurnoutManager turnoutManager = null;
        String sql = "SELECT a.arrno, a.empno, empname, d.depname, e.empposition, c.checktypename, " +
                " a.start, a.end, a.rlstart, a.rlend,isover,  islate, isearly, isleave, istemporary" +
                " FROM arrangement a, users u, checktype c, department d, empposition e" +
                " WHERE a.empno=u.empno AND a.checktypeno=c.checktypeno " +
                " AND u.depno=d.depno AND e.empposno=u.empposno AND a.arrno=?" +
                " ORDER BY a.start ASC ";
        try {
            turnoutManager = template.queryForObject(sql,
                    new BeanPropertyRowMapper<TurnoutManager>(TurnoutManager.class), arrno);
        } catch (Exception e) {
            System.out.println(e);
        }
        return turnoutManager;
    }

    @Override
    public int deleteAffectedArrByArrno(int arrno) {
        int num = 0;
        String sql = "DELETE FROM affectedarr WHERE affectedno=?";
        try {
            num = template.update(sql, arrno);
        } catch (Exception e) {
            System.out.println(e);
        }
        return num;
    }

    @Override
    public List<LeaveApply> showBackApplyByDep(String depno) {
        List<LeaveApply> list = null;
        String sql = "SELECT l.leaveno, l.empno, u.empname, d.depname, l.start, l.end, l.reason, l.isapply" +
                " FROM leaveapply l, users u, department d " +
                " WHERE l.empno=u.empno AND u.depno=d.depno AND l.isapply=3 AND u.depno=?";
        try {
            list = template.query(sql, new BeanPropertyRowMapper<LeaveApply>(LeaveApply.class), depno);
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    @Override
    public List<OverApply> showOverApplyByDep(String depno) {
        List<OverApply> list = null;
        String sql = "SELECT o.overno, o.empno, u.empname, d.depname, o.start, o.end, o.reason, o.isapply" +
                " FROM overapply o, users u, department d " +
                " WHERE o.empno=u.empno AND u.depno=d.depno AND u.depno=? AND o.isapply=0";
        try {
            list = template.query(sql, new BeanPropertyRowMapper<OverApply>(OverApply.class), depno);
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    @Override
    public int updateIsApplyOver(int overno, int isapply) {
        int num = 0;
        String sql = "UPDATE overapply set isapply=?" +
                " WHERE overno=?";
        try {
            num = template.update(sql, isapply, overno);

        } catch (Exception e) {
            System.out.println(e);
        }
        return num;
    }

    @Override
    public OverApply showOverApplyByOverno(int overno) {
        OverApply overApply = null;
        String sql = "SELECT o.overno, o.empno, u.empname, d.depname, o.start, o.end, o.reason, o.isapply" +
                " FROM overapply o, users u, department d " +
                " WHERE o.empno=u.empno AND u.depno=d.depno AND o.overno=?";
        try {
            overApply = template.queryForObject(sql, new BeanPropertyRowMapper<OverApply>(OverApply.class), overno);
        } catch (Exception e) {
            System.out.println(e);
        }
        return overApply;
    }

    @Override
    public List<LeaveApply> showLeaveApplyEmpno(String empno) {
        List<LeaveApply> list = null;
        String sql = "SELECT l.leaveno, l.empno, u.empname, d.depname, l.start, l.end, l.reason, l.isapply " +
                " FROM leaveapply l, users u, department d " +
                " WHERE l.empno=u.empno AND u.depno=d.depno AND l.empno=?";
        try {
            list = template.query(sql, new BeanPropertyRowMapper<LeaveApply>(LeaveApply.class), empno);
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    @Override
    public int addNewLeaveApply(LeaveApply leaveApply) {
        int num = 0;
        String sql = "INSERT INTO leaveapply(empno, start, end, reason) " +
                " VALUES(?, ?, ?, ?)";
        try {
            num = template.update(sql,
                    leaveApply.getEmpno(),
                    leaveApply.getStart(),
                    leaveApply.getEnd(),
                    leaveApply.getReason());

        } catch (Exception e) {
            System.out.println(e);
        }
        return num;
    }

    @Override
    public int deleteLeaveApplyByLeaveno(int leaveno) {
        int num = 0;
        String sql = "delete from leaveapply where leaveno=?";
        try {
            num = template.update(sql, leaveno);
        } catch (Exception e) {

        }
        return num;
    }

    @Override
    public List<OverApply> showOverApplyByEmpno(String empno) {
        List<OverApply> list = null;
        String sql = "SELECT o.overno, o.empno, u.empname, d.depname, o.start, o.end, o.reason, o.isapply" +
                " FROM overapply o, users u, department d " +
                " WHERE o.empno=u.empno AND u.depno=d.depno AND o.empno=?";
        try {
            list = template.query(sql, new BeanPropertyRowMapper<OverApply>(OverApply.class), empno);
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    @Override
    public int deleteOverApplyByOverno(int overno) {
        int num = 0;
        String sql = "delete from overapply where overno=?";
        try {
            num = template.update(sql, overno);
        } catch (Exception e) {

        }
        return num;
    }

    @Override
    public int addNewOverApply(OverApply overApply) {
        int num = 0;
        String sql = "INSERT INTO overapply(empno, start, end, reason) " +
                " VALUES(?, ?, ?, ?)";
        try {
            num = template.update(sql,
                    overApply.getEmpno(),
                    overApply.getStart(),
                    overApply.getEnd(),
                    overApply.getReason());
        } catch (Exception e) {
            System.out.println(e);
        }
        return num;
    }

    @Override
    public int updateRlstartByarrno(int arrno, String recordTime, boolean islate) {
        int num = 0;
        String sql = "UPDATE arrangement set rlstart=?, islate=?" +
                " WHERE arrno=?";
        try {
            num = template.update(sql, recordTime, islate, arrno);
        } catch (Exception e) {
            System.out.println(e);
        }
        return num;
    }

    @Override
    public int updateRlendByarrno(int arrno, String recordTime, boolean isearly) {
        int num = 0;
        String sql = "UPDATE arrangement set rlend=?, isearly=?" +
                " WHERE arrno=?";
        try {
            num = template.update(sql, recordTime, isearly, arrno);
        } catch (Exception e) {
            System.out.println(e);
        }
        return num;
    }

    @Override
    public int updateChecktypeByarrno(int arrno, String checktype) {
        int num = 0;
        String sql = "UPDATE arrangement set checktypeno=?" +
                " WHERE arrno=?";
        try {
            num = template.update(sql, checktype, arrno);
        } catch (Exception e) {
            System.out.println(e);
        }
        return num;
    }

    @Override
    public List<LeaveApply> showApprovedLeaveByEmpno(String empno) {
        List<LeaveApply> list = null;
        String sql = "SELECT l.leaveno, l.empno, u.empname, d.depname, l.start, l.end, l.reason, l.isapply " +
                " FROM leaveapply l, users u, department d " +
                " WHERE l.empno=u.empno " +
                " AND u.depno=d.depno " +
                " AND l.empno=? " +
                " ANd l.isapply=1";
        try {
            list = template.query(sql, new BeanPropertyRowMapper<LeaveApply>(LeaveApply.class), empno);
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    @Override
    public Integer countUnapprovedLeaveByDep(String depno) {
        String sql = "SELECT count(*) FROM leaveapply l, users u WHERE depno=? AND l.empno=u.empno AND l.isapply=0";
        Integer sum = 0;
        sum = template.queryForObject(sql, Integer.class, depno);
        return sum;
    }

    @Override
    public Integer countUnapprovedOverByDep(String depno) {
        String sql = "SELECT count(*) FROM overapply o, users u WHERE depno=? AND o.empno=u.empno AND o.isapply=0";
        Integer sum = 0;
        sum = template.queryForObject(sql, Integer.class, depno);
        return sum;
    }
}
