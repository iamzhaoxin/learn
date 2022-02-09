package domain;

/**
 * @Author: 赵鑫
 * @Date: 2022/2/8 13:52
 */
public class User {
    private Integer id;
    private Double money;
    private String password;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", money=" + money +
                ", password='" + password + '\'' +
                '}';
    }

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
}
