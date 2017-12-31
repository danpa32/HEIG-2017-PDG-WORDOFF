package ch.heigvd.wordoff.client.Controller;

import ch.heigvd.wordoff.client.Util.UtilChangeScene;
import ch.heigvd.wordoff.client.Util.UtilStringReference;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Gabriel Luthier
 */
public class SettingsController implements Initializable {

    @FXML
    private CheckBox checkBoxSound;
    @FXML
    private CheckBox checkBoxNotifications;
    @FXML
    private CheckBox checkBoxHelp;
    @FXML
    private ChoiceBox<String> choiceBoxLang;
    @FXML
    private Label playerName;
    @FXML
    private ImageView playerAvatar;
    @FXML
    private VBox friendsList;
    @FXML
    private VBox blackList;

    @FXML
    private void handleGotoMenu(ActionEvent event) {
        UtilChangeScene.getInstance().handleGotoMenu();
    }

    /**
     * Initializes the Controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initState();
    }

    private void initState(){
        checkBoxSound.setText(UtilStringReference.TEXT_PARAM_SOUND);
        checkBoxHelp.setText(UtilStringReference.TEXT_PARAM_HELP);
        checkBoxNotifications.setText(UtilStringReference.TEXT_PARAM_NOTIF);
        choiceBoxLang.getItems().addAll(UtilStringReference.TEXT_PARAM_LANG);
        choiceBoxLang.setValue(UtilStringReference.TEXT_PARAM_LANG.get(0));
        playerName.setText("Player name");
        // TODO set l'image
        // TODO set les listes d'amis / ennemis, utiliser ListCustom
        // TODO check la config, à save localement ?!
    }

    @FXML
    private void changeAvatar(){
        // TODO Charger un nouvel avatar (playerAvatar)

    }

    @FXML
    private void addNewFriend(){
        // TODO dialog pour ajouter un ami
    }

    @FXML
    private void addBlackList(){
        // TODO dialog pour ajouter dans la blacklist
    }

    @FXML
    private void removeFriend(){
        // TODO supprimer l'ami de la liste
    }

    @FXML
    private void removeBlackList(){
        // TODO supprimer de la blacklist
    }
}
