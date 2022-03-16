function testpassword(){
    const password1Obj=document.getElementById("newPassword1");
    const password1=password1Obj.value;
    const warn_msgObj=document.getElementById("warn_msg");
    const pat0=/^[\w-_]{6,32}$/;//密码的规则
    const pat1=/^[a-zA-Z0-9]{6,32}$/;//密码的规则
    const pat2=/^[0-9-_]{6,32}$/;//密码的规则
    const pat3=/^[-_a-zA-Z]{6,32}$/;//密码的规则
    if(!pat1.test(password1)&&!pat2.test(password1)&&!pat3.test(password1)&&!pat0.test(password1)){
        warn_msgObj.innerText="*密码需长度为6-32位，且字母，数字或者特殊字符（-_）至少包含两种";
        return false;
    }
    else {
        warn_msgObj.innerText="&nbsp;";
        return true;
    }
}