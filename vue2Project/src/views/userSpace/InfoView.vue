<template>
  <el-form class="space-container" :rules="rules" ref="sizeForm" :model="sizeForm" label-width="80px">
    <el-form-item class="space-item" label="用户名" prop="userName">
      <!--禁止修改用户名-->
      <el-input placeholder="用户名" v-model="sizeForm.userName" :disabled="true"></el-input>
    </el-form-item>
    <el-form-item label="密码" prop="password">
      <el-input placeholder="密码" v-model="sizeForm.password" show-password></el-input>
    </el-form-item>
    <el-form-item class="space-item" label="性别" prop="gender">
      <el-radio v-model="sizeForm.gender" label="男">男</el-radio>
      <el-radio v-model="sizeForm.gender" label="女">女</el-radio>
    </el-form-item>
    <el-form-item class="space-item" label="年龄" prop="age">
      <el-input placeholder="年龄" v-model.number="sizeForm.age"></el-input>
    </el-form-item>
    <el-form-item class="space-item" label="邮箱" prop="email">
      <el-input placeholder="邮箱" v-model="sizeForm.email"></el-input>
    </el-form-item>
    <el-form-item class="space-item" label="电话号码" prop="phoneNumber">
      <el-input placeholder="电话号码" v-model="sizeForm.phoneNumber"></el-input>
    </el-form-item>
    <el-form-item size="large">
      <el-button type="primary" @click="onSubmit('sizeForm')">确定更新</el-button>
    </el-form-item>
  </el-form>
</template>
<script>

import request from "@/utility/request";

export default {
  data() {
    return {
      //用户信息表单
      sizeForm: {
        userName: '',
        password: '',
        gender: '',
        age: '',
        email: '',
        phoneNumber: ''
      },
      rules: {
        userName: [
          {required: true, message: '用户名不能为空', trigger: 'blur'},
          {min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '密码不能为空', trigger: 'blur'},
          {min: 8, max: 20, message: '长度在 8 到 20 个字符', trigger: 'blur'}
        ],
        age: [
          {type: "number", message: '年龄必须为数字值', trigger: 'blur'},
          {
            validator: (rule, value, callback) => {  //验证数值是否为合法范围
              if (value < 0 || value > 200) {
                callback(new Error("年龄数值错误"));
              }
              callback();
            }
          }
        ],
        email: [
          {required: true, message: '邮箱不能为空', trigger: 'blur',},
          {type: "email", required: true, message: '邮箱格式错误', trigger: ['blur', 'change']}
        ]
      }
    };
  },
  //通过令牌访问个人信息
  created() {
    this.sizeForm.userName = this.$parent.userName
    //获取个人信息表单
    request.post("http://localhost:8080/user/selectInfo", this.sizeForm.userName)
        .then(result => {
          // result = Result类对象，其data字段为user类对象
          let data = result.data
          if (data != null) {
            this.sizeForm = data
          } else {
            //说明令牌失效或者不存在该令牌
            localStorage.removeItem('token')
            localStorage.removeItem('userName')
            localStorage.removeItem('userID')
            localStorage.removeItem('accountType')
            // 重定向到登录界面
            this.$router.push('/rl/login')
          }
        })
  },
  methods: {
    onSubmit(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          request.post("http://localhost:8080/user/updateInfo", this.sizeForm).then(result => {
            let msg = result.msg
            this.$message.info(msg);
          })
        } else {
          this.$message.error("表单验证不通过，请重试");
        }
      });
    }
  }
}
;
</script>
<style scoped>

.space-container {
  width: 90%;
  height: 75%;
  border-radius: 10px;
  margin: 0px 0px;
  padding: 20px 20px 10px 20px;
  text-align: left;
}

.space-item {
//width: 100%; //height: 100px
}


</style>