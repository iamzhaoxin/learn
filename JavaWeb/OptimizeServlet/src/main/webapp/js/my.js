new Vue({
    el: "#login",
    data() {
        // let pathname = window.location.pathname;
        // console.log(pathname)
        // let projectName = pathname.substring(0, pathname.indexOf('/', 1));

        return {
            // projectName: projectName,
            id: "",
            id_msg: "",
            password: "",
            rememberAccount: false,
            rememberPassword: false,
        }
    },
    mounted() {
        this.getCookie();
    },
    methods: {
        /*提交登陆信息*/
        submit() {
            let loginData = {
                "id": this.id,
                "password": this.password,
                "rememberAccount": this.rememberAccount,
                "rememberPassword": this.rememberPassword,
            }
            axios.post("/user/login", loginData).then(function (response) {
                alert("或许登陆成功了")
            })
        },
        /*获取cookie，设置保存账号和密码*/
        getCookie() {
            let cookies = document.cookie.split('; ');
            for (let cookie in cookies) {
                let pair = cookie.split('=');
                if (pair[0] === 'id') {
                    this.id = pair[1];
                    this.rememberAccount = true;
                } else if (pair[0] === 'password') {
                    this.password = pair[1];
                    this.rememberPassword = true;
                }
            }
        },
    },
    watch: {
        /*验证id是否存在*/
        id: function (newVal, oldVal) {
            console.log(this.id)
            /*
                        let _this = this;
                        axios.post("http://localhost/OptimizeServlet_war/user/verifierById", "id=" + this.id).then(function (response) {
                            if (response.data !== true) {
                                console.log(response.data)
                                _this.id_msg = response.data;
                            }
                        })
            */
            //notice 不新建function时，不用传递this
            axios.post("http://localhost/OptimizeServlet_war/user/verifierById", "id=" + this.id).then(response => {
                if (response.data !== true) {
                    console.log(response.data)
                    this.id_msg = response.data;
                }
            })
        },
        rememberPassword: function (newVal, oldVal) {
            if (newVal === true) {
                this.rememberAccount = true;
            }
        },
        rememberAccount: function (newVal, oldVal) {
            if (newVal === false) {
                this.rememberPassword = false;
            }
        },
    }
})