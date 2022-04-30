import Nav from "@/views/inc/Nav.vue";
import request from "@/utils/request";
import ALERTMSG from "@/assets/js/alert";

export default {
    name: "finishedclass",
    components:{
        Nav
    },
    data(){
        return{
            years:[
                { text: '2016', value: '2016' },
                { text: '2017', value: '2017' },
                { text: '2018', value: '2018' },
                { text: '2019', value: '2019' },
            ],
            terms:[
                { text: '1', value: '1' },
                { text: '2', value: '2' },

            ],
        }
    },
    mounted() {},
    created(){
    },
    methods:{
    }
}
