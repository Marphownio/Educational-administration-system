<template>
    <Nav></Nav>
    <body>
    <div class="showlist">
        <el-form :inline="true" >
            <el-form-item>
                <el-button type="primary" @click="addbuilding=true">添加教学楼</el-button>
                <el-button type="primary" @click="addclassroom=true">添加教室</el-button>
            </el-form-item>
        </el-form>
        <el-table :data="buildingData" :header-cell-style="{'text-align':'center'}" :cell-style="{'text-align':'center'}">
            <el-table-column label="教学楼列表">
                <el-table-column prop="buildingId" sortable label="教学楼编号" />
                <el-table-column prop="buildingName" sortable label="教学楼名" />
                <el-table-column  label="操作" align="right" width="420px">
                    <template v-slot="scope">
                        <el-button @click="editHandle1(scope.row)">编辑</el-button>
                        <el-button type="danger" @click="delHandle1(scope.row.buildingId)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table-column>
        </el-table>
        <el-table :data="filterclassroomdata"  :header-cell-style="{'text-align':'center'}" :cell-style="{'text-align':'center'}" border default-expand-all>
            <el-table-column label="教室列表">
            <el-table-column prop="classroomId" label="教室编号" sortable />
            <el-table-column prop="buildingName" label="所属教学楼" sortable />
                <el-table-column prop="buildingId" label="教学楼编号" sortable />
            <el-table-column  align="right" width="420px">
                <template #header>
                    <el-input v-model="search" @change="getForm" size="small" placeholder="输入教学楼名" />
                </template>
                <template v-slot="scope">
                    <el-button @click="editHandle2(scope.row)">编辑</el-button>
                    <el-button type="danger" @click="delHandle2(scope.row.classroomId)">删除</el-button>
                </template>
            </el-table-column>
            </el-table-column>
        </el-table>
    </div>
    <el-dialog v-model="addbuilding" title="添加教学楼" width="600px" @close="refresh">
        <el-form
                ref="ruleForm1"
                :model="ruleForm1"
                :rules="editRules1"
                label-width="120px"
                class="demo-ruleForm"
        >
            <el-form-item label="教学楼编号" prop="buildingId">
                <el-input v-model="ruleForm1.buildingId"></el-input>
            </el-form-item>
            <el-form-item label="教学楼名" prop="buildingName">
                <el-input v-model="ruleForm1.buildingName"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="submitaddbuilding" >添加</el-button>
            </el-form-item>
        </el-form>
    </el-dialog>
    <el-dialog v-model="updatebuilding" title="编辑教学楼信息" width="600px" @close="refresh">
        <el-form
                ref="ruleForm1"
                :model="ruleForm1"
                :rules="editRules1"
                label-width="120px"
                class="demo-ruleForm"
        >
            <el-form-item label="教学楼编号" prop="buildingId" >
                <el-input v-model="ruleForm1.buildingId" readonly></el-input>
            </el-form-item>
            <el-form-item label="教学楼名" prop="buildingName">
                <el-input v-model="ruleForm1.buildingName"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="submitupdatebuilding" >修改</el-button>
            </el-form-item>
        </el-form>
    </el-dialog>

    <el-dialog v-model="addclassroom" title="添加教室" width="600px" @close="refresh">
        <el-form
                ref="ruleForm2"
                :model="ruleForm2"
                :rules="editRules2"
                label-width="120px"
                class="demo-ruleForm"
        >
            <el-form-item label="教室编号" prop="classroomId">
                <el-input v-model="ruleForm2.classroomId"></el-input>
            </el-form-item>
            <el-form-item label="所属教学楼" prop="buildingId">
                <el-select v-model="ruleForm2.buildingId" placeholder="所属教学楼" >
                    <el-option v-for="item in buildingData" :key="item.buildingName" :label="item.buildingName" :value="item.buildingId" />
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="submitaddclassroom" >添加</el-button>
            </el-form-item>
        </el-form>
    </el-dialog>
    <el-dialog v-model="updateclassroom" title="编辑教室信息" width="600px" @close="refresh">
        <el-form
                ref="ruleForm2"
                :model="ruleForm2"
                :rules="editRules2"
                label-width="120px"
                class="demo-ruleForm"
        >
            <el-form-item label="教室编号" prop="classroomId">
                <el-input v-model="ruleForm2.classroomId" readonly></el-input>
            </el-form-item>
            <el-form-item label="所属教学楼" prop="buildingId">
                <el-select v-model="ruleForm2.buildingId" placeholder="所属教学楼" >
                    <el-option v-for="item in buildingData" :key="item.buildingName" :label="item.buildingName" :value="item.buildingId" />
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="submitupdateclassroom" >修改</el-button>
            </el-form-item>
        </el-form>
    </el-dialog>
    </body>
</template>

<script src="../../../assets/js/classroomManage.js" type="text/javascript">

</script>

<style  scoped>
    @import "../../../assets/css/classroomManage.css";
</style>
