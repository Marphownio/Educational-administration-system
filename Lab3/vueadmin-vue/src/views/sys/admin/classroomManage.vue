<template>
    <Nav></Nav>
    <body>
    <div class="ShowList">

        <el-table :data="tableData"
                  :header-cell-style="{'text-align':'center'}"
                  :cell-style="{'text-align':'center'}"
        >
            <el-table-column label="上课时间表">
                <el-table-column prop="number" label="课程编号" width="210px"/>
                <el-table-column prop="begin" label="开始时间" width="210px"/>
                <el-table-column prop="end" label="结束时间" width="210px"/>
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
