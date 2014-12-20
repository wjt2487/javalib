/**
 * 
 */
package cn.mxj.io;

import java.util.ArrayList;

/**
 * CSV 格式的文件生成器
 * 
 * @author fl
 * 
 */
public class CSVFileBuilder {

	public CSVFileBuilder() {
		this.buffer = new StringBuffer();
	}

	private StringBuffer buffer;

	protected final String FIELD_SEPARATOR = ",";

	protected final String LINE_SEPARATOR_R = "\r";

	protected final String LINE_SEPARATOR_RN = "\r\n";

	protected final String DOUBLE_QUOTE = "\"";

	/**
	 * 在文件中追加一个域分隔符
	 * 
	 * @return 自身引用
	 */
	public CSVFileBuilder appendFieldSeparator() {
		this.buffer.append(FIELD_SEPARATOR);
		return this;
	}

	/**
	 * 在文件中追加一个行分隔符
	 * 
	 * @return 自身引用
	 */
	public CSVFileBuilder appendLineSeparator() {
		this.buffer.append(LINE_SEPARATOR_RN);
		return this;
	}

	/**
	 * 直接在文件中追加文本，不做任何文本处理
	 * 
	 * @param text
	 * @return 自身引用
	 */
	public CSVFileBuilder appendText(String text) {
		this.buffer.append(text);
		return this;
	}

	/**
	 * 在文件中追加一个内容域，自动进行必要的格式处理，并在最后添加一个域分隔符
	 * 
	 * @param field
	 * @return 自身引用
	 */
	public CSVFileBuilder appendField(String field) {
		this.buffer.append(this.processText(field)).append(FIELD_SEPARATOR);
		return this;
	}

	/**
	 * 在文件中追加一行记录，自动进行必要的格式处理，并在此行前面添加一个行分隔符
	 * 
	 * @param fields
	 * @return 自身引用
	 */
	public CSVFileBuilder appendLine(Object[] fields) {
		this.buffer.append(LINE_SEPARATOR_RN);
		for (Object f : fields) {
			this.appendField(f.toString());
		}
		return this;
	}

	/**
	 * 在文件中追加一行记录，自动进行必要的格式处理，并在此行前面添加一个行分隔符
	 * 
	 * @param fields
	 * @return 自身引用
	 */
	public CSVFileBuilder appendLine(String[] fields) {
		this.buffer.append(LINE_SEPARATOR_RN);
		for (String f : fields) {
			this.appendField(f);
		}
		return this;
	}

	/**
	 * 在文件中追加一行记录，自动进行必要的格式处理，并在此行前面添加一个行分隔符
	 * 
	 * @param fields
	 * @return 自身引用
	 */
	public CSVFileBuilder appendLine(ArrayList<String> fields) {
		this.buffer.append(LINE_SEPARATOR_RN);
		for (String f : fields) {
			this.appendField(f);
		}
		return this;
	}

	/**
	 * 获取构建好的文件内容
	 * 
	 * @return
	 */
	public String getContent() {
		String content = this.buffer.toString();
		if (content.startsWith(LINE_SEPARATOR_RN)) {
			content = content.substring(LINE_SEPARATOR_RN.length());
		} else if (content.startsWith(LINE_SEPARATOR_R)) {
			content = content.substring(LINE_SEPARATOR_R.length());
		}

		return content;
	}

	/**
	 * 写入外部文件
	 * 
	 * @param filename
	 */
	public void writeToFile(String filename) {
		FileHelper.writeFile(filename, this.getContent(), false);
	}
	
	public void writeToFile(String filename,String encoding) {
		FileHelper.writeFile(filename, this.getContent(), false,encoding);
	}

	public String processText(String text) {
		text = text.trim();

		// 字符序列中是否含有关键字符，eg: str,str2
		boolean hasKeyChar = text.contains(FIELD_SEPARATOR)
				|| text.contains(LINE_SEPARATOR_R)
				|| text.contains(LINE_SEPARATOR_RN);

		// 全部字符序列处于 "" 的包括中，eg: "str1"
		boolean quoted = text.matches("^\".*\"$");

		if (hasKeyChar || quoted) {
			// 将字符序列中已有的 " 全部替换成 ""，然后将其用 "" 包括起来
			// eg: str,"str1" --> "str,""str1"""
			text = text.replaceAll(DOUBLE_QUOTE, DOUBLE_QUOTE + DOUBLE_QUOTE);
			text = DOUBLE_QUOTE + text + DOUBLE_QUOTE;
		}
		return text;
	}
}

//
// The CSV File Format Specification
//
// # Each record is one line ...but
// A record separator may consist of a line feed (ASCII/LF=0x0A), or a carriage
// return and line feed pair (ASCII/CRLF=0x0D 0x0A).
// ...but: fields may contain embedded line-breaks (see below) so a record may
// span more than one line.
//
// # Fields are separated with commas.
// Example John,Doe,120 any st.,"Anytown, WW",08123
//
// # Leading and trailing space-characters adjacent to comma field separators
// are ignored.
// So John , Doe ,... resolves to "John" and "Doe", etc. Space characters can be
// spaces, or tabs.
//
// # Fields with embedded commas must be delimited with double-quote characters.
// In the above example. "Anytown, WW" had to be delimited in double quotes
// because it had an embedded comma.
//
// # Fields that contain double quote characters must be surounded by
// double-quotes, and the embedded double-quotes must each be represented by a
// pair of consecutive double quotes.
// So, John "Da Man" Doe would convert to "John ""Da Man""",Doe, 120 any st.,...
//
// # A field that contains embedded line-breaks must be surounded by
// double-quotes
//
// # Fields with leading or trailing spaces must be delimited with double-quote
// characters.
// So to preserve the leading and trailing spaces around the last name above:
// John ," Doe ",...
//
// # Fields may always be delimited with double quotes.
// The delimiters will always be discarded.
//
// # The first record in a CSV file may be a header record containing column
// (field) names
//
//
// CSV格式的具体规范如下:
// 1. 每条记录为一行
// 2. 除非记录的字段中包含一个回车/换行符号,这样会导致这条记录保存为多行。换行的字段必须使用双引号括起来。
// 3. 字段之间使用逗号进行分割
// 4. 字段的前置空格和后置空格都被自动忽略。可以使用双引号括起来以保持这些空格。
// 5. 字段中如果包含逗号的值，那么这个字段必须使用双引号括起来。
// 6. 字段中包含双引号的时候使用两个双引号进行转义。
// 7. 第一行的数据可以是列的头信息
//
