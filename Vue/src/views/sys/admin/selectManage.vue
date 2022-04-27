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
                <el-button type="primary">修改学年学期</el-button>
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

        <!--        <li style="list-style-type:none"><el-button @click="openSelection" class="open" :disabled="isDisabled1">开放选课</el-button></li>-->
<!--        <li style="list-style-type:none"><el-button @click="closeSelection" class="close" :disabled="isDisabled2">关闭选课</el-button></li>-->
      </div>
    </div>

    </body>
</template>

<script src="../../../assets/js/selectManage.js" type="text/javascript">

</script>

<style src="../../../assets/css/selectManage.css" scoped>
</style>
