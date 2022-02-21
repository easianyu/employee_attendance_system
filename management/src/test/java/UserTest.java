import com.edu.ustb.dao.CompanyDao;
import com.edu.ustb.dao.UserDao;
import com.edu.ustb.dao.impl.CompanyDaoImpl;
import com.edu.ustb.dao.impl.UserDaoImpl;
import com.edu.ustb.entities.Company;
import com.edu.ustb.entities.DepartmentInfo;
import com.edu.ustb.entities.User;
import com.edu.ustb.service.CompanyService;
import com.edu.ustb.service.UserService;
import com.edu.ustb.service.impl.CompanyServiceImpl;
import com.edu.ustb.service.impl.UserServiceImpl;
import com.edu.ustb.utils.FileUtils;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class UserTest {
//获取核心容器对象
ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");

@Test
public void findDepnoByNameDao() {
    UserDao userDao = (UserDao) ac.getBean("userDao");
    String depno = userDao.findDepnoByDepname("市场部门");
    System.out.println(depno);
}

@Test
public void findEmpposnoByNameDao() {
    UserDao userDao = (UserDao) ac.getBean("userDao");
    String empposno = userDao.findEmpposnoByName("员工");
    System.out.println(empposno);
}

@Test
public void getAllEmpnoDao() {
    UserDao userDao = (UserDao) ac.getBean("userDao");
    List<String> list = userDao.getAllEmpno();
    if (list != null) {
        for (String s : list) {
            System.out.println(s);
        }
    }
}


@Test
public void showAllUsersDao() {
    UserDao userDao = (UserDao) ac.getBean("userDao");
    List<User> list = userDao.showAllUsers();
    for (User user : list) {
        System.out.println(user);
    }
}

@Test
public void updateUsersDao() {
    UserDao userDao = (UserDao) ac.getBean("userDao");
    User user = new User("1024", "tests", "ttestword",
            "0", "xx@mail.com", "88", "200", true);
    int num = userDao.updateUserInfo(user);
    System.out.println(num == 1);
}

@Test
public void findByUsernoAndPasswordDao() {
    UserDao userDao = (UserDao) ac.getBean("userDao");
    String empno = "123";
    String emppassword = "123";
    User user = userDao.findByUsernoAndPassword(empno, emppassword);
    System.out.println(user);
}

@Test
public void findInfoByEmpnoDao() {
    UserDao userDao = (UserDao) ac.getBean("userDao");
    String empno = "2001";
    User use = userDao.findInfoByEmpno(empno);
    System.out.println(use);
}

@Test
public void findDepnameByDepnoDao() {
    UserDao userDao = (UserDao) ac.getBean("userDao");
    String depName = userDao.findDepnameByDepno("100");
    System.out.println(depName);
}

@Test
public void findEmppositionByEmppositionDao() {
    UserDao userDao = (UserDao) ac.getBean("userDao");
    String empPosition = userDao.findEmppositionByEmpposition("10");
    System.out.println(empPosition);
}

@Test
public void addANewUserDao() {
    UserDao userDao = (UserDao) ac.getBean("userDao");
    User user = new User("2333", "yyx2", "1",
            "10", "xx@mail.com", "88", "200", true);
    int i = userDao.addANewUser(user);
    System.out.println(i == 1);
}

@Test
public void countAllUsersDao() {
    UserDao userDao = (UserDao) ac.getBean("userDao");
    int sum = userDao.countAllUsers();
    System.out.println(sum);
}

@Test
public void deleteAUserDao() {
    UserDao userDao = (UserDao) ac.getBean("userDao");
    int sum = userDao.deleteAUser("2333");
    System.out.println(sum == 1);
}

@Test
public void findUserByDepnoDao() {
    UserDao userDao = (UserDao) ac.getBean("userDao");
    List<User> list = userDao.findUserByDepno("200");
    try {
        for (User u : list) {
            System.out.println(u);
        }
    } catch (Exception e) {
        System.out.println(e);
    }
}

@Test
public void showDepartmentInfoDao() {
    CompanyDao companyDao = new CompanyDaoImpl();
    List<DepartmentInfo> list = companyDao.showDepartmentInfo("400");
    try {
        for (DepartmentInfo u : list) {
            System.out.println(u);
        }
    } catch (Exception e) {
        System.out.println(e);
    }
}

@Test
public void countUsersByDepDao() {
    UserDao userDao = (UserDao) ac.getBean("userDao");
    int sum = userDao.countUsersByDep("200");
    System.out.println(sum);
}

@Test
public void showAllUsersService() {
    UserService userService = ac.getBean("userService", UserServiceImpl.class);
    for (User user : userService.showAllUsers()) {
        System.out.println(user);
    }
}

@Test
public void updateUsersService() {
    UserService userService = ac.getBean("userService",
            UserServiceImpl.class);
    User user = new User("1024", "service", "ttestword",
            "0", "xx@mail.com", "88", "200", true);
    int num = userService.updateUserInfo(user);
    System.out.println(num == 1);
}

@Test
public void findInfoByEmpnoService() {
    UserService userService = ac.getBean("userService", UserServiceImpl.class);
    String empno = "2001";
    User use = userService.findInfoByEmpno(empno);
    System.out.println(use);
}

@Test
public void findDepnameByDepnoService() {
    UserService userService = ac.getBean("userService", UserServiceImpl.class);
    String depno = "200";
    String depname = userService.findDepnameByDepno(depno);
    System.out.println(depname);
}

@Test
public void findEmppositionByEmppositionService() {
    UserService userService = ac.getBean("userService", UserServiceImpl.class);
    String empPosition = userService.findEmppositionByEmpposition("10");
    System.out.println(empPosition);
}

@Test
public void countAllUsersService() {
    UserService userService = ac.getBean("userService", UserServiceImpl.class);
    int sum = userService.countAllUsers();
    System.out.println(sum);
}

@Test
public void addANewUserService() {
    UserService userService = ac.getBean("userService", UserServiceImpl.class);
    User user = new User("1025", "yyxService", "1",
            "10", "xx@mail.com", "88", "200", true);
    int i = userService.addANewUser(user);
    System.out.println(i == 1);
}

@Test
public void deleteAUserService() {
    UserService userService = ac.getBean("userService", UserServiceImpl.class);
    int num = userService.deleteAUser("1025");
    System.out.println(num == 1);
}

@Test
public void findUserByDepnoService() {
    UserService userService = ac.getBean("userService", UserServiceImpl.class);
    List<User> list = userService.findUserByDepno("200");

    try {
        for (User u : list) {
            System.out.println(u);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}

@Test
public void showDepartmentInfoService() {
    CompanyService companyService = new CompanyServiceImpl();
    ArrayList<DepartmentInfo> list = companyService.showDepartmentInfo("200");
    try {
        for (DepartmentInfo u : list) {
            System.out.println(u);
        }
    } catch (Exception e) {
        System.out.println(e);
    }
}

@Test
public void testUsersXml() {
    User user = ac.getBean("user1", User.class);
    System.out.println(user);
}

@Test
public void testAnnoUserXml() {//创建对象这是注释创建对象注入
    User user = ac.getBean("annouser", User.class);
    UserDao userDao = ac.getBean("annouserDao", UserDaoImpl.class);
    System.out.println(user);
    System.out.println(userDao);
}

@Test
public void testTimeStamp() {
    Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());
    for (int i = 0; i < 99999999; i++) {
    }
    Timestamp timestamp2 = new Timestamp(System.currentTimeMillis());
    System.out.println(timestamp1);
    System.out.println(timestamp2);
    System.out.println(timestamp1.before(timestamp2));
}

@Test
public void showCmpInfoDao() {
    CompanyDao companyDao = new CompanyDaoImpl();
    List<Company> list = companyDao.showCmpInfo();
    System.out.println(list);
}

@Test
public void showCmpInfoService() {
    List<Company> list = new CompanyServiceImpl().showCmpInfo();
    System.out.println(list);
}

@Test
public void readUsersFromXls() {
    List<User> list = null;
    try {
        list = FileUtils.readUsersFromXls("/Users/jesse/Desktop/inputusers.xls");
    } catch (IOException e) {
        e.printStackTrace();
    }
    if (list != null) {
        for (User user : list) {
            System.out.println(user);
        }
    }
}

}
