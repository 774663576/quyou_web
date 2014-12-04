package com.quyou.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class DownAPKServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String path = "app/quyou.apk";
		download(path, request, response);
	}

	@SuppressWarnings("deprecation")
	public HttpServletResponse download(String path,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			// path��ָ�����ص��ļ���·����
			File file = new File(request.getRealPath("/") + "/" + path);
			// ȡ���ļ�����
			String filename = file.getName();
			// ȡ���ļ��ĺ�׺����
			// String ext = filename.substring(filename.lastIndexOf(".") +
			// 1).toUpperCase();

			// ��������ʽ�����ļ���
			InputStream fis = new BufferedInputStream(new FileInputStream(file));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			// ���response
			response.reset();
			// ����response��Header
			response.addHeader("Content-Disposition", "attachment;filename="
					+ new String(filename.getBytes("utf-8"), "ISO-8859-1"));
			response.addHeader("Content-Length", "" + file.length());
			OutputStream toClient = new BufferedOutputStream(
					response.getOutputStream());
			response.setContentType("application/octet-stream");
			toClient.write(buffer);
			toClient.flush();
			toClient.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return response;
	}
}