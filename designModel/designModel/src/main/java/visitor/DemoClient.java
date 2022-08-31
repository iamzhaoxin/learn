package visitor;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**年底，CEO和CTO开始评定员工一年的工作绩效，
 * 员工分为工程师和经理，
 * CTO关注工程师的代码量、经理的新产品数量；
 * CEO关注的是工程师的KPI和经理的KPI以及新产品数量。
 * 由于CEO和CTO对于不同员工的关注点是不一样的，这就需要对不同员工类型进行不同的处理。
 * 访问者模式此时可以派上用场了。
 * @Author: 赵鑫
 * @Date: 2022/8/30
 */
public class DemoClient {

    public static void main(String[] args) {
        // 构建报表
        BusinessReport report = new BusinessReport();
        System.out.println("=========== CEO看报表 ===========");
        report.showReport(new CEOVisitor());
        System.out.println("=========== CTO看报表 ===========");
        report.showReport(new CTOVisitor());
    }
}

// 员工基类（元素）
@Data
abstract class Staff{
    private String name;
    private int kpi;

    public Staff(String name){
        this.name=name;
        kpi=new Random().nextInt(10);
    }

    // 核心方法，接受visitor的访问
    public abstract void accept(Visitor visitor);
}

// 具体元素
class Engineer extends Staff{

    public Engineer(String name) {
        super(name);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    // 工程师一年代码量
    public int getCodeLines(){
        return new Random().nextInt(10*10000);
    }
}

// 具体元素
class Manager extends Staff {

    public Manager(String name) {
        super(name);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
    // 一年做的产品数量
    public int getProducts() {
        return new Random().nextInt(10);
    }
}

// 员工业务报表类
class BusinessReport {

    private List<Staff> mStaffs = new LinkedList<>();

    public BusinessReport() {
        mStaffs.add(new Manager("经理-A"));
        mStaffs.add(new Engineer("工程师-A"));
        mStaffs.add(new Engineer("工程师-B"));
        mStaffs.add(new Engineer("工程师-C"));
        mStaffs.add(new Manager("经理-B"));
        mStaffs.add(new Engineer("工程师-D"));
    }

    /**
     * 为访问者展示报表
     * @param visitor 公司高层，如CEO、CTO
     */
    public void showReport(Visitor visitor) {
        for (Staff staff : mStaffs) {
            staff.accept(visitor);
        }
    }
}

// 访问者
interface Visitor {

    // 访问工程师类型
    void visit(Engineer engineer);

    // 访问经理类型
    void visit(Manager manager);
}

// CEO访问者
class CEOVisitor implements Visitor {
    @Override
    public void visit(Engineer engineer) {
        System.out.println("工程师: " + engineer.getName() + ", KPI: " + engineer.getKpi());
    }

    @Override
    public void visit(Manager manager) {
        System.out.println("经理: " + manager.getName() + ", KPI: " + manager.getKpi() +
                ", 新产品数量: " + manager.getProducts());
    }
}

// CTO访问者
class CTOVisitor implements Visitor {
    @Override
    public void visit(Engineer engineer) {
        System.out.println("工程师: " + engineer.getName() + ", 代码行数: " + engineer.getCodeLines());
    }

    @Override
    public void visit(Manager manager) {
        System.out.println("经理: " + manager.getName() + ", 产品数量: " + manager.getProducts());
    }
}