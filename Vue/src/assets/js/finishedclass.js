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
                { text: '2000-2001', value: '2000-2001' },
                { text: '2001-2002', value: '2001-2002' },
                { text: '2002-2003', value: '2002-2003' },
                { text: '2003-2004', value: '2003-2004' },
                { text: '2004-2005', value: '2004-2005' },
                { text: '2005-2006', value: '2005-2006' },
                { text: '2006-2007', value: '2006-2007' },
                { text: '2007-2008', value: '2007-2008' },
                { text: '2008-2009', value: '2008-2009' },
                { text: '2009-2010', value: '2009-2010' },
                { text: '2010-2011', value: '2010-2011' },
                { text: '2011-2012', value: '2011-2012' },
                { text: '2012-2013', value: '2012-2013' },
                { text: '2013-2014', value: '2013-2014' },
                { text: '2014-2015', value: '2014-2015' },
                { text: '2015-2016', value: '2015-2016' },
                { text: '2016-2017', value: '2016-2017' },
                { text: '2017-2018', value: '2017-2018' },
                { text: '2018-2019', value: '2018-2019' },
                { text: '2019-2020', value: '2019-2020' },
                { text: '2020-2021', value: '2020-2021' },
                { text: '2021-2022', value: '2021-2022' },
                { text: '2022-2023', value: '2022-2023' },
                { text: '2023-2024', value: '2023-2024' },
            ],
            terms:[
                { text: '1', value: '1' },
                { text: '2', value: '2' },
            ],
            finishedClassData:[],
        }
    },
    mounted() {},
    created(){
        this.getFinishedClass();
    },
    methods: {
        getFinishedClass(){
            const that = this;
            request.get("/student/course/completed").then(function (res){
                that.finishedClassData=res;
            },function (err){
                ALERTMSG.show(that,true,"获取已修课程失败!请再次尝试！","error");
            })
        },
        filterHandlerYear(value, row, column) {
            let property = column['property'];
            return row[property] === value;
        },
        filterHandlerTerm(value, row, column) {
            let property = column['property'];
            return row[property] === value;
        },
    }
}
