package com.whf.music.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * 异常工具类
 *
 * @author whf
 * @date 2023/04/21
 */
public class ExceptionUtils {

	public static String getStackTraceMessage(Throwable exception) {
		if (exception == null) {
			return null;
		}
		String failMessage = null;
		try {
			ByteArrayOutputStream buf = new ByteArrayOutputStream();
			exception.printStackTrace(new java.io.PrintWriter(buf, true));
			failMessage = buf.toString();
			buf.close();
		} catch (IOException e1) {
			failMessage = exception.getMessage();
		}
		return failMessage;
	}

	public static String getExceptionInfo(Exception e){
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		e.printStackTrace(new PrintStream(baos));
		return baos.toString();
	}
}
