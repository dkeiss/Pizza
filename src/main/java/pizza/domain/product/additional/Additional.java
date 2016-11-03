package pizza.domain.product.additional;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Daniel Keiss on 28.10.2016.
 */
@Entity
@Data
public class Additional {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "ADDITIONAL_ID_SEQ")
    @SequenceGenerator(name = "ADDITIONAL_ID_SEQ", sequenceName = "ADDITIONAL_ID_SEQ", allocationSize = 100)
    private Integer additionalId;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ADDITIONAL_CATEGORY_ID")
    private AdditionalCategory additionalCategory;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "additional", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<AdditionalPrice> additionalPrices;

    private Date creationDate;

}
