package cn.mxj.mail;

import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

public class MailSender {

	public MailSender(SenderInfo senderInfo) {
		this(senderInfo, "utf-8");
	}

	public MailSender(SenderInfo senderInfo, String charset) {
		props = System.getProperties();
		props.put("mail.smtp.host", senderInfo.getSmtpHost());
		props.put("mail.smtp.auth", senderInfo.isNeedAuth() ? "true" : "false");

		this.senderInfo = senderInfo;
		this.charset = charset;
	}

	protected SenderInfo senderInfo;

	protected Session session;

	protected Properties props;

	protected MimeMessage mimeMsg;

	protected String charset;

	/**
	 * Multipart 对象,邮件内容,标题,附件等內容均添加到其中后再生成MimeMessage对象
	 */
	protected Multipart multipart;

	public SenderInfo getSenderInfo() {
		return this.senderInfo;
	}

	public void send(EmailInfo mailInfo, MailTransportListener listener)
			throws Exception {
		createMailContent(mailInfo);
		listener.setMailInfo(mailInfo);
		listener.setSenderInfo(senderInfo);

		Session mailSession = Session.getInstance(props, null);
		Transport transport = mailSession.getTransport("smtp");
		transport.addTransportListener(listener);
		transport.addConnectionListener(new MailConnectionListener());
		transport.connect((String) props.get("mail.smtp.host"), senderInfo
				.getUsername(), senderInfo.getPassword());
		transport.sendMessage(mimeMsg, mimeMsg
				.getRecipients(Message.RecipientType.TO));
		transport.close();
	}

	protected void createMailContent(EmailInfo mailInfo) throws Exception {
		// create session
		try {
			session = Session.getDefaultInstance(props, null);
		} catch (Exception e) {
			throw new Exception("获取邮件会话对象出错�!");
		}

		// create mime
		try {
			mimeMsg = new MimeMessage(session);
			multipart = new MimeMultipart();
		} catch (Exception e) {
			throw new Exception("创建MIME邮件对象失败�!");
		}

		// set mail from
		String personal = MimeUtility.encodeText(senderInfo.getSenderName(),
				charset, "B");
		mimeMsg.setFrom(new InternetAddress(senderInfo.getUsername(), personal,
				charset));

		// set mail to
		try {
			mimeMsg.setRecipients(Message.RecipientType.TO, InternetAddress
					.parse(mailInfo.getMailTo()));
		} catch (Exception e) {
			throw new Exception("目标邮件地址非法�!");
		}

		// set mail subject
		try {
			String subject = MimeUtility.encodeText(mailInfo.getSubject(),
					charset, "B");
			mimeMsg.setSubject(subject, charset);
		} catch (Exception e) {
			throw new Exception("设置邮件主题出错��!");
		}

		// set mail body
		try {
			BodyPart bp = new MimeBodyPart();
			if (mailInfo.isHtml())
				bp.setContent(mailInfo.getBody(), "text/html;charset="
						+ charset);
			else
				bp.setText(mailInfo.getBody());
			multipart.addBodyPart(bp);
		} catch (Exception e) {
			throw new Exception("设置邮件正文出错�!");
		}

		mimeMsg.setContent(multipart);
		mimeMsg.saveChanges();
	}

}
