package cn.mxj.net;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.mxj.beans.UserBean;

/**
 * the context environment of system running
 * 
 * @author fl
 * 
 */
public class SysRtContext {

	protected HttpServlet servlet;

	protected HttpServletRequest request;

	protected HttpServletResponse response;

	public final static String KEY_USER_INFO = "SYSRTCONTEXT_KEY_USER_INFO";

	public SysRtContext(HttpServlet servlet, HttpServletRequest request,
			HttpServletResponse response) {
		this.servlet = servlet;
		this.request = request;
		this.response = response;
	}

	public ServletContext getApplication() {
		return this.servlet.getServletContext();
	}

	public HttpSession getSession() {
		return this.request.getSession();
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public String getContextPath() {
		return this.request.getContextPath();
	}

	/**
	 * get the user's info of current logined
	 * 
	 * @return
	 */
	public UserBean getMyInfo() {
		return (UserBean) this.getSession().getAttribute(KEY_USER_INFO);
	}

	/**
	 * set current user's info to system context
	 * 
	 * @param myInfo
	 */
	public void setMyInfo(UserBean myInfo) {
		this.getSession().setAttribute(KEY_USER_INFO, myInfo);
	}

}
