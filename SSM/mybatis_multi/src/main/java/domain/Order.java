package domain;

/**
 * @Author: 赵鑫
 * @Date: 2022/2/10 21:58
 */
public class Order {
    private int oid;    // 订单id
    private String things;  // 商品

    private User user;    // 用户id

    @Override
    public String toString() {
        return "Order{" +
                "oid=" + oid +
                ", user=" + user +
                ", things='" + things + '\'' +
                '}';
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }



    public String getThings() {
        return things;
    }

    public void setThings(String things) {
        this.things = things;
    }
}
