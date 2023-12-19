<template>
  <el-form class="login-container" :rules="rules" ref="ruleForm" :model="sizeForm" label-width="80px">
    <el-form-item class="login-item" label="用户名" prop="userName">
      <el-input placeholder="请设置用户名" v-model="sizeForm.userName"></el-input>
    </el-form-item>
    <el-form-item label="密码" prop="password">
      <el-input placeholder="请设置密码" v-model="sizeForm.password" show-password></el-input>
    </el-form-item>
    <el-form-item label="账号类型">
      <el-radio-group v-model="sizeForm.accountType">
        <el-radio-button label="消费者"></el-radio-button>
        <el-radio-button label="商家"></el-radio-button>
      </el-radio-group>
    </el-form-item>
    <el-form-item size="large">
      <el-button type="primary" @click="onSubmit">登录</el-button>
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
        accountType: "消费者"
      },
      rules: {
        userName: [
          {required: true, message: '用户名不能为空', trigger: 'blur'},
          {min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '密码不能为空', trigger: 'blur'},
          {min: 8, max: 20, message: '长度在 8 到 20 个字符', trigger: 'blur'}
        ]
      }
    };
  },
  methods: {
    onSubmit() {
      return request.post("http://localhost:8080/user/login", this.sizeForm)
          .then(result => {
            let jwt = result.data;
            //后端返回的信息(用户ID)
            let msg = result.msg;
            //登录成功,保存登录之后的令牌和用户名,以及用户ID和账号类型
            if (jwt != null) {
              localStorage.setItem("token", jwt);
              localStorage.setItem("userInfo", JSON.stringify({
                'userName': this.sizeForm.userName,
                'userID': parseInt(msg),
                "accountType": this.sizeForm.accountType
              }))
              //网页重定向到个人空间
              this.$message.success("登录成功，已进入个人空间")
              this.$router.push("/userSpace")
            } else {
              this.$message.error("登录失败，请检查输入数据")
            }
          })
    }
  }
};
</script>
<style scoped>

.login-container {
  width: 90%;
  height: 75%;
  border-radius: 10px;
  margin: 0px 0px;
  padding: 20px 20px 10px 20px;
  text-align: left;
}

.login-item {
//width: 100%; //height: 100px
}


</style>