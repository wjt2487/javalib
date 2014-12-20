package cn.mxj.beans;

import java.util.Date;

/**
 * @author fl
 * 
 */
public class BaseFileBean extends BaseExBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 124994003923444758L;

	private int creatorId;

	private String creatorNumber;

	private String creatorName;

	private Date creationTime;

	private Date modificationTime;

	private String extensionName;

	private long fileSize;

	private String originalFileName;

	private String physicalFilePath;

	private String guidFileName;

	private boolean uploadedNewFile;

	public Date getCreationTime() {
		return this.creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public int getCreatorId() {
		return this.creatorId;
	}

	public void setCreatorId(int creatorId) {
		this.creatorId = creatorId;
	}

	public String getCreatorName() {
		return this.creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public String getCreatorNumber() {
		return this.creatorNumber;
	}

	public void setCreatorNumber(String creatorNumber) {
		this.creatorNumber = creatorNumber;
	}

	public String getExtensionName() {
		return this.extensionName;
	}

	public void setExtensionName(String extensionName) {
		this.extensionName = extensionName;
	}

	public long getFileSize() {
		return this.fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public String getGuidFileName() {
		return this.guidFileName;
	}

	public void setGuidFileName(String guidFileName) {
		this.guidFileName = guidFileName;
	}

	public Date getModificationTime() {
		return this.modificationTime;
	}

	public void setModificationTime(Date modificationTime) {
		this.modificationTime = modificationTime;
	}

	public String getOriginalFileName() {
		return this.originalFileName;
	}

	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}

	public String getPhysicalFilePath() {
		return this.physicalFilePath;
	}

	public void setPhysicalFilePath(String physicalFilePath) {
		this.physicalFilePath = physicalFilePath;
	}

	public boolean isUploadedNewFile() {
		return this.uploadedNewFile;
	}

	public void setUploadedNewFile(boolean uploadedNewFile) {
		this.uploadedNewFile = uploadedNewFile;
	}
}
