package genetico;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Main {
	static final String SEPARADOR = "\t ";
	
	public static void main(String[] args) {
		System.out.println("TP de Redes Complejas / Sistemas Multiagente");
		System.out.println("Alumnos:");
		System.out.println("	Stephanie Abigail Zurita");
		System.out.println("	Hugo Chavar");
		System.out.println("	Diego Meller");
		System.out.println();
		System.out.println();

		// Defino parametros y veo si hay archivo de configuración
		int cantidadIndividuosEnPoblacion = 10;
		double tasaDeSeleccion = 0.1; // tasa de reproduccion es el mismo nro
		double tasaDeMutacion = 0.01;
		double minimaVariacionEntreGeneraciones = 0.0000001;

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
			int cantIndividuos = pobl.getCantidadIndividuo();

			String res = String.format("%9s", String.format("%.5f", indMedio))
					+ SEPARADOR
					+ String.format("%9s", String.format("%.5f", indMax))
					+ SEPARADOR
					+ String.format("%9s", String.format("%.5f", indMin))
					+ SEPARADOR
					+ String.format("%d", cantIndividuos);
			estAptitudPoblaciones.add(res);

			if (!pobl.esCondicionDeFin()) {
				break;
			}

		}
		Individuo mejor = pobl.getMejor();
		System.out.println("El mejor individuo tiene un valor de: "
				+ mejor.getValor());
		System.out.println();

		
		Writer writer = null;
		Date fecha = new Date();
		String fileName = "Salida_"
				+ new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(fecha);
		try {
			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(fileName + ".txt"), "utf-8"));
			System.out
			.println("Gen."+ SEPARADOR +"Aptitud Media" + SEPARADOR
					+"Aptitud Peor"+ SEPARADOR +"Aptitud Mejor"+ SEPARADOR + "Individuos");
			
			writer.write("Gen."+ SEPARADOR +"Aptitud Media" + SEPARADOR
					+"Aptitud Peor"+ SEPARADOR +"Aptitud Mejor"+ SEPARADOR + "Individuos");
			writer.write("\r\n");
			int i;
			for (i = 0; i < estAptitudPoblaciones.size(); i++) {
				System.out.println(i + SEPARADOR + estAptitudPoblaciones.get(i));
				writer.write(i + SEPARADOR + estAptitudPoblaciones.get(i));
				writer.write("\r\n");
			}
		} catch (IOException ex) {
			// report
			System.err.println(ex.getMessage());
		} finally {
			try {
				writer.close();
			} catch (Exception ex) {
			}
		}

	}
}
