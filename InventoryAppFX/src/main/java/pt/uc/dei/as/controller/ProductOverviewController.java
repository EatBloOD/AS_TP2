/*
 * Copyright 2017 (C) <University of Coimbra>
 * 
 * Created on : 15-02-2017
 * Author     : Bruno Cabral 
 */
package pt.uc.dei.as.controller;

import java.util.ArrayList;

import javax.persistence.TypedQuery;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import pt.uc.dei.as.AlertUtil;
import pt.uc.dei.as.MainApp;
import pt.uc.dei.as.entity.*;

// TODO: Auto-generated Javadoc
/**
 * The Class ProductOverviewController.
 */
public class ProductOverviewController {
	
	/** The product table. */
	@FXML
	private TableView<Product> productTable;

	/** The code column. */
	@FXML
	private TableColumn<Product, String> codeColumn;
	
	/** The description column. */
	@FXML
	private TableColumn<Product, String> descriptionColumn;
	
	/** The quantity column. */
	@FXML
	private TableColumn<Product, String> quantityColumn;

	/** The price column. */
	@FXML
	private TableColumn<Product, String> priceColumn;
	
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

	/** The main app. */
	private MainApp mainApp;

	/**
	 * Instantiates a new product overview controller.
	 */
	public ProductOverviewController() {

	}

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
		toggleGroup.selectToggle(treesRadioButton);
		
		
		codeColumn.setCellValueFactory(cellData -> cellData.getValue().products_CodeProperty());
		descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().products_DescriptionProperty());
		quantityColumn.setCellValueFactory(cellData -> cellData.getValue().products_StockProperty());
		priceColumn.setCellValueFactory(cellData -> cellData.getValue().products_PriceProperty());


		productTable.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.DELETE)
					handleDelete();
				else if (event.getCode() == KeyCode.ENTER)
					handleEditProduct();
			}
		});

		productTable.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
					handleEditProduct();
				}
			}
		});
		

		
		toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> obs, Toggle wasPreviouslySelected,
					Toggle isNowSelected) {
				handleRefresh();
			}
		});
		
		

	}

	/**
	 * Sets the main app.
	 *
	 * @param mainApp the new main app
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		handleRefresh();
		productTable.setItems(mainApp.getProductsData());
	}


	/**
	 * Handle new product.
	 */
	@FXML
	private void handleNewProduct() {
		Product newProduct = new Product();
		newProduct.setItems(new ArrayList<Item>());
		boolean saveClicked = mainApp.showProductEditorDialog(newProduct);
		if (saveClicked) {

			try {
				MainApp.em.getTransaction().begin();
				MainApp.em.persist(newProduct);
				MainApp.em.getTransaction().commit();
			} catch (Exception e) {
				AlertUtil.alert("Could not complete the operation", "Something is wrong!", "Try again or restart the application");
				handleRefresh();
				return;
			}
			switch(newProduct.getProductType().getProduct_Types_Code()) {
			case "Tree":
				toggleGroup.selectToggle(treesRadioButton);
				break;
			case "Shrub":
				toggleGroup.selectToggle(shrubsRadioButton);
				break;
			default:
				toggleGroup.selectToggle(seedsRadioButton);
			}
			handleRefresh();
		}
	}

	/**
	 * Handle edit product.
	 */
	@FXML
	private void handleEditProduct() {
		Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
		if (selectedProduct != null) {
			boolean saveClicked = mainApp.showProductEditorDialog(selectedProduct);
			if (saveClicked) {
				try {
					MainApp.em.getTransaction().begin();
					selectedProduct = MainApp.em.merge(selectedProduct);
					MainApp.em.getTransaction().commit();
				} catch (Exception e) {
					AlertUtil.alert("Could not complete the operation", "Something is wrong!", "Try again or restart the application");
					handleRefresh();
					return;
				}
				switch(selectedProduct.getProductType().getProduct_Types_Code()) {
				case "Tree":
					toggleGroup.selectToggle(treesRadioButton);
					break;
				case "Shrub":
					toggleGroup.selectToggle(shrubsRadioButton);
					break;
				default:
					toggleGroup.selectToggle(seedsRadioButton);
				}
				handleRefresh();
			}

		} else {
			AlertUtil.alert("No selection", "Product not selected", "Please, select as product on the left table.");
		}
	}


	/**
	 * Handle delete.
	 */
	@FXML
	private void handleDelete() {

		if (productTable.getItems() != null && productTable.getItems().size() > 0
				&& productTable.getSelectionModel().getSelectedItem() != null) {

			Product p = productTable.getSelectionModel().getSelectedItem();

			if (AlertUtil.askYesNoCancel("Delete Product?") == ButtonType.NO)
				return;
			mainApp.getProductsData().remove(productTable.getSelectionModel().getSelectedIndex());
			productTable.refresh();

			try {
				MainApp.em.getTransaction().begin();
				MainApp.em.remove(p);
				MainApp.em.getTransaction().commit();
			} catch (Exception e) {
				AlertUtil.alert("Could not complete the operation", "Something is wrong! Product might not be deletable at this time.", "Try again or check if the product has already be referenced in an order.");
				handleRefresh();			
			}
			return;
		}
		AlertUtil.alert("No selection", "Product not selected", "Please, select as product on the left table.");
	}

	/**
	 * Handle refresh.
	 */
	@FXML
	private void handleRefresh() {
		try {
			MainApp.refreshEm();
			TypedQuery<Product> queryP = MainApp.em.createNamedQuery("Product.findProductsByType",
					Product.class);
			String param = toggleGroup.getSelectedToggle().getUserData().toString();
			queryP.setParameter("product_type", param);
			mainApp.setProductsData(queryP.getResultList());
			productTable.setItems(mainApp.getProductsData());
		} catch (Exception e) {
			AlertUtil.alert("Could not complete the operation", "Something is wrong!", "Try again or restart the application");
			MainApp.refreshEm();
		}
		productTable.refresh();
		return;
	}

}
