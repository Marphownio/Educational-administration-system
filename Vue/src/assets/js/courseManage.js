import Nav from "@/views/inc/Nav.vue";
import request from "@/utils/request";
import {reactive} from "vue";
import {set} from "core-js/internals/task";
import {computed} from "vue";


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
            updatecoursecategory:false,
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
                classArrangements:[{}],
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
                ]
            }),
            editRules1 :({
                courseCategoryNumber: [
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
                editRules2 :({
                    courseCategoryNumber:[
                        {
                            required: true,
                            message: '请输入课程编号',
                            trigger: 'blur',
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

            ],
            timeData:[

            ],
            majorListData:[

            ],
            courseData:[],
            search11: '',
            search12: '',
            search13: '',
            search14:'',
            search15:'',
            search21:'',
            search22:'',
            courseCategoryData1:[{}],
            courseCategoryDatashow:[{}],
            selectableDataShow: [{}],
            courseData1: [{}],
            courseData2: [{}],
            courseData3: [{}],
            courseData4: [{}],
            courseData5: [{}],
            courseDatashow:[{}],
            academicyear:'',
            term:'',
            academicyearData:[
                {year:"2019-2020"},
                {year:"2020-2021"},
                {year:"2021-2022"},
                {year:"2022-2023"},
                {year:"2023-2024"}
            ],
            termData:[
                {term:1},
                {term:2}
            ]
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
        this.getCourse();
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
        search1(){
            //if判断防止数据为空时报错
            if(this.courseData){
            this.courseData1 = computed(() =>
                this.courseData.filter(
                    (data) =>
                        !this.search11 ||
                        data.courseCategoryNumbershow.toLowerCase().includes(this.search11.toLowerCase())
                )
            );
            this.courseData2 = computed(() =>
                this.courseData1.filter(
                    (data) =>
                        !this.search12 ||
                        data.courseName.toLowerCase().includes(this.search12.toLowerCase())
                )
            );
            this.courseData3 = computed(() =>
                this.courseData2.filter(
                    (data) =>
                        !this.search13 ||
                        data.teacherName.toLowerCase().includes(this.search13.toLowerCase())
                )
            );
            this.courseData4 = computed(() =>
                this.courseData3.filter(
                    (data) =>
                        !this.search14 ||
                        data.time.toLowerCase().includes(this.search14.toLowerCase())
                )
            );
            this.courseDatashow = computed(() =>
                this.courseData4.filter(
                    (data) =>
                        !this.search15 ||
                        data.place.toLowerCase().includes(this.search15.toLowerCase())
                )
            );
            }
        },
        changeterm(){
            request.get("/course/list",{
                params:{
                    academicYear:this.academicyear,
                    term:this.term
                },
            }).then(res=>{
                this.courseData= res;
                this.fillcourse();
                this.search1();
            })
        },
        search2(){
            //if判断防止数据为空时报错
            if(this.courseCategoryData){
            this.courseCategoryData1 = computed(() =>
                this.courseCategoryData.filter(
                    (data) =>
                        !this.search21 ||
                        data.courseCategoryNumber.toLowerCase().includes(this.search21.toLowerCase())
                )
            );
            this.courseCategoryDatashow = computed(() =>
                this.courseCategoryData1.filter(
                    (data) =>
                        !this.search22 ||
                        data.courseName.toLowerCase().includes(this.search22.toLowerCase())
                )
            );
            }
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
            this.ruleForm1.courseCategoryNumber=this.ruleForm1.courseCategory.courseCategoryNumber;
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
                this.courseCategoryData=res;
                this.search2();
            })
        },

        getacademicterm(){
            request.get("/admin/academicYearAndTerm").then(res=>{
                this.academicData=res;
                this.academicyear=res.slice(0,9);
                this.term=res.slice(9,10);
            })
        },
        getapplicationData(){
            request.get("/teacher/application/list").then(res=>{
                this.applicationData=res;
                console.log(res);
                for(let i=0;i<Object.keys(this.applicationData).length;i++)
                {
                    let classarrangementstr='';
                    for(let k=0;k<Object.keys(this.applicationData[i].classArrangements).length;k++)
                    {
                        let times='';
                        let timesinorder=[];
                        timesinorder.length=Object.keys(this.applicationData[i].classArrangements[k].classTimes).length;
                        //将classTimes按从小到大放入timesinorder
                        for(let l=0;l<Object.keys(this.applicationData[i].classArrangements[k].classTimes).length;l++)
                        {
                            timesinorder[l]=this.applicationData[i].classArrangements[k].classTimes[l].classTimeId;
                        }
                        timesinorder=timesinorder.sort();
                        for(let l=0;l<Object.keys(timesinorder).length;l++)
                        {
                            times=times.concat(timesinorder[l]+',');
                        }
                        classarrangementstr=classarrangementstr.concat(this.applicationData[i].classArrangements[k].building.buildingName+','+
                            this.applicationData[i].classArrangements[k].classroom.classroomId+','+
                            times+
                            JSON.stringify(this.applicationData[i].classArrangements[k].dayOfWeek).replace(/"/g,"")+' ; ');
                    }
                    this.applicationData[i].classarrangement=classarrangementstr;
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
            this.ruleForm1.arrangement[index].classroom=null;
        },
        getMajor:function(id){
            request.get("/school/majors",{
                params:{
                    schoolId:id
                }
            }).then(res=>{
                this.majordata= res;
            })
            this.ruleForm1.majorId=null;
            this.ruleForm2.majorId=null;
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
            request.delete("/admin/application/teacher",{
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
            this.checkcourse=false;
        },
        showCourse(){
            request.get("/course/list")
        },
        getCourse(){
            request.get("/course/list").
            then(res=>{
                this.courseData= res;
                this.fillcourse();
                this.search1();
            })
        },
        fillcourse(){
            for(let j=0;j<Object.keys(this.courseData).length;j++)
            {
                this.courseData[j].courseName=this.courseData[j].courseCategory.courseName;
                this.courseData[j].courseCategoryNumber=this.courseData[j].courseCategory.courseCategoryNumber;
                this.courseData[j].courseCategoryNumbershow=this.courseData[j].courseCategoryNumber+'.'+this.courseData[j].courseNumber;
                this.courseData[j].courseCategoryId=this.courseData[j].courseCategory.courseCategoryId;
                this.courseData[j].schoolName=this.courseData[j].courseCategory.school.schoolName;
                this.courseData[j].schoolId=this.courseData[j].courseCategory.school.schoolId;
                this.courseData[j].majorName=this.courseData[j].courseCategory.major.majorName;
                this.courseData[j].majorId=this.courseData[j].courseCategory.major.majorId;
                this.courseData[j].classHour=this.courseData[j].courseCategory.classHour;
                this.courseData[j].credit=this.courseData[j].courseCategory.credit;
                this.courseData[j].teacherName=this.courseData[j].teacher.username;
                this.courseData[j].teacherId=this.courseData[j].teacher.userId;
                let classarrangementstr='';
                let timestr='';
                let placestr='';
                for(let k=0;k<Object.keys(this.courseData[j].classArrangements).length;k++)
                {
                    let times='';
                    let timesinorder=[];
                    timesinorder.length=Object.keys(this.courseData[j].classArrangements[k].classTimes).length;
                    //将classTimes按从小到大放入timesinorder
                    for(let l=0;l<Object.keys(this.courseData[j].classArrangements[k].classTimes).length;l++)
                    {
                        timesinorder[l]=this.courseData[j].classArrangements[k].classTimes[l].classTimeId;
                    }
                    timesinorder=timesinorder.sort();
                    for(let l=0;l<Object.keys(timesinorder).length;l++)
                    {
                        times=times.concat(timesinorder[l]+',');
                    }
                    classarrangementstr=classarrangementstr.concat(this.courseData[j].classArrangements[k].building.buildingName+','+
                        this.courseData[j].classArrangements[k].classroom.classroomId+','+
                        times+
                        JSON.stringify(this.courseData[j].classArrangements[k].dayOfWeek).replace(/"/g,"")+' ; ');
                    timestr=timestr.concat(times+
                        JSON.stringify(this.courseData[j].classArrangements[k].dayOfWeek).replace(/"/g,"")+' ; ');
                    placestr=placestr.concat(this.courseData[j].classArrangements[k].building.buildingName+','+
                        this.courseData[j].classArrangements[k].classroom.classroomId+' ; ');
                }
                this.courseData[j].classarrangement=classarrangementstr;
                this.courseData[j].time=timestr;
                this.courseData[j].place=placestr;
            }
        },
        rejectApplication(id){
            request.delete("/admin/application/teacher",{
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
            this.checkcourse=false;
        },
        remove(item){
            if(Object.keys(this.ruleForm1.arrangement).length>1)
            {
                const index = this.ruleForm1.arrangement.indexOf(item)
                if (index !== -1) {
                    this.ruleForm1.arrangement.splice(index, 1);
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
            console.log(this.ruleForm1);
            this.fillclassArrangementId();
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
                        'courseCategoryNumber':this.ruleForm1.courseCategoryNumber,
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
                            //this.$router.go(0);
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
                                message: '课程代码不能与其他课程类相同',
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
            this.fillclassArrangementId();
            this.$refs.ruleForm1.validate(valid=>{
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
                    params.courseId= this.ruleForm1.courseId;
                    params.academicYear= this.academicData.toString().slice(0,9);
                    params.term= this.academicData.toString().slice(9,10);
                    params.courseCategory= {
                        'courseCategoryNumber':this.ruleForm1.courseCategoryNumber,
                        'courseCategoryId':this.ruleForm1.courseCategoryId,
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
                        method: 'put',
                        url:'/api/course/update',
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
        submitupdatecoursecategory(){
            this.$refs.ruleForm2.validate((valid,error)=>{
                if(valid){
                    let params ={};
                    params.courseCategoryId= this.ruleForm2.courseCategoryId;
                    params.courseCategoryNumber=this.ruleForm2.courseCategoryNumber;
                    params.courseName= this.ruleForm2.courseName;
                    params.classHour= this.ruleForm2.classHour;
                    params.credit= this.ruleForm2.credit;
                    params.major={'majorId': this.ruleForm2.majorId};
                    params.school= {'schoolId': this.ruleForm2.schoolId};
                    params.capacity=this.ruleForm2.capacity;
                    console.log(params);
                    this.$axios({
                        headers:{'Content-Type':'application/json'},
                        type:'application/json',
                        method: 'put',
                        url:'/api/courseCategory/update',
                        data:JSON.stringify(params)
                    }).then(res=>{
                            if(res.data==='SUCCESS'){
                                this.$message({
                                    showClose: true,
                                    message: '操作成功',
                                    type: 'success',
                                });
                                this.updatecoursecategory = false;
                                this.$router.go(0);
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
                this.ruleForm1.arrangement=[];
                let opentomajors=[];
                for(let i=0;i<Object.keys(this.ruleForm1.openToMajors).length;i++)
                {
                    opentomajors[i]=this.ruleForm1.openToMajors[i].majorId;
                }
                this.ruleForm1.openToMajors=opentomajors;
                for(let i=0;i<Object.keys(this.ruleForm1.classArrangements).length;i++)
                {
                    let time=[];
                    for(let j=0;j<Object.keys(this.ruleForm1.classArrangements[i].classTimes).length;j++)
                    {
                        time[j]=this.ruleForm1.classArrangements[i].classTimes[j].classTimeId;
                    }
                    this.ruleForm1.arrangement[i]={
                        'building':this.ruleForm1.classArrangements[i].building,
                        'buildingId':this.ruleForm1.classArrangements[i].building.buildingId,
                        'classroom':this.ruleForm1.classArrangements[i].classroom,
                        'dayOfWeek':this.ruleForm1.classArrangements[i].dayOfWeek,
                        'classTimeId': time
                    }
                    request.get("/building/classrooms",{
                        params:{
                            buildingId:this.ruleForm1.classArrangements[i].building.buildingId
                        }
                    }).then(res=>{
                        this.classroomData[i]= res;
                    })
                }
        },
        editHandle2(row){
            this.updatecoursecategory=true;
            this.ruleForm2=row;
            this.ruleForm2.schoolId=row.school.schoolId;
            this.ruleForm2.majorId=row.major.majorId;
            request.get("/school/majors",{
                params:{
                    schoolId:this.ruleForm2.school.schoolId
                }
            }).then(res=>{
                this.majordata= res;
            })
        },
        delHandle(obj){
                this.$axios.delete("/api/course/delete/"+obj.courseId).then(res=> {
                    if(res.data==='SUCCESS'){
                        this.$message({
                            showClose: true,
                            message: '操作成功',
                            type: 'success',
                        });
                        this.$router.go(0);
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
        delHandle2(row){
            this.$axios.delete("/api/courseCategory/delete/"+row.courseCategoryId).then(res=> {
                if(res.data==='SUCCESS'){
                    this.$message({
                        showClose: true,
                        message: '操作成功',
                        type: 'success',
                    });
                    this.$router.go(0);
                }
                else if(res.data==='FAILED')
                {
                    this.$message({
                        showClose: true,
                        message: '该课程类下仍有课程，无法删除',
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

