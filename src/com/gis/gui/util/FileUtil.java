package com.gis.gui.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class FileUtil {
	private static Info info;

	static {
		info = Info.getInstance();
	}

	// ��ȡ�ļ���չ��
	public static String getExt(String name) {
		int index = name.lastIndexOf(".");
		if (index < 0) {
			return "";
		}
		return name.substring(index + 1);
	}

	// �����ļ�
	public static void copyFiles(File source, File destination) {
		FileChannel in = null;
		FileChannel out = null;
		try {
			in = new FileInputStream(source).getChannel();
			File outFile = destination;
			out = new FileOutputStream(outFile).getChannel();
			in.transferTo(0, in.size(), out);
		} catch (Exception e) {
			info.warn("������ͼ�ļ�ʧ��");
			e.printStackTrace();
		} finally {
			try {
				if (in != null)
					in.close();
				if (out != null)
					out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
}
