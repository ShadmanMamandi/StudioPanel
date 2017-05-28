package ir.valinejad.entity;

import javax.persistence.*;

@Entity
@Table(name = "test")
public class Test {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    public String id;
}
