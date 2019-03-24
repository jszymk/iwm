/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller;

import app.Application;
import app.conversion.InverseRadonTransform;
import app.conversion.RadonTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import app.dicom.DicomExport;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author Weronika
 */
public class DisplayImagesController implements Initializable {

    Application app;

    @FXML
    private ImageView inputImage;

    @FXML
    private ImageView sinograph;

    @FXML
    private ImageView outputImage;

    @FXML
    private ProgressIndicator progressCircle1;

    @FXML
    private ProgressIndicator progressCircle2;
    
    @FXML
    private Slider slider;
    
    @FXML
    private Label iteration;
    
    @FXML
    private HBox iterationBox;
    
    private List<BufferedImage> outList;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        progressCircle1.setVisible(false);
        progressCircle2.setVisible(false);
        iterationBox.setVisible(false);
    }

    public void setMain(Application app) {
        try {
            this.app = app;
            inputImage.setImage(new Image(app.getInputImage().toURI().toURL().toExternalForm()));
            showSinograph();
        } catch (MalformedURLException ex) {
            Logger.getLogger(DisplayImagesController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void showSinograph() {

        final File file = this.app.getInputImage();

        progressCircle1.setVisible(true);
        new Thread(() -> {
            try {
                BufferedImage image = ImageIO.read(this.app.getInputImage());

                BufferedImage sin = new RadonTransform(this.app.getN(), this.app.getL(), this.app.getAlfa()).transform(image);
                File f = new File("sin.jpg");
                ImageIO.write(sin, "jpg", f);

                progressCircle1.setVisible(false);
                sinograph.setImage(new Image(f.toURI().toURL().toExternalForm()));

                progressCircle2.setVisible(true);
                this.outList = new InverseRadonTransform(this.app.getN(), this.app.getL(), this.app.getAlfa()).transform(sin, 1);
                BufferedImage out = outList.get(outList.size()-1);

                f = new File("out.jpg");
                ImageIO.write(out, "jpg", f);

                outputImage.setImage(new Image(f.toURI().toURL().toExternalForm()));

                progressCircle2.setVisible(false);
                iterationBox.setVisible(true);
                slider.setMax(outList.size() - 1);

                DicomExport.writeFile("dicom.dcm", out, "Jacek Przypadek", new Date(), "xD");
                

            } catch (IOException ex) {
                Logger.getLogger(MainViewConroller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
        
        
        
        
        slider.valueProperty().addListener(new ChangeListener<Number>(){
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                iteration.setText(Integer.toString(newValue.intValue()));
                outputImage.setImage(SwingFXUtils.toFXImage(outList.get(newValue.intValue()), null));
                
            }
            
            
            
        });

    }

}
