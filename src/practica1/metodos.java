package practica1;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class metodos {
	
	@SuppressWarnings("resource")
	public static String compruebaSys() throws FileNotFoundException {
		@SuppressWarnings("unused")
		FileInputStream fis=null;
		try {
			fis = new FileInputStream("directorioArchivos\\");
			return "\\";
		}catch(Exception e){
			return "/";
		}
		
	}
	
	public static void compruebaRuta(String dirHash,String nHash){
		try{
			@SuppressWarnings("unused")
			File f=null;
			if(nHash==null)
				 f=new File(dirHash);
			f=new File(dirHash+compruebaSys()+nHash);
			
		}catch(Exception e){
			System.out.println("El directorio o fichero no existen: "+e.toString());
		}
	}
	
	public static SecretKey cifrar(String dirHash,String nHash) 
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException{
		
		String fichero = dirHash+metodos.compruebaSys()+nHash;
		//Generar clave
		KeyGenerator keygen = KeyGenerator.getInstance("DESede");
		SecretKey key = keygen.generateKey();
		Cipher codigo = Cipher.getInstance("DESede/ECB/PKCS5Padding");
		codigo.init(Cipher.ENCRYPT_MODE,key);
		
		//Cifrar el documento
		InputStream input = new BufferedInputStream( new FileInputStream(fichero));
		CipherOutputStream salida = new CipherOutputStream(new BufferedOutputStream(new FileOutputStream(dirHash+metodos.compruebaSys()+"2"+nHash)),codigo);
		byte [] buffer = new byte [1024];  
		   int r;  
		   while ((r = input.read(buffer)) > 0) {  
		       salida.write(buffer, 0, r);  
		   }  
		input.close();
		purgaFichero(dirHash+metodos.compruebaSys()+nHash);
		salida.close();
		return key;
	}
	
	public static void descifrar(SecretKey key,String dirHash,String nHash) 
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, 
			IOException, ClassNotFoundException{
		
		//Descifrar el documento
		Cipher cifra = Cipher.getInstance("DESede/ECB/PKCS5Padding");
		cifra.init(Cipher.DECRYPT_MODE,(Key) key);
		InputStream input = new BufferedInputStream( new FileInputStream(dirHash+metodos.compruebaSys()+"2"+nHash));
		CipherInputStream entrada= new CipherInputStream(input,cifra);
		BufferedOutputStream salida = new BufferedOutputStream(new FileOutputStream(dirHash+metodos.compruebaSys()+nHash));
		byte [] buffer = new byte [1024];  
		   int r;  
		   while ((r = entrada.read(buffer)) > 0) {  
		       salida.write(buffer, 0, r);  
		   }  
		entrada.close();
		salida.close();
	}
	
	public static void purgaFichero(String name) throws IOException {
		File f = new File(name);
		BufferedWriter bw;
		bw = new BufferedWriter(new FileWriter(f));
		bw.write("");
		bw.close();
	}
	
}
