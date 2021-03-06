package cn.mxj.skoserver;

public enum ErrorCode {

	EC_SUCCESSFULLY(0x0000), EC_INVALID_AGENT(0x0001), EC_INVALID_PARAM(0x0002), EC_CANT_OPEN_DB(
			0x0004), EC_NOSUCH_COURSE(0x0008),
	// EC_INVALID_COURSE_LOG = 0x0010,
	// EC_COURSE_ALREADYUSED = 0x0011,
	// EC_TOOMUCH_COURSE = 0x0012,
	EC_INVALID_DOG(0x0020), EC_ERROR_LOGIN(0x0030), EC_CANT_ENTER_COURSE(0x0031), EC_CANT_SAVE_SESSION(
			0x0032), EC_UNEXPECTED(0x0040), EC_INVALID_HEADER(0x0080), EC_INVALID_SESSION(
			0x0100), EC_NO_RIGHT(0x0200), EC_CLIENT_EXPIRED(0x0300), EC_INVALID_MODULE(
			0x0400);

	private int code;

	public int getCode() {
		return code;
	}

	private ErrorCode(int code) {
		this.code = code;
	}
}
