package com.client.window;

import javax.swing.*;

import com.client.service.ConectionServise;
import com.client.tools.ErrorTip;
import com.client.tools.ThreadMap;
import com.client.tools.windowMap;
import com.common.bean.TextMessage;
import com.common.bean.UserMessage;
import com.common.bean.MessageType;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ClientLogin extends JFrame {
	// 定义北部需要的组件
	JLabel jbl1;
	// 定义中部需要的组件
	// 需要三葛ＪＰａｎｅｌ。
	JTabbedPane jtp;
	JPanel jp2;
	JLabel jp2_jb11, jp2_jb12;
	JTextField jp2_jtf;
	JPasswordField jp2_jpf;
	JCheckBox jp2_jcb1, jp2_jcb2;

	// 定义南部的组件
	JPanel jp1;
	JButton jp1_jb1, jp1_jb2, jp1_jb3;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ClientLogin ClientLogin = new ClientLogin();
	}

	public ClientLogin() {
		// 处理北部
		jbl1 = new JLabel("Welcome to load");

		// 处理中部
		jp2 = new JPanel(new GridLayout(3, 2));
		jp2_jb11 = new JLabel("账号", JLabel.CENTER);
		jp2_jb12 = new JLabel("密码", JLabel.CENTER);

		jp2_jtf = new JTextField();
		jp2_jpf = new JPasswordField();
		jp2_jcb1 = new JCheckBox("隐身");
		jp2_jcb2 = new JCheckBox("记住密码");
		// 把控件按照顺序加入到jp2
		jp2.add(jp2_jb11);
		jp2.add(jp2_jtf);
		jp2.add(jp2_jb12);
		jp2.add(jp2_jpf);
		jp2.add(jp2_jcb1);
		jp2.add(jp2_jcb2);

		// 创建选项卡窗口
		jtp = new JTabbedPane();
		jtp.add("账号", jp2);

		// 处理南部
		jp1 = new JPanel();
		jp1_jb1 = new JButton("登录");
		jp1_jb1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				UserMessage um = new UserMessage(jp2_jtf.getText(), jp2_jpf
						.getText());
				ConectionServise cs = new ConectionServise();

				if (jp2_jtf.getText().equals("")
						|| jp2_jpf.getText().equals("")
						|| cs.CheceUser(um) == false) {
					ErrorTip error = new ErrorTip("帐号或密码错误！！");
				} else {
					ChatWindow ul = new ChatWindow(jp2_jtf.getText(), "");
					try {
						ObjectOutputStream oos = new ObjectOutputStream(
								ThreadMap.getThread(jp2_jtf.getText())
										.getSocket().getOutputStream());
						TextMessage tm2 = new TextMessage(jp2_jtf.getText(),
								null, null, MessageType.OnlinefriendListMessage);
						oos.writeObject(tm2);
						windowMap.addWindow(jp2_jtf.getText(), ul);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					dispose();
				}

			}
		});
		jp1_jb2 = new JButton("取消");
		jp1_jb2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		// 把这三个按钮放到jp1
		jp1.add(jp1_jb1);
		jp1.add(jp1_jb2);
		// jp1.add(jp1_jb3);

		this.add(jbl1, "North");
		this.add(jtp, "Center");
		// 把jp1放到南部
		this.add(jp1, "South");
		this.setSize(350, 240);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

}
