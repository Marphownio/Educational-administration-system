import Nav from "@/views/inc/Nav.vue";
import request from "@/utils/request";
import ALERTMSG from "@/assets/js/alert";
import tokenmanage from "@/utils/Tokenmanage";
export default {
    name: "myclass",
    components:{
        Nav
    },
    data(){
        return {
            currentStudentId:'',
            classtimetable:[],
            classinfortable:[{
                academicYear: "2000-2001",
                capacity: 30,
                classArrangements: [
                    {
                        classArrangementId: 8,
                        building:
                            {
                                buildingId: 2,
                                buildingName: "第二教学楼"
                            },
                        classroom:
                            {
                                building: {buildingId: 2, buildingName: "第二教学楼"},
                                capacity: 80,
                                classroomId: 202
                            },
                        dayOfWeek: "TUESDAY",
                        classTimes: [{
                            classTimeId: 1,
                            startTimeHour: null,
                            startTimeMin: null,
                            endTimeHour: null,
                            endTimeMin: null
                        }],
                    },
                    {
                        classArrangementId: 2,
                        building:
                            {
                                buildingId: 1,
                                buildingName: "第一教学楼"
                            },
                        classroom:
                            {
                                building: {buildingId: 2, buildingName: "第二教学楼"},
                                capacity: 80,
                                classroomId: 102
                            },
                        dayOfWeek: "FRIDAY",
                        classTimes: [{
                            classTimeId: 1,
                            startTimeHour: null,
                            startTimeMin: null,
                            endTimeHour: null,
                            endTimeMin: null
                        },
                            {
                                classTimeId: 2,
                                startTimeHour: null,
                                startTimeMin: null,
                                endTimeHour: null,
                                endTimeMin: null
                            },
                            {
                                classTimeId: 3,
                                startTimeHour: null,
                                startTimeMin: null,
                                endTimeHour: null,
                                endTimeMin: null
                            },
                            {
                                classTimeId: 7,
                                startTimeHour: null,
                                startTimeMin: null,
                                endTimeHour: null,
                                endTimeMin: null
                            },],
                    },],
                courseCategory:
                    {
                        courseCategoryId: 1,
                        courseName: "数学分析",
                        classHour: 5,
                        credit: 5,
                    },
                courseId: 2,
                courseNumber: 2,
                teacher: {username: "哈哈哈",},
            },],

        }
    },
    computed:{
    },
    mounted() {
        const that=this;
        setTimeout(function() {
            that.getStudingClass()
            that.fillInClassInForm()
        }, 300); // 定时时间
    },
    created(){
        this.getTimeinfor();
        // this.getuserId();
        // this.getStudingClass();

    },
    methods:{
        getuserId:function (){
            const that = this;
            this.$axios({
                "method": 'get',
                "url":'/api/user/info',
                async: false,
            }).then(function(res){
                that.currentStudentId = res.data.userId;
            })
        },
        getTimeinfor:function(){
            request.get("/classTime/list").then(res=>{
                this.classtimetable= res;
            });
        },
        getStudingClass:function(){
            const that =this;
            let form = new FormData();
            console.log(that.currentStudentId)
            form.append('studentId', that.currentStudentId);
            request.get("/student/courses/studying",form).then(res=>{
                console.log(res)
                this.classinfortable= res;
            });
        },
        fillInClassInForm:function(){
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
            for(let i=0;i<that.classinfortable.length;i++){
                currentClass=that.classinfortable[i];
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
                        inform.innerText=currentClass.courseNumber;
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
