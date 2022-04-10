package com.test.task;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class ScheTask {

    private static Logger logger = LoggerFactory.getLogger(ScheTask.class);
    private int num = 1;
  //  @Scheduled(cron = "*/10 * * * * *")
    @PostConstruct
    public void work() {
        JSONObject jsonObject = getJsonObject();
        isSucceed(jsonObject);
    }

    private boolean isSucceed(JSONObject jsonObject) {
        boolean bool = true;
        if (jsonObject.containsKey("errcode") && !jsonObject.getString("errcode").equals("0")) {
            bool = false;
            String errCode = jsonObject.getString("errcode");
            String errmsg = jsonObject.getString("errmsg");
            String errMean = null;
            switch (errCode) {
                case "-1":  errMean = "系统繁忙，此时请开发者稍候再试";break;
                case "40001":  errMean = "AppSecret错误或者AppSecret不属于这个公众号，请开发者确认AppSecret的正确性";break;
                case "40002":  errMean = "请确保grant_type字段值为client_credential";break;
                case "40164":  errMean = "调用接口的IP地址不在白名单中，请在接口IP白名单中进行设置。";break;
                case "89503":  errMean = "此IP调用需要管理员确认,请联系管理员";break;
                case "89501":  errMean = "此IP正在等待管理员确认,请联系管理员";break;
                case "89506":  errMean = "24小时内该IP被管理员拒绝调用两次，24小时内不可再使用该IP调用";break;
                case "89507":  errMean = "1小时内该IP被管理员拒绝调用一次，1小时内不可再使用该IP调用";break;
                default : errMean = "未知异常";break;

            }
            logger.error("获取token错误，错误码：{},  错误信息：{}, 错误码示意：{}", errCode, errmsg, errMean);
        }
        return bool;
    }

    private JSONObject getJsonObject(){
        JSONObject jsonObject = new JSONObject();
        if (num == 1) {
            jsonObject.put("errcode", "-1");
        }else if (num == 2) {
            jsonObject.put("errcode", "40001");
        }else if (num == 3) {
            jsonObject.put("errcode", "40002");
        }
        else if (num == 4) {
            jsonObject.put("errcode", "40164");
        }else if (num == 5) {
            jsonObject.put("errcode", "89503");
        }else {
            jsonObject.put("errcode", "89501");
            num = 0;
        }
        jsonObject.put("errmsg", "invalid appid");
        num ++;
        return jsonObject;
    }
}
