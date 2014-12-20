package cn.mxj.skoserver;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import cn.mxj.string.StringWrapper;

public class MultipartParser {

	private Map<String, byte[]> _mapData = new HashMap<String, byte[]>();

	public MultipartParser(HttpServletRequest request) {
		try {
			_parse(request);
		} catch (Exception e) {
			// System.out.println("Parse Error:" + e.getMessage());
		}
	}

	public int getSafeIntValue(String key, int defaultValue) {
		int value = defaultValue;
		if (_mapData.containsKey(key)) {
			byte[] data = (_mapData.get(key));
			String s = new String(data, 0, data.length);
			value = StringWrapper.newInstance(s).intValue(defaultValue);
		}
		return value;
	}

	public String getSafeStringValue(String key, String defaultValue) {
		String value = defaultValue;
		if (_mapData.containsKey(key)) {
			byte[] data = (_mapData.get(key));
			value = new String(data, 0, data.length);
		}
		return value;
	}

	public byte[] getData(String key) {
		if (_mapData.containsKey(key)) {
			return (_mapData.get(key));
		} else {
			return new byte[0];
		}
	}

	private void _parse(HttpServletRequest request) throws Exception {
		ServletInputStream inputStream = request.getInputStream();

		final int BUFFER_SIZE = 4096;
		byte[] buffer = new byte[BUFFER_SIZE];
		int readCount = -1;

		while (true) {
			// 1, boundary
			inputStream.readLine(buffer, 0, buffer.length);

			// 2, name
			readCount = inputStream.readLine(buffer, 0, buffer.length);
			if (readCount <= 0) { // end
				break;
			}
			String line = new String(buffer, 0, readCount - 2);
			String name = line.substring(
					"Content-Disposition: form-data; name=".length() + 1, line
							.indexOf("\"",
									"Content-Disposition: form-data; name="
											.length() + 2));

			// 3, \r\n or Content-length + \r\n
			int contentLength = 0;
			readCount = inputStream.readLine(buffer, 0, buffer.length);

			if (readCount > 2) { // > "\r\n".length
				line = new String(buffer, 0, readCount - 2);
				contentLength = Integer.parseInt(line
						.substring("Content-Length: ".length()));
				inputStream.readLine(buffer, 0, buffer.length);
			}

			// 4, value or data
			if (contentLength > 0) {
				byte[] dataBuffer = new byte[contentLength];

				// TODO
				// inputStream.read似乎有bug，read函数当指定读取的数据较大时，虽然stream里可以读取的数据，但总是返回一个小于该值的数字，因此使用一个循环来读取
				int readed = 0;
				while (readed < contentLength) {
					readed += inputStream.read(dataBuffer, readed,
							contentLength - readed);
				}
				_mapData.put(name, dataBuffer);

				inputStream.read(buffer, 0, 2); // \r\n
			} else {
				readCount = inputStream.readLine(buffer, 0, buffer.length);
				line = new String(buffer, 0, readCount - 2);
				_mapData.put(name, line.getBytes());
			}
		}
	}
}
