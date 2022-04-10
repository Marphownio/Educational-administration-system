import Nav from "@/views/inc/Nav.vue";
export default {
    name: "userManage",
    components:{
        Nav
    },
    data(){
        return{
            addcourse:false,
            updatecourse:false,
            checkcourse:false,
            schooldata:[],
            teacherdata:[],
            ruleForm1:{},
            ruleForm2:{

            },
            editRules1 :({
                courseId: [
                    {
                        required: true,
                        message: '请输入课程代码',
                        trigger: 'blur'
                    },
                ],
                courseName: [
                    {
                        required: true,
                        message: '请输入课程名',
                        trigger: 'blur',
                    },
                ],
                school: [
                    {
                        required: true,
                        message: '请输入开课院系',
                        trigger: 'blur',
                    },
                ],
                teacher:[
                    {
                        required: true,
                        message: '请输入任课教师',
                        trigger: 'blur',
                    },
                ],
                courseHour:[
                    {
                        required: true,
                        message: '请输入学时',
                        trigger: 'blur',
                    },
                ],
                credit: [
                    {
                        required: true,
                        message: '请选择学分',
                        trigger: 'change',
                    },
                ],
                classTime:[
                    {
                        required: true,
                        message: '请输入上课时间',
                        trigger: 'blur',
                    },
                ],
                classPlace:[
                    {
                        required: true,
                        message: '请输入上课地点',
                        trigger: 'blur',
                    },
                ],
                capacity:[
                    {
                        required: true,
                        message: '请输入选课容量',
                        trigger: 'blur',
                    },
                ],
            }),
            tableData:[
                {
                    id: 1,
                    name: '课程1',
                },
                {
                    id: 2,
                    name: '课程2',
                },
            ],
            applyData:[
                {
                    request:2,
                    id: 1,
                    name: '课程1',
                },
                {
                    request:1,
                    id: 2,
                    name: '课程2',
                },
            ]
        }
    },
    created(){
        this.getSchool();
        this.getTeacher();
    },
    methods:{
        getSchool:function(){
            request.get("/school/list").then(res=>{
                this.schooldata= res;
            })
        },
        getTeacher:function(){
            request.get("/user/teacher/list").then(res=>{
                this.teacherdata= res;
            })
        },
        getCourseForm(){
            request.get("/course/list").then(res=>{
                this.tableData=res;
                for(let i=0;i<Object.keys(res).length;i++)
                {
                    this.tableData[i].school=res[i].school.key[schoolName];
                    this.tableData[i].major=res[i].major;
                    console.log(JSON.parse(res[i].school))
                }
            })
        },
        submitForm(){
            this.$refs.ruleForm.validate(valid=>{
                if(valid){
                    let params = new URLSearchParams();
                    params.append('courseId', this.ruleForm1.courseId);
                    params.append('courseName', this.ruleForm1.courseName);
                    params.append('school', JSON.parse(this.ruleForm1.school));
                    params.append('teacher', JSON.parse(this.ruleForm1.teacher));
                    params.append('courseHour', this.ruleForm1.courseHour);
                    params.append('credit', this.ruleForm1.credit);
                    params.append('classTime', this.ruleForm1.classTime);
                    params.append('classPlace', this.ruleForm1.classPlace);
                    params.append('capacity', this.ruleForm1.capacity);
                    params.append('introduction', this.ruleForm1.introduction);
                    this.$axios({
                        method: 'post',
                        url:'/api/course/add',
                        data:params,

                    }).then(res=>{
                            this.$message({
                                showClose: true,
                                message: '操作成功',
                                type: 'success',
                                onClose:()=>{
                                    this.getCourseForm()
                                }
                            });
                            this.dialogVisible=false;
                        }
                    )
                }
                else{
                    this.$nextTick(() => {
                        this.scrollToTop(this.$refs.ruleForm.$el)
                    })
                }
            })
        },
        rejectForm(){

        },
        handleChange(file) {
            this.fileTemp = file.raw
            if (this.fileTemp) {
                if ((this.fileTemp.type === '.csv') || (this.fileTemp.type === 'application/vnd.ms-excel')) {
                    this.importcsv(file.raw)
                } else {
                    this.$message({
                        type: 'warning',
                        message: '附件格式错误，请删除后重新上传！'
                    })
                }
            } else {
                this.$message({
                    type: 'warning',
                    message: '请上传附件！'
                })
            }
        },
        importcsv (obj) {
            Papa.parse(obj, {
                complete (results) {
                    console.log(results)//调试查看csv文件的数据
                    let data = []
                    //遍历csv文件中的数据，存放到data中
                    for (let i = 0; i < results.data.length; i++) {
                        let obj = {}
                        obj.number = results.data[i][0]
                        obj.name = results.data[i][1]
                        obj.nameRemark = results.data[i][2]
                        obj.index = results.data[i][3]
                        data.push(obj)
                    }
                    data.splice(0, 1)//将数组第一位的表格名称去除
                    console.log('data', data)
                    this.tableData = data//将数据放入要展示的表格中
                }
            })
        },
        editHandle(id){
            this.$axios.get('/sys/menu/info/'+id).then(res=>{
                this.ruleForm=res.data.data;
                this.dialogVisible=true;
            })
        },
        scrollToTop (node) {
            const ChildHasError = Array.from(node.querySelectorAll('.is-error'))
            if (!ChildHasError.length) throw new Error('有错误，但是找不到错误位置')
        }
    }
}
import { computed, ref } from 'vue'
import request from "@/utils/request";
const search = ref('')
const filterTableData = computed(() =>
    tableData.filter(
        (data) =>
            !search.value ||
            data.name.toLowerCase().includes(search.value.toLowerCase())
    )
)
