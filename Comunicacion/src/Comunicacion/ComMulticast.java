package Comunicacion;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;

import App.Logica;

public class ComMulticast extends Thread implements Observador{

	MulticastSocket socket;
	static int PUERTO;
	InetAddress Ipgrupo;
	String myIp;
	int maxId;
	Logica log;
	Observador observer;

	public ComMulticast(Logica log) {
		this.log = log;

	}

	public void inicializar() {
		try {
			this.PUERTO = 5000;
			// 224.0.0.0 to 239.255.255.255
			this.Ipgrupo = InetAddress.getByName("224.0.0.0");
			myIp = InetAddress.getLocalHost().getHostAddress();

			socket = new MulticastSocket(PUERTO);

			socket.joinGroup(Ipgrupo);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		inicializar();
		autoId();

		while (true) {
			try {
				recibir();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void enviar(String mensaje) {
		new Thread(new Runnable() {

			@Override
			public void run() {

				DatagramPacket datagramPacket = new DatagramPacket(mensaje.getBytes(), mensaje.length(), Ipgrupo,
						PUERTO);

				try {
					socket.send(datagramPacket);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();

	}

	public void recibir() throws IOException {
		byte[] capacidadAlmacenamiento = new byte[500];
		DatagramPacket datagramaPacket = new DatagramPacket(capacidadAlmacenamiento, capacidadAlmacenamiento.length);

		socket.receive(datagramaPacket);

		String mensajeRecibido = new String(datagramaPacket.getData()).trim();

		mensajesRecibidos(mensajeRecibido);

	}

	private void mensajesRecibidos(String mensajeRecibido) {

		if (Identificado == true) {
			if (mensajeRecibido.equals("Identifiquense")) {
				enviar("Myides:" + ID + ":" + myIp);
			}

			if (mensajeRecibido.contains("Ultimo:")) {
				String[] datos = mensajeRecibido.split(":");
				int temId = Integer.parseInt(datos[1]);
				if (temId >= ID) {
					maxId = temId;
				}
			}

			if (mensajeRecibido.contains("Pasar:")) {
				String[] datos = mensajeRecibido.split(":");
				int temId = Integer.parseInt(datos[1]);
				
				
				
				if ((temId == ID)) {
					if(observer != null)
					observer.pasarBolita();
					
				} else if (temId > maxId && ID == 0) {
					if(observer != null)
					observer.pasarBolita();
					
				}
			}

		}

		if (Identificado == false) {
			if (mensajeRecibido.contains("Myides:")) {
				String[] datos = mensajeRecibido.split(":");
				int temId = Integer.parseInt(datos[1]);
				String myip = "" + datos[2];
				System.out.println("Hola mi id es:" + temId + " Y mi ip es: " + myip);
				if (temId >= ID) {
					ID = temId + 1;
				}
			}
		}

	}

	// ---------------------------------------------------------

	int ID;
	boolean Identificado;

	public void autoId() {
		ID = -1;

		enviar("Identifiquense");

		try {
			socket.setSoTimeout(500);
		} catch (SocketException e) {
			e.printStackTrace();
		}

		while (Identificado == false) {

			try {
				recibir();
			} catch (IOException e) {

				Identificado = true;

				try {
					socket.setSoTimeout(0);
				} catch (SocketException e1) {
					e.printStackTrace();
				}

				if (ID == -1) {
					ID = 0;
					if(observer != null)
						observer.pasarBolita();
				
				}

				System.out.println("My ID ES: " + ID);
				enviar("Ultimo:" + ID);
			}

		}
	}

	public int getID() {
		return ID;
	}

	public int getMAXID() {
		return maxId;
	}


	@Override
	public void pasarBolita() {
		int siguiente = ID + 1;
		enviar("Pasar:" + siguiente);
	}
	
	public void setObservador(Observador observer) {
		this.observer = observer;
		
	}


}
