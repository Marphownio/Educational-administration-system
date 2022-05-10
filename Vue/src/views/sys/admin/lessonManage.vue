<template>
    <head>
        <meta charset="UTF-8">
        <title>课程管理</title>
    </head>

    <body>

    <Nav></Nav>

    <div class="void">
        <span></span>
    </div>

    <div class="showlist">
        <el-form :inline="true" >
            <el-form-item>
                <el-button type="primary" @click="addcourse=true">添加课程</el-button>
                <el-upload
                        class="upload-demo"
                        action="http://localhost/api/course/batchimport"
                        :on-success="getCourseForm"
                        method="post"
                        enctype=“multipart/form-data”
                        accept=".csv"
                        >
                    <el-button type="primary" style="margin-left: 20px;margin-right: 20px;margin-top: 10px">通过csv文件添加</el-button>
                </el-upload>
                <el-button type="primary" @click="checkcourse=true">审核课程申请</el-button>
            </el-form-item>
        </el-form>
        <el-table :data="tableData" >
            <el-table-column prop="courseId" label="" width="120px" v-if="false"/>
            <el-table-column prop="academicYear" label="" width="120px" v-if="false"/>
            <el-table-column prop="term" label="" width="120px" v-if="false"/>
            <el-table-column prop="courseCategoryId" label="" width="120px" v-if="false"/>
            <el-table-column prop="courseNumber" label="课程代码" width="120px"/>
            <el-table-column prop="courseName" label="课程名称" width="120px" />
            <el-table-column prop="schoolName" label="开课院系" width="120px"/>
            <el-table-column prop="schoolId" label="学院代码" width="120px"/>
            <el-table-column prop="majorName" label="所属专业" width="120px"/>
            <el-table-column prop="majorId" label="专业代码" width="120px"/>
            <el-table-column prop="classHour" label="学时" width="120px"/>
            <el-table-column prop="credit" label="学分" width="120px"/>
            <el-table-column prop="teacherName" label="任课教师" width="120px"/>
            <el-table-column prop="teacherId" label="教师工号" width="120px"/>
            <el-table-column prop="capacity" label="选课容量" width="120px"/>
            <el-table-column prop="introduction" label="课程介绍" />
            <el-table-column v-slot="scope" fixed="right" prop="icon" label="操作" width="170px">
                <div>
                    <el-button @click="editHandle(scope.row)">编辑</el-button>
                    <el-button type="danger" @click="delHandle(scope.row.courseId)">删除</el-button>
                </div>
            </el-table-column>
        </el-table>


        <el-dialog
                id="apply"
                v-model="checkcourse"
                title="课程申请"
                width="80%"
        >
            <el-table :data="applicationData" >
                <el-table-column fixed prop="type" label="请求" width="120px">
                <template v-slot="scope">
                    <el-tag v-if="scope.row.type==='ADD'" type="success" >添加</el-tag>
                    <el-tag v-if="scope.row.type==='UPDATE'" >修改</el-tag>
                    <el-tag v-if="scope.row.type==='DELETE'" type="danger" >删除</el-tag>
                </template>
                </el-table-column>
                <el-table-column prop="applicationId" v-if="false" />
                <el-table-column prop="courseName" label="课程名称" width="120px" />
                <el-table-column prop="schoolName" label="开课院系" width="120px"/>
                <el-table-column prop="schoolId" label="学院代码" width="120px"/>
                <el-table-column prop="majorName" label="所属专业" width="120px"/>
                <el-table-column prop="majorId" label="专业代码" width="120px"/>
                <el-table-column prop="classHour" label="学时" width="120px"/>
                <el-table-column prop="credit" label="学分" width="120px"/>
                <el-table-column prop="teacherName" label="任课教师" width="120px"/>
                <el-table-column prop="teacherId" label="教师工号" width="120px"/>
                <el-table-column prop="courseTime" label="上课时间" width="120px"/>
                <el-table-column prop="coursePlace" label="上课地点" width="120px"/>
                <el-table-column prop="capacity" label="选课容量" width="120px"/>
                <el-table-column prop="introduction" label="课程介绍" />
                <el-table-column v-slot="scope" fixed="right" prop="icon" label="操作" width="170px">
                    <div>
                        <el-button @click="passApplication(scope.row.applicationId)">同意</el-button>
                        <el-button type="danger" @click="rejectApplication(scope.row.applicationId)">拒绝</el-button>
                    </div>
                </el-table-column>
            </el-table>
        </el-dialog>

        <el-dialog
                id="add"
                v-model="addcourse"
                title="添加课程"
                width="800px"
        >
            <el-form
                    ref="ruleForm1"
                    :model="ruleForm1"
                    :rules="editRules1"
                    label-width="120px"
                    class="demo-ruleForm"
            >
                <el-form-item label="所属课程类" prop="courseCategory">
                    <el-select v-model="ruleForm1.courseCategory" placeholder="选择所属课程类" @change="fillData" clearable value-key="courseName">
                        <el-option v-for="item in courseCategoryData" :key="item.courseName" :label="item.courseName" :value="item" />
                    </el-select>
                </el-form-item>
                <el-form-item label="课程代码" prop="courseNumber">
                    <el-input v-model="ruleForm1.courseNumber" style="width: 220px"></el-input>
                </el-form-item>
                <el-form-item label="课程名" prop="courseName" @change="changeCategory">
                    <el-input v-model="ruleForm1.courseName" style="width: 220px"></el-input>
                </el-form-item>
                <el-form-item label="开课院系" prop="schoolId">
                    <el-select v-model="ruleForm1.schoolId" placeholder="选择学院" @change="getMajor">
                        <el-option v-for="item in schooldata" :key="item.schoolName" :label="item.schoolName" :value="item.schoolId" />
                    </el-select>
                </el-form-item>
                <el-form-item label="所属专业" prop="majorId" @change="changeCategory">
                    <el-select v-model="ruleForm1.majorId" placeholder="选择专业">
                        <el-option v-for="item in majordata" :key="item.majorName" :label="item.majorName" :value="item.majorId" />
                    </el-select>
                </el-form-item>
                <el-form-item label="任课教师" prop="teacherId" @change="changeCategory">
                    <el-select v-model="ruleForm1.teacherId" placeholder="选择教师">
                        <el-option v-for="item in teacherdata" :key="item.username" :label="item.username+item.userId" :value="item.userId" />
                    </el-select>
                </el-form-item>
                <el-form-item label="选课开放专业" prop="openToMajors" :rules="rule.openToMajors">
                    <el-select
                            multiple
                            filterable
                            v-model='ruleForm1.openToMajors'
                            placeholder='请选择'
                            collapse-tags
                            clearable
                            @change='changeSelect' @remove-tag='removeTag' >
                        <el-option label='全选' value='全选' @click.native='selectAll'></el-option>
                        <el-option v-for='item in majorListData' :key='item.majorName' :label='item.majorName' :value='item.majorId'></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="学时" prop="classHour" @change="changeCategory">
                    <el-input v-model="ruleForm1.classHour" style="width: 220px"></el-input>
                </el-form-item>
                <el-form-item label="学分" prop="credit" @change="changeCategory">
                    <el-input v-model="ruleForm1.credit" style="width: 220px"></el-input>
                </el-form-item>
                <el-row  v-for="(item, index) in ruleForm1.arrangement" label="课时安排" :key="index" gutter="0">
                    <span>
                        <el-form-item   :prop="'arrangement[' + index + '].buildingId'" :rules="rule.arrangement">
                        <el-select v-model="ruleForm1.arrangement[index].buildingId" prop="'buildingId'+index" placeholder="教学楼"  @change="getClassroom(index,ruleForm1.arrangement[index].buildingId)" style="margin:0">
                            <el-option v-for="item in buildingData" :key="item.buildingId" :label="item.buildingName" :value="item.buildingId" />
                        </el-select>
                        </el-form-item>
                    </span>
                    <span>
                        <el-form-item  :prop="'arrangement[' + index + '].classroom'"  :rules="rule.arrangement">
                        <el-select v-model="ruleForm1.arrangement[index].classroom" prop="'classroom'+index" placeholder="教室" value-key="classroomId">
                            <el-option v-for="item in classroomData[index]" :key="item.classroomId" :label="item.classroomId" :value="item" />
                        </el-select>
                        </el-form-item>
                    </span>
                    <span>
                        <el-form-item  :prop="'arrangement[' + index + '].dayOfWeek'"  :rules="rule.arrangement">
                        <el-select v-model="ruleForm1.arrangement[index].dayOfWeek" prop="'dayofweek'+index" placeholder="周几" >
                            <el-option v-for="item in dayofweekData" :key="item.value" :label="item.name" :value="item.value" />
                        </el-select>
                        </el-form-item>
                    </span>
                    <span>
                        <el-form-item  :prop="'arrangement[' + index + '].classTimeId'" :rules="rule.arrangement" >
                        <el-select v-model="ruleForm1.arrangement[index].classTimeId" prop="'classTimeId'+index" placeholder="节次" multiple clearable >
                            <el-option v-for="item in timeData" :key="item.classTimeId" :label="item.classTimeId" :value="item.classTimeId" />
                        </el-select>
                        </el-form-item>
                    </span>
                    <span style="margin-left: 20px">
                        <el-button @click.prevent="remove(item)" >删除</el-button>
                    </span>
                </el-row>
                <el-form-item>
                <el-button @click="addarrangement">新增安排</el-button>
                </el-form-item>
                <el-form-item label="选课容量" prop="capacity">
                    <el-input v-model="ruleForm1.capacity" style="width: 220px"></el-input>
                </el-form-item>
                <el-form-item label="课程介绍" prop="introduction">
                    <el-input v-model="ruleForm1.introduction"></el-input>
                </el-form-item>

                <el-form-item>
                    <el-button type="primary" @click="submitaddcourse" >添加</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>

        <el-dialog
                id="update"
                v-model="updatecourse"
                title="编辑课程信息"
                width="800px"
                @close="refresh"
        >
            <el-form
                    ref="ruleForm1"
                    :model="ruleForm1"
                    :rules="editRules1"
                    label-width="120px"
                    class="demo-ruleForm"
            >
                <el-form-item label="课程代码" prop="courseId">
                    <el-input v-model="ruleForm1.courseId"></el-input>
                </el-form-item>
                <el-form-item label="课程名" prop="courseName">
                    <el-input v-model="ruleForm1.courseName"></el-input>
                </el-form-item>
                <el-form-item label="开课院系" prop="schoolId">
                    <el-select v-model="ruleForm1.schoolId" placeholder="选择学院" @change="getMajor">
                        <el-option v-for="item in schooldata" :key="item.schoolName" :label="item.schoolName" :value="item.schoolId" />
                    </el-select>
                </el-form-item>
                <el-form-item label="所属专业" prop="majorId">
                    <el-select v-model="ruleForm1.majorId" placeholder="选择专业">
                        <el-option v-for="item in majordata" :key="item.majorName" :label="item.majorName" :value="item.majorId" />
                    </el-select>
                </el-form-item>
                <el-form-item label="任课教师" prop="teacherId">
                    <el-select v-model="ruleForm1.teacherId" placeholder="选择教师">
                        <el-option v-for="item in teacherdata" :key="item.username" :label="item.username" :value="item.userId" />
                    </el-select>
                </el-form-item>
                <el-form-item label="学时" prop="classHour">
                    <el-input v-model="ruleForm1.classHour"></el-input>
                </el-form-item>
                <el-form-item label="学分" prop="credit">
                    <el-input v-model="ruleForm1.credit"></el-input>
                </el-form-item>
                <el-form-item label="上课时间" prop="courseTime">
                    <el-input v-model="ruleForm1.courseTime"></el-input>
                </el-form-item>
                <el-form-item label="上课地点" prop="coursePlace">
                    <el-input v-model="ruleForm1.coursePlace"></el-input>
                </el-form-item>
                <el-form-item label="选课容量" prop="capacity">
                    <el-input v-model="ruleForm1.capacity"></el-input>
                </el-form-item>
                <el-form-item label="课程介绍" prop="introduction">
                    <el-input v-model="ruleForm1.introduction"></el-input>
                </el-form-item>

                <el-form-item>
                    <el-button type="primary" @click="submitupdatecourse" >修改</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>

    </div>
    </body>
</template>

<script src="../../../assets/js/lessonManage.js" type="text/javascript">

</script>

<style src="../../../assets/css/lessonManage.css" scoped>
</style>
