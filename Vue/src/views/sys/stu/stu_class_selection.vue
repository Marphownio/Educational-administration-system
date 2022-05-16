<template>
  <head>
    <meta charset="UTF-8">
    <title>学生选课</title>
  </head>

  <body>

  <Nav></Nav>
  <span id="welcomeinform">可选课程信息：</span>
  <div id="classinform">
    <div id="filterbtn">
      <el-popover  placement="bottom"
                   title="设置筛选条件"
                   :width="500"
                   trigger="click"
      >
        <el-form
            :model="fliterTimeAndPlaceForm"
            label-width="80px">
          <div class="flexItems">
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
                <el-option v-for="item in schooltimetable" :key="item.classTimeId" :label="item.classTimeId" :value="item.classTimeId" />
              </el-select>
            </el-form-item>
          </div>
          <div class="flexItems">
            <el-form-item label="教学楼:">
              <el-select  v-model="fliterTimeAndPlaceForm.buildingId" placeholder="选择教学楼" @change="getClassroom(this.fliterTimeAndPlaceForm.buildingId)">
                <el-option v-for="item in buildings" :key="item.buildingId" :label="item.buildingName" :value="item.buildingId" />
              </el-select>
            </el-form-item>
            <el-form-item label="教室:">
              <el-select v-model="fliterTimeAndPlaceForm.classroomId" placeholder="选择教室" >
                <el-option v-for="item in classrooms" :key="item.classroomId" :label="item.classroomId" :value="item.classroomId" />
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

        <el-table
            id="classinformtable"
            :data="selectableDataShow"
        >
          <el-table-column prop="courseId" v-if="false"/>
          <el-table-column align="center" label="课程编号">
            <el-table-column align="center" width="130px" prop="courseNumber">
              <template #header>
                <el-input  v-model="search11" @change="search()" size="small" placeholder="搜索课程编号" />
              </template>
              <template v-slot="scope">
                {{scope.row.courseCategory.courseCategoryNumber}}.{{scope.row.courseNumber}}
              </template>
            </el-table-column>
          </el-table-column>
          <el-table-column align="center" label="课程名称">
            <el-table-column align="center" prop="courseCategory.courseName" width="130px">
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
        <el-table-column align="center" prop="introduction" label="课程介绍" />
          <el-table-column v-slot="scope" align="center" label="时间及地点" width="100px">
              <el-button plain @click="openit(scope.row)">详情</el-button>
          </el-table-column>
          <el-table-column align="center" prop="capacity,numberOfStudents" label="已选/可选">
            <template v-slot="scope">
              <div v-if="scope.row.numberOfStudents==null">
                {{0}}/{{scope.row.capacity}}
              </div>
              <div v-else>
                {{scope.row.numberOfStudents}}/{{scope.row.capacity}}
              </div>
            </template>
          </el-table-column>
        <el-table-column align="center" v-slot="scope" fixed="right" prop="icon" label="操作">
          <div v-if="scope.row.capacity<=scope.row.numberOfStudents&&this.currentState==='START_SECOND'">
            <el-button @click="applyStudentSelectOpen(scope.row)" type="success" plain>选课申请</el-button>
          </div>
          <div v-else>
            <el-popconfirm  @confirm="stu_select(scope.row)"  title="确认选课吗？">
              <template #reference>
                <el-button type="primary" plain>&nbsp;选&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;课&nbsp;</el-button>
              </template>
            </el-popconfirm>
          </div>

        </el-table-column>
      </el-table>
  </div>


  <el-dialog
      v-model="applyForSelectVisiable"
      width="40%"
      title="提交选课申请"
  >
      <el-form
          ref="applyForSelectForm"
          :model="applyForSelectForm"
          :rules="applyForSelectFormRules"
          label-width="80px">
        <div class="flexItems">
          <el-form-item label="课程编号:">
            <el-input v-model="applyForSelectForm.applyCourseCategoryNumber" readonly/>
          </el-form-item>
          <el-form-item label="课程名称:">
            <el-input v-model="applyForSelectForm.applyClassName" readonly/>
          </el-form-item>
        </div>
        <div class="flexItems">
          <el-form-item label="申请人:">
            <el-input v-model="applyForSelectForm.applyStudentName" readonly/>
          </el-form-item>
          <el-form-item label="学号:">
            <el-input v-model="applyForSelectForm.applyStudentId" readonly/>
          </el-form-item>
        </div>
          <el-form-item label="申请理由:" prop="applyReason">
            <el-input :rows="8" style="margin-left:10px;width:96%;" v-model="applyForSelectForm.applyReason" type="textarea" />
          </el-form-item>
        <div class="flexItems">
          <el-form-item>
            <el-button type="primary" @click="toSubmitApply">确认提交</el-button>
            <el-button type="danger" @click="cancleApply">取消提交</el-button>
          </el-form-item>
        </div>
      </el-form>
  </el-dialog>


  <el-dialog
      v-model="dialogVisible1"
      width="70%"
  >
    <p class="xxh">您的课程时间表</p>
    <div id="showcolor">
      <div class="samples">
        <div class="colors1"></div>
        <div class="colortext">&nbsp;:已选课程</div>
      </div>
      <div class="samples">
        <div class="colors2"></div>
        <div class="colortext">&nbsp;:预选课程</div>
      </div>
      <div class="samples">
        <div class="colors3"></div>
        <div class="colortext">&nbsp;:冲突课程</div>
      </div>
    </div>
    <el-table
        :data="schooltimetable"
        id="chosenclassform"
        border-collapse: collapse
        :row-style="{height:'70px'}"
        :cell-style="{padding:'0px'}"
        border>
      <el-table-column align="center" prop="classTimeId,startTimeHour,startTimeMin,endTimeHour,endTimeMin" label="次">
        <template v-slot="scope">
          <p class="xxh1">第{{scope.row.classTimeId}}节课</p>
          <p class="xxh2">{{scope.row.startTimeHour}}:<span v-if="scope.row.startTimeMin<10">0</span>{{scope.row.startTimeMin}}---{{scope.row.endTimeHour}}:<span v-if="scope.row.endTimeMin<10">0</span>{{scope.row.endTimeMin}}</p>
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

    <el-table id="classtimeData" :data="theClassTimeData" style="width: 100%">
      <el-table-column prop="dayOfWeek" align="center" label="日次">
        <template v-slot="scope">
          <span v-if="scope.row.dayOfWeek=='MONDAY'">周一</span>
          <span v-if="scope.row.dayOfWeek=='TUESDAY'">周二</span>
          <span v-if="scope.row.dayOfWeek=='WEDNESDAY'">周三</span>
          <span v-if="scope.row.dayOfWeek=='THURSDAY'">周四</span>
          <span v-if="scope.row.dayOfWeek=='FRIDAY'">周五</span>
          <span v-if="scope.row.dayOfWeek=='SATURDAY'">周六</span>
          <span v-if="scope.row.dayOfWeek=='SUNDAY'">周日</span>
        </template>
      </el-table-column>
      <el-table-column prop="classTimes" align="center" label="节次">
        <template v-slot="scope">
          <span v-for="item in scope.row.classTimes">{{item.classTimeId}};</span>
        </template>
      </el-table-column>
      <el-table-column prop="building.buildingName,classroom.classroomId" align="center" label="教室">
      <template v-slot="scope">
        {{scope.row.building.buildingName}}-{{scope.row.classroom.classroomId}}
      </template>
      </el-table-column>
    </el-table>
  </el-dialog>


  </body>
</template>

<script src="../../../assets/js/stu_class_selection.js" type="text/javascript">

</script>

<style src="../../../assets/css/stu_class_selection.css" scoped>
</style>
