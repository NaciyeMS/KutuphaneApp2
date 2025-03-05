package application;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class KullaniciGirisController {
	
	
	VeriTabani vt= new VeriTabani();
	PreparedStatement ps;
	ResultSet rs;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField email_tf;

    @FXML
    private Button girisButton;

    @FXML
    private PasswordField sifre_pf;

    @FXML
    void girisButton_click(ActionEvent event) {
    	try {    		 
	        vt.baglan();
	        String email = email_tf.getText().trim();
	        String sifre = sifre_pf.getText().trim(); 	       
	        String sorgu = "SELECT * FROM kullanicilar WHERE email=? AND sifre=?";
	        ps = vt.baglanti.prepareStatement(sorgu);
	        ps.setString(1, email);
	        ps.setString(2, sifre);	        
	        rs = ps.executeQuery();
	        if (rs.next()) {
	            JOptionPane.showMessageDialog(null, "Başarılı giriş!");   
	            //diğer sayfaya yönlendirme işlemini yapmamız lazım 
	            FXMLLoader loader =new FXMLLoader(getClass().getResource("KullaniciPanel.fxml"));
	            Parent root=loader.load();
	            Stage stage=new Stage();
	            stage.setTitle("Kullanıcı Paneli");
	            stage.setScene(new Scene(root));
	            stage.show();
	            ((Stage)girisButton.getScene().getWindow()).close();
	        } else {
	            JOptionPane.showMessageDialog(null, "Kullanıcı bulunamadı!");
	        }
	        rs.close();
	        ps.close();
	        vt.kapat();
	        
    	}catch (Exception e) {
    		JOptionPane.showMessageDialog(null, e);
		}
	        
    }

    @FXML
    void initialize() {
        assert email_tf != null : "fx:id=\"email_tf\" was not injected: check your FXML file 'KullaniciGiris.fxml'.";
        assert girisButton != null : "fx:id=\"girisButton\" was not injected: check your FXML file 'KullaniciGiris.fxml'.";
        assert sifre_pf != null : "fx:id=\"sifre_pf\" was not injected: check your FXML file 'KullaniciGiris.fxml'.";

    }

}
