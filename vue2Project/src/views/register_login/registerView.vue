<template>
  <el-form class="register-container" :rules="rules"
           ref="ruleForm" :model="sizeForm" label-width="auto">
    <el-form-item label="用户名" prop="userName">
      <el-input placeholder="请设置用户名" v-model="sizeForm.userName"></el-input>
    </el-form-item>
    <el-form-item label="密码" prop="password">
      <el-input placeholder="请设置密码" v-model="sizeForm.password" show-password></el-input>
    </el-form-item>
    <el-form-item label="邮箱" prop="email">
      <el-input placeholder="请输入邮箱" v-model="sizeForm.email"></el-input>
    </el-form-item>
    <el-form-item label="账号类型">
      <el-radio-group v-model="sizeForm.accountType">
        <el-radio-button label="消费者"></el-radio-button>
        <el-radio-button label="商家"></el-radio-button>
      </el-radio-group>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="onSubmit">立即注册</el-button>
      <el-button>取消</el-button>
    </el-form-item>
  </el-form>
</template>
<script>

import request from "@/utility/request";

export default {
  data() {
    return {
      sizeForm: {
        userName: '',
        password: '',
        email: '',
        // 账户类型，消费者||商家
        accountType: '消费者'
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
        email: [
          {required: true, message: '邮箱不能为空', trigger: 'blur',},
          {type: "email", required: true, message: '邮箱格式错误', trigger: ['blur', 'change']}
        ]
      },
    };
  },
  methods: {
    onSubmit() {
      return request.post("http://localhost:8080/user/register", this.sizeForm).then(result => {
        if (result.code === 0) {
          //存在重复用户名，重新注册
          this.$message.error("存在重复用户名，请重新注册")
        } else {
          //注册成功
          this.$message.success("注册成功,请登录账号")
          this.$router.push('/rl/login')
        }
      })
    }
  }
};
</script>
<style scoped>

.register-container {
  width: 90%;
  height: 75%;
  border-radius: 10px;
  margin: 0px 0px;
  padding: 20px 20px 10px 20px;
  text-align: left;
}

.el-form-item {
  height: 50px;
}


</style>