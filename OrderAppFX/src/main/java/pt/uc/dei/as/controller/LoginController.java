/*
 * Copyright 2017 (C) <University of Coimbra>
 *
 * Created on : 15-02-2017
 * Author     : Bruno Cabral
 */
package pt.uc.dei.as.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import pt.uc.dei.as.AlertUtil;
import pt.uc.dei.as.MainApp;
import pt.uc.dei.as.RestsUtils;
import pt.uc.dei.as.data.Login;
import pt.uc.dei.as.entity.Employer;

import java.net.HttpURLConnection;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.swing.*;

/**
 * The Class OrderOverviewController.
 */
public class LoginController {

    /** The name field. */
    @FXML
    private TextField userNameField;

    /** The telephone field. */
    @FXML
    private PasswordField passwordField;

    /** The root layout. */
    private BorderPane rootLayout;

    /** The main app. */
    private MainApp mainApp;

    /**
     * Instantiates a new login overview controller.
     */
    public LoginController() {
    }

    /**
     * Initialize.
     */
    @FXML
    private void initialize() {
    }

    /**
     * Sets the main app.
     *
     * @param mainApp the new main app
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

    }

    @FXML
    private void handleSave() {
        String username = userNameField.getText();
        String userpassword = passwordField.getText();
        System.out.println("UserName = " + username);
        System.out.println("UserPassword = " + userpassword);

        Login login = new Login();

        login.setPassword(userpassword);
        login.setUSername(username);

        try {
            
            Employer e = RestsUtils.doPost("login", login , Employer.class, HttpURLConnection.HTTP_OK);

            System.out.println("Employer: " + e.toString());

            if (e != null){

                JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),"Login efectuado com sucesso!");

                mainApp.setEmployer(e);
                mainApp.showOrderOverview();
            }else{
                if(AlertUtil.askYesNoCancel("Username ou password errados. Deseja tentar outra vez?!") == ButtonType.NO){
                    return;
                }
            }
        } catch (Exception nre) {
            if(AlertUtil.askYesNoCancel("Username ou password errados. Deseja tentar outra vez?!") == ButtonType.NO){
                return;
            }
            return;
        } 
    }

}
