package cn.mxj.skoserver;

import java.util.Date;

public class LoginSessionRecord {

	private Date lastRefreshTime;

	private int timeCode;

	private int userId;

	private int userType;

	private int currentCourseId;

	public LoginSessionRecord(int timeCode) {
		lastRefreshTime = new Date();
		this.timeCode = timeCode;
		this.userId = 0;
		this.userType = 0;
		this.currentCourseId = 0;
	}

	public void setUserInfo(int userId, int userType) {
		this.userId = userId;
		this.userType = userType;
	}

	public void setCurrentCourseId(int currentCourseId) {
		this.currentCourseId = currentCourseId;
	}

	public Date getLastRefreshTime() {
		return lastRefreshTime;
	}

	public int getTimeCode() {
		return timeCode;
	}

	public int getUserId() {
		return userId;
	}

	public int getUserType() {
		return userType;
	}

	public int getCurrentCourseId() {
		return currentCourseId;
	}

	public void setLastRefreshTime(Date lastRefreshTime) {
		this.lastRefreshTime = lastRefreshTime;
	}
}
