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
                <el-button type="primary" @click="dialogVisible1=true">添加课程</el-button><!--这个写法无法写入两个弹窗，需将课程申请换成方法（待完成）-->
                <el-upload
                        class="upload-demo"
                        action=""
                        :on-change="handleChange"
                        accept=".csv"
                        :auto-upload="false">
                    <el-button type="primary" style="margin-left: 20px;margin-right: 20px;margin-top: 10px">通过csv文件添加</el-button>
                </el-upload>
                <el-button type="primary" @click="dialogVisible2=true">审核课程申请</el-button>
            </el-form-item>
        </el-form>
        <el-table :data="tableData" >
            <el-table-column fixed prop="id" label="课程编号" width="120px"/>
            <el-table-column prop="name" label="课程名称" width="120px" />
            <el-table-column prop="college" label="开课院系" width="120px"/>
            <el-table-column prop="major" label="学时" width="120px"/>
            <el-table-column prop="idNumber" label="学分" width="120px"/>
            <el-table-column prop="phoneNumber" label="任课教师" width="120px"/>
            <el-table-column prop="email" label="课程介绍" width="120px"/>
            <el-table-column prop="state" label="上课时间" width="120px"/>
            <el-table-column prop="email" label="上课地点" width="120px"/>
            <el-table-column prop="state" label="选课容量" width="120px"/>
            <el-table-column fixed="right" prop="icon" label="操作" width="170px">
                <div>
                    <el-button @click="editHandle(scope.row)">编辑</el-button>
                    <el-popconfirm title="确认删除吗？">
                        <template #reference>
                            <el-button type="danger">删除</el-button>
                        </template>
                    </el-popconfirm>
                </div>
            </el-table-column>
        </el-table>


        <el-dialog
                id="apply"
                v-model="dialogVisible2"
                title="课程申请"
                width="80%"
        >
            <el-table :data="applyData" >
                <el-table-column fixed prop="request" label="请求" width="120px"><!--指的是“增加”、“删除”、“修改”之类的要求-->
                <template v-slot="scope">
                    <el-tag v-if="scope.row.request===1" type="success" >添加</el-tag>
                    <el-tag v-if="scope.row.request===2" >修改</el-tag>
                    <el-tag v-if="scope.row.request===3" type="danger" >删除</el-tag>
                </template>
                </el-table-column>
                <el-table-column prop="id" label="课程编号" width="120px"/>
                <el-table-column prop="name" label="课程名称" width="120px" />
                <el-table-column prop="college" label="开课院系" width="120px"/>
                <el-table-column prop="major" label="学时" width="120px"/>
                <el-table-column prop="idNumber" label="学分" width="120px"/>
                <el-table-column prop="phoneNumber" label="任课教师" width="120px"/>
                <el-table-column prop="email" label="课程介绍" width="120px"/>
                <el-table-column prop="state" label="上课时间" width="120px"/>
                <el-table-column prop="email" label="上课地点" width="120px"/>
                <el-table-column prop="state" label="选课容量" width="120px"/>
                <el-table-column fixed="right" prop="icon" label="操作" width="170px">
                    <div>
                        <el-button @click="editHandle(scope.row.id)">同意</el-button>
                        <el-button type="danger">拒绝</el-button>
                    </div>
                </el-table-column>
            </el-table>
        </el-dialog>

        <el-dialog
                id="add"
                v-model="dialogVisible1"
                title="添加课程"
                width="600px"
        >
            <el-form
                    ref="ruleForm"
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
                <el-form-item label="开课院系">
                    <el-select v-model="ruleForm1.school" placeholder="选择学院">
                        <el-option v-for="item in schooldata" :key="item.id" :label="item.schoolName" :value="item.schoolId" />
                    </el-select>
                </el-form-item>
                <el-form-item label="任课教师">
                    <el-select v-model="ruleForm1.school" placeholder="选择教师">
                        <el-option v-for="item in teacherdata" :key="item.id" :label="item.schoolName" :value="item.schoolId" />
                    </el-select>
                </el-form-item>
                <el-form-item label="学时" prop="courseHour">
                    <el-input v-model="ruleForm1.courseHour"></el-input>
                </el-form-item>
                <el-form-item label="学分" prop="credit">
                    <el-input v-model="ruleForm1.credit"></el-input>
                </el-form-item>
                <el-form-item label="上课时间" prop="classTime">
                    <el-input v-model="ruleForm1.classTime"></el-input>
                </el-form-item>
                <el-form-item label="上课地点" prop="classPlace">
                    <el-input v-model="ruleForm1.classPlace"></el-input>
                </el-form-item>
                <el-form-item label="选课容量" prop="capacity">
                    <el-input v-model="ruleForm1.capacity"></el-input>
                </el-form-item>
                <el-form-item label="课程介绍" prop="introduction">
                    <el-input v-model="ruleForm1.introduction"></el-input>
                </el-form-item>

                <el-form-item>
                    <el-button type="primary" @click="submitForm1" >添加</el-button>
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
