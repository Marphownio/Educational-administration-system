import Nav from "@/views/inc/Nav.vue";
export default {
    name: "userManage",
    components:{
        Nav
    },
    data(){
        return{
            tableData:[
                {
                    building:'二号教学楼',
                    classroom:'H201'
                },
               //该部分后续通过定义接口实现，定义接口后可定义树形结构、直接修改表单值
            ]
        }
    },
    methods:{

    }
}
