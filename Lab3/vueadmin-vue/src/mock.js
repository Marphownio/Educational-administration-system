Mock.mock('/sys/menu/list','get',()=>{
    Result.data={
        record:[
            {
                role: '教师',
                name: 'test',
                id:'12345678901',
                idNumber:'123456789012345678',
                phoneNumber:'12345678901',
                email:'12345678901@fudan.edu.cn'
            },
        ]
    }
})

