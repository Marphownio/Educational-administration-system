import Nav from "@/views/inc/Nav.vue";
import request from "@/utils/request";

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
            applicationform:{
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
                introduction:[{required: true, message: '请输入课程介绍', trigger: 'blur'}],
                classTime:[{required: true, message: '请输入上课时间', trigger: 'blur'} ],
                classPlace:[{required: true, message: '请输入上课地点', trigger: 'blur'} ],
                capacity:[{required: true, message: '请输入选课容量', trigger: 'blur'}  ],
            }),
            dialogVisible1:false,
            tableData : [
                {//17
                    courseId: '1',
                    courseName: '1',
                    classHour:'1',
                    credit:'1',
                    classTime:'1',
                    classPlace:'1',
                    introduction: '1',
                    major:'1',
                    majorName:'0',
                    majorId:'2',
                    school: '1',
                    schoolName:'0',
                    schoolId:'2',
                    teacher:'1',
                    teacherName:'0',
                    teacherId:'3',
                    capacity:'1',
                },
            ]
        }
    },
    created(){
       this.get_class_infor();
    },
    methods:{
        apply_new_class(){
            this.$refs.ruleForm1.validate((valid) => {
                if (valid) {
                    console.log(this.ruleForm1);
                    request.post("/application/add",this.ruleForm1)
                        .then(function (response) {
                            alert("申请新课程成功！");
                        }, function (err) {
                            alert("申请新课程失败！请重新尝试！");
                            return false;
                        });
                }
                else{
                    return false;
                }
            })
        },
        edit_class_inform(row){
            this.dialogVisible=true;
            this.ruleForm.courseName=row.courseName;
            this.ruleForm.classTime=row.classTime;
            this.ruleForm.classPlace=row.classPlace;
            this.applicationform.applicationId=row.courseId;
            this.applicationform.classHour=row.classHour;
            this.applicationform.credit=row.credit;
            this.applicationform.capacity=row.capacity;
            this.applicationform.introduction=row.introduction;
            this.applicationform.majorId=row.majorId;
            this.applicationform.schoolId=row.schoolId;
            this.applicationform.teacherId=row.teacherId;
            this.applicationform.type="UPDATE";
        },
        submit_edit(){
            this.$refs.ruleForm.validate((valid) => {
                    if (valid) {
                        this.applicationform.courseName=this.ruleForm.courseName;
                        this.applicationform.classTime=this.ruleForm.classTime;
                        this.applicationform.classPlace=this.ruleForm.classPlace;
                        //console.log(this.applicationform);
                        request.post("/application/add",this.applicationform)
                            .then(function (response) {
                                alert("课程编辑申请成功！");
                            }, function (err) {
                                alert("课程编辑申请失败！请重新尝试！");
                                return false;
                            });
                    }
                    else{
                        return false;
                    }
            })

        },
        get_class_infor(){
            request.get("/course/list")
                .then(res=>{
                    this.tableData.courseId=res.courseId;
                    this.tableData.courseName=res.courseName;
                    this.tableData.classHour=res.classHour;
                    this.tableData.credit=res.credit;
                    this.tableData.classTime=res.classTime;
                    this.tableData.classPlace=res.classPlace;
                    this.tableData.introduction=res.introduction;
                    this.tableData.capacity=res.capacity;
                    this.tableData.major=res.major;
                    this.tableData.majorName=res.major.majorName;
                    this.tableData.majorId=res.major.majorId;
                    this.tableData.school=res.school;
                    this.tableData.schoolName=res.school.schoolName;
                    this.tableData.schoolId=res.school.schoolId;
                    this.tableData.teacher=res.teacher;
                    this.tableData.teacherName=res.username;
                    this.tableData.teacherId=res.userId;
                },function (err) {
                        alert("课程信息获取失败！");
                        return false;
                });
        },
        tea_delete(row){
            this.applicationform.applicationId=row.courseId;
            this.applicationform.courseName=row.courseName;
            this.applicationform.classHour=row.classHour;
            this.applicationform.credit=row.credit;
            this.applicationform.classTime=row.classTime;
            this.applicationform.classPlace=row.classPlace;
            this.applicationform.capacity=row.capacity;
            this.applicationform.introduction=row.introduction;
            this.applicationform.majorId=row.majorId;
            this.applicationform.schoolId=row.schoolId;
            this.applicationform.teacherId=row.teacherId;
            this.applicationform.type="DELETE";
            //console.log(this.applicationform);
            request.post("/application/add",this.applicationform)
                .then(function (response) {
                    alert("课程删除申请成功！")
                }, function (err) {
                    alert("课程删除申请失败！请重新尝试！");
                    return false;
                });
        },
    }
}
