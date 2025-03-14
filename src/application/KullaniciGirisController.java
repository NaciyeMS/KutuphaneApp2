package application;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField ad_tf;

    @FXML
    private TextField email_reg_tf;

    @FXML
    private TextField email_tf;

    @FXML
    private TextField email_tf11;

    @FXML
    private Button girisButton;

    @FXML
    private Button girisButton11;

    @FXML
    private Button kayitButton;

    @FXML
    private PasswordField sifre_pf;

    @FXML
    private TextField sifre_reg_tf;

    @FXML
    private TextField soyad_tf;

    @FXML
    private TextField tc_tf;
    
    VeriTabani vt= new VeriTabani();
	PreparedStatement ps;
	ResultSet rs;
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
    void kayitButton_click(ActionEvent event) {
    	try {
    	    vt.baglan();  // Bağlantıyı başlat

    	    // Kullanıcıdan alınan verileri al
    	    String ad = ad_tf.getText().trim();
    	    String soyad = soyad_tf.getText().trim();
    	    String mail = email_reg_tf.getText().trim();
    	    String tc = tc_tf.getText().trim();
    	    String sifre = sifre_reg_tf.getText().trim();

    	    // TextField boşluk kontrolü
    	    if (ad.isEmpty() || soyad.isEmpty() || mail.isEmpty() || tc.isEmpty() || sifre.isEmpty()) {
    	        JOptionPane.showMessageDialog(null, "Lütfen tüm alanları doldurun!", "Uyarı", JOptionPane.WARNING_MESSAGE);
    	        return; // Eğer herhangi bir alan boşsa, işlemi durdur
    	    }
    	    // Sorgu hazırlanıyor
    	    String sorgu = "INSERT INTO kullanicilar (ad, soyad, email, sifre, tc) VALUES (?, ?, ?, ?, ?)";
    	    ps = vt.baglanti.prepareStatement(sorgu);

    	    // Parametreler ayarlanıyor
    	    ps.setString(1, ad);
    	    ps.setString(2, soyad);	  
    	    ps.setString(3, mail);	 
    	    ps.setString(4, tc);	 
    	    ps.setString(5, sifre);	   

    	    // Sorgu çalıştırılıyor
    	    int sonuc = ps.executeUpdate();
    	    if (sonuc > 0) {
    	        // Kayıt başarılı
    	        JOptionPane.showMessageDialog(null, "Kayıt başarılı!");
    	        
    	        // TextField'ları temizle
    	        ad_tf.setText("");
    	        soyad_tf.setText("");
    	        email_reg_tf.setText("");
    	        tc_tf.setText("");
    	        sifre_reg_tf.setText("");
    	    } else {
    	        // Kayıt başarısız
    	        JOptionPane.showMessageDialog(null, "Kayıt başarısız!");
    	    }

    	    // Kaynakları kapat
    	    ps.close();
    	    vt.kapat();

    	} catch (SQLException e) {
    	    // SQL hatası durumunda
    	    JOptionPane.showMessageDialog(null, "Veritabanı hatası: " + e.getMessage());
    	} catch (Exception e) {
    	    // Diğer hatalar için genel catch bloğu
    	    JOptionPane.showMessageDialog(null, "Bir hata oluştu: " + e.getMessage());
    	}

    }

    @FXML
    void initialize() {
        assert ad_tf != null : "fx:id=\"ad_tf\" was not injected: check your FXML file 'KullaniciGiris.fxml'.";
        assert email_reg_tf != null : "fx:id=\"email_reg_tf\" was not injected: check your FXML file 'KullaniciGiris.fxml'.";
        assert email_tf != null : "fx:id=\"email_tf\" was not injected: check your FXML file 'KullaniciGiris.fxml'.";
        assert email_tf11 != null : "fx:id=\"email_tf11\" was not injected: check your FXML file 'KullaniciGiris.fxml'.";
        assert girisButton != null : "fx:id=\"girisButton\" was not injected: check your FXML file 'KullaniciGiris.fxml'.";
        assert girisButton11 != null : "fx:id=\"girisButton11\" was not injected: check your FXML file 'KullaniciGiris.fxml'.";
        assert kayitButton != null : "fx:id=\"kayitButton\" was not injected: check your FXML file 'KullaniciGiris.fxml'.";
        assert sifre_pf != null : "fx:id=\"sifre_pf\" was not injected: check your FXML file 'KullaniciGiris.fxml'.";
        assert sifre_reg_tf != null : "fx:id=\"sifre_reg_tf\" was not injected: check your FXML file 'KullaniciGiris.fxml'.";
        assert soyad_tf != null : "fx:id=\"soyad_tf\" was not injected: check your FXML file 'KullaniciGiris.fxml'.";
        assert tc_tf != null : "fx:id=\"tc_tf\" was not injected: check your FXML file 'KullaniciGiris.fxml'.";

    }

}

/*
    VeriTabani vt= new VeriTabani();
	PreparedStatement ps;
	ResultSet rs;
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
*/
   
