package com.edu.ustb.utils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;

/**
 * @ClassName:GoodsTypeDao
 * @Description:realize the adding, find, find all of the goods' type
 * @author: Yixuan Yu
 * @ClassName: GoodsTypeDao.java
 * @date: 2019.7.8
 */
public class DBUtils {
    //用到的pstm对象(民工)
    public static PreparedStatement pstm;

    //连接池的对象
    public static DataSource dataSource;

    //操作属性文件对象
    public static Properties properties = new Properties();

    //连接池对象的实例化
    static {
        //通过文件流的方式读取属性文件
        InputStream is = DBUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
        try {
            //让属性文件对象加载文件流
            properties.load(is);
            dataSource = BasicDataSourceFactory.createDataSource(properties);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //保证线程安全的方式进行数据库访问，一个线程只绑定一个连接对象
    private static ThreadLocal<Connection> local = new ThreadLocal<Connection>();

    //打开数据库连接
    public static Connection getConnection() {
        //从线程绑定中取对象
        Connection con = local.get();
        try {
            if (con == null || con.isClosed()) {
                //从池子中取出连接对象
                con = dataSource.getConnection();
                //取出后切记绑定到线程上
                local.set(con);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return con;
    }

    //关闭数据库连接
    public static void closeAll(Connection con, PreparedStatement pstm, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (pstm != null)
                pstm.close();
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //优化后的增删改的方法
    public static int myExecuteUpdate(String sql, List<?> list) throws Exception {
        //1.打开连接并入到构建民工操作中
        //2.使用传来的参数
        //3.
        pstm = getConnection().prepareStatement(sql);
        //sql语句中可能会有？，需要对?进行赋值
        if (list != null) {
            //用for循环取出list中的每个值 ，赋值给pstm
            for (int i = 0; i < list.size(); i++) {
                //从list集合中下标为0(第一个值)取出赋给pstm对象
                pstm.setObject(i + 1, list.get(i));
            }
        }
        //4.
        return pstm.executeUpdate();

    }

    //优化后的增删改的方法
    public static ResultSet myExecuteQuery(String sql, List<?> list) throws Exception {
        //1.打开连接并入到构建民工操作中
        //2.使用传来的参数
        //3.
        pstm = getConnection().prepareStatement(sql);
        //sql语句中可能会有？，需要对?进行赋值
        if (list != null) {
            //用for循环取出list中的每个值 ，赋值给pstm
            for (int i = 0; i < list.size(); i++) {
                //从list集合中下标为0(第一个值)取出赋给pstm对象
                pstm.setObject(i + 1, list.get(i));
            }
        }
        //4.
        return pstm.executeQuery();

    }


}
