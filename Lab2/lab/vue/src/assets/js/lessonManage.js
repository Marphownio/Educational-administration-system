import Nav from "@/views/inc/Nav.vue";
export default {
    name: "userManage",
    components:{
        Nav
    },
    data(){
        return{
            dialogVisible:false,
            ruleForm:{},
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

    },

    methods:{
        submitForm(){

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
            // 找到第一个错误位置
            const FirstErrorNode = ChildHasError[0]
        }
    }
}
import { computed, ref } from 'vue'
const search = ref('')
const filterTableData = computed(() =>
    tableData.filter(
        (data) =>
            !search.value ||
            data.name.toLowerCase().includes(search.value.toLowerCase())
    )
)
