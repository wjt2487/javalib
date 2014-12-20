/**
 * 
 */
package cn.mxj.net;

import java.io.Serializable;
import java.util.Random;

import cn.mxj.beans.UserBean;
import cn.mxj.io.AppLogger;
import cn.mxj.string.MD5;

/**
 * 用户标记，客户端持有此标记，用于标识合法用户身份，防止非法请求
 * 
 * @author fl
 * 
 */
public class UserToken implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3145042936057875077L;

	private static UserToken empty;

	public static UserToken getEmpty() {
		if (empty == null) {
			empty = new UserToken(0, "", "", "", "");
		}
		return empty;
	}

	public UserToken() {
	}

	public UserToken(int userId, String userName, String userNumber,
			String encryptedPassword, String clientIp) {
		this.userId = userId;
		this.userName = userName;
		this.userNumber = userNumber;
		this.encryptedPassword = encryptedPassword;
		this.clientIp = clientIp;

		this.generate();
	}

	public UserToken(UserBean user) {
		this(user.getId(), user.getName(), user.getNumber(),
				user.getPassword(), "");
	}

	public UserToken(String token) {
		String[] arr = token.split("&&");
		this.userId = Integer.valueOf(arr[0]);
		this.userName = arr[1];
		this.userNumber = arr[2];
		this.encryptedPassword = arr[3];
		this.clientIp = arr[4];
		this.randomNum = arr[5];
		this.token = arr[6];
	}

	/**
	 * 提供基本的用户信息后，生成此用户的一个token
	 * 
	 */
	public void generate() {
		Random rnd = new Random(this.userId);
		this.randomNum = String.valueOf(rnd.nextLong());
		this.token = this.generateToken();
	}

	/**
	 * 验证此 token 是否合法
	 * 
	 * @return
	 */
	public boolean validate() {
		boolean ok = this.token.equals(this.generateToken());
		if (!ok) {
			AppLogger.getInstance().info("非法用户访问，ClientIp:" + this.clientIp);
		}
		return ok;
	}

	/**
	 * 根据自身包含的部分数据生成 token
	 * 
	 * @return
	 */
	protected String generateToken() {
		// 自定义生成 token 的算法...

		String result = MD5
				.encrypt(String.valueOf(this.userId) + this.userName);
		result = MD5.encrypt(result + this.encryptedPassword + this.clientIp
				+ this.randomNum + "user-token");
		return result;
	}

	@Override
	public String toString() {
		String t = "%1$s&&%2$s&&%3$s&&%4$s&&%5$s&&%6$s&&%7$s";
		return String.format(t, this.userId, this.userName, this.userNumber,
				this.encryptedPassword, this.clientIp, this.randomNum,
				this.token);
	}

	private int userId;

	private String userName;

	private String userNumber;

	private String encryptedPassword;

	private String clientIp;

	private String randomNum;

	private boolean logined;

	private String token;

	public String getClientIp() {
		return this.clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public String getEncryptedPassword() {
		return this.encryptedPassword;
	}

	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

	public boolean isLogined() {
		return this.logined;
	}

	public void setLogined(boolean logined) {
		this.logined = logined;
	}

	public String getRandomNum() {
		return this.randomNum;
	}

	public void setRandomNum(String randomNum) {
		this.randomNum = randomNum;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserNumber() {
		return this.userNumber;
	}

	public void setUserNumber(String userNumber) {
		this.userNumber = userNumber;
	}
}
