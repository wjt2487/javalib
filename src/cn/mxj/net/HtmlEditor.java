package cn.mxj.net;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import cn.mxj.io.FileUtil;

public class HtmlEditor {

	public static String HTML_EDITOR_PHYSICAL_INPUT_NAME = "HTML_EDITOR_PHYSICAL_PATH";

	private static String HTML_EDITOR_MAIN_URL = "/root/editor.jsp";

	private static String HTML_EDITOR_UPLOAD_DIR_NAME = "upload-file";

	private static String HTML_EDITOR_GET_PHYSICAL_PATH_URL = "/get-physical-path.do";

	public static String processContent(HttpServletRequest request,
			ServletContext servletContext, String content,
			String destBaseRelativeUrl, String destUrl, String destPath) {

		String editorUploadUrl = getEditorUrl(request, servletContext) + '/'
				+ HTML_EDITOR_UPLOAD_DIR_NAME + '/';
		String editorUploadPath = request
				.getParameter(HTML_EDITOR_PHYSICAL_INPUT_NAME)
				+ HTML_EDITOR_UPLOAD_DIR_NAME + '\\';

		String basePath = getServerRoot(request) + request.getContextPath()
				+ '/';
		String desetAbsoluteUrl = basePath + destBaseRelativeUrl;

		return processContent(content, editorUploadUrl, editorUploadPath,
				desetAbsoluteUrl, destUrl, destPath);
	}

	public static String processContent(String content, String editorUploadUrl,
			String editorUploadPath, String destAbsoluteUrl, String destUrl,
			String destPath) {

		// 1, 查看编辑器里的上传文件，并移动到Client应用的目标路径，最后替换URL
		String regEx = editorUploadUrl + "\\w+.\\w+";
		Pattern pattern = Pattern.compile(regEx);
		Matcher m = pattern.matcher(content);

		while (m.find()) {
			String fileUrl = m.group();
			String fileName = fileUrl.substring(fileUrl.lastIndexOf('/'));
			FileUtil.moveFile(editorUploadPath + fileName, destPath + fileName);
		}
		content = content.replace(editorUploadUrl, destUrl);

		// 2, 处理已有的资源，将其绝对URL替换为相对URL
		content = content.replace(destAbsoluteUrl, destUrl);

		return content;
	}

	public static String getContentForEditor(HttpServletRequest request,
			String content, String sourceUrl, String destBaseAbsoulateUrl) {
		String basePath = getServerRoot(request) + request.getContextPath()
				+ '/';
		return content.replace(sourceUrl, basePath + destBaseAbsoulateUrl);
	}

	public static String getContentForEditor(String content, String sourceUrl,
			String destUrl) {
		return content.replace(sourceUrl, destUrl);
	}

	public static String getEditorUploadUrl(HttpServletRequest request,
			ServletContext servletContext) {
		return getEditorUrl(request, servletContext) + '/'
				+ HTML_EDITOR_UPLOAD_DIR_NAME + '/';
	}

	public static String getEditorUploadPhysicalPath(
			HttpServletRequest request, ServletContext servletContext) {
		return servletContext.getRealPath("/") + '/'
				+ HTML_EDITOR_UPLOAD_DIR_NAME + '/';
	}

	public static String getEditorMainUrl(HttpServletRequest request,
			ServletContext servletContext) {
		return getEditorUrl(request, servletContext) + HTML_EDITOR_MAIN_URL;
	}

	public static String getGetEditorPhysicalPath(HttpServletRequest request,
			ServletContext servletContext) {
		return getEditorUrl(request, servletContext)
				+ HTML_EDITOR_GET_PHYSICAL_PATH_URL;
	}

	private static String getEditorUrl(HttpServletRequest request,
			ServletContext servletContext) {
		return getServerRoot(request) + '/'
				+ servletContext.getInitParameter("HtmlEditorAppName");
	}

	private static String getServerRoot(HttpServletRequest request) {
		return request.getScheme() + "://" + request.getServerName() + ':'
				+ request.getServerPort();
	}
}