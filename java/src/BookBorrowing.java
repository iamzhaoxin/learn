import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class BookBorrowing
{
    public static void main(String[] args) throws IOException {
        Book[] book = new Book[20];
        book[0] = new Book("Principle of Compliers","001",10);
        book[1] = new Book("Probability Theory","210",5);
        book[2] = new Book("English book","348",13);
        book[3] = new Book("Math book","234",19);
        book[4] = new Book("Chinese book","534",11);
        int BookNum = 5;

        Student[] stu = new Student[100];
        stu[0] = new Student("Alex","201804");
        stu[1] = new Student("Green","202011");
        int StuNum = 2;

        Menu menu = new Menu();
        menu.ShowMenu();
        Scanner in = new Scanner(System.in);
        int a = in.nextInt();

        BufferedWriter out = new BufferedWriter(new FileWriter("output.txt"));
        while(a != 0)
        {
            switch (a)
            {
                //借书
                case 1:
                    Scanner input3 = new Scanner(System.in);
                    String stuName;
                    System.out.print("请输入借书人的姓名:");
                    stuName = input3.nextLine();
                    stuName = String.format("%-10s",stuName);

                    int BorrowNum = -1;     //借书人的下标
                    for(int i=0;i<StuNum;i++)
                    {
                        if(stu[i].GetStuName().equals(stuName))
                        {
                            if(stu[i].Getnum() < 5)
                            {
                                BorrowNum = i;
                                break;
                            }
                            else
                            {
                                System.out.println("每人最多只能借五本哦");
                                break;
                            }
                        }
                    }
                    if(BorrowNum == -1)
                        System.out.println("借书人信息错误或已经借了五本书了");
                    else
                    {
                        String bookName;
                        System.out.print("请输入欲借的图书书名:");  bookName = input3.nextLine();
                        bookName = String.format("%-25s",bookName);

                        boolean xx = false;
                        for(int i=0;i<BookNum;i++)
                        {
                            if(book[i].GetBookName().equals(bookName))
                            {
                                xx = true;
                                if(book[i].GetBookNum() == 0)
                                    System.out.println("抱歉，图书已全部借出");
                                else
                                {
                                    book[i].Borrow(stuName);
                                    stu[BorrowNum].Borrow(bookName);
                                }
                                break;
                            }
                        }
                        if(!xx)
                            System.out.println("抱歉，暂无此图书");
                    }
                    break;
                //还书
                case 2:
                    Scanner input4 = new Scanner(System.in);
                    System.out.print("请输入还书人的姓名:");  stuName = input4.nextLine();
                    stuName = String.format("%-10s",stuName);

                    BorrowNum = -1;     //还书人的下标
                    for(int i=0;i<StuNum;i++)
                    {
                        if(stu[i].GetStuName().equals(stuName))
                        {
                            BorrowNum = i;
                            break;
                        }
                    }
                    if(BorrowNum == -1)
                        System.out.println("还书人信息错误");
                    else
                    {
                        String bookName;
                        System.out.print("请输入欲还的图书书名:");  bookName = input4.nextLine();
                        bookName = String.format("%-25s",bookName);

                        boolean flag = false;
                        for(int i=0;i<BookNum;i++)
                        {
                            if(book[i].GetBookName().equals(bookName))
                            {
                                flag = true;
                                book[i].GiveBack(stuName);
                                stu[BorrowNum].GiveBack(bookName);
                                break;
                            }
                        }
                        if(!flag)
                            System.out.println("该学生没有借过此书");
                    }
                    break;
                //显示图书信息
                case 3:
                    out.write("---------------------------------------"+"\n");
                    out.write("书名                     书号      剩余数量     借书学生"+"\n");
                    for(int i=0;i<BookNum;i++)
                    {
                        book[i].ShowBookMessage(out);
                    }
                    out.write("---------------------------------------"+"\n");
                    break;
                //显示学生信息
                case 4:
                    out.write("---------------------------------------"+"\n");
                    out.write("姓名         学号      所借书籍"+"\n");
                    for(int i=0;i<StuNum;i++)
                    {
                        stu[i].ShowStuMessage(out);
                    }
                    out.write("---------------------------------------"+"\n");
                    break;

                //添加图书
                case 5:
                    Scanner input = new Scanner(System.in);
                    String name;
                    System.out.print("请输入添加图书的书名:");  name = input.nextLine();
                    String Id;
                    System.out.print("请输入添加图书的书号:");  Id = input.next();
                    int num;
                    System.out.print("请输入添加图书的数量:");  num = input.nextInt();
                    book[BookNum++] = new Book(name,Id,num);
                    System.out.println("图书信息添加成功");
                    break;

                //添加学生
                case 6:
                    Scanner input2 = new Scanner(System.in);
                    System.out.print("请输入添加学生的姓名:");  name = input2.nextLine();
                    System.out.print("请输入添加学生的学号:");  Id = input2.nextLine();
                    stu[StuNum++] = new Student(name,Id);
                    System.out.println("学生信息添加成功");
                    break;
                default:
                    System.out.println("输入错误，请重新输入");
                    break;
            }

            menu.ShowMenu();
            a = in.nextInt();
        }
        out.close();
    }
}

class Student
{
    private String StuName;                           //学生姓名
    private String StuId;                             //学生学号
    private String book[] = new String[5];           //所借书籍,每人最多借五本
    private int num;

    //方法
    //带参数初始化
    public Student(String name,String Id)
    {
        StuName = String.format("%-10s",name);
        StuId = String.format("%-10s",Id);
        num = 0;
    }
    //无参数初始化
    public Student()
    {
        StuName = null;
        StuId = null;
    }

    public String GetStuName()
    {
        return StuName;
    }
    public String GetStuId()
    {
        return StuId;
    }
    public int Getnum()
    {
        return num;
    }

    //学生借书
    public void Borrow(String BookName)
    {
        book[num] = BookName;
        num++;
        System.out.println("借书成功");
    }

    //学生还书
    public void GiveBack(String BookName)
    {
        for(int i=0;i<num;i++)
        {
            if(book[i].equals(BookName))
            {
                for(int j=i;j<num-1;j++)
                    book[j] = book[j+1];
                break;
            }
        }

        System.out.println("还书成功");
        num--;

    }
    //显示学生信息
    public void ShowStuMessage(BufferedWriter out) throws IOException {
        out.write(StuName+StuId);
        if(num == 0)
            out.write("   null"+"\n");
        else
        {
            for(int i = 0 ; i < num ; i++)
            {
                if(i == 0)
                    out.write("   "+(i+1)+"."+book[i]+"\n");
                else
                    out.write("                       "+(i+1)+"."+book[i]+"\n");
            }
        }
    }
}

class Book
{
    //数据成员
    private String BookName;     //书名
    private String BookId;       //书号
    private int BookNum;         //书籍数量
    private String Stu[] = new String[100];         //借书学生
    private int StuNum;
    //方法
    public Book(String name,String Id,int num)
    {
        BookName = String.format("%-25s",name);
        BookId = String.format("%-10s",Id);
        BookNum = num;
        StuNum = 0;
    }
    public Book()
    {
        BookName = null;
        BookId = null;
        BookNum = 0;
        StuNum = 0;
    }

    public String GetBookName()
    {
        return BookName;
    }

    public String GetBookId()
    {
        return BookId;
    }

    public int GetBookNum()
    {
        return BookNum;
    }

    //借书
    public void Borrow(String stuName)
    {
        Stu[StuNum++] = stuName;
        BookNum -= 1;
    }

    //还书
    public void GiveBack(String stuName)
    {
        for(int i=0;i<StuNum;i++)
        {
            if(Stu[i].equals(stuName))
            {
                for(int j=i;j<StuNum-1;j++)
                    Stu[j] = Stu[j+1];
                StuNum -= 1;
                break;
            }
        }
        BookNum += 1;
    }

    //显示书籍信息
    public void ShowBookMessage(BufferedWriter out) throws IOException {
        out.write(BookName + BookId + BookNum);
        if(StuNum == 0)
            out.write("          null"+"\n");
        for(int i=0;i<StuNum;i++)
        {
            if(i == 0)
                out.write("          "+Stu[i]+"\n");
            else
                out.write("                                              "+Stu[i]+"\n");
        }
    }
}

class Menu
{
    public void ShowMenu()
    {
        System.out.println("-------------------菜单------------------");
        System.out.println("1.借书");
        System.out.println("2.还书");
        System.out.println("3.显示图书信息");
        System.out.println("4.显示学生信息");
        System.out.println("5.添加图书");
        System.out.println("6.添加学生");
        System.out.println("0.退出");
        System.out.println("---------------------------------------");
        System.out.print("请输入选项:");
    }
}