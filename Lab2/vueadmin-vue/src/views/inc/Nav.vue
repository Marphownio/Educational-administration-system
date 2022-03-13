<template>
    <nav class="nav">
        <div class="banner_area">
            <el-dropdown>
                <span class="el-dropdown-link">
                {{userInfo.id}}|{{userInfo.role}}
                    <el-icon class="el-icon--right">
                        <arrow-down />
                    </el-icon>
                </span>
                <template #dropdown>
                    <el-dropdown-menu>
                        <el-dropdown-item>个人中心</el-dropdown-item>
                        <el-dropdown-item divided>退出登录</el-dropdown-item>
                    </el-dropdown-menu>
                </template>
            </el-dropdown>
            <router-link to="/">首页</router-link>
            <el-link href="/">首页</el-link>
        </div>
    </nav>
</template>

<script>
    export default {
        name: "Nav",
        data()
        {
            return{
                userInfo:{
                    role:"",
                    id:"",
                    name:"",
                    idNumber:"",
                    phoneNumber:"",
                    email:""
                }
            }
        }
        /*create(){
            this.getUserInfo()
        },
        methods(){
            getUserInfo(){
                this.$axios.get(url:"/sys/userInfo").then(res=>{
                    this.userInfo=res.data.data
                })
            }
        }*/
    }
</script>

<style scoped>
    .el-dropdown-link {
        cursor: pointer;
        color: black;
        display: flex;
        align-items: center;
    }

    .nav{
        width: 100%;
        position: relative;
        height: 60px;
        background-color: rgb(255,255,255,0.3);
    }
    .nav .banner_area{
        padding-top: 18px;
        padding-right: 50px;
        float:right;
        display: flex;
        width: 210px;
        justify-content: space-around;
        align-items: center;
        transform: scale(1.2);
    }
</style>
