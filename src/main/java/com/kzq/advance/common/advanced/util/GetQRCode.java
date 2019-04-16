package com.kzq.advance.common.advanced.util;


import com.kzq.advance.common.util.CommonUtil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class GetQRCode extends CommonUtil {
	/**
	 * 通过ticket换取二维码
	 * 
	 * @param ticket 获取的二维码ticket
	 * @param savePath 保存路径
	 * @return String
	 */
	public static String getQRCode(String ticket,String savePath) {
		String filePath=null;
		String requestUrl=GET_QRCODE_URL.replace("TICKET", urlEncodeUTF8(ticket));
		try {
			URL url=new URL(requestUrl);
			HttpURLConnection conn=(HttpURLConnection)url.openConnection();
			conn.setDoInput(true);
			conn.setRequestMethod("GET");
			if (!savePath.endsWith("/")) {
				savePath+="/";
			}
			// 将ticket作为文件名
			filePath=savePath+ticket+".jpg";
			// 将微信服务器返回的输入流写入文件
			BufferedInputStream bis=new BufferedInputStream(conn.getInputStream());
			FileOutputStream fos=new FileOutputStream(new File(filePath));
			byte[] buf=new byte[8096];
			int size=0;
			while ((size = bis.read(buf)) != -1)
				fos.write(buf, 0, size);
			fos.close();
			bis.close();
			conn.disconnect();
			log.info("根据ticket换取二维码成功，filePath="+filePath);
		} catch (Exception e) {
			filePath=null;
			log.error("根据ticket换取二维码失败:{}",e);
		}
		return filePath;
	}
	
	public static void main(String[] args) {
		//String ticket="gQHN7zoAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL0pFTUxKVDdsS1Q1UWFOeGtvbTJ3AAIEIn4ZUwMECAcAAA==";
		String ticket="gQGx8DoAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL1JrTlIyajNsZ2o3NzlyNXFfRzJ3AAIEGKUZUwMEPAAAAA==";
		String savePath="D:/db";
		// 根据ticket换取二维码
		getQRCode(ticket, savePath);
	}
	
}
