package com.test.entity;


import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
@Getter
@Setter
@Data
@EqualsAndHashCode()
public class InspectRecordExport {

    @ExcelProperty(value = "柱塞泵型号", index = 0)
    private String deviceSpec;
    @ExcelProperty(value = "编号", index = 1)
    private String code;
    @ExcelProperty(value = "序号", index = 2)
    private String serialNo;

    @ExcelProperty(value = "检查日期", index = 4)
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private Timestamp inspectTime;

//    @ExcelProperty(value = "动力端序列号", index = 3)
//    private String powerSerial;
//    @ExcelProperty(value = "项目号", index = 4)
//    private String projectNo;
//    @ExcelProperty(value = "产品编号", index = 5)
//    private String productNo;
//    @ExcelProperty(value = "客户名称", index = 6)
//    private String guestName;
//    @ExcelProperty(value = "柱塞泵累计运行时间(小时)", index = 7)
//    private Long runtime;
//    @ExcelProperty(value = "动力端润滑方式", index = 8)
//    private String lubricationMode;
//    @ExcelProperty(value = "曲轴轴承型式", index = 9)
//    private String bearingType;
//    @ExcelProperty(value = "检查负责人", index = 10)
//    private String inspectorName;
//    @ExcelProperty(value = "检查日期", index = 11)
//    @DateTimeFormat("yyyy年MM月dd日HH时mm分ss秒")
//    private Timestamp inspectTime;
//    @ExcelProperty(value = "设备区域", index = 12)
//    private String deviceArea;
//    @ExcelProperty(value = "设备所在地", index = 13)
//    private String deviceAddress;
//    @ExcelProperty(value = "是否有异响", index = 14)
//    private Boolean abnormalSound;
//    @ExcelProperty(value = "异响位置及解决方案", index = 15)
//    private String soundSolution;
//    @ExcelProperty(value = "备注", index = 16)
//    private String remark;

    public String getDeviceSpec() {
        return deviceSpec;
    }

    public void setDeviceSpec(String deviceSpec) {
        this.deviceSpec = deviceSpec;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public Timestamp getInspectTime() {
        return inspectTime;
    }

    public void setInspectTime(Timestamp inspectTime) {
        this.inspectTime = inspectTime;
    }
}
