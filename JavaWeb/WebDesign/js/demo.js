/*
    notice 语法&输出
        - 区分大小写
        - 每行结尾的分号可有可无
        - 大括号表示代码块
        - 输出语句
            - windows.alert()   弹出警告框
            - document.write()  写入HTML输出
            - console.log()     写入浏览器控制台
 */

// alert("alert:hello js");
document.write("write:hello js");
console.log("log:hello js");

/*
    notice 变量
        JavaScript: 弱类型语言，变量可以存放不同类型的值
        - 规则
             - 由 字母、数字、下划线、美元符号$组成
             - 不能数字开头
             - 建议驼峰命名
        - 关键字
             - var：作用域：全局    可以重复定义
             - let：类似var，但声明的变量只在let关键字所在的代码块有效，且不能重复声明
             - const：声明一个只读常量
 */

/*
    notice 数据类型
        - typeof运算符可以获取数据类型
        - 原始类型
             - number：数字（整数、小数、NaN（Not a number））
             - string：字符、字符串
             - boolean
             - null
             - undefined：声明的变量未初始化时，默认值
        - 引用类型
 */
var age = 22;
document.write("<br>数据类型：" + typeof age)

/*
    运算符
        - 一元运算符：++ --
        - 算数运算符：+ - * / %
        - 赋值运算符：= += -= ……
        - notice 关系运算符：> < >= <= != == ===
        - 逻辑运算符：&& || !
        - 三元运算符：条件表达式?true_value:false_value
 */
var age1 = 20;
var age2 = "20"
document.write("<br>等于（会进行类型转换）：" + (age1 == age2) + "    全等于：" + (age1 === age2))

/*
    notice 类型转换
        - 其他类型转number
            1. string：按照字符串的字面值，转为数字。如果字面值不是数字，转为NaN
            2. boolean：true->1 false->0
        - 其他类型转boolean
            1. number: 0和NaN转换为false，其他为true
            2. string：空字符串为false
            3. null：false
            4. undefined：false
 */
var a = "abc"
a = +a
document.write("<br>前面加“正号”转为数字 " + a + "，数据类型：" + typeof a)
a = "abc"
a = parseInt(a);
document.write("<br>用parseInt()转换：" + a + "," + typeof a)

var flag = +false
document.write("<br>false的number值：" + flag)

//健壮性判断
var str = "aaa"
if (str != null && str.length > 0) {
    //……
}
//↑等效于↓
if (str) {
    //……
}

/*
    流程控制
        - if、switch、for、while、do…while
 */

/*
    notice 函数
        - function关键字,两种定义格式
        - 形参和返回值不需要定义类型
        - 可以传递多个参数，多余参数不接收
 */
function add(a, b) {
    return a + b
}

var add2 = function (a, b) {
    return a + b
};

document.write("<br>add(1,2,3): " + add(1, 2, 3))


/*
    JavaScript对象
    Browser对象
    DOM对象
 */

/*
    JavaScript对象举例：Array数组
        - 类似Java集合，长度、类型可变
        - 定义：两种方式
        - 属性：length
        - notice 方法：push添加、splice(a,b)从a开始删除b个元素
 */
var arr = new Array(1, 2, 3)
var arr = [1, 2, 3]
document.write("<br>访问数组：" + arr[0])
arr[6] = "aaa"
document.write("<br>类型长度可变：" + arr)

arr.push(true)
arr.splice(1, 1)
console.log(arr.length)
for (let i = 0; i < arr.length; i++) {
    document.write("<br>第" + i + "个元素是：" + arr[i] + "，数据类型：" + typeof arr[i])
}

/*
    notice 自定义对象
 */
var person = {
    name: "张三", age: 22, eat: function () {
        document.write("<br>干饭ing")
    }
}
document.write("<br>" + person.age)
person.eat()

/*
    notice BOM(Browser Object Model)浏览器对象
        - 组成
            - Window
            - History
            - Navigator
            - Screen
            - Location
        - 举例：windows
            - 直接使用window，其中window.可以省略
            - 属性：获取其他BOM对象
                - History
                - Navigator
                - Screen
                - Location
            - 方法
                - alert()
                - confirm()
                - setInterval() 按照指定周期（单位毫秒）调用函数或计算表达式
                - setTimeout() 指定毫秒后调用函数或计算表达式
 */
setTimeout(function () {
    var flag = confirm("确认跳转百度？");
    if (flag) {
        location.href = "https://baidu.com";
    }
}, 30000)
// var th = 0;
// setInterval("alert(\"循环执行\"+(th++))", 2000)

/*
    notice DOM(Document Object Model)文本对象
         - JavaScript通过DOM操作HTML
         - W3C DOM标准分为
            1. 核心DOM，针对任何结构化文档的标准模型
            2. XML DOM
            3. HTML DOM：将标签封装为对象
 */

/*fixme jQuery
$('button1').click(function () {
    var img = document.getElementById('image');
    img.src = 'https://bkimg.cdn.bcebos.com/pic/91529822720e0cf3acdfc0280046f21fbe09aa3d?x-bce-process=image/resize,m_lfit,w_536,limit_1/format,f_jpg';
    img.style.width = '200';
})
$('body').on('click', 'button2', () => {
    var img = document.getElementById('image');
    img.src = 'https://img1.baidu.com/it/u=3981672482,3582814781&fm=253&fmt=auto&app=120&f=JPEG?w=650&h=393';
});
*/
function img1() {
    var img = document.getElementById('image');
    img.src = 'https://t7.baidu.com/it/u=4162611394,4275913936&fm=193&f=GIF';
    img.style.height = '600px'
}

document.getElementById('button2').onclick = function () {
    var img = document.getElementById('image');
    img.src = 'https://img1.baidu.com/it/u=3981672482,3582814781&fm=253&fmt=auto&app=120&f=JPEG?w=650&h=393';
}
/*
    notice DOM事件监听
        - onclick   点击
        - onblur    失去焦点
        - ………
 */
// 验证填写内容
document.getElementById('username').oninput = checkUsername;
// 获取表单对象
var regFrom = document.getElementById("register");
//提交前验证填写内容
regFrom.onsubmit = () => {
    return checkUsername();
}
// 刷新后回显
// notice js获取url中参数的方法
var params = new URLSearchParams(window.location.search)
var username = params.get('username')
if (username) {
    document.getElementById("msg1").innerText = "上次提交：" + username;
}

/*
    notice 正则表达式
        - 定义
            - 直接量   两个/包围，不用引号
            - 创建RegExp对象
        - 方法
            - test(str)
        - 语法
            - 单个字符
                - ^     表示开始
                - $     表示结束
                - []    表示某范围内的单个字符     [0-9]表示单个数字
                - .     任意单个字符，除了换行和行结束
                - \w    单词字符（字母、数字、下划线）
                - \d    数字字符
            - 量词
                - +     至少一个
                - *     零个或多个
                - ?     零个或一个
                - {x}   x个
                - {m,}  最少m个
                - {m,n} 至少m，至多n个
 */
function checkUsername() {
    // notice getElementsByName返回的是数组
    let username = document.getElementsByName('username')[0].value.trim();
    //8~16位字母、数字或下划线
    let regExp = /^\w{8,16}$/
    if (regExp.test(username)) {
        document.getElementById("msg1").innerText = "";
        return true;
    }
    document.getElementById("msg1").innerText = "长度不符";
    return false;
    // return username.length >= 8 && username.length <= 16;
}
