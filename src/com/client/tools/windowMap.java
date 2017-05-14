package com.client.tools;

import java.util.HashMap;

import com.client.window.ChatWindow;

public class windowMap {
	public static HashMap<String, ChatWindow> whm = new HashMap<String, ChatWindow>();

	public static void addWindow(String Id, ChatWindow chat) {
		whm.put(Id, chat);
	}

	public static ChatWindow getChatWindow(String Id) {
		return whm.get(Id);
	}
}
