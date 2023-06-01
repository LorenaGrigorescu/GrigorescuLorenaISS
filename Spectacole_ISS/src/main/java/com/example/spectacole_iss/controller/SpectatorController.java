package com.example.spectacole_iss.controller;

import com.example.spectacole_iss.model.Bilet;
import com.example.spectacole_iss.model.Rezervare;
import com.example.spectacole_iss.model.Spectacol;
import com.example.spectacole_iss.service.Service;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import javax.naming.spi.ResolveResult;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class SpectatorController {

    private Service service ;
    private int loggedUserID;

    private final ObservableList<Spectacol> spectacolModel = FXCollections.observableArrayList();
    private final ObservableList<Rezervare> rezervariModel = FXCollections.observableArrayList();
    @FXML
    private TextField dataSpectacolTF;
    @FXML
    private TextField oraSpectacolTF;
    @FXML
    private TextField randTF;
    @FXML
    private TextField lojaTF;
    @FXML
    private TextField pretTF;

    @FXML
    private TextArea descriereTA;

    @FXML
    private TextField filtrareZiTF;

    @FXML
    private TableColumn<Spectacol, String> numeColumn;

    @FXML
    private TableColumn<Spectacol, Integer> durataColumn;

    @FXML
    private TableColumn<Spectacol, String> startColumn;

    @FXML
    private TableColumn<Spectacol, String> descriereColumn;

    @FXML
    private TableColumn<Spectacol, Integer> locuriColumn;

    @FXML
    private TableColumn<Spectacol, String> genColumn;

    @FXML
    private TableView<Spectacol> spectacoleTableView;

    @FXML
    private TableColumn<Rezervare, String> numarTelefonColumn;
    @FXML
    private TableColumn<Rezervare, String> emailColumn;
    @FXML
    private TableColumn<Rezervare, Integer> numarLocuriColumn;
    @FXML
    private TableColumn<Rezervare, String> dataSpectacolColumn;
    @FXML
    private TableView<Rezervare> rezervariTableView;
    @FXML
    private TextField telefonTF;
    @FXML
    private TextField emailTF;
    @FXML
    private TextField numarLocuriTF;
    @FXML
    private TextField dataspectacolTF;
    @FXML
    private TextField newNumarLocuriTF;

    @FXML
    private void initialize() {
        numeColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getNume()));
        durataColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getDurata()).asObject());
        startColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getStart()));
        locuriColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getNumar_locuri()).asObject());
        genColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getGen().toString()));
        descriereColumn.setCellValueFactory(p->new SimpleStringProperty(p.getValue().getDescriere()));
        spectacoleTableView.setItems(spectacolModel);
        pretTF.setText("100");
        pretTF.setEditable(false);

        numarTelefonColumn.setCellValueFactory(p->new SimpleStringProperty(p.getValue().getNumarTelefon()));
        emailColumn.setCellValueFactory(p->new SimpleStringProperty(p.getValue().getEmail()));
        numarLocuriColumn.setCellValueFactory(p->new SimpleIntegerProperty(p.getValue().getNumarLocuri()).asObject());
        dataSpectacolColumn.setCellValueFactory(p->new SimpleStringProperty(p.getValue().getDataSpectacol()));
        rezervariTableView.setItems(rezervariModel);
    }


    private void initModel() {
        Iterable<Spectacol> spectacole = service.getAll();
        List<Spectacol> spectacoleList = StreamSupport.stream(spectacole.spliterator(), false).collect(Collectors.toList());
        spectacolModel.setAll(spectacoleList);
    }
    private void initModelRezervari() {
        Iterable<Rezervare> rezervari = service.getAllRezervari(loggedUserID);
        List<Rezervare> rezervariList = StreamSupport.stream(rezervari.spliterator(), false).collect(Collectors.toList());
        rezervariModel.setAll(rezervariList);
    }

    public void setService(Service service) {
        this.service = service;
        initModel();
        initModelRezervari();
    }

    public void setLoggedUserID(int loggedUserID) {
        this.loggedUserID = loggedUserID;
    }

    public void selecteazaSpectacol(MouseEvent mouseEvent) {
        dataSpectacolTF.setText(this.spectacoleTableView.getSelectionModel().getSelectedItem().getStart().split(";")[0]);
        oraSpectacolTF.setText(this.spectacoleTableView.getSelectionModel().getSelectedItem().getStart().split(";")[1]);
        descriereTA.setText(this.spectacoleTableView.getSelectionModel().getSelectedItem().getDescriere());
    }

    public void onCumparaBilet(ActionEvent actionEvent) {
        int rand = Integer.parseInt(this.randTF.getText());
        String loja = this.lojaTF.getText();
        System.out.println(loja);
        int pret = Integer.parseInt(this.pretTF.getText());
        String dataSpectacol  = this.dataSpectacolTF.getText();
        String dataAchizitie = LocalDate.now().toString();
        Bilet biletAchizitionat =
                new Bilet(rand, loja, pret, LocalDate.parse(dataSpectacol),LocalDate.parse(dataAchizitie));
        Bilet bilet = this.service.cumaparaBilet(loggedUserID, biletAchizitionat);
        if(bilet == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Biletul nu a putut fi achizitionat!\nReincercati");
            alert.showAndWait();
        }else {
            initModel();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Bilet achizitionat cu succes!\n");
            alert.showAndWait();
        }

    }

    public void onVizualizarePeZile(ActionEvent actionEvent) {
        String ziSpectacol = filtrareZiTF.getText();
        if(ziSpectacol!="" && ziSpectacol != null)
        {
            List<Spectacol> spectacoleFiltrate = this.service.spectacolePeZile(ziSpectacol);
            if(spectacoleFiltrate.size()!=0)
                spectacolModel.setAll(spectacoleFiltrate);
            else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Niciun spectacol gasit!\n");
                alert.showAndWait();
            }
            initialize();
        }else
        {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Niciun spectacol gasit!\n");
            alert.showAndWait();
        }
    }

    public void selecteazaRezervare(MouseEvent mouseEvent) {

        telefonTF.setText(this.rezervariTableView.getSelectionModel().getSelectedItem().getNumarTelefon());
        emailTF.setText(this.rezervariTableView.getSelectionModel().getSelectedItem().getEmail());
        numarLocuriTF.setText(String.valueOf(this.rezervariTableView.getSelectionModel().getSelectedItem().getNumarLocuri()));
        dataspectacolTF.setText(this.rezervariTableView.getSelectionModel().getSelectedItem().getDataSpectacol());
    }


    public void onModificaRezervare(ActionEvent actionEvent) {
        String telefon = telefonTF.getText();
        String email = emailTF.getText();
        int numarLocuri = Integer.parseInt(numarLocuriTF.getText());
        int newNumarLocuri = Integer.parseInt(newNumarLocuriTF.getText());
        String dataSpectacol = dataspectacolTF.getText();
        Rezervare rezervare = new Rezervare(telefon, email, numarLocuri, dataSpectacol);
        Rezervare rezervareModificata = this.service.modificareLocuriRezervare(rezervare,newNumarLocuri, loggedUserID);
        if(rezervareModificata!=null)
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Rezervare modificata cu succes!\n");
            alert.showAndWait();
            initModelRezervari();
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Rezervare nu a putut fi modificata!\n");
            alert.showAndWait();
        }
    }

    public void onStergeRezervare(ActionEvent actionEvent) {
        String telefon = telefonTF.getText();
        String email = emailTF.getText();
        int numarLocuri = Integer.parseInt(numarLocuriTF.getText());
        String dataSpectacol = dataspectacolTF.getText();
        Rezervare rezervare = new Rezervare(telefon, email, numarLocuri, dataSpectacol);
        Rezervare rezervareStearsa = this.service.stergeRezervare(rezervare, loggedUserID);
        if(rezervareStearsa!=null)
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Rezervare stearsa cu succes!\n");
            alert.showAndWait();
            initModelRezervari();
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Rezervare nu a putut fi stearsa!\n");
            alert.showAndWait();
        }
    }
}
