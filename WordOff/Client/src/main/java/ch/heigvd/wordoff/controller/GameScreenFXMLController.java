package ch.heigvd.wordoff.controller;


import ch.heigvd.wordoff.MainApp;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

/**
 *
 * @author Gabriel Luthier
 */
public class GameScreenFXMLController implements Initializable {
    
    @FXML
    private Button gotoMenu;
    
    @FXML
    private void handleGotoMenu(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/mainMenu.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            MainApp.getStage().setScene(scene);
        } catch (IOException ex) {
            Logger.getLogger(GameScreenFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
}