<template>
  <head>
    <meta charset="UTF-8">
    <title>课程管理</title>
  </head>

  <body>

  <Nav></Nav>
  <span id="teawelcomeinform">当前课程信息：</span>
  <div id="teaclassinform">
    <div id="thisbtn">
      <el-form id="requirebtn" :inline="true" >
        <el-form-item>
          <el-button type="primary" @click="dialogVisible1=true">申请新课程</el-button>
        </el-form-item>
      </el-form>
    </div>
    <el-table id="teaclassinformtable" :data="selectableDataShow" >
        <el-table-column prop="openToMajors" label="" width="120px" v-if="false"/>
        <el-table-column prop="classArrangements" label="" width="120px" v-if="false"/>
        <el-table-column prop="courseId" label="" width="120px" v-if="false"/>
        <el-table-column prop="academicYear" label="" width="120px" v-if="false"/>
        <el-table-column prop="term" label="" width="120px" v-if="false"/>
        <el-table-column prop="courseCategoryId" label="" width="120px" v-if="false"/>
        <el-table-column prop="courseCategoryNumber" label="" width="120px" v-if="false"/>
        <el-table-column align="center" label="课程编号" width="120px">
            <el-table-column align="center" width="120px" prop="courseCategoryNumbershow">
                <template #header>
                    <el-input  v-model="search11" @change="search()" size="small" placeholder="搜索课程编号" />
                </template>
                <template v-slot="scope">
                    {{scope.row.courseCategoryNumbershow}}
                </template>
            </el-table-column>
        </el-table-column>
        <el-table-column align="center" label="课程名称">
            <el-table-column align="center" prop="courseCategory.courseName" width="120px">
                <template #header>
                    <el-input v-model="search22" size="small" @change="search()" placeholder="搜索课程名称" />
                </template>
                <template v-slot="scope">
                    {{scope.row.courseCategory.courseName}}
                </template>
            </el-table-column>
        </el-table-column>
        <el-table-column align="center"  prop="courseCategory.school.schoolName" label="开课院系"/>
        <el-table-column align="center" prop="courseCategory.major.majorName" label="开课专业" />
        <el-table-column align="center" prop="coursetype" label="课程类型"/>
        <el-table-column align="center" prop="courseCategory.classHour" label="学时" width="60px" />
        <el-table-column align="center" prop="courseCategory.credit" label="学分" width="60px" />
        <el-table-column align="center" label="任课教师">
            <el-table-column align="center" prop="teacher.username" width="130px">
                <template #header>
                    <el-input v-model="search33" size="small" @change="search()" placeholder="搜索任课教师" />
                </template>
                <template v-slot="scope">
                    {{scope.row.teacher.username}}
                </template>
            </el-table-column>
        </el-table-column>
        <el-table-column align="center" prop="capacity" label="课程容量" width="60px"/>
        <el-table-column prop="classarrangement" label="课程安排" width="120px"/>
        <el-table-column align="center" prop="introduction" label="课程介绍" />
      <el-table-column v-slot="scope" fixed="right" prop="icon" label="操作" width="170px">
        <el-button type="primary" @click="edit_class_inform(scope.row)">编辑</el-button>
        <el-popconfirm @confirm="tea_delete(scope.row)" title="确认删除课程吗？">
          <template #reference>
            <el-button type="danger">删除</el-button>
          </template>
        </el-popconfirm>
      </el-table-column>
    </el-table>

  </div>


  <el-dialog
      v-model="dialogVisible"
      title="修改当前课程信息"
      width="800px"
  >
    <el-form
        ref="ruleForm1"
        @submit.prevent="submit_edit()"
        :model="ruleForm1"
        :rules="editRules1"
        label-width="100px"
        class="demo-ruleForm"
        onclose="fresh()"
    >
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
        <el-button type="primary" @click="submit_edit()" >确认修改</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>


  <el-dialog
          id="add"
          v-model="dialogVisible1"
          title="申请新增课程"
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
              <el-select v-model="ruleForm1.schoolId" placeholder="选择学院" @change="getMajor">
                  <el-option v-for="item in schooldata" :key="item.schoolName" :label="item.schoolName" :value="item.schoolId" />
              </el-select>
          </el-form-item>
          <el-form-item label="所属专业" prop="majorId" @change="changeCategory">
              <el-select v-model="ruleForm1.majorId" placeholder="选择专业">
                  <el-option v-for="item in majordata" :key="item.majorName" :label="item.majorName" :value="item.majorId" />
              </el-select>
          </el-form-item>
          <el-form-item label="任课教师" prop="teacherId" >
              <el-input v-model="ruleForm1.teacherName" placeholder="任课教师" readonly style="width: 220px"/>
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
                        <el-form-item  :prop="'arrangement[' + index + '].classTimeId'" >
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
              <el-button type="primary" @click="apply_new_class" >添加</el-button>
          </el-form-item>
      </el-form>
  </el-dialog>



  </body>
</template>

<script src="../../../assets/js/tea_class_management.js" type="text/javascript">

</script>

<style src="../../../assets/css/tea_class_management.css" scoped>
</style>
