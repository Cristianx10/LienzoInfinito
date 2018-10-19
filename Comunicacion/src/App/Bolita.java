package App;

import Comunicacion.Observador;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

public class Bolita implements Observador {

	PApplet app;
	Logica log;
	PVector pos;
	int tam;
	boolean mover;
	Observador observer;

	public Bolita(Logica log) {
		this.log = log;
		this.app = log.getPApplet();
		this.tam = 20;
		int height = app.height;
		this.pos = new PVector(-tam, height / 2);
		mover = false;
	}

	public void pintar() {
		app.fill(255, 0, 0);
		app.ellipseMode(PConstants.CENTER);
		app.ellipse(pos.x, pos.y, tam * 2, tam * 2);
	}

	public void comenzar() {
		mover = true;
	}

	public void pasar() {
		mover = false;
	}

	public void mover() {
		if (mover == true) {
			pos.x += 10;
			if (pos.x - tam > app.width) {
				System.out.println("Se fue");
				pos.x = -tam;

				if (observer != null) {
					observer.pasarBolita();
				}

				pasar();

			}
		}
	}

	public void setObservador(Observador observer) {
		this.observer = observer;

	}

	@Override
	public void pasarBolita() {
		comenzar();

	}

}
