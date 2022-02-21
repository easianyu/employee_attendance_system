package com.edu.ustb.utils;

import com.edu.ustb.dao.UserDao;
import com.edu.ustb.dao.impl.UserDaoImpl;
import com.edu.ustb.entities.TurnoutManager;
import com.edu.ustb.entities.TurnoutManagerFront;
import com.edu.ustb.entities.User;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static com.edu.ustb.utils.FaceSpot.addUser;

public class FileUtils {

    /**
     * 从网页请求中获取并存储文件
     *
     * @param request
     * @return
     */
    public static String receiveFile(HttpServletRequest request, String subPath) {
        //得到上传文件的保存目录，将上传的文件存放于WEB-INF目录下，不允许外界直接访问，保证上传文件的安全
        String savePath = request.getServletContext().getRealPath("/WEB-INF" + subPath);
        String filePath = null;
        File file = new File(savePath);
        //判断上传文件的保存目录是否存在
        if (!file.exists() && !file.isDirectory()) {
            System.out.println(savePath + "目录不存在，需要创建");
            //创建目录
            file.mkdir();
        }
        //消息提示
        try {
            //使用Apache文件上传组件处理文件上传步骤：
            //1、创建一个DiskFileItemFactory工厂
            DiskFileItemFactory factory = new DiskFileItemFactory();
            //2、创建一个文件上传解析器
            ServletFileUpload upload = new ServletFileUpload(factory);
            //解决上传文件名的中文乱码
            upload.setHeaderEncoding("UTF-8");
            //3、判断提交上来的数据是否是上传表单的数据
            if (!ServletFileUpload.isMultipartContent(request)) {
                //按照传统方式获取数据
                return filePath;
            }
            //4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
            List<FileItem> list = upload.parseRequest(request);
            for (FileItem item : list) {
                //如果fileitem中封装的是普通输入项的数据
                if (item.isFormField()) {
                    String name = item.getFieldName();
                    //解决普通输入项的数据的中文乱码问题
                    String value = item.getString("UTF-8");
                    //value = new String(value.getBytes("iso8859-1"),"UTF-8");
                    System.out.println(name + "=" + value);
                } else {//如果fileitem中封装的是上传文件
                    //得到上传的文件名称，
                    String filename = item.getName();
                    System.out.println(filename);
                    if (filename == null || filename.trim().equals("")) {
                        continue;
                    }
                    //注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如：  c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
                    //处理获取到的上传文件的文件名的路径部分，只保留文件名部分
                    filename = filename.substring(filename.lastIndexOf("/") + 1);

                    //获取文件的扩展名，是xls就去写入
                    String extend = filename.substring(filename.lastIndexOf(".") + 1);
                    //获取item中的上传文件的输入流
                    InputStream in = item.getInputStream();
                    //创建一个文件输出流
                    filename = UuidUtil.getUuid() + filename;
                    filePath = savePath + "/" + filename;
                    FileOutputStream out = new FileOutputStream(savePath + "/" + filename);
                    //创建一个缓冲区
                    byte buffer[] = new byte[1024];
                    //判断输入流中的数据是否已经读完的标识
                    int len = 0;
                    //循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
                    while ((len = in.read(buffer)) > 0) {
                        //使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
                        out.write(buffer, 0, len);
                    }
                    //关闭输入流
                    in.close();
                    //关闭输出流
                    out.close();
                    //删除处理文件上传时生成的临时文件
                    item.delete();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filePath;
    }

    /**
     * 将排班信息写入xls文件
     *
     * @param filename
     * @param list
     */
    public static void writeExcel(String filename, List<TurnoutManagerFront> list) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("员工出勤排班信息");

        //创建标题行
        HSSFRow headRow = sheet.createRow(0);
        headRow.createCell(0).setCellValue("排班号");
        headRow.createCell(1).setCellValue("员工工号");
        headRow.createCell(2).setCellValue("员工姓名");
        headRow.createCell(3).setCellValue("部门名称");
        headRow.createCell(4).setCellValue("职位");
        headRow.createCell(5).setCellValue("打卡类型");
        headRow.createCell(6).setCellValue("日期");
        headRow.createCell(7).setCellValue("上班时间");
        headRow.createCell(8).setCellValue("下班时间");
        headRow.createCell(9).setCellValue("实际上班时间");
        headRow.createCell(10).setCellValue("实际下班时间");
        headRow.createCell(11).setCellValue("是否加班");
        headRow.createCell(12).setCellValue("是否迟到");
        headRow.createCell(13).setCellValue("是否早退");
        headRow.createCell(14).setCellValue("是否请假");
        headRow.createCell(15).setCellValue("是否是临时排班");

        for (TurnoutManagerFront turnoutManagerFront : list) {
            createCell(turnoutManagerFront, sheet);
        }

        File xlxFile = new File(filename);
        try {
            workbook.write(xlxFile);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    //写入一行
    private static void createCell(TurnoutManagerFront turnoutManagerFront, HSSFSheet sheet) {
        HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
        dataRow.createCell(0).setCellValue(turnoutManagerFront.getArrno());
        dataRow.createCell(1).setCellValue(turnoutManagerFront.getEmpno());
        dataRow.createCell(2).setCellValue(turnoutManagerFront.getEmpname());
        dataRow.createCell(3).setCellValue(turnoutManagerFront.getDepname());
        dataRow.createCell(4).setCellValue(turnoutManagerFront.getEmpposition());
        dataRow.createCell(5).setCellValue(turnoutManagerFront.getChecktypename());
        dataRow.createCell(6).setCellValue(turnoutManagerFront.getDdddate());
        dataRow.createCell(7).setCellValue(turnoutManagerFront.getStart());
        dataRow.createCell(8).setCellValue(turnoutManagerFront.getEnd());
        dataRow.createCell(9).setCellValue(turnoutManagerFront.getRlstart());
        dataRow.createCell(10).setCellValue(turnoutManagerFront.getRlend());
        dataRow.createCell(11).setCellValue(turnoutManagerFront.getIsover());
        dataRow.createCell(12).setCellValue(turnoutManagerFront.getIslate());
        dataRow.createCell(13).setCellValue(turnoutManagerFront.getIsearly());
        dataRow.createCell(14).setCellValue(turnoutManagerFront.getIsleave());
        dataRow.createCell(15).setCellValue(turnoutManagerFront.getIstemporary());
    }

    /**
     * 将排班信息读取出
     *
     * @param fileName
     * @return 排班信息list
     */
    public static List<TurnoutManager> readExcel(String fileName) throws IOException {
        if (fileName == null) return null;

        File xlsFile = new File(fileName);
        if (!xlsFile.exists()) return null;

        // 工作表
        Workbook workbook = WorkbookFactory.create(xlsFile);
        // 表个数
        int numberOfSheets = workbook.getNumberOfSheets();
//      System.out.println(numberOfSheets);
        if (numberOfSheets <= 0) return null;

        List<TurnoutManager> list = new ArrayList<>();
        //我们的需求只需要处理一个表，因此不需要遍历
        Sheet sheet = workbook.getSheetAt(0);
        // 行数
        int rowNumbers = sheet.getLastRowNum() + 1;
        //列数
        for (int row = 1; row < rowNumbers; row++) {
            Row r = sheet.getRow(row);
            String empno = r.getCell(1).toString();
            if (empno.indexOf('.') >= 0) {
                empno = empno.substring(0, empno.indexOf('.'));
            }
            String dateTemp = r.getCell(6).toString();
            String timeOn = r.getCell(7).toString();
            String timeOff = r.getCell(8).toString();
            String realTimeOn = TimeUtils.getRealTimeByMonthDay(dateTemp, timeOn);
            String realTimeOff = TimeUtils.getRealTimeByMonthDay(dateTemp, timeOff);
            boolean isover = "1".equals(r.getCell(11).toString());
            TurnoutManager turnoutManager = new TurnoutManager();
            turnoutManager.setEmpno(empno);
            turnoutManager.setStart(Timestamp.valueOf(realTimeOn));
            turnoutManager.setEnd(Timestamp.valueOf(realTimeOff));
            turnoutManager.setIsover(isover);
            turnoutManager.setIstemporary(false);
            list.add(turnoutManager);
        }
        return list;
    }

    public static void registImg(String fileName, String empno) {
        byte[] img2 = new byte[0];
        try {
            img2 = FaceSpot.FileToByte(new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String info = addUser(img2, "BASE64", empno, "face");
        System.out.println(info);
    }

    public static List<User> readUsersFromXls(String fileName) throws IOException {
        UserDao userDao = new UserDaoImpl();
        if (fileName == null) return null;

        File xlsFile = new File(fileName);
        if (!xlsFile.exists()) return null;

        // 工作表
        Workbook workbook = WorkbookFactory.create(xlsFile);
        // 表个数
        int numberOfSheets = workbook.getNumberOfSheets();
//      System.out.println(numberOfSheets);
        if (numberOfSheets <= 0) return null;

        List<User> list = new ArrayList<>();
        //我们的需求只需要处理一个表，因此不需要遍历
        Sheet sheet = workbook.getSheetAt(0);
        // 行数
        int rowNumbers = sheet.getLastRowNum() + 1;
        //列数
        for (int row = 1; row < rowNumbers; row++) {
            Row r = sheet.getRow(row);
            String empno = r.getCell(0).toString();
            if (empno.indexOf('.') >= 0) {
                empno = empno.substring(0, empno.indexOf('.'));
            }
            String empname = r.getCell(1).toString();
            String emppassword = r.getCell(2).toString();
            if (emppassword.indexOf('.') >= 0) {
                emppassword = emppassword.substring(0, emppassword.indexOf('.'));//取小数点前部分}
            }
            String empposname = r.getCell(3).toString();
            String empposno = userDao.findEmpposnoByName(empposname);

            String empemail = r.getCell(4).toString();
            String empphoneno = r.getCell(5).toString();
            if (empphoneno.indexOf('.') >= 0) {
                empphoneno = empphoneno.substring(0, empphoneno.indexOf('.'));//取小数点前部分
            }

            String depname = r.getCell(6).toString();
            String depno = userDao.findDepnoByDepname(depname);

            //封装数据
            User user = new User();
            user.setEmpno(empno);
            user.setEmpname(empname);
            user.setEmppassword(emppassword);
            user.setEmpposno(empposno);
            user.setEmpemail(empemail);
            user.setEmpphoneno(empphoneno);
            user.setDepno(depno);

            list.add(user);
        }
        return list;

    }
}
