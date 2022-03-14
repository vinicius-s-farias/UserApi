package com.okta.springbootspa.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
@Service
@Setter
@Getter
@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private double dollar_balance;
    private boolean enabled;
    @CreationTimestamp
    @Column(name = "created_on")
    private Timestamp created_on;
    @UpdateTimestamp
    @Column(name = "updated_on")
    private Timestamp updated_on;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result();
    }

    private int result() {
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    public User(String username, String password, double dollar_balance) {
        this.username = username;
        this.password = password;
        this.dollar_balance = dollar_balance;
    }

    public User() {
    }
}
