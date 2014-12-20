/**
 * 
 */
package cn.mxj.business;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.tree.DefaultElement;

import cn.mxj.exception.ExceptionLevel;
import cn.mxj.io.AppLogger;
import cn.mxj.io.SitePathInfo;
import cn.mxj.string.StringEncoder;
import cn.mxj.string.StringUtil;
import cn.mxj.xml.XmlUtil;

/**
 * 自定义的配置文件的读取类。 配置文件格式参照此包中的 web-config.xml 文件
 * 
 * @author fl
 * 
 */
public class WebConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8788849879524699813L;

	private static final String DEFAULT_CONFIG_FILE_PATH = "WEB-INF/data/web-config.xml";

	private static WebConfig instance;

	private static Object locker = WebConfig.class;

	/**
	 * 获取配置文件的实例
	 * 
	 * @param configFilePath
	 *            要读取的配置文件的相对于站点根目录的路径，如此值为空字符串或是 null，则使用默认路径。 默认值是
	 *            WEB-INF/data/web-config.xml
	 * @return
	 */
	public static WebConfig getInstance(String configFilePath) {
		synchronized (locker) {
			if (WebConfig.instance == null) {
				WebConfig.instance = new WebConfig(configFilePath);
			} else if (WebConfig.instance.expired()) {
				WebConfig.instance.resetConfig();
			}
			return WebConfig.instance;
		}
	}

	/**
	 * 获取配置文件的实例，使用默认的配置文件
	 * 
	 * @return
	 */
	public static WebConfig getInstance() {
		return getInstance(DEFAULT_CONFIG_FILE_PATH);
	}

	/**
	 * 使用默认的配置文件路径（WEB-INF/data/web-config.xml）
	 * 
	 */
	protected WebConfig() {
		this(DEFAULT_CONFIG_FILE_PATH);
	}

	/**
	 * 使用自定义的配置文件路径
	 * 
	 * @param configFilePath
	 *            示例：WEB-INF/data/web-config.xml
	 */
	protected WebConfig(String configFilePath) {
		if (StringUtil.isNullOrEmpty(configFilePath)) {
			configFilePath = DEFAULT_CONFIG_FILE_PATH;
		}

		this.logger = AppLogger.getInstance();
		this.configFileFullPath = SitePathInfo.getInstance()
				.getRootPhysicalPath()
				+ configFilePath;

		this.resetConfig();
	}

	private boolean configLoaded;

	private long configFileLastModifiedTime;

	private String configFileFullPath;

	private Document configDoc;

	private AppLogger logger;

	public boolean isConfigLoaded() {
		return this.configLoaded;
	}

	public String getConfigFileFullPath() {
		return this.configFileFullPath;
	}

	protected Document getConfigDoc() {
		return this.configDoc;
	}

	protected AppLogger getLogger() {
		return this.logger;
	}

	/**
	 * 重置配置文件，即重新读取配置文件的信息
	 * 
	 */
	public void resetConfig() {
		File file = new File(this.configFileFullPath);
		if (file.exists()) {
			this.configDoc = XmlUtil.loadDocument(this.configFileFullPath);
			this.configLoaded = (this.configDoc != null);
			this.configFileLastModifiedTime = file.lastModified();
			if (this.configLoaded) {
				this.parseContent();
			}
		} else {
			logger.info("Can't find config file. Path: "
					+ this.configFileFullPath);
			this.configDoc = null;
			this.configLoaded = false;
			this.configFileLastModifiedTime = 0;
		}
	}

	public boolean expired() {
		File file = new File(this.configFileFullPath);
		return !file.exists()
				|| file.lastModified() != this.configFileLastModifiedTime;
	}

	/**
	 * 开始解析配置文件的内容
	 */
	protected void parseContent() {
		// do nothing
	}

	/**
	 * 读取 appSettings 配置节的指定键值的数据
	 * 
	 * @param key
	 * @return 如不存在指定的键值，则返回空字符串("")
	 */
	public String getAppSettingValue(String key) {
		List<String> l = this.getAppSettingValues(key);
		if (l.size() > 0) {
			return l.get(0);
		} else {
			String msg = "Can't find value in appSettings section in config file. key = "
					+ key;
			logger.info(msg);
			return "";
		}
	}

	/**
	 * 读取 appSettings 配置节的指定键值的多个数据
	 * 
	 * @param key
	 * @return 字符串值列表
	 */
	public List<String> getAppSettingValues(String key) {
		List<String> values = new ArrayList<String>();
		List<DefaultElement> es = this
				.getElements("/web-config/appSettings/add[@key='" + key + "']");
		for (DefaultElement e : es) {
			try {
				values.add(StringEncoder
						.encode(e.attribute("value").getValue()));
			} catch (Exception ex) {
				logger.exception(ex, ExceptionLevel.CanIgnore);
			}
		}
		return values;
	}

	/**
	 * 根据提供的 xpath 来获取所有匹配的元素
	 * 
	 * @param xpath
	 * @return
	 */
	protected List<DefaultElement> getElements(String xpath) {
		if (this.configDoc != null) {
			return XmlUtil.getElements(this.configDoc, xpath);
		} else {
			return new ArrayList<DefaultElement>(0);
		}
	}

	/**
	 * 根据提供的 xpath 来获取匹配的第一个元素
	 * 
	 * @param xpath
	 * @return 如果没有匹配的元素，则返回 null
	 */
	protected DefaultElement getElement(String xpath) {
		List<DefaultElement> es = this.getElements(xpath);
		if (es.size() > 0) {
			return es.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 上传文件时使用的路径，默认路径是网站根目录下的 data 目录
	 * 
	 * @return example: {...Tomcat 5.5}/webapps/tester-web/data/
	 */
	public String getUploadPath() {
		String value = this.getAppSettingValue("UploadPath");
		String def = SitePathInfo.getInstance().getRootPhysicalPath() + "data/";
		return StringUtil.isNullOrEmpty(value) ? def : value;
	}

	/**
	 * 上传文件时使用的临时路径，默认路径是上传路径下的 __temp 目录
	 * 
	 * @return example: {...Tomcat 5.5}/webapps/tester-web/data/__temp/
	 */
	public String getUploadTempPath() {
		String value = this.getAppSettingValue("UploadTempPath");
		String def = this.getUploadPath() + "__temp/";
		return StringUtil.isNullOrEmpty(value) ? def : value;
	}

	/**
	 * 多媒体编辑器存放文件的访问地址
	 * 
	 * @return example: /htmleditor/upload-file/
	 */
	public String getHtmlEditorUploadUrl() {
		String value = this.getAppSettingValue("HtmlEditorUploadUrl");
		String def = "/htmleditor/upload-file/";
		return StringUtil.isNullOrEmpty(value) ? def : value;
	}

	/**
	 * 多媒体编辑器存放文件的路径
	 * 
	 * @return example: {...}/webapps/htmleditor/upload-file/
	 */
	public String getHtmlEditorUploadPath() {
		String value = this.getAppSettingValue("HtmlEditorUploadPath");
		String def = SitePathInfo.getInstance().getTomcatAppsPath()
				+ this.getHtmlEditorUploadUrl();
		return StringUtil.isNullOrEmpty(value) ? def : value;
	}

}
