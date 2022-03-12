<template>
    <nav class="nav">
        <div class="banner_area">
            <span>admin</span>           <!-- 显示账号 -->
            <span>|管理员</span>           <!-- 显示身份-->
            &nbsp;&nbsp;
            <button type="submit" name="首页">
                <span>首页</span>
            </button>
            &nbsp;&nbsp;
            <button type="submit" name="退出登录">
                <span>退出登录</span>
            </button>
        </div>
    </nav>
  <router-view/>
</template>

<style>


.nav{
    width: 100%;
    position: relative;
    height: 60px;
    background-color: rgb(255,255,255,0.3);
}
.nav .banner_area{
    padding-top:20px;
    float:right;
    padding-right:20px;
    vertical-align: middle;


}
</style>
<script>
</script>
