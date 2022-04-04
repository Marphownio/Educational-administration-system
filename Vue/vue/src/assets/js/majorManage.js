import Nav from "@/views/inc/Nav.vue";

import { computed, ref } from 'vue'

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

            }),
            tableData:[
                {
                    id: 1,
                    college: '学院1',
                    major:'专业1'
                },
                {
                    id: 2,
                    college: '学院2',
                    major:'专业2'
                },

            ]
        }
    },
    created(){

    },

    methods:{
        submitForm(){

        },
        editHandle(id){
            this.$axios.get('/sys/menu/info/'+id).then(res=>{
                this.ruleForm=res.data.data;
                this.dialogVisible=true;
            })
        },
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
