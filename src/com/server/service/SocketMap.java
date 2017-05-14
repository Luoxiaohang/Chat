package com.server.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class SocketMap {
	public static Map<String, SerConCliService> sm = new HashMap<String, SerConCliService>();

	public static void addSocket(String Id, SerConCliService sccs) {
		sm.put(Id, sccs);
	}

	public static SerConCliService getSocket(String Id) {
		return sm.get(Id);
	}

	public static String getOnLineFriendList() {
		String list = "";

		for (int i = 0; i < 50; i++) {
			if (sm.containsKey(String.valueOf(i))) {
				list = list + " " + String.valueOf(i);
			}
		}
		return list;
	}

	public static void closeAll() {
		for (Entry<String, SerConCliService> service : sm.entrySet()) {
			service.getValue().closeSocket();
		}

	}
}
