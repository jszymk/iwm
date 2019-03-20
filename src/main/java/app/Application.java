package app;

import app.controller.MainViewConroller;
import app.controller.SettingsController;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import app.conversion.InverseRadonTransform;
import app.conversion.RadonTransform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.imageio.ImageIO;


// maska -1 3 -1
public class Application extends javafx.application.Application {
   
    private File inputImage;
    private Image sinImage;
    
    private Stage primaryStage;
    private BorderPane rootLayout;
    
    private double alfa;
    private int n;
    private double l;

    public static FXMLLoader loader = new FXMLLoader();
    
    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("IWM");

        initRootLayout();
        showSettings();
        
        primaryStage.show();

    }

    public void initRootLayout() {
        try {
            
            URL url = Paths.get("src/main/java/app/view/MainView.fxml").toUri().toURL();
            loader.setLocation(url);

            rootLayout = loader.load();

            Scene scene = new Scene(rootLayout);

            primaryStage.setScene(scene);
            primaryStage.show();

            MainViewConroller controller = loader.getController();
            controller.setMain(this);

        } catch (IOException ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void showSettings(){
        try {
            FXMLLoader loader = new FXMLLoader();
            URL url = Paths.get("src/main/java/app/view/Settings.fxml").toUri().toURL();
            loader.setLocation(url);

            AnchorPane settings = loader.load();

            Stage settingsStage = new Stage();
            settingsStage.setTitle("Settings");
            settingsStage.initModality(Modality.WINDOW_MODAL);
            settingsStage.initOwner(this.getPrimaryStage());

            Scene scene = new Scene(settings);
            settingsStage.setScene(scene);

            SettingsController controller = loader.getController();
            controller.setMain(this);
            
            controller.setSettingStage(settingsStage);

            settingsStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
  

    public File getInputImage() {
        return inputImage;
    }

    public void setInputImage(File inputImage) {
        this.inputImage = inputImage;
    }

    public BorderPane getRootLayout() {
        return rootLayout;
    }

    public void setRootLayout(BorderPane rootLayout) {
        this.rootLayout = rootLayout;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Image getSinImage() {
        return sinImage;
    }

    public void setSinImage(Image sinImage) {
        this.sinImage = sinImage;
    }

    public double getAlfa() {
        return alfa;
    }

    public void setAlfa(double alfa) {
        this.alfa = alfa;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public double getL() {
        return l;
    }

    public void setL(double l) {
        this.l = l;
    }
    
    
    
    

}
