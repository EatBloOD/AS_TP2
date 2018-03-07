/*
 * Copyright 2017 (C) <University of Coimbra>
 *
 * Created on : 15-02-2017
 * Author     : Bruno Cabral
 */
package pt.uc.dei.as.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * The persistent class for the Employers database table.
 *
 */
@Entity
@Table(name = "Employers")
@NamedQueries({ @NamedQuery(name = "Employers.findAll", query = "SELECT e FROM Employer e"),
        @NamedQuery(name = "Employers.findEmployer", query = "SELECT e FROM Employer AS e where e.employers_Name = :employers_Name"), })
public class Employer implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int idEmployers;

    @Column(nullable = false, length = 250)
    private String employers_Location;

    @Column(nullable = false, length = 100)
    private String employers_Email;

    @Column(nullable = false, length = 45)
    private String employers_Name;

    @Column(nullable = false, length = 15)
    private String employers_Telephone;

    @Column(nullable = false, length = 100)
    private String employers_Password;

    // bi-directional many-to-one association to Order
    @OneToMany(mappedBy = "employe")
    private List<Order> orders;

    public Employer() {
    }

    public Employer(String name, String email, String password, String location, String phone){
        this.employers_Name = name;
        this.employers_Email = email;
        this.employers_Location = location;
        this.employers_Telephone = phone;
        this.employers_Password = password;
    }

    @Override
    public String toString() {
        return "Employer{" +
                "idEmployers=" + idEmployers +
                ", employers_Location='" + employers_Location + '\'' +
                ", employers_Email='" + employers_Email + '\'' +
                ", employers_Name='" + employers_Name + '\'' +
                ", employers_Telephone='" + employers_Telephone + '\'' +
                ", orders=" + orders +
                '}';
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getIdEmployers() {
        return idEmployers;
    }

    public void setIdEmployers(int idEmployers) {
        this.idEmployers = idEmployers;
    }

    public String getEmployers_Location() {
        return employers_Location;
    }

    public void setEmployers_Location(String employers_Location) {
        this.employers_Location = employers_Location;
    }

    public String getEmployers_Email() {
        return employers_Email;
    }

    public void setEmployers_Email(String employers_Email) {
        this.employers_Email = employers_Email;
    }

    public String getEmployers_Name() {
        return employers_Name;
    }

    public void setEmployers_Name(String employers_Name) {
        this.employers_Name = employers_Name;
    }

    public String getEmployers_Telephone() {
        return employers_Telephone;
    }

    public void setEmployers_Telephone(String employers_Telephone) {
        this.employers_Telephone = employers_Telephone;
    }

    public List<Order> getOrders() {
        return this.orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Order addOrder(Order order) {
        getOrders().add(order);
        //order.setClient(this);

        return order;
    }

    public Order removeOrder(Order order) {
        getOrders().remove(order);
        order.setClient(null);

        return order;
    }

    public String getEmployers_Password() {
        return employers_Password;
    }

    public void setEmployers_Password(String employers_Password) {
        this.employers_Password = employers_Password;
    }
}