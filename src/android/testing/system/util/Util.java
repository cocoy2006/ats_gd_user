package android.testing.system.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Util {
	private static Util util;
	private Configuration cfg;
	private SessionFactory factory;
	
	private final String SERVERS_CONF_FILE = "/servers.xml";
	private List<String> servers;
	
	private Map<String, String> properties;
	
	private Timer timeoutTimer;
	
	public static void main(String args[]) {
		Util util = new Util();
//		util.initServers();
//		System.out.println(util.servers.get(0));
		
//		Timer timer = new Timer();
//		timer.cancel();
		Timer timer = new Timer();
		timer.schedule(util.new TestTask(), new Date(System.currentTimeMillis() + 5000));
		System.out.println(Thread.currentThread().getName());
//		System.out.println("the end1");
//		timer.cancel();
		Timer timer1 = new Timer();
		timer1.schedule(util.new TestTask(), new Date(System.currentTimeMillis() + 10000));
		System.out.println(Thread.currentThread().getName());
		System.out.println(Thread.activeCount());
		
//		timer1.cancel();
//		System.out.println("the end2");
//		System.exit(0);
	}
	class TestTask extends TimerTask {

		@Override
		public void run() {
			System.out.println(new Date(System.currentTimeMillis()));
		}
		
	}
	
	private Util() {
		cfg = new Configuration();
		factory = cfg.configure().buildSessionFactory();
		properties = new HashMap<String, String>();
		timeoutTimer = new Timer();
//		timeoutTimer.schedule(new TimeoutTimer(), time)
	}
	
	public static Util getInstance() {
		if(util == null) {
			synchronized(Util.class) {
				util = new Util();
			}
		}
		return util;
	}
	
	public Timer getTimeoutTimer() {
		return timeoutTimer;
	}
	
	public void reSchedule() {
		timeoutTimer.cancel();
		timeoutTimer = new Timer();
//		timeoutTimer.schedule(task, time)
	}
	
	public SessionFactory getFactory() {
		return factory;
	}
	
	public String getProperty(String key) {
		return properties.get(key);
	}	
	public void addProperty(String key, String value) {
		properties.put(key, value);
	}
	
	public void initServers(String file) {
		servers = extractUrls(file.concat(SERVERS_CONF_FILE));		
	}
	
	public List<String> getServers() {
		return servers;
	}
	
	private List<String> extractUrls(String file) {
		List<String> list = new ArrayList<String>();
		Document doc = validation(file);
		Element root = doc.getRootElement();
		List<Element> servers = root.elements("server");
		for(Element server : servers) {
			list.add(server.element("url").getTextTrim());
		}
		return list;
	}
	
	private Document validation(String file) {
		FileInputStream fis = null;
		Document doc = null;
		try {
			fis = new FileInputStream(new File(file));
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
		SAXReader reader = new SAXReader();
		reader.setStripWhitespaceText(true);
		reader.setMergeAdjacentText(true);
		try {
			doc = reader.read(fis);
		} catch (DocumentException e) {
			System.out.println(e.getMessage());
		}
		return doc;
	}
}
