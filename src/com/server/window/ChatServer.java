package com.server.window;

import java.awt.Button;
import java.awt.Color;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import com.common.bean.UserMessage;
import com.server.service.SerConCliService;
import com.server.service.SocketMap;

public class ChatServer extends JFrame {
	boolean started = false;

	ServerSocket ss = null;
	TextArea Content = new TextArea();
	TextField acount = new TextField();
	JLabel jlb = new JLabel("��ǰ���ӿͻ�������");
	Button kq = new Button("����������");
	JLabel jlb1 = new JLabel("�����ӵĿͻ���");
	TextArea jtb = new TextArea();

	public static void main(String[] args) {
		ChatServer cs = new ChatServer();
		cs.ServerFrame();

	}

	// ����������
	public void start() {
		try {
			ss = new ServerSocket(8888);
			started = true;
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Content.setText(df.format(System.currentTimeMillis()) + "  "
					+ "�����������������ڼ���..." + '\n');
			acount.setText(0 + "");
		} catch (BindException e) {
			System.out.println("�˿�ʹ����...");
			System.out.println("��ر���س��򣬲��������з�����");
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			while (started) {
				Socket s = ss.accept();

				ObjectInputStream ois = new ObjectInputStream(
						s.getInputStream());
				UserMessage um = (UserMessage) ois.readObject();
				ObjectOutputStream oos = new ObjectOutputStream(
						s.getOutputStream());

				if (um.getPw().equals("123456")) {
					System.out.println("����ˣ�" + um.getUserId() + " ��½�ɹ�");
					um.setHasUser(true);
					oos.writeObject(um);
					SerConCliService sccs = new SerConCliService(s);
					SocketMap.addSocket(um.getUserId(), sccs);
					Thread t = new Thread(sccs);
					t.start();

					SimpleDateFormat df = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					Content.setText(Content.getText()
							+ df.format(System.currentTimeMillis()) + "   "
							+ s.getInetAddress().toString() + "  "
							+ s.getPort() + "������" + '\n');

					jtb.setText("");

					for (Entry<String, SerConCliService> en : SocketMap.sm
							.entrySet()) {
						jtb.setText(jtb.getText()
								+ en.getValue().getSocket().getInetAddress()
										.toString() + " : "
								+ en.getValue().getSocket().getPort() + '\n');

					}
					acount.setText(SocketMap.sm.entrySet().size() + "");
				} else {
					oos.writeObject(um);
					s.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ss.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// ����������
	public void ServerFrame() {
		this.setBackground(Color.white);
		setLocation(200, 200);
		this.setSize(800, 400);
		this.setVisible(true);
		this.setLayout(null);
		this.setResizable(false);
		this.setTitle("���������̨");
		Content.setBounds(0, 0, 620, 290);
		Content.setBackground(Color.white);
		kq.setBounds(280, 340, 80, 30);
		kq.setBackground(Color.white);
		acount.setBounds(310, 305, 100, 20);
		acount.setEditable(false);
		jlb.setBounds(180, 300, 130, 30);
		jlb1.setBounds(660, 0, 200, 30);
		jtb.setBounds(626, 30, 160, 330);
		jtb.setEditable(false);
		jtb.setBackground(Color.white);
		this.add(jlb);
		this.add(acount);
		this.add(Content);
		this.add(kq);
		this.add(jlb1);
		this.add(jtb);
		Content.setEditable(false);
		kq.addActionListener(new kqlistener());

		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent arg0) {
				dispose();
				started = false;
				disconnect();

			}
		});
	}

	// �Ͽ�����
	public void disconnect() {
		try {
			ss.close();
			SocketMap.closeAll();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// ��ť����
	private class kqlistener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			start();
		}
	}

}
