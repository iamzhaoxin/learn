import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;
import java.awt.Font;


// TODO 未完成

public class PinTu extends Frame implements Runnable{
    Font messageFont1,messageFont2;
    String message1,message2="congratulations!",message3="恭喜您已完成！",message4="Powered by Dustin",message5="2014-11-20";
    int temp1D=10,temp2D=10;
    static int the1D=2,the2D=2;                 //记录空位置
    int x0=0,y0=0,x1=200,y1=0,x2=400,y2=0,x3=0,y3=200,x4=200,y4=200;
    int x5=400,y5=200,x6=0,y6=400,x7=200,y7=400,x8=400,y8=400;
    Image[] img = new Image[9];
    Image yuantu;
    Image bufferPage=null;                      //缓冲页，用于消除闪烁现象
    int[] xulie = {10,10,10,10,10,10,10,10,10};
    private static Random r = new Random();
    public static void main (String args[]){
        PinTu workstart = new PinTu();
        workstart.getAXulie();
        System.out.print("you get a array : ");
        for(int i=0;i<9;i++){
            System.out.print(workstart.xulie[i]+" ");
        }
        if(workstart.judgeNiXuShu()%2==0){
            System.out.print(" try your best to finish it!");
        }
        else
            System.out.print("this problem is No solution!press start again to get a new one!");
    }
    public void getAXulie(){
        //数组的无序不重复填充
        int temp;
        int j=0;
        while(j<8){
            temp = r.nextInt(8);
            if((temp!=xulie[0])&&(temp!=xulie[1])&&(temp!=xulie[2])&&(temp!=xulie[3])&&(temp!=xulie[4])&&(temp!=xulie[5])&&(temp!=xulie[6])){
                this.xulie[j++] = temp;
            }
        }
        xulie[j]=8;
    }
    public int judgeNiXuShu(){
        int nixushu=0;
        for(int i=7;i>=0;i--){
            for(int j=0;j<i;j++){
                if(xulie[j]>xulie[i]){
                    nixushu++;
                }
            }
        }
        return nixushu;
    }
    public PinTu(){
        super("PGame--GHTao");
        setSize(1000,640);
        Toolkit tk = Toolkit.getDefaultToolkit();
        enableEvents(AWTEvent.KEY_EVENT_MASK);
        for(int i = 0;i<9;i++){
            img[i] = tk.getImage("图"+i+".jpg");
        }
        yuantu = tk.getImage("原图.jpg");
        setVisible(true);
        addWindowListener(new WindowAdapter(){
            //加入窗口关闭
            public void windowClosing(WindowEvent evt){
                System.exit(0);
            }
        });
        new Thread(this).start();
    }
    public void processKeyEvent(KeyEvent e){
        if(e.getID()==KeyEvent.KEY_PRESSED){
            switch(e.getKeyCode()){
                case KeyEvent.VK_UP:
                    if(the1D!=2){
                        temp1D=xulie[the1D*3+the2D];
                        xulie[the1D*3+the2D]=xulie[the1D*3+the2D+3];
                        xulie[the1D*3+the2D+3]= temp1D;
                        the1D++;
                        temp1D=10;
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(the1D!=0){
                        temp1D=xulie[the1D*3+the2D];
                        xulie[the1D*3+the2D]=xulie[the1D*3+the2D-3];
                        xulie[the1D*3+the2D-3]= temp1D;
                        the1D--;
                        temp1D=10;
                    }
                    break;
                case KeyEvent.VK_LEFT:
                    if(the2D!=2){
                        temp2D=xulie[the1D*3+the2D];
                        xulie[the1D*3+the2D]=xulie[the1D*3+the2D+1];
                        xulie[the1D*3+the2D+1]= temp2D;
                        the2D++;
                        temp2D=10;
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(the2D!=0){
                        temp2D=xulie[the1D*3+the2D];
                        xulie[the1D*3+the2D]=xulie[the1D*3+the2D-1];
                        xulie[the1D*3+the2D-1]= temp2D;
                        the2D--;
                        temp2D=10;
                    }
            }
            //end switch
        }
        //end if
    }
    public void run(){
        message1 = "目标图像";
        while(true){
            repaint();
            if((xulie[0]==0)&&(xulie[1]==1)&&(xulie[2]==2)&&(xulie[3]==3)&&(xulie[4]==4)&&(xulie[5]==5)&&(xulie[6]==6)&&(xulie[7]==7)&&(xulie[8]==8)){
                break;
            }
            try{Thread.sleep(200);
            }catch(InterruptedException e){;}
        }
    }
    public void update(Graphics g){
        paint(g);
    }
    public void paint(Graphics g){
        Graphics bufferg;
        if(bufferPage==null)
            bufferPage = createImage(1000,640);
        bufferg = bufferPage.getGraphics();
        bufferg.drawImage(img[xulie[0]],x0,y0,this);
        bufferg.drawImage(img[xulie[1]],x1,y1,this);
        bufferg.drawImage(img[xulie[2]],x2,y2,this);
        bufferg.drawImage(img[xulie[3]],x3,y3,this);
        bufferg.drawImage(img[xulie[4]],x4,y4,this);
        bufferg.drawImage(img[xulie[5]],x5,y5,this);
        bufferg.drawImage(img[xulie[6]],x6,y6,this);
        bufferg.drawImage(img[xulie[7]],x7,y7,this);
        bufferg.drawImage(img[xulie[8]],x8,y8,this);
        bufferg.drawImage(yuantu,680,50,this);
        messageFont1 = new Font("宋体",Font.PLAIN,30);
        messageFont2 = new Font("宋体",Font.PLAIN,25);
        bufferg.setColor(Color.blue);
        bufferg.setFont(messageFont1);
        bufferg.drawString(message1, 740, 35);
        if((xulie[0]==0)&&(xulie[1]==1)&&(xulie[2]==2)&&(xulie[3]==3)&&(xulie[4]==4)&&(xulie[5]==5)&&(xulie[6]==6)&&(xulie[7]==7)&&(xulie[8]==8)){
            bufferg.drawString(message2, 710, 400);
            bufferg.drawString(message3,750,450);
            bufferg.setFont(messageFont2);
            bufferg.drawString(message4,720,550);
            bufferg.drawString(message5,800,590);
        }		bufferg.dispose();
        g.drawImage(bufferPage,getInsets().left,getInsets().top,this);
    }


}

