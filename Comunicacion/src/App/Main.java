package App;

import Comunicacion.ComMulticast;
import processing.core.PApplet;

public class Main extends PApplet{

	public static void main(String[] args) {
		PApplet.main("App.Main");
	
	}

	private Logica log;

	public void settings() {
		size(300, 300);
	}
	
	@Override
	public void setup() {
		log = new Logica(this);
	}
	
	@Override
	public void draw() {
		
		log.pintar();
	}
	

}
