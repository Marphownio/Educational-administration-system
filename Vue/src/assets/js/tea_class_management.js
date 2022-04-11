import Nav from "@/views/inc/Nav.vue";
import request from "@/utils/request";
import ALERTMSG from "@/assets/js/alert";

export default {
    name: "teaclassmanage",
    components:{
        Nav
    },
    data(){
        return{
            ruleForm:{
                courseName: '',
                classTime:'',
                classPlace:'',
            },
            editRules:({
                courseName:[{required: true, message: '请输入新课程名称', trigger: 'blur'}],
                classTime:[{required: true, message: '请输入新上课时间', trigger: 'blur'}],
                classPlace:[{required: true, message: '请输入新上课地点', trigger: 'blur'}],
            }),
            dialogVisible:false,
            applicationform:{//12
                applicationId: '',
                courseName: '',
                classHour:'',
                credit:'',
                classTime:'',
                classPlace:'',
                introduction: '',
                majorId:'',
                schoolId: '',
                teacherId:'',
                capacity:'',
                type:''//ADD,DELETE,UPDATE
            },
            //第二个对话框
            ruleForm1:{//12
                applicationId: '',
                courseName: '',
                classHour:'',
                credit:'',
                classTime:'',
                classPlace:'',
                introduction: '',
                majorId:'',
                schoolId: '',
                teacherId:'',
                capacity:'',
                type:'ADD'//ADD,DELETE,UPDATE
            },
            editRules1:({
                applicationId:[{required: true, message: '请输入课程编号', trigger: 'blur'}],
                courseName:[{required: true, message: '请输入课程名称', trigger: 'blur'}],
                schoolId:[{required: true, message: '请输入开课院系', trigger: 'blur'} ],
                majorId:[{required: true, message: '请输入开课专业', trigger: 'blur'} ],
                classHour:[{required: true, message: '请输入课程学时', trigger: 'blur'} ],
                credit:[{required: true, message: '请输入课程学分', trigger: 'blur'}  ],
                teacherId:[{required: true, message: '请输入任课教师', trigger: 'blur'} ],
                classTime:[{required: true, message: '请输入上课时间', trigger: 'blur'} ],
                classPlace:[{required: true, message: '请输入上课地点', trigger: 'blur'} ],
                capacity:[{required: true, message: '请输入选课容量', trigger: 'blur'}  ],
            }),
            dialogVisible1:false,
            tableData :[],
            schooldata:[],
            teacherdata:[],
            majordata:[],
        }
    },
    mounted() {
        this.getSchool();
        this.getTeacher();
        this.getMajor();
    },
    created(){
       this.get_class_infor();
    },
    methods:{
        getMajor:function(){
            request.get("/school/majors",{
                params:{
                    schoolId:this.ruleForm1.schoolId
                }
            }).then(res=>{
                console.log(res);
                this.majordata= res;
            })
            this.ruleForm1.majorId=null;
        },
        getSchool:function(){
            request.get("/school/list").then(res=>{
                this.schooldata= res;
            })
        },
        getTeacher:function(){
            request.get("/user/teacher/list").then(res=>{
                this.teacherdata= res;
            })
        },
        apply_new_class(){
            this.$refs.ruleForm1.validate((valid) => {
                if (valid) {
                    const that =this;
                    this.applicationform.courseName=this.ruleForm1.courseName
                    this.applicationform.classTime=this.ruleForm1.classTime
                    this.applicationform.classPlace=this.ruleForm1.classPlace;
                    this.applicationform.applicationId=this.ruleForm1.applicationId;
                    this.applicationform.classHour=this.ruleForm1.classHour;
                    this.applicationform.credit=this.ruleForm1.credit;
                    this.applicationform.capacity=this.ruleForm1.capacity;
                    this.applicationform.introduction=this.ruleForm1.introduction;
                    this.applicationform.majorId=this.ruleForm1.majorId;
                    this.applicationform.schoolId=this.ruleForm1.schoolId;
                    this.applicationform.teacherId=this.ruleForm1.teacherId;
                    this.applicationform.type="ADD";
                    let applyclassform = new FormData();
                    for(let key in this.applicationform) {
                        applyclassform.append(key,this.applicationform[key]);
                    }
                    request.post("/application/add",applyclassform)
                        .then(function (response) {
                            ALERTMSG.show(that,true,"申请新课程成功!","success");

                        }, function (err) {
                            ALERTMSG.show(that,true,"申请新课程失败！请重新尝试！","error");
                            return false;
                        });
                }
                else{
                    return false;
                }
            })
        },
        edit_class_inform(row){//12
            this.dialogVisible=true;
            this.ruleForm.courseName=row.courseName;
            this.ruleForm.classTime=row.classTime;
            this.ruleForm.classPlace=row.classPlace;
            this.applicationform.applicationId=row.courseId;
            this.applicationform.classHour=row.classHour;
            this.applicationform.credit=row.credit;
            this.applicationform.capacity=row.capacity;
            this.applicationform.introduction=row.introduction;
            this.applicationform.majorId=row.major.majorId;
            this.applicationform.schoolId=row.school.schoolId;
            this.applicationform.teacherId=row.teacher.userId;
            this.applicationform.type="UPDATE";
        },
        submit_edit(){
            this.$refs.ruleForm.validate((valid) => {
                    if (valid) {
                        const that=this;
                        this.applicationform.courseName=this.ruleForm.courseName;
                        this.applicationform.classTime=this.ruleForm.classTime;
                        this.applicationform.classPlace=this.ruleForm.classPlace;
                        console.log(this.applicationform);
                        let editclassform = new FormData();
                        for(let key in this.applicationform) {
                            editclassform.append(key,this.applicationform[key]);
                        }
                        request.post("/application/add",editclassform)
                            .then(function (response) {
                                ALERTMSG.show(that,true,"课程编辑申请成功!","success");
                            }, function (err) {
                                ALERTMSG.show(that,true,"课程编辑申请失败！请重新尝试！","error");
                                return false;
                            });
                    }
                    else{
                        return false;
                    }
            })

        },
        get_class_infor(){
            const that =this;
            request.get("/course/list")
                .then(res=>{
                    this.tableData=res;
                },function (err) {
                    ALERTMSG.show(that,true,"课程信息获取失败！","error");
                    return false;
                });
        },
        tea_delete(row){
            const that=this;
            this.applicationform.applicationId=row.courseId;
            this.applicationform.courseName=row.courseName;
            this.applicationform.classHour=row.classHour;
            this.applicationform.credit=row.credit;
            this.applicationform.classTime=row.classTime;
            this.applicationform.classPlace=row.classPlace;
            this.applicationform.capacity=row.capacity;
            this.applicationform.introduction=row.introduction;
            this.applicationform.majorId=row.major.majorId;
            this.applicationform.schoolId=row.school.schoolId;
            this.applicationform.teacherId=row.teacher.userId;
            this.applicationform.type="DELETE";
            let deleteclassform = new FormData();
            for(let key in this.applicationform) {
                deleteclassform.append(key,this.applicationform[key]);
            }
            request.post("/application/add",deleteclassform)
                .then(function (response) {
                    ALERTMSG.show(that,true,"课程删除申请成功！","success");
                }, function (err) {
                    ALERTMSG.show(that,true,"课程删除申请失败！请重新尝试！","error");
                    return false;
                });
        },
    }
}
