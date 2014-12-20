package cn.mxj.exception;

/**
 * 自定义异常类
 * 
 * @author fl
 * 
 */
public class CustomException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4491863436515223448L;

	public CustomException() {
	}

	public CustomException(String message) {
		super(message);
	}

	public CustomException(Throwable cause) {
		super(cause);
	}

	public CustomException(String message, Throwable cause) {
		super(message, cause);
	}

}
