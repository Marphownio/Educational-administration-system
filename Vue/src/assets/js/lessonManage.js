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
        let capacitycheck=(rule,value,callback)=>{
            for(let i=0;i<Object.keys(this.ruleForm1.arrangement).length;i++)
            {
                if(value>this.ruleForm1.arrangement[i].classroom.capacity)
                {
                    callback(new Error('课程容量不能大于教室容量'));
                    return false;
                }
            }
            return true;
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
                    classTimeId:[],
                    classTimes:[],
                    dayOfWeek:'',
                }],
                openToMajors:[

                ],
                 courseCategory:''
            },
            ruleForm2:{

            },
            rule:({
                openToMajors:[
                    {
                        required: true,
                        message: '请选择选课开放专业',
                        trigger: 'change'
                    },
                ],
                arrangement:[
                    {
                        required: true,
                        message: '课程安排不能为空',
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
                    {validator: numbercheck,trigger: 'blur'},
                    {validator: capacitycheck,trigger: 'blur'}
                ],
            }),
            tableData:[
                {
                    children:[]
                }
            ],
            academicData:[

            ],
            applicationData:[
                {
                },
            ],
            courseCategoryData:[

            ],
            timeData:[

            ],
            majorListData:[

            ],
            courseData:[]
        }
    },

    mounted() {
        this.getSchool();
        this.getTeacher();
        this.getBuilding();
        this.getTime();
        this.getacademicterm();
        this.getMajorList();
        this.getcourseCategory();
    },
    created(){
        this.getapplicationData();
    },
    methods:{
        refresh(){
            this.ruleForm1= {
                arrangement: []
            };
        },
        fillclassArrangementId(){
            for(let i=0;i<Object.keys(this.ruleForm1.arrangement).length;i++)
            {
                this.ruleForm1.arrangement[i].building={'buildingId':this.ruleForm1.arrangement[i].buildingId};
                this.ruleForm1.arrangement[i].classroom= {'classroomId':this.ruleForm1.arrangement[i].classroom.classroomId,'capacity':this.ruleForm1.arrangement[i].classroom.capacity};
                this.ruleForm1.arrangement[i].classArrangementId=i;
                this.ruleForm1.arrangement[i].classTimes=[];
                for(let j=0;j<Object.keys(this.ruleForm1.arrangement[i].classTimeId).length;j++)
                    this.ruleForm1.arrangement[i].classTimes.push({'classTimeId':this.ruleForm1.arrangement[i].classTimeId[j]});
            }
        },
        selectAll() {
            if (this.ruleForm1.openToMajors.length < this.majorListData.length) {
                this.ruleForm1.openToMajors = [];
                this.majorListData.map((item) => {
                    this.ruleForm1.openToMajors.push(item.majorId)
                })
                this.ruleForm1.openToMajors.unshift('全选')
            } else {
                this.ruleForm1.openToMajors = [];
            }
        },
        changeSelect(val) {
            if (!val.includes('全选') && val.length === this.majorListData.length) {
                this.ruleForm1.openToMajors.unshift('全选')
            } else if (val.includes('全选') && (val.length - 1) < this.majorListData.length) {
                this.ruleForm1.openToMajors = this.ruleForm1.openToMajors.filter((item) => {
                    return item !== '全选'
                })
            }
        },
        removeTag(val) {
            if (val === '全选') {
                this.ruleForm1.openToMajors = [];
            }
        },
        getMajorList(){
            request.get("/major/list").then(res=>{
                this.majorListData=res;
            })
        },
        fillData(){
          this.ruleForm1.courseName=this.ruleForm1.courseCategory.courseName;
          this.ruleForm1.schoolId=this.ruleForm1.courseCategory.school.schoolId;
          request.get("/school/majors",{
                params:{
                    schoolId:this.ruleForm1.schoolId
                }
            }).then(res=>{
                this.majordata= res;
            })
          this.ruleForm1.majorId=this.ruleForm1.courseCategory.major.majorId;
          this.ruleForm1.classHour=this.ruleForm1.courseCategory.classHour;
          this.ruleForm1.credit=this.ruleForm1.courseCategory.credit;
        },
        changeCategory(){
            this.ruleForm1.courseCategory='';
        },
        getcourseCategory(){
            request.get("/courseCategory/list").then(res=>{
                for(let i=0;i<Object.keys(res).length;i++)
                {
                    let coursecategory={
                        'classHour': res[i].classHour,
                        'courseCategoryId': res[i].courseCategoryId,
                        'courseName': res[i].courseName,
                        'credit': res[i].credit,
                        'major':{
                            'introduction': res[i].major.introduction,
                            'majorId': res[i].major.majorId,
                            'majorName': res[i].major.majorName
                        },
                        'school':{
                            'introduction': res[i].school.introduction,
                            'schoolId': res[i].school.schoolId,
                            'schoolName': res[i].school.schoolName
                        }
                    }
                    this.courseCategoryData.push(coursecategory);
                }
                this.getCourseForm();
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
        getcourse(id,i){
            request.get("/courseCategory/courses",{
            params:{
                courseCategoryId:id
            }
            }).then(res=>{
                console.log(res);
                this.courseData[i]= res;
                for(let j=0;j<Object.keys(this.courseData[i]).length;j++)
                {
                    this.courseData[i][j].courseName=this.courseCategoryData[i].courseName;
                    this.courseData[i][j].courseCategoryId=this.courseCategoryData[i].courseCategoryId;
                    this.courseData[i][j].schoolName=this.courseCategoryData[i].school.schoolName;
                    this.courseData[i][j].schoolId=this.courseCategoryData[i].school.schoolId;
                    this.courseData[i][j].majorName=this.courseCategoryData[i].major.majorName;
                    this.courseData[i][j].majorId=this.courseCategoryData[i].major.majorId;
                    this.courseData[i][j].classHour=this.courseCategoryData[i].classHour;
                    this.courseData[i][j].credit=this.courseCategoryData[i].credit;
                    this.courseData[i][j].teacherName=this.courseData[i][j].teacher.username;
                    this.courseData[i][j].teacherId=this.courseData[i][j].teacher.userId;
                    let arrangementstr='';
                    for(let k=0;k<Object.keys(this.courseData[i][j].classArrangements).length;k++)
                    {
                        let times='';
                        for(let l=0;l<Object.keys(this.courseData[i][j].classArrangements[k].classTimes).length;l++)
                        {
                            times=times.concat(this.courseData[i][j].classArrangements[k].classTimes[l].classTimeId+',');
                        }
                        console.log(this.courseData[i][j]);
                        console.log(this.courseData[i][j].classArrangements[k]);
                        console.log(times);
                        arrangementstr=arrangementstr.concat(this.courseData[i][j].classArrangements[k].building.buildingName+','+
                            this.courseData[i][j].classArrangements[k].classroom.classroomId+','+
                            times+
                            JSON.stringify(this.courseData[i][j].classArrangements[k].dayOfWeek).replace(/"/g,"")+';')
                    }
                    this.courseData[i][j].arrangement=arrangementstr;
                }
                this.tableData[i]= {
                    'courseCategoryId': this.courseCategoryData[i].courseCategoryId,
                    'courseName': this.courseCategoryData[i].courseName,
                    'schoolName': this.courseCategoryData[i].school.schoolName,
                    'schoolId': this.courseCategoryData[i].school.schoolId,
                    'majorName': this.courseCategoryData[i].major.majorName,
                    'majorId': this.courseCategoryData[i].major.majorId,
                    'classHour': this.courseCategoryData[i].classHour,
                    'credit': this.courseCategoryData[i].credit,
                    children: this.courseData[i]
                }
            })
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
            for(let i=0;i<Object.keys(this.courseCategoryData).length;i++)
            {
                if(this.courseCategoryData[i]!==null)
                {
                    this.getcourse(this.courseCategoryData[i].courseCategoryId,i);
                }
            }
        },
        remove(item){
            if(Object.keys(this.ruleForm1.arrangement).length>1)
            {
                const index = this.ruleForm1.arrangement.indexOf(item)
                if (index !== -1) {
                    this.ruleForm1.arrangement.splice(index, 1)
                }
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
                        if(this.ruleForm1.openToMajors[i]!=="全选")
                            openToMajors.push({'majorId':this.ruleForm1.openToMajors[i]});
                    }
                    params.courseNumber=this.ruleForm1.courseNumber;
                    params.courseId= this.ruleForm1.courseNumber;
                    params.academicYear= this.academicData.toString().slice(0,9);
                    params.term= this.academicData.toString().slice(9,10);
                    params.courseCategory= {
                        'courseCategoryId':this.ruleForm1.courseNumber,
                        'courseName': this.ruleForm1.courseName,
                        'classHour': this.ruleForm1.classHour,
                        'credit': this.ruleForm1.credit,
                        'major': {'majorId': this.ruleForm1.majorId},
                        'school': {'schoolId': this.ruleForm1.schoolId}
                    }
                    params.teacher= {'userId':this.ruleForm1.teacherId};
                    params.classArrangements= classArrangements;
                    params.openToMajors=  openToMajors;
                    params.capacity=this.ruleForm1.capacity;
                    if(typeof this.ruleForm1.introduction==="undefined"||(typeof this.ruleForm1.introduction==="string"&&this.ruleForm1.introduction.length===0))
                        params.introduction= "该课程暂无描述信息";
                    else
                        params.introduction=this.ruleForm1.introduction;
                    console.log(params);
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
                            this.$router.go(0);
                        }
                        else if(res.data==='EXIST')
                        {
                            this.$message({
                                showClose: true,
                                message: '该课程代码已存在',
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
                                this.getcourseCategory()
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
            request.get("/school/majors",{
                params:{
                    schoolId:this.ruleForm1.schoolId
                }
            }).then(res=>{
                this.majordata= res;
            })
        },
        delHandle(id){
            this.$axios.delete("/api/course/delete/"+id).then(res=> {
                if(res.data==='SUCCESS'){
                    this.$message({
                        showClose: true,
                        message: '操作成功',
                        type: 'success',
                    });
                    this.getcourseCategory()
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

