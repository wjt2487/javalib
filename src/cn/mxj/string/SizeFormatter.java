package cn.mxj.string;

/**
 * size formatter for byte number
 * 
 * @author fl
 * 
 */
public class SizeFormatter {

	private long size;

	private static final double KB = 1024.0d; // ==2e10

	private static final double MB = 1048576.0d; // ==2e20

	private static final double GB = 1073741824.0d; // ==2e30

	private static final double TB = 1099511627776.0d; // ==2e40

	public SizeFormatter(long size) {
		this.size = size;
	}

	public long getOriginalSize() {
		return this.size;
	}

	public String getFormattedString() {
		return this.toString(2);
	}

	@Override
	public String toString() {
		return this.toString(2);
	}

	public String toString(int precision) {
		if (size < 0) {
			return "--";
		} else if (size < KB) {
			return size + " B";
		} else if (size < MB) {
			return this.format(size / KB, precision, "KB");
		} else if (size < GB) {
			return this.format(size / MB, precision, "MB");
		} else if (size < TB) {
			return this.format(size / GB, precision, "GB");
		} else {
			return this.format(size / TB, precision, "TB");
		}
	}

	private String format(double value, int precision, String suffix) {
		String pattern = "";
		if (precision <= 0) {
			pattern = "%1$.0f %2$s";
		} else if (precision < 10) {
			pattern = "%1$." + String.valueOf(precision) + "f %2$s";
		} else {
			pattern = "%1$.2f %2$s";
		}

		return String.format(pattern, value, suffix);
	}

	public static String format(long size) {
		SizeFormatter sf = new SizeFormatter(size);
		return sf.toString(2);
	}

	public static String format(long size, int precision) {
		SizeFormatter sf = new SizeFormatter(size);
		return sf.toString(precision);
	}

}
