<template>
  <Nav></Nav>
  <div id="chosenclassinfor">
    <div id="welcom">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      &nbsp;&nbsp;&nbsp;&nbsp;这是您的本学期课表</div>
    <el-table
        :data="formClassData"
        id="chosenclassform">
      <el-table-column prop="classtime" label="课次"  />
      <el-table-column prop="name" label="周一"  />
      <el-table-column prop="address" label="周二"/>
      <el-table-column prop="address" label="周三"/>
      <el-table-column prop="address" label="周四" />
      <el-table-column prop="address" label="周五"/>
      <el-table-column prop="address" label="周六"/>
      <el-table-column prop="address" label="周日"/>
    </el-table>
    <el-table
        :data="tableClassData"
        id="chosenclasstabel">
      <el-table-column prop="date" label="Date" width="180" />
      <el-table-column prop="name" label="Name" width="180" />
      <el-table-column prop="address" label="Address" />
    </el-table>
  </div>

</template>


<script src="../../../assets/js/myclass.js" type="text/javascript">
</script>


<style  scoped>
@import "../../../assets/css/myclass.css";
</style>