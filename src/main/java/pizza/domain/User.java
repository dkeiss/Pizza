package pizza.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Daniel Keiss on 11.09.2016.
 */
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prooduct_id_seq")
    @SequenceGenerator(name = "prooduct_id_seq", sequenceName = "PRODUCT_ID_SEQ", allocationSize = 100)
    private Integer id;

    private String alias;
    private String firstname;
    private String lastname;
    private Date creationDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
