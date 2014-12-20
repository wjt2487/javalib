package cn.mxj.skoserver;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

public class BasicServlet extends HttpServlet {

	private static final long serialVersionUID = 6675411375033248525L;

	protected void _reportError(HttpServletResponse response,
			String operationType, boolean succes, ErrorCode code) {
		response.setHeader("Operation Type", operationType);
		response.setHeader("Successful", succes ? "1" : "0");
		response
				.setHeader("Error Code", new Integer(code.getCode()).toString());
	}

	protected void _writeLog(String method, boolean success, String detail) {

	}
}
