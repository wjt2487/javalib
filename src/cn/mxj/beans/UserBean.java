package cn.mxj.beans;

/**
 * @author fl
 * 
 */
public class UserBean extends BaseExBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7894980591658492962L;

	private String password;

	private String trueName;

	private String nickname;

	private String email;

	private int role;

	private int type;

	private boolean enabled;

	private String number;

	private int ownerAcademyId;

	private int ownerSpecialityId;

	private int ownerClassId;

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isEnabled() {
		return this.enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getNumber() {
		return this.number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public int getOwnerAcademyId() {
		return this.ownerAcademyId;
	}

	public void setOwnerAcademyId(int ownerAcademyId) {
		this.ownerAcademyId = ownerAcademyId;
	}

	public int getOwnerClassId() {
		return this.ownerClassId;
	}

	public void setOwnerClassId(int ownerClassId) {
		this.ownerClassId = ownerClassId;
	}

	public int getOwnerSpecialityId() {
		return this.ownerSpecialityId;
	}

	public void setOwnerSpecialityId(int ownerSpecialityId) {
		this.ownerSpecialityId = ownerSpecialityId;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getRole() {
		return this.role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public String getTrueName() {
		return this.trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public int getType() {
		return this.type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
