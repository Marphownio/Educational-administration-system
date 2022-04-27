<template>
    <head>
        <meta charset="UTF-8">
        <title>课程管理</title>
    </head>

    <body>

    <Nav></Nav>
    <div class="showlist">
        <li style="list-style-type:none"><el-button @click="openSelection" class="open" :disabled="isDisabled1">开放选课</el-button></li>
        <li style="list-style-type:none"><el-button @click="closeSelection" class="close" :disabled="isDisabled2">关闭选课</el-button></li>
    </div>
    </body>
</template>

<script src="../../../assets/js/selectManage.js" type="text/javascript">

</script>

<style src="../../../assets/css/selectManage.css" scoped>
</style>
