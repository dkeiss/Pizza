package pizza.domain.user;

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
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "USER_ID_SEQ")
    @SequenceGenerator(name = "USER_ID_SEQ", sequenceName = "USER_ID_SEQ", allocationSize = 100)
    private Integer userId;

    @Column(unique = true)
    private String userName;

    private String password;

    @Enumerated(EnumType.STRING)
    private PasswordType passwordType;

    private boolean isAdmin;

    private String firstName;

    private String lastName;

    private BigDecimal discount = BigDecimal.ZERO;

    private Date creationDate;

    private Date modificationDate;

}
