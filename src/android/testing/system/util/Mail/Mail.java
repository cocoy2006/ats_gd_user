package android.testing.system.util.Mail;

import java.util.Random;

import android.testing.system.util.Util;

public class Mail {

	private String STMP = "";
	private String PORT = "";
	private String USERNAME = "";
	private String PASSWORD = "";
	private String FROM_ADDRESS = "";
	
	private String SYS_TITLE = "";
	
	private final String BLANK = "&nbsp;&nbsp;&nbsp;&nbsp;";
	
	private int id;
	private String username;
	private String email;
	private String fromUrl;
	
	public Mail(int id, String username, String email, String fromUrl) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.fromUrl = fromUrl;
		init();
	}
	
	public Mail(String username, String email) {
		this.username = username;
		this.email = email;
		init();
	}
	
	private void init() {
		Util util = Util.getInstance();
		STMP = util.getProperty("MAIL_STMP");
		PORT = util.getProperty("MAIL_PORT");
		USERNAME = util.getProperty("MAIL_USERNAME");
		PASSWORD = util.getProperty("MAIL_PASSWORD");
		FROM_ADDRESS = util.getProperty("MAIL_ADDRESS");
		SYS_TITLE = util.getProperty("MAIL_TITLE");
		STMP = util.getProperty("MAIL_STMP");
	}

	public boolean send() {
		MailSenderInfo mailInfo = createSender(SYS_TITLE, content());
		SimpleMailSender sms = new SimpleMailSender();
		sms.sendHtmlMail(mailInfo);
		return true;
	}
	
	public void send(MailSenderInfo mailInfo) {
		SimpleMailSender sms = new SimpleMailSender();
		sms.sendHtmlMail(mailInfo);
	}
	
	public String passwordResetSender() {
		String newPassword = randomString(8);
		if(newPassword == null) return null;
		send(createSender(SYS_TITLE, passwordResetContent(newPassword)));
		return newPassword;
	}
	
	private String content() {
		String url = parseUrl().concat("?id=").concat(String.valueOf(id))
			.concat("&username=").concat(username).concat("&email=").concat(email);
		
		StringBuffer sb = new StringBuffer();
		sb.append("Hi, <font color='#005EAC'>").append(username).append("</font><br/><br/>")
			.append(BLANK).append("感谢您选择").append(SYS_TITLE).append("<br/><br/>")
			.append(BLANK).append("请您点击以下链接以激活您的账号:<br/>")
			.append(BLANK).append("<a href='").append(url).append("' color='red'>").append(url).append("</a><br/><br/>")
			.append(BLANK).append("(如果无法点击，请完整的复制上面地址到您的浏览器地址栏中并打开)<br/><br/>")
			.append(BLANK).append("您必须先验证通行证电子邮箱地址，方可保证账号安全，日后可以享有邮件找回密码等服务。<br/><br/>")
			.append(BLANK).append("【注意】本邮件为系统自动发送的邮件，请不要回复本邮件。<br/><br/>");
		return sb.toString();
	}
	private String parseUrl() {
		String[] parts = fromUrl.split("/"); 
		return parts[0] + "//" + parts[2] + "/" + parts[3] + "/servlet/ActivateUser";
	}
	
	private String passwordResetContent(String newPassword) {
		StringBuffer sb = new StringBuffer();
		sb.append("Hi, <font color='#005EAC'>").append(username).append("</font><br/><br/>")
			.append(BLANK).append("感谢您选择").append(SYS_TITLE).append("<br/><br/>")
			.append(BLANK).append("您的新密码是:<br/>")
			.append(BLANK).append(newPassword)
			.append(BLANK).append("(请您注意保管您的密码，及时登录系统修改)<br/><br/>")
			.append(BLANK).append("【注意】本邮件为系统自动发送的邮件，请不要回复本邮件。<br/><br/>");
		return sb.toString();
	}
	
	private String randomString(int length) {
		if (length < 1)
			return null;
		Random randGen = new Random();
		char[] numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz"
				+ "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
		char[] randBuffer = new char[length];
		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];
		}
		return new String(randBuffer);
	}
	
	public MailSenderInfo createSender(String subject, String content) {
		MailSenderInfo msi = new MailSenderInfo();
		msi.setMailServerHost(STMP); 
		msi.setMailServerPort(PORT);
		msi.setValidate(true);
		msi.setUserName(USERNAME);
		msi.setPassword(PASSWORD);
		msi.setFromAddress(FROM_ADDRESS);
		msi.setToAddress(email);
		msi.setSubject(subject);
		msi.setContent(content);
		return msi;
	}
}
