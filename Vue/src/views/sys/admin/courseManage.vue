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
        <div style="font-size: 20px;margin: 20px">课程信息:</div>
        <el-form :inline="true" >
            <el-form-item>
                <el-button type="primary" @click="addcourse=true">添加课程</el-button>
                <el-upload
                        class="upload-demo"
                        action="http://localhost/api/course/batchimport"
                        :on-success="showwrongmessage"
                        method="post"
                        enctype=“multipart/form-data”
                        accept=".csv"
                        >
                    <el-button type="primary" style="margin-left: 20px;margin-right: 20px;margin-top: 10px">通过csv文件添加</el-button>
                </el-upload>
                <el-button type="primary" @click="checkcourse=true">审核课程申请</el-button>
                <div id="filterbtn" type="primary" style="margin-left: 20px;">
                    <el-popover  placement="bottom"
                                 title="课程筛选"
                                 :width="500"
                                 trigger="click"
                    >
                        <el-form
                                :model="fliterTimeAndPlaceForm"
                                label-width="80px">
                            <div class="flexitems">
                                <el-form-item label="上课日次:">
                                    <el-select v-model="fliterTimeAndPlaceForm.dayOfWeek" placeholder="选择上课日次">
                                        <el-option label="周一" value="MONDAY" />
                                        <el-option label="周二" value="TUESDAY" />
                                        <el-option label="周三" value="WEDNESDAY" />
                                        <el-option label="周四" value="THURSDAY" />
                                        <el-option label="周五" value="FRIDAY" />
                                        <el-option label="周六" value="SATURDAY" />
                                        <el-option label="周日" value="SUNDAY" />
                                    </el-select>
                                </el-form-item>
                                <el-form-item label="上课节次:">
                                    <el-select  v-model="fliterTimeAndPlaceForm.classTimeId" placeholder="选择上课节次">
                                        <el-option v-for="item in timeData" :key="item.classTimeId" :label="item.classTimeId" :value="item.classTimeId" />
                                    </el-select>
                                </el-form-item>
                            </div>
                            <div class="flexitems">
                                <el-form-item label="教学楼:">
                                    <el-select  v-model="fliterTimeAndPlaceForm.buildingId" placeholder="选择教学楼" @change="getClassroom(0,this.fliterTimeAndPlaceForm.buildingId)">
                                        <el-option v-for="item in buildingData" :key="item.buildingId" :label="item.buildingName" :value="item.buildingId" />
                                    </el-select>
                                </el-form-item>
                                <el-form-item label="教室:">
                                    <el-select v-model="fliterTimeAndPlaceForm.classroomId" placeholder="选择教室" >
                                        <el-option v-for="item in classroomData[0]" :key="item.classroomId" :label="item.classroomId" :value="item.classroomId" />
                                    </el-select>
                                </el-form-item>
                            </div>
                        </el-form>
                        <div style="text-align: right; margin: 0">
                            <el-button size="small" type="danger" plain text @click="cancleFilter();">取消筛选</el-button>
                        </div>
                        <template #reference>
                            <el-button @click="visible = true" type="primary" plain>设置筛选条件</el-button>
                        </template>
                    </el-popover>
                </div>
                <span>
                <el-form-item label="学年" prop="academicyear" style="margin-left: 40px">
                    <el-select v-model="academicyear" placeholder="选择学年" >
                        <el-option v-for="item in academicyearData" :key="item.year" :label="item.year" :value="item.year" />
                    </el-select>
                </el-form-item>
                </span>
                <span>
                <el-form-item label="学期" prop="term">
                    <el-select v-model="term" placeholder="选择学期">
                        <el-option v-for="item in termData" :key="item.term" :label="item.term" :value="item.term" />
                    </el-select>
                </el-form-item>
                </span>
                <el-button @click="changeterm()">变更</el-button>
            </el-form-item>
        </el-form>
        <el-table :data="courseDatashow"  default-expand-all>
            <el-table-column prop="openToMajors" label="" width="120px" v-if="false"/>
            <el-table-column prop="classArrangements" label="" width="120px" v-if="false"/>
            <el-table-column prop="courseId" label="" width="120px" v-if="false"/>
            <el-table-column prop="courseCategoryId" label="" width="120px" v-if="false"/>
            <el-table-column prop="courseCategoryNumber" label="" width="120px" v-if="false"/>
            <el-table-column align="center" label="课程代码" sortable>
                <el-table-column align="center" width="130px" prop="courseCategoryNumbershow">
                    <template #header>
                        <el-input  v-model="search11" @change="search1()" size="small" placeholder="搜索课程代码" />
                    </template>
                    <template v-slot="scope">
                        {{scope.row.courseCategoryNumbershow}}
                    </template>
                </el-table-column>
            </el-table-column>
            <el-table-column align="center" label="课程名称" sortable>
                <el-table-column align="center" prop="courseName" width="130px">
                    <template #header>
                        <el-input v-model="search12" size="small" @change="search1()" placeholder="搜索课程名称" />
                    </template>
                    <template v-slot="scope">
                        {{scope.row.courseName}}
                    </template>
                </el-table-column>
            </el-table-column>
            <el-table-column prop="schoolName" label="开课院系" width="120px" sortable/>
            <el-table-column prop="schoolId" label="学院代码" width="120px" sortable/>
            <el-table-column prop="majorName" label="所属专业" width="120px" sortable/>
            <el-table-column prop="majorId" label="专业代码" width="120px" sortable/>
            <el-table-column prop="coursetype" label="课程类型" width="120px" sortable/>
            <el-table-column prop="classHour" label="学时" width="120px" sortable/>
            <el-table-column prop="credit" label="学分" width="120px" sortable/>
            <el-table-column align="center" label="任课教师" sortable>
                <el-table-column align="center" prop="teacherName" width="130px">
                    <template #header>
                        <el-input v-model="search13" size="small" @change="search1()" placeholder="搜索任课教师" />
                    </template>
                    <template v-slot="scope">
                        {{scope.row.teacherName}}
                    </template>
                </el-table-column>
            </el-table-column>
            <el-table-column prop="teacherId" label="教师工号" width="120px" sortable/>
            <el-table-column prop="capacity" label="选课容量" width="120px" sortable/>
            <el-table-column prop="numberOfStudents" label="已选人数" width="120px"/>
            <el-table-column prop="classarrangement" label="课程安排" width="200px" />
            <el-table-column prop="time" label="上课时间" v-if="false"/>
            <el-table-column prop="place" label="上课地点" v-if="false"/>
            <el-table-column prop="academicYear" label="学年" width="120px"/>
            <el-table-column prop="term" label="学期" width="120px" />
            <el-table-column prop="introduction" label="课程介绍" width="200px"/>
            <el-table-column v-slot="scope" fixed="right" prop="icon" label="操作" width="170px">
                <div>
                    <el-button @click="editHandle(scope.row)">编辑</el-button>
                    <el-popconfirm @confirm="delHandle(scope.row)" title="确认删除课程吗？">
                        <template #reference>
                            <el-button type="danger">删除</el-button>
                        </template>
                    </el-popconfirm>
                </div>
            </el-table-column>
        </el-table>

        <div style="font-size: 20px;margin: 20px">课程类信息:</div>
        <el-table :data="courseCategoryDatashow" >
            <el-table-column prop="courseCategoryId" label="" width="120px" v-if="false"/>
            <el-table-column align="center" label="课程类代码" sortable>
                <el-table-column align="center" width="130px" prop="courseCategoryNumber">
                    <template #header>
                        <el-input  v-model="search21" @change="search2()" size="small" placeholder="搜索课程类代码" />
                    </template>
                    <template v-slot="scope">
                        {{scope.row.courseCategoryNumber}}
                    </template>
                </el-table-column>
            </el-table-column>
            <el-table-column align="center" label="课程名称" sortable>
                <el-table-column align="center" prop="courseName" width="130px">
                    <template #header>
                        <el-input v-model="search22" size="small" @change="search2()" placeholder="搜索课程类名称" />
                    </template>
                    <template v-slot="scope">
                        {{scope.row.courseName}}
                    </template>
                </el-table-column>
            </el-table-column>
            <el-table-column prop="school.schoolName" label="开课院系" width="120px" sortable/>
            <el-table-column prop="school.schoolId" label="学院代码" width="120px" sortable/>
            <el-table-column prop="major.majorName" label="所属专业" width="120px" sortable/>
            <el-table-column prop="major.majorId" label="专业代码" width="120px" sortable/>
            <el-table-column prop="classHour" label="学时" width="120px" sortable/>
            <el-table-column prop="credit" label="学分" width="120px" sortable/>
            <el-table-column v-slot="scope" fixed="right" prop="icon" label="操作" >
                <div>
                    <el-button @click="editHandle2(scope.row)">编辑</el-button>
                    <el-button type="danger" @click="delHandle2(scope.row)">删除</el-button>
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
                <el-table-column prop="openToMajors" label="" width="120px" v-if="false"/>
                <el-table-column prop="classArrangements" label="" width="120px" v-if="false"/>
                <el-table-column prop="courseId" label="" width="120px" v-if="false"/>
                <el-table-column prop="academicYear" label="" width="120px" v-if="false"/>
                <el-table-column prop="term" label="" width="120px" v-if="false"/>
                <el-table-column prop="courseCategoryId" label="" width="120px" v-if="false"/>
                <el-table-column prop="courseCategoryNumber" label="" width="120px" v-if="false"/>
                <el-table-column prop="courseCategoryNumbershow" label="课程代码" width="120px"/>
                <el-table-column prop="courseCategory.courseName" label="课程名称" width="120px" />
                <el-table-column prop="courseCategory.school.schoolName" label="开课院系" width="120px"/>
                <el-table-column prop="courseCategory.school.schoolId" label="学院代码" width="120px"/>
                <el-table-column prop="courseCategory.major.majorName" label="所属专业" width="120px"/>
                <el-table-column prop="courseCategory.major.majorId" label="专业代码" width="120px"/>
                <el-table-column prop="coursetype" label="课程类型" width="120px"/>
                <el-table-column prop="courseCategory.classHour" label="学时" width="120px"/>
                <el-table-column prop="courseCategory.credit" label="学分" width="120px"/>
                <el-table-column prop="teacher.username" label="任课教师" width="120px"/>
                <el-table-column prop="teacher.userId" label="教师工号" width="120px"/>
                <el-table-column prop="capacity" label="选课容量" width="120px"/>
                <el-table-column prop="classarrangement" label="课程安排" width="120px"/>
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
                <el-form-item label="课程代码" prop="courseCategoryNumber" @change="changeCategory">
                    <el-input v-model="ruleForm1.courseCategoryNumber" style="width: 220px"></el-input>
                </el-form-item>
                <el-form-item label="课程名" prop="courseName" @change="changeCategory">
                    <el-input v-model="ruleForm1.courseName" style="width: 220px"></el-input>
                </el-form-item>
                <el-form-item label="开课院系" prop="schoolId">
                    <el-select v-model="ruleForm1.schoolId" placeholder="选择学院" @change="getMajor(ruleForm1.schoolId)">
                        <el-option v-for="item in schooldata" :key="item.schoolName" :label="item.schoolName" :value="item.schoolId" />
                    </el-select>
                </el-form-item>
                <el-form-item label="所属专业" prop="majorId" @change="changeCategory">
                    <el-select v-model="ruleForm1.majorId" placeholder="选择专业">
                        <el-option v-for="item in majordata" :key="item.majorName" :label="item.majorName" :value="item.majorId" />
                    </el-select>
                </el-form-item>
                <el-form-item label="任课教师" prop="teacherId" >
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
                        <el-form-item   :prop="'arrangement[' + index + '].buildingId'" >
                        <el-select v-model="ruleForm1.arrangement[index].buildingId" prop="'buildingId'+index" placeholder="教学楼"  @change="getClassroom(index,ruleForm1.arrangement[index].buildingId)" style="margin:0">
                            <el-option v-for="item in buildingData" :key="item.buildingId" :label="item.buildingName" :value="item.buildingId" />
                        </el-select>
                        </el-form-item>
                    </span>
                    <span>
                        <el-form-item  :prop="'arrangement[' + index + '].classroom'"  >
                        <el-select v-model="ruleForm1.arrangement[index].classroom" prop="'classroom'+index" placeholder="教室" value-key="classroomId">
                            <el-option v-for="item in classroomData[index]" :key="item.classroomId" :label="item.classroomId" :value="item" />
                        </el-select>
                        </el-form-item>
                    </span>
                    <span>
                        <el-form-item  :prop="'arrangement[' + index + '].dayOfWeek'" >
                        <el-select v-model="ruleForm1.arrangement[index].dayOfWeek" prop="'dayofweek'+index" placeholder="周几" >
                            <el-option v-for="item in dayofweekData" :key="item.value" :label="item.name" :value="item.value" />
                        </el-select>
                        </el-form-item>
                    </span>
                    <span>
                        <el-form-item  :prop="'arrangement[' + index + '].classTimeId'" >
                        <el-select v-model="ruleForm1.arrangement[index].classTimeId" prop="'classTimeId'+index" placeholder="节次" multiple clearable value-key="classTimeId">
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
                id="updatecourse"
                v-model="updatecourse"
                title="编辑课程信息"
                width="800px"
        >
            <el-form
                    ref="ruleForm1"
                    :model="ruleForm1"
                    :rules="editRules1"
                    label-width="120px"
                    class="demo-ruleForm"
            >
                <el-form-item label="任课教师" prop="teacherId" >
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
                <el-row  v-for="(item, index) in ruleForm1.arrangement" label="课时安排" :key="index" gutter="0">
                    <span>
                        <el-form-item   :prop="'arrangement[' + index + '].buildingId'" >
                        <el-select v-model="ruleForm1.arrangement[index].buildingId" prop="'buildingId'+index" placeholder="教学楼"  value-key="buildingId"
                                   @change="getClassroom(index,ruleForm1.arrangement[index].buildingId)" style="margin:0">
                            <el-option v-for="item in buildingData" :key="item.buildingId" :label="item.buildingName" :value="item.buildingId" />
                        </el-select>
                        </el-form-item>
                    </span>
                    <span>
                        <el-form-item  :prop="'arrangement[' + index + '].classroom'" >
                        <el-select v-model="ruleForm1.arrangement[index].classroom" prop="'classroom'+index" placeholder="教室" value-key="classroomId">
                            <el-option v-for="item in classroomData[index]" :key="item.classroomId" :label="item.classroomId" :value="item" />
                        </el-select>
                        </el-form-item>
                    </span>
                    <span>
                        <el-form-item  :prop="'arrangement[' + index + '].dayOfWeek'"  >
                        <el-select v-model="ruleForm1.arrangement[index].dayOfWeek" prop="'dayofweek'+index" placeholder="周几" >
                            <el-option v-for="item in dayofweekData" :key="item.value" :label="item.name" :value="item.value" />
                        </el-select>
                        </el-form-item>
                    </span>
                    <span>
                        <el-form-item  :prop="'arrangement[' + index + '].classTimeId'"  >
                        <el-select v-model="ruleForm1.arrangement[index].classTimeId" prop="'classTimeId'+index" placeholder="节次" multiple clearable value-key="classTimeId">
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
                    <el-button type="primary" @click="submitupdatecourse" >修改</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>

        <el-dialog
                id="updatecoursecategory"
                v-model="updatecoursecategory"
                title="编辑课程类"
                width="800px"
        >
            <el-form
                    ref="ruleForm2"
                    :model="ruleForm2"
                    :rules="editRules2"
                    label-width="120px"
                    class="demo-ruleForm"
            >
                <el-form-item label="课程类代码" prop="courseCategoryNumber" >
                    <el-input v-model="ruleForm2.courseCategoryNumber" style="width: 220px"></el-input>
                </el-form-item>
                <el-form-item label="课程名" prop="courseName" >
                    <el-input v-model="ruleForm2.courseName" style="width: 220px"></el-input>
                </el-form-item>
                <el-form-item label="开课院系" prop="schoolId">
                    <el-select v-model="ruleForm2.schoolId" placeholder="选择学院" @change="getMajor(ruleForm2.schoolId)">
                        <el-option v-for="item in schooldata" :key="item.schoolName" :label="item.schoolName" :value="item.schoolId" />
                    </el-select>
                </el-form-item>
                <el-form-item label="所属专业" prop="majorId">
                    <el-select v-model="ruleForm2.majorId" placeholder="选择专业">
                        <el-option v-for="item in majordata" :key="item.majorName" :label="item.majorName" :value="item.majorId" />
                    </el-select>
                </el-form-item>
                <el-form-item label="学时" prop="classHour" >
                    <el-input v-model="ruleForm2.classHour" style="width: 220px"></el-input>
                </el-form-item>
                <el-form-item label="学分" prop="credit" >
                    <el-input v-model="ruleForm2.credit" style="width: 220px"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="submitupdatecoursecategory" >修改</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>

        <el-dialog
                v-model="dialogVisible3"
                title="错误信息显示"
                width="1000px"
                @close="fresh()"
                id="dialog3"
        >
            <textarea v-model="wrongmessage" readonly style="font-size: 15px"></textarea>
            <el-button type="primary" @click="exporttxt" >导出为txt</el-button>
        </el-dialog>

    </div>

    </body>
</template>

<script src="../../../assets/js/courseManage.js" type="text/javascript">

</script>

<style src="../../../assets/css/courseManage.css" scoped>
</style>
