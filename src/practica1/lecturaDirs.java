package practica1;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

public class lecturaDirs {
	
	public static void leeDirectorios(String directorio,String dirHash,String nHash,String alg) 
			throws NoSuchAlgorithmException, IOException {
		
		MessageDigest algorithm=MessageDigest.getInstance(alg);	//CREAMOS LA HUELLA SHA-256
		
		metodos.compruebaRuta(dirHash, nHash);	//comprobamos directorios
		
		File dir = new File(directorio);	//DIRECTORIO QUE VAMOS A MONITORIZAR
		File dirlog=new File(dirHash+metodos.compruebaSys()+nHash);
		
		BufferedWriter bw;
		
		String[] ficheros = dir.list();
		
		if(ficheros==null)
			System.out.println("Directorios vacios");
		
		else {
			
			bw = new BufferedWriter(new FileWriter(dirlog,true));
		
			for (int i=0;i<ficheros.length;i++) {
				try {
					FileInputStream fis = new FileInputStream(directorio+metodos.compruebaSys()+ficheros[i]);
					
					BufferedInputStream bis = new BufferedInputStream(fis);
					@SuppressWarnings("resource")
					DigestInputStream dis =  new DigestInputStream(bis,algorithm);//APLICAMOS LA HUELLA AL FICHERO
					
					byte [] b= new byte[dis.available()];
					dis.read(b,0,dis.available());
					algorithm= dis.getMessageDigest();
					byte[] digest = algorithm.digest();
					
					//escritura en logs ---
					
					bw.write("["+ficheros[i]+","+DatatypeConverter.printHexBinary(digest)+"]\n");
					
				    
				    //fin escritura ----
				    
					System.out.println("Fichero "+ficheros[i]+", HASH: "+DatatypeConverter.printHexBinary(digest));
					
				}catch(java.io.FileNotFoundException e) {
					leeDirectorios(directorio+metodos.compruebaSys()+ficheros[i],dirHash,nHash,alg);
				}
			
			}//fin bloque for
			bw.close();
		}//fin else
	}
	
}
