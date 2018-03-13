/*
 * Copyright 2017 (C) <University of Coimbra>
 * 
 * Created on : 15-02-2017
 * Author     : Bruno Cabral 
 */
package entity;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * The persistent class for the Clients database table.
 * 
 */
@Entity
@Table(name = "Clients")
@NamedQueries({ @NamedQuery(name = "Client.findAll", query = "SELECT c FROM Client c"),
		@NamedQuery(name = "Client.findClient", query = "SELECT c FROM Client AS c where c.clients_Name = :clients_name"), })
public class Client implements Serializable {
	private static final long serialVersionUID = 1L;

	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private int idClients;

	@Expose
	@Column(nullable = false, length = 250)
	private String clients_Address;

	@Expose
	@Column(nullable = false, length = 100)
	private String clients_Email;

	@Expose
	@Column(nullable = false, length = 45)
	private String clients_Name;

	@Expose
	@Column(nullable = false, length = 15)
	private String clients_Telephone;

	// bi-directional many-to-one association to Order
	@OneToMany(mappedBy = "client")
	private List<Order> orders;

	public Client() {
	}

	public int getIdClients() {
		return this.idClients;
	}

	public void setIdClients(int idClients) {
		this.idClients = idClients;
	}

	public String getClients_Address() {
		return this.clients_Address;
	}

	public String clients_AddressProperty() {
		return new String(this.clients_Address);
	}

	public void setClients_Address(String clients_Address) {
		this.clients_Address = clients_Address;
	}

	public String getClients_Email() {
		return this.clients_Email;
	}

	public String clients_EmailProperty() {
		return new String(this.clients_Email);
	}

	public void setClients_Email(String clients_Email) {
		this.clients_Email = clients_Email;
	}

	public String getClients_Name() {
		return this.clients_Name;
	}

	public String clients_NameProperty() {
		return new String(this.clients_Name);
	}

	public void setClients_Name(String clients_Name) {
		this.clients_Name = clients_Name;
	}

	public String getClients_Telephone() {
		return this.clients_Telephone;
	}

	public String clients_TelephoneProperty() {
		return new String(this.clients_Telephone);
	}

	public void setClients_Telephone(String clients_Telephone) {
		this.clients_Telephone = clients_Telephone;
	}

	public List<Order> getOrders() {
		return this.orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public Order addOrder(Order order) {
		getOrders().add(order);
		order.setClient(this);

		return order;
	}

	public Order removeOrder(Order order) {
		getOrders().remove(order);
		order.setClient(null);

		return order;
	}

}