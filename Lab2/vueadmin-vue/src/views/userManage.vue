<template>
    <head>
        <meta charset="UTF-8">
        <title>教师/学生信息录入</title>
    </head>
    <body>
    <header>
    </header>
    <div class="void">
        <span></span>
    </div>
    <div class="showlist">
        <div class="showlist_header">
        <span class="role_list">
            <button class="teacher_list">教师列表</button>
            <button class="student_list">学生列表</button>
        </span>
            <span class="function_list">
            <!--<a href="addUser.vue" ><span class="add" id="Add">添加</span></a>-->
        </span>
        </div>
        <table class="showlist_content">
            <thead>
            <tr>
                <th>学号/工号</th>
                <th>姓名</th>
                <th>身份证号</th>
                <th>手机号</th>
                <th>邮箱</th>
            </tr>
            </thead>
        </table>
    </div>
    </body>
</template>
<style scoped>
    @import "../assets/css/userManage.css";
</style>
