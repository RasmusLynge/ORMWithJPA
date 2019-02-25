/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author Rasmu
 */
@Entity
public class CustomerOrder implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @ManyToOne
    private Customer customer;
    @OneToMany(mappedBy = "customerOrder")
    private List<OrderLine> oLines = new ArrayList();

    public CustomerOrder() {
    }

    public CustomerOrder(Customer customer) {
        this.customer = customer;
    }

    public List<OrderLine> getoLines() {
        return oLines;
    }

    public void setoLines(List<OrderLine> oLines) {
        this.oLines = oLines;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "CustomerOrder id: " + id + ", customer: " + customer + ", oLines: " + oLines + ". ";
    }

}
