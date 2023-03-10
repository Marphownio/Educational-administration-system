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
            search1:'',
            search2:'',
            depatment:'',
            depss:{},
            addschool:false,
            addmajor:false,
            updateschool:false,
            updatemajor:false,
            ruleForm1:{
                schoolId: '',
                schoolName: '',
                introduction:'',
            },
            ruleForm2:{
                majorId: '',
                majorName: '',
                schoolId: '',
                introduction:'',
            },
            editRules1 :({
                schoolId: [
                    {
                        required: true,
                        message: '请输入学院代码',
                        trigger: 'blur'
                    },
                    {validator: numbercheck,trigger: 'blur'}
                ],
                schoolName: [
                    {
                        required: true,
                        message: '请输入学院名',
                        trigger: 'blur'
                    },
                ],
            }),
            editRules2 :({
                majorId: [
                    {
                        required: true,
                        message: '请输入专业代码',
                        trigger: 'blur'
                    },
                    {validator: numbercheck,trigger: 'blur'}
                ],
                schoolId: [
                    {
                        required: true,
                        message: '请选择所属学院',
                        trigger: 'change',
                    }
                ],
                majorName: [
                    {
                        required: true,
                        message: '请输入专业名',
                        trigger: 'blur'
                    },
                ],
            }),
            tableData1:[
                {},
            ],
            tableData2:[
                {

                },
            ],
            filterSchooldata:[
                {

                }
            ],
            filterMajordata:[
                {

                }
            ]
        }
    },
    mounted() {
        this.getDep();
    },
    created(){
        this.getSchoolForm();
        this.getMajorForm();
    },

    methods:{
        getForm1(){
            this.filterSchooldata=computed(() =>
                this.tableData1.filter(
                    (data) =>
                        !this.search1 ||
                        data.schoolName.toLowerCase().includes(this.search1.toLowerCase())
                )
            )
        },
        getForm2(){
            this.filterMajordata=computed(() =>
                this.tableData2.filter(
                    (data) =>
                        !this.search2 ||
                        data.majorName.toLowerCase().includes(this.search2.toLowerCase())
                )
            )
        },
        refresh(){
            this.ruleForm1={};
            this.ruleForm1.introduction='';
            this.ruleForm2={};
            this.ruleForm2.introduction='';
        },
        getDep:function(){
            request.get("/school/list").then(res=>{
                this.depss= res;
            })
        },
        getSchoolForm(){
            request.get("/school/list").then(res=>{
                this.tableData1=res;
                if(this.tableData1)
                    this.getForm1();
            })
        },
        getMajorForm(){
            request.get("/major/list").then(res=>{
                this.tableData2=res;
                for(let i=0;i<Object.keys(this.tableData2).length;i++)
                {
                    if(this.tableData2[i].school!==null){
                        this.tableData2[i].schoolName=this.tableData2[i].school.schoolName;
                        this.tableData2[i].schoolId=this.tableData2[i].school.schoolId;
                }
                }
                if(this.tableData2){
                    this.getForm2();
                }
            })
        },
        submitaddschool() {
            this.$refs.ruleForm1.validate(valid => {
                if (valid) {
                    let params = {};
                    params.schoolId=this.ruleForm1.schoolId;
                    params.schoolName= this.ruleForm1.schoolName;
                    if(this.ruleForm1.introduction==='')
                        params.introduction='该学院暂无描述信息';
                    else
                        params.introduction=this.ruleForm1.introduction;
                    this.$axios({
                        method: 'post',
                        url: '/api/school/add',
                        data: params
                    }).then(res => {
                        if(res.data==='SUCCESS')
                        {
                            this.$message({
                                showClose: true,
                                message: '操作成功',
                                type: 'success',
                            });
                            this.getSchoolForm();
                            this.getDep();
                            this.addschool = false;
                        }
                        else if(res.data==='EXIST')
                        {
                            this.$message({
                                showClose: true,
                                message: '该学院代码已存在',
                                type: 'error',
                            });
                        }
                        else if(res.data==='FAILED')
                        {
                            this.$message({
                                showClose: true,
                                message: '该学院名称已存在',
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
        submitupdateschool() {
            this.$refs.ruleForm1.validate(valid => {
                if (valid) {
                    let params = {};
                    params.schoolId=this.ruleForm1.schoolId;
                    params.schoolName= this.ruleForm1.schoolName;
                    if(this.ruleForm1.introduction==='')
                        params.introduction='该学院暂无描述信息';
                    else
                        params.introduction=this.ruleForm1.introduction;
                    this.$axios({
                        method: 'put',
                        url: '/api/school/update',
                        data: params
                    }).then(res => {
                            if(res.data==='SUCCESS')
                            {
                                this.$message({
                                    showClose: true,
                                    message: '操作成功',
                                    type: 'success',
                                });
                                this.getSchoolForm();
                                this.getDep();
                                this.updateschool = false;
                            }
                            else if(res.data==='FAILED')
                            {
                                this.$message({
                                    showClose: true,
                                    message: '该学院名称已存在',
                                    type: 'error',
                                    onClose:()=>{
                                        this.getSchoolForm();
                                    }
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
        submitaddmajor() {
            this.$refs.ruleForm2.validate(valid => {
                if (valid) {
                    let params = {};
                    params.majorId=this.ruleForm2.majorId;
                    params.majorName=this.ruleForm2.majorName;
                    params.school= {'schoolId':this.ruleForm2.schoolId};
                    if(this.ruleForm2.introduction==='')
                        params.introduction='该专业暂无描述信息';
                    else
                        params.introduction= this.ruleForm2.introduction;
                    this.$axios({
                        method: 'post',
                        url: '/api/major/add',
                        data: params
                    }).then(res => {
                        if(res.data==='SUCCESS'){
                            this.$message({
                                showClose: true,
                                message: '操作成功',
                                type: 'success',
                            });
                            this.getMajorForm()
                            this.addmajor = false;
                        }
                        else if(res.data==='EXIST')
                        {
                            this.$message({
                                showClose: true,
                                message: '该专业代码已存在',
                                type: 'error',
                            });
                        }
                        else if(res.data==='FAILED')
                        {
                            this.$message({
                                showClose: true,
                                message: '该专业名称已存在',
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
        submitupdatemajor() {
            this.$refs.ruleForm2.validate(valid => {
                if (valid) {
                    let params = {};
                    params.majorId=this.ruleForm2.majorId;
                    params.majorName=this.ruleForm2.majorName;
                    params.school= {'schoolId':this.ruleForm2.schoolId};
                    if(this.ruleForm2.introduction==='')
                        params.introduction='该专业暂无描述信息';
                    else
                        params.introduction= this.ruleForm2.introduction;
                    this.$axios({
                        method: 'put',
                        url: '/api/major/update',
                        data: params
                    }).then(res => {
                            console.log(res);
                            if(res.data==='SUCCESS'){
                                this.$message({
                                    showClose: true,
                                    message: '操作成功',
                                    type: 'success',
                                });
                                this.getMajorForm()
                                this.updatemajor = false;
                            }
                            else if(res.data==='FAILED')
                            {
                                this.$message({
                                    showClose: true,
                                    message: '该专业名称已存在',
                                    type: 'error',
                                    onClose:()=>{
                                        this.getMajorForm();
                                    }
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
            this.updateschool=true;
            this.ruleForm1=obj;
        },
        delHandle1(id){
            this.$axios.delete("/api/school/delete/"+id).then(res=> {
                if(res.data==='SUCCESS'){
                    this.$message({
                        showClose: true,
                        message: '操作成功',
                        type: 'success',
                    });
                    this.getSchoolForm();
                    this.$router.go(0)
                }
                else if(res.data==='FAILED')
                {
                    this.$message({
                        showClose: true,
                        message: '该学院下仍存在专业，无法删除',
                        type: 'error',
                    });
                }
            })
        },
        editHandle2(obj){
            console.log(obj);
            this.updatemajor=true;
            this.ruleForm2=obj;
        },
        delHandle2(id){
            this.$axios.delete("/api/major/delete/"+id).then(res=> {
                if(res.data==='SUCCESS'){
                    this.$message({
                        showClose: true,
                        message: '操作成功',
                        type: 'success',
                    });
                    this.getMajorForm();
                    this.$router.go(0)
                }
                else if(res.data==='FAILED')
                {
                    this.$message({
                        showClose: true,
                        message: '该专业下仍存在用户，无法删除',
                        type: 'error',
                    });
                }
            })
        },
        scrollToTop (node) {
            const ChildHasError = Array.from(node.querySelectorAll('.is-error'))
            if (!ChildHasError.length) throw new Error('有错误，但是找不到错误位置')
        }
    }
}

