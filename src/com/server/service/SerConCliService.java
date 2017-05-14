package com.server.service;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map.Entry;

import com.common.bean.MessageType;
import com.common.bean.TextMessage;
import com.server.window.ChatServer;

public class SerConCliService implements Runnable {

	Socket socket = null;
	boolean connected;

	public SerConCliService(Socket socket) {
		this.socket = socket;
		connected = true;
	}

	public void run() {
		try {
			while (connected) {
				ObjectInputStream ois = new ObjectInputStream(
						socket.getInputStream());
				TextMessage tm = (TextMessage) ois.readObject();
				// 转发
				if (tm.getMessageType().equals(MessageType.message)) {

					String receiver = tm.getReceiver();
					if ("all".equals(receiver)) {
						System.out.println("服务端：" + tm.getSender() + "发送群消息："
								+ tm.getMessage());

						for (Entry<String, SerConCliService> en : SocketMap.sm
								.entrySet()) {
							SerConCliService sccs = en.getValue();
							tm.setReceiver(en.getKey());
							ObjectOutputStream oos = new ObjectOutputStream(
									sccs.socket.getOutputStream());
							oos.writeObject(tm);
						}
					} else {
						SerConCliService sccs = SocketMap.getSocket(tm
								.getReceiver());
						ObjectOutputStream oos = new ObjectOutputStream(
								sccs.socket.getOutputStream());
						oos.writeObject(tm);
					}

				} else if (tm.getMessageType().equals(
						MessageType.OnlinefriendListMessage)) {
					String list = SocketMap.getOnLineFriendList().trim();
					String[] onlineUser = list.split(" ");
					for (int i = 0; i < onlineUser.length; i++) {
						tm.setMessage(list);
						tm.setReceiver(onlineUser[i]);
						SerConCliService sccs = SocketMap
								.getSocket(onlineUser[i]);
						ObjectOutputStream oos = new ObjectOutputStream(
								sccs.socket.getOutputStream());
						oos.writeObject(tm);
					}
				}
			}
		} catch (Exception e) {
			System.out.println("客户端已关闭");
		} finally {

		}

	}

	public Socket getSocket() {
		return socket;
	}

	public void closeSocket() {
		try {
			connected = false;
			socket.getOutputStream().close();
			socket.getInputStream().close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
