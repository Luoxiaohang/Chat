package com.client.service;

import java.io.ObjectInputStream;
import java.net.Socket;

import com.client.tools.windowMap;
import com.client.window.ChatWindow;
import com.common.bean.TextMessage;
import com.common.bean.MessageType;

public class CliConSerThread extends Thread implements MessageType {
	Socket s = null;

	public CliConSerThread(Socket s) {
		this.s = s;
	}

	public Socket getSocket() {
		return this.s;
	}

	public void run() {

		while (true) {
			try {
				ObjectInputStream ois = new ObjectInputStream(
						this.s.getInputStream());
				TextMessage tm = (TextMessage) ois.readObject();

				if (tm.getMessageType().equals(message)) {
					System.out.println("客户端" + tm.getReceiver() + "收到"
							+ tm.getSender() + "发送的消息");
					ChatWindow chat = windowMap.getChatWindow(tm.getReceiver());
					chat.ShowMessage(tm);
				} else if (tm.getMessageType().equals(OnlinefriendListMessage)) {
					ChatWindow window = windowMap.getChatWindow(tm
							.getReceiver());
					window.updateFriendList(tm);
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
