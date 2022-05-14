import Nav from "@/views/inc/Nav.vue";
import request from "@/utils/request";
import ALERTMSG from "@/assets/js/alert";
import {computed} from "vue";
import tokenmanage from "@/utils/Tokenmanage";
export default {
    name: "classselection",
    components:{
        Nav
    },
    data(){
        return {
            schooltimetable: [],
            classinfortable1: [],
            teachingBuildings: [],
            teachingRooms: [],
            selectableDataShow: [{}],
            selectableData1: [{}],
            selectableData2: [{}],
            selectableData3: [{}],
            selectableData: [],
            search11: '',
            search22: '',
            search33: '',
            dialogVisible1: false,
            applyForSelectVisiable: false,
            applyForSelectFormRules: ({
                applyReason: [
                    {required: true, message: '请输入申请理由', trigger: 'blur',},
                ]
            }),
            applyForSelectForm: {
                applyCourseCategoryNumber: '',
                applyClassId: '',
                applyClassName: '',
                applyStudentId: '',
                applyStudentName: '',
                applyReason: '',
            },
            userTable: {
                User: '',
                id: '',
                name: '',
            },
            theClassTimeData:[],
            fliterTimeAndPlaceForm:{
                dayOfWeek:'',
                classTimeId:'',
                buildingId:'',
                classroomId:'',
            },
            buildings:[],
            classrooms:[],
        }
    },
    mounted(){},
    created(){
        this.getclassinfo();
        this.search();
        this.getSchooltimetable();
        this.getStudingClass();
        this.getBuildings();
    },
    methods:{
        cancleFilter(){
            this.fliterTimeAndPlaceForm.classroomId='';
            this.fliterTimeAndPlaceForm.buildingId='';
            this.fliterTimeAndPlaceForm.dayOfWeek='';
            this.fliterTimeAndPlaceForm.classTimeId='';
        },
        getBuildings(){
            request.get("/building/list").then(res=>{
                this.buildings=res;
            })
        },
        getClassroom:function(id){
            this.fliterTimeAndPlaceForm.classroomId='';
            request.get("/building/classrooms",{
                params:{
                    buildingId:id
                }
            }).then(res=>{
                this.classrooms= res;
            })
        },
        toSubmitApply(){
            const that=this;
            this.$refs.applyForSelectForm.validate(valid=>{
                    if(valid){
                        let selectApplyForm = new FormData();
                        selectApplyForm.append('courseId', that.applyForSelectForm.applyClassId);
                        selectApplyForm.append('studentId', that.applyForSelectForm.applyStudentId);
                        selectApplyForm.append('reason', that.applyForSelectForm.applyReason);
                        console.log(selectApplyForm)
                        request.post("/student/application/add",selectApplyForm)
                            .then(function(res){
                                    ALERTMSG.show(that,true,"选课申请提交成功!","success");
                                    return true;
                                }
                                ,function (err) {
                                    ALERTMSG.show(that,true,"提交申请失败!请再次尝试！","error");
                                    return false;
                                }
                            )
                    }
            else{
                        ALERTMSG.show(that,true,"请先填写选课申请理由","warning");
                    }})
        },
        cancleApply(){
            this.applyForSelectVisiable=false;
        },
        getUserInfo(){
            const that=this;
            request.get("/user/info")
                .then(function(res){
                        that.applyForSelectForm.applyStudentId=res.userId;
                        that.applyForSelectForm.applyStudentName=res.username;})
        },
        applyStudentSelectOpen(row){
            const that=this;
            that.getUserInfo();
            that.applyForSelectVisiable=true;
            that.applyForSelectForm.applyClassId=row.courseId;
            that.applyForSelectForm.applyCourseCategoryNumber=row.courseCategory.courseCategoryNumber+'.'+row.courseNumber;
            that.applyForSelectForm.applyClassName=row.courseCategory.courseName;
        },
        getSchooltimetable:function(){
            request.get("/classTime/list").then(res=>{
                this.schooltimetable= res;
            });
        },
        filterAll(classArrangements){
            let thisClassroomId = this.fliterTimeAndPlaceForm.classroomId;
            let thisBuildingId = this.fliterTimeAndPlaceForm.buildingId;
            let thisClassTimeId = this.fliterTimeAndPlaceForm.classTimeId;
            let thisDayOfWeek = this.fliterTimeAndPlaceForm.dayOfWeek;
            for(let i = 0;i<classArrangements.length;i++) {
                if(classArrangements[i].dayOfWeek.includes(thisDayOfWeek)&&
                    String(classArrangements[i].building.buildingId).includes(String(thisBuildingId))&&
                    String(classArrangements[i].classroom.classroomId).includes(String(thisClassroomId))){
                    for(let j=0;j<classArrangements[i].classTimes.length;j++){
                        if(String(classArrangements[i].classTimes[j].classTimeId).includes(String(thisClassTimeId))){
                            return true;
                        }
                    }
                }
            }
            return false;
        },
        search(){
            //搜索部分
            this.selectableData1 = computed(() =>
                this.selectableData.filter(
                    (data) =>
                        !this.search11 ||
                        String(data.courseCategory.courseCategoryNumber+'.'+data.courseNumber).toLowerCase().includes(this.search11.toLowerCase())
                )
            );
            this.selectableData2 = computed(() =>
                this.selectableData1.filter(
                    (data) =>
                        !this.search22 ||
                        data.courseCategory.courseName.toLowerCase().includes(this.search22.toLowerCase())
                )
            );
            this.selectableData3 = computed(() =>
                this.selectableData2.filter(
                    (data) =>
                        !this.search33 ||
                        data.teacher.username.toLowerCase().includes(this.search33.toLowerCase())
                )
            );
            //筛选部分
            this.selectableDataShow = computed(() =>
                this.selectableData3.filter(
                    (data) =>this.filterAll(data.classArrangements)
                )
            );
        },
        openit(currentRow){
            const that=this;
            that.dialogVisible1=true;
            setTimeout(function() {
                that.cleanTable();
                that.fillInClassInForm2();
                that.findTarget(currentRow);
                that.fillInTimeTable(currentRow.classArrangements);
            }, 300); // 定时时间
        },
        fillInTimeTable(classArrangements){
            this.theClassTimeData=classArrangements;
        },
        getStudingClass:function(){
            request.get("/student/course/studying").then(res=>{
                this.classinfortable1= res;
            });
        },
        getclassinfo(){
            const that=this;
                request.get("/student/course/selectable")
                    .then(function(res){
                            console.log(res);
                            that.selectableData=res;
                    }
                    ,function (err) {
                            ALERTMSG.show(that,true,"当前选课未开放!","warning");
                            return false;
                }
                )
        },
        stu_select(row){
            let courseId=row.courseId;
            let classArrangements = row.classArrangements;
            const that =this;
            if(!that.conflictBeforSelect(classArrangements)){
                ALERTMSG.show(that,true,"当前课程与您已选的课程冲突！","error");
                return false;
            }
            let xuankeform = new FormData();
            xuankeform.append('courseId', courseId);
            request.post("/student/course/select",xuankeform)
                .then(function (response) {
                    if (response === "FAILED") {
                        ALERTMSG.show(that,true,"选课失败！请重新尝试!","error");
                        that.getclassinfo();
                        return false;
                    } else if (response === "NOT_OPEN") {
                        ALERTMSG.show(that,true,"当前选课未开放!","warning");
                        that.getclassinfo();
                        return false;
                    } else if (response === "SUCCESS") {
                        ALERTMSG.show(that,true,"选课成功!","success");
                        that.getStudingClass();
                        that.getclassinfo();
                    }
                },function (err) {
                    ALERTMSG.show(that,true,"选课失败！请重新尝试!","error");
                    that.getclassinfo();
                    return false;
                    }
            )
        },
        conflictBeforSelect(classArrangements){
            const that = this;
            let day;
            let result;
            for(let i=0;i<classArrangements.length;i++){
                day = classArrangements[i].dayOfWeek;
                for(let j=0;j<classArrangements[i].classTimes.length;j++){
                    result = that.conflictTest(day,classArrangements[i].classTimes[j].classTimeId);
                    if(result===1){
                        return false;
                    }
                }
            }
            return true;
        },
        conflictTest(day,ci){
            const that=this;
            for(let i=0;i<that.classinfortable1.length;i++){
                for(let j=0;j<that.classinfortable1[i].classArrangements.length;j++){
                    if(that.classinfortable1[i].classArrangements[j].dayOfWeek==day){
                        for(let k=0;k<that.classinfortable1[i].classArrangements[j].classTimes.length;k++){
                            if(that.classinfortable1[i].classArrangements[j].classTimes[k].classTimeId==ci){
                                return 1;
                            }
                        }
                    }
                }
            }
            return 0;

        },
        cleanTable(){
            let MondayObj=document.querySelectorAll(".Monday");
            let TuesdayObj=document.querySelectorAll(".Tuesday");
            let WednesdayObj=document.querySelectorAll(".Wednesday");
            let ThursdayObj=document.querySelectorAll(".Thursday");
            let FridayObj=document.querySelectorAll(".Friday");
            let SaturdayObj=document.querySelectorAll(".Saturday");
            let SundayObj=document.querySelectorAll(".Sunday");
            let week=[MondayObj,TuesdayObj,WednesdayObj,ThursdayObj,FridayObj,SaturdayObj,SundayObj];
            let inform;
            for(let i=0;i<week.length;i++) {
                for(let j=0;j<week[i].length;j++){
                    week[i][j].parentElement.parentElement.style.backgroundColor="#FFFFFF";
                    inform=week[i][j].firstElementChild;
                    inform.innerText="";
                    inform=inform.nextElementSibling;
                    inform.innerText="";
                    inform=inform.nextElementSibling;
                    inform.innerText="";
                    inform=inform.nextElementSibling;
                    inform.innerText="";
                }
            }
        },
        findTarget(currentRow){
            let MondayObj=document.querySelectorAll(".Monday");
            let TuesdayObj=document.querySelectorAll(".Tuesday");
            let WednesdayObj=document.querySelectorAll(".Wednesday");
            let ThursdayObj=document.querySelectorAll(".Thursday");
            let FridayObj=document.querySelectorAll(".Friday");
            let SaturdayObj=document.querySelectorAll(".Saturday");
            let SundayObj=document.querySelectorAll(".Sunday");
            let week=[MondayObj,TuesdayObj,WednesdayObj,ThursdayObj,FridayObj,SaturdayObj,SundayObj];
            const that=this;
            let currentCi;
            let inform;
            let currentClassDay;
            for(let i=0;i<currentRow.classArrangements.length;i++){
                switch (currentRow.classArrangements[i].dayOfWeek){
                    case "MONDAY":
                        currentClassDay=1;
                        break;
                    case "TUESDAY":
                        currentClassDay=2;
                        break;
                    case "WEDNESDAY":
                        currentClassDay=3;
                        break;
                    case "THURSDAY":
                        currentClassDay=4;
                        break;
                    case "FRIDAY":
                        currentClassDay=5;
                        break;
                    case "SATURDAY":
                        currentClassDay=6;
                        break;
                    case "SUNDAY":
                        currentClassDay=7;
                        break;
                }
                for(let j=0;j<currentRow.classArrangements[i].classTimes.length;j++){
                    currentCi = currentRow.classArrangements[i].classTimes[j].classTimeId;
                    if(that.conflictTest(currentRow.classArrangements[i].dayOfWeek,currentCi)==1){
                        week[currentClassDay-1][currentCi-1].parentElement.parentElement.style.backgroundColor="#c45656";
                    }
                    else{
                        week[currentClassDay-1][currentCi-1].parentElement.parentElement.style.backgroundColor="#409EFF";
                        inform=week[currentClassDay-1][currentCi-1].firstElementChild;
                        inform.innerText=currentRow.courseCategory.courseCategoryNumber+'.'+currentRow.courseNumber;
                        inform=inform.nextElementSibling;
                        inform.innerText=currentRow.courseCategory.courseName;
                        inform=inform.nextElementSibling;
                        inform.innerText=currentRow.teacher.username;
                        inform=inform.nextElementSibling;
                        inform.innerText=currentRow.classArrangements[i].building.buildingName+currentRow.classArrangements[i].classroom.classroomId;
                    }
                }
            }
        },
        fillInClassInForm2:function(){
            let MondayObj=document.querySelectorAll(".Monday");
            let TuesdayObj=document.querySelectorAll(".Tuesday");
            let WednesdayObj=document.querySelectorAll(".Wednesday");
            let ThursdayObj=document.querySelectorAll(".Thursday");
            let FridayObj=document.querySelectorAll(".Friday");
            let SaturdayObj=document.querySelectorAll(".Saturday");
            let SundayObj=document.querySelectorAll(".Sunday");
            let week=[MondayObj,TuesdayObj,WednesdayObj,ThursdayObj,FridayObj,SaturdayObj,SundayObj];
            const that=this;
            let currentClass;
            let currentClassDay;
            let currentClassDayTimeObj;
            let ci;
            let inform;
            for(let i=0;i<that.classinfortable1.length;i++){
                currentClass=that.classinfortable1[i];
                for(let j=0;j<currentClass.classArrangements.length;j++){
                    switch (currentClass.classArrangements[j].dayOfWeek){
                        case "MONDAY":
                            currentClassDay=1;
                            break;
                        case "TUESDAY":
                            currentClassDay=2;
                            break;
                        case "WEDNESDAY":
                            currentClassDay=3;
                            break;
                        case "THURSDAY":
                            currentClassDay=4;
                            break;
                        case "FRIDAY":
                            currentClassDay=5;
                            break;
                        case "SATURDAY":
                            currentClassDay=6;
                            break;
                        case "SUNDAY":
                            currentClassDay=7;
                            break;
                    }
                    currentClassDayTimeObj=currentClass.classArrangements[j].classTimes;
                    ci=0
                    for(ci;ci<currentClassDayTimeObj.length;ci++){
                        week[currentClassDay-1][currentClassDayTimeObj[ci].classTimeId-1].parentElement.parentElement.style.backgroundColor="#B0C4DE";
                        inform=week[currentClassDay-1][currentClassDayTimeObj[ci].classTimeId-1].firstElementChild;
                        inform.innerText=currentClass.courseCategory.courseCategoryNumber+'.'+currentClass.courseNumber;
                        inform=inform.nextElementSibling;
                        inform.innerText=currentClass.courseCategory.courseName;
                        inform=inform.nextElementSibling;
                        inform.innerText=currentClass.teacher.username;
                        inform=inform.nextElementSibling;
                        inform.innerText=currentClass.classArrangements[j].building.buildingName+currentClass.classArrangements[j].classroom.classroomId;
                    }
                }
            }
        }
    }
}
