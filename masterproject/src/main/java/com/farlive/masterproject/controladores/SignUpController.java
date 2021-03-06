package com.farlive.masterproject.controladores;

import java.net.URL;
import java.util.ResourceBundle;

import com.farlive.masterproject.config.Views;
import com.farlive.masterproject.entidades.Customer;
import com.farlive.masterproject.entidades.Person;
import com.farlive.masterproject.entidades.Role;
import com.farlive.masterproject.entidades.RoleType;
import com.farlive.masterproject.entidades.User;
import com.farlive.masterproject.service.service.CustomerService;
import com.jfoenix.controls.JFXTextField;
import com.kwms.core.alert.Alert;
import com.kwms.core.managent.SceneManagent;
import com.kwms.core.validation.FieldValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

@Controller
public class SignUpController implements Initializable{
    
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
           
    }

    @FXML
    private void createNewAccount(ActionEvent event) {
        if(!FieldValidator.areEmpty(true, fullNameField, userNameField, emailField, passwordField, phoneField, addressField)) {
            String message = customerService.save(newCustomer()) ? "Ahora puede usar sus credenciales para ingresar al sistema."
                                                                 : "Algo salio mal, no se ha podido registrar su cuenta, vuelva a intentarlo";
            Alert.info("Informacion sobre el registro", message, e -> switchCreate(e));     
        }
    }

    @FXML
    private void switchCreate(MouseEvent event) {
        SceneManagent.getInstance().changeScene(Views.LOGIN);
    }

    private Customer newCustomer() {
        Customer customer = new Customer();
        Person person = new Person();
        person.setId(0);
        person.setName(fullNameField.getText());
        person.setAddress(addressField.getText());
        person.setPhone(phoneField.getText());
        User user = new User();
        user.setId(0);
        user.setUsername(userNameField.getText());
        user.setEmail(emailField.getText());
        user.setPassword(passwordField.getText());
        user.setRole(new Role(0, RoleType.CUSTOMER.name()));
        person.setUser(user);
        customer.setPerson(person);
        customer.setCreditcards(null);
        return customer;
    }
}