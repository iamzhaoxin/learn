# 补充知识

## 命令行常用指令

```bash
#切换到某盘符
D:
#查看当前路径下文件信息
dir
#进入目录
cd learn
cd D:\learn\JavaSE
cd .. 	#回退到上一级目录
cd \	#回退到盘符根目录
#清屏
cls
```

***文件夹视图中，在地址栏直接输入cmd，可以进入当前路径下的cmd窗口***



 ## 代码->运行

编写代码->**HelloWord.java**->使用javac编译->**HelloWorld.class**->使用java运行

代码文件名**全英文，首字母大写，驼峰模式**

**文件名必须与代码中类名一致**

jdk11开始，可以直接**用java执行源代码文件**

## JDK组成

**JDK**（Java Development Kit）：Java开发工具包

- 开发工具：java、javac、……

- **JRE**（Java Runtime Environment）：Java运行环境
  - 核心类库(**API**)：Java内置程序，由程序员调用
  - **JVM**（Java Virtual Machine）：Java虚拟机

> 新版本JDK只自动配置了PATH环境变量，但不配置JAVA_HOME

## IDEA

| 快捷键                |                            |
| --------------------- | -------------------------- |
| main/psvm、sout、……   | 代码简写                   |
| Ctrl+D                | 复制当前行到下一行         |
| Ctrl+Y                | 删除所在行（Ctrl+X更顺手） |
| Ctrl+Alt+L            | 格式化                     |
| Alt+Shift+↑ / ↓       | 上下移动当前代码           |
| Ctrl+ / ,Ctrl+Shift+/ | 注释                       |

**从IDEA中删除model**

1. 先从IDEA项目中remove（自动从.idea/moudle.xml文件中删除相应信息）
2. 从文件夹中delete

# 基础语法

## 注释

```java
/**
 * 可以被JavaDoc工具软件识别的标签：
 * @author zhaoxin
 * @docRoot path
 * @version 1.0
 */
public class test {
    public static void main(String[] args) {
        //单行注释

       /*
       多行注释
       多行注释
        */
    }
}
```

## 标识符

- 组成：**数字、字母、下划线、美元符**
- **不能数字开头**，不能是关键字，区分大小写

### 命名

- **变量：** 首字母**小写**，驼峰
- **类：** 首字母**大写**，驼峰

## 数据类型

- Java中支持 **二进制、八进制、十六进制** 数据，分别需要以 **0B、0、0X或0x** 开头

| 数据类型 | 关键字           | 取值范围               | 内存占用(字节) |
| -------- | ---------------- | ---------------------- | -------------- |
| 整数     | byte             | (-2^7) ~ (2^7-1)       | 1              |
|          | short            | (-2^15) ~ (2^15-1)     | 2              |
|          | **int(默认)**    | **(-2^31) ~ (2^31-1)** | **4**          |
|          | long             | (-2^63) ~ (2^63-1)     | 8              |
| 浮点数   | float            |                        | 4              |
|          | **double(默认)** |                        | **8**          |
| 字符     | char             |                        | 2              |
| 布尔     | boolean          |                        | 1              |

### 类型转换

```Java
//因为整数默认是int，所以long类型后面要加“L"强制转换，float后面加”F“
long c=999999999999L,d=99999999999999l;
float a=9.5F,b=9.5f;
```

- 小范围数据类型自动转化为大范围数据类型
- **最终类型由表达式中最高类型决定**
- **byte、short、char以int类型参与运算**

#### 强制转换

```java
int b;
byte a=(byte)b;
```

- 小数转换成整数 是 截断小数保留整数
- 涉及<font color="red">原码、补码、反码</font>

## 运算符

### "+"

- 数学运算

- 连接符：能算就算，不能算就连接

  ```java
  int a=5;
  System.out.println(a+'a');//102
  System.out.println(a+"a");//5a
  ```

### "/"

- Java中两个整数相除，结果还是整数

  ```java
  (double)10/3=3.333333
      
  (int)10*1.0/(int)3=3.333333
  (int)10/(int)3*1.0=3
  ```

### ==

- 基本数据类型，比较的是他们的值
- 引用数据类型，比较的是在内存中的存放地址（堆内存的地址）
  - 每new一次，会重新开辟堆内存空间
  - `equals()`方法，默认比较地址值，一般默认重写，比较对象的变量值

### 拓展赋值运算符

- +=、-=、*=、/=、%=

- 隐含了强制类型转换

  ​	a+=b 等价于 a=(a的数据类型)(a+b)

### 逻辑运算符

& | ！ ^

- **&、|**    无论左边false还是true，**右边都执行**
- **^  逻辑异或，两个条件相同为false，两个条件不同为true**

#### 短路逻辑运算符

- &&    左边为false，**右边不执行**
- ||     左边true，**右边不执行**

### 优先级

- () 优先级最高
- */ 高于 +-
- && 高于 ||

## 流程控制

### switch

```java
string a="aaa";
switch(a){	//switch(表达式)
    case "bbb":
        System.out.println("ok");
        break;
    case "aaa":
        System.out.println("ok");
        break;
    case "ccc":
        System.out.println("ok");
        break;
    default:
        System.out.println("ok");
}
```

- 区别

  - if 适合 **区间匹配**，功能强大

  - switch 适合 **值匹配的分支选择**，格式清晰，**性能好**

- **表达式**只能是 byte、short、int、char、枚举、String，**不支持double、float、long** 。（因为浮点数 底层 不是精确的）

- **case的值不能重复，不能是变量**

- 如果不写break，会执行到底（后面的case不执行匹配，直接执行，直到break）

## 数组

### 静态数组

```java
//静态初始化 完整格式: 数据类型[] 数组名 = new 数据类型[]{元素1, 元素2, 元素3}
double[] a = new double[]{89.9, 99.5, 88};
//静态初始化 简化格式: 数据类型[] 数组名 = {元素1, 元素2, 元素3}
double[] a = {89.9, 99.5, 88};
//静态初始化 也可以写成：数据类型 数组名[]
double a[] = {89.9, 99.5, 88};

//动态初始化: 数据类型[] 数组名 = new 数据类型[数组长度]
double[] a = new double[3];
```

- 数组变量名 存储的是 数组在内存中的地址信息，所以是“**引用类型**”

- 数组定义后，程序执行过程中，**长度、类型固定**

- 静态初始化 和 动态初始化 **不能混用**

  ​	错误：`double[] a = new double[3]{89.9, 99.5, 88};`

- 动态初始化的**默认值**

  - 整型数组：0
  - **字符数组：0**
  - 浮点数组：0.0
  - 布尔数组：false
  - 引用数组：null

### 内存

**Java内存分配：**

- 程序计数器
  - 较小的内存，当前线程所执行的字节码的**行号指示器**。
  - 每个线程都有**独立的**程序计数器，且互不影响。

- **Java虚拟机栈**
  - 线程私有，生命周期与线程相同。
  - 描述Java方法执行的内存模型：每个方法运行时创建一个栈帧用于存储**局部变量、操作数、操作数栈、动态链接、方法出口**等信息。
  - 每个方法的调用过程直至执行完成的过程，对应一个栈帧在虚拟机栈中入栈到出栈的过程。
  - 如果线程请求的栈深度大于虚拟机允许的深度，抛出`StackOverFlowError`异常；如果虚拟机可动态扩展，扩展时无法申请到足够内存，抛出`OutOfMemoryError`异常。

- **Java堆(Java Heap)**
  - 内存最大的一块，被**所有线程共享**的内存区域，唯一目的是存放**对象实例**。
  - Java堆是垃圾收集器管理的主要区域，也叫"GC堆"。

- **方法区**
  - 和Java堆一样也是**各个线程共享**的内存区域，用于存储已被虚拟机加载的**类信息、常量、静态变量、即时编译后的代码**等数据。使用`永久代`实现方法区
- 本地方法栈
  - 线程私有
  - 虚拟机栈 为 虚拟机执行Java方法（字节码）服务，本地方法栈为虚拟机使用到的Native方法服务。


## 方法

- **方法中不可以再次声明方法，只能调用其他的方法**
  - 如果出现方法里面嵌套方法，那只有一种情况，那就是**方法里面定义了内部类**，里面的方法属于内部类中的方法
- Java传参：**值传递**
  - 基本类型：传递实参的数据值
  - 引用类型：（String、array、…）传递实参的**地址值**，地址中存储的值可以改变

### 方法重载

- 同一个类中，方法名相同，形参列表不同

## 面向对象

- Java中**不需要`import`就能引用的类**
  - `java.lang（唯一的基础类）`下面的包
  - 同一个包
- 类名 **首字母大写**
- 一个Java文件中可以有多个class类，**只能有一个类是public**，public类的**类名必须是文件名**
  - 建议一个文件只定义一个class

### 内存

> 见“六、数组 -> 内存”

```java
Student s1 = new Student();
Student s2 = s1;
//s2指向的是s1的内存空间，即s1指向的地址被赋值给s2，而不是一个新的对象（类似String、Array）
```

#### 垃圾回收

- Java存在自动垃圾回收器
  - 堆内存中的**类对象**或**数组对象**，没有被任何变量引用时，被判定为内存中的垃圾

### 构造器

- 初始化类的对象，并返回对象的地址

- 格式

  ```java
  public class Car(){
      //无参数构造器(默认存在，成员变量数据采用默认值)
      public car(){	}
      //有参数构造器
      public car(String s,int n){		}
  }
  ```

- 一旦**定义了有参数构造器，默认的无参数构造器就没有了**，如果需要，要自己写一个无参构造器

### this关键字

- 在 成员方法、构造器中代表当前对象的地址，用于访问当前对象的成员变量、成员方法。

### 封装

- 面向对象的三大特征：**封装、继承、多态**
- 步骤
  - 对成员变量使用private修饰
  - 提供public的get、set方法访问成员变量的值

### 标准JavaBean

- 规范标准

  - 成员变量私有

  - 每个成员变量提供setXxx()  / getXxx()

    > IDEA中，右键Generate->Getter and Setter

  - 提供一个无参构造器

### 常用API

#### String

- String：不可变字符串类型，**对象在创建后不可修改**

  - 修改String时，会指向新产生的对象（在堆内存）

- 创建字符串对象

  - `String name = "zhaoxin";`

    - 用 "" 创建的字符串对象，**存储在字符串常量池中，相同内容只存储一份**

  - 通过String类的构造器

    ```java
    String s1 = new String();//（几乎不用）
        
    String s2 = new String("zhaoxin");//（几乎不用）
    
    char[] chars = {'a', 'b', 'c'};
    String s3 = new String(chars);
    
    byte[] bytes = {97, 98, 66, 67};
    String s4 = new String(bytes);
    ```

    - 通过构造器new对象，每次产生**新对象**，存储在**堆内存**中

  - 举例

    ```java
    //1
    String s1 = new String("abc");//创建了2个对象，一个是字符串常量池里的”abc“，另一个是堆内存中的”abc“(被s2指向)。
    String s2 = "abc";//创建了0个对象，直接指向字符串常量池里的“abc”
    //2
    String s1 = "abc";
    String s2 = "ab";
    String s3 = s2 + "c";
    System.out.println(s1==s3);//false，s1在字符串常量池，s3在堆内存
    //3
    String s1 = "abc";
    String s3 = "a" + "b" + "c";
    System.out.println(s1==s3);//true，因为s3由三个常量组成，程序编译时，直接转化为"abc"
    ```

- 常用API

  ```java
  String s;
  int a = s.length();
  char b = s.charAt(0);
  char[] chars = s.toCharArray();//字符串转换为字符数组
  String c = s.substring(0,5);//截取内容，左闭右开
  String d = s.substring(3);//从当前索引截取到末尾
  String e = s.replace("被替换","替换");
  boolean f = s.contains("xxx");
  boolean g = s.startsWith("xxx");
  String[] ss = s.split(',');//分割成字符串数组
  ```

#### ArrayList

> 集合类型可以不固定，大小可变。
>
> 集合对象的**变量名** 存储的是 在堆内存中的**集合对象的首地址**，**集合对象中每个元素**存的是**每个对象在堆内存中的地址**

- 创建/添加

  ```java
  ArrayList list = new ArrayList();
  list.add("abc");
  list.add(23);
  list.add(2,'c');
  ```

- 泛型：统一ArrayList集合操作的元素类型

  `ArrayList<Integer> list2 = new ArrayList<>();`

- 常用API

  ```java
  ArrayList<String> list = new ArrayList<>();
  String a = list.get(3);//获取索引位置元素的值
  int b = list.size();
  String c = list.remove(3);//删除索引位置的元素值，并返回被删除的元素值
  boolean d = list.remove("xxx");//删除某元素值，删除成功返回true
  String f = list.set(0,"xxx");//修改某位置的值，返回修改前的值
  ```

- 正向遍历时，如果用集合的remove方法删除a[i]，可能导致a[i+1]变成a[i]没有被遍历到
  - 存在该问题的情况：
    - 迭代器遍历集合，用集合的方法删除元素
    - 增强for循环遍历集合，用集合删除元素
  - 安全方法：
    - 用迭代器遍历集合，用迭代器的的删除方法`it.remove()`
    - for循环 反向遍历或删除元素后`i--`

# JavaSE加强

## 面向对象进阶

### static

#### 静态修饰

- 修饰成员变量

  - **静态成员变量**：**有static**修饰，属于类，在内存中**只存储一份**，可以被**共享访问、修改**
    - **类名.静态成员变量** -> 推荐
    - 对象名.静态成员变量 -> 不推荐
  - 实例成员变量：属于对象，只能用对象触发访问
- 修饰成员方法

  - 静态成员方法：有static修饰，不依赖具体实例，属于类，建议用类名访问，也可以用对象名访问
    - 可以访问静态成员
    - 不能访问对象：
      - 静态方法中不能出现this关键字
      - **静态方法无法直接访问任何实例成员**
        - 可以将实例作为参数传入，或静态方法中创建对象然后访问实例成员
  - 实例成员方法：属于对象，只能用对象触发访问
    - 要直接访问实例成员的，只能是实例成员方法
- 工具类

  - **内部都是静态方法**，每个方法完成一个功能
  - 提高代码重用性
  - 工具类不需要创建对象，可直接调用静态方法
  - 可以将工具类的构造器私有化，使其无法创建对象（浪费内存）

#### 代码块

定义在类中方法外

##### 静态代码块

- **static**{}
- 随着类的加载而加载，自动触发，只执行一次
- 可用于类加载时，对静态数据初始化

##### 构造代码块

- {}
- 每次创建对象调用构造器时执行，在构造器之前执行
- 用于初始化实例（等效于构造器）

#### 单例模式

- 应用这个类创造的对象只有一个

##### 饿汉单例设计模式

- 用类获取对象时，**对象已经被提前创建好**
- 步骤
  1. 定义一个类，将构造器私有（单例必须私有构造器）
  2. 定义一个静态变量存储一个对象（`public static classname name = new classname ();`）

##### 懒汉单例模式

- 需要对象的时候，再去创建对象（延迟加载）

- 步骤

  1. 定义类，构造器私有

  2. 定义静态变量存储对象

  3. 提供返回单例对象的方法

     ```java
     public class SingleInstance{
         private static SingleInstance instance;//私有，避免误访问
         private SingleInstance(){}
         public static SingleInstance getInstance(){
             if(name == null){
                 name = new classname ();
             }    
             return name;
         }
     }
     ```

### 继承

- 通过**super**关键字来实现对父类成员的访问，用来引用当前对象的父类。
- **final** ，表示“**最终**”可以修饰 类、方法、变量
  - 可以把类定义为不能继承的，即最终类
  - 修饰方法，该方法不能被子类重写
    - **模板方法最好用final修饰，避免被子类重写**
    - 被声明为 final 的类，它的方法自动地声明为 final，而不包括域和变量
  - 修饰变量，变量被赋初始值后，不能再被赋值
    - 修饰基本类型，值不能改变。修饰引用类型，地址不能变，地址里存储的值可以改变。



#### 重写

- “**@Override**”重写注解
  - 加在重写后的方法前，如果重写错误，编译时会报错，代码可读性更高
- 重写方法的 **名称、形参列表** 必须与被重写方法一致
  - 子类重写方法的**返回类型**必须与父类**相同**，或是父类方法的返回类型的**子类型**
- 私有方法不能被重写
- 子类重写父类方法时，**访问权限要大于等于父类**（否则会影响多态）
  - **访问权限由高到低：public、protected、包访问权限、private**。
- **不能重写静态方法**

#### 构造器

- 子类初始化之前，先自动调用父类无参构造器

- **子类不继承父类的构造器**，子类的构造器中可以**通过 super 关键字调用父类的构造器**
- **"this调用本类其他构造器"和"super调用父类构造器"都只能放在构造器第一行**，所以不能同时使用

### 包

- 包 的语法格式：公司域名倒写.技术名称
- 导包
  - 相同包下的类可以直接访问，不同包下的类要导包：`import 包名.类名`
  - 如果要用到两个类名相同的类，默认只能导入一个包，另一个类带包名访问

### 权限修饰符

| 可访问性                                   | 同一个类 | 同一个包下其他类 | 不同包的子类 | 不同包的无关类 |
| ------------------------------------------ | -------- | ---------------- | ------------ | -------------- |
| private 私有权限                           | √        |                  |              |                |
| default(**package-private**) 缺省 同包权限 | √        | √                |              |                |
| **protected** 受保护的权限                 | √        | √                | **√**        |                |
| public 公共权限                            | √        | √                | √            | √              |

- **protected 不能修饰类**
- **private可以修饰类，但只能修饰内部类**

### 常量

- `public static final 数据类型 变量名 = xxx;`（必须有初始化值）
- 命名：英文字母全部大写、多个单词用下划线连接
- 编译阶段 进行 宏替换，把使用常量的地方全部替换为字面
- 可以用常量作为信号量，增加可读性`public static final int UP = 1;` 
  - 相比于枚举，常量的入参值不受约束，代码不够严谨，不好不好

### 枚举

- 作用：信息的标志和信息的分类

- 格式：`修饰符 enum 枚举名称{罗列枚举类实例的名称;}`

  ```java
  public enum Season{
      SPRING, SUMMER, AUTUMN, WINTER;
  }
  ```

### 抽象类

- 减少代码重复

- **抽象类不能创建对象**（避免实例对象调用抽象方法）

- 没有方法体，只有方法签名，用`abstract`修饰。（抽象方法交由子类重写实现）

  ```java
  public abstract class Animal{
      private String name;
      public abstract void run(); //抽象方法
      public void setName(String name){this.name=name;}
      public String getName(){return name;}
  }
  ```

- 不能用`abstract`修饰 变量、代码块、构造器

- **抽象类不必须有抽象方法**，有抽象方法的类必须修饰为抽象类

- 继承抽象类的子类，必须**重写抽象类的全部抽象方法**，否则也要定义为抽象类

- abstract 和 final 是互斥关系

### 接口

- 更彻底的抽象，体现**规范**

- 接口不是类，无法实例化，但可以被**实现`(implements)`**。

  - 实现接口的类，必须实现接口描述的所有方法，否则要声明为抽象类

    ```java
    修饰符 class 实现类 implements 接口1, 接口2, 接口3, ……{
    }
    ```

  - 类和类：单继承

    - 一个类继承了父类，同时实现了接口，父类和接口中有同名方法，默认用父类的

  - 类和接口：**多实现**

    - 一个类实现多个接口，多个接口中有同样的静态方法不冲突（因为接口的静态方法由接口名调用）

  - 接口和接口：**多继承**（一个接口可以同时继承多个接口，因为即使有多个重复的方法，在实现时也只会重写成一个方法（规范合并））

    - 当 不同接口 的 相同方法名 的 返回类型 不同时，无法被多继承。（有其他规范冲突也不能多继承）

      ```java
      public interface Law{
          String eat();
      }
      public interface People{
          void eat();
      }
      public interface Man extends Law, People{	//冲突
      }
      ```

- 格式

  ```java
  public interface 接口名{
      //常量
      //抽象方法
  }
  ```

  - 接口中的方法会被隐式的指定为 **public abstract**
  - 接口中的变量会被隐式的指定为 **public static final** 变量

#### JDK8

JDK8以后，接口中的方法可以有方法体

- 默认方法（实例模式）：必须用default修饰，默认为public。
  - 接口不能创建对象，方法过继给实现类，由实现类的对象调用
- 静态方法：必须用static修饰，默认为public。
  - 接口的静态方法，必须接口名自己调用（`接口名.静态方法名`）
- 私有方法（实例方法）：JDK1.9开始支持，private修饰
  - 只能在接口内部访问

### 多态

- 多态的前提：

  - 有继承/实现关系
  - 有父类引用指向子类对象
  - 有方法重写

- 多态：**同类型**的对象，执行同一个行为，表现不同的行为

- 多态中，成员访问特点：

  - **方法调用**：编译看左边，**运行看右边**（运行右边子类的方法）
  - **变量调用**：编译看左边，**运行看左边**（运行左边父类的变量）（**多态侧重行为多态**）

- 常见形式

  - 父类类型 对象名称 = new 子类构造器；
  - 接口 对象名称 = 实现类构造器；

  ```java
  public abstract class Animal{
      public String name="父类动物";
      public abstract void run();
  }
  public class Dog extends Animal{
      public String name="子类狗";
      
      @Override
      public void run(){
          System.out.println("狗在跑");
      }
  }
  public class Tortoise extends Animal{
      public String name="子类乌龟";
      
      @Override
      public void run(){
          System.out.println("乌龟在跑");
      }
  }
  
  public class Test{
      public static void main(String[] args){
          //多态的形式：父类类型 对象名称 = new 子类构造器;
          Animal a = new Dog();
          a.run();//"狗在跑"
          System.out.println(a.name);	//"父类动物"
      }
  }
  ```

- 多态的优势

  - 多态形式下，右边对象可以解耦合，便于扩展维护。（对象改变时，后续代码无需改变）
  - 定义方法时，**使用父类型作为参数**，该**方法可以接收该父类的一切子对象**

- 劣势

  - 多态下，不能使用子类独有功能

#### 多态类型转换

- 子类 -> 父类：自动

- 父类转换为子类：强制

  - `子类 对象变量 = (子类)父类类型变量`

  - 可以解决多态的劣势，实现调用子类独有功能

  - 如果转型后的类型，与对象的真实类型 不是同一种类型，编译时不报错，运行时报错`ClassCastException`

    - 可以用`instanceof`判断，判断关键字左边的变量指向的对象的真实类型，如果是右边的类型则返回true

      ```java
      if(a instanceof Dog){Dog d = (Dog)a;……}else if(a instanceof Tortoise){Tortoise t = (Tortoise)a;……}
      ```

### 内部类

一个类定义在另一个类中，称为嵌套类（内部类）（外部类只能声明为public或缺省包私有）

- 用static声明：静态内部类
  - 不需要创建外部类来访问，可直接访问
  - **静态内部类无法访问外部类的非static成员**
- 没有用static声明：非静态内部类（成员内部类），属于外部类的对象
  - 可声明为 private、public、protected 或package-private。
  - 内部类可以无条件地访问外部类的成员，包括private和静态成员
  - 创建成员内部类对象的方式：`外部类名.内部类名 对象名 = new 外部类构造器().new内部类构造器();`
  - 成员内部类中访问所在外部类成员，格式：`外部类名.this.成员名`
- 局部内部类（了解）
  - 和成员内部类的区别在于局部内部类的访问仅限于方法内或者该作用域内
  - 局部内部类就像是方法里面的一个局部变量一样，是不能有public、protected、private以及static修饰符的
- 匿名内部类（重要)

#### 匿名内部类

```java
public abstract class Animal{
    public abstract void run();
}
public class Dog extends Animal{
    @Override
    public void run(){
        System.out.println("狗在跑");
    }
}
public class Test{
    public static void main(String[] args){
        Animal a = new Dog();
        a.run();//"狗在跑"
    }
}
//===========用匿名内部类简化代码：===========
public abstract class Animal{
    public abstract void run();
}
public class Test{
    public static void main(String[] args){
        Animal a = new Animal(){
            @Override
            public void run(){
                System.out.prinln("狗在跑");
            }
        };
        a.run();//"狗在跑"
    }
}
```

- 本质是个没有名字的局部内部类，方便创建子类对象，**简化代码**
- 格式`new 类名|抽象类名|接口名(){重写方法};`
- 匿名内部类写出来，就会产生一个匿名内部类对象，对象的类型是new的类型的子类型

### 可变参数

- 在形参中可以接收多个数据

- 格式`数据类型...数据名称`

  ```java
  sum();
  sum(1,2,4);
  public static void sum(int...nums){
      System.oout.println(nums.length());
  }
  ```

- 可变参数在方法内部本质上是一个**数组**

- 注意事项

  - 一个形参列表中只能有一个可变参数
  - 可变参数必须放形参列表最后面

## 常用API

### Object类

#### toString方法

- 默认为返回当前对象在堆内存中的地址信息（毫无意义）
  - 直接输出对象变量，默认可以省略`toString`，也是输出地址信息

- 用于被子类重写，以便返回对象的内容信息
  - IDEA中输出`tos`自动生成


#### equals

- 默认比较两个对象的地址是否相同，等效于`==`

- 用于被子类重写，比较两个对象的内容

  ```java
  public class Student(){
      //……
      @Override
      public boolean equals(Object o){
      if(this==o)	return true;	//自己和自己比
      if(o==null || this.getClass() != o.getClass())	return false;
      Student s2 = (Student) o;
      return this.name.equals(s2.name))&&this.age==s2.age&&this.sex==s2.sex;
      }
  }
  ```

  - IDEA中同样可以自动生成重写

### Objects

- Object的子类(Java1.7之后)

- Objects的equals比Object的equals**更安全**，更准确

  ```java
  String s1=null;
  String s2="aaa";
  System.out.println(s1.equals(s2));//空指针异常
  System.out.println(Objects.equals(s1,s2));//"false"
  ```

### StringBuilder

- 内容可变，拼接字符性能比String好

#### 常用方法

```java
StringBuilder s=new StringBuilder();
//支持链式编程
s.append('a').append("aa").append(false).append(21);
//反转
s.reverse();
//长度
int a=s.length();
//恢复成String
String rs=s.toString();
```

### Math

```java
int abs(int a);//取绝对值
double ceil(double a);//向上取整
double floor(double a);//向下取整
int round(float a);//四舍五入
int max(int a,int b);
double pow(double a,double b);//a的b次幂
double random();//返回[0.0,1.0)的随机数（不同于Random类）
```

### System

```java
exit(0);//终止当前运行的Java虚拟机，非零表示异常终止
long currentTimeMillis();//返回当前系统时间的毫秒值
void arraycopy(数据源数组，起始索引，目的地数组，起始索引，拷贝个数);//数组拷贝
```

### BigDecimal

- 大数据类型，用于解决浮点数失真

- **禁止使用构造方法`BigDecimal(double)`的方式把double值转化为BigDecimal对象**，因为仍存在精度损失的风险

- 优先推荐入参为String的构造方法，或**使用BigDecimal的valueOf方法**。

  ```java
  BigDecimal a = new BigDecimal("0.1");
  BigDecimal b = BigDecimal.valueOf(0.1);
  BigDecimal c=a.add(b);//加
  c=a.substract(b);//减
  c=a.multiply(b);//乘
  c=a.divide(b);//除，无法整除的时候需要指定保留小数位和舍入模式
  double d=c.doubleValue();
  ```

### Date

```java
Date date = new Date();//获取当前时间
Long time = date.getTime();//获取当前时间的毫秒值
Date d = new Date(time);//把时间毫秒值time转化为时间
d.setTime(time);//设置时间为时间毫秒值time的时间
```

#### SimpleDateFormat

- 转换时间格式

```java
Date d = new Date();
SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss EEE a");//EEE:周几  a:上午/下午
String rs = sdf.format(d);
rs=sdf.format(d,getTime());//格式化时间毫秒值
```

- 解析字符串时间

```java
String dateStr = "2022年01月03日 09:42:00";
SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");//形式必须与被解析时间的格式完全一样，否则解析报错
Date d = sdf.parse(dateStr);
long time = d.getTime()+(2L*24*60*60 + 14*60*60 + 49*60 +6)*1000;//往后走2天14小时49分06秒（2L是将数据变为long类型，时间毫秒值可能过大）
String s = sdf.format(time);
```



> 略：
>
> Calendar 日历类

#### JDK8新增的日期API更方便灵活

LoadTIme、LocalDate、LocalDateTime 都是**不可变**的，每次修改返回新对象

##### Instant时间戳

- 需要设置系统时区
- 可以转换为Date对象

##### DateTimeFormatter

- 更灵活的时间日期格式化器

##### Period

- 计算日期间隔（年月日）

##### Duration

- 计算时间间隔（日 时分秒）

##### ChronoUnit

- 比较所有时间单位

## 包装类

- 包装类：8种基本数据类型对应的引用类型

  | 基本数据类型 | 引用数据类型 |
  | ------------ | ------------ |
  | byte         | Byte         |
  | short        | Short        |
  | int          | Integer      |
  | long         | Long         |
  | char         | Character    |
  | float        | Float        |
  | double       | Double       |
  | boolean      | Boolean      |

- 目的

  - Java为了实现“一切皆对象”
  - **集合和泛型只支持包装类型**，不支持基本数据类型

- 基本类型变量和引用数据类型 **可以自动转换**

- 引用数据类型特有功能：

  - 默认值可以是null（容错率更高）

  - 把基本数据类型转换为字符串形式

    ```java
    Integer i=23;
    String s=i.toString();
    //也可以直接 +字符串 得到字符串类型
    String s2=i+"";
    ```

  - **字符串类型转换为真实数据类型**

    ```java
    String number="23";
    int a=Integer.valueOf(number);
    ```

## 正则表达式

- 规则
  - 普通字符

    | 字符           | 描述                                                         |
    | -------------- | ------------------------------------------------------------ |
    | [ABC]          | 匹配 **[...]** 中的所有字符                                  |
    | [^ABC]         | 匹配除了 **[...]** 中字符的所有字符                          |
    | [A-Z]          | [A-Z] 表示一个区间，匹配所有大写字母，[a-z] 表示所有小写字母 |
    | [a-z&&[ ^m-p]] | a-z，除了m-p                                                 |
    | .              | 匹配除换行符（\n、\r）之外的任何单个字符，相等于 [ ^\n\r]<br />如果要表示“点”，要写成`\\.`（两个转义斜杠） |
    | [\s\S]         | 匹配所有。\s 是匹配所有空白符，包括换行，**\S 非空白符，不包括换行** |
    | \w             | 匹配字母、数字、下划线。等价于 [A-Za-z0-9_]                  |
    | \W             | [ ^\w]非单词字符                                             |
    | \d             | [0-9]                                                        |
    | \D             | [^0-9]                                                       |

  - 量词

    | 语法         | 说明                                                         |
    | ------------ | ------------------------------------------------------------ |
    | a.*b         | 匹配最长的以a开始，以b结束的字符串(**贪婪**匹配)             |
    | a.*?b（X？） | 匹配最短的，以a开始，以b结束的字符串（**懒惰**匹配，也就是匹配尽可能少的字符） |
    | ？           | 重复前面内容的0次或一次                                      |
    | *            | 零次或多次                                                   |
    | +            | 一次或多次                                                   |
    | X{n}         | 把X重复n次                                                   |
    | X{n,}        | 把X重复n次以上                                               |
    | X{n,m}       | 把X重复n-m次                                                 |

  - 不要随便写空格

- 正则表达式在字符串方法中的应用

  - `boolean matches(String regex)`
  - `String replaceAll(String regex, String newStr);`
  - `String[] split(String regex);`按照匹配内容分割字符串

Java通过**Pattern类和Matcher类**提供对正则匹配的支持

## Arrays

- 数组操作工具类

```java
String toString(类型[] a);
void sort(类型[] a);//默认升序排序
<T> void sort(类型[] a,Comparator<?super T> c);//用比较器对象 自定义排序
int binarySearch(int[] a,int key);//二分搜索（数组要提前排好序），存在返回索引，不存在：返回“（-1）*（应该插入的位置+1）”
```

- 自定义比较器对象(只能对引用类型排序)

  - 左边 ＞ 右边 ： 返回正整数
  - 左边 ＜ 右边 ： 返回负整数
  - 左边  =  右边 ： 返回0

  ```java
  Integer[] a={12,34,15,36};
  Arrays.sort(a, new java.util.Comparator<Integer>() {
      @Override
      public int compare(Integer o1, Integer o2) {
          return -(o1-o2);//比较浮点型，必须用Double.compare(Double a,Double b)
      }
  });
  //System.out.println(a);会输出地址
  System.out.println(Arrays.toString(a));//[12, 15, 34, 36]
  ```

## Lambda表达式

- JDK8开始的新语法形式，用于**简化“接口中只有一个抽象方法”的匿名内部类的代码写法**
- 简化格式
- Lambda表达式的省略写法
  - 参数类型可以省略不写
  - 如果只有一个参数，参数类型可以省略，同时()也可以省略
  - 如果Lambda表达式的方法体代码只有一行，可以省略大括号不写，同时必须省略分号
    - 如果这一行代码是return语句，必须省略return不写

```java
public class Lambda {
    public static void main(String[] args) {
        //初始
        Swimming s = new Swimming() {
            @Override
            public void swim(String name) {
                System.out.println(name + "swimming");
            }
        };
        go(s, "小明");

        //Lambda
        go((String name) -> {
            System.out.println(name + "swimming");
        }, "小明");

        //Lambda简化：只有一个参数，参数类型省略，()省略
        //Lambda简化：只有一行代码，大括号省略，分号省略
        go(name -> System.out.println(name + "swimming"), "小明");
    }

    public static void go(Swimming s, String name) {
        s.swim(name);
    }
}

@FunctionalInterface //一旦加上这个注解，就必须是函数式接口，里面只能有一个抽象方法
interface Swimming {
    void swim(String name);
}
```

## 集合

- 集合类型可以不固定，大小可变。

- 集合对象的**变量名** 存储的是 在堆内存中的**集合对象的首地址**，**集合对象中每个元素**存的是**每个对象在堆内存中的地址**
- 集合中**只能存储引用类型**数据（基本数据类型要用包装类）
- 体系结构
  - **Collection**单列（每个元素只包含一个值）
    - **List**系列：添加的元素 **有序、可重复、有索引**
    - **Set**系列：添加的元素 **不重复、无索引**
  - **Map**双列（每个元素包含键值对）
- 集合支持泛型，在编译阶段约束集合，只能操作某种引用数据类型

### Collection

- 常用API（Collection的功能，是所有**单列集合都可以继承使用**的）

```java
boolean add(E e);
void clear();
boolean remove(E e);
boolean contains(Object obj);
boolean isEmpty();
int size();
Object[] toArray();//集合中的元素存储到数组
```

- 遍历

  - 迭代器遍历（Java中的迭代器是`Iterator`）

    ```java
    Iterator<String> it = lists.iterator();
    while(it.hasNext()){
        String ele = it.next();
        System.out.println(ele);
    }
    ```

  - 增强for循环（内部原理是迭代器，相当于迭代器的简化写法）

    ```java
    for(String ele : lists){
        System.out.println(ele);
    }
    ```

  - Lambda表达式遍历集合

    ```java
    Collection<String> lists = new ArrayList<>();
    lists.forEach(new Consumer<String>(){
        @Override
        public void accept(String s){
            System.out.println(ele);
        }
    });
    //简化后：
    lists.forEach(s->{
       System.out.println(ele); 
    });
    //lists.forEach(s->System.out.println(ele));
    ```

#### List系列集合

- 常用多态实现
  - `List<String> lists = new ArrayList<>();`
  - 面向接口编程
    - 便于扩展、便于替换（解耦）

- **ArrayList**：基于**数组**实现
- **LinkedList**：底层数据结构是**双链表**

#### Set系列集合

- 常用多态形式实现

- **Set系列的底层用Map实现**，不过只要键不要值

- 特点

  - 无序：**存取**顺序不一致（不是随机无序，只有存的时候无序一次）

  - 不重复：去重

  - 无索引：没有带索引的方法，所以不能用普通for循环，也不能用索引取元素

- HashSet：无序、不重复、无索引。底层采取**哈希表**存储

  - 哈希表的底层组成：**数组+链表+红黑树**
  - 哈希表详细流程：
    1. 创建默认长度16，默认加载因为0.75的数组，数组名table
    2. 根据元素的**哈希值(根据对象的地址求得)和数组的长度取余**计算出应存入的位置
    3. 判断当前位置是否为null，是则直接存入，否则**调用equals比较，一样则不存**，不一样则存入当前位置的链表。一个位置存入数据超过8个，则把这个位置的数据转换为红黑树形式
    4. 数组存满到16*0.75=12时，自动扩容，每次扩容2倍
  - 如果希望Set集合认为**2个内容一样的不同对象是重复的，则必须重写对象的HashCode()和equals()**方法。（IDEA可自动重写）

- LinkedHashSet：**有序(存取顺序一致)**、不重复、无索引。

  - 底层依旧是哈希表，但每个元素多了个双链表记录存储顺序。

- TreeSet：**排序(按元素大小，默认升序)**、不重复、无索引

  - 底层基于红黑树的数据结构实现排序。

  - TreeSet**一定要排序**，可以将元素按照指定规则排序。自定义类型必须重写排序规则：(两种规则都存在时，默认用集合自带的比较器对象)

    - 类自定义比较规则

      ```java
      public class Apple{
          //o1.compareTo(o2);
          @Override
          public int compareTo(Apple o){
              return this.weight-o.weight;	//去除重复重量
              return this.weight-o.weight >= 0 ? 1 : -1;	//保留重复元素
          }
      }
      ```

      - 深度自定义比较规则

        ```java
        //数据：“一.啊啊啊啊”“二.啊啊啊啊”“十一.啊啊啊啊”
        ArrayList<String> sizes = {"一","二","三","四","五","六","七","八","九","十","十一"};	//错误定义
        List<String> sizes = new ArrayList<>();
        Collections.addAll(sizes,"一","二","三","四","五","六","七","八","九","十","十一");
        Collections.sort(	data,(o1,o2) -> sizes.indexOf(o1.substring(0,o1.indexOf(".")))
                         - sizes.indexOf(o2.substring(0,o2.indexOf(".")))	);
        ```
    
    - **集合自带比较器对象**
    
      ```java
      Set<Apple> apples = new TreeSet<>(new Comparator<Apple>(){
         @Override
          public int compare(Apple o1,Apple o2){
              //return o1.weight-o2.weight;
              return Double.compare(o1.getWeight(),o2.getWeight());//浮点型用Double.compare更好
          }
      });
      //简化：
      Set<Apple> apples = new TreeSet<>((o1,o2)->	Double.compare(o1.getWeight(),o2.getWeight())	);
      ```

#### Collections工具类

- Collections不属于集合，是用来操作集合的工具类

- 常用API

  - `<T> boolean addAll(Collection<? super T> c, T...elements)`给集合对象批量添加元素（比一个个得add方便）

  - `void shuffle(List<?> list)`打乱List集合元素顺序

  - `<T> void sort(List<T> list)`将集合中元素按照默认规则排序（对于自定义类型的List，要指定比较规则）（参考TreeSet中的自定义排序规则）

    - Collections类的sort方法自带比较器对象

      ```java
      <T> void sort(List<T> list, Comparator<? super T> c)
      ```

    - 类中自定义比较规则

### Map

- Map集合是双列集合，每个元素都是键值对`key=value`

  - **键**是**无序**，**不重复**，**无索引**
  - 重复的键对应的值会覆盖重复的键的值
  - **键值对都可以是null**

- **HashMap**：键是无序、不重复、无索引

  - 底层是**哈希表**，类似HashSet，不过把单个元素换成Entry键值对

- LinkedHashMap：键是**有序**、不重复、无索引

  - 有序 指存取顺序一致
  - 底层哈希表，双链表保证有序

- TreeMap：键是**排序**、不重复、无索引

  - 底层原理和TreeSet一样（类比HashMap）

- 常用API

  ```java
  Map<String，Integer> maps = new HashMap<>();
  maps.put("huawei",1000);//添加元素
  maps.clear();//清空集合
  maps.isEmpty();
  Integer num = maps.get("huawei");
  maps.remove("huawei");
  maps.containsKey("huawei");//判断是否包含某个键
  maps.containsValue(1000);//判断是否包含某个值
  Set<String> keys = maps.keySet();//获取全部键的集合
  Collection<Integer> values = maps.values();//获取全部值的集合
  maps.size();
  //合并集合
  Map<String，Integer> maps2 = new HashMap<>();
  maps.putAll(maps2);//把集合maps2的元素拷贝到maps中
  ```

#### Map的遍历

- 键找值

  1. `maps.keySet()`获得键的集合
  2. 遍历Set，`maps.get(key)`获得值

- 键值对

  ``` java
  Set<Map.Entry<String, Integer>> entries = maps.entrySet();
  for(Map.Entry<String, Integer> entry : entries){
      String Key = entry.getKey();
      int vlaue = entry.getValue();
  }
  ```

- Lambda表达式

  ```java
  maps.forEach(new BigConsumer<String,Integer>(){
      @Override
      public void accept(String Key,Integer value){
          System.out.prinln(key+value);
      }
  });
  maps.forEach((key,value)->System.out.prinln(key+value));
  ```



### 泛型深入

- 好处：统一了数据类型（E、T、K、V 用于定义泛型）

#### 自定义泛型类

- 格式`修饰符 class 类名<泛型变量>{}`

  ```java
  public class MyArrayList<T>{……}
  ```

#### 自定义泛型方法

- 格式`修饰符 <泛型变量> 返回类型 方法名称 (形参列表){} `

  ```java
  public <T> void show(){……}
  ```

- 使方法接收一切实际类型的参数，方法更有通用性

#### 自定义泛型接口

- 格式`修饰符 interface 接口名<T>{}`

  ```java
  public interface Data<E>{……}
  ```

- 在实现接口时，传入自己操作的数据类型，重写后的方法都是针对该类型的操作

#### 泛型通配符、上下限

- ？ 在使用泛型时代表一切类型
  - **A和B都继承了C**，但ArrayList< A >和ArrayList< B >和ArrayList< C >没有关系，
    所以接口接收的形参类型定义成**ArrayList< C >，无法接收ArrayList< A >和ArrayList< B >**
    接口接收的形参类型定义成**ArrayList< ？>，可以接收ArrayList< A >和ArrayList< B >**
- 上下限
  - 接口接收的形参类型定义成ArrayList< ？>，可以接收ArrayList< A >和ArrayList< B >，但也可以接收ArrayList< F >（无关类，不应该接收）
  - `? extends C` 必须是C或C的子类，**泛型上限**
  - `? super C` 必须是C或C的父类，**泛型下限**

### 集合的嵌套

举例：`Map<String, List<String>> data = new HashMap<>();`

### 不可变集合

- List、Set、Map都有`of`方法（定义后不可修改）

  `List<Double> lists = List.of(569.0, 700.5, 654.2);`

- 其他创建方法

## 常见数据结构

- 栈
- 队列
- 数组 （查询快，增删慢
- 链表 （查询慢，增删快
- 二叉树
  - 二叉搜索树
    - 平衡二叉树
    - <font color="red">红黑树(基于红黑规则实现自平衡)</font> （增删改查性能都很好

## Stream流

- Java8中得益于Lambda的函数式编程，用于**简化集合和数组操作的API**

- 步骤

  1. 先得到数组或集合的Stream流
  2. 把元素放上去
  3. 用Stream流简化的API操作元素

### 三类方法

  - 获取Stream流 //创建流水线，把数据放到流水线上准备操作

    ```java
    //集合 .stream() 方法
    Collection<String> list = new ArrayList<>();
    Stream<String> s = list.stream();
    //Map .stream() 方法
    Map<String,Integer> maps = new HashMap<>();
    Stream<String> keystream = maps.keySet.stream();
    Stream<Integer> valueStream = maps.values().stream();
    Stream<Map.Entry<String,Integer>> keyAndValueStream = maps.entrySet().stram();
    //数组 Arrays.stream(name)  Stream.of(name)
    String[] name={"aa","bb","cc"};
    Stream<String> nameStream1 = Arrays.stream(name);
    Stream<String> nameStream2 = Stream.of(name);
    ```

  - 中间方法 //支持链式编程 //Stream流无法直接修改集合、数组的数据

    ```java
    //Stream<T> filter(Predicate<? super T> predicate) 过滤
    list.stream().filter(new Predicate<String>(){
        @Override
        public boolean test(String s){
            return s.startsWith("a");
        }
    });
    
    // limit 取前几个元素
    list.stream().filter(s -> s.startsWith("a")).limit(2).forEach(s -> System.out.println(s));
    //对于forEach(s -> System.out.println(s))，形参s和println方法中的s是同一个变量，可以使用“方法引用”简化为：
    list.stream().filter(s -> s.startsWith("a")).limit(2).forEach(System.out::println);
    
    //skip 跳过前几个元素（使用方法同limit）
    
    //Map 加工方法
    list.stream().map(new Function<String, String>(){ //第一个String是原元素的类型，第二个String是加工后的元素类型
        @Override
        public String apply(String s){ //返回值String是加工后的元素类型，参数String是原元素的类型
            return s+"xxx";
        }
    });
    //举例：把所有学生名都加工成一个学生对象并输出
    list.stream().map(s -> new Student(s)).forEach(s -> System.ou.println(s));
    list.stream().map(Student::new).forEach(System.out::println); //构造器引用  方法引用
    
    //Stream<T> concat(Stream<? extends T> a,Stream<? extends T> b) 合并流
    Stream<String> s3 = Stream.concat(s1,s2);
    ```

  - 终结方法 //一个Stream流只能有一个终结方法，是Stream流最后一个操作

    - `forEach(Consumer action)`对流中每个元素遍历
    - `count()`返回流中元素个数
    - ……

### 收集Stream流

#### 收集到集合中

- Collectors工具类：把元素收集到List、Set、Map中
  - `R collect(Collector collector)`

```java
Stream<String> s = list.stream().filter(s -> s.startsWith("a"));

List<String> list = s.collect(Collectors.toList());
Set<String> set = s.collect(Collectors.toSet());
Map<String,Integer> map = s.collect(Collectors.toMap(Functions.identity(),String::length));
//Function.identity() 返回一个输出跟输入一样的Lambda表达式对象，等价于形如t -> t形式的Lambda表达式
//String::length 将其长度存储为值
//https://github.com/CarpenterLee/JavaLambdaInternals/blob/master/5-Streams%20API(II).md
```

#### 收集到数组中

```java
String[] arrs = s.toArry(new IntFunction<String[]>(){
    @Override
    public String[] apply(int value){	//value：元素个数
        return new String[value];	//返回长度为value的String数组
    }
});
// String[] arrs = s.toArry(value -> new String[value]);
// String[] arrs = s.toArry(String[]::new);
```

## 异常

- 程序在 编译、执行 时出现的问题。**语法错误 不是 异常**
- 出现异常后，如果没有提前处理，程序就会退出JVM虚拟机
- 分类
  - ERROR：系统级别问题、JVM退出等，代码无法控制
  - Exception，异常类
    - RuntimeException及其子类：运行时异常，编译不报错。（空指针、数组越界、数学操作异常、类型转换错误等）
    - 其他所有异常：编译时异常，不能通过编译。

### 异常的处理

> 默认的异常处理机制不好，程序出现异常后立即死亡

**编译时异常**的处理机制（三种）：

- `throws`将方法内部出现的异常抛给本方法的调用者处理
  - 不好，发生异常的方法自己不处理异常，如果异常最终抛给虚拟机将导致程序死亡
    - 格式`方法 throws 异常1，异常2，异常3,……{}`//最终只抛出一个最先遇到的异常
  - 推荐格式`方法 throws Exception{}`//简化代码
    - 因为**Exception可以捕获一切类型的异常**
- `try……catch……`方法内部监视捕获异常，直接处理
  - 格式`try{...}catch(异常类型1 变量){...}catch(异常类型2 变量){...}...`
    - 推荐格式`try{...}catch(Exception e){e.printStackTrace();}`
- 前两者结合，出现异常抛出给调用者，调用者捕获处理。
  - 有的异常需要调用层处理，所以前两者结合使用
    - 遇到异常的方法throws给调用者
    - 调用者捕捉处理异常

运行时异常的处理

- 可以不处理，编译不报错
- 可以`try{}catch{}`处理

### 自定义异常

#### 自定义编译时异常

> 编译时异常是编译阶段强制报错处理（throws或try catch）

1. 定义一个异常类继承Exception
2. 重写构造器
3. 在出现异常的地方用throw new 自定义对象抛出

```java
package basic.exception;

public class AgeIllegalException extends Exception{
    public AgeIllegalException(){

    }

    public AgeIllegalException(String message) {
        super(message);
    }
}
```

```java
package basic.exception;

public class ExceptionDemo {

    public static void main(String[] args) throws AgeIllegalException {
        checkAge(-34);
    }

    public static void checkAge(int age) throws AgeIllegalException {
        if (age < 0 || age > 200) {
            //抛出一个异常给调用者
            //throw：方法内部创建一个异常对象并抛出
            //throws：用在方法申明上，抛出方法内部的异常
            throw new AgeIllegalException(age + " is illegal");
        } else {
            System.out.println("年龄合法");
        }
    }
}
```

```shell
输出报错：
-34 is illegal
```

#### 自定义运行时异常

> 编译阶段不强制报错，运行时可能出现

1. 定义一个异常类继承RuntimeException
2. 重写构造器
3. 在出现异常的地方用throw new 自定义对象抛出

```java
public class AgeIllegalException extends RuntimeException{
    public AgeIllegalException(){    }

    public AgeIllegalException(String message) {
        super(message);
    }
}
```

```java
public class ExceptionDemo {

    public static void main(String[] args){
        checkAge(-34);
    }

    public static void checkAge(int age) throws AgeIllegalException {
        if (age < 0 || age > 200) {
            //抛出一个异常给调用者
            //throw：方法内部创建一个异常对象并抛出
            //throws：用在方法申明上，抛出方法内部的异常
            throw new AgeIllegalException(age + " is illegal");
        } else {
            System.out.println("年龄合法");
        }
    }
}
//输出报错：
// -34 is illegal
```

## 日志

- 输出语句的弊端
  - 信息只能展示在控制台
  - 不能记录到文件或数据库
  - 想取消记录的信息要修改代码
- 日志技术的优势
  - 将系统执行信息**选择性**记录到**指定位置**
  - 性能好
- 日志技术的体系结构
  - 规范（大都是接口，提供给实现框架）
    - Commons logging
    - Simple Logging Facade for Java
  - 实现框架
    - Log4J
    - **Logback**

### Logback

> 由Log4j创始人设计的另一个开源日志组件，性能比Log4j好

- Logback主要分为三个技术模块
  - logback-core ：为其他两个模块奠定基础
  - logback-classic ：Log4j的改良版本，完整实现了slf4j API
  - logback-access ：与Tomcat和Jetty等Servlet容器集成，以提供HTTP访问日志功能
- 步骤
  1. 项目下新建lib文件夹，导入Logback相关jar包，并添加到项目库
  2. 将Logback核心配置文件logback.xml拷贝到src目录下
  3. 在代码中获取日志对象
  4. 使用日志对象输出日志信息

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Logback {
    //public static final Logger LOGGER = LoggerFactoy.getLogger("Logback.class");  //因为少了一个 r ，崩溃两个小时
    public static final Logger LOGGER = LoggerFactory.getLogger("Logback.class");

    public static void main(String[] args) {
        try {
            LOGGER.debug("main开始执行");
            LOGGER.info("这是一条info日志");
            int a = 10, b = 0;
            LOGGER.trace("b= " + a);

            System.out.println(a / b);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("出现异常： " + e);
        }
    }
}
```

日志内容：

```
2022-01-04 22:42:49.412 [DEBUG]  Logback.class [main] : main开始执行
2022-01-04 22:42:49.414 [INFO ]  Logback.class [main] : 这是一条info日志
2022-01-04 22:42:49.418 [TRACE]  Logback.class [main] : b= 10
2022-01-04 22:42:49.419 [ERROR]  Logback.class [main] : 出现异常： java.lang.ArithmeticException: / by zero
```

#### 日志输出位置、格式设置

logback.xml：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <!--
        CONSOLE ：表示当前的日志信息是可以输出到控制台的。
    -->
    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <!--输出流对象 默认 System.out 改为 System.err后日志文字颜色变为红色-->
        <target>System.out</target>
        <encoder>
            <!--格式化输出：%d表示日期，%thread表示线程名，%-5level：级别从左显示5个字符宽度
                %msg：日志消息，%n是换行符-->
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%-5level]  %c [%thread] : %msg%n</pattern>
        </encoder>
    </appender>

    <!-- File是输出的方向通向文件的 -->
    <appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
            <charset>utf-8</charset>
        </encoder>
        <!--日志输出路径-->
        <file>log/learn-java.log</file>
        <!--指定日志文件拆分和压缩规则-->
        <rollingPolicy
                class="ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy">
            <!--通过指定压缩文件名称，来确定分割文件方式-->
            <fileNamePattern>log/learn-java-%d{yyyy-MMdd}.log%i.gz</fileNamePattern>
            <!--文件拆分大小-->
            <maxFileSize>1MB</maxFileSize>
        </rollingPolicy>
    </appender>

    <!--

    level:用来设置打印级别，大小写无关：TRACE, DEBUG, INFO, WARN, ERROR, ALL 和 OFF
   ， 默认debug
    <root>可以包含零个或多个<appender-ref>元素，标识这个输出位置将会被本日志级别控制。
    -->
    <root level="ALL">
        <appender-ref ref="CONSOLE"/>
        <appender-ref ref="FILE" />
    </root>
</configuration>
```

### 日志级别

TRACE < DEBUG < INFO < WARN < ERROR ，默认是debug级（忽略大小写）

- 只输出级别不低于设定级别的日志
- ALL 和 OFF 是输出所有和关闭所有

## 读写文件

### File类

- 代表操作系统的**文件对象**，可以定位、操作文件/文件夹，但不能读写文件
- [API](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/io/File.html#constructor-summary)
- **路径**写法：
  - 绝对路径
    - `D:\\aaa\\bbb\\cc.jpg`
    - `D:/aaa/bbb/cc.jpg`
  - 相对路径（相对路径的根目录是工程文件夹)
    - `模块名\\cc.jpg`

### 字符集

- GBK 国标码的一种。**一个中文 = 两个字节**
- Unicode 统一码。以UTF-8编码后**一个中文 = 三个字节**
- **英文和数字**在任何字符集中都只占**1个字节**

#### 编码与解码

- 编码：把文字转换为字节（使用指定编码）

```java
String name = "zx";
byte[] bytes = name.getBytes();//以当前代码的默认字符集编码（UTF-8)
byte[] bytes2 = name.getBytes("GBK");
```

- 解码

```java
String names = new String(bytes);
String names = new String(bytes,"GBK");
```

**如果编码和解码的字符集不同，会导致乱码**

### IO流

- IO流
  - 按方向分
    - 输入流
    - 输出流
  - 按流中的数据最小单位分
    - 字节流 - 所有文件
    - 字符流 - 文本

#### 字节流

##### 字节输入流

- 读取失败返回 -1

- 每次读取一个字节：`int read()`（读取中文会乱码）

- 每次读取字节数组：（分割的地方，读取到中文，也可能导致乱码）

  ```java
  InputStream is = new FileInputStream(path);
  //定义一个字节数组用于接收
  byte[] buffer = new byte[1024]; //1KB
  int len = is.read(buffer); //len:读取了的字节长度 
  String rs = new String(buffer); //读取到的内容
  String rs = new String(buffer,0,len); //避免重复使用buffer时（从前往后覆盖内容），有之前使用且未被覆盖的字节。
  
  is.close();//释放资源
  ```

- 一次读完全部字节：（如果文件过大，可能导致数组内存溢出）

  - 定义一个与文件一样大的数组
    - `byte[] buffer = new byte[(int) file,length()];`
  - JDK9的API：`byte[] buffer = is.readAllBytes();`

##### 字节输出流

```java
OutputStream os = new FileOutputStream(path);//默认清空数组再写
OutputStream os = new FileOutputStream(path，true);//追加写入数据

os.write(98);//写单个字节
os.flush();//刷新数据，不释放
os.write("\r\n".getBytes());//写一个字节数组
os.close();
```

#### 资源释放

- `try{...}catch(Exception e){...}finally{...}`

  - 被finally控制的语句最终一定执行

    ```java
    ……;
    }finally{
        try{
            if(is != null){
            is.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    ```

  - finally里的return会在正常的return之前返回，导致返回的永远是finally里的数据

- JDK7:  `try(定义流对象){...}catch(Exception e){...}`资源用完后自动释放

- JKD9: `定义输入流对象；定义输出流对象；try(输入流对象;输出流对象){...}catch(Exception e){...}` 资源用完自动释放

#### 字符流

- 读数据 FileReader

```java
Reader fr = new FileReader(path);
int code = fr.read();//读取一个字符，失败返回-1
System.out.println((char)code);

char[] buffer = new char[1024];
int len = fr.read(buffer);//读取字符数组
```

- 写数据 FileReader

```java
Writer fw = new FileReader(path,true);
fw.write(98);//写单个字符
fw.write('我');
fw.write("我呜呜呜呜呜");
fw.flush();
fw.write("我呜呜呜呜呜",0,3);
fw.write(buffer);
fw.close();
```

### 缓冲流

- **自带8KB缓冲区**，提高原始字节流、字符流读写数据的性能
- `BufferedInputStream`、`BufferedOutputStream`
- `BufferedReader`、`BufferedWriter`

**推荐使用缓冲流结合数组**

- 缓冲流是一个**包装流**，**对字符流进行包装**

- 缓冲字符输入流 增加 **按行读取** 的API
  - `BufferedReader br = new BufferedReader(new FileReader(path));  String line = br.readLine();`
  - 读取失败返回null
- 字符缓冲输出流 增加 **换行** 的API
  - `BufferedWriter bw = new BufferedWriter(new FileWriter(path));    bw.newLine();`

### 转换流

>  如果**代码编码和文件编码不一致**，用**字符流直接读取中文会导致乱码**(因为UTF-8是3个字节一个中文字符，GBK是两个字节)

**字符输入转换流`InputStreamReader`：**把 原始**字节流** 以指定编码转换成 **字符流**（然后包装成缓冲流），继承自Reader类

```java
InputStream is = new FileInputStream(path);//字节输入流
Reader isr = new InputStreamReader(is,"GBK");//字节转换成字符流
BufferedReader br = new BufferedReader(isr);//字符流包装成缓冲流
String line;
while(line = br.readLine() != null){System.out.println(line);}
```

以指定字符集**输出**：

- 转换成字节，然后字节流输出（`os.write(lines.getBytes());`）

- **字符输出转换流**`OutputStreamWriter`：

  ```java
  OutputStream os = new FileOutputStream(path);//字节输出流
  Writer osw = new OutputStreamWriter(os);//字节输出流转换成字符输出流,默认UTF-8
  BufferedWriter bw = new BufferedWriter(osw);
  bw.close();
  ```

### 对象序列化

> 以内存为基准，把内存中的对象存储到磁盘文件，称为对象序列化

使用到的流：对象**字节**输出流`ObjectOutputStream`

- 把字节输出流 包装成 对象字节输出流

  ```java
  ObjectOutputStream oos = new ObjectOutputStream( new FileOutputStream(path));
  Student s = new Student();
  oos.writeObject(s);
  oos.close();
  ```

  - 对象如果要序列化，必须实现Serializable接口

    ```java
    public class Student implements Serializable{
        //申明序列化的版本号
        //反序列化时 代码中的对象的版本号 必须与 序列化时保存到文件的对象的版本号 一致，否则会报错
        //代码更新后可以更新版本号
        private static final long seriaVersionUID = 1;
        
        private String name;
        private transient String password;//transient修饰的成员变量不参与序列化
    }
    ```

- 把字节输出流 包装成 对象字节输入流

  ```java
  ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path));
  Student s = (Student) is.readObject();
  ```

### 打印流-输出流

> 方便高效得打印数据到文件中，一般指`PrintStream`和`PrintWriter`两个流

- PrintStream

  ```java
  PrintStream ps = new PrintStream(new FileOutputStream(path,true),true,"GBK");
  //第一个true:追加数据（只能在低级管道加true）；第二个true：自动刷新
  PrintStream ps = new PrintStream(path);//不能加true和"GBK"
  ps.println(23);
  ps.println("aaa");
  ps.close();
  ```

- PrintWriter

  - **打印(println)**数据功能上，和PrintStream一样，都使用方便，性能高效
  - PrintWriter继承自字符输出流Writer，支持**写(write)**字符数据
  - PrintStream继承OutputStream，支持写**字(write)**节数据
  
- 如果**没有开启自动刷新**,则**需要手动刷新**或者当缓冲区满的时候,再自动刷新.（或直接调用close()方法，会自动刷新）

**输出语句重定向：**

```java
PrintStream ps = new PrintStream(path);
System.setOut(ps);//重定向
System.out.println("aaaa");
```

### Properties

> properties : Map集合。但一般不当集合用，HashMap更好用
>
> 作用：代表属性文件。把对象中的键值对信息存到属性文件中
>
> 属性文件：后缀是.propeerties的文件，内容都是键值对

- 保存到文件

  ```java
  Properties properties = new Properties();
  properties.serProperty("admin","123456");
  properties.serProperty("deia","123456");
  properties.store(new FileWriter(path),"注释内容");//会自动close
  ```

- 读取

  ```java
  Properties properties = new Properties();
  properties.load(new FileReader(path));
  String s = properties.getProperty("admin");
  ```

### IO 框架

> commons-io 是Apache提供的IO操作的类库。两个主要的类：FileUtils,IOUtils

FileUtils:

- API举例：
  - `String readFileToString(File file,String encoding)`读取文件，返回字符串
  - `void copyFile(File srcFile,File destFile)`复制文件 
  - `void copyDirectoryToDirectory(File srcDir,File destDir)`复制文件夹

> JDK 1.7 也新增了一些IO操作

## 多线程

### 创建多线程

三种方法：

- 继承**Thread**类

  ```java
  main(String[] args){
      Thread t = new MyThread();//new一个线程对象
      t.start();//调用start()方法启动线程（执行run方法）
  }
  class MyThread extends Thread{
      @Override
      public void run(){
          System.out.println("ok");
      }
  }
  ```

  - 优点：编程简单
  - 缺点：已经继承了Thread类，不能再继承其他类，不好拓展
  - 只有调用start()方法才启动线程，调用run就是普通方法
  - 主线程任务代码要放在调用子线程之后

- 实现**Runnable**接口

  - **把任务对象交给Thread**，再调用start()

    ```java
    main(String[] args){
        Runnable target = new MyRunnable();//创建任务对象
        Thread t = new Thread(target);//把任务对象交给thread处理
        t.start();
    }
    class MyRunnable implements Runnable{
        @Override
        public void run(){
            System.out.println("ok");
        }
    }
    //可以用匿名内部类和Lambda简化
    ```

  - 优点：拓展性强

  - 缺点：编程多了一层对象包装，线程的执行**结果不能直接返回**

- 用**Callable**、**FutureTask**接口实现

  - 步骤

    1. 得到任务对象

       ①**定义类实现Callable接口，重写call方法**

       ②**用FutureTask把Callable对象封装成线程任务对象**

    2. 把线程任务对象交给Thread处理

    3. 调用Thread的start方法启动线程执行任务

    4. 通过**Future的get方法获取任务执行的结果**

    ```java
    main(){
        Callable<String> call = new MyCallable();
        FutureTask<String> f = new FutureTask<>(call);
        Thread t = new Thread(f);
        t.start();
        String rs = f.get();//如果f任务没有完成，会等待，直到线程t结束
    }
    class MyCallable implements Callable<String>{
        @Override
        public String call() throws Exception{
            return "aaa";
        }
    }
    ```

  - 优点：扩展性好，有返回结果

  - 缺点：编程相对复杂

### 常用API

- 区分线程

  -  `String getNanme();`  `void setName()`

      ```java
      Thread m = Thread.currentThread();
      Sysytem.out.println(m.getName());
      ```
      
  -  构造器中写名字

      ```java
      Thread t =new Thread("name");
      Thread tt = new Thread(target,"name");
      ```

- 线程休眠

  ```java
  Thread.sleep(3000);	//	单位：毫秒
  ```


### 线程同步

> 多个线程同时操作同一个共享资源出现问题：线程安全

加锁方法：

- 同步代码块

  ```java
  synchronized(锁对象){
  //方法体
  }
  ```

  锁对象：拿到锁对象才可以执行同步代码块

  - 用任意的唯一对象当锁对象（比如“xxx”字符串）
    - 会影响其他无关线程的执行。比如A和B竞争资源m，C和D竞争资源n。m或n作为参数传入方法。那么A使用m时，C和D都无法使用n。
  - 锁对象规范要求：
    - 建议使用共享资源作为锁对象
    - 对于**实例方法建议用this作为锁对象**
    - 对于**静态方法用字节码对象(类名.class)作为锁对象**

- 同步方法：把出现线程安全问题的核心方法上锁

  - 格式：`修饰符 synchronized 返回类型 方法名称（形参列表）{...}`
  - 底层原理：隐式锁对象，默认用`this`或`类名.class`作为锁对象。所以代码要高度面向对象

- Lock锁。**功能更强大**。Lock是接口不能直接实例化，用实现类`ReentrantLock`构建锁对象

  ```java
  public class Account{
      private final Lock lock = new ReentrantLock();
      public void xxx(){
          //...
          lock.lock();	//加锁
          try{
              //...
          }finally{
              lock.unlock();	//解锁
          }
      }
  }
  ```

### 线程通信

> 通过共享数据的方式实现

Object类的几个常用方法：

- `void wait()`**当前线程等待**，直到另一个线程`notify()`或`notifyAll()`唤醒自己
- `notify()`唤醒等待锁对象的**单个线程**
- `notifyAll`唤醒等待锁对象的**所有线程**
- 上述方法最好**使用“当前同步锁对象”进行调用**（this是指的是匿名内部Runnable类）

```java
package basic.thread;

import static java.lang.Thread.sleep;

@SuppressWarnings("AlibabaAvoidManuallyCreateThread")
public class Phone {
    //默认false：等待接听电话
    private boolean flag = false;

    public static void main(String[] args) {
        Phone huawei = new Phone();
        huawei.run();
    }

    public void run() {

        new Thread(() -> {
            try {
                while (true) {
                    /*
                    如果用this作为锁对象，很容易崩
                    因为this是指的是匿名内部Runnable类，而不是被共享的Phone对象
                     */
                    synchronized (Phone.this) {
                        if (flag) {     //正在接听电话（逻辑上用不到这部分代码）
                            System.out.println(Thread.currentThread().getName() + " 通话中(异常状态)");
                        } else {
                            System.out.println("有电话接入");
                            flag = true;
                        }
                        Phone.this.notify();
                        Phone.this.wait();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "等待接听状态").start();

        new Thread(() -> {
            try {
                while (true) {
                    synchronized (Phone.this) {
                        if (flag) {     //正在接听电话
                            System.out.println("通话中……挂断电话");
                            //noinspection BusyWait
                            sleep(1000);
                            flag = false;
                        } else {
                            System.out.println("等待接听中");
                        }
                        Phone.this.notify();
                        Phone.this.wait();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "正在接听状态").start();
    }
}
```

### 线程池

> 可以复用线程的技术（否则要不断创建新线程，性能开销大）

得到线程池对象：

- **方法一：使用ExecutorService的实现类ThreadPoolExecutor创建一个线程池对象**（**自定义程度高**）

    ```java
    public ThreadPoolExecutor(
        int corePoolSize,	//核心线程（永久线程）数量（不小于0）
        int maximumPoolSize,	//线程池支持的最大线程数，>=核心线程数
        long keepAliveTime,	//临时线程的最大存活时间，不小于0
        TimeUnit unit,	//存活时间的单位
        BlockingQueue<Runnable> workQueue,	//指定任务队列，不能为空
        ThreadFactory threadFactory,	//指定用哪个线程工厂创建线程，不能为空
        RejectedExecutionHandler handler)	//线程忙、任务满时，来新任务后要进行的操作，不能为空
    ```

    - **新任务提交**时，**核心线程都在忙**，且**任务队列已经满**（因为要尽少得创建线程），并且**还可以创建临时线程**时，**创建临时线程**
    - 核心线程都在忙，任务队列满，无法创建临时线程时：提交新任务，会执行默认的报错处理
    - 调用线程池处理任务：`.execute(Runnable command)`
    - 关闭线程池
      - `.shutdownNow();`	及时任务没完成，也立即关闭
      - `.shutdown();`  等待全部任务执行完后关闭线程池
    
    ```java
    ExecutorService pool = new ThreadPoolExecutor(3,5,6,TimeUnit.SECONDS,
                                                  new ArrayBlockingQueue<>(5),//创建阻塞队列对象，继承自ArrayList
                                                 Executors.defaultThreadFactory(),//默认线程工厂
                                                 new ThreadPoolExecutor.AbortPolicy());//默认拒接策略，丢弃任务并抛出异常
    //执行Runnable对象
    Runnable target = new MyRunnable();
    pool.execute(target);
    //执行Callable对象 Future<T> submit(Callable<T> task);
    Future<String> f = pool.submit(new MyCallable());
    String rs = f.get();
    pool.shutdown(); //一般不关
    ```
    
    - 新任务拒绝策略
        - `ThreadPoolExecutor.AbortPolicy` 丢弃任务并抛出异常，默认
        - `ThreadPoolExecutor.DiscardPolicy` 丢弃任务，不抛出异常，不推荐
        - `ThreadPoolExecutor.DiscardOldestPolicy` 抛弃队列中等待最久的任务，然后把当前任务加入
        - `ThreadPoolExecutor.CallerRunsPolicy` 由主线程调用任务的run()方法，绕过线程池直接执行
    
- 方法二：使用**Executors**（线程池的工具类）调用方法返回不同特点的线程池对象     //工具类都是静态方法，可以直接调用

    - Executors的**底层**也是基于线程池的实现类ThreadPoolExecutor创建线程池对象

    - 创建线程对象常用**方法**：

        - `ExecutorService newCachedThreadPool()`    
            - 根据任务动态调整线程数量
        - `ExecutorService newFixedThreadPool(int numThreads)`    
            - 创建固定线程数的线程池，线程因异常结束后，线程池补充一个替代它
        - `ExecutorService newSingleThreadExecutor()`    
            - 创建只有一个线程的线程池对象，线程异常结束，自动创建补充
        - `ScheduleExecutorService newScheduledThreadPool(int corePoolSize);`参数: 核心线程数    
            - 创建线程池，在给定延迟后运行任务，或定期执行任务 

    - 陷阱：前两个允许请求的任务队列长度是Integer.MAX_VALUE，任务数过多可能导致OOM错误（OutOfMemoryERROR）。

           		后两个创建线程数的上限是Integer.MAX_VALUE，也可能OOM错误

### 定时器

> 控制任务延时调用，或周期调用

实现一：Timer

- 问题

  - 单线程。处理多个任务按序执行，存在延时与设置定时器时间有出入（两个任务都“延迟0，周期2”执行，某任务执行时间过长，可能因单线程导致另一个任务存在执行延迟）
  - 可能因某个任务异常使Timer死掉，影响后续任务

- ```java
  Timer timer = new Timer();
  timer.schedule(new TimerTask(){
      @Override
      public void run(){
          //...
      }
  },2000,3000);	//先隔2秒执行一次，之后每隔3秒执行
  ```

实现二：ScheduleEecutorService

```java
ScheduleExecutorService pool = Executors.newScheduledThreadPool(3);
pool.scheduleAtFixedRate(new TimerTask(){
    @Override
    public void run(){
        //...
    }
},2,3,TimeUnit.SECONDS);	//先隔2秒执行一次，之后每隔3秒执行
```

### 线程的生命周期

- 线程的六种状态
  - NEW 新建
  - RUNNABLE 可运行状态（调用start()后）
  - BLOCKED 阻塞状态 （未获得锁对象）
  - WAITING 无限等待 （获得锁对象调用wait()）
  - TIMED_WAITING 计时等待 （sleep(毫秒), wait(毫秒)）
  - TERMINATED 被终止（执行完毕|出现异常）

## 网络编程

```java
InetAddress ip = InetAddress.getLocalHost();//获取本机地址对象
System.out.println(ip);//输出主机名和IP地址
System.out.println(ip.getName());//输出主机名
System.out.println(ip.getHostAddress());//输出IP地址

InetAddress ip2 = InetAddress.getByName("www.baidu.com");//获取域名ip对象（主机名就是域名）
InetAddress ip3 = InetAddress.getByName("112.80.248.76");//获取公网ip对象（主机名就是IP）
System.out.prinln(ip.isReachable(5000));//判断5s内能否ping通
```

### UDP

> 无连接，不可靠协议

将数据源IP、目的地IP和端口以及数据封装成64KB以内的数据包直接发送

- 数据包对象：DatagramPacket

  ```java
  //创建发送端数据包对象
  DatagramPacket(byte[] buf,	//发送内容，字节数组
                int length,	//发送内容的字节长度
                InetAddress address,
                int port)
  //创建接收端数据包对象
  DatagramPacket(byte[] buf,	//用于存储接收到的内容
                int length)	//能够接受的内容长度
  //获取接收数据长度的方法：
  int getLength();
  //获取发送端IP和端口号
  InetAddress getSocketAddress();	
  int getPort();
  ```

- 发送/接收端 对象：DatagramSocket

  ```java
  //构造器：
  DatagramSocket()	//创建发送端的Socket对象，系统随机分配端口号
  DatagramSocket(int port)	//创建接收端Socket对象，并指定端口号
  ```

  - 类成员方法

    ```java
    void send(DatagramPacket dp)	//发送数据包
    void receive(DatagramPacket p)	//等待接收数据包
    void close()
    ```

通信实例：

```java
/** Client.java
 * @Author: 赵鑫
 * @Date: 2022/1/7 22:14
 */
public class Client {
    public static void main(String[] args) throws Exception {
        System.out.println("客户端启动");
        //创建发送端对象
        DatagramSocket socket = new DatagramSocket();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("input msg:");
            String msg = scanner.nextLine();

            if ("exit".equalsIgnoreCase(msg)) {
                System.out.println("exit success");
                socket.close();
                break;
            }
            //创建数据包对象封装数据
            DatagramPacket packet = new DatagramPacket(msg.getBytes(), msg.getBytes().length, InetAddress.getLocalHost(), 8888);
            socket.send(packet);
        }
    }
}


/** Server.java
 * @Author: 赵鑫
 * @Date: 2022/1/7 22:21
 */
public class Server {
    public static void main(String[] args) throws Exception {
        System.out.println("服务端启动");
        //创建接收端对象
        DatagramSocket socket = new DatagramSocket(8888);
        //数据包接收数据到buffer
        byte[] buffer = new byte[1024 * 64];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

        while (true) {
            socket.receive(packet); //从这个端口不断接收packet，处理packet延迟低，所以不需要多线程
            //buffer 的0~packet.length 转换为string，防止多余转换
            String msg = new String(buffer, 0, packet.getLength());
//            String address = packet.getSocketAddress().toString();    //输出  /192.168.50.147:6666
            String ip = packet.getAddress().toString();
            int port = packet.getPort();
            System.out.println("from "+ip+":"+port+"  msg: "+msg);
        }
    }
}
```

- 广播：

  - 发送端目的IP使用广播IP：255.255.255.255，指定端口
  - 所在网段其他主机使用对应端口即可接收信息

- 组播：

  - 发送端目的IP使用组播IP（224.0.0.0~239.255.255.255），指定端口

  - 接收端绑定该组播IP，端口对应，即可接收组播消息

    - DatagramSocket的子类MulticastSocket可以在接收端绑定组播IP

      ```java
      MulticastSocket socket = new MulticastSocket(8888);
      socket.joinGroup(InetAddress.getByName("224.0.0.1"));
      ```

### TCP 

#### 客户端

TCP客户端的代表类是`Socket`类，使用Socket管道发送、接收数据

- **java中只要使用`java.net.Socket`类实现通信，底层都是TCP协议**
- Socket构造器：`Socket(String host,int port)`参数为服务端程序的ip和端口
- Socket类成员方法`OutputStream getOutputStream()`获得字节输出流对象  `InputStream getInputStream()`获得字节流输入对象

步骤：

1. 创建客户端的Socket对象，请求连接
2. 使用Socket对象调用getOutputStream方法得到字节输出流
3. （包装成：打印流 或 2种缓冲流）完成数据发送
4. 释放资源，关闭Socket管道（强行关闭可能导致数据传输不完全）

```java
//P182 12:30
```

#### 服务端

使用SeverSocket类

- 调用`accept()`方法阻塞等待接收客户端消息，**得到Socket对象**
  - 得到的Socket是单线程对象，如果要实现接收多个客户端的消息，要定义多个Socket
    - 即**每接收到一个Socket，交给单独的子线程处理**

TCP通信特点

- 客户端没有消息，服务端进入阻塞状态
- Socket的一方关闭或出现异常，对方Socket也会失效或出错

> 可以使用线程池优化

#### 群聊案例

> 存在的问题：主机断开连接后，客户端无法即时主动结束主线程
>
> 只能在子线程通过字节输入流随时获得输入，发现套接字失效，提示服务器断开连接
>
> 主线程只能在发送消息时，发现发送失败，进而结束主线程。（不即时）

```java
package basic.tcp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: 赵鑫
 * @Date: 2022/1/9 9:22
 * @Target: 服务器模式的群聊, 服务器使用线程池优化
 */
@SuppressWarnings("AlibabaCommentsMustBeJavadocFormat")
public class Server {
    //创建日志对象        缺省权限：package-private
    static final Logger LOGGER = LoggerFactory.getLogger("Server.class");
    //保存连接到的主机，用于群发
    static final List<Socket> ONLINE_SOCKETS = new ArrayList<>();
    //创建线程池
    /**
     * keepAliveTime：临时线程等待超时时间
     * 表示创建的临时线程在空闲的时候最长的等待时间，但因为子线程一直在while中等待接收消息，不是空闲状态，所以不会关闭
     */
    private static final ExecutorService executorService = new ThreadPoolExecutor(1, 2, 6,
            TimeUnit.SECONDS, new ArrayBlockingQueue<>(1), new ThreadPoolExecutor.AbortPolicy());

    public static void main(String[] args) {
        try {
            System.out.println("服务器启动");
            //1. 注册端口
            ServerSocket serverSocket = new ServerSocket(9999);
            //2. 循环中接收每个客户端的socket管道连接，并交给子线程处理
            while (true) {
                Socket socket = serverSocket.accept();
                //3. 交给线程池调用线程处理
                try {
                    executorService.execute(new ServerThread(socket));
                } catch (Exception e) {
                    LOGGER.info(socket.getRemoteSocketAddress() + "失败，线程资源不足");
                    new PrintStream(socket.getOutputStream()).println("连接人数过多,稍后重试");
                    socket.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

```java
package basic.tcp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.time.LocalDateTime;

/**
 * @Author: 赵鑫
 * @Date: 2022/1/9 9:47
 */
public class ServerThread implements Runnable {
    Socket socket;

    public ServerThread(Socket socket) throws Exception {
        this.socket = socket;
        /**
         * 运行构造器不代表分配到了子线程，分配到子线程后执行run
         */
        Server.LOGGER.trace(LocalDateTime.now().toString() + socket.getRemoteSocketAddress() + "正在加入群聊");
        new PrintStream(socket.getOutputStream()).println("连接服务器成功，排队加入群聊ing");
    }

    /**
     * 从当前套接字接收消息，群发给其他主机
     */
    @Override
    public void run() {
        try {
            synchronized (Server.ONLINE_SOCKETS) {
                //将当前套接字加入列表       （调试发现，某个线程停在这儿时，其他线程成功被阻塞？？？
                Server.ONLINE_SOCKETS.add(socket);
            }
            Server.LOGGER.trace(LocalDateTime.now().toString() + socket.getRemoteSocketAddress() + "加入群聊成功");
            new PrintStream(socket.getOutputStream()).println("加入群聊成功");
            //接收消息
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //群发消息
            String msg;
            while ((msg = bufferedReader.readLine()) != null) {
                for (Socket onlineSocket : Server.ONLINE_SOCKETS) {
                    if (onlineSocket != socket) {
                        new PrintStream(onlineSocket.getOutputStream()).println(msg);
                    }
                }
            }
        } catch (Exception e) {
            //连接断开
            Server.LOGGER.trace(LocalDateTime.now().toString() + socket.getRemoteSocketAddress() + "断开连接" + e);
            synchronized (Server.ONLINE_SOCKETS) {
                Server.ONLINE_SOCKETS.remove(socket);
            }
        }
    }
}
```

```java
package basic.tcp;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * 服务器模式的群聊,服务器使用线程池优化     TODO：服务器断开连接后，只能由子线程发现，无法结束主线程🤬
 *
 * @Author: 赵鑫
 * @Date: 2022/1/8 22:52
 */
public class Client {
    public static void main(String[] args) {
        System.out.println("客户端启动，正在请求连接");
        try {
            //1. 创建接收消息的Socket对象，参数：目标服务器。（不指定，则本地端口号随机）
            Socket socket = new Socket(InetAddress.getLocalHost(), 9999);
            //创建线程随时接收消息
            new ClientInputThread(socket).start();
            //2. 得到字节输出流，包装成打印流
            PrintStream printStream = new PrintStream(socket.getOutputStream());
            //3. 发送数据
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String msg = scanner.nextLine();

                if ("exit".equalsIgnoreCase(msg)) {
                    System.out.println("exited");
                    socket.close();
                    break;
                }
                //如果是print，服务器接收到数据读取时，readLine读取不到换行，gg
                printStream.println(msg);
                //要刷新！！！
                printStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

```java
package basic.tcp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * 创建客户端的接收消息线程
 *
 * @Author: 赵鑫
 * @Date: 2022/1/9 9:45
 */
public class ClientInputThread extends Thread {
    private final Socket socket;

    public ClientInputThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            //得到字节输出流并包装为打印流 × 因为打印流是输出流
            //得到字节流->缓冲流(BufferedInputStream) × 因为BufferedInputStream只能按字节读取
            //字节流->转换流->缓冲流，按行读取
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //处理接收到的字符流
            String msg;
            //平时会处于 阻塞 状态，等待读取
            //如果另一端断开连接，会读取到 null
            while ((msg = bufferedReader.readLine()) != null) {
                System.out.println("receive msg: " + msg);
            }
            System.out.println("服务器断开连接");
        } catch (Exception e) {
            System.out.println("服务器断开连接");
        }
    }
}
```

## 单元测试

> Junit是Java实现的单元测试框架，开源，几乎所有IDE工具都集成了JUnit

Junit的优点：

- 可以灵活选择执行哪些测试方法，可以一键全部执行
- 可以生成全部方法的测试报告
- 某个方法测试失败，不影响其他方法的测试

步骤

1. 如果IDE没有整合Junit框架，需要手动导入`hamcrest-core.jar`和`junit.jar`到模块
2. 编写测试方法。该测试方法必须是**公共的无参数无返回值的非静态方法**
3. 在测试方法上使用@test注解
4. 在测试方法中完成被测试方法的预期正确性测试
5. 选中测试方法，选择”Junit“运行

Junit5 常用注解：

| 注解        | 说明                                                   |
| ----------- | ------------------------------------------------------ |
| @Test       | 测试方法                                               |
| @BeforeEach | 修饰实例方法，该方法在每个测试方法执行之前执行一次     |
| @AfterEach  | 修饰实例方法，该方法在每个测试方法执行之后执行一次     |
| @BeforeAll  | 静态修饰方法，该方法在**所有**测试方法执行之前执行一次 |
| @AfterAll   | 静态修饰方法，该方法在**所有**测试方法执行之后执行一次 |

## 反射

> 对于任何一个编译后的class类文件对象，可以在运行时得到这个类的全部成分
>
> 构造器对象：Constructor
>
> 成员变量：Field
>
> 方法对象：Method
>
> 这种运行时动态获取类信息、动态调用类中成分的能力 称为Java语言的反射机制

1. 获取Class对象的三种方法

   - Class类中的一个静态方法`Class c = Class.forName("basic.tcp.Client")`	参数：全限名（包名+类名）
   - 类名.class `Class c = Client.class`
   - 对象.getClass() `Client client = new Client(); Class c = client.getClass()`

2. Class类中获取构造器的方法(部分举例)

   - Constructor< ?>[ ] getDeclaredConstructors()	返回所有构造器对象数组
     - `Constructor[] cons = c.getDeclaredConstructors();`
   - Constructor<T> getConstructor(Class<?>...parameterTypes)    返回单个构造器对象（仅public） 
     - `Constructor con = c.getConstructor(String.class,int.class)`	返回参数是String,int的构造器对象

3. Constructor类中用于创建对象的方法

     ```java
     Class c = Student.class;
     Constructor con = c.getDeclaredConstructor(String.class,int.class);
     //如果遇到了私有构造器，可以暴力反射
     con.setAccessible(true);
     Student s = (Student) con.newInstance("xiaoming",12);
     ```

4. 获取成员变量

   - 获取全部成员变量`Field[] getDeclaredField()`
   - 获取某个成员变量`Field getDeclaredField(String name)`

   ```java
   Field ageF = c.getDeclaredField("age");
   ageF.setAccessible(true);
   //赋值
   Student s = new Student();
   ageF.set(s,18);
   //取值
   int age = (int) ageF.get(s);
   ```

5. 反射获取方法对象（类似获取构造器方法）(部分举例)

   - `Method[] getMethods()`  返回所有方法对象的数组，仅限public
   - `Method getDeclaredMethod(String name,Class<?>... parameterTypes)` 
   
   ```java
   Class c = Dog.class; //获取类对象
   Method m = c.getDeclaredMethod("eat");	//提取单个方法对象
   m.setAcessible(true);
   Dog d = new Dog();
   //触发方法执行，参数：调用该方法的对象、调用方法要传递的参数
   Object result = m.invoke(d); //如果没有结果返回，返回结果是null
   ```

### 反射的作用

- 绕过编译阶段，为集合添加任意类型的数据

  - 泛型 只是在编译阶段约束集合只能操作某种类型数据，编译成class文件运行时，真实类型都是ArrayList，泛型相当于被擦除了

  ```java
  ArrayList<Integer> list = new ArrayList<>();
  list.add(23);
  //通过反射为list添加其他类型数据
  Class c = list.getClass();
  Method add = c.getDeclaredMethod("add",Object.class);
  boolean result = (boolean) add.invoke(list,"xxx");
  //其他方法,没必要用反射
  ArrayList list2 = list;
  list2.add("xxx");
  ```

- **通用框架的底层原理**

  - 重要应用！！！

## 注解

> 又称Java标注
>
> 对Java中的类、方法、成员变量做标记，然后特殊处理

- 自定义注解

    ```java
    public @interface 注解名称{
        public 属性类型 属性名() default 默认值;
    }
    //举例
    public @interface MyBook{
        String name();
        String[] authors();
    }
    //使用：
    @MyBook(name="xx",authors={"a","b"})
    ```
    
    - 如果value属性只有一个，可以省略不写
    
- 元注解：注解的注解

    - 常用的两个（具体细节用的时候查……）
        - @Target：约束自定义注解只能在哪儿使用
        - @Retention：申明注解的生命周期

- 注解的解析

    - Annotation：注解的顶级接口，注解都是Annotation类型的对象

    ```java
    Class c = BookStore.class;	//得到类对象
    if(c.isAnnotationOresent(MyBook.class)){	//判断这个类上面是否存在这个注解
        MyBook book = (Mybook) c.getDeclaredAnnotaion(MyBook.class);
        String[] authors = book.authors();
    }
    ```

> 只操作注解了的对象 思路：
>
> 反射对象，获取全部方法/类/成员，判断是否存在某注解，如果存在则操作

## 动态代理

```java
/**
    public static Object newProxyInstance(ClassLoader loader,  Class< ?>[] interfaces, InvocationHandler h)
    参数一：类加载器，负责加载代理类到内存中使用。
    参数二：获取被代理对象实现的全部接口。代理要为全部接口的全部方法进行代理
    参数三：代理的核心处理逻辑
 */
public class ProxyUtil {
    /**
      生成业务对象的代理对象。
     * @param obj
     * @return
     */
    public static <T> T  getProxy(T obj) {
        // 返回了一个代理对象了
        return (T)Proxy.newProxyInstance(obj.getClass().getClassLoader(),
                obj.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        // 参数一：代理对象本身。一般不管
                        // 参数二：正在被代理的方法
                        // 参数三：被代理方法，应该传入的参数
                       long startTimer = System .currentTimeMillis();
                        // 马上触发方法的真正执行。(触发真正的业务功能)
                        Object result = method.invoke(obj, args);

                        long endTimer = System.currentTimeMillis();
                        System.out.println(method.getName() + "方法耗时：" + (endTimer - startTimer) / 1000.0 + "s");

                        // 把业务功能方法执行的结果返回给调用者
                        return result;
                    }
                });
    }
}
```

优点：

- 可以为任意接口类型的实现类对象做代理（使用泛型）也可以为接口本身做代理
- 可以为被代理对象的所有方法做代理
- 不改变方法源码的情况下，对方法功能的增强
- 简化编程、提高拓展性、提高效率

> :exclamation:
>
> AOP：面向切面编程，可以对业务逻辑的各个部分进行隔离。
>
> 通过预编译方式和运行期间动态代理实现程序功能的统一维护的一种技术，Spring框架重要内容，函数式编程的衍生范型。

## XML

> XML：可拓展标记语言，可以描述复杂的数据结构，常用于传输和存储数据

特点

- 纯文本，默认用UTF-8编码
- 可嵌套

使用场景：常被当成消息进行网络传输，或作为配置文件用于存储系统信息

语法规则：

- 文档声明 必须在 第一行
- XML的标签（元素）规则：
  - 必须存在根标签，有且只能有一个`<name></name>`
  - 标签必须成对出现
  - 特殊标签可以不成对，但要有结束标记`</br>`
  - 标签中可以定义属性，属性和标签名空格隔开，属性值用引号`<student id = "1" ></student>`
  - 要正确嵌套
- 其他组成
  - 注释信息`<!--   -->`
  - 特殊字符
    - `&lt` 小于
    - `&gt` 大于
    - `&amp` &
    - `&apos` 单引号
    - `&quot` 引号
- XML文件可以存在CDATA区：`<![CDATA[]]>`   (数据区，写任意内容都不冲突)

### 文档约束

> 限定XML文件中的标签和属性如何写

方式一：DTD（不能约束数据类型）（了解）

1. 编写DTD约束文档，文件后缀.dtd
2. 编写的XML文件中导入DTD约束文档
3. 按照约束编写XML

data.dtd

```dtd
<!ELEMENT 书架 (书+)>
<!ELEMENT 书 (书名,作者,售价)>
<!ELEMENT 书名 (#PCDATA)>
<!ELEMENT 作者 (#PCDATA)>
<!ELEMENT 售价 (#PCDATA)>
```

data.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE 书架 SYSTEM "data.dtd">
<书架>
    <书>
        <书名>精通JavaSE加强</书名>
        <作者>dlei</作者>
        <售价>很贵</售价>
    </书>
    <书>
        <书名></书名>
        <作者></作者>
        <售价></售价>
    </书>
    <书>
        <书名></书名>
        <作者></作者>
        <售价></售价>
    </书>
</书架>
```



方式二：schema（可以约束具体的数据类型）（了解）

- schema本就是xml文件，所以也可以被约束

data.xsd

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<schema xmlns="http://www.w3.org/2001/XMLSchema"
        targetNamespace="http://www.itcast.cn"
        elementFormDefault="qualified" >
    <!-- targetNamespace:申明约束文档的地址（命名空间）-->
    <element name='书架'>
        <!-- 写子元素 -->
        <complexType>
            <!-- maxOccurs='unbounded': 书架下的子元素可以有任意多个！-->
            <sequence maxOccurs='unbounded'>
                <element name='书'>
                    <!-- 写子元素 -->
                    <complexType>
                        <sequence>
                            <element name='书名' type='string'/>
                            <element name='作者' type='string'/>
                            <element name='售价' type='double'/>
                        </sequence>
                    </complexType>
                </element>
            </sequence>
        </complexType>
    </element>
</schema>
```

data.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<书架 xmlns="http://www.itcast.cn"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://www.itcast.cn data.xsd">
    <!-- xmlns="http://www.itcast.cn"  基本位置
         xsi:schemaLocation="http://www.itcast.cn books02.xsd" 具体的位置 -->
    <书>
        <书名>神雕侠侣</书名>
        <作者>金庸</作者>
        <售价>399.9</售价>
    </书>
    <书>
        <书名>神雕侠侣</书名>
        <作者>金庸</作者>
        <售价>19.5</售价>
    </书>

</书架>
```

### XML解析

SAX解析：一行行地读取XML，适合解析大文件

DOM解析：读取整个文件

常用解析工具之一：**dom4j**

#### dom4j

Node对象

- Document对象：整个XML文档
- Element对象：标签
- Attribute：属性
- Text：文本内容

```java
/**
   目标：学会使用dom4j解析XML文件中的数据。
 */
public class Dom4JHelloWorldDemo1 {
    @Test
    public void parseXMLData() throws Exception {
        // 1、创建一个Dom4j的解析器对象，代表了整个dom4j框架
        SAXReader saxReader = new SAXReader();

        // 2、把XML文件加载到内存中成为一个Document文档对象
        // Document document = saxReader.read(new File("xml-app\\src\\Contacts.xml")); // 需要通过模块名去定位
        // Document document = saxReader.read(new FileInputStream("xml-app\\src\\Contacts.xml"));

        // 注意: getResourceAsStream中的/是直接去src下寻找的文件
        InputStream is = Dom4JHelloWorldDemo1.class.getResourceAsStream("/Contacts.xml");
        Document document = saxReader.read(is);

        // 3、获取根元素对象
        Element root = document.getRootElement();
        System.out.println(root.getName());

        // 4、拿根元素下的全部子元素对象(一级)
        // List<Element> sonEles =  root.elements();
        List<Element> sonEles =  root.elements("contact");
        for (Element sonEle : sonEles) {
            System.out.println(sonEle.getName());
        }

        // 拿某个子元素
        Element userEle = root.element("user");
        System.out.println(userEle.getName());

        // 默认提取第一个子元素对象 (Java语言。)
        Element contact = root.element("contact");
        // 获取子元素文本
        System.out.println(contact.elementText("name"));
        // 去掉前后空格
        System.out.println(contact.elementTextTrim("name"));
        // 获取当前元素下的子元素对象
        Element email = contact.element("email");
        System.out.println(email.getText());
        // 去掉前后空格
        System.out.println(email.getTextTrim());

        // 根据元素获取属性值
        Attribute idAttr = contact.attribute("id");
        System.out.println(idAttr.getName() + "-->" + idAttr.getValue());
        // 直接提取属性值
        System.out.println(contact.attributeValue("id"));
        System.out.println(contact.attributeValue("vip"));
    }
}
```

```xml
<?xml version="1.0" encoding="UTF-8"?>
<contactList>
    <contact id="1" vip="true">
        <name>   潘金莲  </name>
        <gender>女</gender>
        <email>panpan@itcast.cn</email>
    </contact>
    <contact id="2" vip="false">
        <name>武松</name>
        <gender>男</gender>
        <email>wusong@itcast.cn</email>
    </contact>
    <contact id="3" vip="false">
        <name>武大狼</name>
        <gender>男</gender>
        <email>wuda@itcast.cn</email>
    </contact>
    <user>
    </user>
</contactList>
```

### XML检索-Xpath

> 使用路径表达式来定位XML文档中的节点

```java
SAXReader saxReader = new SAXReader();	//创建解析器对象
Document document = saxReader.read(XPathDemo.class.getResourceAsStream("/Contacts.xml"));	//把XML加载成Document文档对象
//绝对路径检索全部节点
List<Node> nameNodes = document.selectNodes("/contactList/contact/name");
//相对路径（./子元素/子元素）（. 代表当前元素）
Element root = doucument.getRootElement();
List<Node> nameNodes = root.selectNodes("./contact/name");
//全文检索
List<Node> nameNodes = document.selectNodes("//name");

for(Node nameNode : nameNodes){
    Element nameEle = (Element) nameNode;
    System.out.println(nameEle.getTextTrim());
}
//属性查找
//@属性名称	在全文检索属性对象
//元素[@属性名称]	在全文检索包含该属性的元素对象
//元素[@属性名称=值] 在全文检索包含该属性、且属性值等于该值的元素对象
List<Node> nameNodes = document.selectNodes("//@id");
```

## 设计模式

### 工厂模式

- 对象通过工厂的方法创建返回，工厂的方法可以为该对象进行加工和数据注入
- **可以实现类与类之间的解耦**

### 装饰模式

创建一个新类，包装原始类，在不改变原类的基础上，动态得拓展类的功能（例：字节流->转换流->缓冲流）

