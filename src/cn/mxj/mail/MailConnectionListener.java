package cn.mxj.mail;

import javax.mail.event.ConnectionEvent;
import javax.mail.event.ConnectionListener;

public class MailConnectionListener implements ConnectionListener {

	public void closed(ConnectionEvent arg0) {
		System.out.println("mail connection was closed!");
	}

	public void disconnected(ConnectionEvent arg0) {
		System.out.println("mail connection was disconnected!");
	}

	public void opened(ConnectionEvent arg0) {
		System.out.println("mail connection was opened!");
	}

}
