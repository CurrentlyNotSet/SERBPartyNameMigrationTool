/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sceneControllers;

import com.model.partyModel;
import com.model.partyNameTableModel;
import com.sql.SQLnamePrefix;
import com.sql.SQLparty;
import com.util.Global;
import com.util.StringUtilities;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Andrew
 */
public class MainWindowSceneController implements Initializable {

    Stage mainstage;
    private double X, Y;
    private int partyID;
    
    @FXML
    private ComboBox<String> startsWithComboBox;
    @FXML
    private ComboBox<String> prefixComboBox;
    @FXML
    private TextField firstNameTextBox;
    @FXML
    private TextField miTextBox;
    @FXML
    private TextField lastNameTextBox;
    @FXML
    private TextField suffixTextBox;
    @FXML
    private TextField nameTitleTextBox;
    @FXML
    private TextField jobTitleTextBox;
    @FXML
    private TextField companyTextBox;
    @FXML
    private TextField address1TextBox;
    @FXML
    private TextField address2TextBox;
    @FXML
    private TextField address3TextBox;
    @FXML
    private TextField cityTextBox;
    @FXML
    private ComboBox<String> statesComboBox;
    @FXML
    private TextField zipCodeTextBox;
    @FXML
    private TextField phone1TextBox;
    @FXML
    private TextField phone2TextBox;
    @FXML
    private TextField emailTextBox;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TableView<partyNameTableModel> searchTable;
    @FXML
    private TableColumn<partyNameTableModel, String> iDColumn;
    @FXML
    private TableColumn<partyNameTableModel, String> nameColumn;
    @FXML
    protected void onRectanglePressed(MouseEvent event) {
        X = mainstage.getX() - event.getScreenX();
        Y = mainstage.getY() - event.getScreenY();
    }
    @FXML
    protected void onRectangleDragged(MouseEvent event) {
        mainstage.setX(event.getScreenX() + X);
        mainstage.setY(event.getScreenY() + Y);
    }
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setTableProps();
        setListeners();
    }    
    
    private void setTableProps() {
        iDColumn.setCellValueFactory(cellData -> cellData.getValue().getID());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getPartyName());
    }
    
    private void setListeners() {
        saveButton.disableProperty().bind(
                (firstNameTextBox.textProperty().isEmpty())
                .or(lastNameTextBox.textProperty().isEmpty())
                .or(address1TextBox.textProperty().isEmpty())
                .or(cityTextBox.textProperty().isEmpty())
                .or(statesComboBox.valueProperty().isNull())
                .or(zipCodeTextBox.textProperty().isEmpty())
        );
    }
    
    public void setDefaults(Stage stage){
        mainstage = stage;
        populateLastNameComboBox();
        populatePreFixComboBox();
        populateStateComboBox();
        setTableSizes();
        search();
    }
    
    private void populateLastNameComboBox() {
        startsWithComboBox.getItems().clear();
        startsWithComboBox.getItems().addAll(Global.getAlphabet());
        startsWithComboBox.setValue("A");
    }
    
    private void populateStateComboBox() {
        statesComboBox.getItems().clear();
        statesComboBox.getItems().addAll(Global.getStates());
        statesComboBox.setValue("");
    }
    
    private void populatePreFixComboBox() {
        prefixComboBox.getItems().clear();
        prefixComboBox.getItems().addAll(SQLnamePrefix.getSalutationList());
        prefixComboBox.setValue("");
    }
    
    
    private void setTableSizes() {
        iDColumn.setVisible(false);
    }
    
    @FXML
    private void MenuExit() {
        System.exit(0);
    }
    
    @FXML
    private void saveButton(){
        saveContactCard();
    }
    
    @FXML
    private void cancelButton(){
        loadContactCard(partyID);
    }
    
    @FXML
    private void search(){
        clearContactCard();
        String searchParam = startsWithComboBox.getValue() + "%";
        ObservableList<partyNameTableModel> list = SQLparty.getContactList(searchParam);
        loadTable(list);
    }
    
    private void loadTable(ObservableList<partyNameTableModel> list) {
        searchTable.getItems().removeAll();
        searchTable.setItems(list);
    }
    
    @FXML
    private void tableListener(MouseEvent event) {
        partyNameTableModel row = searchTable.getSelectionModel().getSelectedItem();
        
        if (row != null && event.getClickCount() >= 1) {
            loadContactCard(Integer.parseInt(row.getID().getValue()));
        }
    }
  
    private void loadContactCard(int partyID) {
        partyModel item = SQLparty.getPartydetails(partyID);
                
        setCardDisabled(false);
        this.partyID = partyID;
        prefixComboBox.setValue(item.getPrefix());
        firstNameTextBox.setText(item.getFirstName());
        miTextBox.setText(item.getMiddleInitial());
        lastNameTextBox.setText(item.getLastName());
        suffixTextBox.setText(item.getSuffix());
        nameTitleTextBox.setText(item.getNameTitle());
        jobTitleTextBox.setText(item.getJobTitle());
        companyTextBox.setText(item.getCompanyName());
        address1TextBox.setText(item.getAddress1());
        address2TextBox.setText(item.getAddress2());
        address3TextBox.setText(item.getAddress3());
        cityTextBox.setText(item.getCity());
        statesComboBox.setValue(item.getState());
        zipCodeTextBox.setText(item.getZip());
        phone1TextBox.setText(
                StringUtilities.convertStringToPhoneNumber(
                        ((item.getPhoneOne() == null) ? "" : item.getPhoneOne())
                ));
        phone2TextBox.setText(
                StringUtilities.convertStringToPhoneNumber(
                        ((item.getPhoneTwo() == null) ? "" : item.getPhoneTwo())
                ));
        emailTextBox.setText(item.getEmailAddress());
    }
    
    private void saveContactCard(){
        partyModel item = new partyModel();
        
        item.setPartyID(partyID);
        item.setPrefix(((prefixComboBox.getValue() == null) ? "" : prefixComboBox.getValue().trim()));
        item.setFirstName(((firstNameTextBox.getText() == null) ? "" : firstNameTextBox.getText().trim()));
        item.setMiddleInitial(((miTextBox.getText() == null) ? "" : miTextBox.getText().trim()));
        item.setLastName(((lastNameTextBox.getText() == null) ? "" : lastNameTextBox.getText().trim()));
        item.setSuffix(((suffixTextBox.getText() == null) ? "" : suffixTextBox.getText().trim()));
        item.setNameTitle(((nameTitleTextBox.getText() == null) ? "" : nameTitleTextBox.getText().trim()));
        item.setJobTitle(((jobTitleTextBox.getText() == null) ? "" : jobTitleTextBox.getText().trim()));
        item.setCompanyName(((companyTextBox.getText() == null) ? "" : companyTextBox.getText().trim()));
        item.setAddress1(((address1TextBox.getText() == null) ? "" : address1TextBox.getText().trim()));
        item.setAddress2(((address2TextBox.getText() == null) ? "" : address2TextBox.getText().trim()));
        item.setAddress3(((address3TextBox.getText() == null) ? "" : address3TextBox.getText().trim()));
        item.setCity(((cityTextBox.getText() == null) ? "" : cityTextBox.getText().trim()));
        item.setState(((statesComboBox.getValue() == null) ? "" : statesComboBox.getValue().trim()));
        item.setZip(((zipCodeTextBox.getText() == null) ? "" : zipCodeTextBox.getText().trim()));
        item.setPhoneOne(StringUtilities.convertPhoneNumberToString(phone1TextBox.getText().trim()));
        item.setPhoneTwo(StringUtilities.convertPhoneNumberToString(phone2TextBox.getText().trim()));
        item.setEmailAddress(((emailTextBox.getText() == null) ? "" : emailTextBox.getText().trim()));
        
        // save information
        SQLparty.savePartyInformation(item);
        search();
    }
    
    private void clearContactCard(){
        partyID = -1;
        prefixComboBox.setValue("");
        firstNameTextBox.setText("");
        miTextBox.setText("");
        lastNameTextBox.setText("");
        suffixTextBox.setText("");
        nameTitleTextBox.setText("");
        jobTitleTextBox.setText("");
        companyTextBox.setText("");
        address1TextBox.setText("");
        address2TextBox.setText("");
        address3TextBox.setText("");
        cityTextBox.setText("");
        statesComboBox.setValue("");
        zipCodeTextBox.setText("");
        phone1TextBox.setText("");
        phone2TextBox.setText("");
        emailTextBox.setText("");
        setCardDisabled(true);
    }
    
    private void setCardDisabled(boolean disabled){
        prefixComboBox.setDisable(disabled);
        firstNameTextBox.setDisable(disabled);
        miTextBox.setDisable(disabled);
        lastNameTextBox.setDisable(disabled);
        suffixTextBox.setDisable(disabled);
        nameTitleTextBox.setDisable(disabled);
        jobTitleTextBox.setDisable(disabled);
        companyTextBox.setDisable(disabled);
        address1TextBox.setDisable(disabled);
        address2TextBox.setDisable(disabled);
        address3TextBox.setDisable(disabled);
        cityTextBox.setDisable(disabled);
        statesComboBox.setDisable(disabled);
        zipCodeTextBox.setDisable(disabled);
        phone1TextBox.setDisable(disabled);
        phone2TextBox.setDisable(disabled);
        emailTextBox.setDisable(disabled);
    }
        
}
