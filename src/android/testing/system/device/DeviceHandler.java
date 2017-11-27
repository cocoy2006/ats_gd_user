package android.testing.system.device;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import android.testing.system.SQL;
import android.testing.system.reservation.Reservation;
import android.testing.system.user.User;
import android.testing.system.util.Util;

public class DeviceHandler {

	private SessionFactory factory;
	
	public DeviceHandler() {
		factory = Util.getInstance().getFactory();
	}
	
	/**
	 * 0:free,1:busy,2:reserved*/
	public int setState(String server, String serialNumber, int state) {
		Session session = null;
		Transaction ts = null;
		try {
			session = factory.openSession();
			ts = session.beginTransaction();		
			
			Device d = (Device) session.createQuery(String.format(SQL.SELECT_DEVICE, server, serialNumber)).uniqueResult();
			d.setState(state);
			session.update(d);
			
			ts.commit();
			return 0;
		} catch(Exception e) {
			System.out.println(e.getMessage());
			ts.rollback();
			return 1;
		} finally {
			session.close();
		}
	}
	
	/**
	 * 0:free,1:busy,2:reserved,or -1:error*/
	public int getState(String server, String serialNumber) {
		Session session = null;
		Transaction ts = null;
		try {
			session = factory.openSession();
			ts = session.beginTransaction();		
			
			Device d = (Device) session.createQuery(String.format(SQL.SELECT_DEVICE, server, serialNumber)).uniqueResult();
			int state = d.getState();
			
			ts.commit();
			return state;
		} catch(Exception e) {
			System.out.println(e.getMessage());
			ts.rollback();
			return -1;
		} finally {
			session.close();
		}
	}
	
	/**
	 * 0:free, 1:busy, 3:no reservation, 4:not user's reservation, 5:user's reservation, or -1:error*/
	public int isAvailable(String username, String server, String serialNumber) {
		Session session = null;
		Transaction ts = null;
		try {
			session = factory.openSession();
			ts = session.beginTransaction();		
			
			Device d = (Device) session.createQuery(String.format(SQL.SELECT_DEVICE, server, serialNumber)).uniqueResult();
			int state = d.getState();
			switch(state) {
				case 0:
					return 0;
				case 1:
					return 1;
				case 2:
					Reservation r = (Reservation) session.createQuery(String.format(SQL.SELECT_DEVICE_NOW_RESERVATION, d.getId(), 1, System.currentTimeMillis(), System.currentTimeMillis())).uniqueResult();
					if(r == null) {
						return 3;
					} else {
//						User u = (User) session.createQuery(String.format(SQL.SELECT_USER_INFO, r.getUser_id())).uniqueResult();
						User u = (User) session.load(User.class, r.getUser_id());
						if(!username.equals(u.getUsername())) {
							return 4;
						} else {
							return 5;
						}
					}
			}
			
			ts.commit();
			return 1;
		} catch(Exception e) {
			System.out.println(e.getMessage());
			ts.rollback();
			return -1;
		} finally {
			session.close();
		}
	}
	
	public Device getDeviceInfo(int id) {
		Session session = null;
		Transaction ts = null;
		try {
			session = factory.openSession();
			ts = session.beginTransaction();		
			
//			Device d = (Device) session.createQuery(String.format(SQL.SELECT_DEVICE_INFO, id)).uniqueResult();
			Device d = (Device) session.load(Device.class, id);
			
			ts.commit();
			return d;
		} catch(Exception e) {
			System.out.println(e.getMessage());
			ts.rollback();
			return null;
		} finally {
			session.close();
		}
	}
	
	public List getAllDevices() {
		Session session = null;
		Transaction ts = null;
		try {
			session = factory.openSession();
			ts = session.beginTransaction();		
			
			List list = session.createQuery(String.format(SQL.SELECT_ALL_DEVICES)).list();
			
			ts.commit();
			return list;
		} catch(Exception e) {
			System.out.println(e.getMessage());
			ts.rollback();
			return null;
		} finally {
			session.close();
		}
	}
	
	public Device getDevice(int id) {
		Session session = null;
		Transaction ts = null;
		try {
			session = factory.openSession();
			ts = session.beginTransaction();		
			
			Device d = (Device) session.load(Device.class, id);			
			return d;
		} catch(Exception e) {
			System.out.println(e.getMessage());
			ts.rollback();
			return null;
		} finally {
			session.close();
		}
	}
	/**
	 * 0:success, 1:device exist already, -1:error*/
	public int newDevice(String imei, String server, String serialNumber, String manufacturer, 
			String model, int isEmulator, String os, int width, int height) {
		Session session = null;
		Transaction ts = null;
		try {
			session = factory.openSession();
			ts = session.beginTransaction();		
			
			Device d1 = (Device) session.createQuery(String.format(SQL.SELECT_DEVICE, server, serialNumber)).uniqueResult();
			if(d1 != null) return 1;
			
			Device d = new Device();
			d.setImei(imei);
			d.setServer(server);
			d.setSerialNumber(serialNumber);
			d.setManufacturer(manufacturer);
			d.setModel(model);
			d.setWidth(width);
			d.setHeight(height);
			d.setIsEmulator(isEmulator);
			d.setOs(os);
			d.setState(3);
			session.save(d);
			
			ts.commit();
			return 0;
		} catch(Exception e) {
			System.out.println(e.getMessage());
			ts.rollback();
			return -1;
		} finally {
			session.close();
		}
	}
	/**
	 * 0:success, 1:device exist already, -1:error*/
	public int updateDevice(int id, String imei, String server, String serialNumber, String manufacturer, 
			String model, int isEmulator, String os, int width, int height) {
		Session session = null;
		Transaction ts = null;
		try {
			session = factory.openSession();
			ts = session.beginTransaction();
			
			Device device = (Device) session.load(Device.class, id);
			if(device == null) return -1;
			if(server.equalsIgnoreCase(device.getServer()) && serialNumber.equalsIgnoreCase(device.getSerialNumber())) {
				// do nothing.
			} else {
				if((Device) session.createQuery(String.format(SQL.SELECT_DEVICE, server, serialNumber)).uniqueResult() != null) {
					return 1;
				} else {
					device.setServer(server);
					device.setSerialNumber(serialNumber);
				}
			}
			device.setManufacturer(manufacturer);
			device.setModel(model);
			device.setWidth(width);
			device.setHeight(height);
			device.setIsEmulator(isEmulator);
			device.setImei(imei);
			device.setOs(os);
			device.setState(3);
			session.update(device);
			
			ts.commit();
			return 0;
		} catch(Exception e) {
			System.out.println(e.getMessage());
			ts.rollback();
			return -1;
		} finally {
			session.close();
		}
	}
	/**
	 * 1:success, 0:error*/
	public int unactiveDevices(String[] ids) {
		Session session = null;
		Transaction ts = null;
		try {
			session = factory.openSession();
			ts = session.beginTransaction();
			
			Device d;
			for(String id : ids) {
				int i = Integer.parseInt(id);
				d = (Device) session.load(Device.class, i);
				if(d != null) {
					d.setState(3);
					session.update(d);
				}
			}
			
			ts.commit();
			return 1;
		} catch(Exception e) {
			System.out.println(e.getMessage());
			ts.rollback();
			return 0;
		} finally {
			session.close();
		}
	}
	/**
	 * 1:success, 0:error*/
	public int unactiveDevice(int id) {
		Session session = null;
		Transaction ts = null;
		try {
			session = factory.openSession();
			ts = session.beginTransaction();
			
			Device d = (Device) session.load(Device.class, id);
			if(d != null) {
				d.setState(3);
				session.update(d);
			}
			
			ts.commit();
			return 1;
		} catch(Exception e) {
			System.out.println(e.getMessage());
			ts.rollback();
			return 0;
		} finally {
			session.close();
		}
	}
	/**
	 * 1:success, 0:error*/
	public int activeDevices(String[] ids) {
		Session session = null;
		Transaction ts = null;
		try {
			session = factory.openSession();
			ts = session.beginTransaction();
			
			Device d;
			for(String id : ids) {
				int i = Integer.parseInt(id);
				d = (Device) session.load(Device.class, i);
				if(d != null) {
					d.setState(0);
					session.update(d);
				}
			}
			
			ts.commit();
			return 1;
		} catch(Exception e) {
			System.out.println(e.getMessage());
			ts.rollback();
			return 0;
		} finally {
			session.close();
		}
	}
	/**
	 * 1:success, 0:error*/
	public int deleteDevices(String[] ids) {
		Session session = null;
		Transaction ts = null;
		try {
			session = factory.openSession();
			ts = session.beginTransaction();
			
			Device d;
			for(String id : ids) {
				int i = Integer.parseInt(id);
				d = (Device) session.load(Device.class, i);
				session.delete(d);
			}
			
			ts.commit();
			return 1;
		} catch(Exception e) {
			System.out.println(e.getMessage());
			ts.rollback();
			return 0;
		} finally {
			session.close();
		}
	}
}
