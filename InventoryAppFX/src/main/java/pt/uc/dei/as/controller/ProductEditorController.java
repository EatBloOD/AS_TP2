/*
 * Copyright 2017 (C) <University of Coimbra>
 * 
 * Created on : 15-02-2017
 * Author     : Bruno Cabral 
 */
package pt.uc.dei.as.controller;

import java.math.BigDecimal;

import javax.persistence.TypedQuery;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import pt.uc.dei.as.AlertUtil;
import pt.uc.dei.as.MainApp;
import pt.uc.dei.as.entity.*;

// TODO: Auto-generated Javadoc
/**
 * The Class ProductEditorController.
 */
public class ProductEditorController {

	/** The code field. */
	@FXML
	private TextField codeField;
	
	/** The price field. */
	@FXML
	private TextField priceField;
	
	/** The description area. */
	@FXML
	private TextArea descriptionArea;
	
	/** The quantity field. */
	@FXML
	private TextField quantityField;
	
	/** The trees radio button. */
	@FXML
	private RadioButton treesRadioButton;
	
	/** The shrubs radio button. */
	@FXML
	private RadioButton shrubsRadioButton;
	
	/** The seeds radio button. */
	@FXML
	private RadioButton seedsRadioButton;
	
	/** The toggle group. */
	@FXML
	private ToggleGroup toggleGroup = new ToggleGroup();

	/** The dialog stage. */
	private Stage dialogStage;
	
	/** The product. */
	private Product product;
	
	/** The save clicked. */
	private boolean saveClicked = false;


	/**
	 * Initialize.
	 */
	@FXML
	private void initialize() {

		treesRadioButton.setToggleGroup(toggleGroup);
		treesRadioButton.setUserData("Tree");
		shrubsRadioButton.setToggleGroup(toggleGroup);
		shrubsRadioButton.setUserData("Shrub");
		seedsRadioButton.setToggleGroup(toggleGroup);
		seedsRadioButton.setUserData("Seed");
		
	}

	/**
	 * Sets the dialog stage.
	 *
	 * @param dialogStage the new dialog stage
	 */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	/**
	 * Sets the Product.
	 *
	 * @param product the new product
	 */
	public void setProduct(Product product) {
		this.product = product;
		if (product.getIdProducts() > 0 ) {

			codeField.setText(product.getProducts_Code());
			priceField.setText(product.getProducts_Price().toString());
			descriptionArea.setText(product.getProducts_Description());
			quantityField.setText(Integer.toString(product.getProducts_Stock()));
			switch (product.getProductType().getProduct_Types_Code().toString()) {
			case "Tree":
				toggleGroup.selectToggle(treesRadioButton);
				break;
			case "Shrub":
				toggleGroup.selectToggle(shrubsRadioButton);
				break;
			default:
				toggleGroup.selectToggle(seedsRadioButton);
			}
		}
	}

	/**
	 * Checks if is save clicked.
	 *
	 * @return true, if is save clicked
	 */
	public boolean isSaveClicked() {
		return saveClicked;
	}

	/**
	 * Handle save.
	 */
	@FXML
	private void handleSave() {
		if (AlertUtil.askYesNoCancel("Save Product?") == ButtonType.NO)
			return;
		if (isInputValid()) {
			saveClicked = true;
			product.setProducts_Code(codeField.getText());
			product.setProducts_Description(descriptionArea.getText());
			product.setProducts_Price(new BigDecimal(priceField.getText()));
			product.setProducts_Stock(Integer.parseInt(quantityField.getText()));
			try {
				TypedQuery<Product_Type> query = 
						MainApp.em.createNamedQuery("Product_Type.findProductTypeByName", Product_Type.class);
				String pType = toggleGroup.getSelectedToggle().getUserData().toString();
				query.setParameter("ptname", pType);
				product.setProductType(query.getSingleResult());
			} catch (Exception e) {
				AlertUtil.alert("Could not complete the operation", "Something is wrong!", "Try again or restart the application");
				MainApp.refreshEm();
				saveClicked = false;
			}
			dialogStage.close();
		}
	}
	

	/**
	 * Handle cancel.
	 */
	@FXML
	private void handleCancel() {
		
		if (AlertUtil.askYesNoCancel("Changes were not saved. Continue?") == ButtonType.NO)
			return;
		dialogStage.close();
	}

	/**
	 * Checks if is input valid.
	 *
	 * @return true, if is input valid
	 */
	private boolean isInputValid() {
		String errorMessage = "";

		if (codeField.getText() == null || codeField.getText().length() == 0 
				|| codeField.getText().length() > 44) {
			errorMessage += "Invalid code!\n";
		}

		if (priceField.getText() == null || priceField.getText().length() == 0
				|| priceField.getText().length() > 14) {
			errorMessage += "Invalid price!\n";
		}
		if (descriptionArea.getText() == null || descriptionArea.getText().length() == 0
				|| descriptionArea.getText().length() > 249) {
			errorMessage += "Invalid description!\n";
		}
		if (quantityField.getText() == null || quantityField.getText().length() == 0 
				|| quantityField.getText().length() > 10) {
			errorMessage += "Invalid quantity!\n";
		}


		if (errorMessage.length() == 0)
			return true;

		AlertUtil.alert("Invalid inputs", "Please, correct the following problems.", errorMessage);
		return false;

	}
}
