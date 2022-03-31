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
