import Nav from "@/views/inc/Nav.vue";
import request from "@/utils/request";
import ALERTMSG from "@/assets/js/alert";
export default {
    name: "myclass",
    components:{
        Nav
    },
    data(){
        return {
                classtimetable:[],
            classinfortable:[
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
            ]

        }
    },
    computed:{
    },
    mounted() {
        const that=this;
        setTimeout(function() {
            that.fillInClassInForm()
        }, 300); // 定时时间
    },
    created(){
        this.getTimeinfor();

    },
    methods:{
        getTimeinfor:function(){
            request.get("/classTime/list").then(res=>{
                this.classtimetable= res;
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
            console.log(SundayObj.length);
            for(let i=0;i<that.classinfortable.length;i++){
                currentClass=that.classinfortable[i];
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