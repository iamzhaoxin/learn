package eventbus.events;

import com.google.common.base.MoreObjects;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author: 赵鑫
 * @Date: 2022/7/3
 */
@Getter
@Setter
public class Fruit {

    private String name;

    public Fruit(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return MoreObjects.toStringHelper(this).add("Name",name).toString();
    }
}
