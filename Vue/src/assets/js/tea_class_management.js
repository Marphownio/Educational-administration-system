import Nav from "@/views/inc/Nav.vue";
import request from "@/utils/request";
import ALERTMSG from "@/assets/js/alert";
import {computed} from "vue";

export default {
    name: "teaclassmanage",
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
            ruleForm:{
                courseName: '',
                courseTime:'',
                coursePlace:'',
            },
            dialogVisible:false,
            applicationform:{//12
                applicationId: '',
                courseName: '',
                classHour:'',
                credit:'',
                courseTime:'',
                coursePlace:'',
                introduction: '',
                major:'',//实际传过去的值是id
                school:'',//实际传过去的值是id
                teacher:'',//实际传过去的值是id
                capacity:'',
                type:''//ADD,DELETE,UPDATE
            },
            //第二个对话框
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
                courseCategory:'',
                type:'ADD'
            },
            rule:({
                openToMajors:[
                    {
                        required: true,
                        message: '请选择选课开放专业',
                        trigger: 'change'
                    },
                ],
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
            dialogVisible1:false,
            selectableDataShow:[{}],
            selectableData1:[{}],
            selectableData2:[{}],
            selectableData:[],
            tableData :[],
            schooldata:[],
            majordata:[],
            academicData:[

            ],
            courseCategoryData:[

            ],
            timeData:[

            ],
            majorListData:[

            ],
            search11:'',
            search22:'',
            search33:'',
            teacherdata:[

            ]
        }
    },
    mounted() {
        this.getSchool();
        this.getBuilding();
        this.getTime();
        this.getTeacher();
        this.getacademicterm();
        this.getMajorList();
        this.getcourseCategory();
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
                this.majordata= res;
            })
            this.ruleForm1.majorId=null;
        },
        getTeacher:function(){
            const _this=this;
            request.get("/user/info")
                .then(function(res){
                        _this.teacherdata.teacherName=res.username;
                        _this.teacherdata.teacherId=res.userId;
                        _this.ruleForm1.teacherName=res.username;
                        _this.ruleForm1.teacherId=res.userId;
            })
        },
        getMajorList(){
            request.get("/major/list").then(res=>{
                this.majorListData=res;
            })
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
        getSchool:function(){
            request.get("/school/list").then(res=>{
                this.schooldata= res;
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
            this.ruleForm.arrangement[index].classroomId=null;
        },
        fillData(){
            this.ruleForm1.courseName=this.ruleForm1.courseCategory.courseName;
            console.log(this.ruleForm1.courseCategory);
            this.ruleForm1.courseCategoryNumber=this.ruleForm1.courseCategory.courseCategoryNumber;
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
        getacademicterm(){
            request.get("/admin/academicYearAndTerm").then(res=>{
                this.academicData=res;
            })
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
                        'courseCategoryNumber':res[i].courseCategoryNumber,
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
            })
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
        search(){
            this.selectableData1 = computed(() =>
                this.selectableData.filter(
                    (data) =>
                        !this.search11 ||
                        data.courseCategoryNumbershow.toLowerCase().includes(this.search11.toLowerCase())
                )
            );
            this.selectableData2 = computed(() =>
                this.selectableData1.filter(
                    (data) =>
                        !this.search22 ||
                        data.courseCategory.courseName.toLowerCase().includes(this.search22.toLowerCase())
                )
            );
            this.selectableDataShow = computed(() =>
                this.selectableData2.filter(
                    (data) =>
                        !this.search33 ||
                        data.teacher.username.toLowerCase().includes(this.search33.toLowerCase())
                )
            );
        },
        apply_new_class(){
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
                    params.courseNumber=0;
                    params.courseId= 1;
                    params.academicYear= this.academicData.toString().slice(0,9);
                    params.term= this.academicData.toString().slice(9,10);
                    params.courseCategory= {
                        'courseCategoryNumber':this.ruleForm1.courseCategoryNumber,
                        'courseCategoryId':this.ruleForm1.courseCategory.courseCategoryId,
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
                    params.type='ADD';
                    params.applicationId=Math.floor(Math.random()*(9999-1000+1))+1000;
                    if(typeof this.ruleForm1.introduction==="undefined"||(typeof this.ruleForm1.introduction==="string"&&this.ruleForm1.introduction.length===0))
                        params.introduction= "该课程暂无描述信息";
                    else
                        params.introduction=this.ruleForm1.introduction;
                    console.log(params);
                    this.$axios({
                        headers:{'Content-Type':'application/json'},
                        type:'application/json',
                        method: 'post',
                        url:'/api/teacher/application/add',
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
        edit_class_inform(row){//12
            this.dialogVisible=true;
            this.ruleForm1=row;
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
        submit_edit(){
            this.fillclassArrangementId();
            console.log(this.ruleForm1);
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
                        'courseCategoryNumber':this.ruleForm1.courseCategory.courseCategoryNumber,
                        'courseCategoryId':this.ruleForm1.courseCategory.courseCategoryId,
                        'courseName': this.ruleForm1.courseCategory.courseName,
                        'classHour': this.ruleForm1.courseCategory.classHour,
                        'credit': this.ruleForm1.courseCategory.credit,
                        'major': {'majorId': this.ruleForm1.courseCategory.major.majorId},
                        'school': {'schoolId': this.ruleForm1.courseCategory.school.schoolId}
                    }
                    params.teacher= {'userId':this.teacherdata.teacherId};
                    params.classArrangements= classArrangements;
                    params.openToMajors=  openToMajors;
                    params.capacity=this.ruleForm1.capacity;
                    if(typeof this.ruleForm1.introduction==="undefined"||(typeof this.ruleForm1.introduction==="string"&&this.ruleForm1.introduction.length===0))
                        params.introduction= "该课程暂无描述信息";
                    else
                        params.introduction=this.ruleForm1.introduction;
                    params.type='UPDATE'
                    params.applicationId=this.ruleForm1.courseId;
                    console.log(params);
                        this.$axios({
                            headers:{'Content-Type':'application/json'},
                            type:'application/json',
                            method: 'post',
                            url:'/api/teacher/application/add',
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
        get_class_infor(){
            const that =this;
            request.get("/course/list")
                .then(res=>{
                    this.tableData=res;
                    this.filltabledata();
                },function (err) {
                    ALERTMSG.show(that,true,"课程信息获取失败！","error");
                    return false;
                });
        },
        filltabledata(){
            for(let i=0;i<Object.keys(this.tableData).length;i++)
            {
                this.tableData[i].courseCategoryNumbershow=this.tableData[i].courseCategory.courseCategoryNumber+'.'+this.tableData[i].courseNumber;
                let classarrangementstr='';
                for(let k=0;k<Object.keys(this.tableData[i].classArrangements).length;k++)
                {
                    let times='';
                    let timesinorder=[];
                    timesinorder.length=Object.keys(this.tableData[i].classArrangements[k].classTimes).length;
                    //将classTimes按从小到大放入timesinorder
                    for(let l=0;l<Object.keys(this.tableData[i].classArrangements[k].classTimes).length;l++)
                    {
                        timesinorder[l]=this.tableData[i].classArrangements[k].classTimes[l].classTimeId;
                    }
                    timesinorder=timesinorder.sort();
                    for(let l=0;l<Object.keys(timesinorder).length;l++)
                    {
                        times=times.concat(timesinorder[l]+',');
                    }
                    classarrangementstr=classarrangementstr.concat(this.tableData[i].classArrangements[k].building.buildingName+','+
                        this.tableData[i].classArrangements[k].classroom.classroomId+','+
                        times+
                        JSON.stringify(this.tableData[i].classArrangements[k].dayOfWeek).replace(/"/g,"")+'\n')
                }
                this.tableData[i].classarrangement = classarrangementstr;
            }

        },
        tea_delete(row){
            this.applicationform=row;
            this.applicationform.type="DELETE";
            this.applicationform.applicationId=this.applicationform.courseId;
            this.$axios({
                headers:{'Content-Type':'application/json'},
                type:'application/json',
                method: 'post',
                url:'/api/teacher/application/add',
                data:JSON.stringify(this.applicationform)
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
    }
}
