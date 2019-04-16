package com.kzq.advance.common.advanced.util;


import com.kzq.advance.common.advanced.model.SNSUserInfo;
import com.kzq.advance.common.advanced.model.WeixinOauth2Token;
import com.kzq.advance.common.util.CommonUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.List;

/**  
*   
* 项目名称：wechatapi  
* 类名称：OAuthUtil  
* 类描述：OAuth授权工具 
* 创建人：WQ  
* 创建时间：2014-3-6 下午9:40:30  
* @version       
*/
public class OAuthUtil extends CommonUtil {
	/**
	 * 1.通过code换取网页授权access_token
	 * 
	 * @param appId  公众号的唯一标识
	 * @param appSecret 公众号的appsecret
	 * @param code 填写第一步获取的code参数
	 * @return WeixinOauth2Token
	 */
	public static WeixinOauth2Token getOauth2AccessToken(String appId,
														 String appSecret, String code) {
		WeixinOauth2Token wat=null;
		String requestUrl=OAUTH2_ACCESSTOKEN_URL.replace("APPID", appId).replace("SECRET", appSecret).replace("CODE", code);
		/*String re="http://api.weixin.qq.com/sns/oauth2/access_token?appid=wx13c0a227486f7e64&secret=864e16284d38c05c62cddc1be000351e&code=0011d88ec32c1ab93fcd8dfa2e138b4e&grant_type=authorization_code";*/
		System.err.println(requestUrl);
		//获取网页授权凭证
		JSONObject jsonObject = httpRequest(requestUrl, "GET", null);  
		if (null != jsonObject) {
			try {
				wat=new WeixinOauth2Token();
				wat.setAccessToken(jsonObject.getString("access_token"));
				System.err.println(jsonObject.getString("access_token"));
				wat.setExpiresIn(jsonObject.getInt("expires_in"));
				wat.setRefreshToken(jsonObject.getString("refresh_token"));
				wat.setOpenId(jsonObject.getString("openid"));
				wat.setScope(jsonObject.getString("scope"));
			} catch (Exception e) {
				wat=null;
				int errorCode=jsonObject.getInt("errcode");
				String errorMsg=jsonObject.getString("errmsg");
				log.error("获取网页授权凭证失败 errcode:{} errmsg:{}",errorCode,errorMsg);
			}
		}
		return wat;
	}
	
	
	/**
	 * 2.刷新access_token
	 * 
	 * @param appId 公众号的唯一标识 
	 * @param refreshToken 通过access_token获取到的refresh_token参数 
	 * @return
	 */
	public static WeixinOauth2Token refreshOauth2Token(String appId,
			String refreshToken) {
		WeixinOauth2Token wat=null;
		String requestUrl=REFRESH_ACCESSTOKEN_URL.replace("APPID", appId).replace("REFRESH_TOKEN", refreshToken);
		//获取网页授权凭证
		JSONObject jsonObject = httpRequest(requestUrl, "GET", null);  
		if (null != jsonObject) {
			try {
				wat=new WeixinOauth2Token();
				wat.setAccessToken(jsonObject.getString("access_token"));
				wat.setExpiresIn(jsonObject.getInt("expires_in"));
				wat.setRefreshToken(jsonObject.getString("refresh_token"));
				wat.setOpenId(jsonObject.getString("openid"));
				wat.setScope(jsonObject.getString("scope"));
			} catch (Exception e) {
				wat=null;
				int errorCode=jsonObject.getInt("errcode");
				String errorMsg=jsonObject.getString("errmsg");
				log.error("刷新网页授权凭证失败 errcode:{} errmsg:{}",errorCode,errorMsg);
			}
		}
		return wat;
	}
	
	/**
	 * 3.拉取用户信息(需scope为 snsapi_userinfo)
	 * 
	 * @param accessToken 网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同
	 * @param openId 用户的唯一标识
	 * @return SNSUserInfo
	 */
	@SuppressWarnings({ "deprecation", "unchecked" })
	public static SNSUserInfo getSNSUserInfo(String accessToken, String openId) {
		SNSUserInfo sui=null;
		String requestUrl=OAUTH2_USERINFO_URL.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
		// 通过网页授权获取用户信息
		JSONObject jsonObject=httpRequest(requestUrl, "GET", null);
		if (null!=jsonObject) {
			try {
				sui=new SNSUserInfo();
				// 用户标识
				sui.setOpenId(jsonObject.getString("openid"));
				// 昵称
				sui.setNickname(jsonObject.getString("nickname"));
				// 性别(1是男性，2是女鞋，3是未知)
				sui.setSex(jsonObject.getInt("sex"));
				// 用户所在国家
				sui.setCountry(jsonObject.getString("country"));
				// 用户所在省份
				sui.setProvince(jsonObject.getString("province"));
				// 用户所在城市
				sui.setCity(jsonObject.getString("city"));
				// 用户头像
				sui.setHeadImgUrl(jsonObject.getString("headimgurl"));
				// 用户特权信息
				sui.setPrivilegeList(JSONArray.toList(jsonObject.getJSONArray("privilege"),List.class));
			} catch (Exception e) {
				sui=null;
				int errorCode=jsonObject.getInt("errcode");
				String errorMsg=jsonObject.getString("errmsg");
				log.error("获取用户信息失败 errcode:{} errmsg:{}",errorCode,errorMsg);
			}
		}
		return sui;
	}
	
	
	public static void main(String[] args) {
		WeixinOauth2Token weixinOauth2Token=getOauth2AccessToken("wx13c0a227486f7e64",
				"864e16284d38c05c62cddc1be000351e", "0011d88ec32c1ab93fcd8dfa2e138b4e");
		System.err.println("weixintoken是："+weixinOauth2Token);
		String accessToken=weixinOauth2Token.getAccessToken();
		System.err.println("accestoken是"+accessToken);
		String openId=weixinOauth2Token.getOpenId();
		System.err.println("openId是"+openId);
		SNSUserInfo snsUserInfo=getSNSUserInfo(accessToken, openId);
		System.err.println("snsUserInfo是"+snsUserInfo);
		
	}
}
