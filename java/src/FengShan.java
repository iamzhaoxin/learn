import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.concurrent.Delayed;

public class FengShan{
    public static void main(String[] args) {
        new FanFrame();
    }
}


//定义FanRunnable类，实现Runnable接口
class FanRunnable implements Runnable{
    private FanPanel panel;
    private static int time = 30;  // 单位帧间隔时间

    public FanRunnable(FanPanel jpanel) { // 需要
        this.panel = jpanel;
    }

    //启动循环动画
    @Override
    public void run() {
        while(true){  // 动画
            panel.f.move();
            panel.repaint();
            try {  // 处理InterruptedException
                Thread.sleep(time);
            } catch (InterruptedException e) {

            }
        }
    }

}

// 风扇的物理属性
class fan{
    public int on = 1; // 是否启动
    public int du = 0;  // 当前角度
    double v = 0; // 瞬时速度
    double max = 10.1; // 最大速度
    double a = 0.03;  // 速度增量比
    public void move(){  // 对风扇每一帧进行处理
        if(on==1){
            v+=((max-v)*a);  //加速
        }else{
            v-=(v*3*a); // 减速
        }
        du+=(int)v; //移动
        du = du %360;
    }

}

// FanPanel 每个电扇的panel
class FanPanel extends JPanel{
    public fan f= new fan() ; // 当前panel的风扇
    JPanel buttonPanel;
    JButton start = new JButton("启动");
    JButton pause = new JButton("暂停");
    int X;
    int Y;
    public FanPanel(){
        super();
        setLayout(null);
    }
    public void after(){  // 设置好大小后 才能初始化
        setBackground(Color.white);
        Rectangle2D bound = this.getBounds();
        X = (int)bound.getWidth();
        Y = (int)bound.getHeight();

        addButton();
        buttonPanel.setSize(145, 40);
        buttonPanel.setLocation((X-145)/2, Y-40);

        add(buttonPanel);
    }
    void addButton(){  // 添加按钮面板
        buttonPanel = new JPanel();

        start = new JButton("启动");
        Action beginAction = new Action(1);
        start.addActionListener(beginAction); // 绑定事件
        buttonPanel.add(start);

        pause = new JButton("停止");
        Action exitAction = new Action(0);
        pause.addActionListener(exitAction);
        buttonPanel.add(pause);
    }
    public class Action implements ActionListener{ // 监听按钮点击
        private int a;
        Action(int tag){
            a = tag;
        }
        public void actionPerformed(ActionEvent event){
            f.on = a;
        }
    }
    public void trun(int a){
        f.on=a;
    }
    public void paintComponent(Graphics g) { // 画图
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        double pi = 3.141592653589;
        double du=f.du*pi/180.0; // 旋转角度

        int [] xs={X/2-X/12,X/2,X/2+X/12,X/2}; //叶片对应坐标
        int [] ys={X/2-X/3,X/2-X/3-X/30,X/2-X/3,4*X/7};
        // 画3片叶片
        g2d.rotate(du,X/2, Y/2);
        g.setColor(Color.BLUE);  // 叶片颜色
        g2d.fillPolygon(xs, ys, xs.length); // 画叶片

        g2d.rotate(2.0/3*pi,X/2, Y/2);
        g.setColor(Color.RED);
        g2d.fillPolygon(xs, ys, xs.length);

        g2d.rotate(2.0/3*pi,X/2, Y/2);
        g.setColor(Color.YELLOW);
        g2d.fillPolygon(xs, ys, xs.length);

        g2d.rotate(2.0/3*pi,X/2, Y/2);

        g2d.rotate(-du,X/2, Y/2);
        g.setColor(Color.GRAY);
        int r =  X/12;
        g2d.fillOval(X/2-r/2, Y/2-r/2,r, r);// 画中心圆

        g.setColor(Color.BLACK);
        g2d.drawString("speed:"+(int)f.v,20,20);
    }
}

//  FanFrame 总界面构造
class FanFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    private FanPanel[] panels=new FanPanel[3]; // fan窗口
    JButton start;
    JButton pause;
    JPanel buttonPanel;

    // 方便debug 提高debug效率
    public static < E > void log( E[] inputArray )
    {// 输出数组元素
        for ( E element : inputArray ){
            System.out.printf( "%s ", element );
        }
        System.out.println();
    }
    public static < T > void log( T input )
    { // 元素
        System.out.println(input);
    }


    public FanFrame() {  // 搭建GUI
        setTitle("Fans");
        setBounds(100,50,900,500);;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        Rectangle2D bound = this.getBounds();
        log(bound.getMaxX());
        int X = (int)bound.getWidth();
        int Y = (int)bound.getHeight();

        int count = 0;
        for(FanPanel panel: panels){ // 显示每一个子窗口
            panel = new FanPanel( );
            panels[count] = panel;
            panel.setSize((int)(X/3*0.98),Y*3/4);
            panel.setLocation(count*X/3,0);
            panel.after();
            add(panel);
            new Thread(new FanRunnable(panel)).start();
            count++;
        }


        getButtonPanel();

        buttonPanel.setSize(145, 40);
        buttonPanel.setLocation((X-145)/2, Y-2*40);
        add(buttonPanel);

        setVisible(true);
    }
    public void getButtonPanel(){ // 布置按钮
        buttonPanel = new JPanel();
        start = new JButton("启动");
        Action beginAction = new Action(1);
        start.addActionListener(beginAction); // 绑定事件
        buttonPanel.add(start);

        pause = new JButton("停止");
        Action exitAction = new Action(0);
        pause.addActionListener(exitAction);
        buttonPanel.add(pause);
    }
    public class Action implements ActionListener{ // 监听按钮事件
        private int a;
        Action(int tag){
            a = tag;
        }

        public void actionPerformed(ActionEvent event){
            for(FanPanel panel: panels){
                panel.trun(a);
            }

        }
    }

}
