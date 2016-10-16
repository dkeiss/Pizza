package pizza.domain;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Daniel Keiss on 11.09.2016.
 */
@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq")
    @SequenceGenerator(name = "user_id_seq", sequenceName = "USER_ID_SEQ", allocationSize = 100)
    private Integer id;
    @Column(unique = true)
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private PasswordType passwordType;
    private boolean admin;
    private String firstname;
    private String lastname;
    private BigDecimal discount;
    private Date creationDate;
    private Date modificationDate;

}
