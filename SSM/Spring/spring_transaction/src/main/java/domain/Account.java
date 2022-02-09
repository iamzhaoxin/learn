package domain;

/**
 * @Author: 赵鑫
 * @Date: 2022/2/9 14:51
 */
public class Account {
    int id;
    double money;

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", money=" + money +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}
