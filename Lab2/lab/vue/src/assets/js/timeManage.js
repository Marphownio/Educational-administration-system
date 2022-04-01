import Nav from "@/views/inc/Nav.vue";
export default {
    name: "userManage",
    components:{
        Nav
    },
    data(){
        return{
            tableData:[
                {
                    number: 1,
                    begin: '8:00',
                    end:'8:45'
                },
                {
                    number: 2,
                    begin: '8:55',
                    end:'9:40'
                },
                {
                    number: 3,
                    begin: '9:55',
                    end:'10:40'
                },
                {
                    number:4,
                    begin:'10:50',
                    end: '11:35'
                },
                {
                    number: 5,
                    begin: '11:45',
                    end:'12:30'
                },
                {
                    number: 6,
                    begin: '13:30',
                    end:'14:15'
                },
                {
                    number: 7,
                    begin: '14:25',
                    end:'15:10'
                },
                {
                    number: 8,
                    begin: '15:25',
                    end:'16:10'
                },
                {
                    number: 9,
                    begin: '16:20',
                    end:'17:05'
                },
                {
                    number: 10,
                    begin: '17:15',
                    end:'18:30'
                },
                {
                    number: 11,
                    begin: '18:30',
                    end:'19:15'
                },
                {
                    number: 12,
                    begin: '19:25',
                    end:'20:10'
                },
                {
                    number: 13,
                    begin: '20:20',
                    end:'21:05'
                },
            ]
        }
    },
    methods:{

    }
}
