/**
 * 
 */
package cn.mxj.exception;

/**
 * @author fl
 * 
 */
public class DaoException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4763121318284410494L;

	public DaoException() {
	}

	public DaoException(String message) {
		super(message);
	}

	public DaoException(Throwable cause) {
		super(cause);
	}

	public DaoException(String message, Throwable cause) {
		super(message, cause);
	}
}
