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
        <form class="registration_form" >
            <div class="registration_table">
                <ul>
                    <li>
                        <span class="sign">*</span>
                        <label >用户角色</label>
                        <input type="radio" name="role" id="teacher" checked ><label for="teacher">教师</label>
                        <input type="radio" name="role" id="student" ><label for="student">学生</label>
                    </li>
                    <li>
                        <span class="sign">*</span>
                        <label >学号/工号</label>
                        <td><input type="text" aria-required="true" id="Username" name="username" onblur="username_check()" autocomplete="off"></td>
                        <span class="error username_void" id="username_void_message">该字段不能为空！</span>
                        <span class="error username_void" id="username_error_message">输入格式不正确，请检查输入！</span>
                    </li>
                    <li>
                        <span class="sign">*</span>
                        <label >姓名</label>
                        <td><input type="text" aria-required="true" id="name" name="name" onblur="name_check()" autocomplete="new-password" > </td>
                        <span class="error void" id="name_void_message">该字段不能为空！</span>
                    </li>
                    <li>
                        <span class="sign">*</span>
                        <label >身份证号</label>
                        <td><input type="text" aria-required="true" id="id" name="id" onblur="id_check()" autocomplete="off"></td>
                        <span class="error void" id="id_void_message">该字段不能为空！</span>
                    </li>
                    <li>
                        <span class="sign">(选填)</span>
                        <label >手机号</label>
                        <td><input type="text" id="phone_number" name="phone_number" onblur="phone_check()" autocomplete="new-password"></td>
                        <span class="error" id="phone_error_message">输入格式不正确，请检查输入！</span>
                    </li>
                    <li>
                        <span>(选填)</span>
                        <label >邮箱</label>
                        <td><input type="text" id="email" name="email" autocomplete="off"></td>
                    </li>
                    <div class="btn">
                        <button class="submit" >提交</button>
                    </div>
                    <a href="@{/userManage.html}" class="return" >返回</a>
                </ul>

            </div>
        </form>
    </div>

    </body>
</template>

 <script>
        function username_check(){
        const usernameObj=document.getElementById("Username");
        const username_voidObj=document.getElementById("username_void_message");
        const username_errorObj=document.getElementById("username_error_message");
        if(usernameObj.value.length==0)
    {
        username_voidObj.style.display="inline";
    }
        else
        username_voidObj.style.display="none"
        if(isNaN(usernameObj.value))
        username_errorObj.style.display="inline";
        else
        username_errorObj.style.display="none";
    }
        function name_check(){
        const nameObj=document.getElementById("name");
        const name_voidObj=document.getElementById("name_void_message");
        if(nameObj.value.length==0)
        name_voidObj.style.display="inline";
        else
        name_voidObj.style.display="none"
    }
        function id_check(){
        const idObj=document.getElementById("id");
        const id_voidObj=document.getElementById("id_void_message");
        if(idObj.value.length==0)
        id_voidObj.style.display="inline";
        else
        id_voidObj.style.display="none"
    }
        function phone_check(){
        const phoneObj=document.getElementById("phone_number");
        const phone_errorObj=document.getElementById("phone_error_message");
        if(phoneObj.value.length!=0&&phoneObj.value.length!=11||isNaN(phoneObj.value))
        phone_errorObj.style.display="inline";
        else
        phone_errorObj.style.display="none"
    }
        function submit_check()
        {

        }
</script>


<style scoped>
    @import "../assets/css/addUser.css";
</style>
