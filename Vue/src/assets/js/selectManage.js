import Nav from "@/views/inc/Nav.vue";
import request from "@/utils/request";

export default {
    name: "selectManage",
    components:{
        Nav
    },
    methods:{
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
                    onClose: () => {
                        this.getUserForm()
                    }
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
                        onClose: () => {
                            this.getUserForm()
                        }
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
