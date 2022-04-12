<template>
<Nav></Nav>
    <body>
    <div class="showlist">
      <el-form id="requirebtn" :inline="true" >
        <el-form-item>
          <el-button type="primary" @click="dialogVisible1=true">申请新课次</el-button>
        </el-form-item>
      </el-form>
    <el-table :data="tableData"
              :header-cell-style="{'text-align':'center'}"
              :cell-style="{'text-align':'center'}"
              >
      <el-table-column prop="classTimeId" label="课次编号" width="350px" />
      <el-table-column prop="time" label="时间" />
      <el-table-column v-slot="scope" fixed="right" prop="icon" label="操作" width="350px">
        <el-button type="primary" @click="edittime(scope.row)">编辑</el-button>
        <el-popconfirm @confirm="deletetime(scope.row)" title="确认删除该课次吗？">
          <template #reference>
            <el-button type="danger">删除</el-button>
          </template>
        </el-popconfirm>
      </el-table-column>
    </el-table>
    </div>


    <el-dialog
        v-model="dialogVisible1"
        title="新增课次信息"
        width="600px"
    >
      <el-form
          ref="ruleForm1"
          @submit.prevent="submit_apply()"
          :model="ruleForm1"
          :rules="editRules1"
          label-width="100px"
          class="demo-ruleForm"
      >
        <el-form-item label="课次:" prop="classTimeId">
          <el-input v-model="ruleForm1.classTimeId"></el-input>
        </el-form-item>
        <el-form-item label="课堂时间:" prop="time">
          <el-input v-model="ruleForm1.time"></el-input>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="submit_apply()" >确认新增</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <el-dialog
        v-model="dialogVisible2"
        title="修改课次信息"
        width="600px"
    >
      <el-form
          ref="ruleForm2"
          @submit.prevent="submitEdit()"
          :model="ruleForm2"
          :rules="editRules2"
          label-width="100px"
          class="demo-ruleForm"
      >
        <el-form-item label="课次:" prop="classTimeId">
          <el-input v-model="ruleForm2.classTimeId" readonly></el-input>
        </el-form-item>
        <el-form-item label="课堂时间:" prop="time">
          <el-input v-model="ruleForm2.time"></el-input>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="submitEdit()" >确认修改</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>


    </body>
</template>

<script src="../../../assets/js/timeManage.js" type="text/javascript">

</script>

<style  scoped>
   @import "../../../assets/css/timeManage.css";
</style>
