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
            editRules :({
                name: [
                    { required: true, message: '请输入姓名', trigger: 'blur' },
                    {pattern:'^[a-zA-Z\u4e00-\u9fa5]+$',message: '输入只能为中英文',trigger: 'change'}
                ],
                id: [
                    {
                        required: true,
                        message: '请输入学号/工号',
                        trigger: 'blur',
                    },
                    { type:'number',message:'输入只能为数字',trigger: 'change'}
                ],
                idNumber: [
                    {
                        required: true,
                        message: '请输入身份证号',
                        trigger: 'blur',
                    },
                    {pattern:'/(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)/',message:'输入格式不正确，请检查输入',trigger: 'blur'}
                ],
                phoneNumber:[
                    {pattern:'^1[0-9]{10}$',message:'请输入以1开头的11位数字',trigger:'blur',}
                ],
                email:[
                    {pattern:'/^([a-zA-Z0-9]+[-_\\.]?)+@[a-zA-Z0-9]+\\.[a-z]+$/',message:'邮箱格式不正确，请重新输入',trigger:'blur',}
                ],
                role: [
                    {
                        required: true,
                        message: '请选择角色',
                        trigger: 'blur',
                    },
                ]
            }),
            tableData:[
                {
                    id: 1,
                    name: '学院1',
                },
                {
                    id: 2,
                    name: '学院2',
                },
                {
                    id: 3,
                    name: '学院3',
                    children: [
                        {
                            id: 31,
                            name: '专业1',
                        },
                        {
                            id: 32,
                            name: '专业2',
                        },
                    ],
                },
                {
                    id: 4,
                    name: '学院4',
                },
            ]
        }
    },
    created(){

    },

    methods:{
        nameCheck () {
            const relu = "^[a-zA-Z\u4e00-\u9fa5]+$";
            const re = new RegExp(relu);
            if (this.value.search(re) !== -1){
            } else {
                message:'输入只能为中英文！'
            }
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
