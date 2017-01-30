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
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

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
    private Label recordCountLabel;
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
    private Button deleteButton;
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
    
    @FXML
    private void MenuExit() {
        System.exit(0);
    }
    
    @FXML
    private void saveButton(){
        saveContactCard();
    }
    
    @FXML
    private void deleteButton(){
        deleteContact();
    }
    
    @FXML
    private void cancelButton(){
        loadContactCard(partyID);
    }
    
    @FXML
    private void search(){
        clearContactCard();
        ObservableList<partyNameTableModel> list = SQLparty.getContactList();
        loadTable(list);
    }
    
    @FXML
    private void tableListener(MouseEvent event) {
        partyNameTableModel row = searchTable.getSelectionModel().getSelectedItem();
        
        if (row != null && event.getClickCount() >= 1) {
            loadContactCard(Integer.parseInt(row.getID().getValue()));
        }
    }
    
    /**
     * Initializes the controller class.
     * @param url URL
     * @param rb ResourceBundle
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
        
        ValidationSupport validationSupport = new ValidationSupport();
        validationSupport.registerValidator(firstNameTextBox, Validator.createEmptyValidator("Text is required"));
        validationSupport.registerValidator(lastNameTextBox, Validator.createEmptyValidator("Text is required"));
        validationSupport.registerValidator(address1TextBox, Validator.createEmptyValidator("Text is required"));
        validationSupport.registerValidator(cityTextBox, Validator.createEmptyValidator("Text is required"));
        validationSupport.registerValidator(statesComboBox, Validator.createEmptyValidator("Text is required"));
        validationSupport.registerValidator(zipCodeTextBox, Validator.createEmptyValidator("Text is required"));
    }
    
    public void setDefaults(Stage stage){
        mainstage = stage;
        populatePreFixComboBox();
        populateStateComboBox();
        setTableSizes();
        search();
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
    
    private void loadTable(ObservableList<partyNameTableModel> list) {
        searchTable.getItems().removeAll();
        searchTable.setItems(list);
        recordCountLabel.setText("Record Count: " + Global.getRecordCount());
    }
    
    private void loadContactCard(int partyID) {
        partyModel item = SQLparty.getPartydetails(partyID);
                
        setCardDisabled(false);
        this.partyID = partyID;
        prefixComboBox.setValue(item.getPrefix() == null     ? "" : item.getPrefix().trim());
        firstNameTextBox.setText(item.getFirstName() == null ? "" : item.getFirstName().trim());
        miTextBox.setText(item.getMiddleInitial() == null    ? "" : item.getMiddleInitial().trim());
        lastNameTextBox.setText(item.getLastName() == null   ? "" : item.getLastName().trim());
        suffixTextBox.setText(item.getSuffix() == null       ? "" : item.getSuffix().trim());
        nameTitleTextBox.setText(item.getNameTitle() == null ? "" : item.getNameTitle().trim());
        jobTitleTextBox.setText(item.getJobTitle() == null   ? "" : item.getJobTitle().trim());
        companyTextBox.setText(item.getCompanyName() == null ? "" : item.getCompanyName().trim());
        address1TextBox.setText(item.getAddress1() == null   ? "" : item.getAddress1().trim());
        address2TextBox.setText(item.getAddress2() == null   ? "" : item.getAddress2().trim());
        address3TextBox.setText(item.getAddress3() == null   ? "" : item.getAddress3().trim());
        cityTextBox.setText(item.getCity() == null           ? "" : item.getCity().trim());
        statesComboBox.setValue(item.getState() == null      ? "" : item.getState().trim());
        zipCodeTextBox.setText(item.getZip() == null         ? "" : item.getZip().trim());
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
        item.setPrefix("".equals(prefixComboBox.getValue().trim())      ? null : prefixComboBox.getValue().trim());
        item.setFirstName("".equals(firstNameTextBox.getText().trim())  ? null : firstNameTextBox.getText().trim());
        item.setMiddleInitial("".equals(miTextBox.getText().trim())     ? null : miTextBox.getText().trim());
        item.setLastName("".equals(lastNameTextBox.getText().trim())    ? null : lastNameTextBox.getText().trim());
        item.setSuffix("".equals(suffixTextBox.getText().trim())        ? null : suffixTextBox.getText().trim());
        item.setNameTitle("".equals(nameTitleTextBox.getText().trim())  ? null : nameTitleTextBox.getText().trim());
        item.setJobTitle("".equals(jobTitleTextBox.getText().trim())    ? null : jobTitleTextBox.getText().trim());
        item.setCompanyName("".equals(companyTextBox.getText().trim())  ? null : companyTextBox.getText().trim());
        item.setAddress1("".equals(address1TextBox.getText().trim())    ? null : address1TextBox.getText().trim());
        item.setAddress2("".equals(address2TextBox.getText().trim())    ? null : address2TextBox.getText().trim());
        item.setAddress3("".equals(address3TextBox.getText().trim())    ? null : address3TextBox.getText().trim());
        item.setCity("".equals(cityTextBox.getText().trim())            ? null : cityTextBox.getText().trim());
        item.setState(statesComboBox.getValue() == null                 ? null : statesComboBox.getValue().trim());
        item.setZip("".equals(zipCodeTextBox.getText().trim())          ? null : zipCodeTextBox.getText().trim());        
        item.setPhoneOne(StringUtilities.convertPhoneNumberToString(phone1TextBox.getText().trim()));
        item.setPhoneTwo(StringUtilities.convertPhoneNumberToString(phone2TextBox.getText().trim()));
        
        if (item.getPrefix() != null) {
            item.setPrefix("".equals(item.getPrefix().trim()) 
                    ? null : item.getPrefix().trim());
        }
        if (item.getPhoneTwo() != null) {
            item.setPhoneTwo("".equals(item.getPhoneTwo().trim()) 
                    ? null : item.getPhoneTwo().trim());
        }
        
        if (item.getPhoneOne() != null) {
            item.setPhoneOne("".equals(item.getPhoneOne().trim()) 
                    ? null : item.getPhoneOne().trim());
        }
        if (item.getPhoneTwo() != null) {
            item.setPhoneTwo("".equals(item.getPhoneTwo().trim()) 
                    ? null : item.getPhoneTwo().trim());
        }
        
        // save information
        SQLparty.savePartyInformation(item);
        search();
    }
    
    private void deleteContact() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Deletion");
        alert.setHeaderText("Deletion Confirmation");
        alert.setContentText("Are you sure you wish to remove this entry from the database?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            SQLparty.deleteContact(partyID);
            search();
        } else {
            alert.close();
        }
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
        deleteButton.setDisable(disabled);
        cancelButton.setDisable(disabled);
    }
        
}
