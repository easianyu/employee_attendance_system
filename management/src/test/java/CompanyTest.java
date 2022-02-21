import com.edu.ustb.entities.Company;
import com.edu.ustb.entities.TurnoutManager;
import com.edu.ustb.entities.TurnoutManagerFront;
import com.edu.ustb.service.CompanyService;
import com.edu.ustb.service.impl.ArrangeServiceImpl;
import com.edu.ustb.service.impl.CompanyServiceImpl;
import com.edu.ustb.utils.FileUtils;
import com.edu.ustb.utils.TimeUtils;
import org.junit.Test;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

public class CompanyTest {

    @Test
    public void updateRlstartByarrnoDao() {
        CompanyService companyService = new CompanyServiceImpl();
        int i = companyService.userCheckinByImg(1001, "0", "2020-05-30 08:40:00");
        int j = companyService.userCheckinByImg(1001, "1", "2020-05-30 17:40:00");
        System.out.println(i);
        System.out.println(j);
    }

    @Test
    public void testGetCurrentTime() {
        Timestamp currentT = new Timestamp(System.currentTimeMillis());
        System.out.println(currentT);
    }

    @Test
    public void getStartOfDay(){
        Timestamp currentT = new Timestamp(System.currentTimeMillis());
        System.out.println(currentT);
        String start = TimeUtils.getStartOfDay(currentT.toString());
        System.out.println(start);
    }

    @Test
    public void getEndOfDay(){
        Timestamp currentT = new Timestamp(System.currentTimeMillis());
        System.out.println(currentT);
        String end = TimeUtils.getEndOfDay(currentT.toString());
        System.out.println(end);
    }

    @Test
    public void writeExcel(){
        FileUtils fileUtils = new FileUtils();
        ArrangeServiceImpl arrangeService = new ArrangeServiceImpl();
        List<TurnoutManagerFront> list = arrangeService.showAllTurnoutToInte("200");
        fileUtils.writeExcel("/Users/jesse/Desktop/file_test.xls",list);
    }

    @Test
    public void getDaysBetween(){
        List<String> daysBetween = TimeUtils.getDaysBetween("2020-06-09", "2020-06-25");
        for (String s : daysBetween) {
            System.out.println(s);
        }
    }

    @Test
    public void readExcelTest(){
        try {
            List<TurnoutManager> list = FileUtils.readExcel("/Users/jesse/Desktop/output (2).xls");
            for (TurnoutManager turnoutManager : list) {
                System.out.println(turnoutManager);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
