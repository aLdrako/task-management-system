package com.telerikacademy.tms.utils;

import static com.telerikacademy.tms.utils.TestUtils.getString;

public class ModelsConstants {
	private static final int TEAM_MIN_LEN = 5;
	private static final int TEAM_MAX_LEN = 15;
	private static final int BOARD_MIN_LEN = 5;
	private static final int BOARD_MAX_LEN = 10;
	private static final int USER_MIN_LEN = 5;
	private static final int USER_MAX_LEN = 15;
	private static final int TITLE_MIN_LEN = 10;
	private static final int TITLE_MAX_LEN = 50;
	private static final int DESCRIPTION_MIN_LEN = 10;
	private static final int DESCRIPTION_MAX_LEN = 500;

	public static final String TEAM_VALID_NAME = getString(TEAM_MIN_LEN + 1);
	public static final String TEAM_INVALID_NAME = getString(TEAM_MAX_LEN + 1);
	public static final String BOARD_VALID_NAME = getString(BOARD_MIN_LEN + 1);
	public static final String BOARD_INVALID_NAME = getString(BOARD_MAX_LEN + 1);
	public static final String USER_VALID_NAME = getString(USER_MIN_LEN + 1);
	public static final String USER_INVALID_NAME = getString(USER_MAX_LEN + 1);
	public static final String TASK_VALID_NAME = getString(TITLE_MIN_LEN + 1);
	public static final String TASK_INVALID_NAME = getString(TITLE_MAX_LEN + 1);
	public static final String DESCRIPTION_VALID_NAME = getString(DESCRIPTION_MIN_LEN + 1);
	public static final String DESCRIPTION_INVALID_NAME = getString(DESCRIPTION_MAX_LEN + 1);

	public static final String COMMENT_MESSAGE = "Comment message";

}
