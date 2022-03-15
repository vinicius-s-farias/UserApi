package com.okta.springbootspa.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Setter
@Getter
@Entity
@Table(name = "users_orders")
public class UserOrder implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order")
    private long idOrder;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User idUser;
    @Column(name = "id_stock")
    private Long idStock;
    @Column(name = "stock_symbol")
    private String stockSymbol;
    @Column(name = "stock_name")
    private String stockName;
    private double price;
    private int type;
    private int status;
    private Long volume;
    @Column(name = "remaining_value")
    private Long remainingValue;
    @CreationTimestamp
    @Column(name = "created_on")
    private Timestamp createdOn;
    @UpdateTimestamp
    @Column(name = "updated_on")
    private Timestamp updatedOn;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserOrder userOrder = (UserOrder) o;
        return idOrder == userOrder.idOrder;
    }
    @Override
    public int hashCode() {
        return Objects.hash(idOrder);
    }


}
