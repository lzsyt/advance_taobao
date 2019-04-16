package com.kzq.advance.common.utils;

import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

public class IpAdrressUtil {
    private static final Logger LOGGER = LogManager.getLogger(IpAdrressUtil.class);

    /**
     * 获取Ip地址
     * @param request
     * @return
     */
    public static String getIpAdrress(HttpServletRequest request) {
        String Xip = request.getHeader("X-Real-IP");
        String XFor = request.getHeader("X-Forwarded-For");
        LOGGER.info("xip:"+Xip);
        LOGGER.info("XFor:"+XFor);

        Map<String,Object> result=new HashMap<String,Object>();
        if(StringUtils.isNotBlank(XFor) && !"unKnown".equalsIgnoreCase(XFor)){
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = XFor.indexOf(",");
            if(index != -1){
                return XFor.substring(0,index);
            }else{
                return XFor;
            }
        }
        XFor = Xip;
        if(StringUtils.isNotBlank(XFor) && !"unKnown".equalsIgnoreCase(XFor)){
            return XFor;
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getRemoteAddr();
        }
        return XFor;

    }
    public  static Map<String,String> findRealAddress(HttpServletRequest request){
        String ip=getIpAdrress(request);
        HttpSession session= request.getSession();
        Map<String,String> addressMap=new HashMap<String,String>();
        if (StringUtils.isBlank(ip)){
            return addressMap;

        }
      else if (ip.equals("127.0.0.1")){
            addressMap.put("ip",ip);
            addressMap.put("address","本地");
        }else {
            String url="http://ip.taobao.com/service/getIpInfo.php";
            String rs=HttpUtils.sendPost(url,"ip="+ip);
            if (StringUtils.isNotBlank(rs)){
                JSONObject result= JSONObject.fromObject(rs);

                JSONObject data=JSONObject.fromObject(result.get("data"));
                //country":"中国","area":"","region":"湖南","city":"长沙",
                String address="";
                if (data.get("region")==null){
                    address= String.valueOf(data.get("country"));

                }else {
                    address= String.valueOf(data.get("region"))+ String.valueOf(data.get("city"));}

                addressMap.put("ip",ip);
                addressMap.put("address",address);
            }


        }

        return addressMap;
    }
    public static void main(String [] args)
    {
        String url="http://ip.taobao.com/service/getIpInfo.php?ip= 175.13.240.137";

        JSONObject result= HttpUtils.sendGet(url,"");
        //country":"中国","area":"","region":"湖南","city":"长沙",
        String address=result.toString();
        System.out.println(address);
    }
}
