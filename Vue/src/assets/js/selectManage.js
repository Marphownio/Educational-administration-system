import Nav from "@/views/inc/Nav.vue";
import request from "@/utils/request";

export default {
    name: "selectManage",
    components:{
        Nav
    },
    data(){
       return{
           isDisabled1:true,
           isDisabled2:false,
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
            let CHANGE=true;
            request.post("/admin/courseSelect/change",{
                params:{
                    change:CHANGE
                }
            }).then(res=> {
                if(res==='SUCCESS')
                {
                    this.$message({
                    showClose: true,
                    message: '操作成功',
                    type: 'success',
                })
            }
                else{
                    this.$message({
                        showClose: false,
                        message: '操作失败',
                        type: 'danger',
                    })
                }
            })
        },
        closeSelection(){
            let CHANGE=false;
            request.post("/admin/courseSelect/change",{
                params:{
                    change:CHANGE
                }
            }).then(res=> {
                if(res==='SUCCESS')
                {
                    this.$message({
                        showClose: true,
                        message: '操作成功',
                        type: 'success',
                    })
                }
                else{
                    this.$message({
                        showClose: false,
                        message: '操作失败',
                        type: 'danger',
                    })
                }
            })
        }
    }
}
