<template>
    <head>
        <meta charset="UTF-8">
        <title>课程管理</title>
    </head>

    <body>

    <Nav></Nav>
    <div id="setFrame">
          <el-table
              :data="currentTime"
              style="width: 80%"
              id="showCurrentTime">
            <el-table-column align="center" prop="academicYear" label="当前学年" />
            <el-table-column align="center" v-slot="scope" prop="term" label="当前学期">
              <div v-if="scope.row.term==1">
                第一学期
              </div>
              <div v-else>
                第二学期
              </div>
            </el-table-column>
            <el-table-column align="center" label="操作">
                <el-button @click="showdialog()" type="primary">修改学年学期</el-button>
            </el-table-column>
          </el-table>
      <div class="showlist">
        <div id="timeline">
          <el-timeline>
            <el-timeline-item
                v-for="(activity, index) in activities"
                :key="index"
                :type="activity.type"
                :color="activity.color"
                :size="activity.size"
            >
              {{ activity.content }}
            </el-timeline-item>
          </el-timeline>
        </div>
        <div>
          <el-button @click="nextStatue()" type="primary">进入下一阶段</el-button>
        </div>
      </div>
    </div>
    <el-dialog
        v-model="dialogVisible0"
        title="设置学年学期"
        width="600px"
    >
      <el-form
               ref="editRuleForm"
               :model="formInLine"
               :rules="editFormInLine"
               label-width="100px"
               class="demo-form-inline">
        <el-form-item label="设置学年" prop="academicYear" ref="academicYear">
          <div id="flexInput">
            <el-input @change="formInLine.academicYear2=Number(formInLine.academicYear)+1" v-model="formInLine.academicYear"/>
            {{'-'}}
            <el-input v-model="formInLine.academicYear2" readonly/>
          </div>

        </el-form-item>
        <el-form-item label="设置学期" prop="term" ref="term">
          <el-select v-model="formInLine.term" placeholder="选择学期">
            <el-option label="第一学期" value="1" />
            <el-option label="第二学期" value="2" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="setYearAndTerm">确认修改</el-button>
        </el-form-item>
      </el-form>

    </el-dialog>
    </body>
</template>

<script src="../../../assets/js/selectManage.js" type="text/javascript">

</script>

<style src="../../../assets/css/selectManage.css" scoped>
</style>
