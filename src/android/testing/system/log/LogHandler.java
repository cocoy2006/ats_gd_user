package android.testing.system.log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import android.testing.system.SQL;
import android.testing.system.device.Device;
import android.testing.system.user.User;
import android.testing.system.util.Util;

public class LogHandler {

	private SessionFactory factory;
	
	public LogHandler() {
		factory = Util.getInstance().getFactory();
	}
	
	public boolean newLog(String username, String server, String serialNumber, String operation, long time) {
		Session session = null;
		Transaction ts = null;
		try {
			session = factory.openSession();
			ts = session.beginTransaction();		
			
			User u = (User) session.createQuery(String.format(SQL.SELECT_USER, username)).uniqueResult();
			Log log = new Log();
			log.setUser_id(u.getId());
			if(server != null && serialNumber != null) {
				Device d = (Device) session.createQuery(String.format(SQL.SELECT_DEVICE, server, serialNumber)).uniqueResult();
				log.setDevice_id(d.getId());
			}
			if(time == 0) time = System.currentTimeMillis();
			log.setOperation(operation);
			log.setTime(time);
			session.save(log);
			
			ts.commit();
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			ts.rollback();
			return false;
		} finally {
			session.close();
		}
	}
	
	public List getLogs(String username, long startTime, long endTime) {
		Session session = null;
		Transaction ts = null;
		try {
			session = factory.openSession();
			ts = session.beginTransaction();		
			
//			User u = (User) session.createQuery(String.format(SQL.SELECT_USER, username)).uniqueResult();
			List list = session.createQuery(String.format(SQL.SELECT_LOGS_WITH_USERNAME, username, startTime, endTime)).list();
			
			ts.commit();
			return list;
		} catch(Exception e) {
			e.printStackTrace();
			ts.rollback();
			return null;
		} finally {
			session.close();
		}
	}
	
	public List getLogs(String server, String serialNumber, long startTime, long endTime) {
		Session session = null;
		Transaction ts = null;
		try {
			session = factory.openSession();
			ts = session.beginTransaction();		
			
//			Device d = (Device) session.createQuery(String.format(SQL.SELECT_DEVICE, server, serialNumber)).uniqueResult();
			List list = session.createQuery(String.format(SQL.SELECT_LOGS_WITH_DEVICE_INFO, server, serialNumber, startTime, endTime)).list();
			
			ts.commit();
			return list;
		} catch(Exception e) {
			e.printStackTrace();
			ts.rollback();
			return null;
		} finally {
			session.close();
		}
	}
	
	public List getLogs(int deviceId, long startTime, long endTime) {
		Session session = null;
		Transaction ts = null;
		try {
			session = factory.openSession();
			ts = session.beginTransaction();		
			
//			Device d = (Device) session.load(Device.class, deviceId);
			List list = session.createQuery(String.format(SQL.SELECT_LOGS_WITH_DEVICE_ID, deviceId, startTime, endTime)).list();
			
			ts.commit();
			return list;
		} catch(Exception e) {
			e.printStackTrace();
			ts.rollback();
			return null;
		} finally {
			session.close();
		}
	}
	
	public List getQueryLogs(String username, String server, String serialNumber, long startTime, long endTime) {
		Session session = null;
		Transaction ts = null;
		try {
			session = factory.openSession();
			ts = session.beginTransaction();
			
//			List<User> users = session.createQuery(String.format(SQL.SEELCT_USERS_WITH_USERNAME, username)).list();
			User u = (User) session.createQuery(String.format(SQL.SELECT_USER, username)).uniqueResult();
			if(u == null) return null;
			
			List<Device> devices = session.createQuery(String.format(SQL.SELECT_DEVICE, server, serialNumber)).list();
			if(devices == null) return null;
			
			List list = new ArrayList();
			Device d = null;
			for(int i = 0; i < devices.size(); i++) {
				d = (Device) devices.get(i);
				List temp = session.createQuery(String.format(SQL.SELECT_QUERY_LOGS, u.getId(), d.getId(), startTime, endTime)).list();
				if(temp != null) {
					for(int j = 0; j < temp.size(); j++) {
						Map map = new HashMap();
						map.put("username", u.getUsername());
						map.put("server", d.getServer());
						map.put("serialNumber", d.getSerialNumber());
						Log l = (Log) temp.get(j);
						map.put("operation", l.getOperation());
						map.put("time", l.getTime());
						list.add(map);
					}
				}
			}
			
			ts.commit();
			return list;
		} catch(Exception e) {
			e.printStackTrace();
			ts.rollback();
			return null;
		} finally {
			session.close();
		}
	}
	
	public List getAllLogs(long startTime, long endTime) {
		Session session = null;
		Transaction ts = null;
		try {
			session = factory.openSession();
			ts = session.beginTransaction();
			
			List list = new ArrayList();
			List logs = session.createQuery(String.format(SQL.SELECT_ALL_LOGS, startTime, endTime)).list();
			for(int i = 0; i < logs.size(); i++) {
				Log log = (Log) logs.get(i);
				Map map = new HashMap();
				User u = (User) session.load(User.class, log.getUser_id());
				map.put("username", u.getUsername());
				int device_id = log.getDevice_id();
				if(device_id != 0) {
					Device d = (Device) session.load(Device.class, log.getDevice_id());
					map.put("server", d.getServer());
					map.put("serialNumber", d.getSerialNumber());
				}
				map.put("operation", log.getOperation());
				map.put("time", log.getTime());
				list.add(map);
			}
			
			ts.commit();
			return list;
		} catch(Exception e) {
			e.printStackTrace();
			ts.rollback();
			return null;
		} finally {
			session.close();
		}
	}
}
