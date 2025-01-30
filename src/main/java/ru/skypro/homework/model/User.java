package ru.skypro.homework.model;

import lombok.*;
import ru.skypro.homework.dto.Role;
import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String phone;

    @Enumerated(EnumType.STRING)
    private Role role;

//    @Lob
//    @Column(columnDefinition = "oid")
    @Basic(fetch=FetchType.LAZY)
    private byte [] image;


    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Ad> ads;

}
