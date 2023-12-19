<template>
  <div class="main" style="width: 100%;height: 100%">
    <el-main>
      <!--启用router模式，其index将作为router的path值-->
      <el-row>
        <el-col :span="8">
          <!--商品分类侧边栏-->
          <div class="left-content">
            <el-menu class="kinds-menu" active-text-color="#ffd04b" router>
              <el-menu-item-group title="商品分类">
                <el-menu-item index="/shopping">笔记本电脑</el-menu-item>
                <el-menu-item index="/shopping">台式电脑</el-menu-item>
                <el-menu-item index="/shopping">平板电脑</el-menu-item>
                <el-menu-item index="/shopping">手机通信</el-menu-item>
                <el-menu-item index="/shopping">智能产品</el-menu-item>
                <el-menu-item index="/shopping">配件办公</el-menu-item>
              </el-menu-item-group>
            </el-menu>
          </div>
        </el-col>
        <el-col :span="16">
          <div class="right-content">
            <el-carousel height="400px" trigger="click">
              <el-carousel-item v-for="item in ImageNameList" :key="item" >
                <el-image :src="baseImageUrl+item"></el-image>
              </el-carousel-item>
            </el-carousel>
          </div>
        </el-col>
      </el-row>
    </el-main>
  </div>
</template>
<script>

import request from "@/utility/request";

export default {
  created() {
    request.get("http://localhost:8080/images/download/carousel").then(result => {
      this.ImageNameList = (result.data);
      console.log(this.ImageNameList)
    })
  },
  data() {
    return {
      // 图片文件名列表
      ImageNameList: [],
      //图片基础URL地址
      baseImageUrl: 'http://localhost:8080/images/download/carousel/',
    }
  },
  methods: {
    //退出登录状态
    logout() {
      // 删除本地保存的令牌信息
      localStorage.removeItem('token')
      localStorage.removeItem('username')
      //重定向到登录页面
      this.$router.push("/rl/login")
    }
  }
}

</script>
<style scoped>

.main {
//background: url("../../assets/sign_bg.jpg"); width: 100%; height: 100%; background-size: cover; background-color: #f4f4f4;
  display: flex;
}


.left-content {
  width: 100%;
  height: 100%;
  text-align: center;
//float: left;
}

.right-content {
  width: 100%;
  height: 100%;
  text-align: center;
//float: right; padding: 5%;
}

</style>