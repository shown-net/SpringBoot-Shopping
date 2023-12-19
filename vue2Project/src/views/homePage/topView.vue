<template>
  <div style="height: 100%;width: 100%">
    <el-menu
        :default-active="activeIndex"
        mode="horizontal"
        background-color="#545c64"
        text-color="#fff"
        active-text-color="#ffd04b"
        router>
      <el-menu-item>logo</el-menu-item>
      <el-menu-item index="/">商城主页</el-menu-item>
      <el-menu-item index="/shopping">商品搜索</el-menu-item>
      <el-submenu index="3">
        <template slot="title">{{ getUsername }}</template>
        <el-menu-item index="/userSpace/info">空间首页</el-menu-item>
        <el-menu-item index="/userSpace/info">个人信息</el-menu-item>
        <el-menu-item index="/userSpace/cart" v-if="getAccountType==='消费者'">购物车</el-menu-item>
        <el-menu-item index="/userSpace/catalogue" v-if="getAccountType==='商家'">商品目录</el-menu-item>
        <el-menu-item index="/userSpace/order">订单记录</el-menu-item>
      </el-submenu>
      <el-menu-item index="4" disabled>消息中心</el-menu-item>
      <el-menu-item index="5"><a href="https://www.ele.me" target="_blank">订单管理</a></el-menu-item>
    </el-menu>
    <router-view></router-view>
  </div>
</template>
<script>
export default {
  mounted() {
  },
  computed: {
    getUserInfo() {
      return JSON.parse(localStorage.getItem('userInfo'));
    },
    // 用户名字
    getUsername() {
      return this.getUserInfo ? this.getUserInfo.userName : "用户未登录"
    },
    getAccountType() {
      return this.getUserInfo ? this.getUserInfo.accountType : null
    }
  },
  data() {
    return {
      activeIndex: '1'
    };
  },
  methods: {}
}
</script>