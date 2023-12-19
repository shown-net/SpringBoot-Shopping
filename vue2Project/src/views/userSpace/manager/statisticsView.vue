<template>
  <div>
    <el-table height="400" :data="stats" border
              :highlight-current-row="true" :row-style="{height:'100px'}">
      <el-table-column label="商品ID" prop="productID"></el-table-column>
      <el-table-column label="销售总量" prop="quantity"></el-table-column>
      <el-table-column label="销售额(￥)" prop="price"></el-table-column>
    </el-table>
  </div>
</template>
<script>

import request from "@/utility/request";

export default {
  mounted() {
    request.post("http://localhost:8080/order/selectInfoStatistics")
        .then(result => {
          // result = Result类对象，其data字段为Product类对象列表
          this.stats = result.data
        })
  },
  beforeDestroy() {

  },
  data() {
    return {
      // 统计数据
      stats: [],
      // 对话框可见标志
      formLabelWidth: '120px'
    };
  },
  computed: {
    getUserID() {
      return this.$parent.userID
    }
  },
  methods: {
    // 从商品目录删除某个商品
    deleteGoods(ID) {
      this.stats = this.stats.filter(item => item.id !== ID);
      request.post("http://localhost:8080/product/delete", ID);
      this.$message.success("删除成功")
    },
  }
}
;
</script>
<style scoped>


div {
  box-sizing: content-box;
}

el-table-column {
  text-align: center;
}

</style>