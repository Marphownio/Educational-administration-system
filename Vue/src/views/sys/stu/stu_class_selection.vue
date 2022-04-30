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
      <el-button type="primary" @click="">设置筛选信息</el-button>
      <el-button  type="danger"  @click="">取消筛选条件</el-button>
    </div>

        <el-table
            id="classinformtable"
            :data="selectableData"
        >
          <el-table-column align="center" label="课程编号">
            <el-table-column align="center" width="130px" prop="courseId">
              <template #header>
                <el-input  v-model="search" size="small" placeholder="搜索课程编号" />
              </template>
              <template v-slot="scope">
                {{scope.row.courseId}}
              </template>
            </el-table-column>
          </el-table-column>
          <el-table-column align="center" prop="courseName" label="课程名称">
            <el-table-column align="center" width="130px">
              <template #header>
                <el-input v-model="search" size="small" placeholder="搜索课程名称" />
              </template>
              <template v-slot="scope">
                {{scope.row.courseName}}
              </template>
            </el-table-column>
          </el-table-column>
        <el-table-column align="center"  prop="school.schoolName" label="开课院系"/>
        <el-table-column align="center" prop="major.majorName" label="开课专业" />
        <el-table-column align="center" prop="classHour" label="学时" width="60px" />
        <el-table-column align="center" prop="credit" label="学分" width="60px" />
          <el-table-column align="center" prop="teacher" label="任课教师">
            <el-table-column align="center" width="130px">
              <template #header>
                <el-input v-model="search" size="small" placeholder="搜索任课教师" />
              </template>
              <template v-slot="scope">
                {{scope.row.teacher}}
              </template>
            </el-table-column>
          </el-table-column>
        <el-table-column align="center" prop="introduction" label="课程介绍" />
          <el-table-column v-slot="scope" align="center" label="时间及地点" width="100px">
              <el-button plain @click="openit(scope.row)">详情</el-button>
          </el-table-column>
          <el-table-column align="center" prop="" label="已选/可选">
            <template v-slot="scope">
              <div>
                {{}}/{{}}
              </div>
            </template>
          </el-table-column>
        <el-table-column align="center" v-slot="scope" fixed="right" prop="icon" label="操作">
            <el-popconfirm  @confirm="stu_select(scope.row.courseId)"  title="确认选课吗？">
              <template #reference>
                <el-button type="primary">选课</el-button>
              </template>
            </el-popconfirm>
        </el-table-column>
      </el-table>
  </div>


  <el-dialog
      v-model="dialogVisible1"
      width="70%"
      :before-close="handleClose"
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
        :data="classtimetable"
        id="chosenclassform"
        border-collapse: collapse
        :row-style="{height:'70px'}"
        :cell-style="{padding:'0px'}"
        border>
      <el-table-column align="center" prop="classTimeId,startTimeHour,startTimeMin,endTimeHour,endTimeMin" label="次">
        <template v-slot="scope">
          <p class="xxh1">第{{scope.row.classTimeId}}节课</p>
          <p class="xxh2">{{scope.row.startTimeHour}}:{{scope.row.startTimeMin}}---{{scope.row.endTimeHour}}:{{scope.row.endTimeMin}}</p>
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

    <el-table id="classtimeData" :data="theclasstimeData" style="width: 100%">
      <el-table-column prop="date" align="center" label="日次" />
      <el-table-column prop="name" align="center" label="节次" />
      <el-table-column prop="address" align="center" label="教室" />
    </el-table>
  </el-dialog>


  </body>
</template>

<script src="../../../assets/js/stu_class_selection.js" type="text/javascript">

</script>

<style src="../../../assets/css/stu_class_selection.css" scoped>
</style>
