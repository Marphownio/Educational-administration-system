<template>
    <Nav></Nav>
    <body>
    <div class="showlist">

        <el-table :data="tableData"
                  :header-cell-style="{'text-align':'center'}"
                  :cell-style="{'text-align':'center'}"
        >
            <el-table-column label="教室列表">
                <el-table-column prop="building" sortable label="教学楼" />
                <el-table-column prop="classroom" sortable label="教室" />
            </el-table-column>
        </el-table>
        <el-form :inline="true" >
            <el-form-item>
                <el-button type="primary" @click="dialogVisible=true" class="edit">修改</el-button>
            </el-form-item>
        </el-form>
    </div>
    </body>
</template>

<script src="../../../assets/js/classroomManage.js" type="text/javascript">

</script>

<style  scoped>
    @import "../../../assets/css/classroomManage.css";
</style>
