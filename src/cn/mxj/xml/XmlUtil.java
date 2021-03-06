package cn.mxj.xml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PushbackInputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.dom4j.tree.DefaultElement;

import cn.mxj.io.AppLogger;
import cn.mxj.string.StringUtil;

/**
 * 用于 xml 文档的工具类
 * 
 * @author fl
 * 
 */
public class XmlUtil {

	/**
	 * load xml document by specified file path and encoding
	 * 
	 * @param xmlFilePath
	 * @param encoding
	 * @return
	 */
	public static Document loadDocument(String xmlFilePath, String encoding) {
		Document doc = null;
		Reader reader = null;
		try {
			reader = getReader(new FileInputStream(new File(xmlFilePath)),
					encoding);
			SAXReader sr = new SAXReader();
			sr.setEncoding(encoding);
			doc = sr.read(reader);
			reader.close();
		} catch (Exception ex) {
			AppLogger.getInstance().exception(ex);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return doc;
	}

	/**
	 * load xml document by specified file path
	 * 
	 * @param xmlFilePath
	 * @return null if file not exsit or Exception occur
	 */
	public static Document loadDocument(String xmlFilePath) {
		return loadDocument(xmlFilePath, "utf-8");
	}

	public static BufferedReader getReader(InputStream is, String encoding)
			throws IOException, UnsupportedEncodingException {
		PushbackInputStream pis = new PushbackInputStream(is, 1024);
		String bomEncoding = getBOMEncoding(pis);
		if (bomEncoding == null) {
			return new BufferedReader(new InputStreamReader(pis, encoding));
		} else {
			return new BufferedReader(new InputStreamReader(pis, bomEncoding));
		}
	}

	private static String getBOMEncoding(PushbackInputStream is)
			throws IOException {
		String encoding = null;
		int[] bytes = new int[3];
		bytes[0] = is.read();
		bytes[1] = is.read();
		bytes[2] = is.read();

		if (bytes[0] == 0xFE && bytes[1] == 0xFF) {
			encoding = "UTF-16BE";
			is.unread(bytes[2]);
		} else if (bytes[0] == 0xFF && bytes[1] == 0xFE) {
			encoding = "UTF-16LE";
			is.unread(bytes[2]);
		} else if (bytes[0] == 0xEF && bytes[1] == 0xBB && bytes[2] == 0xBF) {
			encoding = "UTF-8";
		} else {
			for (int i = bytes.length - 1; i >= 0; i--) {
				is.unread(bytes[i]);
			}
		}

		return encoding;
	}

	/**
	 * ��Ԫ�� get elements by xpath in document doc
	 * 
	 * @param doc
	 * @param xpath
	 * @return
	 */
	public static List<DefaultElement> getElements(Document doc, String xpath) {
		List<DefaultElement> es = new ArrayList<DefaultElement>();
		if (doc == null || xpath == null) {
			return es;
		}
		Element root = doc.getRootElement();
		try {
			List l = root.selectNodes(xpath);
			for (Object o : l) {
				if (o instanceof DefaultElement) {
					es.add((DefaultElement) o);
				}
			}
		} catch (Exception ex) {
			AppLogger.getInstance().exception(ex);
		}
		return es;
	}

	/**
	 * write xml document to a file
	 * 
	 * @param fileName
	 *            target file full name path
	 * @param document
	 *            the xml doc to write
	 * @param coding
	 *            xml doc encoding
	 * @return whether write successfully
	 */
	public static boolean writeDocument(String fileName, Document document,
			String coding) {
		if (StringUtil.isNullOrEmpty(fileName) || document == null) {
			return false;
		}
		if (StringUtil.isNullOrEmpty(coding)) {
			coding = "utf-8";
		}
		try {
			OutputFormat of = OutputFormat.createPrettyPrint();
			of.setEncoding(coding);
			XMLWriter out = new XMLWriter(new FileOutputStream(new File(
					fileName)), of);
			out.write(document);
			out.close();
			return true;
		} catch (Exception e) {
			AppLogger.getInstance().exception(e);
			return false;
		}
	}

	/**
	 * write xml document to a file with utf-8 encoding
	 * 
	 * @param fileName
	 * @param document
	 * @return
	 */
	public static boolean writeDocument(String fileName, Document document) {
		return writeDocument(fileName, document, "utf-8");
	}

	/**
	 * write xml document with given writer
	 * 
	 * @param writer
	 * @param document
	 * @param coding
	 * @return
	 * @throws IOException
	 */
	public static boolean writeDocument(Writer writer, Document document,
			String coding) throws IOException {
		if (writer == null || document == null) {
			return false;
		}
		if (StringUtil.isNullOrEmpty(coding)) {
			coding = "utf-8";
		}
		try {
			OutputFormat of = OutputFormat.createPrettyPrint();
			of.setEncoding(coding);
			XMLWriter out = new XMLWriter(writer, of);
			out.write(document);
			out.close();
			return true;
		} catch (IOException e) {
			AppLogger.getInstance().exception(e);
			throw e;
		}
	}
}
