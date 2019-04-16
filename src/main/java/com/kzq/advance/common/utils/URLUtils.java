package com.kzq.advance.common.utils;


import org.apache.commons.lang.StringUtils;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * url处理工具类
 * @author L.cm
 */
public class URLUtils extends org.springframework.web.util.UriUtils {

	/**
	 * url 编码
	 * @param source url
	 * @param charset 字符集
	 * @return 编码后的url
	 */
	public static String encodeURL(String source, Charset charset) {
		try {
			return URLUtils.encode(source, charset.name());
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 解析地址
	 * @author lin
	 * @param address
	 * @return
	 */
	public static List<Map<String,String>> addressResolution(String address){
		String regex="(?<province>[^省]+自治区|.*?省|.*?行政区|.*?市)(?<city>[^市]+自治州|.*?地区|.*?行政单位|.+盟|市辖区|.*?市|.*?县)(?<county>[^县]+县|.+区|.+市|.+旗|.+海域|.+岛)?(?<town>[^区]+区|.+镇)?(?<village>.*)";
		Matcher m=Pattern.compile(regex).matcher(address);
		String province=null,city=null,county=null,town=null,village=null;
		List<Map<String,String>> table=new ArrayList<Map<String,String>>();
		Map<String,String> row=null;
		while(m.find()){
			row=new LinkedHashMap<String,String>();
			province=m.group("province");
			row.put("province", province==null?"":province.trim());
			city=m.group("city");
			row.put("city", city==null?"":city.trim());
			county=m.group("county");
			row.put("county", county==null?"":county.trim());
			town=m.group("town");
			row.put("town", town==null?"":town.trim());
			village=m.group("village");
			row.put("village", village==null?"":village.trim());
			table.add(row);
		}
		return table;
	}

	/**
	 * 解析姓名，电话和地址
	 * @param dealAddress
	 */
	public static Map<String,String> addressSplit(String dealAddress){
		String[] part=dealAddress.split(",");
		Map<String,String> row=new LinkedHashMap<String,String>();
		if(part.length<2){

			part=dealAddress.split(" ");
		}
			//收件人姓名
			String name=part[0].trim();
			if (name.contains(":")){

				String [] nameArray=name.split(":");
				if (nameArray.length>1&&StringUtils.isNotBlank(nameArray[1])){
					name=nameArray[1];
				}else {
					name=nameArray[0];

				}

			}
			//收件人电话 地址分割
		    String telephone="";
		    String address="";
		    String code="";

		for (int i=1;i<part.length;i++){
			    String p=part[i].trim();
				String regex="[0-9]+[-]?[0-9]+";
				String re="[0-9]+";

				Matcher m=Pattern.compile(regex).matcher(p);
				if(m.matches()){
					telephone=telephone+p+" ";
				}else {
					address=p;
					if (i+1<part.length){
						code=part[i+1];

						if(!Pattern.compile(re).matcher(code).matches()){
							//还是地址
							address=address+","+code;
							code="";
						}
					}
					break;
				}


			}
		if(StringUtils.isNotBlank(name)){

			row.put("name",name);
			row.put("telephone",telephone.trim());
			row.put("address",address);
			row.put("code",code);

		}


		return row;


	}



		public static void main(String[] args) {

		Map<String,String> address=addressSplit("方周亁,18772202069,湖北省 十堰市 茅箭区 鸳鸯乡 湖北省十堰市茅箭区和谐大道和谐小区1号楼二单元13一02,442500");
		System.out.println("提取省："+address);

	}




}
