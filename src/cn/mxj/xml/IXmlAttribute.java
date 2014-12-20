/**
 * 
 */
package cn.mxj.xml;

/**
 * 支持 xml 形式的序列化，即实例可以把自身的全部属性序列化为一个 xml 节点
 * 
 * @author fl
 * 
 */
public interface IXmlAttribute {

	/**
	 * 将实体的属性作为 Attribute 写入 XmlDocBuilder 的当前节点
	 * 
	 * @param b
	 *            xml 文档构建器
	 */
	public void toXmlAttribute(XmlDocBuilder b);
}
