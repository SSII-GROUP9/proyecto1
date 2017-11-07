package practica1;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class principal {

	public static void main(String[] args) throws NoSuchAlgorithmException, IOException, 
	InvalidKeyException, NoSuchPaddingException, ClassNotFoundException {
		String rutaConf="configuracion.txt";
		List<String>datosFicheros=null;
		List<Integer>auxiliar=new ArrayList<Integer>();
		List<Integer>aux2=new ArrayList<Integer>();
		Map<String,Integer>almacenKPI=new HashMap<String,Integer>();
		Map<String,Float>almacenRatio=new HashMap<String,Float>();
		Date date = new Date();
		Boolean semaforoKPI=false;
		Boolean semaforoCifrado=false;
		Boolean kpiHora=false;
		SecretKey key=null;
		
		DateFormat hora=new SimpleDateFormat("20");	//rutina KPI
		int mensual=1;	//rutina mensual
		almacenRatio.put("total", (float) 0);
		Integer contKPID=0;
		Integer contKPIM=0;
		
		try {
			datosFicheros=leerFicheroConfiguracion.leeFichero(rutaConf); //length->4.
		}catch(Exception e) {
			leerFicheroConfiguracion.restablecerFichero();
		}
		
		datosFicheros=leerFicheroConfiguracion.leeFichero(rutaConf);
		
		String dirInicial=datosFicheros.get(0);
		System.out.println("============GENERAMOS LOS HASH DE FICHEROS================");
		lecturaDirs.leeDirectorios(dirInicial,datosFicheros.get(1),datosFicheros.get(2),datosFicheros.get(5));
		System.out.println("==========================================================");
		try {
			while(true){
				DateFormat hourFormat = new SimpleDateFormat("HH");
				
				if(Integer.parseInt(hourFormat.format(date)) > Integer.parseInt(hora.format(date)))
					kpiHora=false;
				
				if(mensual>30) {	//espera de 30 días de nuevo
					mensual=0;
					contKPIM++;
				}
				//comprobar fecha horas y tal del kpi
				if(semaforoKPI) {
					semaforoKPI=KpiCalculator.compruebaDiario(semaforoKPI,hora,mensual,kpiHora);
					if(!semaforoKPI) {
						mensual++;
						contKPID++;
						kpiHora=true;
					}
				}
				
				if(semaforoCifrado)
					almacenKPI=comprobarDir.comprobarHash(dirInicial,datosFicheros.get(5),datosFicheros.get(1),
							datosFicheros.get(2),0,0,auxiliar,dirInicial,semaforoKPI,mensual,aux2,key,
							almacenKPI,almacenRatio,contKPID,contKPIM);
				
				key=metodos.cifrar(datosFicheros.get(1), datosFicheros.get(2));
				semaforoCifrado=true;
				
				if(!semaforoKPI)
					semaforoKPI=true;
				
				TimeUnit.SECONDS.sleep(Integer.parseInt(datosFicheros.get(3)));
			}
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
