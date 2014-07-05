package genetico;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class Grafico {

	private static final String fileName1 = "graficoEvolucion";
	/*utilizo otra salida donde se cambia el limite maximo del eje, de esa forma
	 * se aprecia mas la convergencia*/
	private static final String fileName2 = "graficoEvolucionZoom";
	private static final String fileExtension = ".m";
	private ArrayList<String> aptitudMedia;
	private ArrayList<String> aptitudPeor;
	private ArrayList<String> aptitudMejor;
	
	public Grafico(ArrayList<String> aptitudMedia, ArrayList<String> aptitudPeor, ArrayList<String> aptitudMejor){
		this.aptitudMedia = aptitudMedia;
		this.aptitudPeor = aptitudPeor;
		this.aptitudMejor = aptitudMejor;
	}
	
	public void mostrarGrafico(ArrayList<String> aptitudMedia, ArrayList<String> aptitudPeor, ArrayList<String> aptitudMejor){
		//limite Aptitud=10000 porque lo máximo de aptitud que se obtuve fue en el rango [9000,10000)
		escribirArchivoSalida(fileName1, 10000); 
		escribirArchivoSalida(fileName2, 200); 
		ejecutar();
	}

	/*
	 * Se escribe el correspondiente archivo .m para que luego se realice el grafico
	 * de la evolucion de la poblacion segun la aptitud.
	 * 
	 * @params
	 * 		fileName = nombre del archivo salida, coindice con el nombre de la funcion
	 * 		limiteAptitudMaximo = es el valor máximo para el eje y (en este caso aptitud)
	 */
	private void escribirArchivoSalida(String fileName, int limiteAptitudMaximo){
		int cantGeneraciones = aptitudMedia.size();
		String output = "function "+ fileName+ "\n";
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName + fileExtension)));
			output += crearComandoGeneracion(cantGeneraciones);
			output += crearComandoAptitud("aptitudmedia", aptitudMedia);
			output += crearComandoAptitud("aptitudpeor", aptitudPeor);
			output += crearComandoAptitud("aptitudmejor", aptitudMejor);
			
			//plot = para dibujar el grafico
			output += "clf;\nplot(generacion, aptitudmedia, '2');\nhold on;\nplot(generacion, aptitudpeor, '3');\n"
					+ "hold on;\nplot(generacion, aptitudmejor, '1');\nhold on;\n";
			
			/*
			 * axis = para cambiar valor minimo y maximo para los ejes
			 * axis[(valor_minimo_EjeX, valor_maximo_EjeX, valor_minimo_EjeY, valor_maximo_EjeY)]
			 */
			String axis = "axis([0,"+cantGeneraciones+",-100,"+limiteAptitudMaximo+"]);\n";
			output += axis + "xlabel('Generacion');\nylabel('Aptitud');\nlegend('Aptitud Media', 'Aptitud Peor', 'Aptitud Mejor');\n";
			output += "endfunction\n";
			writer.write(output);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private String crearComandoGeneracion(int cantGeneracion){
		String outputGen = "generacion=[\n";
		for (int i = 0; i< cantGeneracion; i++)
			outputGen += i + "\n";
		outputGen += "];\n"; 
		return outputGen;
	}
	
	private static String crearComandoAptitud(String nameAptitud, ArrayList<String> aptitud){
		String outputAptitud = nameAptitud+"=[\n";
		for (int i = 0; i< aptitud.size(); i++)
			outputAptitud+= aptitud.get(i).replace(",", ".") + "\n";
		outputAptitud += "];\n"; 
		return outputAptitud;
	}
	
	private static void ejecutar(){
                        
        Runtime r = Runtime.getRuntime();
        String cmd[] = {"cmd.exe","/c start C:\\Software\\Octave-3.6.4\\bin\\octave-3.6.4"};
		try {
			r.exec(cmd);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
