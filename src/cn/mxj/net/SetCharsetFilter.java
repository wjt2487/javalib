/**
 * 
 */
package cn.mxj.net;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class SetCharsetFilter implements Filter {

	protected String encoding = null;

	protected FilterConfig filterConfig = null;

	public void destroy() {
		this.encoding = null;
		this.filterConfig = null;
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if (request.getCharacterEncoding() == null) {
			String encoding = getEncoding();
			if (encoding != null) {
				request.setCharacterEncoding(encoding);
			}
		}

		chain.doFilter(request, response);
	}

	public void init(FilterConfig fc) throws ServletException {
		this.filterConfig = fc;
		this.encoding = fc.getInitParameter("encoding");
	}

	protected String getEncoding() {
		return (this.encoding);
	}
}
