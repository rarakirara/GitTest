import java.awt.HeadlessException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

public class Server implements Runnable{
	static private ServerSocket server; // принимает соединение
	static private Socket connection;
	static private ObjectOutputStream output;
	static private ObjectInputStream input;

	@Override
	public void run() {
		try {
			server = new ServerSocket(5678, 10);
				while(true){ //слушает аутпут и инпут, принимает и отправляет обратно
					connection = server.accept(); // принимает инфу
					output = new ObjectOutputStream(connection.getOutputStream());
					input = new ObjectInputStream(connection.getInputStream()); //создали и инициализировали потоки
					output.writeObject("Вы пирслали: " +(String)input.readObject());//будет отправлять что прислали с препиской
				}
			} catch (UnknownHostException e){
			} catch (IOException e) {
			} catch (HeadlessException e) {
			} catch (ClassNotFoundException e) {}
		}
	private static void sendData(Object obj){
		try {
			output.flush();//чтобы не смешивалить пакеты
			output.writeObject(obj);//будем записывать объект
		} catch (IOException e) {

		}
	}
	
}

