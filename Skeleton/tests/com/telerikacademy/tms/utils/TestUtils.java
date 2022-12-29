package com.telerikacademy.tms.utils;

import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import com.telerikacademy.tms.models.UserImpl;
import com.telerikacademy.tms.models.contracts.User;

import java.util.Arrays;
import java.util.List;

import static com.telerikacademy.tms.utils.ModelsConstants.USER_VALID_NAME;

public class TestUtils {
	public static String getString(int length) {
		return "x".repeat(length);
	}

	public static List<String> getList(int size) {
		return Arrays.asList(new String[size]);
	}

}
