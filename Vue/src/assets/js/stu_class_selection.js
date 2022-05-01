import Nav from "@/views/inc/Nav.vue";
import request from "@/utils/request";
import ALERTMSG from "@/assets/js/alert";
import {computed} from "vue";
export default {
    name: "classselection",
    components:{
        Nav
    },
    data(){
        return{
            classtimetable:[
                {
                    classTimeId:'1',
                    startTimeHour:'8',
                    startTimeMin:'00',
                    endTimeHour:'8',
                    endTimeMin:'45',
                },
                {
                    classTimeId:"2",
                    startTimeHour:'8',
                    startTimeMin:'55',
                    endTimeHour:'9',
                    endTimeMin:'40',
                },
                {
                    classTimeId:"3",
                    startTimeHour:'9',
                    startTimeMin:'55',
                    endTimeHour:'10',
                    endTimeMin:'40',
                },
                {
                    classTimeId:"4",
                    startTimeHour:'10',
                    startTimeMin:'50',
                    endTimeHour:'11',
                    endTimeMin:'35',
                },
            ],
            classinfortable1:[
                {
                    classid:'MATH0001',
                    classname:'数学分析',
                    teacher:'李四',
                    classroom:'H3102',
                    classtime:[
                        {day:'2',
                            classci:[
                                '1','2','3'
                            ]
                        },
                    ],
                },
                {
                    classid:'MATH0002',
                    classname:'高等代数',
                    teacher:'张三',
                    classroom:'H3108',
                    classtime:[
                        {day:'3',
                            classci:[
                                '1','3'
                            ]
                        },
                        {day:'5',
                            classci:[
                                '2'
                            ]
                        },
                    ],
                },
             ],


            tianCi:[
                { text: '周一', value: '1' },
                { text: '周二', value: '2' },
                { text: '周三', value: '3' },
                { text: '周四', value: '4' },
                { text: '周五', value: '5' },
                { text: '周六', value: '6' },
                { text: '周日', value: '7' },
            ],
            jieCi:[
                { text: '1', value: '1' },
                { text: '2', value: '2' },
                { text: '3', value: '3' },
                { text: '4', value: '4' },
                { text: '5', value: '5' },
                { text: '6', value: '6' },
                { text: '7', value: '7' },
            ],
            teachingBuildings:[],
            teachingRooms:[],
            selectableData:[
                {
                courseId:'MATH101',
                courseName:'线性代数',
                teacher:'张三四',
                credit:'2',
                classTime:[
                    {
                        day:'1',
                        cishu:'1',
                    },
                    {
                        day:'1',
                        cishu:'2',
                    },]

                },
                {
                    courseId:'MATH102',
                    courseName:'高等代数',
                    teacher:'张三五',
                    credit:'2',
                    classTime:[
                        {
                            day:'2',
                            cishu:'1',
                        },
                        {
                            day:'7',
                            cishu:'3',
                        },]

                },
            ],
            search11:'',
            search22:'',
            search33:'',
            selectableData1:[
                {},
            ],
            dialogVisible1:false,
        }
    },
    mounted() {},
    created(){
        this.search1();
    },
    methods:{
        search1(){
            this.selectableData1=computed(() =>
                this.selectableData.filter(
                    (data) =>
                        !this.search11 ||
                        data.courseId.toLowerCase().includes(this.search11.toLowerCase())
                )
            );

        },
        search2(){
            this.selectableData1=computed(() =>
                this.selectableData.filter(
                    (data) =>
                        !this.search22 ||
                        data.courseName.toLowerCase().includes(this.search22.toLowerCase())
                )
            );
        },
        search3(){
            this.selectableData1=computed(() =>
                this.selectableData.filter(
                    (data) =>
                        !this.search33 ||
                        data.teacher.toLowerCase().includes(this.search33.toLowerCase())
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
            }, 300); // 定时时间
        },
        getclassinfo(){
            const that=this;
                request.get("/course/selectable")
                    .then(function(res){
                            that.selectableData=res;
                    }
                    ,function (err) {
                            ALERTMSG.show(that,true,"当前选课未开放!","warning");
                            return false;
                }
                )
        },
        stu_select(courseId){
            const that =this;
            let xuankeform = new FormData();
            xuankeform.append('courseId', courseId);
            request.post("/course/select",xuankeform)
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
                        that.getclassinfo();
                    }
                },function (err) {
                    ALERTMSG.show(that,true,"选课失败！请重新尝试!","error");
                    that.getclassinfo();
                    return false;
                    }
            )
        },
        conflictTest(day,cishu){
            // const that=this;
            // let currentArragement
            // for(let i=0;i<that.classinfortable1.length;i++){
            //     for(let j=0;j<that.classinfortable1[i].classtime.length;i++){
            //         currentArragement=that.classinfortable1[i].classtime[j];
            //         if(currentArragement.day==day&&)
            //     }
            // }

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
            for(let i=0;i<week.length;i++) {
                for(let j=0;j<week[i].length;j++){
                    week[i][j].parentElement.parentElement.style.backgroundColor="#FFFFFF";
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
            let currentClass;
            for(let i=0;i<currentRow.classTime.length;i++){
                currentClass=currentRow.classTime[i];
                if(that.conflictTest()==1){
                    week[currentClass.day-1][currentClass.cishu-1].parentElement.parentElement.style.backgroundColor="#c45656";
                }
                else{
                    week[currentClass.day-1][currentClass.cishu-1].parentElement.parentElement.style.backgroundColor="#409EFF";
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
                for(let j=0;j<currentClass.classtime.length;j++){
                    currentClassDay=currentClass.classtime[j].day;
                    currentClassDayTimeObj=currentClass.classtime[j].classci;
                    ci=0
                    for(ci;ci<currentClassDayTimeObj.length;){
                        week[currentClassDay-1][currentClassDayTimeObj[ci]-1].parentElement.parentElement.style.backgroundColor="#B0C4DE";
                        inform=week[currentClassDay-1][currentClassDayTimeObj[ci]-1].firstElementChild;
                        inform.innerText=currentClass.classid;
                        inform=inform.nextElementSibling;
                        inform.innerText=currentClass.classname;
                        inform=inform.nextElementSibling;
                        inform.innerText=currentClass.teacher;
                        inform=inform.nextElementSibling;
                        inform.innerText=currentClass.classroom;
                        ci=ci+1;
                    }
                }
            }
        }
    }
}
