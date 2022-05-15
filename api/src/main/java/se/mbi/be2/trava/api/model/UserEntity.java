package se.mbi.be2.trava.api.model;

import javax.persistence.*;

@Entity
@Table(name = "user_")
public class UserEntity /*implements UserDetails*/ {

    @Id
    // @GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String password;

    public UserEntity() {

    }

    public UserEntity(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
