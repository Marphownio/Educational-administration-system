import Nav from "@/views/inc/Nav.vue";

export default {
    name: "classselection",
    components:{
        Nav
    },
    data(){
        return{
            tableData : [
                {
                    classid: 'MATH2030001.01',
                    classname: '数学分析',
                    faculty:'数学系',
                    classhours:'每周6课时',
                    credit:'5',
                    teacher:'张三',
                    introduction: '计算机科学技术学院基础课',
                    time:'周一第6-7节，周三第3-4节，周五第1-2节课',
                    classroom: 'H3108',
                    capacity:'90',

                },
                {
                    classid: 'SOFT4000003.01',
                    classname: '离散数学',
                    faculty:'软件学院',
                    classhours:'每周3课时',
                    credit:'2',
                    teacher:'李四',
                    introduction: '计算机科学技术学院基础课',
                    time:'周二第3-5节',
                    classroom: 'HGX408',
                    capacity:'100',

                },
            ]
        }
    },
    created(){
    },
    methods:{
    }
}
