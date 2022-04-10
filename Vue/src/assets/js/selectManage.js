import Nav from "@/views/inc/Nav.vue";
import request from "@/utils/request";
import {getState} from "core-js/modules/web.url-search-params";

export default {
    name: "selectManage",
    components:{
        Nav
    },
    data:{
        isDisabled:false,
    },
    created() {
        this.getStatus();
    },
    methods:{
        Isdisable1(){
            return this.isDisabled;
        },
        Isdisable2(){
            return !(this.isDisabled);
        },
        getStatus(){
            request.get("/admin/courseSelect/status").then(res=>{
                console.log(res)
                if(res ==='SUCCESS'){
                    this.isDisabled=false;
                }
                else this.isDisabled=true;
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
            request.post("/admin/courseSelect/change",CHANGE).then(res=> {
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
