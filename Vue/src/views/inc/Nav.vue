<template>
    <nav class="nav1">
        <div class="bannerarea">
          <div id="shouye" @click="mainpage()">首页</div>
            <el-dropdown placement="bottom-end">
                <span class="el-dropdown-link">
               <span id="role">{{this.navtable.id}}&nbsp;&nbsp;&nbsp;&nbsp;|
                 <el-icon class="el-icon--right">
                        <arrow-down />
                 </el-icon>&nbsp;&nbsp;{{this.navtable.name}}&nbsp;
                  {{this.navtable.role}}</span>

                </span>
                <template #dropdown>
                    <el-dropdown-menu>
                        <el-dropdown-item @click="profile()" >个人中心</el-dropdown-item>
                        <el-dropdown-item @click="logout()" divided>退出登录</el-dropdown-item>
                    </el-dropdown-menu>
                </template>
            </el-dropdown>
        </div>
    </nav>
</template>

<script src="../../assets/js/nav.js" type="text/javascript">

</script>

<style>
@import "../../assets/css/nav.css";
</style>
