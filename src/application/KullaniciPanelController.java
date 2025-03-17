package application;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;


import javafx.scene.input.MouseEvent;

public class KullaniciPanelController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    
    @FXML
    private Button bt_ara;

    @FXML
    private Button bt_dosyaac;

    @FXML
    private Button bt_ekle;

    @FXML
    private Button bt_guncelle;

    @FXML
    private Button bt_sil;
    @FXML
    private TableColumn<KullaniciModel, String> id_clm;
    @FXML
    private TableColumn<KullaniciModel, String> ad_clm;
    @FXML
    private TableColumn<KullaniciModel, String> soyad_clm;
    @FXML
    private TableColumn<KullaniciModel, String> email_clm;
    @FXML
    private TableColumn<KullaniciModel, String> tc_clm;   
    @FXML
    private TableColumn<KullaniciModel, String> resim_clm;
    @FXML
    private TableView<KullaniciModel> tablo;

    
    @FXML
    private TextField tf_ad;

    @FXML
    private TextField tf_ara;

    @FXML
    private TextField tf_email;

    @FXML
    private TextField tf_soyad;

    @FXML
    private TextField tf_tc;

    @FXML
    private ImageView resim;
    
    @FXML
    void bt_ara_click(ActionEvent event) {

    }
    String filePath;
    @FXML
    void bt_dosyaac_click(ActionEvent event) {
    	// FileChooser oluşturuluyor
        FileChooser fileChooser = new FileChooser();
        
        // Yalnızca resim dosyalarına izin ver
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Resim Dosyaları", "*.jpg", "*.png", "*.gif", "*.bmp");
        fileChooser.getExtensionFilters().add(imageFilter);
        
        // Dosya seçme penceresini aç
        Stage stage = (Stage) bt_dosyaac.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        // Eğer dosya seçildiyse
        if (file != null) {
            try {
                // Seçilen dosyadan bir Image nesnesi oluşturuluyor
                Image image = new Image(file.toURI().toString());

                // Seçilen dosyanın yolunu tutalım
                 filePath = file.getAbsolutePath();
                
                // resim ImageView'ine resmi yüklüyoruz
                if (resim != null) {
                    resim.setImage(image);  // ImageView içinde görüntüle
                } else {
                    System.out.println("Resim ImageView öğesi null!");
                }

               
            } catch (Exception e) {
                e.printStackTrace();
                // Hata durumunda bir mesaj gösterilebilir
            }
        }
    }
    VeriTabani vTabani=new VeriTabani();
    PreparedStatement pStatement;
    ResultSet rSet;
    @FXML
    void bt_ekle_click(ActionEvent event) {
    	
      // Veritabanına resmi kaydetmek için:
         
         try {
      	   vTabani.baglan();
      	   String ad = tf_ad.getText().trim();
 				String soyad=tf_soyad.getText().trim();
 				String email=tf_email.getText().trim();
 				String tc=tf_tc.getText().trim();
 				if(ad.isEmpty()||soyad.isEmpty()||email.isEmpty()||tc.isEmpty())
 				{
 					JOptionPane.showMessageDialog(null, "Lütfen tüm alanları doldurun","Uyarı",JOptionPane.WARNING_MESSAGE);
 					return;
 				}
 				//resmi klasöre kopyalama 
 		    	Image kaydedilenResim= resim.getImage();
 		    	 File destinationFile = new File("C:\\Users\\nmacit\\eclipse-workspace\\KutuphaneApp\\src\\profilresimleri\\"+tc+".png");
 		         try {
 					ImageIO.write(SwingFXUtils.fromFXImage(kaydedilenResim,null),"png",destinationFile);
 				} catch (Exception e) {
 				e.printStackTrace();
 				}
 				String sorgu="INSERT INTO kullanicilar (ad,soyad,email,sifre,tc,resim) VALUES (?,?,?,?,?,?)";
 				pStatement=vTabani.baglanti.prepareStatement(sorgu);
 				pStatement.setString(1,ad);
 				pStatement.setString(2,soyad);
 				pStatement.setString(3,email);
 				pStatement.setString(4,tc);
 				pStatement.setString(5,"");
 				pStatement.setString(6,tc+".png");
 				int sonuc=pStatement.executeUpdate();///update insert 
 				if(sonuc>0) {
 					JOptionPane.showMessageDialog(null, "Başarılı kayit");
 					tabloVeriYukle();
 					tf_ad.setText("");
 					tf_soyad.setText("");
 					tf_email.setText("");
 					tf_tc.setText("");
 				    resim.setImage(null);
 				}
 				else {
 					JOptionPane.showMessageDialog(null, "Başarısız kayit");
 				}
 				
 				pStatement.close();
 				vTabani.kapat();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
          
    }

    @FXML
    void bt_guncelle_click(ActionEvent event) {
    	try {
        	vTabani.baglan();
        	String sorgu="UPDATE kullanicilar SET ad = ?, soyad = ?,email = ?,tc = ?,resim = ? WHERE id = ?";
 			pStatement=vTabani.baglanti.prepareStatement(sorgu);
 			pStatement.setString(1,tf_ad.getText());
 			pStatement.setString(2,tf_soyad.getText());
 			pStatement.setString(3,tf_email.getText());
 			pStatement.setString(4,tf_tc.getText());
 			Image kaydedilenResim= resim.getImage();
	    	 File destinationFile = new File("C:\\Users\\nmacit\\eclipse-workspace\\KutuphaneApp\\src\\profilresimleri\\"+tf_tc.getText()+".png");
	         try {
				ImageIO.write(SwingFXUtils.fromFXImage(kaydedilenResim,null),"png",destinationFile);
			} catch (Exception e) {
			e.printStackTrace();
			}
 			pStatement.setString(5,tf_tc.getText()+".png");
 			pStatement.setString(6,tiklananId);
 			int sonuc=pStatement.executeUpdate();///update insert 
 			if(sonuc>0) {
 				JOptionPane.showMessageDialog(null, "güncelleme başarılı");
 				tabloVeriYukle();
 				tf_ad.setText("");
 				tf_soyad.setText("");
 				tf_email.setText("");
 				tf_tc.setText("");
 			    resim.setImage(null);
 			}
 			else {
 				JOptionPane.showMessageDialog(null, "Başarısız güncelleme");
 			}
 			
 			pStatement.close();
 			vTabani.kapat();
     	}
     	catch (Exception e) {
 			// TODO: handle exception
 		}
    }

    @FXML
    void bt_sil_click(ActionEvent event) {
    	try {
       	   vTabani.baglan();
       	String sorgu="DELETE FROM kullanicilar WHERE id=?";
			pStatement=vTabani.baglanti.prepareStatement(sorgu);
			pStatement.setString(1,tiklananId);
			int sonuc=pStatement.executeUpdate();///update insert 
			if(sonuc>0) {
				JOptionPane.showMessageDialog(null, "silme başarılı");
				tabloVeriYukle();
				tf_ad.setText("");
				tf_soyad.setText("");
				tf_email.setText("");
				tf_tc.setText("");
			    resim.setImage(null);
			}
			else {
				JOptionPane.showMessageDialog(null, "Başarısız silme");
			}
			
			pStatement.close();
			vTabani.kapat();
    	}
    	catch (Exception e) {
			// TODO: handle exception
		}
    }
    public String tiklananId;
    @FXML
    void tablo_click(MouseEvent event) {
    	KullaniciModel tiklananKullanici=tablo.getSelectionModel().getSelectedItem();
    	tiklananId=tiklananKullanici.getId();
    	tf_ad.setText(tiklananKullanici.getAd());
    	tf_soyad.setText(tiklananKullanici.getSoyad());
    	tf_email.setText(tiklananKullanici.getEmail());
    	tf_tc.setText(tiklananKullanici.getTc());
    	String filePath = "file:///" + "C:\\Users\\nmacit\\eclipse-workspace\\KutuphaneApp\\src\\profilresimleri\\" + tiklananKullanici.getResim();
    	Image image = new Image(filePath);
    	resim.setImage(image);
    	
    }
    @FXML
    void initialize() {
        assert ad_clm != null : "fx:id=\"ad_clm\" was not injected: check your FXML file 'KullaniciPanel.fxml'.";
        assert bt_ara != null : "fx:id=\"bt_ara\" was not injected: check your FXML file 'KullaniciPanel.fxml'.";
        assert bt_dosyaac != null : "fx:id=\"bt_dosyaac\" was not injected: check your FXML file 'KullaniciPanel.fxml'.";
        assert bt_ekle != null : "fx:id=\"bt_ekle\" was not injected: check your FXML file 'KullaniciPanel.fxml'.";
        assert bt_guncelle != null : "fx:id=\"bt_guncelle\" was not injected: check your FXML file 'KullaniciPanel.fxml'.";
        assert bt_sil != null : "fx:id=\"bt_sil\" was not injected: check your FXML file 'KullaniciPanel.fxml'.";
        assert email_clm != null : "fx:id=\"email_clm\" was not injected: check your FXML file 'KullaniciPanel.fxml'.";
        assert id_clm != null : "fx:id=\"id_clm\" was not injected: check your FXML file 'KullaniciPanel.fxml'.";
        assert resim_clm != null : "fx:id=\"resim_clm\" was not injected: check your FXML file 'KullaniciPanel.fxml'.";
        assert soyad_clm != null : "fx:id=\"soyad_clm\" was not injected: check your FXML file 'KullaniciPanel.fxml'.";
        assert tablo != null : "fx:id=\"tablo\" was not injected: check your FXML file 'KullaniciPanel.fxml'.";
        assert tc_clm != null : "fx:id=\"tc_clm\" was not injected: check your FXML file 'KullaniciPanel.fxml'.";
        assert tf_ad != null : "fx:id=\"tf_ad\" was not injected: check your FXML file 'KullaniciPanel.fxml'.";
        assert tf_ara != null : "fx:id=\"tf_ara\" was not injected: check your FXML file 'KullaniciPanel.fxml'.";
        assert tf_email != null : "fx:id=\"tf_email\" was not injected: check your FXML file 'KullaniciPanel.fxml'.";
        assert tf_soyad != null : "fx:id=\"tf_soyad\" was not injected: check your FXML file 'KullaniciPanel.fxml'.";
        assert tf_tc != null : "fx:id=\"tf_tc\" was not injected: check your FXML file 'KullaniciPanel.fxml'.";
        assert resim != null : "fx:id=\"resim\" was not injected: check your FXML file 'KullaniciPanel.fxml'.";

        // Tabloyu doğru şekilde doldurmak için her bir sütunun değerini ayarlıyoruz
        id_clm.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getId()));
        ad_clm.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getAd()));
        soyad_clm.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getSoyad()));
        email_clm.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getEmail()));
        tc_clm.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTc()));
        resim_clm.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getResim()));

        // Veritabanındaki verileri tabloya yükle
        tabloVeriYukle();
    }
    // Kullanıcı verilerini tabloya yüklemek için kullanacağımız metod
    public void tabloVeriYukle() {
        ObservableList<KullaniciModel> kullaniciListesi = FXCollections.observableArrayList();

        // Veritabanı bağlantısını aç
        VeriTabani vt = new VeriTabani();
        Connection conn = vt.baglan();
        
        // Veritabanından kullanıcı verilerini al
        String query = "SELECT * FROM kullanicilar"; // Kullanıcılar tablosundan tüm veriler
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                // Veritabanından her bir kullanıcıyı al
            	String id = String.valueOf(rs.getInt("id"));
                String ad = rs.getString("ad");
                String soyad = rs.getString("soyad");
                String email = rs.getString("email");
                String tc = rs.getString("tc");
                String resim = rs.getString("resim");

                // Kullanıcıyı model sınıfına ekle
                KullaniciModel kullanici = new KullaniciModel();
                kullanici.setId(id);
                kullanici.setAd(ad);
                kullanici.setSoyad(soyad);
                kullanici.setEmail(email);
                kullanici.setTc(tc);
                kullanici.setResim(resim);

                // Kullanıcıyı ObservableList'e ekle
                kullaniciListesi.add(kullanici);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            vt.kapat(); // Veritabanı bağlantısını kapat
        }

        // Tabloyu verilerle doldur
        tablo.setItems(kullaniciListesi);
    }
}
