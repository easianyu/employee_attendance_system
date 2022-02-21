package com.edu.ustb.servlet;

import com.edu.ustb.entities.FaceTool;
import com.edu.ustb.entities.User;
import com.edu.ustb.service.CompanyService;
import com.edu.ustb.service.impl.CompanyServiceImpl;
import com.edu.ustb.utils.FaceSpot;
import com.edu.ustb.vo.ResultInfo;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/management_war_exploded/face/*")
public class FaceServlet extends BaseServlet {
    CompanyService companyService = new CompanyServiceImpl();

    /**
     * 用户：员工
     * 功能：人脸打卡：人脸信息检测
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void faceTest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取前端的打卡信息
        String img = request.getParameter("img");
        int arrno = Integer.parseInt(request.getParameter("arrno")); // 打卡的排班编号
        String recordTime = request.getParameter("recordTime"); //checkin时间
        String checkfor = request.getParameter("checkfor"); // 上班还是下班打卡

        //获取打卡人资料
        User user = (User)request.getSession().getAttribute("user");
        String empno = user.getEmpno();

        //进行图片比对，返回true说明比对成功
        boolean flag = false;
        try {
            flag = FaceSpot.isPass(img, "face", empno);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ResultInfo info = new ResultInfo();
        info.setFlag(flag);
        if (!flag) {// 不是本人，打卡失败
            writeValue(info, response);
        } else { //是本人，打卡成功
            int i = companyService.userCheckinByImg(arrno, checkfor, recordTime);
            writeValue(info, response);
        }
    }
}
