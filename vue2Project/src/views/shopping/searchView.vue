<template>
  <div class="main">
    <el-container>
      <el-header>
        <el-checkbox-group v-model="checkKinds" :min="0" :max="6" @change="handleCheckedKindsChange">
          <el-checkbox-button v-for="kind in kinds" :label="kind" :key="kind">
            {{ kind }}
          </el-checkbox-button>
        </el-checkbox-group>
      </el-header>
      <el-main class="productDetail">
        <ul>
          <li v-for="v in currentPageItems" v-bind:key="v.id">
            <el-image :src=(baseImageUrl+v.imageUrl) alt="" class="img_Product"></el-image>
            <h4>{{ v.name }}</h4>
            <p>{{ "￥" + v.price }}</p>
            <el-button @click="addToCart(v)" type="danger">加入购物车</el-button>
          </li>
        </ul>
      </el-main>
      <el-pagination style="text-align: center;height: 50px"
                     background
                     layout="prev, pager, next,jumper"
                     :total=this.product_List.length
                     :current-page="currentPage"
                     :page-size="itemsPerPage"
                     @current-change="handlePageChange">
      </el-pagination>
    </el-container>
  </div>
</template>
<script>
import request from "@/utility/request";

const kind_Options = ['笔记本电脑', '台式电脑', '平板电脑', '手机通信', '智能产品', '配件办公']
export default {
  // 组件创建时加载本地的购物车数据
  created() {
    const cartData = localStorage.getItem('cart')
    if (cartData) {
      // 数据格式转换
      this.cart = JSON.parse(cartData)
    }
    //添加商品列表信息到表单
    request.post("http://localhost:8080/product/selectInfoByKind", [])
        .then(result => {
          // result.data = Result类对象，其data字段为Product类对象列表
          this.product_List = result.data
          this.handlePageChange(1)
        })
  },
  beforeDestroy() {
    // 更新本地数据
    localStorage.setItem('cart', JSON.stringify(this.cart))
  },
  data() {
    return {
      baseImageUrl: 'http://localhost:8080/images/download/',
      // 展示的原始商品列表
      product_List: [],
      // 选择的商品种类
      checkKinds: [],
      // 总共的商品种类
      kinds: kind_Options,
      // 购物车存放的商品信息
      cart: [],
      //所有页数显示项目的总数量
      pageTotal: 100,
      // 当前页码
      currentPage: 1,
      // 每页显示的项目数量
      itemsPerPage: 8,
      // 当前页的项目数组
      currentPageItems: []
    }
  },
  computed: {},
  methods: {
    handleCheckedKindsChange() {
      let kindList = this.checkKinds
      if (kindList.length === kind_Options.length) {
        // 如果选择的种类数组为全满，咋直接发送空数组表示不进行商品种类筛选
        kindList = []
      }
      // 根据商品种类更新后端筛选后的商品列表
      request.post("http://localhost:8080/product/selectInfoByKind", kindList)
          .then(result => {
            // result.data = Result类对象，其data字段为Product类对象列表
            this.product_List = result.data
            this.currentPageItems = this.getPaginatedItems(this.currentPage);
          })
    },
    handlePageChange(newPage) {
      this.currentPage = newPage;
      this.currentPageItems = this.getPaginatedItems(newPage);
    },
    getPaginatedItems(page) {
      const start = (page - 1) * this.itemsPerPage;
      const end = start + this.itemsPerPage;
      return this.product_List.slice(start, end);
    },
    // 添加商品至购物车
    addToCart(product) {
      const existingProduct = this.cart.find(item => item.id === product.id);
      if (existingProduct) {
        existingProduct.quantity++;
      } else {
        this.cart.push({
          productID: product.id,
          name: product.name,
          kind: product.kind,
          price: product.price,
          quantity: 1,
          selected: false
        });
      }
      this.$notify.success("已添加至购物车")
    }
  }
}

</script>
<style scoped>

.main {
  display: flex;
  background-color: #f5f5f5;
}

ul li {
  display: block; /* 将ul的行内元素转换为块元素 */
  float: left; /* 增加了左浮动属性*/
  width: 290px;
  height: 385px;
  background-color: #fff;
  margin-right: 2%;
  margin-bottom: 2%;
  vertical-align: top;
  padding: 10px 10px 10px 10px;
  position: relative;
  overflow: hidden;
}

.img_Product {
  width: 160px;
  height: 160px;
  display: block;
  text-align: center;
  cursor: pointer;
  overflow: hidden;
  border-bottom: 1px solid #ebebeb;
  padding-bottom: 25px;
  margin: 15px auto 0;
  position: relative;
}

</style>