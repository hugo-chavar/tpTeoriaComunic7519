package genetico;

import java.util.*;

public class Poblacion {

	public int getTamano() {
		return tamano;
	}

	private ArrayList<Individuo> listaIndividuos;
	private int tamano;
	private double porcSeleccion;
	private double porcMutacion;
	private double aptitudPoblacion;
	private double aptitudAnterior;
	private double condicionDeFin;

	// Constructor
	Poblacion(int tamano, double porcSeleccion, double porcMutacion,
			double condDeFin) {
		this.tamano = tamano;
		this.porcSeleccion = porcSeleccion;
		this.porcMutacion = porcMutacion;
		this.aptitudPoblacion = 0;
		this.condicionDeFin = condDeFin;

		// crear poblacion
		listaIndividuos = new ArrayList<Individuo>();
		int i;
		for (i = 0; i < tamano; i++) {
			listaIndividuos.add(new Individuo(this.porcMutacion));
		}
		this.calcAptitudPoblacion();
	}

	public double getAptitudPoblacion() {
		return this.aptitudPoblacion;
	}

	public double getCondicionDeFin() {
		return this.condicionDeFin;
	}

	public void setAptitudAnterior(double valor) {
		aptitudAnterior = valor;
	}

	public double getAptitudAnterior() {
		return aptitudAnterior;
	}

	public void calcAptitudPoblacion() {
		setAptitudAnterior(this.getAptitudPoblacion());
		int i;
		double result = 0;
		for (i = 0; i < listaIndividuos.size(); i++) {
			result = result + listaIndividuos.get(i).getAptitud();
		}

		this.aptitudPoblacion = result / listaIndividuos.size();
	}

	// Seleccion: eliminar las individuos con la menor aptitud o sea con la
	// aptitud maxima
	public void seleccion() {

		// calcular el numero de individuos de borrar
		double aux = tamano * porcSeleccion;
		int elim = (int) aux;
		Collections.sort(listaIndividuos);
		Iterator<Individuo> ite = listaIndividuos.iterator();

		// elimino los"elim" elementos maximos de listaIndividuo,
		while (ite.hasNext()) {
			ite.next();
			ite.remove();
			elim--;
			if (elim == 0) {
				break;
			}
		}

	}

	// eligo individuo al azar por la reproduccion
	public void selectReproduccion() {

		int noReproduccion = tamano - listaIndividuos.size();
		int i;
		for (i = 0; i < noReproduccion; i++) {

			double random1 = Math.random() * (listaIndividuos.size() - 1);
			double random2 = Math.random() * (listaIndividuos.size() - 1);
			int res1 = (int) random1;
			int res2 = (int) random2;

			Individuo ind1 = listaIndividuos.get(res1);
			Individuo ind2 = listaIndividuos.get(res2);
			Individuo indHijo = ind1.reproducirCon(ind2);
			listaIndividuos.add(indHijo);

		}

	}

	// calcular condicion de fin
	public boolean esCondicionDeFin() {
		double aptitud = this.getAptitudPoblacion();
		double aAnterior = getAptitudAnterior();
		double limit = getCondicionDeFin();
		double check = Math.abs(1 - (aptitud / aAnterior));

		return (check >= limit);
	}

	public void mutacion() {
		int i;
		for (i = 0; i < listaIndividuos.size(); i++) {
			listaIndividuos.get(i).mutar();
		}
	}

	public void mostrarIndividuos() {
		Collections.sort(listaIndividuos);
		Iterator<Individuo> ite = listaIndividuos.iterator();
		while (ite.hasNext()) {
			Individuo s = ite.next();
			System.out.println(s);
		}
	}

	public double getBestIndividuo() {
		int i;
		double max = listaIndividuos.get(0).getAptitud();
		double act;
		for (i = 1; i < listaIndividuos.size(); i++) {
			act = listaIndividuos.get(i).getAptitud();
			if (act > max) {
				max = act;
			}
		}
		return max;
	}

	public double getLeastIndividuo() {
		int i;
		double min = listaIndividuos.get(0).getAptitud();
		double act;
		for (i = 1; i < listaIndividuos.size(); i++) {
			act = listaIndividuos.get(i).getAptitud();
			if (act < min) {
				min = act;
			}
		}
		return min;
	}

	
	public Individuo getMejor() {
		Collections.reverse(listaIndividuos);
		Individuo ind = listaIndividuos.get(0);
		return ind;
	}

}