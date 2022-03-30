<template>
    <head>
        <meta charset="UTF-8">
        <title>课程管理</title>
    </head>

    <body>

    <Nav></Nav>
    <div class="showList">  <!--showList命名是为了防止样式击穿，vue3中style的scoped属性不生效-->
        <li style="list-style-type:none"><el-button @click="openSelection" class="open">开放选课</el-button></li>
        <li style="list-style-type:none"><el-button @click="closeSelection" class="close">关闭选课</el-button></li>
    </div>
    </body>
</template>

<script src="../../../assets/js/selectManage.js" type="text/javascript">

</script>

<style>
  @import "../../../assets/css/selectManage.css";
</style>
