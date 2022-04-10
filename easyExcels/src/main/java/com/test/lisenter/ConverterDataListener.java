package com.test.lisenter;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.test.entity.InspectRecordExport;

import java.text.SimpleDateFormat;

public class ConverterDataListener extends AnalysisEventListener<InspectRecordExport> {
    private final SimpleDateFormat sf = new SimpleDateFormat();
    public void invoke(InspectRecordExport data, AnalysisContext context) {

        data.getInspectTime();
        System.out.println("ConverterDataListener has been use");
    }

    public void doAfterAllAnalysed(AnalysisContext context) {

    }
}
