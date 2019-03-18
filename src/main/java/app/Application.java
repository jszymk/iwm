package app;

import app.controller.MainViewConroller;

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
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.imageio.ImageIO;

public class Application extends javafx.application.Application {
   
    private Image inputImage;
    private Stage primaryStage;
    private BorderPane rootLayout;

    //public static FXMLLoader loader = new FXMLLoader();
    public static void main(String[] args) throws Exception {
        File input_file = new File("inp.jpg");

        BufferedImage image = ImageIO.read(input_file);

        BufferedImage sin = new RadonTransform(400, 90, 0.5).transform(image);

        File f = new File("sin.jpg");
        ImageIO.write(sin, "jpg", f);

        BufferedImage out = new InverseRadonTransform(400, 90, 0.5).transform(sin, 5);

        f = new File("out.jpg");
        ImageIO.write(out, "jpg", f);

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("IWM");

        initRootLayout();
        //showImages();

        primaryStage.show();

    }

    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
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

  

    public Image getInputImage() {
        return inputImage;
    }

    public void setInputImage(Image inputImage) {
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
    
    
    
    
    
    
    

}
