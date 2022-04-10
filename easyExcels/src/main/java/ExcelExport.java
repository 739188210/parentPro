import com.alibaba.excel.EasyExcel;
import com.test.entity.InspectRecordExport;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ExcelExport  {

    private static String path = "C:\\Users\\miaoz\\IdeaProjects\\parentPro\\";

    public static void main(String[] args) {
        testWrite();
    }

    public static void testWrite(){
        String fileName = path + "inspectRecord" + ".xlsx";
        EasyExcel.write(fileName, InspectRecordExport.class).sheet("柱塞泵检修记录").doWrite(data());
    }

    private static List<InspectRecordExport> data(){
        List<InspectRecordExport> list = new ArrayList<InspectRecordExport>();
        for (int i = 0; i < 2; i++) {
            InspectRecordExport inspectRecordExport = new InspectRecordExport();
            inspectRecordExport.setDeviceSpec("2500Q" + i);
            inspectRecordExport.setSerialNo("JERQ22031500" + i);
            inspectRecordExport.setCode("柱塞泵2500Q00" + i);
            inspectRecordExport.setInspectTime(new Timestamp(System.currentTimeMillis()));
            list.add(inspectRecordExport);
        }
        return list;
    }
}
