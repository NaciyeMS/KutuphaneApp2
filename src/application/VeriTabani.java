package application;
import java.sql.*;

import javax.swing.JOptionPane;
public class VeriTabani {
	public Connection baglanti;
	private String databaseismi="kutuphane2";	
	private String kullaniciadi="root";
	private String kullanicisifresi="";
	public Connection baglan()
	{
	 String url="jdbc:mysql://localhost:3307/"+databaseismi;
		 //sizde port ne ise onu kullanmalısınız
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			 baglanti = DriverManager.getConnection(url,kullaniciadi,kullanicisifresi);		 
			
		}catch(Exception hata)
		{			
			JOptionPane.showMessageDialog(null,hata);
		}		
		return baglanti;		
	}	
	public void kapat()
	{  try
		{
			baglanti.close();
		}catch(Exception hata)
		{			
			JOptionPane.showMessageDialog(null,hata);
		}		
	}
	
}
