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
import pt.uc.dei.as.entity.Employer;

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

        //TODO: CÓDIGO PARA INSERIR UTILIZADOR
        /*MainApp.em.getTransaction().begin();
        MainApp.em.persist(new Employer("ola", "12@34", "locX", "91"));
        MainApp.em.getTransaction().commit();*/

            TypedQuery<Employer> queryC = MainApp.em.createNamedQuery("Employers.findEmployer", Employer.class);
            Employer e;
            queryC.setParameter("employers_Name", userNameField.getText());

            try {
                e = queryC.getSingleResult();

                if (e.getEmployers_Name().equals(userNameField.getText())  && e.getEmployers_Password().equals(passwordField.getText())){

                    JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),"Login efectuado com sucesso!");

                    //TODO: USAR ISTO PARA A FUNÇÃO UPDATE
                    /*Criteria criteria = ((Session)MainApp.em.getDelegate()).createCriteria(Employer.class);
                    Employer yourObject = (Employer) criteria.add(Restrictions.eq("employers_Name", "ola"))
                            .uniqueResult();

                    //Employer employee = MainApp.em.find(Employer.class, 1);
                    MainApp.em.getTransaction().begin();
                    yourObject.setEmployers_Name("jorge");
                    MainApp.em.getTransaction().commit();*/

                    //AlertUtil.askYesNoCancel("Login efectuado com sucesso!");
                    mainApp.setEmployer(e);
                    mainApp.showOrderOverview();
                }else{
                    if(AlertUtil.askYesNoCancel("Username ou password errados. Deseja tentar outra vez?!") == ButtonType.NO){
                        return;
                    }
                }
            } catch (NoResultException nre) {
                if(AlertUtil.askYesNoCancel("Username ou password errados. Deseja tentar outra vez?!") == ButtonType.NO){
                    return;
                }
                return;
            }
    }

}
