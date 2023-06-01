package com.example.spectacole_iss.controller;

import com.example.spectacole_iss.model.Spectacol;
import com.example.spectacole_iss.model.User;
import com.example.spectacole_iss.service.Service;
import jakarta.persistence.Column;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.util.List;
import java.util.logging.XMLFormatter;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class SpectacolController {

    private final ObservableList<Spectacol> spectacolModel = FXCollections.observableArrayList();
    @FXML
    private TextField numeTF;
    @FXML
    private TextField durataTF;

    @FXML
    private TextField dataTF;

    @FXML
    private TextField oraTF;

    @FXML
    private TextField locuriTF;

    @FXML
    private TextField descriereTF;

    @FXML
    private TextField genTF;

    @FXML
    private TextField newDataTF;
    @FXML
    private TextField newOraTF;

    @FXML
    private TextField dataFiltruTF;

    @FXML
    private TextArea descriereTA;

    @FXML
    private TableColumn<Spectacol, String> numeColumn;

    @FXML
    private TableColumn<Spectacol, Integer> durataColumn;

    @FXML
    private TableColumn<Spectacol, String> startColumn;

    @FXML
    private TableColumn<Spectacol, Integer> locuriColumn;

    @FXML
    private TableColumn<Spectacol, String> descriereColumn;
    @FXML
    private TableColumn<Spectacol, String> genColumn;

    @FXML
    private TableView<Spectacol> spectacoleTableView;

    private Service service;


    @FXML
    private void initialize() {
        numeColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getNume()));
        durataColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getDurata()).asObject());
        startColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getStart()));
        locuriColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getNumar_locuri()).asObject());
        descriereColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getDescriere()));
        genColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getGen().toString()));
        spectacoleTableView.setItems(spectacolModel);
    }


    private void initModel() {
        Iterable<Spectacol> spectacole = service.getAll();
        List<Spectacol> spectacoleList = StreamSupport.stream(spectacole.spliterator(), false).collect(Collectors.toList());
        spectacolModel.setAll(spectacoleList);
    }


    public void setService(Service service) {
        this.service = service;
        initModel();
    }

    public void selecteazaSpectacol(MouseEvent mouseEvent) {

        numeTF.setText(String.valueOf(this.spectacoleTableView.getSelectionModel().getSelectedItem().getNume()));
        durataTF.setText(String.valueOf(this.spectacoleTableView.getSelectionModel().getSelectedItem().getDurata()));
        dataTF.setText(this.spectacoleTableView.getSelectionModel().getSelectedItem().getStart().split(";")[0]);
        oraTF.setText(this.spectacoleTableView.getSelectionModel().getSelectedItem().getStart().split(";")[1]);
        locuriTF.setText(String.valueOf(this.spectacoleTableView.getSelectionModel().getSelectedItem().getNumar_locuri()));
        descriereTF.setText(this.spectacoleTableView.getSelectionModel().getSelectedItem().getDescriere());
        genTF.setText(String.valueOf(this.spectacoleTableView.getSelectionModel().getSelectedItem().getGen()));
        this.descriereTA.setText(descriereTF.getText());
    }

    public void onAdaugaSpectacol(ActionEvent actionEvent) {

        String nume = numeTF.getText();
        int durata = Integer.parseInt(durataTF.getText());
        String data = dataTF.getText();
        String ora = oraTF.getText();
        String start = data + ";" + ora;
        int locuri = Integer.parseInt(locuriTF.getText());
        String descriere = descriereTF.getText();
        Spectacol.Gen gen = Spectacol.Gen.valueOf(genTF.getText());

        Spectacol spectacol = new Spectacol(0, nume, durata, start, locuri, descriere, gen);
        Spectacol spectacolAdaugat = this.service.adaugaSpectacol(spectacol);
        if (spectacolAdaugat != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Adaugare de spectacol efectuata cu succes!\n");
            alert.showAndWait();
            initModel();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Adaugare de spectacol efectuata cu insucces!\n");
            alert.showAndWait();

        }

    }

    public void onModificaSpectacol(ActionEvent actionEvent) {
        String newStart = "";
        String newData = newDataTF.getText();
        String newOra = newOraTF.getText();
        String oldData = dataTF.getText();
        String oldOra = oraTF.getText();
        if (newData == "" && newOra != "") {
            newStart = oldData + ";" + newOra;
        } else if (newData != "" && newOra == "") {
            newStart = newData + ";" + oldOra;
        } else if (newData != "" && newOra != "") {
            newStart = newData + ";" + newOra;
        }
        String oldStart = oldData + ";" + oldOra;
        Spectacol spectacol = new Spectacol(
                0,
                numeTF.getText(),
                Integer.parseInt(durataTF.getText()),
                oldStart,
                Integer.parseInt(locuriTF.getText()),
                descriereTF.getText(),
                Spectacol.Gen.valueOf(genTF.getText())
        );
        Spectacol spectacolModificat = this.service.modificaSpectacol(spectacol, newStart);
        if (spectacolModificat != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Modificare spectacol efectuata cu succes!\n");
            alert.showAndWait();
            initialize();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Modificare spectacol efectuata cu insucces!\n");
            alert.showAndWait();

        }


    }

    public void onFiltreazaSpectacole(ActionEvent actionEvent) {
        String ziSpectacol = dataFiltruTF.getText();
        if (ziSpectacol != "" && ziSpectacol != null) {
            List<Spectacol> spectacoleFiltrate = this.service.spectacolePeZile(ziSpectacol);
            if (spectacoleFiltrate.size() != 0)
                spectacolModel.setAll(spectacoleFiltrate);
            else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Niciun spectacol gasit!\n");
                alert.showAndWait();
            }
            initialize();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Niciun spectacol gasit!\n");
            alert.showAndWait();
        }
    }

    public void onStergeSpectacol(ActionEvent actionEvent) {
        String nume = numeTF.getText();
        int durata = Integer.parseInt(durataTF.getText());
        String data = dataTF.getText();
        String ora = oraTF.getText();
        String start = data + ";" + ora;
        int locuri = Integer.parseInt(locuriTF.getText());
        String descriere = descriereTF.getText();
        Spectacol.Gen gen = Spectacol.Gen.valueOf(genTF.getText());

        Spectacol spectacol = new Spectacol(0, nume, durata, start, locuri, descriere, gen);
        Spectacol spectacolSters = this.service.stergeSpectacol(spectacol);
        if (spectacolSters != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Stergere  spectacol efectuata cu succes!\n");
            alert.showAndWait();
            initModel();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Stergere spectacol efectuata cu insucces!\n");
            alert.showAndWait();

        }

    }
}
