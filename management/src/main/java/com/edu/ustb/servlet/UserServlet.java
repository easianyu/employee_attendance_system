package com.edu.ustb.servlet;

import com.edu.ustb.entities.User;
import com.edu.ustb.service.ArrangeService;
import com.edu.ustb.service.UserService;
import com.edu.ustb.service.impl.ArrangeServiceImpl;
import com.edu.ustb.service.impl.UserServiceImpl;
import com.edu.ustb.utils.FileUtils;
import com.edu.ustb.utils.MailUtils;
import com.edu.ustb.vo.ResultInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

@WebServlet("/management_war_exploded/user/*")
public class UserServlet extends BaseServlet {
    private UserService userService = new UserServiceImpl();
    private ArrangeService arrangeService = new ArrangeServiceImpl();
    public static boolean isApply;//数据库中是否有需要审批的请假加班等的标志为，1表示有


    //当前是否有用户登录的标志
    private boolean isLogin;

    //当前是否有主管登录的标志
    private boolean isInteLogin;

    //锁对象
    private final Object objLock = new Object();


    /**
     * 功能：根据邮箱发送验证码
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void checkCode(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取邮箱号码
        String email = request.getParameter("empemail");

        //形成4位随机数
        Random rd = new Random();
        int code = rd.nextInt(8999) + 1000;
        String codeStr = String.valueOf(code);

        //将验证码写回到session中，以便点击注册的时候验证
        request.getSession().setAttribute("codeStr", codeStr);

        //定义写回的变量
        ResultInfo info = new ResultInfo();

        //发送邮件
        try {
            MailUtils.sendMail(email, "您注册员工考勤系统的验证码为：" + codeStr, "注册验证码");
        } catch (Exception e) {
            //发送失败就返回
            e.printStackTrace();
            info.setFlag(false);
            writeValue(info, response);
        }

        info.setFlag(true);
        writeValue(info, response);
    }


    /**
     * 功能：判断注册邮箱验证码是否正确，正确就注册用户（也就是添加新用户）
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void registUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //先判断验证码是否正确
        String codeStr = (String) request.getSession().getAttribute("codeStr");
        String subcheckcode = request.getParameter("subcheckcode");

        ResultInfo info = new ResultInfo();
        if (!codeStr.equals(subcheckcode)) { //验证码为空或者不相等
            info.setFlag(false);
            writeValue(info, response);
        } else {
            request.getSession().removeAttribute("codeStr");//把不需要的验证码丢弃
            info.setFlag(true);
            this.addANewUser(request, response);
        }
    }

    public void uploadUserImg(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User user = (User) request.getSession().getAttribute("user");

        //从空间清理的线程获取锁之后才能上传图片，不然会被清理掉
        while (true) {
            synchronized (User.class) {
                //把照片上传到web应用的某个文件夹下
                String fileName = FileUtils.receiveFile(request, "/userimg");

                //获取当前用户的工号
                String empno = user.getEmpno();

                //将该工号的用户进行人脸识别功能的注册（上传人脸照片到百度ai的库中）
                FileUtils.registImg(fileName, empno);

                //释放锁，让空间清理的线程去清理
                break;
            }
        }

        //获取当前员工的职位，以便于上传照片完成后
        String empposno = user.getEmpposno();
        String url;

        if ("10".equals(empposno)) {
            url = "/main1_showinfo.html";
        } else if ("20".equals(empposno)) {
            url = "/main2_showinfo.html";
        } else {
            url = "/main3_showinfo.html";
        }


        //回到原页面
        response.sendRedirect(request.getContextPath() + url);
    }


    /**
     * 用户：所有用户
     * 功能：验证账号正确与否
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void login(final HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // get userno and pwd
        Map<String, String[]> map = request.getParameterMap();
        User user = new User();

        //获取session保存empno给后面进入每个人个人页面用
        HttpSession session = request.getSession();

        //package
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        // Service query
        final User u = userService.login(user);

        ResultInfo info = new ResultInfo();

        // judge username
        if (u == null) {
            info.setFlag(false);
            info.setErrorMsg("工号或密码错误");
        } else {
            info.setData(u);
            info.setFlag(true);
            //将登陆者放到session域中
            session.setAttribute("user", u);
            System.out.println("登录用户为：" + u);

            //设置当前是否登录的标志
            isLogin = true;

            //创建一个线程定期删除这个用户上传的文件：打卡照片，上传的excel等
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String rootPath = request.getServletContext().getRealPath("/WEB-INF");
                    while (isLogin) {
                        synchronized (User.class) {
                            File xlsFile = new File(rootPath + "/uploadxls");
                            File imgFile = new File(rootPath + "/userimg");
                            File[] files = xlsFile.listFiles();
                            if (files != null) {
                                for (File f : files) {
                                    f.delete();
                                }
                            }

                            files = imgFile.listFiles();
                            if (files != null) {
                                for (File f : files) {
                                    f.delete();
                                }
                            }
                        }
                        String name = Thread.currentThread().getName();
                        System.out.println("进行文件缓存清理" + name);

                        //设定线程运行间隔时间
                        try {
                            Thread.sleep(10000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();


            //如果登录的用户是主管，开启查询是否有需要申请的加班或请假的线程
            assert u != null;
            if ("20".equals(u.getEmpposno())) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String depno = u.getDepno();
                        ArrangeServlet.count = 0;//主管登录后肯定要开启先收到提醒
                        isInteLogin = true;
                        while (isInteLogin) {
                            String name = Thread.currentThread().getName();
                            System.out.println("扫描待申请线程开启：" + name);
                            int i = arrangeService.countUnapprovedByDep(depno);
                            UserServlet.isApply = i != 0;
                            System.out.println("当前是否有申请：" + UserServlet.isApply);
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                System.out.println(e);
                            }
                        }
                    }
                }).start();
            }
        }
        writeValue(info, response);
    }

    /**
     * 用户：员工
     * 功能：返回是否开启加班功能
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void notify(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //获取用户设置
        User user = (User) request.getSession().getAttribute("user");
        boolean isNotify = user.getIsnotify();

        ResultInfo info = new ResultInfo();
        if (isNotify) {
            info.setFlag(true);
        } else {
            info.setFlag(false);
        }
        writeValue(info, response);
    }

    /**
     * 用户：员工
     * 功能：打开或关闭提醒功能
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void changeNotify(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ckbStatus = request.getParameter("ckbStatus");
        User user = (User) request.getSession().getAttribute("user");
        if ("true".equals(ckbStatus)) {
            user.setIsnotify(true);
        } else {
            user.setIsnotify(false);
        }
        userService.updateUserInfo(user);
        writeValue(user, response);
    }

    /**
     * 用户：所有用户
     * 功能：头部显示用户类型
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void mainPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Object user = request.getSession().getAttribute("user");
        if (user != null) {
            //创建一个新的user对象用于显示职位和所属部门（原来是编号不方便显示）
            User newUser = userService.userTransfer((User) user);
            System.out.println(user);
            System.out.println(newUser);
            writeValue(newUser, response);
        }
    }

    /**
     * 用户：所有用户
     * 功能：登陆后根据用户类型进行主页定向
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void redirectUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String userType = user.getEmpposno();
        if ("10".equals(userType)) {
            response.sendRedirect(request.getContextPath() + "/main1.html");
        } else if ("20".equals(userType)) {
            response.sendRedirect(request.getContextPath() + "/main2.html");
        } else if ("30".equals(userType)) {
            response.sendRedirect(request.getContextPath() + "/main3.html");
        }
    }

    /**
     * 用户：所有用户
     * 功能：更新个人信息
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void updateUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> map = request.getParameterMap();
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        System.out.println(user);


        User userOld = (User) request.getSession().getAttribute("user");
        userOld.updateInfo(user);


        int i = userService.updateUserInfo(userOld);

        ResultInfo info = new ResultInfo();
        if (i == 0) {
            info.setFlag(false);
        } else {
            info.setFlag(true);
        }

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);
        response.setContentType("application/json; charset=utf-8");
        System.out.println(json);
        response.getWriter().write(json);
    }

    /**
     * 用户：经理
     * 功能：查看企业内所有用户
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> list = userService.showAllUsers();
        List<User> newList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            User user = list.get(i);
            user = userService.userTransfer(user);
            newList.add(i, user);
        }
        writeValue(newList, response);
    }


    /**
     * 用户：主管
     * 功能：查看部门内所有用户
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void showAllUsersToInte(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取主管所在的部门编号
        Object user = request.getSession().getAttribute("user");
        String depno = ((User) user).getDepno();

        List<User> userByDepno = userService.findUserByDepno(depno);
        writeValue(userByDepno, response);
    }

    /**
     * 用户：经理
     * 功能：查看企业内所有用户
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findSelectedUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String depno = request.getParameter("depno");
        if ("10".equals(depno)) {//选择全部的时候
            this.findAll(request, response);
        }
        List<User> list = userService.findUserByDepno(depno);
        writeValue(list, response);
    }


    /**
     * 用户：经理
     * 功能：修改用户信息
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void updateSelectedUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //找出总的用户数
        int sumUsers = Integer.parseInt(request.getParameter("sum"));

        //定义需要修改的list集合
        List<User> list = new ArrayList<>();
        User user = new User();

        ResultInfo info = new ResultInfo();
        info.setFlag(true);


        //遍历表单，找到需要修改的元素放入list中
        for (int i = 0; i < sumUsers; i++) {
            //列名,对应表单中的id，从0开始到sumUser-1，代表第0～sumUser-1行
            String col = String.valueOf(i);
            List<String> colList = new ArrayList<>();
            Collections.addAll(colList, "empno" + col,
                    "empname" + col, "emppassword" + col, "empposno" + col,
                    "empemail" + col, "empphoneno" + col, "depno" + col);
            user.setEmpno(request.getParameter(colList.get(0)));
            user.setEmpname(request.getParameter(colList.get(1)));
            user.setEmppassword(request.getParameter(colList.get(2)));
            user.setEmpposno(request.getParameter(colList.get(3)));
            user.setEmpemail(request.getParameter(colList.get(4)));
            user.setEmpphoneno(request.getParameter(colList.get(5)));
            user.setDepno(request.getParameter(colList.get(6)));
            int flag = userService.updateUserInfo(user);
            if (flag == 0) {
                info.setFlag(false);
            }
        }
        writeValue(info, response);
    }

    /**
     * 用户：经理
     * 功能：删除用户
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void deleteSelectedUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //找出总的用户数
        int sumUsers = Integer.parseInt(request.getParameter("sum"));

        //定义需要修改的list集合
        User user = new User();

        ResultInfo info = new ResultInfo();
        info.setFlag(true);
        String rawEmpno = "empno";

        //遍历表单，找到需要修改的元素放入list中
        for (int i = 0; i < sumUsers; i++) {
            //列名,对应表单中的id，从0开始到sumUser-1，代表第0～sumUser-1行
            String empno = request.getParameter(rawEmpno + String.valueOf(i));
            int flag = userService.deleteAUser(empno);
            if (flag == 0) {
                info.setFlag(false);
            }
        }
        writeValue(info, response);
    }


    /**
     * 用户：经理、员工
     * 功能：增加新用户。新用户注册
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void addANewUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> map = request.getParameterMap();
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        Object user1 = request.getSession().getAttribute("user");
        System.out.println(user);
        int flag = 0;
        ResultInfo info = new ResultInfo();
        flag = userService.addANewUser(user);
        if (flag == 1) {
            info.setFlag(true);
        } else {
            info.setFlag(false);
        }
        writeValue(info, response);
    }

    /**
     * 用户：经理
     * 功能：批量导入员工信息
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void inputUsers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fileName;
        List<User> list;
        while (true) {
            synchronized (User.class) {
                fileName = FileUtils.receiveFile(request, "/uploadxls");
                list = FileUtils.readUsersFromXls(fileName);
                break;
            }
        }

        boolean flag = true;
        if (list != null) {
            for (User user : list) {
                int i = userService.addANewUser(user);
                if (i == 0) {
                    flag = false;
                }
            }
        }
        if (flag) {
            response.sendRedirect(request.getContextPath() + "/main1_showall.html");
        }
    }


    /**
     * 用户：经理
     * 功能：查看企业内所有用户
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void exitUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        isLogin = false;
        User user = (User) request.getSession().getAttribute("user");
        if (user != null && "20".equals(user.getEmpposno())) { //如果是主管退出登录，才能把主管的登录标志改为false
            isInteLogin = false;
        }
        request.getSession().invalidate();
        response.sendRedirect(request.getContextPath());
    }


}