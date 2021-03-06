package com.okta.springbootspa.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.Optional;

@Setter
@Getter
@Entity
@Table(name = "users_stocks_balances")
public class UserStock implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User idUser;
    @Column(name = "id_stock")
    private Long idStock;
    @Column(name = "stock_symbol")
    private String stockSymbol;
    @Column(name = "stock_name")
    private String stockName;
    private Long volume;
    @CreationTimestamp
    @Column(name = "created_on")
    private Timestamp createdOn;
    @UpdateTimestamp
    @Column(name = "updated_on")
    private Timestamp updatedOn;

    public UserStock(User idUser, Long idStock, String stockSymbol, String stockName, Long volume) {
        this.idUser = idUser;
        this.idStock = idStock;
        this.stockSymbol = stockSymbol;
        this.stockName = stockName;
        this.volume = volume;
    }
    public UserStock() {
    }


    public UserStock(long l, long l1, String teste, String teste1, long l2) {
    }

    public UserStock(Optional<User> user, long l, String teste, String teste1, long l1) {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserStock userStock = (UserStock) o;
        return id == userStock.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


}
