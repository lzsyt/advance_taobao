package com.kzq.advance.common.advanced.util;


import com.kzq.advance.common.advanced.model.ModelNotice;
import com.kzq.advance.common.advanced.model.TemplateData;
import com.kzq.advance.common.util.CommonUtil;
import com.kzq.advance.common.utils.JSONUtils;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**  
*   
* 项目名称：wechatapi  
* 类名称：SendCustomMessage  
* 类描述：发送客服消息工具 
* 创建人：WQ  
* 创建时间：2014-3-7 上午10:37:08  
* @version       
*/
public class SendCustomMessage extends CommonUtil {
	/**
	 * 发送客服消息方法
	 * 
	 * @param accessToken 接口访问凭证
	 * @param jsonMsg json格式客服消息
	 * @return true|false
	 */
	public static boolean sendCustomMessage(String accessToken,String jsonMsg) {
		log.info("消息内容：{}",jsonMsg);
		boolean result=false;
		String requestUrl=SEND_CUSTOM_URL.replace("ACCESS_TOKEN", accessToken);
		// 发送客服消息
		JSONObject jsonObject=httpRequest(requestUrl, "POST", jsonMsg);
		if (null!=jsonObject) {
			int errorCode=jsonObject.getInt("errcode");
			String errorMsg=jsonObject.getString("errmsg");
			if (0 == errorCode) {
				result=true;
				log.info("发送客服消息成功 errCode:{} errormsg:{} ",errorCode,errorMsg);
			}else {
				log.error("发送客服消息失败 errCode:{} errormsg:{} ",errorCode,errorMsg);
			}
		}
		return result;
	}

	/**
	 * 发送模板消息
	 *
	 * @param accessToken 接口访问凭证
	 *
	 * @return true | false
	 */
	public static boolean sendModelMessage(ModelNotice notice, String accessToken) {

		boolean result = false;
		// 拼接请求地址
		String requestUrl = NOTICE_CUSTOMER_URL.replace("ACCESS_TOKEN", accessToken);
		String jsonMsg=JSONUtils.toJSONString(notice);
		System.out.println(jsonMsg);
		// 发送客服消息
	JSONObject jsonObject = CommonUtil.httpRequest(requestUrl, "POST", jsonMsg);


		if (null != jsonObject) {
			int errorCode = jsonObject.getInt("errcode");
			String errorMsg = jsonObject.getString("errmsg");
			if (0 == errorCode) {
				result = true;
				log.info("模板消息发送成功 errcode:{} errmsg:{}", errorCode, errorMsg);
			} else {
				log.error("模板消息发送失败 errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}

		return result;
	}

	/**
	 *发送发货通知
	 * {{first.DATA}}
	 * 订单编号： {{OrderSn.DATA}}
	 * 订单状态： {{OrderStatus.DATA}}
	 * {{remark.DATA}}
	 *
	 * 模板ID：f1dA4ZnTe5VyxfFCoddbauJ8FhBt_EJba09muWEcYBw
	 */
public static void sendNotice(String title,String shopNum,String status,String other,String toUser) {
	// 获取接口访问凭证
	String accessToken=getAccessToken("wx8148352aa79f60c7", "e55265afba1663a40388496d39641cb9").getAccesstoken();
	ModelNotice notice=new ModelNotice();
	notice.setTouser(toUser);
	notice.setTemplate_id("f1dA4ZnTe5VyxfFCoddbauJ8FhBt_EJba09muWEcYBw");
	String url="http://www.150ah.com";
	notice.setUrl(url);
	Map<String,TemplateData> mapdata = new HashMap<>();

	TemplateData first=new TemplateData();
	first.setValue(title);
	first.setColor("#173177");
	mapdata.put("first",first);
	TemplateData OrderSn=new TemplateData();
	OrderSn.setValue(shopNum);
	OrderSn.setColor("#173177");
	mapdata.put("OrderSn",OrderSn);
	TemplateData orderStatus=new TemplateData();
	orderStatus.setValue(status);
	orderStatus.setColor("#173177");
	mapdata.put("orderStatus",orderStatus);
	TemplateData remark=new TemplateData();
	remark.setValue(other);
	remark.setColor("#173177");

	mapdata.put("remark",remark);

	notice.setData(mapdata);
	sendModelMessage(notice,accessToken);

}
	public static void sendNotice(String shopNum,String status,String other,String toUser) {
		sendNotice("发货提醒", shopNum, status, other, toUser);
	}


	public static void main(String[] args) {
	sendNotice("shopnum78q7e8r78q","未发货","发货地址：江苏省无锡市\n收件人：张工","oUbRxv8PBPfrsUSbI4G5Sy1xyoX0");
		// 获取接口访问凭证
	/*	String accessToken=getAccessToken("wx8148352aa79f60c7", "e55265afba1663a40388496d39641cb9").getAccesstoken();
		ModelNotice notice=new ModelNotice();
		notice.setTouser("oUbRxv8PBPfrsUSbI4G5Sy1xyoX0");
		notice.setTemplate_id("CT9I0bGVflIf_UaFrE8PMhyYXCKbCUyz180glCA1qao");
		String url="http://www.150ah.com";
		notice.setUrl(url);
		Map<String,TemplateData> mapdata = new HashMap<>();

		TemplateData first=new TemplateData();
		first.setValue("测试模板标题");
		first.setColor("#173177");
		mapdata.put("first",first);
		TemplateData delivername=new TemplateData();
		delivername.setValue("申通快递");
        delivername.setColor("#173177");
		mapdata.put("delivername",delivername);
		TemplateData ordername=new TemplateData();
		ordername.setValue("testST0321908989");
        ordername.setColor("#173177");
		mapdata.put("ordername",ordername);
		TemplateData remark=new TemplateData();
		remark.setValue("需要发货啦");
		remark.setColor("#173177");
		mapdata.put("remark",remark);
		notice.setData(mapdata);
		sendModelMessage(notice,accessToken);*/
	}
}
