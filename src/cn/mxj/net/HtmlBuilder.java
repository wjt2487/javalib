package cn.mxj.net;

/**
 * 用于组装 html 代码的工具类
 * 
 * @author fl
 * 
 */
public class HtmlBuilder {

	private StringBuffer sb;

	{
		this.sb = new StringBuffer();
	}

	public HtmlBuilder appendContent(String content, boolean htmlEncode) {
		this.sb.append(content);
		return this;
	}

	public HtmlBuilder appendContent(Object content, boolean htmlEncode) {
		this.sb.append(content);
		return this;
	}

	public HtmlBuilder appendContent(int content) {
		this.sb.append(content);
		return this;
	}

	public HtmlBuilder appendContent(String content) {
		this.sb.append(content);
		return this;
	}

	public HtmlBuilder appendNewLine(int lineCount) {
		for (int i = 0; i < lineCount; i++) {
			this.sb.append("<br>");
		}
		return this;
	}

	public HtmlBuilder appendSpace(int spaceCount) {
		for (int i = 0; i < spaceCount; i++) {
			this.sb.append("&nbsp;");
		}
		return this;
	}

	public HtmlBuilder appendDivBeginTag() {
		this.sb.append("<div>");
		return this;
	}

	public HtmlBuilder appendDivEndTag() {
		this.sb.append("</div>");
		return this;
	}

	public HtmlBuilder appendSpanBeginTag() {
		this.sb.append("<span>");
		return this;
	}

	public HtmlBuilder appendSpanEndTag() {
		this.sb.append("</span>");
		return this;
	}

	public HtmlBuilder appendTableBeginTag() {
		this.sb.append("<table>");
		return this;
	}

	public HtmlBuilder appendTableEndTag() {
		this.sb.append("</table>");
		return this;
	}

	public HtmlBuilder appendTrBeginTag() {
		this.sb.append("<tr>");
		return this;
	}

	public HtmlBuilder appendTrEndTag() {
		this.sb.append("</tr>");
		return this;
	}

	public HtmlBuilder appendTdBeginTag() {
		this.sb.append("<td>");
		return this;
	}

	public HtmlBuilder appendTdEndTag() {
		this.sb.append("</td>");
		return this;
	}

	/**
	 * 在最近插入的标记内添加属性值，应在插入开始标记后立即调用此方法来添加属性
	 * 
	 * @param name
	 * @param value
	 * @return
	 */
	public HtmlBuilder appendAttribute(String name, String value) {
		int pos = this.sb.length() - 1; // before >
		String attr = String.format(" %1$s=\"%2$s\"", name, value.replace("\"",
				"“"));
		this.sb.insert(pos, attr);
		return this;
	}

	public HtmlBuilder appendAttribute(String name, int value) {
		return appendAttribute(name, String.valueOf(value));
	}

	public HtmlBuilder appendInputTag() {
		this.sb.append("<input>");
		return this;
	}

	@Override
	public String toString() {
		return this.sb.toString();
	}

}
