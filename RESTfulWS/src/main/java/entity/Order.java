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
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the Orders database table.
 * 
 */
@Entity
@Table(name = "Orders")
@NamedQuery(name = "Order.findAll", query = "SELECT o FROM Order o")
public class Order implements Serializable {
	private static final long serialVersionUID = 1L;

	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private int idOrders;

	@Expose
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date orders_Date;

	@Expose
	@Column(nullable = false)
	private byte orders_Shipped;

	@Expose
	@Column(nullable = false, precision = 10)
	private BigDecimal orders_Total_Cost;

	@Expose
	// bi-directional many-to-one association to Item
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<Item> items;

	@Expose
	// bi-directional many-to-one association to Client
	@ManyToOne
	@JoinColumn(name = "Clients_idClients", nullable = false)
	private Client client;

	public Order() {
	}

	public int getIdOrders() {
		return this.idOrders;
	}

	public void setIdOrders(int idOrders) {
		this.idOrders = idOrders;
	}

	public Date getOrders_Date() {
		return this.orders_Date;
	}

	public String orders_DateProperty() {
		SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
		String date1 = format1.format(this.orders_Date);
		return new String(date1);
	}

	public void setOrders_Date(Date orders_Date) {
		this.orders_Date = orders_Date;
	}

	public byte getOrders_Shipped() {
		return this.orders_Shipped;
	}

	public String orders_ShippedProperty() {
		if (orders_Shipped != 0)
			return new String("Yes");
		return new String("No");
	}

	public void setOrders_Shipped(byte orders_Shipped) {
		this.orders_Shipped = orders_Shipped;
	}

	public BigDecimal getOrders_Total_Cost() {
		return this.orders_Total_Cost;
	}

	public String orders_Total_CostProperty() {
		return new String(this.orders_Total_Cost.toString());
	}

	public void setOrders_Total_Cost(BigDecimal orders_Total_Cost) {
		this.orders_Total_Cost = orders_Total_Cost;
	}

	public List<Item> getItems() {
		return this.items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public Item addItem(Item item) {
		getItems().add(item);
		item.setOrder(this);

		return item;
	}

	public Item removeItem(Item item) {
		getItems().remove(item);
		item.setOrder(null);

		return item;
	}

	public Client getClient() {
		return this.client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

}