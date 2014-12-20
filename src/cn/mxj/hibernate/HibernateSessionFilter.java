package cn.mxj.hibernate;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class HibernateSessionFilter implements Filter {

	@SuppressWarnings("unused")
	private FilterConfig fc;

	public void init(FilterConfig fc) throws ServletException {
		this.fc = fc;
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws ServletException, IOException {
		try {
			chain.doFilter(request, response);
		} catch (Exception e) {

		} finally {
			HibernateSessionFactory.closeSession();
		}
	}

	public void destroy() {
	}
}
