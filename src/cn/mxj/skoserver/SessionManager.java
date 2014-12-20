package cn.mxj.skoserver;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SessionManager {

	// private static final int DEF_SESSION_TIME_OUT = 300;

	private static SessionManager _instance;

	public static SessionManager getInstance() {
		if (_instance == null) {
			_instance = new SessionManager();
		}
		return _instance;
	}

	private SessionManager() {
	}

	private Map<Integer, LoginSessionRecord> sessionMap = new HashMap<Integer, LoginSessionRecord>();

	public int openSession(LoginSessionRecord record) {
		int id = _getAvailableSessionId();
		sessionMap.put(id, record);
		return id;
	}

	public boolean isSessionExist(int sessionId) {
		return sessionMap.containsKey(sessionId);
	}

	public void refreshSession(int sessionId) {
		LoginSessionRecord record = sessionMap.get(sessionId);
		record.setLastRefreshTime(new Date());
	}

	public void closeSession(int sessionId) {
		sessionMap.remove(sessionId);
	}

	public LoginSessionRecord getSessionRecord(int sessionId) {
		return sessionMap.get(sessionId);
	}

	private int _getAvailableSessionId() {
		while (true) {
			int id = (int) (Math.random() * 1000);
			if (!isSessionExist(id)) {
				return id;
			}
		}
	}
}
