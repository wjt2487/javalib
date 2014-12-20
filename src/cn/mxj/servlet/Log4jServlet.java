/**
 * 
 */
package cn.mxj.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.PropertyConfigurator;

/**
 * @author fl
 * 
 */
public class Log4jServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6412451527829172952L;

	@Override
	public void init() throws ServletException {
		String prefix = getServletContext().getRealPath("/");

		System.out.println("[Log4j]: InitServlet init start ...");
		System.out.println("[Log4j]: The Root Path: " + prefix);

		// 配置文件位置
		String configFile = getInitParameter("log4j-config-file");
		if (configFile != null) {
			System.out.println("[Log4j]: Loading property file: " + prefix
					+ configFile);
			PropertyConfigurator.configure(prefix + configFile);
		}

		System.out.println("[Log4j]: InitServlet init over.");
	}
}
