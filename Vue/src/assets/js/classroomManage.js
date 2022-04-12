import Nav from "@/views/inc/Nav.vue";
import { computed } from 'vue';
import request from "@/utils/request";


export default {
    name: "userManage",
    components:{
        Nav
    },
    data(){
        let numbercheck=(rule,value,callback)=>{
            if(isNaN(value))
            {
                callback(new Error('输入只能为数字'));
                return false;
            }
            else return true;
        };
        return{
            search:'',
            addbuilding:false,
            addclassroom:false,
            updatebuilding:false,
            updateclassroom:false,
            ruleForm1:{
                buildingId: '',
                buildingName: '',
            },
            ruleForm2:{
                classroomId: '',
                buildingId: '',
            },
            editRules1 :({
                buildingId: [
                    {
                        required: true,
                        message: '请输入教学楼编号',
                        trigger: 'blur'
                    },
                    {validator: numbercheck,trigger: 'blur'}
                ],
                buildingName: [
                    {
                        required: true,
                        message: '请输入教学楼名',
                        trigger: 'blur'
                    },
                ],
            }),
            editRules2 :({
                classroomId: [
                    {
                        required: true,
                        message: '请输入教室编号',
                        trigger: 'blur'
                    },
                    {validator: numbercheck,trigger: 'blur'}
                ],
                buildingId: [
                    {
                        required: true,
                        message: '请输入所属教学楼',
                        trigger: 'blur'
                    },
                ],
            }),
            buildingData:[

            ],
            classroomData:[

            ],
            filterclassroomdata:[

            ]
        }
    },
    created() {
      this.getClassroomForm();
      this.getBuildingForm();
    },
    methods:{
        refresh(){
            this.ruleForm1={};
            this.ruleForm2={};
        },
        getForm(){
            this.filterclassroomdata=computed(() =>
                this.classroomData.filter(
                    (data) =>
                        !this.search ||
                        data.buildingName.toLowerCase().includes(this.search.toLowerCase())
                )
            )
        },
        getBuildingForm(){
            request.get("/building/list").then(res=>{
                this.buildingData=res;
            })
        },
        getClassroomForm(){
            request.get("/building/classroomlist").then(res=>{
                this.classroomData=res;
                for(let i=0;i<Object.keys(this.classroomData).length;i++)
                {
                    if(this.classroomData[i].building!==null){
                        this.classroomData[i].buildingName=this.classroomData[i].building.buildingName;
                        this.classroomData[i].buildingId=this.classroomData[i].building.buildingId;
                    }
                }
                if(this.classroomData){
                    this.getForm();
                }
            })
        },
        submitaddbuilding() {
            this.$refs.ruleForm1.validate(valid => {
                if (valid) {
                    let params = new URLSearchParams();
                    params.append('buildingId', this.ruleForm1.buildingId);
                    params.append('buildingName', this.ruleForm1.buildingName);
                    this.$axios({
                        method: 'post',
                        url: '/api/building/addBuilding',
                        data: params
                    }).then(res => {
                            if(res.data==='SUCCESS')
                            {
                                this.$message({
                                    showClose: true,
                                    message: '操作成功',
                                    type: 'success',
                                });
                                this.getBuildingForm();
                                this.addbuilding = false;
                            }
                            else if(res.data==='EXIST')
                            {
                                this.$message({
                                    showClose: true,
                                    message: '该教学楼编号已存在',
                                    type: 'error',
                                });
                            }
                            else if(res.data==='FAILED')
                            {
                                this.$message({
                                    showClose: true,
                                    message: '操作失败',
                                    type: 'error',
                                });
                            }
                        }
                    )
                }
                else {
                    this.$nextTick(() => {
                        this.scrollToTop(this.$refs.ruleForm1.$el)
                    })
                }
            })
        },
        submitupdatebuilding() {
            this.$refs.ruleForm1.validate(valid => {
                if (valid) {
                    let params = new URLSearchParams();
                    params.append('buildingId', this.ruleForm1.buildingId);
                    params.append('buildingName', this.ruleForm1.buildingName);
                    this.$axios({
                        method: 'put',
                        url: '/api/building/update',
                        data: params
                    }).then(res => {
                            if(res.data==='SUCCESS')
                            {
                                this.$message({
                                    showClose: true,
                                    message: '操作成功',
                                    type: 'success',
                                });
                                this.getBuildingForm();
                                this.updatebuilding = false;
                            }
                            else if(res.data==='FAILED')
                            {
                                this.$message({
                                    showClose: true,
                                    message: '操作失败',
                                    type: 'error',
                                });
                            }
                        }
                    )
                }
                else {
                    this.$nextTick(() => {
                        this.scrollToTop(this.$refs.ruleForm1.$el)
                    })
                }
            })
        },
        submitaddclassroom() {
            this.$refs.ruleForm2.validate(valid => {
                if (valid) {
                    let params = new URLSearchParams();
                    params.append('classroomId', this.ruleForm2.classroomId);
                    params.append('building', JSON.parse(this.ruleForm2.buildingId));
                    this.$axios({
                        method: 'post',
                        url: '/api/building/addclassroom',
                        data: params
                    }).then(res => {
                            if(res.data==='SUCCESS'){
                                this.$message({
                                    showClose: true,
                                    message: '操作成功',
                                    type: 'success',
                                });
                                this.getClassroomForm()
                                this.addclassroom = false;
                            }
                            else if(res.data==='EXIST')
                            {
                                this.$message({
                                    showClose: true,
                                    message: '该教室编码已存在',
                                    type: 'error',
                                });
                            }
                            else if(res.data==='FAILED')
                            {
                                this.$message({
                                    showClose: true,
                                    message: '操作失败',
                                    type: 'error',
                                });
                            }
                        }
                    )
                } else {
                    this.$nextTick(() => {
                        this.scrollToTop(this.$refs.ruleForm2.$el)
                    })
                }
            })
        },
        submitupdateclassroom() {
            this.$refs.ruleForm2.validate(valid => {
                if (valid) {
                    let params = new URLSearchParams();
                    params.append('classroomId', this.ruleForm2.classroomId);
                    params.append('building', JSON.parse(this.ruleForm2.buildingId));
                    this.$axios({
                        method: 'put',
                        url: '/api/building/updateclassroom',
                        data: params
                    }).then(res => {
                            console.log(res);
                            if(res.data==='SUCCESS'){
                                this.$message({
                                    showClose: true,
                                    message: '操作成功',
                                    type: 'success',
                                });
                                this.getClassroomForm()
                                this.updateclassroom = false;
                            }
                            else if(res.data==='FAILED')
                            {
                                this.$message({
                                    showClose: true,
                                    message: '操作失败',
                                    type: 'error',
                                });
                            }
                        }
                    )
                } else {
                    this.$nextTick(() => {
                        this.scrollToTop(this.$refs.ruleForm2.$el)
                    })
                }
            })
        },
        editHandle1(obj){
            this.updatebuilding=true;
            this.ruleForm1=obj;
        },
        delHandle1(id){
            this.$axios.delete("/api/building/delete/"+id).then(res=> {
                this.$message({
                    showClose: true,
                    message: '操作成功',
                    type: 'success',
                })
                this.getBuildingForm();
                this.getClassroomForm();
                this.$router.go(0)
            })
        },
        editHandle2(obj){
            this.updateclassroom=true;
            this.ruleForm2=obj;
        },
        delHandle2(id){
            this.$axios.delete("/api/building/deleteclassroom/"+id).then(res=> {
                this.$message({
                    showClose: true,
                    message: '操作成功',
                    type: 'success',
                })
                this.getClassroomForm();
                this.$router.go(0)
            })
        },
        scrollToTop (node) {
            const ChildHasError = Array.from(node.querySelectorAll('.is-error'))
            if (!ChildHasError.length) throw new Error('有错误，但是找不到错误位置')
        }
    }
}
