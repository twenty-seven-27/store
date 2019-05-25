package cn.ecit.test;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

public class TestEmail {

	public static void main(String[] args) throws AddressException, MessagingException {
		
		/*
		 * 163邮箱：服务端邮箱
		 * qq邮箱：客户端邮箱
		 * 
		 * 需求：用代码实现服务端给客户端发送邮件的功能
		 * 
		 * 分析：
		 * 		1，配置好服务端的邮箱,验证服务端邮箱账户
		 * 		2，明确接受端的邮箱
		 * 		3，写好信息
		 * 		4，发送
		 * 
		 * */
		
		//1, 服务器设置
		Properties props = new Properties();
		//设置发送协议
		//props.setProperty("mail.transport.protocol", "SMTP");
		//设置发送邮件服务器
		props.setProperty("mail.host", "smtp.163.com");
		props.setProperty("mail.smtp.auth", "true");	//指定验证为true
		
		//创建验证器
		Authenticator authenticator = new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				//设置发送人的账号和密码（163账号和密码）
				return new PasswordAuthentication("lee8273dww@163.com", "918wangyirty791");
			}
		};
		
		//1,与163服务器简历链接
		Session session = Session.getDefaultInstance(props, authenticator);
		//2，编写邮件
		Message message = new MimeMessage(session);
		//2.1，发件人
		message.setFrom(new InternetAddress("lee8273dww@163.com"));
		//2.2，收件人
		message.setRecipient(RecipientType.TO, new InternetAddress("2364693101@qq.com"));
		//2.3， 主题
		message.setSubject("这是我们的第一封邮件。");
		//2.4内容
		//message.setContent("恭喜你，您在购物天堂注册成功！", "text/html;charset=UTF-8");
		
		String url = "http://localhost:8080/store_v4/UserServlet?method=active&code=123456789";
		String content = "<h1>恭喜你，您在购物天堂注册成功！</h1><h3><a href='"+url+"'>"+url+"</h3>";
		message.setContent(content, "text/html;charset=UTF-8");
		
		//3，发送信息
		Transport.send(message);
		System.out.println("OKOKOK");
		
	}

}
