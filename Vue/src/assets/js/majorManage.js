import Nav from "@/views/inc/Nav.vue";
import { computed, ref } from 'vue'
import request from "@/utils/request";

export default {
    name: "userManage",
    components:{
        Nav
    },
    data(){
        return{
            depatment:'',
            depss:{},
            dialogVisible1:false,
            dialogVisible2:false,
            ruleForm1:{
                schoolId: '',
                schoolName: '',
                introduction:'',
            },
            ruleForm2:{
                majorId: '',
                majorName: '',
                school: '',
                introduction:'',
            },

            editRules1 :({
                schoolId: [
                    {
                        required: true,
                        message: '请输入学院代码',
                        trigger: 'blur'
                    },
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
                ],
                school: [
                    {
                        required: true,
                        message: '请选择所属学院',
                        trigger: 'blur'
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
                {},
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
        getDep:function(){
            request.get("/school/list").then(res=>{
                this.depss= res;
            })
        },
        getSchoolForm(){
            request.get("/school/list").then(res=>{
                this.tableData1=res;
            })
        },
        getMajorForm(){
            request.get("/major/list").then(res=>{
                this.tableData2=res;
            })
        },
        submitForm1() {
            this.$refs.ruleForm1.validate(valid => {
                if (valid) {
                    let params = new URLSearchParams();
                    params.append('schoolId', this.ruleForm1.schoolId);
                    params.append('schoolName', this.ruleForm1.schoolName);
                    params.append('introduction', this.ruleForm1.introduction);
                    this.$axios({
                        method: 'post',
                        url: '/api/school/add',
                        data: params
                    }).then(res => {
                            this.$message({
                                showClose: true,
                                message: '操作成功',
                                type: 'success',
                                onClose: () => {
                                    this.getSchoolForm()
                                }
                            });
                            this.dialogVisible1 = false;
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
        submitForm2() {
            this.$refs.ruleForm2.validate(valid => {
                if (valid) {
                    let params = new URLSearchParams();
                    params.append('majorId', this.ruleForm2.majorId);
                    params.append('majorName', this.ruleForm2.majorName);
                    params.append('school', this.ruleForm2.school);
                    params.append('introduction', this.ruleForm2.introduction);
                    this.$axios({
                        method: 'post',
                        url: '/api/major/add',
                        data: params
                    }).then(res => {
                            this.$message({
                                showClose: true,
                                message: '操作成功',
                                type: 'success',
                                onClose: () => {
                                    this.getMajorForm()
                                }
                            });
                            this.dialogVisible2 = false;
                        }
                    )
                } else {
                    this.$nextTick(() => {
                        this.scrollToTop(this.$refs.ruleForm2.$el)
                    })
                }
            })
        },
        scrollToTop (node) {
            const ChildHasError = Array.from(node.querySelectorAll('.is-error'))
            if (!ChildHasError.length) throw new Error('有错误，但是找不到错误位置')
        }
    }
}
//const search = ref('')
//const filterTableData = computed(() =>
  //  tableData.filter(
    //    (data) =>
      //      !search.value ||
        //    data.name.toLowerCase().includes(search.value.toLowerCase())
  //  )
//)
/*const handleEdit = (index: number, row: User) => {
    console.log(index, row)
}
const handleDelete = (index: number, row: User) => {
    console.log(index, row)
}*/
