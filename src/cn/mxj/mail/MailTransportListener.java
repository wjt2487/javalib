package cn.mxj.mail;

import javax.mail.event.TransportEvent;
import javax.mail.event.TransportListener;

import cn.mxj.io.AppLogger;

public class MailTransportListener implements TransportListener {

	protected AppLogger logger = AppLogger.getInstance();

	private EmailInfo mailInfo;

	private SenderInfo senderInfo;

	public EmailInfo getMailInfo() {
		return this.mailInfo;
	}

	public void setMailInfo(EmailInfo mailInfo) {
		this.mailInfo = mailInfo;
	}

	public SenderInfo getSenderInfo() {
		return this.senderInfo;
	}

	public void setSenderInfo(SenderInfo senderInfo) {
		this.senderInfo = senderInfo;
	}

	public void messageDelivered(TransportEvent arg0) {
		System.out.println("====>>>> mail sended to:" + mailInfo.getMailTo()
				+ ", from:" + senderInfo.getUsername());
	}

	public void messageNotDelivered(TransportEvent arg0) {
		logger.info("====>>>> mail send failed to:" + mailInfo.getMailTo()
				+ ", from:" + senderInfo.getUsername());
		logger.info(arg0.getMessage().toString());
	}

	public void messagePartiallyDelivered(TransportEvent arg0) {
		logger.info("====>>>> mail partially sended to:" + mailInfo.getMailTo()
				+ ", from:" + senderInfo.getUsername());
		logger.info(arg0.getMessage().toString());
	}

}
