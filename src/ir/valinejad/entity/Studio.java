package ir.valinejad.entity;

import javax.persistence.*;
import java.util.Set;

@Table(name = "STUDIO")
@Entity
public class Studio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "studio_id")
    private Long id;

    @Column(name = "studio_name", nullable = false)
    private String name;

    @Column(name = "studio_address")
    private String address;

    @Column (name = "username", nullable = false)
    private String username;

    @Column (name = "password", nullable = false)
    private String password;

/*    @OneToMany(mappedBy = "studio")
    private Set<Login> users;*/

    @OneToMany(mappedBy = "studio")
    private Set<Wedding> weddings;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Wedding> getWeddings() {
        return weddings;
    }

    public void setWeddings(Set<Wedding> weddings) {
        this.weddings = weddings;
    }
}