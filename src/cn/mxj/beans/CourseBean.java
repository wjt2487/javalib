/**
 * 
 */
package cn.mxj.beans;

/**
 * @author fl
 * 
 */
public class CourseBean extends BaseExBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2803328201897603750L;

	private String classHour;

	private String credit;

	private String shortName;

	private String englishName;

	private String introduction;

	private String number;

	private int academyId;

	private String courseOwner;

	private int courseOwnerId;

	public int getAcademyId() {
		return this.academyId;
	}

	public void setAcademyId(int academyId) {
		this.academyId = academyId;
	}

	public String getClassHour() {
		return this.classHour;
	}

	public void setClassHour(String classHour) {
		this.classHour = classHour;
	}

	public String getCourseOwner() {
		return this.courseOwner;
	}

	public void setCourseOwner(String courseOwner) {
		this.courseOwner = courseOwner;
	}

	public int getCourseOwnerId() {
		return this.courseOwnerId;
	}

	public void setCourseOwnerId(int courseOwnerId) {
		this.courseOwnerId = courseOwnerId;
	}

	public String getCredit() {
		return this.credit;
	}

	public void setCredit(String credit) {
		this.credit = credit;
	}

	public String getEnglishName() {
		return this.englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

	public String getIntroduction() {
		return this.introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getNumber() {
		return this.number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getShortName() {
		return this.shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
}
