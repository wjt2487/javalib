package cn.mxj.web;

import java.io.File;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import cn.mxj.io.SitePathInfo;

public class ContextParamUtil {

	private String configPath;

	public ContextParamUtil() {
		this.configPath = SitePathInfo.getInstance().getRootPhysicalPath()
				+ "WEB-INF/web.xml";
	}

	public String getConfigInfoPath() {
		return this.configPath;
	}

	@SuppressWarnings("unchecked")
	public String getSettingValue(String param) {
		try {
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(new File(configPath));
			Element root = document.getRootElement();

			for (Iterator<Element> i = root.elementIterator("context-param"); i
					.hasNext();) {
				Element e = i.next();
				if (e.elementText("param-name").equals(param)) {
					return e.elementText("param-value");
				}
			}
			return "";

		} catch (DocumentException e) {
			e.printStackTrace();
			return "";
		}
	}
}
