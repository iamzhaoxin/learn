import java.util.*;
public class Test
{
    public static void main(String [] args)
    {
        //使用初始数据进行初始化
        ArrayList<String> list=new ArrayList<String>(Arrays.asList("a0","a1","a2","a3","a4"));
        System.out.println("   init:"+list);
        list.add("end");//添加
        list.add("end");//可添加相同元素
        System.out.println("    add:"+list);
        list.remove(4);//删除
        System.out.println("delete4:"+list);
        list.set(3,"a6");//赋值
        System.out.println("  set 3:"+list);
        String temp=list.get(3);//取值
        System.out.println("位置为3的元素:"+temp);
        int loc=list.indexOf("a6");//查找
        System.out.println("a6的位置:"+loc);
    }
}
//动态数组，引用数据的方式与静态数组不同，静态数据的效率非常高。vector用法与此相似
/*//数组中可以保存对象
      String [] array=new String[5];
      for(int i=0;i<array.length;i++)
         array[i]=""+i;
      System.out.println(Arrays.toString(array));

      ArrayList<String> list=new ArrayList<String>();
      for(int i=0;i<5;i++)
         list.add(""+i);
      System.out.println(Arrays.toString(list.toArray()));

      //数组中可以保存基本类型数据
      int[] array2={0,1,2,3,4,5};
      System.out.println(Arrays.toString(array2));*/
//结果：
//        init:[a0, a1, a2, a3, a4]
//        add:[a0, a1, a2, a3, a4, end, end]
//        delete4:[a0, a1, a2, a3, end, end]
//        set 3:[a0, a1, a2, a6, end, end]
//        位置为3的元素:a6
//        a6的位置:3