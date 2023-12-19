<template>
  <div>
    <el-table height="400" :data="catalogue" border
              :highlight-current-row="true" :row-style="{height:'100px'}">
      <el-table-column width="40px" type="selection">
      </el-table-column>
      <el-table-column label="商品信息">
        <template slot-scope="{row}">
          <el-image :src=(baseImageUrl+row.imageUrl) alt="" class="img_Product"></el-image>
          <el-input minlength="1" maxlength="50" show-word-limit v-model="row.name"></el-input>
        </template>
      </el-table-column>
      <el-table-column label="商品种类" prop="kind">
        <template slot-scope="{row}">
          <el-select v-model="row.kind" placeholder="请选择商品种类">
            <el-option label="笔记本电脑" value="笔记本电脑"></el-option>
            <el-option label="台式电脑" value="台式电脑"></el-option>
            <el-option label="平板电脑" value="平板电脑"></el-option>
            <el-option label="手机通信" value="手机通信"></el-option>
            <el-option label="智能产品" value="智能产品"></el-option>
            <el-option label="配件办公" value="配件办公"></el-option>
          </el-select>
        </template>
      </el-table-column>
      <el-table-column label="单价(￥)" prop="price">
        <template slot-scope="{row}">
          <el-input-number v-model="row.price"></el-input-number>
        </template>
      </el-table-column>
      <el-table-column label="操作" prop="id">
        <template slot-scope="{row}">
          <el-button @click="updateProduct(row)">更新</el-button>
          <el-popconfirm title="确定要删除该商品吗？" @confirm="deleteGoods(row.id,row.imageUrl)">
            <el-button slot="reference">删除</el-button>
          </el-popconfirm>
          <el-button>还原</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-button type="text" @click="dialogFormVisible = true">添加新商品</el-button>
    <el-dialog title="新商品信息" :visible.sync="dialogFormVisible">
      <el-form :model="productForm">
        <el-form-item label="商家ID" :label-width="formLabelWidth">
          <el-input v-model="productForm.managerID" disabled></el-input>
        </el-form-item>
        <el-form-item label="商品种类" :label-width="formLabelWidth">
          <el-select v-model="productForm.kind" placeholder="请选择合适种类">
            <el-option label="笔记本电脑" value="笔记本电脑"></el-option>
            <el-option label="台式电脑" value="台式电脑"></el-option>
            <el-option label="平板电脑" value="平板电脑"></el-option>
            <el-option label="手机通信" value="手机通信"></el-option>
            <el-option label="智能产品" value="智能产品"></el-option>
            <el-option label="配件办公" value="配件办公"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="商品名称" :label-width="formLabelWidth">
          <el-input v-model="productForm.name" minlength="1" maxlength="50" show-word-limit></el-input>
        </el-form-item>
        <el-form-item label="单价(￥)" :label-width="formLabelWidth">
          <el-input-number v-model="productForm.price"></el-input-number>
        </el-form-item>
        <el-form-item label="商品图片" :label-width="formLabelWidth">
          <el-upload
              class="upload-demo"
              drag
              ref="upload"
              action="http://localhost:8080/images/upload"
              :limit="1"
              :auto-upload="false"
              :on-success="handleSuccess"
              :on-exceed="handleExceed">
            <i class="el-icon-upload"></i>
            <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="addGoods()">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>

import request from "@/utility/request";

export default {
  mounted() {
    let kind = "all"
    request.post("http://localhost:8080/product/selectInfoByKind", kind)
        .then(result => {
          // result = Result类对象，其data字段为Product类对象列表
          this.catalogue = result.data
        })
  },
  beforeDestroy() {

  },
  data() {
    return {
      // 商品图片源地址
      baseImageUrl: 'http://localhost:8080/images/download/',
      // 商品目录
      catalogue: [], //name: string, price: int
      //当地时间
      currentTime: new Date(),
      productForm: {
        managerID: 1,
        kind: '',
        name: '',
        price: 0,
        imageUrl: ''
      },
      // 对话框可见标志
      dialogFormVisible: false,
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
    deleteGoods(ID,imageUrl) {
      alert(imageUrl)
      this.catalogue = this.catalogue.filter(item => item.id !== ID);
      request.post("http://localhost:8080/product/delete", ID);
      request.post("http://localhost:8080/images/delete", imageUrl)
      this.$message.success("删除成功")
    },
    // 获取上传的图片文件名 response为Result类对象,并且执行插入商品到数据库操作
    handleSuccess(response) {
      this.productForm.imageUrl = response.msg
      this.catalogue.push({
        'managerID': 1,
        'kind': this.productForm.kind,
        'name': this.productForm.name,
        'price': this.productForm.price,
        "imageUrl": this.productForm.imageUrl
      });
      request.post("http://localhost:8080/product/add", this.productForm);
      this.dialogFormVisible = false
      this.$message.success("添加成功")
    },
    // 处理文件个数超出限制
    handleExceed(){
      this.$notify.error("最多只能上传一张商品图片")
    },
    // 添加新商品
    addGoods() {
      // 触发组件上传文件事件
      this.$refs.upload.submit();
    },
    // 更新单个商品信息
    updateProduct(project) {
      request.post("http://localhost:8080/product/updateInfo", project);
      this.$message.success("更新成功")
    },
  }
}
;
</script>
<style scoped>


div {
  box-sizing: content-box;
}

.img_Product {
  width: 160px;
  height: 160px;
  display: block;
  text-align: center;
  cursor: pointer;
  overflow: hidden;
  padding-bottom: 10px;
  position: relative;
}

el-table-column {
  text-align: center;
}

</style>