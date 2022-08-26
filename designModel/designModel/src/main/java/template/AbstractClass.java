package template;

/**
 * @Author: 赵鑫
 * @Date: 2022/8/24
 */
//抽象类（定义模板方法和基本方法）
public abstract class AbstractClass {
    //模板方法
    public void cookProcess() {
        pourOil();
        heatOil();
        pourVegetable();
        pourSauce();
        getFood();
    }

    //Concrete Method
    public void pourOil() {
        System.out.println("倒油");
    }

    public void heatOil() {
        System.out.println("热油");
    }

    public void getFood(){
        System.out.println("出锅");
    }

    //Abstract Method
    public abstract void pourVegetable();

    public abstract void pourSauce();
}
//具体子类
class ConcreteClass extends AbstractClass{
    @Override
    public void pourSauce() {
        System.out.println("放调料");
    }

    @Override
    public void pourVegetable() {
        System.out.println("放蔬菜");
    }
}

class Client{
    public static void main(String[] args) {
        AbstractClass cooker=new ConcreteClass();
        cooker.cookProcess();
    }
}