"use strict";(self["webpackChunkvueadmin_vue"]=self["webpackChunkvueadmin_vue"]||[]).push([[483],{4483:function(e,l,a){a.r(l),a.d(l,{default:function(){return V}});var r=a(3396),s=a(9242);const u=(0,r._)("head",null,[(0,r._)("meta",{charset:"UTF-8"}),(0,r._)("title",null,"课程管理")],-1),m=(0,r._)("span",{id:"teawelcomeinform"},"当前课程信息：",-1),o={id:"teaclassinform"},t=(0,r.Uk)("申请新课程"),d=(0,r.Uk)("编辑"),c=(0,r.Uk)("删除"),n=(0,r.Uk)("确认修改"),i=(0,r.Uk)("确认申请");function p(e,l,a,p,w,g){const b=(0,r.up)("Nav"),h=(0,r.up)("el-button"),V=(0,r.up)("el-form-item"),W=(0,r.up)("el-form"),f=(0,r.up)("el-table-column"),_=(0,r.up)("el-popconfirm"),F=(0,r.up)("el-table"),y=(0,r.up)("el-input"),U=(0,r.up)("el-dialog");return(0,r.wg)(),(0,r.iD)(r.HY,null,[u,(0,r._)("body",null,[(0,r.Wm)(b),m,(0,r._)("div",o,[(0,r.Wm)(W,{id:"requirebtn",inline:!0},{default:(0,r.w5)((()=>[(0,r.Wm)(V,null,{default:(0,r.w5)((()=>[(0,r.Wm)(h,{type:"primary",onClick:l[0]||(l[0]=l=>e.dialogVisible1=!0)},{default:(0,r.w5)((()=>[t])),_:1})])),_:1})])),_:1}),(0,r.Wm)(F,{id:"teaclassinformtable",data:e.tableData},{default:(0,r.w5)((()=>[(0,r.Wm)(f,{prop:"classid",label:"课程编号",width:"90px"}),(0,r.Wm)(f,{prop:"classname",label:"课程名称",width:"90px"}),(0,r.Wm)(f,{prop:"faculty",label:"开课院系",width:"90px"}),(0,r.Wm)(f,{prop:"classhours",label:"学时",width:"90px"}),(0,r.Wm)(f,{prop:"credit",label:"学分",width:"80px"}),(0,r.Wm)(f,{prop:"teacher",label:"任课教师",width:"90px"}),(0,r.Wm)(f,{prop:"introduction",label:"课程介绍",width:"150px"}),(0,r.Wm)(f,{prop:"time",label:"上课时间",width:"150px"}),(0,r.Wm)(f,{prop:"classroom",label:"上课地点",width:"90px"}),(0,r.Wm)(f,{prop:"capacity",label:"选课容量",width:"90px"}),(0,r.Wm)(f,{fixed:"right",prop:"icon",label:"操作",width:"170px"},{default:(0,r.w5)((()=>[(0,r.Wm)(h,{type:"primary",onClick:l[1]||(l[1]=l=>e.dialogVisible=!0)},{default:(0,r.w5)((()=>[d])),_:1}),(0,r.Wm)(_,{title:"确认删除课程吗？"},{reference:(0,r.w5)((()=>[(0,r.Wm)(h,{type:"danger"},{default:(0,r.w5)((()=>[c])),_:1})])),_:1})])),_:1})])),_:1},8,["data"])]),(0,r.Wm)(U,{modelValue:e.dialogVisible,"onUpdate:modelValue":l[5]||(l[5]=l=>e.dialogVisible=l),title:"修改当前课程信息",width:"600px"},{default:(0,r.w5)((()=>[(0,r.Wm)(W,{ref:"ruleForm",onSubmit:(0,s.iM)(e.submit_check,["prevent"]),model:e.ruleForm,rules:e.editRules,"label-width":"100px",class:"demo-ruleForm"},{default:(0,r.w5)((()=>[(0,r.Wm)(V,{label:"新课程名称:",prop:"changeclassname"},{default:(0,r.w5)((()=>[(0,r.Wm)(y,{modelValue:e.ruleForm.changeclassname,"onUpdate:modelValue":l[2]||(l[2]=l=>e.ruleForm.changeclassname=l)},null,8,["modelValue"])])),_:1}),(0,r.Wm)(V,{label:"新上课时间:",prop:"changeclasstime"},{default:(0,r.w5)((()=>[(0,r.Wm)(y,{modelValue:e.ruleForm.changeclasstime,"onUpdate:modelValue":l[3]||(l[3]=l=>e.ruleForm.changeclasstime=l)},null,8,["modelValue"])])),_:1}),(0,r.Wm)(V,{label:"新上课教室:",prop:"changeclassroom"},{default:(0,r.w5)((()=>[(0,r.Wm)(y,{modelValue:e.ruleForm.changeclassroom,"onUpdate:modelValue":l[4]||(l[4]=l=>e.ruleForm.changeclassroom=l)},null,8,["modelValue"])])),_:1}),(0,r.Wm)(V,null,{default:(0,r.w5)((()=>[(0,r.Wm)(h,{type:"primary",onClick:e.submit_check},{default:(0,r.w5)((()=>[n])),_:1},8,["onClick"])])),_:1})])),_:1},8,["onSubmit","model","rules"])])),_:1},8,["modelValue"]),(0,r.Wm)(U,{modelValue:e.dialogVisible1,"onUpdate:modelValue":l[16]||(l[16]=l=>e.dialogVisible1=l),title:"申请新增课程",width:"600px"},{default:(0,r.w5)((()=>[(0,r.Wm)(W,{ref:"ruleForm1",onSubmit:(0,s.iM)(e.submit_check1,["prevent"]),model:e.ruleForm1,rules:e.editRules1,"label-width":"100px",class:"demo-ruleForm"},{default:(0,r.w5)((()=>[(0,r.Wm)(V,{label:"课程编号:",prop:"newclassid"},{default:(0,r.w5)((()=>[(0,r.Wm)(y,{modelValue:e.ruleForm1.newclassid,"onUpdate:modelValue":l[6]||(l[6]=l=>e.ruleForm1.newclassid=l)},null,8,["modelValue"])])),_:1}),(0,r.Wm)(V,{label:"课程名称:",prop:"newclassname"},{default:(0,r.w5)((()=>[(0,r.Wm)(y,{modelValue:e.ruleForm1.newclassname,"onUpdate:modelValue":l[7]||(l[7]=l=>e.ruleForm1.newclassname=l)},null,8,["modelValue"])])),_:1}),(0,r.Wm)(V,{label:"开课院系:",prop:"newclassfaculty"},{default:(0,r.w5)((()=>[(0,r.Wm)(y,{modelValue:e.ruleForm1.newclassfaculty,"onUpdate:modelValue":l[8]||(l[8]=l=>e.ruleForm1.newclassfaculty=l)},null,8,["modelValue"])])),_:1}),(0,r.Wm)(V,{label:"学时:",prop:"newclasshours"},{default:(0,r.w5)((()=>[(0,r.Wm)(y,{modelValue:e.ruleForm1.newclasshours,"onUpdate:modelValue":l[9]||(l[9]=l=>e.ruleForm1.newclasshours=l)},null,8,["modelValue"])])),_:1}),(0,r.Wm)(V,{label:"学分:",prop:"newclasscredits"},{default:(0,r.w5)((()=>[(0,r.Wm)(y,{modelValue:e.ruleForm1.newclasscredits,"onUpdate:modelValue":l[10]||(l[10]=l=>e.ruleForm1.newclasscredits=l)},null,8,["modelValue"])])),_:1}),(0,r.Wm)(V,{label:"任课教师:",prop:"newclassteacher"},{default:(0,r.w5)((()=>[(0,r.Wm)(y,{modelValue:e.ruleForm1.newclassteacher,"onUpdate:modelValue":l[11]||(l[11]=l=>e.ruleForm1.newclassteacher=l)},null,8,["modelValue"])])),_:1}),(0,r.Wm)(V,{label:"课程介绍:",prop:"newclassintroduction"},{default:(0,r.w5)((()=>[(0,r.Wm)(y,{modelValue:e.ruleForm1.newclassintroduction,"onUpdate:modelValue":l[12]||(l[12]=l=>e.ruleForm1.newclassintroduction=l)},null,8,["modelValue"])])),_:1}),(0,r.Wm)(V,{label:"上课时间:",prop:"newclasstime"},{default:(0,r.w5)((()=>[(0,r.Wm)(y,{modelValue:e.ruleForm1.newclasstime,"onUpdate:modelValue":l[13]||(l[13]=l=>e.ruleForm1.newclasstime=l)},null,8,["modelValue"])])),_:1}),(0,r.Wm)(V,{label:"上课地点:",prop:"newclassroom"},{default:(0,r.w5)((()=>[(0,r.Wm)(y,{modelValue:e.ruleForm1.newclassroom,"onUpdate:modelValue":l[14]||(l[14]=l=>e.ruleForm1.newclassroom=l)},null,8,["modelValue"])])),_:1}),(0,r.Wm)(V,{label:"选课容量:",prop:"newclasscapacity"},{default:(0,r.w5)((()=>[(0,r.Wm)(y,{modelValue:e.ruleForm1.newclasscapacity,"onUpdate:modelValue":l[15]||(l[15]=l=>e.ruleForm1.newclasscapacity=l)},null,8,["modelValue"])])),_:1}),(0,r.Wm)(V,null,{default:(0,r.w5)((()=>[(0,r.Wm)(h,{type:"primary",onClick:e.submit_check1},{default:(0,r.w5)((()=>[i])),_:1},8,["onClick"])])),_:1})])),_:1},8,["onSubmit","model","rules"])])),_:1},8,["modelValue"])])],64)}var w=a(5478),g={name:"teaclassmanage",components:{Nav:w.Z},data(){return{ruleForm:{changeclassname:"",changeclasstime:"",changeclassroom:""},editRules:{changeclassname:[{required:!0,message:"请输入新课程名称",trigger:"blur"}],changeclasstime:[{required:!0,message:"请输入新上课时间",trigger:"blur"}],changeclassroom:[{required:!0,message:"请输入新上课地点",trigger:"blur"}]},dialogVisible:!1,ruleForm1:{newclassid:"",newclassname:"",newclassfaculty:"",newclasshours:"",newclasscredits:"",newclassteacher:"",newclassintroduction:"",newclasstime:"",newclassroom:"",newclasscapacity:""},editRules1:{newclassid:[{required:!0,message:"请输入课程编号",trigger:"blur"}],newclassname:[{required:!0,message:"请输入课程名称",trigger:"blur"}],newclassfaculty:[{required:!0,message:"请输入开课院系",trigger:"blur"}],newclasshours:[{required:!0,message:"请输入课程学时",trigger:"blur"}],newclasscredits:[{required:!0,message:"请输入课程学分",trigger:"blur"}],newclassteacher:[{required:!0,message:"请输入任课教师",trigger:"blur"}],newclassintroduction:[{required:!0,message:"请输入课程介绍",trigger:"blur"}],newclasstime:[{required:!0,message:"请输入上课时间",trigger:"blur"}],newclassroom:[{required:!0,message:"请输入上课地点",trigger:"blur"}],newclasscapacity:[{required:!0,message:"请输入选课容量",trigger:"blur"}]},dialogVisible1:!1,tableData:[{classid:"MATH2030001.01",classname:"数学分析",faculty:"数学系",classhours:"每周6课时",credit:"5",teacher:"张三",introduction:"计算机科学技术学院基础课",time:"周一第6-7节，周三第3-4节，周五第1-2节课",classroom:"H3108",capacity:"90"},{classid:"SOFT4000003.01",classname:"离散数学",faculty:"软件学院",classhours:"每周3课时",credit:"2",teacher:"李四",introduction:"计算机科学技术学院基础课",time:"周二第3-5节",classroom:"HGX408",capacity:"100"}]}},created(){},methods:{}},b=a(89);const h=(0,b.Z)(g,[["render",p]]);var V=h}}]);
//# sourceMappingURL=483.66fc7993.js.map