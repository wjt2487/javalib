package cn.mxj.tag;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import cn.mxj.web.ImportModuleUtil;

public class ModuleUrlTag extends SimpleTagSupport {

	private String destUrl;

	private String destParam;

	private HttpServletRequest request;

	public void setDestUrl(String destUrl) {
		this.destUrl = destUrl;
	}

	public void setDestParam(String destParam) {
		this.destParam = destParam;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void doTag() throws IOException, JspException {
		getJspContext().getOut().print(
				ImportModuleUtil.getParentUrl(request, destUrl, destParam));
		// getJspContext().getOut().print(destUrl + "?" + destParam);
	}
}
