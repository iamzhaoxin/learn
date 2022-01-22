package pojo;

/**
 * @Author: 赵鑫
 * @Date: 2022/1/22 16:56
 */

public class User {
    private Integer id;
    private Double money;
    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User() {
        id=null;
        money=null;
        password=null;
    }

    public User(Integer id, Double money, String password) {
        this.id = id;
        this.money = money;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", money=" + money +
                ", password='" + password + '\'' +
                '}';
    }
}
