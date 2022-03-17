function teacher_checked()
{

}

function id_check(){
    const idObj=document.getElementById("id");
    const id_voidObj=document.getElementById("id_void_message");
    const id_errorObj=document.getElementById("id_error_message");
    const id_TlObj=document.getElementById("id_Tl_message");
    const id_SlObj=document.getElementById("id_Sl_message");
    const input_role = document.getElementById("teacher").checked;
    if(idObj.value.length==0)
    {
        id_voidObj.style.display="inline";
        id_SlObj.style.display="none";
        id_TlObj.style.display="none";
        id_errorObj.style.display="none";
        return -1;
    }
    else if(isNaN(idObj.value))
    {
        id_voidObj.style.display="none";
        id_errorObj.style.display="inline";
        return -1;
    }
    else if(input_role==true)
    {
        id_SlObj.style.display="none";
        id_voidObj.style.display="none";
        id_errorObj.style.display="none";
        if(idObj.value.length!=8)
        {
            id_TlObj.style.display="inline";
            return -1;
        }
        else
        {
            id_TlObj.style.display="none";
            return 1;
        }
    }
    else
    {
        id_TlObj.style.display="none";
        id_voidObj.style.display="none";
        id_errorObj.style.display="none";
        if(idObj.value.length!=6)
        {
            id_SlObj.style.display="inline";
            return -1;
        }
        else
        {
            id_SlObj.style.display="none";
            return 1;
        }
    }
}
function name_check(){
    const nameObj=document.getElementById("name");
    const name_voidObj=document.getElementById("name_void_message");
    const name_errorObj=document.getElementById("name_error_message");
    if(nameObj.value.length==0)
    {
        name_errorObj.style.display="none";
        name_voidObj.style.display="inline";
        return -1;
    }
    else
    {
        name_voidObj.style.display="none"
        const regu = "^[a-zA-Z\u4e00-\u9fa5]+$";
        const re = new RegExp(regu);
        if (nameObj.value.search(re) != -1){
            name_errorObj.style.display="none";
            return 1;
        } else {
            name_errorObj.style.display="inline";
            return -1;
        }
    }
}
function idNumber_check(){
    const idNumberObj=document.getElementById("idNumber");
    const idNumber_voidObj=document.getElementById("idNumber_void_message");
    const idNumber_errorObj=document.getElementById("idNumber_error_message");
    if(idNumberObj.value.length==0)
    {
        idNumber_voidObj.style.display="inline";
        return -1;
    }
    else if(idNumberObj.value.length!=18||isNaN(idNumberObj.value))
    {
        idNumber_voidObj.style.display="none";
        idNumber_errorObj.style.display="inline";
        return -1;
    }
    else
    {
        idNumber_errorObj.style.display="none";
        return 1;
    }
}
function phone_check(){
    const phoneObj=document.getElementById("phoneNumber");
    const phone_errorObj=document.getElementById("phone_error_message");
    if(phoneObj.value.length!=0&&(phoneObj.value.length!=11||isNaN(phoneObj.value)||phoneObj.value.substring(0,1)!=1))
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
function email_check(){
    const emailObj=document.getElementById("email");
    const email_errorObj=document.getElementById("email_error_message");
    const emailtest=/^[\w\-\.]+@[a-z0-9]+(\-[a-z0-9]+)?(\.[a-z0-9]+(\-[a-z0-9]+)?)*\.[a-z]{2,4}$/i;
    const isRight=emailtest.test(emailObj.value)
    if(emailObj.value.length!=0&&isRight==false)
    {
        email_errorObj.style.display="inline";
        return -1;
    }
    else
    {
        email_errorObj.style.display="none"
        return 1;
    }
}
function submit_check()
{
    if(id_check()==-1||name_check()==-1||idNumber_check()==-1||phone_check()==-1||email_check()==-1)
    {
        return false;
    }
    else
    {
        return true;
    }
}

