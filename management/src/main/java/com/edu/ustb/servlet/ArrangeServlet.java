package com.edu.ustb.servlet;

import com.edu.ustb.entities.*;
import com.edu.ustb.service.ArrangeService;
import com.edu.ustb.service.CompanyService;
import com.edu.ustb.service.UserService;
import com.edu.ustb.service.impl.ArrangeServiceImpl;
import com.edu.ustb.service.impl.CompanyServiceImpl;
import com.edu.ustb.service.impl.UserServiceImpl;
import com.edu.ustb.utils.FileUtils;
import com.edu.ustb.utils.TimeUtils;
import com.edu.ustb.utils.UuidUtil;
import com.edu.ustb.vo.ResultInfo;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@WebServlet("/management_war_exploded/arrange/*")
public class ArrangeServlet extends BaseServlet {
    private ArrangeService arrangeService = new ArrangeServiceImpl();
    private CompanyService companyService = new CompanyServiceImpl();
    private List<TurnoutManagerFront> listAffected;

    /**
     * 用于记录登录用户为主管时请求访问是否有需审批的次数，checkApply方法
     * 只有count为0时，请求访问返回true，才让前端能显示"需要批准"，
     * 然后将count置1，在不是0的时候访问，返回false，为的是不让他没次刷新页面时弹窗，弹一次就够了
     * count随着进程的进行需要在两个地方做修改
     * 1、checkApply 方法中，count==0时访问成功之后置1
     * 2、addleaveApply 和addOverApply中，添加成功之后置0
     */
    public static int count = 0;

    final Object objLock = new Object();

    /**
     * 用户：经理
     * 功能：查看全部出勤
     */
    public void showAllToManager(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<TurnoutManagerFront> list = arrangeService.showAllTurnoutToManager();
        writeValue(list, response);
    }


    /**
     * 用户：经理
     * 功能：选择员工或月份后检索
     */
    public void managerSearch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String empno = request.getParameter("empno_search");
        String date_search = request.getParameter("date_search");
        String dep_search = request.getParameter("dep_search");

        List<TurnoutManagerFront> list = null;
        if ("all".equals(dep_search)) { //不限定部门
            if ("".equals(empno) && !"all".equals(date_search)) { //一个月份所有员工
                list = arrangeService.showUsersTurnoutByMonthToManager(date_search);
            } else if (!"".equals(empno) && "all".equals(date_search)) { //一个用户所有月份
                list = arrangeService.showAUserTurnoutToManager(empno);
            } else if (!"".equals(empno) && !"all".equals(date_search)) { //一个用户某个月份
                list = arrangeService.showAUserTurnoutByMonthToManager(empno, date_search);
            } else {
                this.showAllToManager(request, response);
            }
        } else { //限定部门，有两种情况：只限定部门，限定部门和月份
            if ("all".equals(date_search)) { //只限定部门
                list = arrangeService.showAllTurnoutToInte(dep_search);
            } else {
                list = arrangeService.showUsersTurnoutByMonthToInte(dep_search, date_search);
            }
        }

        writeValue(list, response);
    }

    /**
     * 用户：经理
     * 功能：查看公司信息
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void showCmpInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<Company> list = (ArrayList<Company>) companyService.showCmpInfo();
        Company cmp = list.get(0);
        cmp.setTolUsers(new UserServiceImpl().countAllUsers());//调用userService获取总人数
        System.out.println(cmp);
        writeValue(cmp, response);
    }

    /**
     * 用户：主管
     * 功能：查看部门信息
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void showDepInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取登录人的所属部门
        User user = (User) request.getSession().getAttribute("user");
        String depno = user.getDepno();

        //提交到Service层处理
        ArrayList<DepartmentInfo> list = companyService.showDepartmentInfo(depno);
        writeValue(list, response);
    }


    /**
     * 用户：主管
     * 功能：查看部门所有出勤信息
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void showAllToIntendent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //从session获取部门编号
        Object user = request.getSession().getAttribute("user");
        String depno = ((User) user).getDepno();

        List<TurnoutManagerFront> list = arrangeService.showAllTurnoutToInte(depno);
        writeValue(list, response);
    }


    /**
     * 用户：员工
     * 功能：查看自己所有出勤信息
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void showAllToUsers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //从session获取部门编号
        Object user = request.getSession().getAttribute("user");
        String empno = ((User) user).getEmpno();
        String depno = ((User) user).getDepno();
        //跟主管查看某员工的排班调用同一个service方法
        List<TurnoutManagerFront> list = arrangeService.showAUserTurnoutToInte(depno, empno);
        writeValue(list, response);
    }


    /**
     * 用户：员工
     * 功能：打卡界面：查看自己当天所有的出勤信息
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void showUserArrToday(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //获取员工的信息
        Object user = request.getSession().getAttribute("user");
        String empno = ((User) user).getEmpno();

        //获取范围的当前时间
        Timestamp currentT = new Timestamp(System.currentTimeMillis());

        //Service岑根据当前时间返回当天的排班
        List<TurnoutManagerFront> list = arrangeService.showUserArrADay(empno, currentT.toString());
        TurnoutManagerFront t = list.get(list.size() - 1);
        String start = t.getStart();
        String end = t.getEnd();


        writeValue(list, response);
    }

    /**
     * 用户：主管
     * 功能：选择员工或月份后检索
     */
    public void intendentSearch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String empno = request.getParameter("empno_search"); // 获取检索的员工编号
        String date_search = request.getParameter("date_search"); // 获取检索的日期

        //从session获取部门编号
        Object user = request.getSession().getAttribute("user");
        String depno = ((User) user).getDepno();

        List<TurnoutManagerFront> list = null;
        if ("".equals(empno) && !"all".equals(date_search)) { //一个月份所有员工
            list = arrangeService.showUsersTurnoutByMonthToInte(depno, date_search);
        } else if (!"".equals(empno) && "all".equals(date_search)) { //一个用户所有月份
            list = arrangeService.showAUserTurnoutToInte(depno, empno);
        } else if (!"".equals(empno) && !"all".equals(date_search)) { //一个用户某个月份
            list = arrangeService.showAUserTurnoutByMonthToInte(depno, empno, date_search);
        } else {
            this.showAllToIntendent(request, response);
        }

        writeValue(list, response);
    }


    /**
     * 用户：主管
     * 功能：给主管显示需要审批的请假请求
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void showLeaveApplyToInte(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //获取主管所在的部门
        User user = (User) request.getSession().getAttribute("user");
        String depno = user.getDepno();

        //检索出需要批准的请假
        List<LeaveApply> list = arrangeService.showLeaveApplyByDep(depno);

        //提交到前台
        writeValue(list, response);
    }

    /**
     * 用户：主管
     * 功能：给主管显示需要审批的请假请求
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void showLeaveApplyUsers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //获取主管所在的部门
        User user = (User) request.getSession().getAttribute("user");
        String empno = user.getEmpno();

        //检索出需要批准的请假
        List<LeaveApply> list = arrangeService.showLeaveApplyEmpno(empno);

        //提交到前台
        writeValue(list, response);
    }


    /**
     * 用户：主管
     * 功能：给主管显示需要审批的销假请求
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void showBackApplyToInte(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //获取主管所在的部门
        User user = (User) request.getSession().getAttribute("user");
        String depno = user.getDepno();

        //检索出需要批准的请假
        List<LeaveApply> list = arrangeService.showBackApplyByDep(depno);

        //提交到前台
        writeValue(list, response);
    }

    /**
     * 用户：主管
     * 功能：给主管显示需要审批的加班请求
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void showOverApplyToInte(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //获取主管所在的部门
        User user = (User) request.getSession().getAttribute("user");
        String depno = user.getDepno();

        //检索出需要批准的请假
        List<OverApply> list = arrangeService.showOverApplyByDep(depno);

        //提交到前台
        writeValue(list, response);
    }

    /**
     * 用户：主管
     * 功能：给主管显示需要审批的加班请求
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void showOverApplyToUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //获取主管所在的部门
        User user = (User) request.getSession().getAttribute("user");
        String empno = user.getEmpno();

        //检索出需要批准的请假
        List<OverApply> list = arrangeService.showOverApplyByEmpno(empno);

        //提交到前台
        writeValue(list, response);
    }

    /**
     * 用户：主管
     * 功能：同意批准员工的请假申请
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void agreeLeaveApply(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String[] agreeLeaveno = request.getParameterValues("agreeLeaveno");
        boolean flag = false;
        ResultInfo info = new ResultInfo();

        List<TurnoutManager> list = null; //接收某条请假申请范围内的排班

        for (String s : agreeLeaveno) {
            int leaveno = Integer.parseInt(s);
            //获取在请假期间范围内的排班班次
            list = arrangeService.agreeLeaveApply(leaveno);
            flag = flag || !list.isEmpty(); //判断是否有受影响的排班
            for (TurnoutManager t : list) {
                Integer arrno = t.getArrno(); //获取排班
                arrangeService.addAffectedArr(arrno); //将受影响的排班号添加进入数据库
            }
        }

        //根据是否有受影响的排班，前台做不同操作。要是非空，让用户可选择跳转到受影响界面,查看班次批准
        //请假之后收到影响的排班
        info.setFlag(flag);
        writeValue(info, response);
    }

    /**
     * 用户：主管
     * 功能：查看受影响的（被请假的）排班
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */

    public void checkAffected(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取受影响的（被请假的）排班编号
        List<Integer> list = arrangeService.showAllAffectedArr();
        ArrayList<TurnoutManagerFront> listDetails = new ArrayList<>();

        //查询被影响的排班的具体信息
        for (Integer arrno : list) {
            //获取到了排班的具体信息
            TurnoutManagerFront t = arrangeService.showArrByArrno(arrno);
            //放到list中
            listDetails.add(t);
        }
        this.listAffected = listDetails;
        //送到前台
        writeValue(listDetails, response);
    }

    /**
     * 用户：主管
     * 功能：退出请假影响班次重排界面
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void exitCheckAffected(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int i = arrangeService.deleteAllAffectedArr();
        writeValue(i, response);
    }


    /**
     * 用户：主管
     * 功能：不同意批准员工的请假申请（驳回）
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */

    public void disagreeLeaveApply(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] disagreeLeaveno = request.getParameterValues("disagreeLeaveno");
        int flag = 1;
        ResultInfo info = new ResultInfo();
        info.setFlag(true);

        for (String s : disagreeLeaveno) {
            int leaveno = Integer.parseInt(s);
            flag = arrangeService.disagreeLeaveApply(leaveno);
            if (flag == 0) {
                info.setFlag(false);
            }
        }
        writeValue(info, response);
    }

    /**
     * 用户：主管
     * 功能：同意批准员工的销假申请
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void agreeBackApply(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String[] agreeBackno = request.getParameterValues("agreeBackno");
        boolean flag = true;
        ResultInfo info = new ResultInfo();

        List<TurnoutManager> list = null; //接收某条销假申请范围内的排班

        //从list中取出需要批准的销假编号leaveno, 进行销假
        for (String s : agreeBackno) {
            int leaveno = Integer.parseInt(s);
            //获取在请假期间范围内的排班班次
            int i = arrangeService.agreeBackApply(leaveno);
            if (i == 0) flag = false;
        }

        this.showBackApplyToInte(request, response);
    }

    /**
     * 用户：主管
     * 功能：同意批准员工的加班申请
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void agreeOverApply(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String[] agreeOverno = request.getParameterValues("agreeOverno");
        boolean flag = true;
        ResultInfo info = new ResultInfo();

        for (String s : agreeOverno) {
            int overno = Integer.parseInt(s);
            //获取在请假期间范围内的排班班次
            int i = arrangeService.agreeOverApply(overno);
            if (i == 0) flag = true;
        }

        info.setFlag(flag);
        writeValue(info, response);
    }

    /**
     * 用户：主管
     * 功能：不同意批准员工的加班申请（驳回）
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */

    public void disagreeOverApply(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] disagreeOverno = request.getParameterValues("disagreeOverno");
        int flag = 1;
        ResultInfo info = new ResultInfo();
        info.setFlag(true);

        for (String s : disagreeOverno) {
            int overno = Integer.parseInt(s);
            flag = arrangeService.disagreeOverApply(overno);
            if (flag == 0) {
                info.setFlag(false);
            }
        }
        writeValue(info, response);
    }

    /**
     * 用户：主管
     * 功能：增加排班
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void addANewArr(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //先获取前端传来的数据
        String empno = request.getParameter("empno");
        String empstart = request.getParameter("empstart");
        String empend = request.getParameter("empend");
        String isoverStr = request.getParameter("isover");

        //获取当前排班的用户
        User user = (User) request.getSession().getAttribute("user");
        boolean isover = "1".equals(isoverStr);

        //封装
        TurnoutManager turnoutManager = new TurnoutManager();
        turnoutManager.setEmpno(empno);
        turnoutManager.setStart(Timestamp.valueOf(empstart));
        turnoutManager.setEnd(Timestamp.valueOf(empend));
        turnoutManager.setIsover(isover);
        turnoutManager.setIstemporary(false);

        //传到Service干活
        int i = arrangeService.addNewArrangement(turnoutManager);

        ResultInfo info = new ResultInfo();
        if (i == 0) {
            info.setFlag(false);
        } else {
            info.setFlag(true);
        }
        writeValue(info, response);
    }

    /**
     * 用户：经理
     * 功能：创建临时加班活动
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void addTempArr(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //先获取前端传来的数据
        String empstart = request.getParameter("empstart");
        String empend = request.getParameter("empend");

        //传到Service干活
        int i = arrangeService.addTempArr(empstart, empend);

        ResultInfo info = new ResultInfo();
        if (i == 0) {
            info.setFlag(false);
        } else {
            info.setFlag(true);
        }
        writeValue(info, response);
    }

    /**
     * 用户：主管
     * 功能：批量添加加班活动
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void addANewArrByWhole(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //先获取前端传来的数据
        String dstartDay = request.getParameter("dstartDay");
        String dendDay = request.getParameter("dendDay");
        String sstart = request.getParameter("sstart");
        String eend = request.getParameter("eend");
        String isoverStr = request.getParameter("isover");
        String[] selEmpno = request.getParameterValues("selEmpno");
        //添加是否成功的标志
        ResultInfo info = new ResultInfo();
        info.setFlag(true);

        for (String empno : selEmpno) {
            //用自己写的TimeUtils包获取传入的天，返回"yyyy-mm-dd"字符串组成的list
            List<String> list = TimeUtils.getDaysBetween(dstartDay, dendDay);
            for (String day : list) {
                //每天工作开始的时间
                String empstart = day + " " + sstart;
                String empend = day + " " + eend;
                boolean isover = "1".equals(isoverStr);
                //封装
                TurnoutManager turnoutManager = new TurnoutManager();
                turnoutManager.setEmpno(empno);
                turnoutManager.setStart(Timestamp.valueOf(empstart));
                turnoutManager.setEnd(Timestamp.valueOf(empend));
                turnoutManager.setIsover(isover);
                turnoutManager.setIstemporary(false);

                //传入service
                int i = arrangeService.addNewArrangement(turnoutManager);
                if (i == 0) info.setFlag(false);
            }
        }
        writeValue(info, response);
    }


    /**
     * 用户：员工
     * 功能：增加请假申请
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void addOverApply(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //先获取前端传来的数据
        String empno = request.getParameter("empno");
        String empstart = request.getParameter("empstart");
        String empend = request.getParameter("empend");
        String reason = request.getParameter("reason");

        //封装
        OverApply overApply = new OverApply();
        overApply.setEmpno(empno);
        overApply.setStart(Timestamp.valueOf(empstart));
        overApply.setEnd(Timestamp.valueOf(empend));
        overApply.setReason(reason);

        //传到Service干活
        int i = arrangeService.addNewOverApply(overApply);
        ResultInfo info = new ResultInfo();
        if (i == 0) {
            info.setFlag(false);
        } else {
            info.setFlag(true);
            while (true) {
                synchronized (objLock) {
                    //添加成功之后让count=0，让主管能收到通知
                    count = 0;
                    break;
                }
            }
        }
        writeValue(info, response);
    }

    /**
     * 用户：员工
     * 功能：增加请假申请
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void addLeaveApply(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //先获取前端传来的数据
        String empno = request.getParameter("empno");
        String empstart = request.getParameter("empstart");
        String empend = request.getParameter("empend");
        String reason = request.getParameter("reason");

        //封装
        LeaveApply leaveApply = new LeaveApply();
        leaveApply.setEmpno(empno);
        leaveApply.setStart(Timestamp.valueOf(empstart));
        leaveApply.setEnd(Timestamp.valueOf(empend));
        leaveApply.setReason(reason);

        //传到Service干活
        int i = arrangeService.addNewLeaveApply(leaveApply);
        ResultInfo info = new ResultInfo();
        if (i == 0) {
            info.setFlag(false);
        } else {
            info.setFlag(true);
            while (true) {
                synchronized (objLock) {
                    //添加成功之后让count=0，让主管能收到通知
                    count = 0;
                    break;
                }
            }
        }
        writeValue(info, response);
    }

    /**
     * 用户：员工
     * 功能：销假
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void backLeaveApply(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int flag = 1;
        //接收ajax的String数组
        String[] backLeaveno = request.getParameterValues("backLeaveno");
        for (String s : backLeaveno) {
            int leaveno = Integer.parseInt(s);
            int i = arrangeService.backLeaveByLeaveno(leaveno);
            if (i == 0) flag = 0;
        }

        ResultInfo info = new ResultInfo();
        if (flag == 0) {
            info.setFlag(false);
        } else {
            info.setFlag(true);
        }
        writeValue(info, response);
    }


    /**
     * 用户：主管
     * 功能：根据排班编号修改排班信息
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void updateArrByArrno(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //先获取前端传来的数据
        String arrno = request.getParameter("arrno");
        String empstart = request.getParameter("empstart");
        String empend = request.getParameter("empend");
        String isoverStr = request.getParameter("isover");
        boolean isover = "1".equals(isoverStr);

        //封装
        TurnoutManager turnoutManager = new TurnoutManager();
        turnoutManager.setArrno(Integer.parseInt(arrno));
        turnoutManager.setStart(Timestamp.valueOf(empstart));
        turnoutManager.setEnd(Timestamp.valueOf(empend));
        turnoutManager.setIsover(isover);

        //传到Service干活
        int i = arrangeService.updateArrByArrno(turnoutManager);

        ResultInfo info = new ResultInfo();
        if (i == 0) {
            info.setFlag(false);
        } else {
            info.setFlag(true);
        }
        writeValue(info, response);
    }

    /**
     * 用户：主管
     * 功能：删除某排班活动
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void deleteArr(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int flag = 1;
        //接收ajax的String数组
        String[] deltArrno = request.getParameterValues("deltArrno");
        for (String s : deltArrno) {
            Integer arrno = Integer.parseInt(s);
            int i = arrangeService.deleteArrByArrno(arrno);
            if (i == 0) flag = 0;
        }

        ResultInfo info = new ResultInfo();
        if (flag == 0) {
            info.setFlag(false);
        } else {
            info.setFlag(true);
        }
        writeValue(info, response);
    }

    /**
     * 用户：主管
     * 功能：删除被请假影响到的排班活动
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void deleteAffectedArr(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //接收ajax的String数组
        int flag = 1;
        String[] deltArrno = request.getParameterValues("deltArrno");
        for (String s : deltArrno) {
            int arrno = Integer.parseInt(s);
            int i = arrangeService.deleteAffectedArrByArrno(arrno);
            if (i == 0) flag = 0;
        }
        this.deleteArr(request, response);
        this.checkAffected(request, response);
    }

    /**
     * 用户：员工
     * 功能：撤销请假申请
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void deleteLeaveApply(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //接收ajax的String数组
        int flag = 1;
        String[] deleteLeaveno = request.getParameterValues("deleteLeaveno");
        for (String s : deleteLeaveno) {
            Integer leaveno = Integer.parseInt(s);
            int i = arrangeService.deleteLeaveApplyByLeaveno(leaveno);
            if (i == 0) flag = 0;
        }
        ResultInfo info = new ResultInfo();
        if (flag == 1) {
            info.setFlag(true);
        } else {
            info.setFlag(false);
        }

        writeValue(info, response);
    }


    /**
     * 用户：员工
     * 功能：撤销请假申请
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void deleteOverApply(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //接收ajax的String数组
        int flag = 1;
        String[] deleteOverno = request.getParameterValues("deleteOverno");
        for (String s : deleteOverno) {
            int overno = Integer.parseInt(s);
            int i = arrangeService.deleteOverApplyByOverno(overno);
            if (i == 0) flag = 0;
        }
        ResultInfo info = new ResultInfo();
        if (flag == 1) {
            info.setFlag(true);
        } else {
            info.setFlag(false);
        }

        writeValue(info, response);
    }

    /**
     * 用户：主管
     * 功能：修改被请假影响到的排班活动
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void updateAffectedArrByArrno(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.updateArrByArrno(request, response);
        this.checkAffected(request, response);
    }

    /**
     * 用户：主管
     * 功能：导出当前工作安排第一步：将页面中的信息写到xls文件中
     */
    public void outputArrangement1(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fileName = "output.xls";  //23239283-92489-阿凡达.avi
        String fileSaveRootPath = this.getServletContext().getRealPath("/WEB-INF/upload");
        File file = new File(fileSaveRootPath + "/" + fileName);
        boolean delete = file.delete();

        String empno = request.getParameter("empno_search"); // 获取检索的员工编号
        String date_search = request.getParameter("date_search"); // 获取检索的日期

        //从session获取部门编号
        Object user = request.getSession().getAttribute("user");
        String depno = ((User) user).getDepno();

        //获取当前页面展示的信息
        List<TurnoutManagerFront> list = null;
        if ("".equals(empno) && !"all".equals(date_search)) { //一个月份所有员工
            list = arrangeService.showUsersTurnoutByMonthToInte(depno, date_search);
        } else if (!"".equals(empno) && "all".equals(date_search)) { //一个用户所有月份
            list = arrangeService.showAUserTurnoutToInte(depno, empno);
        } else if (!"".equals(empno) && !"all".equals(date_search)) { //一个用户某个月份
            list = arrangeService.showAUserTurnoutByMonthToInte(depno, empno, date_search);
        } else {
            list = arrangeService.showAllTurnoutToInte(depno);
        }

        //写入到xls文件
        FileUtils.writeExcel(fileSaveRootPath + "/" + fileName, list);
        writeValue(list, response);
    }


    /**
     * 用户：主管
     * 功能：导出当前工作安排第二步：写完的文件从服务器下载到本地
     */
    public void outputArrangement2(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String fileName = "output.xls";  //
        String fileSaveRootPath = this.getServletContext().getRealPath("/WEB-INF/upload");
        //设置响应头，控制浏览器下载该文件
        response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        //读取要下载的文件，保存到文件输入流
        FileInputStream in = new FileInputStream(fileSaveRootPath + "/" + fileName);
        //创建输出流
        OutputStream out = response.getOutputStream();
        //创建缓冲区
        byte buffer[] = new byte[1024];
        int len = 0;
        //循环将输入流中的内容读取到缓冲区当中
        while ((len = in.read(buffer)) > 0) {
            //输出缓冲区的内容到浏览器，实现文件下载
            out.write(buffer, 0, len);
        }
        //关闭文件输入流
        in.close();
        //关闭输出流
        out.close();
    }

    /**
     * 用户：主管
     * 功能：导出当前工作安排
     */
    public void inputArrangement(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //首先将文件上传到web应用制定的文件目录下
        String fileName;
        List<TurnoutManager> list;
        while (true) {
            synchronized (User.class) {
                fileName = FileUtils.receiveFile(request, "/uploadxls");
                list = FileUtils.readExcel(fileName);
                break;
            }
        }
        for (TurnoutManager turnoutManager : list) {
            arrangeService.addNewArrangement(turnoutManager);
        }
        response.sendRedirect(request.getContextPath() + "/main2_showarr.html");
    }


    /**
     * 用户：主管
     * 功能：检查返回是否有需要审批的请假或加班
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void checkApply(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //在主管用户登录的时候就已经开启一个扫描是否存在需审批的xx的线程，获取该线程执行的结果UserServlet.isApply即可
        ResultInfo info = new ResultInfo();
        while (true) {
            synchronized (objLock) {
                if (count == 0) {
                    info.setFlag(UserServlet.isApply);
                    writeValue(info, response);
                    if (UserServlet.isApply) count = 1;
                }
                break;
            }
        }
    }


    /**
     * @param filename 文件的原始名称
     * @return uuid+"_"+文件的原始名称
     * @Method: makeFileName
     * @Description: 生成上传文件的文件名，文件名以：uuid+"_"+文件的原始名称
     */
    private String makeFileName(String filename) {  //2.jpg
        //为防止文件覆盖的现象发生，要为上传文件产生一个唯一的文件名
        return UuidUtil.getUuid() + "_" + filename;
    }

    /**
     * 为防止一个目录下面出现太多文件，要使用hash算法打散存储
     *
     * @param filename 文件名，要根据文件名生成存储目录
     * @param savePath 文件存储路径
     * @return 新的存储目录
     * @Method: makePath
     * @Description:
     */
    private String makePath(String filename, String savePath) {
        //得到文件名的hashCode的值，得到的就是filename这个字符串对象在内存中的地址
        int hashcode = filename.hashCode();
        int dir1 = hashcode & 0xf;  //0--15
        int dir2 = (hashcode & 0xf0) >> 4;  //0-15
        //构造新的保存目录
        String dir = savePath + "/" + dir1 + "/" + dir2;  //upload\2\3  upload\3\5
        //File既可以代表文件也可以代表目录
        File file = new File(dir);
        //如果目录不存在
        if (!file.exists()) {
            //创建目录
            file.mkdirs();
        }
        return dir;
    }


}
