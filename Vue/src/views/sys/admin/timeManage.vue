<template>
<Nav></Nav>
    <body>
    <div class="showlist">
      <el-form id="requirebtn" :inline="true" >
        <el-form-item>
          <el-button type="primary" @click="applynew()">申请新课次</el-button>
          <el-button type="danger" @click="deletetime()">删除最后课次</el-button>
        </el-form-item>
      </el-form>
    <el-table :data="tableDatatime"
              :summary-method="getSummaries"
              show-summary
              :header-cell-style="{'text-align':'center'}"
              :cell-style="{'text-align':'center'}"
              >
      <el-table-column prop="index" label="课次编号" type="index" width="180px"/>
      <el-table-column prop="classTimeId" v-if="false"/>
      <el-table-column prop="startTimeHour,startTimeMin" label="开始时间">
        <template v-slot="scope">
          {{ scope.row.startTimeHour }}:{{ scope.row.startTimeMin }}
        </template>
      </el-table-column>
      <el-table-column prop="endTimeHour,endTimeMin" label="结束时间">
        <template v-slot="scope">
          {{ scope.row.endTimeHour }}:{{ scope.row.endTimeMin }}
        </template>
      </el-table-column>>
      <el-table-column v-slot="scope" fixed="right" prop="icon" label="操作" width="180px">
        <el-button type="primary" @click="openedit(scope.row,scope.$index+1)">编辑</el-button>
      </el-table-column>
    </el-table>
    </div>


    <el-dialog
        v-model="dialogVisible0"
        title="删除课次"
        width="600px"
    >
      <div class="deletedia">
        <h1>您确认要删除第{{this.number}}节课吗</h1>
        <div>
          <el-button type="primary" @click="dialogVisible0=false" >取消删除</el-button>
          <el-button type="danger" @click="submitdelete()" >确认删除</el-button>
        </div>
      </div>


    </el-dialog>

    <el-dialog
        v-model="dialogVisible00"
        title="删除课次"
        width="400px"
    >
      <div class="deletedia">
        <h1>当前无可删除课次！</h1>
        <el-button type="primary" @click="dialogVisible00=false" >关闭</el-button>
      </div>
    </el-dialog>


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
          class="ruleformdia"
      >
        <el-form-item label="课次:" prop="classTimeId">
          <el-input v-model="ruleForm1.classTimeId" readonly></el-input>
        </el-form-item>
        <div class="settime">
          <el-form-item label="起始时:" prop="startTimeHour">
            <el-select id="applyStartHour" v-model="ruleForm1.startTimeHour" placeholder="请选择" @change="apllytimeedit">
              <el-option
                  v-for="item in hours1"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                  :disabled="item.disabled"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="起始分:" prop="startTimeMin">
            <el-select id="applyStartMin" v-model="ruleForm1.startTimeMin" placeholder="请选择" @change="apllytimeedit" :disabled="ruleForm1.startTimeHour==''?true:false">
              <el-option
                  v-for="item in minutes1"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                  :disabled="item.disabled"
              />
            </el-select>
          </el-form-item>
        </div>
        <div class="settime">
          <el-form-item label="结束时:" prop="endTimeHour">
            <el-select id="applyEndHour" v-model="ruleForm1.endTimeHour" placeholder="请选择" @change="apllytimeedit" :disabled="ruleForm1.startTimeMin==''?true:false">
              <el-option
                  v-for="item in hours2"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                  :disabled="item.disabled"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="结束分:" prop="endTimeMin">
            <el-select id="applyEndMin" v-model="ruleForm1.endTimeMin" placeholder="请选择" @change="apllytimeedit" :disabled="ruleForm1.endTimeHour==''?true:false">
              <el-option
                  v-for="item in minutes2"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                  :disabled="item.disabled"
              />
            </el-select>
          </el-form-item>
        </div>


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
        <div class="settime">
          <el-form-item label="起始时:" prop="startTimeHour">
            <el-select id="editStartHour" v-model="ruleForm2.startTimeHour" placeholder="请选择" @change="edittime()">
              <el-option
                  v-for="item in hours1"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                  :disabled="item.disabled"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="起始分:" prop="startTimeMin">
            <el-select id="editStartMin" v-model="ruleForm2.startTimeMin" placeholder="请选择" @change="edittime()">
              <el-option
                  v-for="item in minutes1"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                  :disabled="item.disabled"
              />
            </el-select>
          </el-form-item>
        </div>
        <div class="settime">
          <el-form-item label="结束时:" prop="endTimeHour">
            <el-select id="editEndHour" v-model="ruleForm2.endTimeHour" placeholder="请选择" @change="edittime()">
              <el-option
                  v-for="item in hours2"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                  :disabled="item.disabled"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="结束分:" prop="endTimeMin">
            <el-select id="editEndMin" v-model="ruleForm2.endTimeMin" placeholder="请选择" @change="edittime()">
              <el-option
                  v-for="item in minutes2"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                  :disabled="item.disabled"
              />
            </el-select>
          </el-form-item>
        </div>

        <el-form-item>
          <el-button type="primary" @click="submitEdit()" >确认修改</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    </body>
</template>
<script defer=true src="../../../assets/js/timeManage.js" type="text/javascript">

</script>


<style  scoped>
   @import "../../../assets/css/timeManage.css";
</style>
