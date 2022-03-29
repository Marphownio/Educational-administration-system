const Mock=require('mockjs')

let Result={
    code:200,
    msg:'操作成功',
    data:null
}

Mock.mock('/userInfo','get',()=>{
    Result.data={
        id:"000000",
        name:"admin"
    }
    return Result
})

Mock.mock('/admin/user/list','get',()=>{
    Result.data={
        record:[
            {
                role: '教师',
                name: 'test',
                id:'12345678901',
                idNumber:'123456789012345678',
                phoneNumber:'12345678901',
                email:'12345678901@test.cn',
                state:'1'
            },
        ]
    }
    return Result
})

