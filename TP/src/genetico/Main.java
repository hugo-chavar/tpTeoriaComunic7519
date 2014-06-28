package genetico;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		System.out.println("TP de algoritmos geneticos.");
		System.out.println("Alumnos:");
		System.out.println("	Carina Greger");
		System.out.println("	Hugo Chavar");
		System.out.println();
		System.out.println();
		
		//Defino parametros
		int cantidadIndividuosEnPoblacion = 100;
		double tasaDeSeleccion = 0.1; //tasa de reproduccion es el mismo nro
		double tasaDeMutacion = 0.01;
		double minimaVariacionEntreGeneraciones = 0.00000001;
		
		//Creo la generacion 0 de la poblacion
		Poblacion pobl = new Poblacion(cantidadIndividuosEnPoblacion, tasaDeSeleccion, tasaDeMutacion, minimaVariacionEntreGeneraciones);
		ArrayList<Double> aptitudPoblaciones = new ArrayList<Double>();
		ArrayList<String> estAptitudPoblaciones = new ArrayList<String>();

		while (true) {

			pobl.seleccion();
			pobl.selectReproduccion();
			pobl.mutacion();
			aptitudPoblaciones.add(pobl.getAptitudPoblacion());
			pobl.calcAptitudPoblacion();

			double indMedio = pobl.getAptitudPoblacion();
			double indMin = pobl.getLeastIndividuo();
			double indMax = pobl.getBestIndividuo();

			String res = String.format("%9s", String.format("%.5f", indMedio))
					+ "	" + String.format("%9s", String.format("%.5f", indMax)) 
					+ "	" + String.format("%9s", String.format("%.5f", indMin));
			estAptitudPoblaciones.add(res);

			if (!pobl.esCondicionDeFin()) {
				break;
			}

		}
		Individuo mejor = pobl.getMejor();
		System.out.println("El mejor individuo tiene un valor de: "+ mejor.getValor());
		System.out.println();

		System.out
				.println("Gen.	Aptitud Media	Aptitud Peor	Aptitud Mejor Individuo");

		int i;
		for (i = 0; i < estAptitudPoblaciones.size(); i++) {
			System.out.println(i + " 	" + estAptitudPoblaciones.get(i));
		}

	}
}
