package com.okta.springbootspa.model;

import com.okta.springbootspa.keys.Chave;
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
@Table(name = "users_stocks_balances")
public class UserStock implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User id_user;
    private Long id_stock;
    private String stock_symbol;
    private String stock_name;
    private Long volume;
    @CreationTimestamp
    @Column(name = "created_on")
    private Timestamp created_on;
    @UpdateTimestamp
    @Column(name = "updated_on")
    private Timestamp updated_on;

    public UserStock(User id_user,Long id, Long id_stock, String stock_symbol, String stock_name, Long volume) {
        this.id_user = id_user;
        this.id = id;
        this.id_stock = id_stock;
        this.stock_symbol = stock_symbol;
        this.stock_name = stock_name;
        this.volume = volume;
    }
    public UserStock() {

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
