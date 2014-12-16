import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


public class main extends JFrame implements Runnable { // потоки
	static private Socket connection;
	static private ObjectOutputStream output;
	static private ObjectInputStream input;
	
	public static void main(String[] args) {
		new Thread(new main("Test")).start();
		new Thread(new Server()).start(); // запускает класс сервер

	}
	public main(String name){ //создаем окно
		super(name);
		setLayout(new FlowLayout());
		setSize(300, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setLocationRelativeTo(null);
		
		final JTextField t1 = new JTextField(10);
		final JButton b1 = new JButton("Send");
		b1.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(arg0.getSource()==b1){
					sendData(t1.getText());
				}	
			}
		});
		add(t1);
		add(b1);
	}
	

	public void run() {
		try {
			
				while(true){ //слушает аутпут и инпут, принимает и отправляет обратно
					connection = new Socket(InetAddress.getByName("127.0.0.1"), 5678);//соединяет с компом по айпи
					output = new ObjectOutputStream(connection.getOutputStream());
					input = new ObjectInputStream(connection.getInputStream()); //создали и инициализировали потоки
					JOptionPane.showMessageDialog(null, (String)input.readObject());//окно будет актив.только тогда когда из инпута можно будет что-то прочитать
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


