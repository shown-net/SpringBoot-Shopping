<template>
  <div class="main">
    <div class="left-content">
      <!--启用router模式，其index将作为router的path值-->
      <el-menu class="contain_Menu" active-text-color="#ffd04b" router>
        <el-menu-item-group title="个人空间">
          <el-menu-item index="/userSpace/info">首页</el-menu-item>
          <el-menu-item index="/userSpace/info">个人信息</el-menu-item>
          <el-menu-item index="/userSpace/cart" v-if="this.accountType==='消费者'">购物车</el-menu-item>
          <el-menu-item index="/userSpace/catalogue" v-if="this.accountType==='商家'">商品目录</el-menu-item>
          <el-menu-item index="/userSpace/statistics" v-if="this.accountType==='商家'">销售统计</el-menu-item>
          <el-menu-item index="/userSpace/order">订单记录</el-menu-item>
        </el-menu-item-group>
      </el-menu>
    </div>
    <div class="right-content">
      <div class="contain">
        <h4 class="title" style="font-size: 40px">个人中心</h4>
        <el-button type="primary" icon="el-icon-right" @click="logout">退出账号</el-button>
        <el-button type="primary" icon="el-icon-right" @click="LogOff">注销账号</el-button>
        <router-view></router-view>
      </div>
    </div>
  </div>
</template>
<script>

import request from "@/utility/request";

export default {
  computed: {
    getJwt() {
      return localStorage.getItem('token')
    }
  },
  created() {
    // 如果令牌数据不存在
    if (this.getJwt === null) {
      // 重定向到登录界面
      this.$router.push('/rl/login')
    }
    this.userInfo = JSON.parse(localStorage.getItem('userInfo'))
    this.userName = this.userInfo.userName
    this.accountType = this.userInfo.accountType
    this.userID = this.userInfo.userID
  },
  data() {
    return {
      currentComponent: 'InfoView', // 当前要渲染的子路由组件
      userInfo: {},
      userName: '',
      accountType: '',
      userID: ''
    }
  },
  methods: {
    //退出登录状态
    logout() {
      // 删除本地保存的令牌信息
      localStorage.removeItem('token')
      localStorage.removeItem('userInfo')
      localStorage.removeItem('cart')
      //重定向到登录页面
      this.$router.push("/rl/login")
    },
    //注销账号
    LogOff() {
      request.post("http://localhost:8080/user/LogOff", this.userID)
          .then(result => {
            let msg = result.msg
            this.$message.info(msg);
          })
    }
  }
}
</script>
<style scoped>
.main {
//background: url("../../assets/sign_bg.jpg"); width: 100%; height: 100%; background-size: cover; position: fixed;
}

.left-content {
  float: left;
  width: 10%;
  height: 100%;
  text-align: center;

}

.contain_Menu {
  width: 100%;
  height: 100%;
  margin: 0% 0% 20% 0%;
//background-color:#545c64; background-color: #B3C0D1;
}

.right-content {
  float: right;
  width: 90%;
  height: 100%;
  padding-bottom: 3%;
}

.contain {
  width: 95%;
  height: 100%;
  border-radius: 10px;
  padding: 20px 5px 10px 30px;
  background-color: rgba(255, 255, 255, 0.8);
  text-align: left;
  box-shadow: 0 0 20px 2px rgba(0, 0, 0, 0.1);
}

.title {
  margin: 0px 0px auto auto;
  text-align: left;

}


</style>