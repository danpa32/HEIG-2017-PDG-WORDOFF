/*
 * File: InvitationsController.java
 * Authors: Antoine FRIANT, Gabriel LUTHIER, Christopher MEIER, Daniel PALUMBO, Edward RANSOME, Michela ZUCCA
 * Date: 16 janvier 2018
 */

package ch.heigvd.wordoff.client.Controller;

import ch.heigvd.wordoff.client.Api.Api;
import ch.heigvd.wordoff.client.Api.MeApi;
import ch.heigvd.wordoff.client.Exception.TokenNotFoundException;
import ch.heigvd.wordoff.client.Util.Dialog;
import ch.heigvd.wordoff.client.Util.UtilChangeScene;
import ch.heigvd.wordoff.common.Dto.InvitationDto;
import ch.heigvd.wordoff.common.Dto.InvitationStatus;
import ch.heigvd.wordoff.common.Dto.MeDto;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * JavaFX controller for the invitations screen.
 */
public class InvitationsController implements Initializable {

    @FXML
    ListView<InvitationDto> listNewInvitation;
    @FXML
    ListView<InvitationDto> listNewAlerts;

    private MeDto meDto;
    private List<InvitationDto> invitations = new LinkedList<>();

    /**
     * Returns to main menu.
     * @param event
     */
    @FXML
    private void handleGotoMenu(ActionEvent event) {
        UtilChangeScene.getInstance().handleGotoMenu();
    }

    /**
     * Initializes the Controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            meDto = MeApi.getCurrentUser();
            invitations = Api.get(meDto.getInvitations());
        } catch (TokenNotFoundException e) {
            Dialog.getInstance().signalError("Impossible de récupérer les données du profil");
        }
        listNewInvitation.setCellFactory(param -> new InvitationDtoListCellNew());
        listNewAlerts.setCellFactory(param -> new InvitationDtoListCell());

        listNewInvitation.getItems().addAll(invitations);

    }

    /**
     * Invitations list nested class. Allows custom list cells to be used.
     */
    private class InvitationDtoListCellNew extends ListCell<InvitationDto> {
        private GridPane grid;
        private Button accept, decline;
        private Label inviteName;

        public InvitationDtoListCellNew() {
            grid = new GridPane();
            accept = new Button("Accepter");
            decline = new Button("Decliner");
            inviteName = new Label();
            ColumnConstraints col1 = new ColumnConstraints();
            col1.setMinWidth(250);
            ColumnConstraints col2 = new ColumnConstraints();
            col2.setMinWidth(50);
            col2.setHalignment(HPos.RIGHT);
            ColumnConstraints col3 = new ColumnConstraints();
            col3.setMinWidth(50);
            col3.setHalignment(HPos.RIGHT);
            grid.getColumnConstraints().addAll(col1, col2, col3);
            grid.setPadding(new Insets(0, 10, 0, 10));

            grid.add(inviteName, 0, 0);
            grid.add(accept, 1, 0);
            grid.add(decline, 2, 0);
        }

        /**
         * Update method called when modifying the list.
         * @param iDto an InvitationDto
         * @param empty true if new cell is empty
         */
        @Override
        public void updateItem(InvitationDto iDto, boolean empty) {
            super.updateItem(iDto, empty);
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                setText(null);
                inviteName.setText(iDto.getName());

                accept.setOnMouseClicked(new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent event) {
                        acceptInvite(iDto);
                    }
                });

                decline.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        declineInvite(iDto);
                    }
                });

                setGraphic(grid);
            }
        }
    }

    /**
     * Custom cell for nested list class
     */
    private class InvitationDtoListCell extends ListCell<InvitationDto> {
        @Override
        public void updateItem(InvitationDto iDto, boolean empty) {
            super.updateItem(iDto, empty);
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                setText(iDto.getName());
                setGraphic(null);
            }
        }
    }

    /**
     * Accepts an invite and sends updated DTO to server
     *
     * @param iDto an InvitationDto
     */
    private void acceptInvite(InvitationDto iDto) {
        iDto.setStatus(InvitationStatus.ACCEPT);
        try {
            Api.put(iDto);
            listNewInvitation.getItems().remove(iDto);
        } catch (TokenNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Declines an invite and sends updated DTO to server
     *
     * @param iDto an InvitationDto
     */
    private void declineInvite(InvitationDto iDto) {
        iDto.setStatus(InvitationStatus.DENY);
        try {
            Api.put(iDto);
            listNewInvitation.getItems().remove(iDto);
        } catch (TokenNotFoundException e) {
            e.printStackTrace();
        }
    }

}
