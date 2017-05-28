package ir.valinejad.entity;

import javax.persistence.*;

@Table(name = "Login")
@Entity
public class Login {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private String id;
}

/*    @Column(name = "user_name")
    private String name;

    @Column(name = "user_mobile")
    private String mobile;

    @ManyToOne
    @JoinColumn(name="studio_id",nullable=false)
    private Studio studio;
}*/
