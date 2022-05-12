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
        :data="tableClassData"
        id="chosenclasstabel"
        border=true>
      <el-table-column align="center" prop="index" label="序号" type="index" width="60px"/>
      <el-table-column align="center" prop="courseId" label="课程编号" width="100px"/>
      <el-table-column align="center" prop="courseName" label="课程名称" width="100px" />
      <el-table-column align="center" prop="school.schoolName" label="开课院系" width="100px" />
      <el-table-column align="center" prop="major.majorName" label="开课专业" width="100px" />
      <el-table-column align="center" prop="classHour" label="学时" width="60px"/>
      <el-table-column align="center" prop="credit" label="学分" width="60px"/>
      <el-table-column align="center" prop="teacher.username" label="任课教师" width="120px"/>
      <el-table-column align="center" prop="introduction" label="课程介绍" width="160px"/>
      <el-table-column align="center" prop="courseTime" label="上课时间" width="160px"/>
      <el-table-column align="center" prop="coursePlace" label="上课地点" width="120px"/>
      <el-table-column align="center" prop="capacity" label="选课容量" width="60px"/>
      <el-table-column align="center" v-slot="scope" fixed="right" prop="icon" label="操作" width="100px">
        <div >
          <el-popconfirm @confirm="(scope.row.courseId)"  title="确认退课吗？">
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