package genetico;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
	final static Charset ENCODING = StandardCharsets.UTF_8;

	public static void main(String[] args) {
		System.out.println("TP de Redes Complejas / Sistemas Multiagente");
		System.out.println("Alumnos:");
		System.out.println("	Stephanie Abigail Zurita");
		System.out.println("	Hugo Chavar");
		System.out.println("	Diego Meller");
		System.out.println();
		System.out.println();

		// Defino parametros y veo si hay archivo de configuración
		int cantidadIndividuosEnPoblacion = 100;
		double tasaDeSeleccion = 0.1; // tasa de reproduccion es el mismo nro
		double tasaDeMutacion = 0.01;
		double minimaVariacionEntreGeneraciones = 0.00000001;
		String archivo = "parametros.txt";
		Path path = Paths.get(archivo);
		if (Files.exists(path)) {
			try {
				List<String> params = Files.readAllLines(path, ENCODING);
				for (String linea : params) {
					String[] lineaSeparada = linea.split("=");
					String nombre = lineaSeparada[0].trim();
					String valor = lineaSeparada[1].trim();
					if (nombre.equals("CantidadIndividuosEnPoblacion")) {
						cantidadIndividuosEnPoblacion = Integer.parseInt(valor
								.toString());
					} else if (nombre.equals("TasaDeSeleccion")) {
						tasaDeSeleccion = Double.parseDouble(valor);
					} else if (nombre.equals("TasaDeMutacion")) {
						tasaDeMutacion = Double.parseDouble(valor);
					} else if (nombre
							.equals("MinimaVariacionEntreGeneraciones")) {
						minimaVariacionEntreGeneraciones = Double
								.parseDouble(valor);
					}

				}
			} catch (IOException e) {
			}
		}

		// Creo la generacion 0 de la poblacion
		Poblacion pobl = new Poblacion(cantidadIndividuosEnPoblacion,
				tasaDeSeleccion, tasaDeMutacion,
				minimaVariacionEntreGeneraciones);
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
					+ "\t "
					+ String.format("%9s", String.format("%.5f", indMax))
					+ "\t "
					+ String.format("%9s", String.format("%.5f", indMin));
			estAptitudPoblaciones.add(res);

			if (!pobl.esCondicionDeFin()) {
				break;
			}

		}
		Individuo mejor = pobl.getMejor();
		System.out.println("El mejor individuo tiene un valor de: "
				+ mejor.getValor());
		System.out.println();

		System.out
				.println("Gen.\t Aptitud Media\t Aptitud Peor\t Aptitud Mejor\t Individuo");

		int i;
		for (i = 0; i < estAptitudPoblaciones.size(); i++) {
			System.out.println(i + "\t " + estAptitudPoblaciones.get(i));
		}

	}
}
