<template>
  <div>
    <el-table height="400" :data="cart" border @select-all="selectAll()"
              :highlight-current-row="true" :row-style="{height:'75px'}">
      <el-table-column width="40px" type="selection">
        <template slot-scope="{row}">
          <el-checkbox v-model="row.selected"></el-checkbox>
        </template>
      </el-table-column>
      <el-table-column label="商品信息" prop="name"></el-table-column>
      <el-table-column label="商品种类" prop="kind"></el-table-column>
      <el-table-column label="购买数量" prop="quantity">
        <template slot-scope="{row}">
          <el-input-number :min="1" v-model="row.quantity"></el-input-number>
        </template>
      </el-table-column>
      <el-table-column label="单价(￥)" prop="price"></el-table-column>
      <el-table-column label="小计">
        <template slot-scope="{row}"> {{ row.price * row.quantity }}</template>
      </el-table-column>
      <el-table-column label="操作" prop="id">
        <template slot-scope="{row}">
          <el-popconfirm title="确定要删除该商品吗？" @confirm="deleteGoods(row.name)">
            <el-button slot="reference">删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <div class="settlement" style="height: 50px;border: 5px solid #B3C0D1;">
      <div class="amount-sum">已选择{{ this.countSum }}种商品</div>
      <div class="price-sum-amount">￥总价{{ this.priceSum }}</div>
      <div class="btn-area">
        <el-popconfirm title="确定要提交这次订单吗？" @confirm="submitOrder">
          <el-button slot="reference" size="medium">提交订单</el-button>
        </el-popconfirm>
      </div>
    </div>
  </div>
</template>
<script>

import request from "@/utility/request";

export default {
  mounted() {
    const cartData = localStorage.getItem('cart')
    // 如果本地数据有购物车信息，加载数据
    if (cartData) {
      this.cart = JSON.parse(cartData)
    }
  },
  beforeDestroy() {
    // 同步购物车信息到本地数据库
    localStorage.setItem('cart', JSON.stringify(this.cart))
  },
  data() {
    return {
      // 购物车存放的商品信息
      cart: [], //name: string, price: int, quantity: int, selected: bool
      // 全选按钮是否被选中
      all_checked: false,
      //当地时间
      currentTime: new Date()
    };
  },
  computed: {
    // 选中商品的总价格
    priceSum() {
      return this.cart.reduce((sum, e) => sum + (e.selected ? 1 : 0) * e.price * e.quantity, 0)
    },
    // 选中商品的数量
    countSum() {
      return this.cart.reduce((sum, e) => sum + (e.selected ? 1 : 0), 0)
    },
    getUserID() {
      return this.$parent.userID
    }
  },
  methods: {
    // 从购物车删除某个商品
    deleteGoods(name) {
      this.cart = this.cart.filter(item => item.name !== name)
    },
    // 全选或取消全选所有商品
    selectAll() {
      this.cart.forEach(e => {
        e.selected = !e.selected
      })
    },
    // 提交用户订单
    submitOrder() {
      let currentTimeVal = this.currentTime.toLocaleString()
      let OrderList = []
      this.cart.forEach(goods => {
        // 将要购买的商品加入到信息列表，并从购物车删除
        if (goods.selected === true) {
          OrderList.push({
            userID: this.getUserID, productID: goods.productID, productName: goods.name,
            price: goods.price, quantity: goods.quantity, buyTime: currentTimeVal
          })
          // 在购物车删除该商品
          this.deleteGoods(goods.name)
        }
        request.post("http://localhost:8080/order/add",OrderList)
        request.post("http://localhost:8080/order/sendEmail",OrderList);
      })
      this.$notify.success("提交订单成功")
    }
  }
}
;
</script>
<style scoped>


div {
  box-sizing: content-box;
}


.settlement div {
  float: left;
  display: inline-block;
  font-size: 30px;
  text-align: center;
  margin: 0 2% 0 1%;
  width: 30%;
}


</style>