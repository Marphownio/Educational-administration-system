<template>
  <Nav></Nav>
  <div id="chosenclassinfor">
    <div id="welcom">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      &nbsp;&nbsp;&nbsp;&nbsp;这是您的本学期课表</div>
    <el-table
        :data="classtimetable"
        id="chosenclassform"
        border-collapse: collapse
        :row-style="{height:'100px'}"
        :cell-style="{padding:'0px'}"
        border>
      <el-table-column align="center" prop="classTimeId,startTimeHour,startTimeMin,endTimeHour,endTimeMin" label="课次" width="155px">
        <template v-slot="scope">
          <h3>第{{scope.row.classTimeId}}节课</h3>
          <h6>{{scope.row.startTimeHour}}:<span v-if="scope.row.startTimeMin<10">0</span>{{scope.row.startTimeMin}}---{{scope.row.endTimeHour}}:<span v-if="scope.row.endTimeMin<10">0</span>{{scope.row.endTimeMin}}</h6>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="name" label="周一" width="155px">
        <template v-slot="scope">
          <div class="Monday">
            <div class="classid"></div>
            <div class="classname"></div>
            <div class="classteacher"></div>
            <div class="classroom"></div>
          </div>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="address" label="周二" width="155px">
        <template v-slot="scope">
          <div class="Tuesday">
            <div class="classid"></div>
            <div class="classname"></div>
            <div class="classteacher"></div>
            <div class="classroom"></div>
          </div>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="address" label="周三" width="155px">
        <template v-slot="scope">
          <div class="Wednesday">
            <div class="classid"></div>
            <div class="classname"></div>
            <div class="classteacher"></div>
            <div class="classroom"></div>
          </div>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="address" label="周四" width="155px">
        <template v-slot="scope">
          <div class="Thursday">
            <div class="classid"></div>
            <div class="classname"></div>
            <div class="classteacher"></div>
            <div class="classroom"></div>
          </div>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="address" label="周五" width="155px">
        <template v-slot="scope">
          <div class="Friday">
            <div class="classid"></div>
            <div class="classname"></div>
            <div class="classteacher"></div>
            <div class="classroom"></div>
          </div>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="address" label="周六" width="155px">
        <template v-slot="scope">
          <div class="Saturday">
            <div class="classid"></div>
            <div class="classname"></div>
            <div class="classteacher"></div>
            <div class="classroom"></div>
          </div>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="address" label="周日" width="155px">
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
    <el-table
        :data="classinfortable"
        id="chosenclasstabel"
        border=true>
      <el-table-column align="center" prop="index" label="序号" type="index" width="50px"/>
      <el-table-column align="center" prop="courseNumber,courseCategory.courseCategoryNumber" label="课程编号" width="130px">
        <template v-slot="scope">
          {{scope.row.courseCategory.courseCategoryNumber}}.{{scope.row.courseNumber}}
        </template>
      </el-table-column>
      <el-table-column align="center" prop="courseCategory.courseName" label="课程名称" width="100px" />
      <el-table-column align="center" prop="courseCategory.school.schoolName" label="开课院系" width="150px" />
      <el-table-column align="center" prop="courseCategory.major.majorName" label="开课专业" width="100px" />
      <el-table-column align="center" prop="courseCategory.classHour" label="学时" width="60px"/>
      <el-table-column align="center" prop="courseCategory.credit" label="学分" width="60px"/>
      <el-table-column align="center" prop="teacher.username" label="任课教师" width="80px"/>
      <el-table-column align="center" prop="introduction" label="课程介绍" width="160px"/>
      <el-table-column align="center" prop="courseTime" label="时间地点安排" width="270px">
        <template  v-slot="scope">
          <div v-for="item in scope.row.classArrangements">
            <span v-if="item.dayOfWeek=='MONDAY'">周一</span>
            <span v-if="item.dayOfWeek=='TUESDAY'">周二</span>
            <span v-if="item.dayOfWeek=='WEDNESDAY'">周三</span>
            <span v-if="item.dayOfWeek=='THURSDAY'">周四</span>
            <span v-if="item.dayOfWeek=='FRIDAY'">周五</span>
            <span v-if="item.dayOfWeek=='SATURDAY'">周六</span>
            <span v-if="item.dayOfWeek=='SUNDAY'">周日</span>/
             {{item.building.buildingName}}-{{item.classroom.classroomId}}/
            <span v-for="item2 in item.classTimes">
              {{item2.classTimeId}}&nbsp;
            </span>
            节课
          </div>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="capacity" label="选课容量" width="60px"/>
      <el-table-column align="center" v-slot="scope" fixed="right" prop="icon" label="操作" width="100px">
        <div >
          <el-popconfirm @confirm="classDrop(scope.row.courseId)"  title="确认退课吗？">
            <template #reference>
              <el-button type="danger">退课</el-button>
            </template>
          </el-popconfirm>
        </div>
      </el-table-column>
    </el-table>
  </div>

</template>


<script src="../../../assets/js/myclass.js" type="text/javascript">
</script>


<style src="../../../assets/css/myclass.css" scoped>
</style>