function testpassword(){
    const password1Obj=document.getElementById("newPassword1");
    const password1=password1Obj.value;
    const warn_msgObj=document.getElementById("warn_msg");
    const pat0=/^[\w-_]{6,32}$/;//密码的规则0
    const pat1=/^[0-9]{6,32}$/;//密码的规则1
    const pat2=/^[-_]{6,32}$/;//密码的规则2
    const pat3=/^[a-zA-Z]{6,32}$/;//密码的规则3
    if(pat1.test(password1)||pat2.test(password1)||pat3.test(password1)){
        warn_msgObj.innerText="*密码需长度为6-32位，且字母，数字或者特殊字符（-_）至少包含两种";
        return false;
    }
    else {
        if(pat0.test(password1)){
            warn_msgObj.innerText="";
            return true;
        }
        else {
            warn_msgObj.innerText = "*密码需长度为6-32位，且字母，数字或者特殊字符（-_）至少包含两种";
            return false;
        }
    }
}

function WhetherSame(){
    const password1Obj=document.getElementById("newPassword1");
    const password1=password1Obj.value;
    const password2Obj=document.getElementById("newPassword2");
    const password2=password2Obj.value;
    if(password1==password2){
        return true;
    }
    else{
        const warn_msgObj=document.getElementById("warn_msg");
        warn_msgObj.innerText="*请两次输入的密码保持一致";
        return false;
    }
}

function submit_check(){
    if(testpassword()==true&&WhetherSame()==true) return true;
    else return false;
}