package com.okta.springbootspa.keys;

import com.okta.springbootspa.model.User;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class Chave implements Serializable {

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    private Long id_stock;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId_stock() {
        return id_stock;
    }

    public void setId_stock(Long id_stock) {
        this.id_stock= id_stock;
    }

    public Chave(User user, Long id_stock) {
        this.user = user;
        this.id_stock = id_stock;
    }

    public Chave() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Chave)) return false;
        Chave chave = (Chave) o;
        return user.equals(chave.user) && id_stock.equals(chave.id_stock);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, id_stock);
    }
}
