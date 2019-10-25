package com.util;

import java.util.List;

public class SystemConfigures {

	private static List<String> roleUrls;

	public static List<String> getRoleUrls() {
		return roleUrls;
	}

	public static void setRoleUrls(List<String> roleUrls) {
		SystemConfigures.roleUrls = roleUrls;
	}
}
