package App;

import Comunicacion.ComMulticast;
import processing.core.PApplet;

public class Logica {

	PApplet app;
	public Bolita bol;
	ComMulticast multi;
	
	Logica(PApplet app){
		this.app = app;
		
		bol = new Bolita(this);
		
		multi = new ComMulticast(this);
		multi.start();
		
		bol.setObservador(multi);
		multi.setObservador(bol);
		
	}
	
	public void pintar() {
		app.background(255);
		app.fill(0);
		app.textSize(20);
		app.text(multi.getID(), app.width/2, 20);
		bol.pintar();
		bol.mover();
	}
	
	public ComMulticast getMulti(){
		return this.multi;
	}

	public PApplet getPApplet() {
		return app;
	}
	
}
