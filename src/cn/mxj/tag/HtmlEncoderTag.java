package cn.mxj.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import cn.mxj.string.StringEncoder;

public class HtmlEncoderTag extends SimpleTagSupport {

	private String src;

	public void setSrc(String src) {
		this.src = src;
	}

	public void doTag() throws IOException, JspException {
		getJspContext().getOut().print(StringEncoder.htmlEncode(src));
	}
}
