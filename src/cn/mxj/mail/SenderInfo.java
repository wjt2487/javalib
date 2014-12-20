/**
 * 
 */
package cn.mxj.mail;

/**
 * @author fl
 * 
 */
public class SenderInfo {

	public SenderInfo() {
		this.needAuth = true;
		this.senderName = "";
	}

	public SenderInfo(String smtpHost, String username, String password,
			boolean needAuth, String senderName) {
		this.smtpHost = smtpHost;
		this.username = username;
		this.password = password;
		this.needAuth = needAuth;
		this.senderName = senderName;
	}

	public SenderInfo(String smtpHost, String username, String password) {
		this(smtpHost, username, password, true, username);
	}

	private String smtpHost;

	private String username;

	private String password;

	private String senderName;

	private boolean needAuth;

	public boolean isNeedAuth() {
		return this.needAuth;
	}

	public void setNeedAuth(boolean needAuth) {
		this.needAuth = needAuth;
	}

	public String getSmtpHost() {
		return this.smtpHost;
	}

	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSenderName() {
		return this.senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

}
