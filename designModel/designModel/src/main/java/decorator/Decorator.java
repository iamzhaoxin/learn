package decorator;

import lombok.Data;

/**
 * @Author: 赵鑫
 * @Date: 2022/8/22
 */
public class Decorator {
    public static void main(String[] args) {
        // 点一份炒饭（注意变量类型是抽象组件，不能是具体组件
        FastFood food=new FiredRice();
        System.out.println(food.getDesc()+" "+food.cost());

        // 炒饭里加鸡蛋（如果food是具体组件，无法接收具体装饰类的对象
        food=new Egg(food); // 这里等于是有两个FastFood类，一个是鸡蛋类，鸡蛋类里有炒饭类。鸡蛋（炒饭）类也是快餐类，所以可以继续被装饰
        System.out.println(food.getDesc()+" "+food.cost());
    }
}

// 抽象组件
@Data
abstract class FastFood{

    private float price;    //价格
    private String desc;    //描述

    public FastFood(){}
    public FastFood(float price, String desc) {
        this.price = price;
        this.desc = desc;
    }

    public abstract float cost();   //抽象方法
}

// 具体组件
class FiredRice extends FastFood{

    public FiredRice(){
        super(10,"炒饭");
    }

    @Override
    public float cost() {
        return getPrice();
    }
}

// 具体组件
class FiredNoodles extends FastFood{
    public FiredNoodles() {
        super(15,"炒面");
    }

    @Override
    public float cost() {
        return getPrice();
    }
}

// 抽象装饰
abstract class Garnish extends FastFood{
    // 声明快餐类的变量
    private FastFood fastFood;

    public FastFood getFastFood() {
        return fastFood;
    }
    public void setFastFood(FastFood fastFood) {
        this.fastFood = fastFood;
    }

    public Garnish(FastFood fastFood, float price, String desc){
        super(price,desc);
        this.fastFood=fastFood;
    }
}

// 具体装饰
class Egg extends Garnish{
    public Egg(FastFood fastFood){
        super(fastFood,1,"加鸡蛋");
    }

    @Override
    public float cost() {
        return getPrice()+ getFastFood().cost();
    }

    @Override
    public String getDesc() {
        return super.getDesc()+getFastFood().getDesc();
    }
}
