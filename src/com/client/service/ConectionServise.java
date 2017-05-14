package com.client.service;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.client.tools.ThreadMap;
import com.common.bean.UserMessage;

public class ConectionServise {

	public Boolean CheceUser(Object o) {

		try {

			Socket ss = new Socket("127.0.0.1", 8888);

			ObjectOutputStream oos = new ObjectOutputStream(
					ss.getOutputStream());
			oos.writeObject(o);

			ObjectInputStream ois = new ObjectInputStream(ss.getInputStream());
			UserMessage message = (UserMessage) ois.readObject();

			if (message.getHasUser() == true) {
				CliConSerThread cct = new CliConSerThread(ss);
				cct.start();
				ThreadMap.addThread(((UserMessage) o).getUserId(), cct);
				return true;
			}
			oos.close();
			ois.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
