package pizza.vo;

import lombok.Data;

import java.util.List;

/**
 * Created by Daniel Keiss on 26.09.2016.
 */
@Data
public class AdditionMenuAsVO {

    public boolean duty;
    public List<AdditionalMenuBsVO> bs;

}
