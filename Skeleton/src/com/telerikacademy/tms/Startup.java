package com.telerikacademy.tms;

import com.telerikacademy.tms.core.TaskManagementEngineImpl;
import com.telerikacademy.tms.core.contracts.Engine;

public class Startup {
	public static void main(String[] args) {

		Engine engine = new TaskManagementEngineImpl();
		engine.start();

	}
}