package com.atguigu.gulimall.auth.util;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * @Auther: MR.rp
 * @Date: 2021/8/3 14:37
 * @Description:
 */

@Component
@Slf4j
public  class DysmsUtil {

    private static Client client;
    private static  String  signName; //签名
    public static  String  templateCode;// 短信验证码模板id

    @Value("${dysms.templateCode}")
    public void setMsgCode(String templateCode) {
        DysmsUtil.templateCode= templateCode;
    }

    @Autowired
    public DysmsUtil(Client client){
        this.client = client;
    }


    @Value("${dysms.signName}")
    public void setSIGN(String signName) {
        DysmsUtil.signName= signName;
    }


    /**
     * 单条手机发送信息
     * @param phoneNums
     * @param templateCode
     * @param template
     */
    public static void  sendMsg(String phoneNums, String templateCode, String  template){
        //这里为了测试只写单条手机号码的校验是否合法,在开发中如果是多个手机号可以先自己校验再传参
        if(!phoneNums.matches("^1(3\\d|4[5-9]|5[0-35-9]|6[567]|7[0-8]|8\\d|9[0-35-9])\\d{8}$")){
            log.warn("============ 手机号:"+phoneNums+"不合法,发送失败 ======");
            return;
        }
    SendSmsRequest sendSmsRequest = new SendSmsRequest()
            .setPhoneNumbers(phoneNums)
            .setSignName(signName)  //短信签名名称
            .setTemplateCode(templateCode) //短信模板ID
            .setTemplateParam(template); //变量值
        try {
        SendSmsResponse sendSmsResponse = client.sendSms(sendSmsRequest);
        SendSmsResponseBody  sendBody = sendSmsResponse.getBody();

        if(sendBody.getMessage().equals("OK")){
            log.info("========== 给:"+phoneNums+"发送短信["+template+"]成功!!! ==========");
        }

        //下面是查询信息的结果集,因为短信本身会有一定的延迟,所有在查询的时候可能会查不到或者让线程睡眠几秒再查询,这里只是贴出查询代码供大家参考
        String datetime = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String bizId = sendBody.bizId;
        QuerySendDetailsRequest querySendDetailsRequest = new QuerySendDetailsRequest()
                .setPhoneNumber(phoneNums)
                .setSendDate(datetime)
                .setBizId(bizId)
                .setPageSize(10L)  //分页
                .setCurrentPage(1L);
        QuerySendDetailsResponseBody body = client.querySendDetails(querySendDetailsRequest).getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        String msg = objectMapper.writeValueAsString(body);
        log.info("返回的body信息:"+msg);
        String message = objectMapper.writeValueAsString(sendBody);
        log.info("发送返回信息详情:"+message);
    } catch (Exception e) {
        e.printStackTrace();
        log.error(e.getMessage(),e);
    }
}

    /**
     * 获取短信验证码
     * @return 验证码
     */
    public static String getCode() {
        String code = null;
        code = (int) ((Math.random() * 9 + 1) * 100000) + "";
        log.info("验证码为["+code+"]");
        return code;
    }

    public static String getMsgCode(){
        return DysmsUtil.templateCode;
    }
}
