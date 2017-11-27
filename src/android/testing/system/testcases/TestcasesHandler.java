package android.testing.system.testcases;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import android.testing.system.util.Util;

import com.google.gson.Gson;

public class TestcasesHandler {
	
	private String testcases;
	private String username;
	private String userRootFolder;
	private String testcaseRoot; // 测试案例根目录
	
//	private final String CHARSET = "UTF-8";
	
	public TestcasesHandler(String testcases, String username) {
		this.testcases = testcases;
		this.username = username;
		this.userRootFolder = testcases.concat(File.separator).concat(username).concat(File.separator);
	}
	
	public TestcasesHandler(String username) {
		this.username = username;
		this.testcaseRoot = Util.getInstance().getProperty("testcaseRoot").concat(File.separator)
			.concat(username).concat(File.separator).concat("testcase").concat(File.separator);
	}
	
	public String loadTestcases() {
//		String directoryPath = testcases.concat(File.separator).concat(username);
//		File directory = new File(directoryPath);
		File directory = new File(this.testcaseRoot);
		if(directory.isDirectory()) {
			File[] projects = directory.listFiles();
			if(projects.length == 0) {
				return null;
			} else {				
				List list = new ArrayList();
				for(File project : projects) {
					Map map = new HashMap();
					map.put("title", project.getName());
					map.put("isFolder", true);
					map.put("key", project.getName());
					if(project.listFiles().length > 0) {
						List list2 = new ArrayList();
						map.put("children", list2);
						for(File testcase : project.listFiles()) {
							Map map2 = new HashMap();
							map2.put("title", testcase.getName());
							map2.put("key", project.getName() + "/" + testcase.getName());
							list2.add(map2);
						}
					}
					list.add(map);
				}
				Gson gson = new Gson();
				return gson.toJson(list);
			}
		} else {
			directory.mkdirs();			
		}
		return null;
	}
	/**
	 * 0����
	 * 1���ļ����Ѿ�����
	 * 2���ļ�����Ʋ��������*/
	public int newProject(String project) {
		String file = this.testcaseRoot.concat(project);
		File directory = new File(file);
		if(directory.isDirectory()) {
			return 1;
		} else {
			try {
				directory.mkdir();
				return 0;
			} catch(Exception e) {
				return 2;
			}
		}
	}
	/**
	 * 0����
	 * 2���ļ�����Ʋ��������*/
	public int renameProject(String project1, String project2) {
//		String parent = testcases.concat(File.separator).concat(username).concat(File.separator);
		String pro1 = this.testcaseRoot.concat(project1);
		File file1 = new File(pro1);
		String pro2 = this.testcaseRoot.concat(project2);
		File file2 = new File(pro2);
		if(file1.renameTo(file2)) {
			return 0;
		} else {
			return 2;
		}
	}
	/**
	 * 0����
	 * 1��ɾ��ʧ��*/
	public int deleteProject(String project) {
		String pro = this.testcaseRoot.concat(project);
		File file = new File(pro);
		for(File f : file.listFiles()) {
			if(!f.delete()) return 1;
		}
		file.delete();
		return 0;
	}
	/**
	 * 0����
	 * 1: �����쳣
	 * 2���ļ�����Ʋ��������*/
	public int renameTestcase(String project, String testcase1, String testcase2) {
		String parent = this.testcaseRoot.concat(project).concat(File.separator);
		String tc1 = parent.concat(testcase1);
		String tc2 = parent.concat(testcase2);
		File file1 = new File(tc1);
		File file2 = new File(tc2);
		if(file1.renameTo(file2)) {
			return 0;
		} else {
			return 2;
		}
	}
	/**
	 * 0����
	 * 1��ɾ��ʧ��*/
	public int deleteTestcase(String project, String testcase)	 {
		String tc = this.testcaseRoot.concat(project).concat(File.separator).concat(testcase);
		File file = new File(tc);
		if(file.delete()) {
			return 0;
		} else {
			return 1;
		}
	}
	/**
	 * 0����
	 * 1���ƶ�ʧ��*/
	public int moveTestcase(String project1, String project2, String testcase) {
		String src = this.testcaseRoot.concat(project1).concat(File.separator).concat(testcase);
		File sFile = new File(src);
		String tar = this.testcaseRoot.concat(project2).concat(File.separator).concat(testcase);
		File tFile = new File(tar);
		if(sFile.renameTo(tFile)) {
			return 0;
		} else {
			return 1;
		}
	}
	
	public File uploadTestcases(HttpServletRequest request) {
//		String username = request.getParameter("username");
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
		String uploadDir = this.testcaseRoot.concat(project);
		// ���ò���
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// �����ڴ�����С4KB
		factory.setSizeThreshold(4 * 1024);
		// �����ݴ����������ϴ��ļ��������õ��ڴ���Сʱ�����ݴ���������ת
		factory.setRepository(new File(uploadDir));
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
