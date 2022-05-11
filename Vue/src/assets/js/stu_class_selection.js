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
        return{
            schooltimetable:[],
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
            selectableDataShow:[{}],
            selectableData1:[{}],
            selectableData2:[{}],
            selectableData:[],
            search11:'',
            search22:'',
            search33:'',
            dialogVisible1:false,
            applyForSelectVisiable:false,
            applyForSelectFormRules:({
                applyReason: [
                    {required: true, message: '请输入申请理由', trigger: 'blur',},
                ]
            }),
            applyForSelectForm:{
                applyClassId:'',
                applyClassName:'',
                applyStudentId:'',
                applyStudentName:'',
                applyReason:'',
            },
            userTable:{
                User:'',
                id:'',
                name:'',
            }
        }
    },
    mounted(){},
    created(){
        this.getclassinfo();
        this.search();
        this.getSchooltimetable();
    },
    methods:{
        toSubmitApply(){
            const that=this;
            request.post("")
                .then(function(res){
                        ALERTMSG.show(that,true,"选课申请提交成功!","warning");
                        return true;
                    }
                    ,function (err) {
                        ALERTMSG.show(that,true,"提交申请失败!请再次尝试！","error");
                        return false;
                    }
                )
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
            that.applyForSelectForm.applyClassName=row.courseName;
        },
        getSchooltimetable:function(){
            request.get("/classTime/list").then(res=>{
                this.schooltimetable= res;
            });
        },
        search(){
            this.selectableData1 = computed(() =>
                this.selectableData.filter(
                    (data) =>
                        !this.search11 ||
                        data.courseId.toLowerCase().includes(this.search11.toLowerCase())
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
                request.get("/course/list")
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
