package android.testing.system.util.Mail;

public class MailTest {

	public static void main(String[] args) {
		// 
		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setMailServerHost("smtp.exmail.qq.com"); //smtp.qq.com
		mailInfo.setMailServerPort("25");
		mailInfo.setValidate(true);
		mailInfo.setUserName("no-reply@xinfuonline.com"); //xinfuonline@qq.com
		mailInfo.setPassword("xinfuonline,123"); //molab,123
		mailInfo.setFromAddress("no-reply@xinfuonline.com"); //xinfuonline@qq.com
		mailInfo.setToAddress("cocoy2006@qq.com");
		mailInfo.setSubject("xinfuonline");
		mailInfo.setContent("xinfuonline");
		// 
		SimpleMailSender sms = new SimpleMailSender();
		sms.sendTextMail(mailInfo);// 
		sms.sendHtmlMail(mailInfo);// 
	}
}
