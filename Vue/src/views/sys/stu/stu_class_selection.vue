<template>
  <head>
    <meta charset="UTF-8">
    <title>学生选课</title>
  </head>

  <body>

  <Nav></Nav>
  <span id="welcomeinform">可选课程信息：</span>
  <div id="classinform">
    <el-table
        :data="classtimetable"
        id="chosenclassform"
        border-collapse: collapse
        :row-style="{height:'100px'}"
        :cell-style="{padding:'0px'}"
        border>
      <el-table-column align="center" prop="classTimeId,startTimeHour,startTimeMin,endTimeHour,endTimeMin" label="次">
        <template v-slot="scope">
          <h3>第{{scope.row.classTimeId}}节课</h3>
          <h6>{{scope.row.startTimeHour}}:{{scope.row.startTimeMin}}---{{scope.row.endTimeHour}}:{{scope.row.endTimeMin}}</h6>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="name" label="一">
        <template v-slot="scope">
          <div class="Monday">
            <div class="classid"></div>
            <div class="classname"></div>
            <div class="classteacher"></div>
            <div class="classroom"></div>
          </div>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="address" label="二">
        <template v-slot="scope">
          <div class="Tuesday">
            <div class="classid"></div>
            <div class="classname"></div>
            <div class="classteacher"></div>
            <div class="classroom"></div>
          </div>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="address" label="三">
        <template v-slot="scope">
          <div class="Wednesday">
            <div class="classid"></div>
            <div class="classname"></div>
            <div class="classteacher"></div>
            <div class="classroom"></div>
          </div>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="address" label="四">
        <template v-slot="scope">
          <div class="Thursday">
            <div class="classid"></div>
            <div class="classname"></div>
            <div class="classteacher"></div>
            <div class="classroom"></div>
          </div>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="address" label="五">
        <template v-slot="scope">
          <div class="Friday">
            <div class="classid"></div>
            <div class="classname"></div>
            <div class="classteacher"></div>
            <div class="classroom"></div>
          </div>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="address" label="六">
        <template v-slot="scope">
          <div class="Saturday">
            <div class="classid"></div>
            <div class="classname"></div>
            <div class="classteacher"></div>
            <div class="classroom"></div>
          </div>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="address" label="七">
        <template v-slot="scope">
          <div class="Sunday">
            <div class="classid"></div>
            <div class="classname"></div>
            <div class="classteacher"></div>
            <div class="classroom"></div>
          </div>
        </template>
      </el-table-column>
    </el-table>
    <el-table id="classinformtable" :data="selectableData" >
      <el-table-column prop="courseId" label="课程编号"/>
      <el-table-column prop="courseName" label="课程名称"/>
      <el-table-column prop="school.schoolName" label="开课院系"  v-if="false"/>
      <el-table-column prop="major.majorName" label="开课专业" />
      <el-table-column prop="classHour" label="学时" />
      <el-table-column prop="credit" label="学分" />
      <el-table-column prop="teacher.username" label="任课教师" />
      <el-table-column prop="introduction" label="课程介绍" />
      <el-table-column prop="courseTime" label="上课时间"/>
      <el-table-column prop="coursePlace" label="上课地点"/>
      <el-table-column prop="capacity" label="选课容量" />
      <el-table-column v-slot="scope" fixed="right" prop="icon" label="操作">
        <div >
          <el-popconfirm @confirm="stu_select(scope.row.courseId)"  title="确认选课吗？">
            <template #reference>
              <el-button type="danger">选课</el-button>
            </template>
          </el-popconfirm>
        </div>
      </el-table-column>
    </el-table>

  </div>
  </body>
</template>

<script src="../../../assets/js/stu_class_selection.js" type="text/javascript">

</script>

<style src="../../../assets/css/stu_class_selection.css" scoped>
</style>
