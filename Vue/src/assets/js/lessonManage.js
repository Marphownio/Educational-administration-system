import Nav from "@/views/inc/Nav.vue";
import request from "@/utils/request";
import {reactive} from "vue";
import {set} from "core-js/internals/task";


export default {
    name: "userManage",
    components:{
        Nav
    },
    data(){
        let numbercheck=(rule,value,callback)=>{
            if(isNaN(value))
            {
                callback(new Error('输入只能为数字'));
                return false;
            }
            else if(value<=0)
            {
                callback(new Error('输入需大于0'));
                return false;
            }
            else return true;
        };
        return{
            addcourse:false,
            checked: false,
            updatecourse:false,
            checkcourse:false,
            schooldata:[],
            teacherdata:[],
            majordata:[],
            buildingData:[],
            classroomData:[],
            dayofweekData:[
                {
                    name:"周一",
                    value:"MONDAY"
                },
                {
                    name:"周二",
                    value:"TUESDAY"
                },
                {
                    name:"周三",
                    value:"WEDNESDAY"
                },
                {
                    name:"周四",
                    value:"THURSDAY"
                },
                {
                    name:"周五",
                    value:"FRIDAY"
                },
                {
                    name:"周六",
                    value:"SATURDAY"
                },
                {
                    name:"周日",
                    value:"SUNDAY"
                }
            ],
            ruleForm1: {
                arrangement: [{
                    classArrangementId: '',
                    buildingId: '',
                    building:[],
                    classroom:[],
                    classroomId:'',
                    classTimeId:'',
                    classTime:[],
                    dayOfWeek:'',
                }],
                openToMajors:[{
                 majorId: ''
                }],
                 courseCategory:''
            },
            ruleForm2:{

            },
            rule:({
                arrangement:[
                    {
                        required: true,
                        message: '课程安排不能为空',
                        trigger: 'change'
                    },
                ],
                openToMajors:[
                    {
                        required: true,
                        message: '请选择选课开放专业',
                        trigger: 'change'
                    },
                ]
            }),
            editRules1 :({
                courseNumber: [
                    {
                        required: true,
                        message: '请输入课程代码',
                        trigger: 'blur'
                    },
                    {validator: numbercheck,trigger: 'blur'}
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
                        trigger: 'change',
                    },
                ],
                majorId: [
                    {
                        required: true,
                        message: '请选择所属专业',
                        trigger: 'change',
                    },
                ],
                teacherId:[
                    {
                        required: true,
                        message: '请选择任课教师',
                        trigger: 'change',
                    },
                ],
                classHour:[
                    {
                        required: true,
                        message: '请输入学时',
                        trigger: 'blur',
                    },
                    {validator: numbercheck,trigger: 'blur'}
                ],
                credit: [
                    {
                        required: true,
                        message: '请选择学分',
                        trigger: 'change',
                    },
                    {validator: numbercheck,trigger: 'blur'}
                ],
                capacity:[
                    {
                        required: true,
                        message: '请输入选课容量',
                        trigger: 'blur',
                    },
                    {validator: numbercheck,trigger: 'blur'}
                ],
            }),
            tableData:[

            ],
            academicData:[

            ],
            applicationData:[
                {
                },
            ],
            courseCategoryData:[
                {
                    courseName:'（无）',
                    credit:'',
                    schoolId:'',
                    majorId: ''
                }
            ],
            timeData:[

            ],
            majorListData:[

            ]
        }
    },

    mounted() {
        this.getSchool();
        this.getTeacher();
        this.getBuilding();
        this.getTime();
        this.getacademicterm();
        this.getcourseCategory();
        this.getMajorList();
    },
    created(){
        this.getCourseForm();
        this.getapplicationData();
    },
    methods:{
        refresh(){
            this.ruleForm1= {};
        },
        fillclassArrangementId(){
            for(let i=0;i<Object.keys(this.ruleForm1.arrangement).length;i++)
            {
                let classtime=[];
                this.ruleForm1.arrangement[i].building=JSON.parse(this.ruleForm1.arrangement[i].buildingId);
                this.ruleForm1.arrangement[i].classroom=JSON.parse(this.ruleForm1.arrangement[i].classroomId);
                this.ruleForm1.arrangement[i].classArrangementId=i;
                classtime.push({classTimeId:1});
                this.ruleForm1.arrangement[i].classTime=classtime;
            }
        },
        selectAll() {
            this.ruleForm1.openToMajors = [];
            if (this.checked) {
                this.majorListData.forEach((item) => { this.ruleForm1.openToMajors.push({majorId:item.majorId})});
            } else {
                this.ruleForm1.openToMajors = [];
            }
            this.$emit('update:updateMultipleSelect',this.ruleForm1.openToMajors);
        },
        changeSelect(val) {
            if (val.length === this.majorListData.length) {
                this.checked = true;
            } else {
                this.checked = false;
            }
            this.$emit('update:updateMultipleSelect',this.ruleForm1.openToMajors);
        },
        getMajorList(){
            request.get("/major/list").then(res=>{
                this.majorListData=res;
            })
        },
        fillData(){
          this.ruleForm1.courseName=this.ruleForm1.courseCategory.courseName;
          this.ruleForm1.schoolId=this.ruleForm1.courseCategory.school.schoolId;
          this.ruleForm1.majorId=this.ruleForm1.courseCategory.major.majorId;

        },
        getcourseCategory(){
            request.get("/courseCategory/list").then(res=>{
                this.courseCategoryData=res;
            })
        },
        getacademicterm(){
            request.get("/admin/academicYearAndTerm").then(res=>{
                this.academicData=res;
            })
        },
        getapplicationData(){
            request.get("/teacher/application/list").then(res=>{
                this.applicationData=res;
                for(let i=0;i<Object.keys(this.applicationData).length;i++)
                {
                    if(this.applicationData[i].school!==null)
                    {
                        this.applicationData[i].schoolName=this.applicationData[i].school.schoolName;
                        this.applicationData[i].schoolId=this.applicationData[i].school.schoolId;
                    }
                    if(this.applicationData[i].major!==null)
                    {
                        this.applicationData[i].majorName=this.applicationData[i].major.majorName;
                        this.applicationData[i].majorId=this.applicationData[i].major.majorId;
                    }
                    if(this.applicationData[i].teacher!==null)
                    {
                        this.applicationData[i].teacherName=this.applicationData[i].teacher.username;
                        this.applicationData[i].teacherId=this.applicationData[i].teacher.userId;
                    }
                }
            })
        },
        getTime:function(){
          request.get("/classTime/list").then(res=>{
              this.timeData=res;
          })
        },
        getBuilding:function(){
            request.get("/building/list").then(res=>{
                this.buildingData=res;
            })
        },
        getClassroom:function(index,id){
            request.get("/building/classrooms",{
                params:{
                    buildingId:id
                }
            }).then(res=>{
                this.classroomData[index]= res;
            })
            this.ruleForm1.arrangement[index].classroomId=null;
        },
        getMajor:function(){
            request.get("/school/majors",{
                params:{
                    schoolId:this.ruleForm1.schoolId
                }
            }).then(res=>{
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
            request.get("/teacher/list").then(res=>{
                this.teacherdata= res;
            })
        },
        passApplication(id){
            request.delete("/admin/application",{
                params:{
                    applicationId:id,
                    operation:true
                }
            })
            this.$message({
                showClose: true,
                message: '操作成功',
                type: 'success',
            });
            this.getapplicationData();
        },
        rejectApplication(id){
            request.delete("/admin/application",{
                params:{
                    applicationId:id,
                    operation:false
                }
            })
            this.$message({
                showClose: true,
                message: '操作成功',
                type: 'success',
            });
            this.getapplicationData();
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
        remove(item){
            const index = this.ruleForm1.arrangement.indexOf(item)
            if (index !== -1) {
                this.ruleForm1.arrangement.splice(index, 1)
            }
        },
        addarrangement(){
            this.ruleForm1.arrangement.push({
                classArrangementId: "",
                buildingId: '',
                classroomId: '',
                classTimeId:'',
                dayofweek:''
            })
        },

        submitaddcourse(){
            this.fillclassArrangementId();
            console.log(this.ruleForm1);
            this.$refs.ruleForm1.validate((valid,error)=>{
                if(valid){
                    let params ={};
                    let classArrangements=[];
                    let openToMajors=[];
                    for(let i=0;i<Object.keys(this.ruleForm1.arrangement).length;i++)
                    {
                        classArrangements.push(this.ruleForm1.arrangement[i]);
                    }
                    for(let i=0;i<Object.keys(this.ruleForm1.openToMajors).length;i++)
                    {
                        openToMajors.push(this.ruleForm1.openToMajors[i]);
                    }
                    params.courseNumber=this.ruleForm1.courseNumber;
                    params.courseId= this.ruleForm1.courseNumber;
                    params.academicYear= this.academicData.toString().slice(0,9);
                    params.term= this.academicData.toString().slice(9,10);
                    params.courseCategory= {
                        'courseCategoryId':1,
                        'courseName': this.ruleForm1.courseName,
                        'classHour': this.ruleForm1.classHour,
                        'credit': this.ruleForm1.credit,
                        'major': {'majorId': this.ruleForm1.majorId},
                        'school': {'schoolId': this.ruleForm1.schoolId}
                    }
                    params.teacher= {'teacherId':this.ruleForm1.teacherId};
                    params.classArrangements= classArrangements;
                    params.openToMajors=  openToMajors;
                    params.capacity=this.ruleForm1.capacity;
                    console.log(params);
                    console.log(JSON.stringify(params));
                    if(this.ruleForm1.introduction==='')
                        params.introduction= '该课程暂无描述信息';
                    else
                        params.introduction=this.ruleForm1.introduction;
                    this.$axios({
                        headers:{'Content-Type':'application/json'},
                        type:'application/json',
                        method: 'post',
                        url:'/api/course/add',
                        data:JSON.stringify(params)
                    }).then(res=>{
                        if(res.data==='SUCCESS'){
                            this.$message({
                                showClose: true,
                                message: '操作成功',
                                type: 'success',
                            });
                            this.addcourse = false;
                            this.getCourseForm()
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
                    console.log(error);
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
                    params.append('courseTime', this.ruleForm1.courseTime);
                    params.append('coursePlace', this.ruleForm1.coursePlace);
                    params.append('capacity', this.ruleForm1.capacity);
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
                                });
                                this.getCourseForm()
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
                    request.post("/course/batchimport",this.fileTemp).then(res=>{
                        if(res.data==='FAILED')
                        {
                            this.$message({
                                "showClose": true,
                                "message": '上传失败',
                                "type": 'fail',
                            });
                            this.getUserForm()
                        }
                        if(res.data==='SUCCESS')
                        {
                            this.$message({
                                "showClose": true,
                                "message": '操作成功',
                                "type": 'success',
                            });
                            this.getUserForm()
                        }
                    })
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
        editHandle(obj){
            this.updatecourse=true;
            this.ruleForm1=obj;
            console.log(this.ruleForm1)
            request.get("/school/majors",{
                params:{
                    schoolId:this.ruleForm1.schoolId
                }
            }).then(res=>{
                this.majordata= res;
            })
        },
        delHandle(id){
            this.$axios.delete("/api/course/"+id).then(res=> {
                if(res.data==='SUCCESS'){
                    this.$message({
                        showClose: true,
                        message: '操作成功',
                        type: 'success',
                    });
                    this.getCourseForm()
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

