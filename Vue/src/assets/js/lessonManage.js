import Nav from "@/views/inc/Nav.vue";
import request from "@/utils/request";

export default {
    name: "userManage",
    components:{
        Nav
    },
    data(){
        return{
            addcourse:false,
            updatecourse:false,
            checkcourse:false,
            schooldata:[],
            teacherdata:[],
            majordata:[],
            ruleForm1:{
                introduction:''
            },
            ruleForm2:{

            },
            editRules1 :({
                courseId: [
                    {
                        required: true,
                        message: '请输入课程代码',
                        trigger: 'blur'
                    },
                ],
                courseName: [
                    {
                        required: true,
                        message: '请输入课程名',
                        trigger: 'blur',
                    },
                ],
                schoolId: [
                    {
                        required: true,
                        message: '请选择开课院系',
                        trigger: 'blur',
                    },
                ],
                majorId: [
                    {
                        required: true,
                        message: '请选择所属专业',
                        trigger: 'blur',
                    },
                ],
                teacherId:[
                    {
                        required: true,
                        message: '请选择任课教师',
                        trigger: 'blur',
                    },
                ],
                classHour:[
                    {
                        required: true,
                        message: '请输入学时',
                        trigger: 'blur',
                    },
                ],
                credit: [
                    {
                        required: true,
                        message: '请选择学分',
                        trigger: 'change',
                    },
                ],
                classTime:[
                    {
                        required: true,
                        message: '请输入上课时间',
                        trigger: 'blur',
                    },
                ],
                classPlace:[
                    {
                        required: true,
                        message: '请输入上课地点',
                        trigger: 'blur',
                    },
                ],
                capacity:[
                    {
                        required: true,
                        message: '请输入选课容量',
                        trigger: 'blur',
                    },
                ],
            }),
            tableData:[
                {
                    id: 1,
                    name: '课程1',
                },
                {
                    id: 2,
                    name: '课程2',
                },
            ],
            applyData:[
                {
                    request:2,
                    id: 1,
                    name: '课程1',
                },
                {
                    request:1,
                    id: 2,
                    name: '课程2',
                },
            ]
        }
    },
    mounted() {
        this.getSchool();
        this.getTeacher();
    },
    created(){
        this.getCourseForm();
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
        getCourseForm(){
            request.get("/course/list").then(res=>{
                this.tableData=res;
                for(let i=0;i<Object.keys(this.tableData).length;i++)
                {
                    if(this.tableData[i].school!==null)
                    {
                        this.tableData[i].schoolName=this.tableData[i].school.schoolName;
                        this.tableData[i].schoolId=this.tableData[i].school.schoolId;
                    }
                    if(this.tableData[i].teacher!==null)
                    {

                        this.tableData[i].teacherName=this.tableData[i].teacher.username;
                        this.tableData[i].teacherId=this.tableData[i].teacher.userId;
                    }
                    if(this.tableData[i].major!==null)
                    {

                        this.tableData[i].majorName=this.tableData[i].major.majorName;
                        this.tableData[i].majorId=this.tableData[i].major.majorId;
                    }
                }
            })
        },
        submitaddcourse(){
            this.$refs.ruleForm1.validate(valid=>{
                if(valid){
                    let params = new URLSearchParams();
                    params.append('courseId', this.ruleForm1.courseId);
                    params.append('courseName', this.ruleForm1.courseName);
                    params.append('school', JSON.parse(this.ruleForm1.schoolId));
                    params.append('teacher', JSON.parse(this.ruleForm1.teacherId));
                    params.append('major', JSON.parse(this.ruleForm1.majorId));
                    params.append('classHour', this.ruleForm1.classHour);
                    params.append('credit', this.ruleForm1.credit);
                    params.append('classTime', this.ruleForm1.classTime);
                    params.append('classPlace', this.ruleForm1.classPlace);
                    params.append('capacity', this.ruleForm1.capacity);
                    if(this.ruleForm1.introduction==='')
                        params.append('introduction', "该课程暂无介绍");
                    else
                        params.append('introduction', this.ruleForm1.introduction);
                    this.$axios({
                        method: 'post',
                        url:'/api/course/add',
                        data:params,
                    }).then(res=>{
                        if(res.data==='SUCCESS'){
                            this.$message({
                                showClose: true,
                                message: '操作成功',
                                type: 'success',
                                onClose: () => {
                                    this.getCourseForm()
                                }
                            });
                            this.addcourse = false;
                        }
                        else if(res.data==='EXIST')
                        {
                            this.$message({
                                showClose: true,
                                message: '该专业代码已存在',
                                type: 'error',
                            });
                        }
                        else if(res.data==='FAILED')
                        {
                            this.$message({
                                showClose: true,
                                message: '操作失败',
                                type: 'error',
                            });
                        }
                        }
                    )
                }
                else{
                    this.$nextTick(() => {
                        this.scrollToTop(this.$refs.ruleForm1.$el)
                    })
                }
            })
        },
        submitupdatecourse(){
            this.$refs.ruleForm1.validate(valid=>{
                if(valid){
                    let params = new URLSearchParams();
                    params.append('courseId', this.ruleForm1.courseId);
                    params.append('courseName', this.ruleForm1.courseName);
                    params.append('school', JSON.parse(this.ruleForm1.schoolId));
                    params.append('teacher', JSON.parse(this.ruleForm1.teacherId));
                    params.append('major', JSON.parse(this.ruleForm1.majorId));
                    params.append('classHour', this.ruleForm1.classHour);
                    params.append('credit', this.ruleForm1.credit);
                    params.append('classTime', this.ruleForm1.classTime);
                    params.append('classPlace', this.ruleForm1.classPlace);
                    params.append('capacity', this.ruleForm1.capacity);
                    if(this.ruleForm1.introduction==='')
                        params.append('introduction', "该课程暂无介绍");
                    else
                        params.append('introduction', this.ruleForm1.introduction);
                    this.$axios({
                        method: 'put',
                        url:'/api/course/update',
                        data:params,
                    }).then(res=>{
                            if(res.data==='SUCCESS'){
                                this.$message({
                                    showClose: true,
                                    message: '操作成功',
                                    type: 'success',
                                    onClose: () => {
                                        this.getCourseForm()
                                    }
                                });
                                this.updatecourse = false;
                            }
                            else if(res.data==='FAILED')
                            {
                                this.$message({
                                    showClose: true,
                                    message: '操作失败',
                                    type: 'error',
                                });
                            }
                        }
                    )
                }
                else{
                    this.$nextTick(() => {
                        this.scrollToTop(this.$refs.ruleForm1.$el)
                    })
                }
            })
        },
        handleChange(file) {
            this.fileTemp = file.raw
            if (this.fileTemp) {
                if ((this.fileTemp.type === '.csv') || (this.fileTemp.type === 'application/vnd.ms-excel')) {
                    this.importcsv(file.raw)
                } else {
                    this.$message({
                        type: 'warning',
                        message: '附件格式错误，请删除后重新上传！'
                    })
                }
            } else {
                this.$message({
                    type: 'warning',
                    message: '请上传附件！'
                })
            }
        },
        importcsv (obj) {
            Papa.parse(obj, {
                complete (results) {
                    console.log(results)//调试查看csv文件的数据
                    let data = []
                    //遍历csv文件中的数据，存放到data中
                    for (let i = 0; i < results.data.length; i++) {
                        let obj = {}
                        obj.number = results.data[i][0]
                        obj.name = results.data[i][1]
                        obj.nameRemark = results.data[i][2]
                        obj.index = results.data[i][3]
                        data.push(obj)
                    }
                    data.splice(0, 1)//将数组第一位的表格名称去除
                    console.log('data', data)
                    this.tableData = data//将数据放入要展示的表格中
                }
            })
        },
        editHandle(obj){
            this.updatecourse=true;
            this.ruleForm1=obj;
        },
        delHandle(id){
            this.$axios.delete("/api/course/"+id).then(res=> {
                if(res.data==='SUCCESS'){
                    this.$message({
                        showClose: true,
                        message: '操作成功',
                        type: 'success',
                        onClose: () => {
                            this.getCourseForm()
                        }
                    });
                }
                else if(res.data==='FAILED')
                {
                    this.$message({
                        showClose: true,
                        message: '操作失败',
                        type: 'error',
                    });
                }
            })
        },
        scrollToTop (node) {
            const ChildHasError = Array.from(node.querySelectorAll('.is-error'))
            if (!ChildHasError.length) throw new Error('有错误，但是找不到错误位置')
        }
    }
}

