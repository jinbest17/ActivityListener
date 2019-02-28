package com.example.activityserver;

import java.io.*;
import java.net.*;

public class MySocketServer {
	private static final int SOCKET_PORT = 18888;
	private ServerSocket serverSocket = null;
	private boolean flag = true;
	private BufferedReader reader;
	
	public static void main(String[] args) {
		MySocketServer socketServer = new MySocketServer();
		socketServer.initSocket();
	}
	
	private void initSocket() {
		try {
			serverSocket = new ServerSocket(SOCKET_PORT);
			InetAddress adress = InetAddress.getLocalHost();
			System.out.println("��������������IP��ַ��" + adress.getHostAddress() + "�˿ںţ�" + SOCKET_PORT);
			while(flag) {
				Socket clientSocket = serverSocket.accept();
				System.out.println("�пͻ�������");
				SocketThread socketThread = new SocketThread(clientSocket);
				socketThread.start();
				
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public class SocketThread extends Thread{
		private Socket socket;
		
		public SocketThread(Socket clientSocket) {
			this.socket = clientSocket;
		}
		
		@Override
		public void run() {
			super.run();
			
			InputStream inputStream;
			try {
				inputStream = socket.getInputStream();
				reader = new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
				while(flag) {
					if(reader.ready()) {
						String result = reader.readLine();
						System.out.println(result);
					}
				}
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
}
