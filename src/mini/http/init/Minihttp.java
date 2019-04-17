package mini.http.init;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import mini.http.HttpContext;
import mini.http.ServerSocketManager;
import mini.http.SocketManager;
public class Minihttp extends Thread{
	private static ServerSocket server;
	public static void main(String[] args) throws InterruptedException {
		Minihttp t=new Minihttp();
		t.start();
		t.join();
		init();
	}
	private static void init(){
		try {
			server=new ServerSocket(ServerSocketManager.port);
			while(true){
				Socket client=server.accept();
				new SocketManager(client).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void run(){
		HttpContext context=HttpContextFactory.getHttpContext();
		InitFactory.init(context);
	}
	
}
