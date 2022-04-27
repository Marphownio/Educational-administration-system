import Nav from "@/views/inc/Nav.vue";
import request from "@/utils/request";
import ALERTMSG from "@/assets/js/alert";

export default {
    name: "selectManage",
    components:{
        Nav
    },
    data(){
       return{
           isDisabled1:true,
           isDisabled2:false,
           currentTime:[{
               currentYear:'2022',
               currentTerm:'2',
           }],
           activities:[
               {
                   content: '学期开始',
                   size: 'normal ',
                   type: 'primary',
                   color: '#3581E1',
               },
               {
                   content: '一轮选课开始',
                   size: 'normal ',
                   type: 'primary',
                   color: '#3581E1',
               },
               {
                   content: '一轮选课结束',
                   size: 'normal ',
                   type: 'primary',
                   color: '#3581E1',
               },
               {
                   content: '二轮选课开始',
                   size: 'large',
                   type: 'primary',
                   color: '#0bbd87',
               },
               {
                   content: '二轮选课结束',
                   size: 'normal ',
                   type: 'primary',
                   color: '#D3D3D3',
               },
               {
                   content: '学期结束',
                   size: 'normal ',
                   type: 'primary',
                   color: '#D3D3D3',
               },
           ]
       }

    },
    created() {
        this.getStatus();
    },
    methods:{
        getStatus(){
            request.get("/admin/courseSelect/status").then(res=>{
                console.log(res)
                if(res ==='SUCCESS'){
                    this.isDisabled1=true;
                    this.isDisabled2=false;
                }
                else
                {
                    this.isDisabled1=false;
                    this.isDisabled2=true;
                }
            })
        },
        openSelection(){
            const that=this;
            let CHANGE=true;
            let openform = new FormData();
            openform.append('change', CHANGE);
            request.post("/admin/courseSelect/change",openform)
                .then(res=> {
                if(res==='SUCCESS') {
                    ALERTMSG.show(that,true,"操作成功!","success");
                    that.getStatus();
                }
                else{
                    ALERTMSG.show(that,true,"选课失败!请再次尝试","error");
                    that.getStatus();
                }
            })
        },
        closeSelection(){
            const that=this;
            let CHANGE=false;
            let closeform = new FormData();
            closeform.append('change', CHANGE);
            request.post("/admin/courseSelect/change",closeform)
                .then(res=> {
                if(res==='SUCCESS') {
                    ALERTMSG.show(that,true,"操作成功!","success");
                    that.getStatus();
                    }
                else{
                    ALERTMSG.show(that,true,"选课失败!请再次尝试","error");
                    that.getStatus();
                }
            })
        }
    }
}
