import Nav from "@/views/inc/Nav.vue";

export default {
    name: "teaclassmanage",
    components:{
        Nav
    },
    data(){
        return{
            ruleForm:{
                changeclassname:'',
                changeclasstime:'',
                changeclassroom:''
            },
            editRules:({
                changeclassname:[{required: true, message: '请输入新课程名称', trigger: 'blur'}],
                changeclasstime:[{required: true, message: '请输入新上课时间', trigger: 'blur'}],
                changeclassroom:[{required: true, message: '请输入新上课地点', trigger: 'blur'}],
            }),
            dialogVisible:false,
            //第二个对话框
            ruleForm1:{
                newclassid:'',
                newclassname:'',
                newclassfaculty:'',
                newclasshours:'',
                newclasscredits:'',
                newclassteacher:'',
                newclassintroduction:'',
                newclasstime:'',
                newclassroom:'',
                newclasscapacity:''
            },
            editRules1:({
                newclassid:[{required: true, message: '请输入课程编号', trigger: 'blur'}],
                newclassname:[{required: true, message: '请输入课程名称', trigger: 'blur'}],
                newclassfaculty:[{required: true, message: '请输入开课院系', trigger: 'blur'} ],
                newclasshours:[{required: true, message: '请输入课程学时', trigger: 'blur'} ],
                newclasscredits:[{required: true, message: '请输入课程学分', trigger: 'blur'}  ],
                newclassteacher:[{required: true, message: '请输入任课教师', trigger: 'blur'} ],
                newclassintroduction:[{required: true, message: '请输入课程介绍', trigger: 'blur'}],
                newclasstime:[{required: true, message: '请输入上课时间', trigger: 'blur'} ],
                newclassroom:[{required: true, message: '请输入上课地点', trigger: 'blur'} ],
                newclasscapacity:[{required: true, message: '请输入选课容量', trigger: 'blur'}  ],
            }),
            dialogVisible1:false,




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
