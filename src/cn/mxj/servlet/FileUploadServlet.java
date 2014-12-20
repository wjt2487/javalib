/**
 * 
 */
package cn.mxj.servlet;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import cn.mxj.business.WebConfig;
import cn.mxj.io.AppLogger;
import cn.mxj.io.FileUtil;

/**
 * @author fl
 * 
 */
public class FileUploadServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1590261169720899871L;

	protected AppLogger logger = AppLogger.getInstance();

	private String uploadTempPath;

	public String getUploadTempPath() {
		return uploadTempPath;
	}

	@Override
	public void init() throws ServletException {
		super.init();

		synchronized (this) {
			if (uploadTempPath == null) {
				uploadTempPath = WebConfig.getInstance("").getUploadTempPath();
				FileUtil.createFolders(uploadTempPath);
			}
		}

		System.out
				.println("INFO: --> FileUploadServlet has been initialized. Class: "
						+ this.getClass().getName());
	}

	/**
	 * 处理上传文件的请求。需提供的参数： filename: 必须保证是唯一的文件名称，如
	 * 81523216521214753210.txt，一般使用当前时间的毫秒数或额外加上一个随机整数作为文件名。将使用
	 * WebConfig.getUploadTempPath()
	 * 得到的路径和此文件名来组成完整的文件路径，然后将上传的文件流写入此文件。这样就实现把文件写入一个临时目录，然后在外部代码中再使用同样的
	 * filename 来拼装出完整的路径，并通过此路径把文件转移到其他具体的目录。具体目录信息请参考
	 * cn.skyclass.business.WebConfig 类。
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");

		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(4096);

		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setSizeMax(getMaxPostSize());

		try {
			String filePath = getDestFilePath(request);
			List fileItems = upload.parseRequest(request);
			Iterator iter = fileItems.iterator();
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				if (!item.isFormField()) {
					try {
						item.write(new File(filePath));
					} catch (Exception ex) {
						logger.exception(ex);
					}
				}
			}
		} catch (FileUploadException ex) {
			logger.exception(ex);
		}
	}

	/**
	 * 获取上传的文件所要存放的全路径
	 * 
	 * @param request
	 * @return
	 */
	protected String getDestFilePath(HttpServletRequest request) {
		String fileName = request.getParameter("filename");
		String filePath = getUploadTempPath() + fileName;
		return filePath;
	}

	protected int getMaxPostSize() {
		return 100 * 1024 * 1024;
	}
}
