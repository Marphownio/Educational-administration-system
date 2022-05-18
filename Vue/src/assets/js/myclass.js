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
            classinfortable:[],
        }
    },
    computed:{
    },
    mounted() {
        const that=this;
        setTimeout(function() {
            that.fillInClassInForm()
        }, 500); // 定时时间
    },
    created(){
        this.getTimeinfor();
        this.getStudingClass();

    },
    methods:{
        classDrop(courseId){
            const that =this;
            const formAAA = new FormData();
            formAAA.append('courseId', courseId);
            request.post("/student/course/drop",formAAA).then(function(res){
                if(res==="NOT_OPEN"){
                    ALERTMSG.show(that,true,"当前退课未开放!","warning");
                    return false;
                }
                ALERTMSG.show(that,true,"退课成功!","success");
                setTimeout(function() {
                    that.$router.go(0);
                }, 800); // 定时时间
                return true;
            },function(err){
                ALERTMSG.show(that,true,"退课失败!","error");
                return false;
            })
        },
        getTimeinfor:function(){
            request.get("/classTime/list").then(res=>{
                this.classtimetable= res;
            });
        },
        getStudingClass:function(){
          request.get("/student/course/studying").then(res=>{
              console.log(res);
                this.classinfortable= res;
            });
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
