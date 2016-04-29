package com.sixmac.pay;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

public class PayRespUtil {

	public static String toStringFromReq(HttpServletRequest request) {
		
		String msg = "";
		StringBuffer buffer = new StringBuffer();
		try {
			BufferedReader reader = request.getReader();
			while((msg = reader.readLine()) != null) {
				buffer.append(msg);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}
}
