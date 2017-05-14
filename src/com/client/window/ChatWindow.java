package com.client.window;

import java.awt.Button;
import java.awt.Color;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.client.tools.ThreadMap;
import com.common.bean.MessageType;
import com.common.bean.TextMessage;

public class ChatWindow extends Frame {
	String myId;

	public ChatWindow(String myId, String friendId) {
		launchFrame();
		this.myId = myId;
		setTitle("欢迎您：" + myId);
	}

	TextField tfTxt = new TextField();
	TextArea taContent = new TextArea();
	Button button = new Button("发送");
	static Socket s = null;
	JLabel jlb = new JLabel("在线好友：");
	JLabel direction = new JLabel("发送至：");
	TextField who = new TextField();
	TextArea jtb = new TextArea();
	String title = null;
	DataOutputStream dos = null;
	DataInputStream dis = null;

	// 界面
	public void launchFrame() {
		setLocation(200, 200);
		this.setSize(650, 420);
		this.setVisible(true);
		this.setLayout(null);
		taContent.setBounds(10, 40, 480, 270);
		taContent.setBackground(getBackground());
		this.add(taContent);
		tfTxt.setBounds(10, 310, 480, 70);
		this.add(tfTxt);
		tfTxt.setEditable(true);
		taContent.setEditable(false);
		button.setBounds(495, 390, 100, 30);
		button.setBackground(Color.white);
		button.setForeground(getForeground());
		jlb.setBounds(528, 30, 200, 30);
		jtb.setBounds(495, 58, 150, 323);
		jtb.setBackground(Color.white);
		direction.setBounds(78, 385, 70, 30);
		who.setBounds(150, 390, 200, 20);
		this.add(button);
		this.add(jlb);
		this.add(jtb);
		this.setResizable(false);
		this.add(direction);
		this.add(who);

		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent arg0) {
				Socket socket = ThreadMap.getThread(myId).getSocket();
				try {
					socket.getInputStream().close();
					socket.getOutputStream().close();
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.exit(0);
			}
		});

		button.addActionListener(new TFListener());

		tfTxt.addActionListener(new TFListener());
		setVisible(true);
	}

	// 发送监听
	private class TFListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String str = tfTxt.getText().trim();
			String str1 = who.getText().trim();
			if (str.equals("")) {
				JOptionPane jop = new JOptionPane();
				JOptionPane.showMessageDialog(jop, "发送信息不能为空！", "Tip!",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				tfTxt.setText("");
				try {
					if (str1.equals("")) {
						TextMessage message = new TextMessage(myId, "all", str,
								MessageType.message);
						ObjectOutputStream oos = new ObjectOutputStream(
								ThreadMap.getThread(myId).getSocket()
										.getOutputStream());
						oos.writeObject(message);
					} else {
						String to = who.getText().toString();
						if (null == to || to.equals("")) {
							JOptionPane jop = new JOptionPane();
							JOptionPane.showMessageDialog(jop, "好友输入有误，请检查！",
									"Tip!", JOptionPane.INFORMATION_MESSAGE);
						} else {
							TextMessage message = new TextMessage(myId, str1,
									str, MessageType.message);
							ObjectOutputStream oos = new ObjectOutputStream(
									ThreadMap.getThread(myId).getSocket()
											.getOutputStream());
							oos.writeObject(message);
						}
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	public void ShowMessage(TextMessage tm) {
		taContent.append(tm.getSender() + "对我说" + tm.getMessage() + "\n");
	}

	public void updateFriendList(TextMessage tm) {
		jtb.setText("");
		for (String user : tm.getMessage().split(" ")) {
			jtb.append(user + "\n");
		}

	}
}
