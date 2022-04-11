import {ArrowDown} from "@element-plus/icons-vue";
import request from "@/utils/request";
import tokenmanage from "@/utils/Tokenmanage";
export default {
    name: "Nav",
    components: {ArrowDown},
    data()
    {
        return{
            navtable:{
                User:'',
                id:'',
                name:'',
                role:'',
            },
        }
    },
    created(){
        this.getUserInfo()
    },
    methods:{
        logout(){
            const that=this;
            request.post("/logout")
                .then(function(res){
                    if(res==="SUCCESS"){
                        alert("成功退出！");
                        tokenmanage.remove("token");
                        return that.$router.push({path: '/'});
                    }if(res==="FAILED"){
                        alert("退出失败！")
                        return false;
                    }
                })
        },
        profile(){
            const that=this;
            if(that.navtable.User.password==="fudan123456"){
                alert("请先完成密码重置！")
                return false;
            }
            else if(that.navtable.User.role==="TEACHER"){
                return that.$router.push({path: '/personalinfo'});
            }
            else if(that.navtable.User.role==="STUDENT"){
                return that.$router.push({path: '/personalinfo'});
            }
            else{
                return that.$router.push({path: '/index_admin'});
            }
        },
        getUserInfo(){
            const _this=this;
            request.get("/user/info")
                .then(function(res){
                    _this.navtable.User=res;
                    _this.navtable.id=res.userId;
                    _this.navtable.name=res.username;
                    if(res.role==="TEACHER"){
                        _this.navtable.role="老师"
                    }
                    else if(res.role==="STUDENT"){
                        _this.navtable.role="同学"
                    }
                    if(tokenmanage.get("token")==="1"){
                        _this.navtable.id="000000";
                        _this.navtable.name="管理员";
                        _this.navtable.role=""
                    }
                    },function (err){
                        _this.navtable.id="000000";
                        _this.navtable.name="管理员";
                        _this.navtable.role=""
                })
        },
        mainpage(){
            const that=this;
            if(that.navtable.User.password==="fudan123456"){
                alert("请先完成密码重置！")
                return false;
            }
            else if(that.navtable.User.role==="TEACHER"){
                return that.$router.push({path: '/index_teacher'});
            }
            else if(that.navtable.User.role==="STUDENT"){
                return that.$router.push({path: '/index_stu'});
            }
            else{
                return that.$router.push({path: '/index_admin'});
            }
        }
    }
}