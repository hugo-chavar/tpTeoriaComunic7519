package genetico;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class Grafico {

	private static final String fileName = "graficoEvolucion.m";
	private String path;
	private ArrayList<String> aptitudMedia;
	private ArrayList<String> aptitudPeor;
	private ArrayList<String> aptitudMejor;
	
	public Grafico(ArrayList<String> aptitudMedia, ArrayList<String> aptitudPeor, ArrayList<String> aptitudMejor){
		this.aptitudMedia = aptitudMedia;
		this.aptitudPeor = aptitudPeor;
		this.aptitudMejor = aptitudMejor;
		this.path = getPath();
	}
	
	public void mostrarGrafico(ArrayList<String> aptitudMedia, ArrayList<String> aptitudPeor, ArrayList<String> aptitudMejor){
		escribirArchivoSalida(path+fileName); 
		ejecutar();
	}

	/*
	 * Se escribe el correspondiente archivo .m para que luego se realice el grafico
	 * de la evolucion de la poblacion segun la aptitud.
	 * 
	 * @params
	 * 		fileName = nombre del archivo salida, coindice con el nombre de la funcion
	 */
	private void escribirArchivoSalida(String fileName){
		int cantGeneraciones = aptitudMedia.size();
		String output = "";
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName)));
			output += crearComandoGeneracion(cantGeneraciones);
			output += crearComandoAptitud("aptitudmedia", aptitudMedia);
			output += crearComandoAptitud("aptitudpeor", aptitudPeor);
			output += crearComandoAptitud("aptitudmejor", aptitudMejor);
			
			//plot = para dibujar el grafico
			output += "clf;\nplot(generacion, aptitudmedia, '2');\nhold on;\nplot(generacion, aptitudpeor, '3');\n"
					+ "hold on;\nplot(generacion, aptitudmejor, '1');\nhold on;\n";

			output += "xlabel('Generacion');\nylabel('Aptitud');\nlegend('Aptitud Media', 'Aptitud Peor', 'Aptitud Mejor');\n";
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
	
	private String crearComandoAptitud(String nameAptitud, ArrayList<String> aptitud){
		String outputAptitud = nameAptitud+"=[\n";
		for (int i = 0; i< aptitud.size(); i++)
			outputAptitud+= aptitud.get(i).replace(",", ".") + "\n";
		outputAptitud += "];\n"; 
		return outputAptitud;
	}
	
	/*
	 * Ejecuta octave, al abrirse consola ingresar el nombre del archivo .m para generar
	 * el grafico
	 */
	private void ejecutar(){
                        
        Runtime r = Runtime.getRuntime();
        String file = "";
        file += "graficoEvolucion.m"; // o graficoEvolucionZoom.m depende cual queremos correr, deben estar en el path correcto
        // Por ejemplo en mi compu es: C:\Users\Diego\Documents\GitHub\tpTeoriaComunic7519\TP\bin\graficoEvolucionZoom.m
        file = path + file;
        System.out.println(file);
        String cmd[] = {"cmd.exe","/c start C:\\Software\\Octave-3.6.4\\bin\\octave.exe -q  --persist " + file};
		try {
			r.exec(cmd);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String getPath(){
        String path = "";
        path += getClass().getProtectionDomain().getCodeSource().getLocation().toString();
        path = path.substring(6).replace('/', '\\');
        return path;
	}
}
