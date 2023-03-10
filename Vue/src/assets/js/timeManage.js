import Nav from "@/views/inc/Nav.vue";
import request from "@/utils/request";
import ALERTMSG from "@/assets/js/alert";
export default {
    name: "timeManage",
    components:{
        Nav
    },
    data(){
        return{
            hours1:[
                {value: '0', label: '0',}, {value: '1', label: '1',},
                {value: '2', label: '2',}, {value: '3', label: '3',},
                {value: '4', label: '4',}, {value: '5', label: '5',},
                {value: '6', label: '6',}, {value: '7', label: '7',},
                {value: '8', label: '8',}, {value: '9', label: '9',},
                {value: '10', label: '10',}, {value: '11', label: '11',},
                {value: '12', label: '12',}, {value: '13', label: '13',},
                {value: '14', label: '14',}, {value: '15', label: '15',},
                {value: '16', label: '16',}, {value: '17', label: '17',},
                {value: '18', label: '18',}, {value: '19', label: '19',},
                {value: '20', label: '20',}, {value: '21', label: '21',},
                {value: '22', label: '22',}, {value: '23', label: '23',},
            ],
            hours2:[
                {value: '0', label: '0',}, {value: '1', label: '1',},
                {value: '2', label: '2',}, {value: '3', label: '3',},
                {value: '4', label: '4',}, {value: '5', label: '5',},
                {value: '6', label: '6',}, {value: '7', label: '7',},
                {value: '8', label: '8',}, {value: '9', label: '9',},
                {value: '10', label: '10',}, {value: '11', label: '11',},
                {value: '12', label: '12',}, {value: '13', label: '13',},
                {value: '14', label: '14',}, {value: '15', label: '15',},
                {value: '16', label: '16',}, {value: '17', label: '17',},
                {value: '18', label: '18',}, {value: '19', label: '19',},
                {value: '20', label: '20',}, {value: '21', label: '21',},
                {value: '22', label: '22',}, {value: '23', label: '23',},
            ],
            minutes1:[
                {value: '00', label: '00',}, {value: '01', label: '01',},
                {value: '02', label: '02',}, {value: '03', label: '03',},
                {value: '04', label: '04',}, {value: '05', label: '05',},
                {value: '06', label: '06',}, {value: '07', label: '07',},
                {value: '08', label: '08',}, {value: '09', label: '09',},
                {value: '10', label: '10',}, {value: '11', label: '11',},
                {value: '12', label: '12',}, {value: '13', label: '13',},
                {value: '14', label: '14',}, {value: '15', label: '15',},
                {value: '16', label: '16',}, {value: '17', label: '17',},
                {value: '18', label: '18',}, {value: '19', label: '19',},
                {value: '20', label: '20',}, {value: '21', label: '21',},
                {value: '22', label: '22',}, {value: '23', label: '23',},
                {value: '24', label: '24',}, {value: '25', label: '25',},
                {value: '26', label: '26',}, {value: '27', label: '27',},
                {value: '28', label: '28',}, {value: '29', label: '29',},
                {value: '30', label: '30',}, {value: '31', label: '32',},
                {value: '32', label: '32',}, {value: '33', label: '33',},
                {value: '34', label: '34',}, {value: '35', label: '35',},
                {value: '36', label: '36',}, {value: '37', label: '37',},
                {value: '38', label: '38',}, {value: '39', label: '39',},
                {value: '40', label: '40',}, {value: '41', label: '41',},
                {value: '42', label: '42',}, {value: '43', label: '43',},
                {value: '44', label: '44',}, {value: '45', label: '45',},
                {value: '46', label: '46',}, {value: '47', label: '47',},
                {value: '48', label: '48',}, {value: '49', label: '49',},
                {value: '50', label: '50',}, {value: '51', label: '51',},
                {value: '52', label: '52',}, {value: '53', label: '53',},
                {value: '54', label: '54',}, {value: '55', label: '55',},
                {value: '56', label: '56',}, {value: '57', label: '57',},
                {value: '58', label: '58',}, {value: '59', label: '59',},
            ],
            minutes2:[
                {value: '00', label: '00',}, {value: '01', label: '01',},
                {value: '02', label: '02',}, {value: '03', label: '03',},
                {value: '04', label: '04',}, {value: '05', label: '05',},
                {value: '06', label: '06',}, {value: '07', label: '07',},
                {value: '08', label: '08',}, {value: '09', label: '09',},
                {value: '10', label: '10',}, {value: '11', label: '11',},
                {value: '12', label: '12',}, {value: '13', label: '13',},
                {value: '14', label: '14',}, {value: '15', label: '15',},
                {value: '16', label: '16',}, {value: '17', label: '17',},
                {value: '18', label: '18',}, {value: '19', label: '19',},
                {value: '20', label: '20',}, {value: '21', label: '21',},
                {value: '22', label: '22',}, {value: '23', label: '23',},
                {value: '24', label: '24',}, {value: '25', label: '25',},
                {value: '26', label: '26',}, {value: '27', label: '27',},
                {value: '28', label: '28',}, {value: '29', label: '29',},
                {value: '30', label: '30',}, {value: '31', label: '32',},
                {value: '32', label: '32',}, {value: '33', label: '33',},
                {value: '34', label: '34',}, {value: '35', label: '35',},
                {value: '36', label: '36',}, {value: '37', label: '37',},
                {value: '38', label: '38',}, {value: '39', label: '39',},
                {value: '40', label: '40',}, {value: '41', label: '41',},
                {value: '42', label: '42',}, {value: '43', label: '43',},
                {value: '44', label: '44',}, {value: '45', label: '45',},
                {value: '46', label: '46',}, {value: '47', label: '47',},
                {value: '48', label: '48',}, {value: '49', label: '49',},
                {value: '50', label: '50',}, {value: '51', label: '51',},
                {value: '52', label: '52',}, {value: '53', label: '53',},
                {value: '54', label: '54',}, {value: '55', label: '55',},
                {value: '56', label: '56',}, {value: '57', label: '57',},
                {value: '58', label: '58',}, {value: '59', label: '59',},
            ],
            number:'',
            tableDatatime:[{},],
            dialogVisible1:false,
            applyform:{
                classTimeId:'',
                startTimeHour:'',
                startTimeMin:'',
                endTimeHour:'',
                endTimeMin:'',
            },
            ruleForm1:{
                classTimeId:'',
                startTimeHour:'',
                startTimeMin:'',
                endTimeHour:'',
                endTimeMin:'',
            },
            editRules1:({
                startTimeHour:[{required: true, message: '????????????????????????', trigger: 'blur'}],
                startTimeMin:[{required: true, message: '????????????????????????', trigger: 'blur'}],
                endTimeHour:[{required: true, message: '????????????????????????', trigger: 'blur'}],
                endTimeMin:[{required: true, message: '????????????????????????', trigger: 'blur'}],
            }),
            dialogVisible2:false,
            ruleForm2:{
                classTimeId:'',
                startTimeHour:'',
                startTimeMin:'',
                endTimeHour:'',
                endTimeMin:'',
            },
            lastclass:{
                endHour:'',
                endMin:'',
            },
            nextclass:{
                startHour:'24',
                startMin:'60',
            },
            editRules2:({
                startTimeHour:[{required: true, message: '????????????????????????', trigger: 'blur'}],
                startTimeMin:[{required: true, message: '????????????????????????', trigger: 'blur'}],
                endTimeHour:[{required: true, message: '????????????????????????', trigger: 'blur'}],
                endTimeMin:[{required: true, message: '????????????????????????', trigger: 'blur'}],
            }),
            dialogVisible0:false,
            dialogVisible00:false,
        }
    },
    computed:{
    },
    created(){
        this.getinfor();
    },
    methods:{
        getlastinfor(index){
            const that=this;
            if(index=='0'){
                that.lastclass.endHour='-1'
                that.lastclass.endMin='-1'
            }
            else{
                that.lastclass.endHour=that.tableDatatime[index-1].endTimeHour;
                that.lastclass.endMin=that.tableDatatime[index-1].endTimeMin;
            }
        },
        getnextinfor(index){
            const that=this;
            if(index==that.number+1){
                that.nextclass.startHour='25'
                that.nextclass.startMin='60'
            }
            else{
                that.nextclass.startHour=that.tableDatatime[index-1].startTimeHour;
                that.nextclass.startMin=that.tableDatatime[index-1].startTimeMin;
            }
        },
        applynew(){
            const that=this;
            that.dialogVisible1=true;
            that.ruleForm1.classTimeId=that.number+1;
            that.ruleForm1.startTimeHour='';
            that.ruleForm1.startTimeMin='';
            that.ruleForm1.endTimeHour='';
            that.ruleForm1.endTimeMin='';
            that.getlastinfor(that.ruleForm1.classTimeId-1);
            that.apllytimeedit();
        },
        apllytimeedit(){
            const that=this;
            //initial
            for(let i=0;i<that.hours1.length;i++){
                that.hours1[i].disabled=false;
                that.hours2[i].disabled=false;
            }
            for(let i=0;i<that.minutes1.length;i++){
                that.minutes1[i].disabled=false;
                that.minutes2[i].disabled=false;
            }
            //starthours
            for(let i=0;i<that.lastclass.endHour;i++){
                that.hours1[i].disabled=true;
            }
            if(that.ruleForm1.endTimeHour!==''){
                for(let i=that.ruleForm1.endTimeHour;i<that.hours1.length;i++){
                    if(i==that.ruleForm1.endTimeHour){
                        continue;
                    }
                    that.hours1[i].disabled=true;
                }
            }
            //startmin
            if(that.ruleForm1.startTimeHour==that.lastclass.endHour){
                for(let i=0;i<=Number(that.lastclass.endMin);i++){
                    that.minutes1[i].disabled=true;
                }
            }
            if(that.ruleForm1.startTimeHour==that.ruleForm1.endTimeHour&&that.ruleForm1.endTimeMin!==''){
                for(let i=Number(that.ruleForm1.endTimeMin);i<that.minutes1.length;i++){
                    that.minutes1[i].disabled=true;
                }
            }
            //endhour
            for(let i=0;i<that.ruleForm1.startTimeHour;i++){
                that.hours2[i].disabled=true;
            }
            //endmin
            if(that.ruleForm1.endTimeHour==that.ruleForm1.startTimeHour){
                for(let i=0;i<=Number(that.ruleForm1.startTimeMin);i++){
                    that.minutes2[i].disabled=true;
                }
            }
            if(that.ruleForm1.startTimeHour==that.ruleForm1.endTimeHour){
                if(that.ruleForm1.endTimeMin!=''&&that.ruleForm1.startTimeMin>=that.ruleForm1.endTimeMin){
                    // console.log("");
                    that.ruleForm1.endTimeMin='';
                }
            }
        },
        submit_apply:function(){
            this.$refs.ruleForm1.validate((valid) => {
                    if (valid) {
                        const that=this;
                        request.post("/classTime/add",this.ruleForm1)
                            .then(function (response) {
                                ALERTMSG.show(that,true,"??????????????????!","success");
                                that.getinfor();
                                }, function (err) {
                                ALERTMSG.show(that,true,"???????????????????????????????????????","error");
                                that.getinfor();
                                return false;
                            });
                    }
                    else{
                        return false;
                    }
            })
        },
        getinfor:function(){
            request.get("/classTime/list").then(res=>{
                this.tableDatatime= res;
            });
            this.getSummaries();
        },
        getSummaries(){
            if(this.tableDatatime.length==null){
                this.number='0';
            }
            else {
                this.number=this.tableDatatime.length;
            }
        },
        openedit(row,index){
            this.dialogVisible2=true;
            this.ruleForm2.classTimeId=index;
            this.ruleForm2.startTimeHour=row.startTimeHour;
            this.ruleForm2.startTimeMin=row.startTimeMin;
            this.ruleForm2.endTimeHour=row.endTimeHour;
            this.ruleForm2.endTimeMin=row.endTimeMin;
            this.getlastinfor(index-1);
            this.getnextinfor(index+1);
            this.edittime();
        },
        edittime(){
            const that=this;
            //initial
            for(let i=0;i<that.hours1.length;i++){
                that.hours1[i].disabled=false;
                that.hours2[i].disabled=false;
            }
            for(let i=0;i<that.minutes1.length;i++){
                that.minutes1[i].disabled=false;
                that.minutes2[i].disabled=false;
            }
            //starthours
            for(let i=0;i<that.lastclass.endHour;i++){
                that.hours1[i].disabled=true;
            }
            for(let i=that.ruleForm2.endTimeHour;i<that.hours1.length;i++){
                if(i==that.ruleForm2.endTimeHour){
                    continue;
                }
                that.hours1[i].disabled=true;
            }
            //startmin
            if(that.ruleForm2.startTimeHour==that.lastclass.endHour){
                for(let i=0;i<=Number(that.lastclass.endMin);i++){
                    that.minutes1[i].disabled=true;
                }
            }
            if(that.ruleForm2.startTimeHour==that.ruleForm2.endTimeHour&&that.ruleForm2.endTimeMin!==''){
                for(let i=Number(that.ruleForm2.endTimeMin);i<that.minutes1.length;i++){
                    that.minutes1[i].disabled=true;
                }
            }
            //endhour
            for(let i=0;i<that.ruleForm2.startTimeHour;i++){
                that.hours2[i].disabled=true;
            }
            for(let i=Number(that.nextclass.startHour);i<that.hours2.length;i++){
                if(i==that.nextclass.startHour){
                    continue;
                }
                that.hours2[i].disabled=true;
            }
            //endmin
            if(that.ruleForm2.endTimeHour==that.ruleForm2.startTimeHour){
                for(let i=0;i<=Number(that.ruleForm2.startTimeMin);i++){
                    that.minutes2[i].disabled=true;
                }
            }
            if(that.ruleForm2.endTimeHour==that.nextclass.startHour){
                for(let i=Number(that.nextclass.startMin);i<that.minutes2.length;i++){
                    that.minutes2[i].disabled=true;
                }
            }
            if(that.ruleForm2.startTimeHour==that.ruleForm2.endTimeHour){
                if(that.ruleForm2.endTimeMin!=''&&that.ruleForm2.startTimeMin>=that.ruleForm2.endTimeMin){
                    that.ruleForm2.endTimeMin=null;
                }
            }

        },
        submitEdit(){
            const that=this;
            this.$refs.ruleForm2.validate((valid) => {
                if (valid) {
                    request.put("/classTime/update",this.ruleForm2)
                        .then(function (response) {
                            ALERTMSG.show(that,true,"??????????????????!","success");
                            that.getinfor();
                        }, function (err) {
                            ALERTMSG.show(that,true,"???????????????????????????????????????","error");
                            that.getinfor();
                            return false;
                        });
                }
                else{
                    return false;
                }
            })
        },
        deletetime(){
            if(this.number===0){
                this.dialogVisible00=true;
                return false;
            }
            else{
                this.dialogVisible0=true;
                return true;
            }
        },
        submitdelete() {
            const that=this;
            let timeid=that.number;
            request.delete("/classTime/delete/"+timeid)
                .then(function (response) {
                    ALERTMSG.show(that,true,"??????????????????!","success");
                    that.getinfor();
                }, function (err) {
                    ALERTMSG.show(that,true,"???????????????????????????????????????","error");
                    that.getinfor();
                    return false;
                });
        },
    }
}
