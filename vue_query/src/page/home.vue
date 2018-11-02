<template>
  <div class="home">
    <div class="top">
        <img class="photo-css-big" src="../assets/imgs/icon_logo.png">
        <span>两微数据查询</span>
    </div>
    <div class="operation">
      <el-row>
        <el-col :span="8" v-bind:class="{ active: isActive0 }">
          <span @click="jump(0)"  v-bind:style="{ color: isOnClock0 }">账号
          </span>
        </el-col>
        <el-col :span="8" v-bind:class="{ active: isActive1 }">
          <span @click="jump(1)"  v-bind:style="{ color: isOnClock1 }">
            文章
          </span>
        </el-col>
        <el-col :span="8" v-bind:class="{ active: isActive2 }">
          <span @click="jump(2)"  v-bind:style="{ color: isOnClock2 }">
            内容
          </span>
        </el-col>
      </el-row>
    </div>
    <!--</div>-->
    <router-view></router-view>
  </div>
</template>

<script>
import Vue from 'vue'
import element from 'element-ui'
Vue.use(element)
export default {
  name: 'home',
  data () {
    return {
      isActive0: true,
      isActive1: false,
      isActive2: false,
      isOnClock0: '#19c2c8',
      isOnClock1: '#cccccc',
      isOnClock2: '#cccccc'
    }
  },
  created () {
  },
  computed: {
  },
  methods: {
    jump (page) {
      let list = ['/account', '/article', '/theme']
      if (page === 0) {
        this.$router.push({
          path: list[page]
        })
        this.isActive0 = true
        this.isActive1 = false
        this.isActive2 = false
        this.isOnClock0 = '#19c2c8'
        this.isOnClock1 = '#cccccc'
        this.isOnClock2 = '#cccccc'
      }
      if (page === 1) {
        this.$router.push({
          path: list[page]
        })
        this.isActive0 = false
        this.isActive1 = true
        this.isActive2 = false
        this.isOnClock0 = '#cccccc'
        this.isOnClock1 = '#19c2c8'
        this.isOnClock2 = '#cccccc'
      }
      if (page === 2) {
        this.$router.push({
          path: list[page]
        })
        this.isActive0 = false
        this.isActive1 = false
        this.isActive2 = true
        this.isOnClock0 = '#cccccc'
        this.isOnClock1 = '#cccccc'
        this.isOnClock2 = '#19c2c8'
      }
    }
  }
}
</script>

<style lang='scss'>
  @import url("../../node_modules/element-ui/lib/theme-chalk/index.css");
  html, body, #app, .home {
    height: 100% !important;
  }
  .home {
    .top {
      line-height: 78px;
      height: 78px;
      font-size: 30px;
      /*font-family:@方正大黑简体;*/
      width: 100%;
      background:url('../assets/imgs/bg_top2.png') no-repeat;
      img {
        position: relative;
        top: 10px;
        margin-left: -900px;
      }
      span{
        color: #cccccc;
      }
    }
    background: #EEEEEE;
    .operation {
      height: 50px;
      background: #FFFFFF;
      .el-col {
        font-size: 20px;
        line-height: 50px;
        text-align: center;
        /*color: #19c2c8;*/
      }
    }
    .active {
      border-bottom: 3px #19c2c8 solid;
    }
  }
</style>
