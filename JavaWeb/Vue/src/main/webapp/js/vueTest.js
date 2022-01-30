/*
    notice Vue
        - 常用指令
            - v-bind：为HTML标签绑定属性值，如设置href、css等
            - v-model：在表单元素上创建双向数据绑定
            - v-on：为HTML标签绑定事件
            - v-if：见html
            - v-for：见html
        - 生命周期 8个
            - mounted：挂载完成，Vue初始化成功，HTML页面渲染成功
                - 发送异步请求，加载数据
 */

/*创建Vue核心对象*/
new Vue({
    el: "#app",
    /*data:function (){
        return{
            username: ""
        }
    }*/
    //简化写法↓
    data() {
        return {
            url: "https://baidu.com",
            A: 1,
            array: ["aaa", "bbb", "ccc"],
        }
    },
    mounted() {
        /* notice 需要传递this*/
        let _this = this
        console.log("Vue挂载完毕，发送异步请求")
        axios.get("#").then(function (response) {
            console.log("发送异步请求结束")
            _this.A += 3;
        })
    },
    methods: {
        add() {
            this.A++
        },
        subtraction() {
            this.A--
        },
    },
})
