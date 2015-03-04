package Unzip;

import java.io.*;
import java.util.zip.*;

public class UnZip {
	public static void main(String[] args) throws ZipException, IOException{
		String Input_File = ".\\10001.zip" ;
		String Output_Folder = ".\\output";
		UnZip unZip = new UnZip();
		unZip.UnZipit(Input_File,Output_Folder);
		
	}
	
	public void UnZipit(String input_File, String output_Folder) throws IOException{
		byte [] buffer = new byte[1024];

		File folder = new File(output_Folder);
		if(!folder.exists()){
			folder.mkdir();
		}
		ZipInputStream zis = new ZipInputStream(new FileInputStream(input_File));
		ZipEntry ze = zis.getNextEntry();
		
		while(ze != null){
			String fileName = ze.getName();
			File newFile = new File(output_Folder + File.separator+fileName);
			
			System.out.println("File unzip : " + newFile.getAbsoluteFile());
			
			new File(newFile.getParent()).mkdir();
			FileOutputStream fos = new FileOutputStream(newFile);
			int len;
			while((len = zis.read(buffer)) > 0){
				fos.write(buffer, 0, len);
			}
			fos.flush();
			fos.close();
			ze = zis.getNextEntry();
		}
		zis.closeEntry();
		zis.close();
		System.out.println("Done");
		
	}
}
