import Nav from "@/views/inc/Nav.vue";
import request from "@/utils/request";
import ALERTMSG from "@/assets/js/alert";
export default {
    name: "myclass",
    components:{
        Nav
    },
    data(){
        const tableData = [
            {
                date: '2016-05-03',
                name: 'Tom',
                address: 'No. 189, Grove St, Los Angeles',
            },
            {
                date: '2016-05-02',
                name: 'Tom',
                address: 'No. 189, Grove St, Los Angeles',
            },
            {
                date: '2016-05-04',
                name: 'Tom',
                address: 'No. 189, Grove St, Los Angeles',
            },
            {
                date: '2016-05-01',
                name: 'Tom',
                address: 'No. 189, Grove St, Los Angeles',
            },
        ]
        return {}
    },
    computed:{
    },
    created(){
    },
    methods:{

    }
}
