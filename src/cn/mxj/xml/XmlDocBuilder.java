/**
 * 
 */
package cn.mxj.xml;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import cn.mxj.exception.ExceptionLevel;
import cn.mxj.io.AppLogger;
import cn.mxj.util.BeansUtil;

/**
 * xml 文档构建器
 * 
 * @author fl
 * 
 */
public class XmlDocBuilder {

	/**
	 * 使用给定的根节点名称创建文档
	 * 
	 * @param rootName
	 *            根节点名称
	 * @return 新创建的根节点
	 */
	public Element init(String rootName) {
		this.doc = DocumentHelper.createDocument();
		this.rootElem = this.doc.addElement(rootName);
		this.currentElem = this.rootElem;
		return this.rootElem;
	}

	/**
	 * 使用 utf-8 编码将此文档写入文件
	 * 
	 * @param fileName
	 *            目标文件全路径
	 * @return 是否写入成功
	 */
	public boolean writeToFile(String fileName) {
		return XmlUtil.writeDocument(fileName, doc);
	}

	/**
	 * 对当前节点添加属性
	 * 
	 * @param name
	 * @param value
	 * @return 当前节点
	 */
	public Element addAttribute(String name, String value) {
		return this.currentElem.addAttribute(name, value);
	}

	/**
	 * 对当前节点添加属性
	 * 
	 * @param name
	 * @param value
	 * @return 当前节点
	 */
	public Element addAttribute(String name, Object value) {
		String str = "";
		if (value != null) {
			str = value.toString();
		}
		if (this.boolAsInt && (value instanceof Boolean)) {
			str = ((Boolean) value) ? "1" : "0";
		}
		return this.currentElem.addAttribute(name, str);
	}

	/**
	 * 将给定对象的 Property 作为 Attribute 加入当前节点，调用属性的 toString() 方法
	 * 
	 * @param obj
	 * @return 当前节点
	 */
	public Element addAttributeWithProperty(Object obj) {
		return this.addAttributeWithProperty(obj, true);
	}

	/**
	 * 将给定对象的简单的 Property 作为 Attribute 加入当前节点，调用属性的 toString() 方法
	 * 
	 * @param obj
	 * @param simple
	 *            是否仅使用简单的 Property ，包括：int, long, float, String ...
	 * @return 当前节点
	 */
	public Element addAttributeWithProperty(Object obj, boolean simple) {
		Method[] ms = obj.getClass().getMethods();
		for (Method m : ms) {
			if (simple && !this.isSimpleType(m.getReturnType())) {
				continue;
			}
			if (m.getName().startsWith("get")) {
				try {
					String name = this.validateName(BeansUtil.getPropertyName(m
							.getName()));
					this.addAttribute(name, m.invoke(obj));
				} catch (Exception e) {
					AppLogger.getInstance().exception(e,
							ExceptionLevel.CanIgnore);
				}
			}
		}
		Field[] fs = obj.getClass().getFields();
		for (Field f : fs) {
			if (simple && !this.isSimpleType(f.getType())) {
				continue;
			}
			try {
				this.addAttribute(this.validateName(f.getName()), f.get(obj));
			} catch (Exception e) {
				AppLogger.getInstance().exception(e, ExceptionLevel.CanIgnore);
			}
		}
		return this.currentElem;
	}

	private String validateName(String name) {
		if (this.upperCaseFirstChar) {
			name = name.substring(0, 1).toUpperCase() + name.substring(1);
		}
		return name;
	}

	private boolean isSimpleType(Class cla) {
		if (cla.isPrimitive()) {
			return true;
		}
		Class[] clas = { String.class };
		for (Class c : clas) {
			if (c.equals(cla)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 在当前节点下添加新节点，并改变当前节点至新节点
	 * 
	 * @param name
	 *            新节点名称
	 * @return 当前节点
	 */
	public Element addElement(String name) {
		this.currentElem = this.currentElem.addElement(name);
		return this.currentElem;
	}

	/**
	 * 在当前节点下添加新节点，不改变当前节点
	 * 
	 * @param name
	 *            新节点名称
	 * @return 新建立的节点
	 */
	public Element appendElement(String name) {
		return this.currentElem.addElement(name);
	}

	/**
	 * 将当前节点移至其父节点
	 * 
	 * @return 自身引用
	 */
	public XmlDocBuilder backToParentElement() {
		Element p = this.currentElem.getParent();
		if (p != null) {
			this.currentElem = p;
		}
		return this;
	}

	private boolean boolAsInt;

	private boolean upperCaseFirstChar;

	private Element currentElem;

	private Element rootElem;

	private Document doc;

	/**
	 * 当前处理的文档
	 * 
	 * @return
	 */
	public Document getDoc() {
		return this.doc;
	}

	/**
	 * 当前处理的元素
	 * 
	 * @return
	 */
	public Element getCurrentElem() {
		return this.currentElem;
	}

	/**
	 * 文档的根元素
	 * 
	 * @return
	 */
	public Element getRootElem() {
		return this.rootElem;
	}

	public boolean isBoolAsInt() {
		return this.boolAsInt;
	}

	/**
	 * 将 boolean 值转为 int 值输出，false=0, true=1
	 * 
	 * @param boolAsInt
	 */
	public void setBoolAsInt(boolean boolAsInt) {
		this.boolAsInt = boolAsInt;
	}

	public boolean isUpperCaseFirstChar() {
		return this.upperCaseFirstChar;
	}

	/**
	 * 将由 Object 的 Property 自动生成的 Attribute 的第一个字符大写，(name -> Name)
	 * 
	 * @param upperCaseFirstChar
	 */
	public void setUpperCaseFirstChar(boolean upperCaseFirstChar) {
		this.upperCaseFirstChar = upperCaseFirstChar;
	}

}
