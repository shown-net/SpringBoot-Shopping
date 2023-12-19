<template>
  <div style="height:500px;overflow-y: scroll;">
    <el-table height="400" :data="orderRecord" border
              :highlight-current-row="true" :row-style="{height:'100px'}">
      <el-table-column label="商品ID" prop="productID"></el-table-column>
      <el-table-column label="下单时间" prop="buyTime"></el-table-column>
      <el-table-column label="收货人ID" prop="userID"></el-table-column>
      <el-table-column label="购买数量" prop="quantity"></el-table-column>
      <el-table-column label="购买单价" prop="price"></el-table-column>
      <el-table-column label="操作">
        <template slot-scope="{row}">
          <el-popconfirm title="确定要删除该订单吗？" @confirm="deleteOrder(row.id)">
            <el-button slot="reference">删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>
<script>

import request from "@/utility/request";

export default {
  data() {
    return {
      // 订单记录
      orderRecord: [],
      // 删除的订单id
      deleteIDList: [],
    };
  },
  mounted() {
    request.post("http://localhost:8080/order/selectInfoByUserId", {
      userName: this.$parent.userName,
      id: this.$parent.userID,
      accountType: this.$parent.accountType
    })
        .then(result => {
          // result = Result类对象，其data字段为order类对象
          let order_List = result.data
          if (order_List != null) {
            this.orderRecord = order_List
          }
        })
  },
  beforeDestroy() {
    // 提交删除订单请求到后端数据库
    if (this.deleteIDList.length > 0) {
      request.post("http://localhost:8080/order/deleteById", this.deleteIDList)
    }
  },
  methods: {
    // 删除某个商品订单
    deleteOrder(id) {
      this.orderRecord = this.orderRecord.filter(item => item.id !== id)
      this.deleteIDList.push(id)
    }
  }
}
;
</script>
<style scoped>

div {
  box-sizing: content-box;
}


</style>