/*
 * Copyright 2017 (C) <University of Coimbra>
 * 
 * Created on : 15-02-2017
 * Author     : Bruno Cabral 
 */
package pt.uc.dei.as.controller;


import javax.persistence.TypedQuery;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import pt.uc.dei.as.AlertUtil;
import pt.uc.dei.as.MainApp;
import pt.uc.dei.as.entity.*;

// TODO: Auto-generated Javadoc
/**
 * The Class OrderOverviewController.
 */
public class OrderOverviewController {
	
	/** The order table. */
	@FXML
	private TableView<Order> orderTable;

	/** The name column. */
	@FXML
	private TableColumn<Order, String> nameColumn;
	
	/** The date column. */
	@FXML
	private TableColumn<Order, String> dateColumn;
	
	/** The shipped column. */
	@FXML
	private TableColumn<Order, String> shippedColumn;

	/** The items table. */
	@FXML
	private TableView<Item> itemsTable;

	/** The description column. */
	@FXML
	private TableColumn<Item, String> descriptionColumn;
	
	/** The quantity column. */
	@FXML
	private TableColumn<Item, String> quantityColumn;
	
	/** The price column. */
	@FXML
	private TableColumn<Item, String> priceColumn;

	/** The order total label. */
	@FXML
	private Label orderTotalLabel;
	
	/** The telephone label. */
	@FXML
	private Label telephoneLabel;
	
	/** The address label. */
	@FXML
	private Label addressLabel;
	
	/** The email label. */
	@FXML
	private Label emailLabel;

	/** The main app. */
	private MainApp mainApp;

	/** The items data. */
	private ObservableList<Item> itemsData = FXCollections.observableArrayList();

	/**
	 * Instantiates a new order overview controller.
	 */
	public OrderOverviewController() {

	}

	/**
	 * Initialize.
	 */
	@FXML
	private void initialize() {
		nameColumn.setCellValueFactory(cellData -> cellData.getValue().getClient().clients_NameProperty());
		dateColumn.setCellValueFactory(cellData -> cellData.getValue().orders_DateProperty());
		shippedColumn.setCellValueFactory(cellData -> cellData.getValue().orders_ShippedProperty());
		descriptionColumn
				.setCellValueFactory(cellData -> cellData.getValue().getProduct().products_DescriptionProperty());
		quantityColumn.setCellValueFactory(cellData -> cellData.getValue().items_QuantityProperty());
		priceColumn.setCellValueFactory(cellData -> cellData.getValue().items_Unit_PriceProperty());

		showOrderDetails(null);

		orderTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showOrderDetails(newValue));


	}

	/**
	 * Sets the main app.
	 *
	 * @param mainApp the new main app
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;

		orderTable.setItems(mainApp.getOrdersData());
	}

	/**
	 * Show order details.
	 *
	 * @param order the order
	 */
	private void showOrderDetails(Order order) {
		if (order != null) {
			orderTotalLabel.setText(order.getOrders_Total_Cost().toString());
			telephoneLabel.setText(order.getClient().getClients_Telephone());
			addressLabel.setText(order.getClient().getClients_Address());
			emailLabel.setText(order.getClient().getClients_Email());
			itemsData.clear();
			for (Item i : order.getItems()) {
				itemsData.add(i);
			}
			itemsTable.setItems(itemsData);

		} else {
			orderTotalLabel.setText("");
			telephoneLabel.setText("");
			addressLabel.setText("");
			emailLabel.setText("");
			itemsTable.getItems().clear();
		}
	}



	/**
	 * Handle toggle shipped.
	 */
	@FXML
	private void handleToggleShipped() {
		if (orderTable.getItems() != null && orderTable.getItems().size() > 0
				&& orderTable.getSelectionModel().getSelectedItem() != null) {
			Order o = orderTable.getSelectionModel().getSelectedItem();
			if (AlertUtil.askYesNoCancel((o.getOrders_Shipped() == 0 ? "Set the order status to SHIPPED?"
					: "Set the order status to NOT SHIPPED?")) == ButtonType.NO)
				return;
			o.setOrders_Shipped((byte) (o.getOrders_Shipped() == 0 ? 1 : 0));
			orderTable.refresh();

			try {
				MainApp.em.getTransaction().begin();
				MainApp.em.merge(o);
				MainApp.em.getTransaction().commit();
			} catch (Exception e) {
				AlertUtil.alert("Could not complete the operation", "Something is wrong!", "Try again or restart the application");
				handleRefresh();
			}
			return;
		}
		AlertUtil.alert("No selection", "Order not selected", "Please, select as order on the left table.");
	}



	/**
	 * Handle refresh.
	 */
	@FXML
	private void handleRefresh() {
		try {
			MainApp.refreshEm();
			TypedQuery<Order> query = MainApp.em.createNamedQuery("Order.findAll", Order.class);
			mainApp.setOrdersData(query.getResultList());
			orderTable.setItems(mainApp.getOrdersData());
		} catch (Exception e) {
			AlertUtil.alert("Could not complete the operation", "Something is wrong!", "Try again or restart the application");
			MainApp.refreshEm();
		}
		orderTable.refresh();
		return;
	}

}
