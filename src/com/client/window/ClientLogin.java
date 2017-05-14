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
	// ���山����Ҫ�����
	JLabel jbl1;
	// �����в���Ҫ�����
	// ��Ҫ����ʣУ���졣
	JTabbedPane jtp;
	JPanel jp2;
	JLabel jp2_jb11, jp2_jb12;
	JTextField jp2_jtf;
	JPasswordField jp2_jpf;
	JCheckBox jp2_jcb1, jp2_jcb2;

	// �����ϲ������
	JPanel jp1;
	JButton jp1_jb1, jp1_jb2, jp1_jb3;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ClientLogin ClientLogin = new ClientLogin();
	}

	public ClientLogin() {
		// ������
		jbl1 = new JLabel("Welcome to load");

		// �����в�
		jp2 = new JPanel(new GridLayout(3, 2));
		jp2_jb11 = new JLabel("�˺�", JLabel.CENTER);
		jp2_jb12 = new JLabel("����", JLabel.CENTER);

		jp2_jtf = new JTextField();
		jp2_jpf = new JPasswordField();
		jp2_jcb1 = new JCheckBox("����");
		jp2_jcb2 = new JCheckBox("��ס����");
		// �ѿؼ�����˳����뵽jp2
		jp2.add(jp2_jb11);
		jp2.add(jp2_jtf);
		jp2.add(jp2_jb12);
		jp2.add(jp2_jpf);
		jp2.add(jp2_jcb1);
		jp2.add(jp2_jcb2);

		// ����ѡ�����
		jtp = new JTabbedPane();
		jtp.add("�˺�", jp2);

		// �����ϲ�
		jp1 = new JPanel();
		jp1_jb1 = new JButton("��¼");
		jp1_jb1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				UserMessage um = new UserMessage(jp2_jtf.getText(), jp2_jpf
						.getText());
				ConectionServise cs = new ConectionServise();

				if (jp2_jtf.getText().equals("")
						|| jp2_jpf.getText().equals("")
						|| cs.CheceUser(um) == false) {
					ErrorTip error = new ErrorTip("�ʺŻ�������󣡣�");
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
		jp1_jb2 = new JButton("ȡ��");
		jp1_jb2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		// ����������ť�ŵ�jp1
		jp1.add(jp1_jb1);
		jp1.add(jp1_jb2);
		// jp1.add(jp1_jb3);

		this.add(jbl1, "North");
		this.add(jtp, "Center");
		// ��jp1�ŵ��ϲ�
		this.add(jp1, "South");
		this.setSize(350, 240);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

}
