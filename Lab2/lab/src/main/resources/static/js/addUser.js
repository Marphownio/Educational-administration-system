function username_check(){
    const usernameObj=document.getElementById("id");
    const username_voidObj=document.getElementById("username_void_message");
    const username_errorObj=document.getElementById("username_error_message");
    if(usernameObj.value.length==0)
    {
        username_voidObj.style.display="inline";
        return -1;
    }
    else
    {
        username_voidObj.style.display = "none"
        return 1;
    }
    if(isNaN(usernameObj.value))
    {
        username_errorObj.style.display="inline";
        return -1;
    }
    else
    {
        username_errorObj.style.display="none";
        return 1;
    }
}
function name_check(){
    const nameObj=document.getElementById("name");
    const name_voidObj=document.getElementById("name_void_message");
    if(nameObj.value.length==0)
    {
        name_voidObj.style.display="inline";
        return -1;
    }
    else
    {
        name_voidObj.style.display="none"
        return 1;
    }
}
function id_check(){
    const idObj=document.getElementById("idNumber");
    const id_voidObj=document.getElementById("id_void_message");
    if(idObj.value.length==0)
    {
        id_voidObj.style.display="inline";
        return -1;
    }
    else
    {
        id_voidObj.style.display="none"
        return 1;
    }
}
function phone_check(){
    const phoneObj=document.getElementById("phoneNumber");
    const phone_errorObj=document.getElementById("phone_error_message");
    if(phoneObj.value.length!=0&&phoneObj.value.length!=11||isNaN(phoneObj.value))
    {
        phone_errorObj.style.display="inline";
        return -1;
    }
    else
    {
        phone_errorObj.style.display="none"
        return 1;
    }
}
function submit_check()
{
    if(username_check()==-1||name_check()==-1||id_check()==-1||phone_check()==-1)
    {
        return false;
    }
    else
    {
        return true;
    }
}

