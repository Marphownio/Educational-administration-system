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
                        action=""
                        :on-change="handleChange"
                        accept=".csv"
                        :auto-upload="false">
                    <el-button type="primary" style="margin-left: 20px;margin-right: 20px;margin-top: 10px">通过csv文件添加</el-button>
                </el-upload>
                <el-button type="primary" @click="checkcourse=true">审核课程申请</el-button>
            </el-form-item>
        </el-form>
        <el-table :data="tableData" >
            <el-table-column prop="courseId" label="课程代码" width="120px"/>
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
                width="600px"
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
                <el-form-item label="所属专业" prop="schoolId">
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
                    <el-button type="primary" @click="submitaddcourse" >添加</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>

        <el-dialog
                id="update"
                v-model="updatecourse"
                title="编辑课程信息"
                width="600px"
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

<style scoped>
    @import "../../../assets/css/lessonManage.css";
</style>
