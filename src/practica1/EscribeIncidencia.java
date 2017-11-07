package practica1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class EscribeIncidencia {

	
	public static void escribeIncidencia(String cadena, String res,String dirHash) throws IOException{
		
		metodos.compruebaRuta("IncidenciasSeguridad", "incidenciasSeguridad.txt");
		
		File incidencias=new File(dirHash+metodos.compruebaSys()+"incidenciasSeguridad.txt");
		BufferedWriter bw = new BufferedWriter(new FileWriter(incidencias,true));
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		df.setTimeZone(TimeZone.getTimeZone("Europe/Madrid"));
		
		bw.write("=================================================\n");
		bw.write(df.format(date)+"\n");
		bw.write(cadena+"\n");
		bw.write(res+"\n");
		bw.write("\n");
		bw.close();
	}
}
