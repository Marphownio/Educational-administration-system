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
            ],
            terms:[
                { text: '1', value: '1' },
                { text: '2', value: '2' },
            ],
            finishedClassData:[{
                term: "1",
                academicYear: "2000-2001",
                capacity: 30,
                classArrangements: [
                    {
                        classArrangementId: 8,
                        building:
                            {
                                buildingId: 2,
                                buildingName: "第二教学楼"
                            },
                        classroom:
                            {
                                building: {buildingId: 2, buildingName: "第二教学楼"},
                                capacity: 80,
                                classroomId: 202
                            },
                        dayOfWeek: "TUESDAY",
                        classTimes: [{
                            classTimeId: 1,
                            startTimeHour: null,
                            startTimeMin: null,
                            endTimeHour: null,
                            endTimeMin: null
                        }],
                    },
                    {
                        classArrangementId: 2,
                        building:
                            {
                                buildingId: 1,
                                buildingName: "第一教学楼"
                            },
                        classroom:
                            {
                                building: {buildingId: 2, buildingName: "第二教学楼"},
                                capacity: 80,
                                classroomId: 102
                            },
                        dayOfWeek: "FRIDAY",
                        classTimes: [{
                            classTimeId: 1,
                            startTimeHour: null,
                            startTimeMin: null,
                            endTimeHour: null,
                            endTimeMin: null
                        },
                            {
                                classTimeId: 2,
                                startTimeHour: null,
                                startTimeMin: null,
                                endTimeHour: null,
                                endTimeMin: null
                            },
                            {
                                classTimeId: 3,
                                startTimeHour: null,
                                startTimeMin: null,
                                endTimeHour: null,
                                endTimeMin: null
                            },
                            {
                                classTimeId: 7,
                                startTimeHour: null,
                                startTimeMin: null,
                                endTimeHour: null,
                                endTimeMin: null
                            },],
                    },],
                introduction:'cdww',
                courseCategory:
                    {
                        school:{
                            schoolName:'计算机科学学院',
                        },
                        major:{
                            majorName:'软件工程',
                        },
                        courseCategoryNumber:'Math101',
                        courseCategoryId: 1,
                        courseName: "数学分析",
                        classHour: 5,
                        credit: 5,
                    },
                courseId: 2,
                courseNumber: 2,
                teacher: {username: "哈哈哈",},
            },
                {
                    term: "2",
                    academicYear: "2000-2001",
                    classArrangements: [
                        {
                            building:
                                {
                                    buildingId: 2,
                                    buildingName: "第二教学楼"
                                },
                            classroom:
                                {
                                    building: {buildingId: 2, buildingName: "第二教学楼"},
                                    capacity: 80,
                                    classroomId: 202
                                },
                            dayOfWeek: "MONDAY",
                            classTimes: [{
                                classTimeId: 5,
                                startTimeHour: null,
                                startTimeMin: null,
                                endTimeHour: null,
                                endTimeMin: null
                            }],
                        },],
                    courseCategory:
                        {
                            school:{
                                schoolName:'计算机科学学院',
                            },
                            major:{
                                majorName:'软件工程',
                            },
                            courseCategoryNumber:'Math102',
                            courseCategoryId: 1,
                            courseName: "概率论",
                            classHour: 5,
                            credit: 5,
                        },
                    courseId: 2,
                    courseNumber: 2,
                    teacher: {username: "哈哈哈",},
                },
                {
                    term: "2",
                    academicYear: "2002-2003",
                    classArrangements: [
                        {
                            building:
                                {
                                    buildingId: 2,
                                    buildingName: "第二教学楼"
                                },
                            classroom:
                                {
                                    building: {buildingId: 2, buildingName: "第二教学楼"},
                                    capacity: 80,
                                    classroomId: 202
                                },
                            dayOfWeek: "MONDAY",
                            classTimes: [{
                                classTimeId: 5,
                                startTimeHour: null,
                                startTimeMin: null,
                                endTimeHour: null,
                                endTimeMin: null
                            }],
                        },],
                    courseCategory:
                        {
                            school:{
                                schoolName:'计算机科学学院',
                            },
                            major:{
                                majorName:'软件工程',
                            },
                            courseCategoryNumber:'Math103',
                            courseCategoryId: 1,
                            courseName: "高等代数",
                            classHour: 5,
                            credit: 5,
                        },
                    courseId: 2,
                    courseNumber: 2,
                    teacher: {username: "哈哈哈",},
                },
            ],
        }
    },
    mounted() {},
    created(){
    },
    methods: {
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
