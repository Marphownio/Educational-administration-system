import Nav from "@/views/inc/Nav.vue";
import request from "@/utils/request";
import ALERTMSG from "@/assets/js/alert";
export default {
    name: "timeManage",
    components:{
        Nav
    },
    data(){
        return{
            tableData:[],
            dialogVisible1:false,
            applyform:{
                classTimeId: '',
                time:'',
            },
            ruleForm1:{
                classTimeId: '',
                time:'',
            },
            editRules1:({
                classTimeId:[{required: true, message: '请新课次', trigger: 'blur'}],
                time:[{required: true, message: '请输入本课次时间', trigger: 'blur'}],
            }),
            dialogVisible2:false,
            ruleForm2:{
                classTimeId: '',
                time:'',
            },
            editRules2:({
                classTimeId:[{required: true, message: '请新课次', trigger: 'blur'}],
                time:[{required: true, message: '请输入本课次时间', trigger: 'blur'}],
            }),
        }
    },
    created(){
        this.getinfor();
    },
    methods:{
        submit_apply:function(){
            this.$refs.ruleForm1.validate((valid) => {
                    if (valid) {
                        const that=this;
                        let applytimeform = new FormData();
                        for(let key in this.ruleForm1) {
                            applytimeform.append(key,this.ruleForm1[key]);
                        }
                        request.post("/classTime/add",applytimeform)
                            .then(function (response) {
                                ALERTMSG.show(that,true,"课次新增成功!","success");
                                that.getinfor();
                                }, function (err) {
                                ALERTMSG.show(that,true,"课次新增失败！请重新尝试！","error");
                                that.getinfor();
                                return false;
                            });
                    }
                    else{
                        return false;
                    }
        })
        },
        getinfor:function(){
            request.get("/classTime/list").then(res=>{
                this.tableData= res;
            })
        },
        edittime(row){
            this.dialogVisible2=true;
            this.ruleForm2.time=row.time;
            this.ruleForm2.classTimeId=row.classTimeId;
        },
        submitEdit(){
            const that=this;
            this.$refs.ruleForm2.validate((valid) => {
                if (valid) {
                    let edittimeform = new FormData();
                    for(let key in this.ruleForm2) {
                        edittimeform.append(key,this.ruleForm2[key]);
                    }
                    request.put("/classTime/update",edittimeform)
                        .then(function (response) {
                            ALERTMSG.show(that,true,"课次修改成功!","success");
                            that.getinfor();
                        }, function (err) {
                            ALERTMSG.show(that,true,"课次修改失败！请重新尝试！","error");
                            that.getinfor();
                            return false;
                        });
                }
                else{
                    return false;
                }
            })
        },
        deletetime(row){
            const that=this;
            let timeid=row.classTimeId;
            request.delete("/classTime/delete/"+timeid)
                .then(function (response) {
                    ALERTMSG.show(that,true,"课次删除成功!","success");
                    that.getinfor();
                }, function (err) {
                    ALERTMSG.show(that,true,"课次删除失败！请重新尝试！","error");
                    that.getinfor();
                    return false;
                });
        }

    }
}
