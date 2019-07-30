package sample.example.sockets;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	public static void main(String[] args) throws IOException {
		int listeningPort = 12365;
		ServerSocket serverSocket = new ServerSocket();
		serverSocket.bind(new InetSocketAddress("localhost", listeningPort));
		Socket socket = serverSocket.accept();
		InputStream is = socket.getInputStream();
	}

}
