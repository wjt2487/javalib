package cn.mxj.web;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

/**
 * @author syg
 * 
 */
public class ImportModuleUtil {

	private static final String PARENT_URL_NAME = "parentUrl";

	private static final String MODULE_URL_NAME = "moduleUrl";

	private static final String PARENT_PARAM_LIST = "parentParamList";

	/*
	 * 
	 */
	public static String getModuleUrl(HttpServletRequest request,
			String parentUrl, String parentParamList, String moduleContextPath,
			String moduleHomePage, String moduleHomePageParam) {
		String parentParam = "";
		String moduleParam = "";
		String moduleUrl = "";
		boolean isHomePage = false;

		if (request.getParameter(MODULE_URL_NAME) == null) {
			isHomePage = true;
			moduleUrl = moduleHomePage;
		} else {
			moduleUrl = request.getParameter(MODULE_URL_NAME);
		}

		String[] parentParamNames = parentParamList.split(",");

		Enumeration nameEnum = request.getParameterNames();
		while (nameEnum.hasMoreElements()) {
			String paramName = (String) nameEnum.nextElement();
			if (isHomePage) {
				parentParam += parentParam.length() > 0 ? "&" : "";
				parentParam += paramName + "="
						+ request.getParameter(paramName);
			} else {
				if (paramName.compareTo(MODULE_URL_NAME) != 0) {
					if (_isInStringArray(parentParamNames, paramName)) {
						parentParam += parentParam.length() > 0 ? "&" : "";
						parentParam += paramName + "="
								+ request.getParameter(paramName);
					} else {
						moduleParam += moduleParam.length() > 0 ? "&" : "";
						moduleParam += paramName + "="
								+ request.getParameter(paramName);
					}
				}
			}
		}

		if (isHomePage) {
			moduleParam = moduleHomePageParam;
		}

		return request.getScheme() + "://" + request.getServerName() + ":"
				+ request.getServerPort() + "/" + moduleContextPath + "/"
				+ moduleUrl + "?" + PARENT_URL_NAME + "=" + parentUrl + "&"
				+ PARENT_PARAM_LIST + "=" + parentParamList + "&" + parentParam
				+ "&" + moduleParam;
	}

	/**
	 * 
	 * @param request
	 * @param moduleParamList
	 *            本页的参数列表，逗号分割
	 * @param destUrl
	 *            链接目标地址
	 * @param destParam
	 *            链接参数
	 * @return
	 */
	public static String getParentUrl(HttpServletRequest request,
			String destUrl, String destParam) {
		String parentParamList = SafeRequestValue.getSafeRequestStringValue(
				request, PARENT_PARAM_LIST, "");

		String parentParam = "";

		if (parentParamList.length() > 0) {
			String[] parentParams = parentParamList.split(",");
			Enumeration nameEnum = request.getParameterNames();
			while (nameEnum.hasMoreElements()) {
				String paramName = (String) nameEnum.nextElement();
				if (_isInStringArray(parentParams, paramName)) {
					parentParam += parentParam.length() > 0 ? "&" : "";
					parentParam += paramName + "="
							+ request.getParameter(paramName);
				}
			}
		}
		String parentUrl = request.getParameter(PARENT_URL_NAME);
		return parentUrl + "?" + parentParam + "&" + MODULE_URL_NAME + "="
				+ destUrl + "&" + destParam;
	}

	private static boolean _isInStringArray(String[] array, String dest) {
		boolean exist = false;
		for (int i = 0; i < array.length; ++i) {
			if (array[i].compareToIgnoreCase(dest) == 0) {
				exist = true;
				break;
			}
		}
		return exist;
	}
}
