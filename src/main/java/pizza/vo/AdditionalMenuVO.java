package pizza.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Daniel Keiss on 26.09.2016.
 */
@Data
public class AdditionalMenuVO {

    public List<Long> numbers;
    public List<AdditionMenuAsVO> as;

}

