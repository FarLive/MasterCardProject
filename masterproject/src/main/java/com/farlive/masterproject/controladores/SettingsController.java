package com.farlive.masterproject.controladores;

import java.net.URL;
import java.util.ResourceBundle;

import com.farlive.masterproject.config.Views;
import com.farlive.masterproject.entidades.Customer;
import com.farlive.masterproject.entidades.Person;
import com.farlive.masterproject.entidades.User;
import com.farlive.masterproject.service.service.CustomerService;
import com.jfoenix.controls.JFXTextField;
import com.kwms.core.alert.Alert;
import com.kwms.core.managent.SceneManagent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

@Controller
public class SettingsController implements Initializable {

    @FXML
    private AnchorPane registerClientPane;

    @FXML
    private JFXTextField fullNameField;

    @FXML
    private JFXTextField userNameField;

    @FXML
    private JFXTextField emailField;

    @FXML
    private JFXTextField passwordField;

    @FXML
    private JFXTextField phoneField;

    @FXML
    private JFXTextField addressField;

    @Autowired
    private CustomerService customerService;

    private Customer customer;

    @FXML
    private void createNewAccount(ActionEvent event) {
        Person person = customer.getPerson();
        person.setName(fullNameField.getText());
        person.setAddress(addressField.getText());
        person.setPhone(phoneField.getText());
        User user = customer.getPerson().getUser();
        user.setUsername(userNameField.getText());
        user.setEmail(emailField.getText());
        user.setPassword(passwordField.getText());
        person.setUser(user);
        customer.setPerson(person);
        if(customerService.save(customer)) {
            Alert.success("Completado", "Sus datos han sido actualizados correctamente");
            SceneManagent.getInstance().getModalStage().close();
        }else {
            Alert.error("Oh no!", "Ha ocurrido un error al actualizar su informacion");
        }
    }

    @FXML
    private void deleteAccount(ActionEvent event) {
        customerService.remove(customer);
        SceneManagent.getInstance().getModalStage().close();
        SceneManagent.getInstance().changeScene(Views.LOGIN);
    }

    protected void putInfoCustomer(Customer customer) {
        this.customer = customer;
        Person person = customer.getPerson();
        fullNameField.setText(person.getName());
        userNameField.setText(person.getUser().getUsername());
        emailField.setText(person.getUser().getEmail());
        passwordField.setText(person.getUser().getPassword());
        phoneField.setText(person.getPhone());
        addressField.setText(person.getAddress());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
   
}