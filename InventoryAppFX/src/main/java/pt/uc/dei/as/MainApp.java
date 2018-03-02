/*
 * Copyright 2017 (C) <University of Coimbra>
 * 
 * Created on : 15-02-2017
 * Author     : Bruno Cabral 
 */
package pt.uc.dei.as;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pt.uc.dei.as.controller.ProductEditorController;
import pt.uc.dei.as.controller.ProductOverviewController;
import pt.uc.dei.as.entity.Product;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

// TODO: Auto-generated Javadoc
/**
 * The Class MainApp.
 */
public class MainApp extends Application {

	/** The primary stage. */
	private Stage primaryStage;
	
	/** The root layout. */
	private BorderPane rootLayout;

	/** The em. */
	public static EntityManager em;
	
	/** The pm. */
	private static PersistenceManager pm;

	/** The products data. */
	private ObservableList<Product> productsData = FXCollections.observableArrayList();

	/**
	 * Instantiates a new main app.
	 *
	 * @throws Exception the exception
	 */
	public MainApp() throws Exception {

	}

	/**
	 * Gets the products data.
	 *
	 * @return the products data
	 */
	public ObservableList<Product> getProductsData() {
		return productsData;
	}

	/**
	 * Sets the products data.
	 *
	 * @param productsData the new products data
	 */
	public void setProductsData(List<Product> productsData) {
		this.productsData.setAll(productsData);
	}

	/* (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("InventoryAppFX");
		this.primaryStage.getIcons().add(new Image("/images/sprout.png"));
		Platform.setImplicitExit(true);

		initRootLayout();
		showProductOverview();
	}

	/* (non-Javadoc)
	 * @see javafx.application.Application#stop()
	 */
	@Override
	public void stop() throws Exception {
		super.stop();
		Platform.exit();
		System.exit(0);
	}

	/**
	 * Inits the root layout.
	 */
	public void initRootLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/fxml/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();
			rootLayout.getStylesheets().add("/stylesheets/DarkTheme.css");

			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Show product overview.
	 */
	public void showProductOverview() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/fxml/ProductOverview.fxml"));
			AnchorPane productOverview = (AnchorPane) loader.load();
			productOverview.getStylesheets().add("/stylesheets/DarkTheme.css");

			rootLayout.setCenter(productOverview);
			ProductOverviewController controller = loader.getController();
			controller.setMainApp(this);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Show product editor dialog.
	 *
	 * @param product the product
	 * @return true, if successful
	 */
	public boolean showProductEditorDialog(Product product) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/fxml/ProductEditor.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			page.getStylesheets().add("/stylesheets/DarkTheme.css");

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Edit Product");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			ProductEditorController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setProduct(product);

			dialogStage.showAndWait();

			return controller.isSaveClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Gets the primary stage.
	 *
	 * @return the primary stage
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	/**
	 * Refresh em.
	 */
	public static void refreshEm() {
		MainApp.em.close();
		MainApp.em = MainApp.pm.getEntityManager();
	}
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		try {
			if (args != null && args.length == 4) {
				MainApp.pm = new PersistenceManager(args[0], args[1], args[2], args[3]);
			} else if (args != null && args.length == 1) {
				MainApp.pm = new PersistenceManager(args[0]);
			} else {
				MainApp.pm = new PersistenceManager();
			}
			MainApp.em = pm.getEntityManager();
		} catch (Exception e) {
			System.err.println(
					"\nInventoryAppFX: Cannot connect to the \"ete_db\" database!. Please check if the database is running and properly configured.\n");
			System.err.println(
					"InventoryAppFX: To bypass the default database configuration please use the following command:");
			System.err.println("java -jar InventoryAppFX-0.0.1.jar <driver> <url> <username> <password> \n");
			System.err.println("InventoryAppFX: Alternatively, just pass the database server IP:");
			System.err.println("java -jar InventoryAppFX-0.0.1.jar <db_server_IP> \n");
			System.exit(-1);
		}
		launch(args);
	}

}
