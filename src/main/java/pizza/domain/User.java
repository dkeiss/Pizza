package pizza.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Daniel Keiss on 11.09.2016.
 */
@Entity
@Data
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id_seq")
	@SequenceGenerator(name = "product_id_seq", sequenceName = "PRODUCT_ID_SEQ", allocationSize = 100)
	private Integer id;

	private String username;
	private String password;
	@Enumerated(EnumType.STRING)
	private PasswordType passwordType;
	private boolean admin;
	private String firstname;
	private String lastname;
	private Date creationDate;

}
