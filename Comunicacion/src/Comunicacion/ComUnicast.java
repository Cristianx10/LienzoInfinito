package Comunicacion;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ComUnicast {
	
	DatagramSocket socket;
	static int PUERTO;
	InetAddress DIRECCION;
	
	
	public void inicializar() {
		try {
			PUERTO = 5000;
			DIRECCION = InetAddress.getByName("10.0.2.2");
			socket = new DatagramSocket(PUERTO);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void enviar(String mensaje) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				DatagramPacket datagramPacket = new DatagramPacket(
						mensaje.getBytes(), mensaje.length(),
						DIRECCION, PUERTO);
				
				try {
					socket.send(datagramPacket);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}
		}).start();
		
	}
	
	public void recibir() {
		byte[] capacidadDeRecepcion = new byte[100];
		DatagramPacket datagramPacket = new DatagramPacket(
				capacidadDeRecepcion, capacidadDeRecepcion.length);
		
		try {
			socket.receive(datagramPacket);
		} catch (IOException e) {
			e.printStackTrace();
		}
				
	}

}
