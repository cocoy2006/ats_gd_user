package android.testing.system.servlet.testcases;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import android.testing.system.testcase.TestcaseHandler;
import android.testing.system.testcases.TestcasesHandler;
import android.testing.system.util.Util;

public class UploadTestcases extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
//		String testcases = Util.getInstance().getProperty("testcases");
		String username = request.getParameter("username");
		String project = request.getParameter("project");
		
//		int result = new TestcasesHandler(testcases, username).uploadTestcase(request, project);
//		int result = uploadTestcase(request);
		String js = "<script type='text/javascript'>parent.uploadTestcaseResult(";
//		File file = uploadTestcases(request);
//		File file = new TestcasesHandler(username).uploadTestcases(request);
		int result = new TestcaseHandler().newTestcase(username, project, uploadTestcases(request));
		
//		if(file != null) {
//			js += 0;
//		} else {
//			js += 1;
//		}
		js += result;
		js += ")</script>";
		
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(js);
	}
	
//	private int uploadTestcase(HttpServletRequest request) {
//		File file = uploadTestcases(request);
//		if(file != null) {
//			return 0;
//		} else {
//			return 1;
//		}
//	}
	
	private File uploadTestcases(HttpServletRequest request) {
		String username = request.getParameter("username");
//		String project = request.getParameter("project");
		String project = null;
		try {
//			project = new String(request.getParameter("project").getBytes("utf8"));
//			String str = request.getParameter("project");
			project = URLDecoder.decode(request.getParameter("project"), "utf-8");
//			System.out.println(project);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		// ����Ŀ¼
		String uploadDir = Util.getInstance().getProperty("temp").concat(File.separator).concat(username).concat(File.separator).concat(project);
		
		// ���ò���
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// �����ڴ�����С4KB
		factory.setSizeThreshold(4 * 1024);
		// �����ݴ����������ϴ��ļ��������õ��ڴ���Сʱ�����ݴ���������ת
		File repository = new File(uploadDir);
		if(!repository.exists()) repository.mkdirs();
		factory.setRepository(repository);
		ServletFileUpload fileUpload = new ServletFileUpload(factory);
		fileUpload.setHeaderEncoding("UTF-8");
		
		List<FileItem> fileItemList = null;
		try {
			fileItemList = fileUpload.parseRequest(request);
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
		FileItem fileItem = null;
		File writeToFile = null;
		
		Iterator<FileItem> fileItemIterator = fileItemList.iterator();
		while (fileItemIterator.hasNext()) {
			fileItem = fileItemIterator.next();
			// ��ͨ�ļ����ϴ�
			if (fileItem.isFormField()) {

			} else {
				// ��ȡ�ļ��ϴ����ļ���
				String OriginalFileName = takeOutFileName(fileItem.getName());
				if (!"".equals(OriginalFileName)) {					
					writeToFile = new File(uploadDir + File.separator + OriginalFileName);
					try {
						fileItem.write(writeToFile);
						break;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return writeToFile;
	}
	
	private String takeOutFileName(String filePath) {
		String fileName = filePath;
		if (null != filePath && !"".equals(filePath)) {
			int port = filePath.lastIndexOf("\\" + 1);
			if(port != -1){
				fileName = filePath.substring(port);
			}
		}
		return replace(fileName);
	}
	
	private String replace(String ori) {
		ori = ori.trim();
		char[] oriChar = ori.toCharArray();
		int len = oriChar.length;
		char[] fileChar = new char[len];
		for(int i = 0; i < len; i++) {
			if(oriChar[i] == ' ') {
				fileChar[i] = '_';
			} else {
				fileChar[i] = oriChar[i];
			}			
		}
		return String.copyValueOf(fileChar);
	}
}
