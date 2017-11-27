package android.testing.system.user;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import android.testing.system.SQL;
import android.testing.system.util.Util;
import android.testing.system.util.Mail.Mail;

public class UserHandler {
	
	private SessionFactory factory;
	private final String MAX_ID = "SELECT MAX(id) FROM User";
	private final String CHECK_USERNAME = "SELECT id FROM User WHERE username='%s'";
	private final String CHECK_USER = "SELECT id, state, role FROM User WHERE username='%s' AND password='%s'";
	private final String SELECT_LEFTTIME = "SELECT lefttime FROM User WHERE username='%s'";
	private final String SELECT_ID_AND_LEFTTIME = "SELECT id, lefttime FROM User WHERE username='%s'";
	private final String SELECT_USER = "SELECT id, nickname, email, phone_no FROM User WHERE username='%s'";
	
	public UserHandler() {
		factory = Util.getInstance().getFactory();
	}

	/**
	 * �����û�*/
	public int createUser(User user) {
		Session session = null;
		Transaction ts = null;
		try {
			session = factory.openSession();
			ts = session.beginTransaction();
			
			Integer max = (Integer) session.createQuery(MAX_ID).uniqueResult();
			user.setId(max + 1);
			session.save(user);
			
			ts.commit();
			return max + 1;
		} catch(Exception e) {
			e.printStackTrace();
			ts.rollback();
			return 0;
		} finally {
			session.close();
		}
	}
	/**
	 * 0:success, -1:failure*/
	public int createUser(String username, String nickname, String password, String email, String phone_no, int lefttime, int state, int role) {
		Session session = null;
		Transaction ts = null;
		try {
			session = factory.openSession();
			ts = session.beginTransaction();
			
			User user = new User();
			user.setUsername(username);
			user.setNickname(nickname);
			user.setPassword(password);
			user.setEmail(email);
			user.setPhone_no(phone_no);
			user.setLefttime(lefttime);
			user.setState(state); // default: not activated
			user.setRole(role); // default: normal user
			session.save(user);
			
			ts.commit();
			return 0;
		} catch(Exception e) {
			e.printStackTrace();
			ts.rollback();
			return -1;
		} finally {
			session.close();
		}
	}
	
	public Object getUser(String username) {
		Session session = null;
		Transaction ts = null;
		try {
			session = factory.openSession();
			ts = session.beginTransaction();
			
			Object obj = session.createQuery(String.format(SELECT_USER, username)).uniqueResult();
			
			ts.commit();
			return obj;
		} catch(Exception e) {
			e.printStackTrace();
			ts.rollback();
			return null;
		} finally {
			session.close();
		}
	}
	
	public User getUser(String username, int flag) {
		Session session = null;
		Transaction ts = null;
		try {
			session = factory.openSession();
			ts = session.beginTransaction();
			
			User u = (User) session.createQuery(String.format(SQL.SELECT_USER, username)).uniqueResult();
			
			ts.commit();
			return u;
		} catch(Exception e) {
			e.printStackTrace();
			ts.rollback();
			return null;
		} finally {
			session.close();
		}
	}
	
	public User getUser(int id) {
		Session session = null;
		Transaction ts = null;
		try {
			session = factory.openSession();
			ts = session.beginTransaction();
			
			User u = (User) session.load(User.class, id);
			
			ts.commit();
			return u;
		} catch(Exception e) {
			e.printStackTrace();
			ts.rollback();
			return null;
		} finally {
			session.close();
		}
	}
	
	public List getAllUsers() {
		Session session = null;
		Transaction ts = null;
		try {
			session = factory.openSession();
			ts = session.beginTransaction();
			
			List list = session.createQuery(SQL.SELECT_ALL_USERS).list();
			
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
	
	/**
	 * �޸��û���Ϣ*/
	public boolean updateUser(int id, String nickname, String email, String phone_no) {
		Session session = null;
		Transaction ts = null;
		try {
			session = factory.openSession();
			ts = session.beginTransaction();
			
			User user = (User) session.load(User.class, id);
			user.setNickname(nickname);
			user.setEmail(email);
			user.setPhone_no(phone_no);
			session.update(user);
			
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
	/**
	 * 0:success, -1:failure*/
	public int passwordModify(int id, String password) {
		Session session = null;
		Transaction ts = null;
		try {
			session = factory.openSession();
			ts = session.beginTransaction();
			
			User user = (User) session.load(User.class, id);
			user.setPassword(password);
			session.update(user);
			
			ts.commit();
			return 0;
		} catch(Exception e) {
			e.printStackTrace();
			ts.rollback();
			return -1;
		} finally {
			session.close();
		}
	}
	
	public boolean activateUser(int id, String username, String email) {
		Session session = null;
		Transaction ts = null;
		try {
			session = factory.openSession();
			ts = session.beginTransaction();
			
			User user = (User) session.load(User.class, id);
			if(username.equals(user.getUsername()) && email.equals(user.getEmail())) {
				user.setState(0);
				session.update(user);
			} else {
				throw new Exception();
			}
			
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
	/**
	 * 0:success, -1:error*/
	public int updateStates(String[] ids, int state) {
		Session session = null;
		Transaction ts = null;
		try {
			session = factory.openSession();
			ts = session.beginTransaction();
			
			User u;
			for(String id : ids) {
				int i = Integer.parseInt(id);
				u = (User) session.load(User.class, i);
				u.setState(state);
				session.update(u);
			}
			
			ts.commit();
			return 0;
		} catch(Exception e) {
			e.printStackTrace();
			ts.rollback();
			return -1;
		} finally {
			session.close();
		}
	}
	
	/**
	 * 0:success, -1:error*/
	public int deleteUsers(String[] ids) {
		Session session = null;
		Transaction ts = null;
		try {
			session = factory.openSession();
			ts = session.beginTransaction();
			
			User user;
			for(String id : ids) {
				int i = Integer.parseInt(id);
				user = (User) session.load(User.class, i);
				session.delete(user);
			}
			
			ts.commit();
			return 0;
		} catch(Exception e) {
			e.printStackTrace();
			ts.rollback();
			return -1;
		} finally {
			session.close();
		}
	}
	
	/**
	 * ��ȡ�û�*/
	public boolean checkUsername(String username) {
		Session session = null;
		Transaction ts = null;
		try {
			session = factory.openSession();
			ts = session.beginTransaction();
			
			Object obj = session.createQuery(String.format(CHECK_USERNAME, username)).uniqueResult();
			
			ts.commit();
			if(obj != null) return true;
			else return false;
		} catch(Exception e) {
			e.printStackTrace();
			ts.rollback();
			return false;
		} finally {
			session.close();
		}
	}
	public Object checkUser(User user) {
		Session session = null;
		Transaction ts = null;
		try {
			session = factory.openSession();
			ts = session.beginTransaction();
			
			Object obj = session.createQuery(String.format(CHECK_USER, user.getUsername(), user.getPassword())).uniqueResult();
			
			ts.commit();
			return obj;
//			if(obj != null) return true;
//			else return false;
		} catch(Exception e) {
			e.printStackTrace();
			ts.rollback();
//			return false;
			return null;
		} finally {
			session.close();
		}
	}
	
	public Object checkUser(String username, String password) {
		Session session = null;
		Transaction ts = null;
		try {
			session = factory.openSession();
			ts = session.beginTransaction();
			
			Object o = session.createQuery(String.format(SQL.CHECK_USER, username, password)).uniqueResult();
			
			ts.commit();
			return o;
		} catch(Exception e) {
			e.printStackTrace();
			ts.rollback();
			return null;
		} finally {
			session.close();
		}
	}
	/**
	 * 0:success, 1:user name error, 2:email error, 3: system error*/
	public int resetUserPassword(String username, String email) {
		Session session = null;
		Transaction ts = null;
		try {
			session = factory.openSession();
			ts = session.beginTransaction();
			
			User u = (User) session.createQuery(String.format(SQL.SELECT_USER, username)).uniqueResult();
			if(u == null) return 1;
			if(!u.getEmail().equals(email)) return 2;
			Mail mail = new Mail(username, email);
			String newPassword = mail.passwordResetSender();
			u.setPassword(newPassword);
			session.update(u);
			
			ts.commit();
			return 0;
		} catch(Exception e) {
			e.printStackTrace();
			ts.rollback();
			return 3;
		} finally {
			session.close();
		}
	}
	
	public int getLefttime(String username) {
		int lefttime = 0;
		Session session = null;
		Transaction ts = null;
		try {
			session = factory.openSession();
			ts = session.beginTransaction();			
			
			lefttime = (Integer) session.createQuery(String.format(SELECT_LEFTTIME, username)).uniqueResult();
			
			ts.commit();
			return lefttime;
		} catch(Exception e) {
			e.printStackTrace();
			ts.rollback();
			return lefttime;
		} finally {
			session.close();
		}
	}
	public boolean addLefttime(String username, int time) {
		Session session = null;
		Transaction ts = null;
		try {
			session = factory.openSession();
			ts = session.beginTransaction();			
			
			Object obj = session.createQuery(String.format(SELECT_ID_AND_LEFTTIME, username)).uniqueResult();
			Object[] objs = (Object[]) obj;
			int id = (Integer) objs[0];
			int lefttime = (Integer) objs[1];
			User u = (User) session.get(User.class, id);
			lefttime += time;
			u.setLefttime(lefttime);
			session.update(u);
			
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
	public boolean updateLefttime(String username, int time) {
		Session session = null;
		Transaction ts = null;
		try {
			session = factory.openSession();
			ts = session.beginTransaction();			
			
			Object obj = session.createQuery(String.format(SELECT_ID_AND_LEFTTIME, username)).uniqueResult();
			Object[] objs = (Object[]) obj;
			int id = (Integer) objs[0];
			int lefttime = (Integer) objs[1];
			User u = (User) session.get(User.class, id);
			lefttime = time;
			u.setLefttime(lefttime);
			session.update(u);
			
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
}
