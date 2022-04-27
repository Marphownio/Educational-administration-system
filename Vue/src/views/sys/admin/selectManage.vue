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
            <el-table-column align="center" prop="currentYear" label="当前学年" />
            <el-table-column align="center" prop="currentTerm" label="当前学期" />
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
          <el-button type="primary">进入下一阶段</el-button>
        </div>
      </div>
    </div>
    <el-dialog
        v-model="dialogVisible0"
        title="设置学年学期"
        width="600px"
    >
      <el-form :inline=true
               :model="formInLine"
               :rules="editFormInLine"
               label-width="100px"
               class="demo-form-inline">
        <el-form-item label="设置学年" prop="newCurrentYear">
          <el-input v-model="formInLine.newCurrentYear"/>
        </el-form-item>
        <el-form-item label="设置学期" prop="newCurrentTerm">
          <el-select v-model="formInLine.newCurrentTerm" placeholder="选择学期">
            <el-option label="第一学期" value="1" />
            <el-option label="第二学期" value="2" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="">确认修改</el-button>
        </el-form-item>
      </el-form>

    </el-dialog>
    </body>
</template>

<script src="../../../assets/js/selectManage.js" type="text/javascript">

</script>

<style src="../../../assets/css/selectManage.css" scoped>
</style>
