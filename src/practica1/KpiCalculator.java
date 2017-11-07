package practica1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

public class KpiCalculator {

	
	public static float CalculaKPI(int total,String nHash,String dirHash,Map<String,Integer>almacenK,Integer contKPID) 
			throws IOException{
		
		metodos.compruebaRuta(dirHash, nHash); //por si no est� el fichero
		
		File incidencias=new File(dirHash+metodos.compruebaSys()+"DailyKPI.txt");	
		BufferedWriter bw = new BufferedWriter(new FileWriter(incidencias,true));
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		df.setTimeZone(TimeZone.getTimeZone("Europe/Madrid"));
		
		int hashSuccess=almacenK.get("+");
		int hashFailed=almacenK.get("-");
		
		float kpi = (float)(hashSuccess)/total;//Calculo del KPI
		
		bw.write("====================KPI-"+contKPID+"=============================\n");
		bw.write("Fecha y Hora: "+df.format(date)+"\n");
		bw.write("archivos totales: "+total+"\n");
		bw.write("fallos: "+hashFailed+"\n");
		bw.write("Resultado: "+kpi+"\n");
		bw.write("\n");
		bw.close();
		
		return kpi;
	}
	
	public static void calculaKPIMensual(String nHash,String dirHash,Map<String,Float>almacen,Integer contKPIM)
			throws IOException {
		
		Float res=almacen.get("*");
		Float ratios=almacen.get("total");
		
		metodos.compruebaRuta(dirHash, nHash);        
        //escritura de fichero--
        
        File kpiMensual=new File(dirHash+metodos.compruebaSys()+"MonthlyKPI.txt");	
		BufferedWriter bw = new BufferedWriter(new FileWriter(kpiMensual,true));
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		df.setTimeZone(TimeZone.getTimeZone("Europe/Madrid"));
		
		Float kpi = res/ratios;
		
		bw.write("=====================KPI-"+contKPIM+"============================\n");
		bw.write("Fecha y Hora: "+df.format(date)+"\n");
		bw.write("Resultado: "+kpi+"\n");
		bw.write("\n");
		bw.close();
		
        //----------------------
	}
	
	
	public static Boolean compruebaDiario(Boolean sm, DateFormat h, int contadorM, Boolean sh) {
		Boolean res=true;
		
		if(sm) {
			Date date = new Date();
			DateFormat hourFormat = new SimpleDateFormat("HH");
			
			if(hourFormat.format(date).equals(h.format(date)) && !sh )	//rutina diaria
				res=false;
			
			if(contadorM==30) {		//mensual
				res=false;
			}
			
			//System.out.println("Hora: "+hourFormat.format(date));
		}
		
		return res;
	}
	
	public static String tratamientoDatos(String dato) {
		Boolean semaforo=false;
		String res="";
		
		if(dato.contains("Resultado:")) {
			
			for(int i=0;i<dato.length();i++) {
				char c=dato.charAt(i);
				//System.out.println(c);
				if(c==':') {
					//System.out.println(dato.charAt(i));
					semaforo=true;
				}else{
					if(semaforo && c !=' ') {
						res+=c;
					}
				}	
			}
		}
		
		return res;
		
	}
	
}
