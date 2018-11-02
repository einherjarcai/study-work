<template>
  <div class="firstpage">
    <div class="container">
      <div class="login">
        <div class="reg-slogan">账号登录</div>
        <el-form>
          <span id="tip"></span>
          <el-form-item>
            <el-input v-model="username" placeholder="请输入登录账号"></el-input>
          </el-form-item>
          <el-form-item>
            <el-input v-model="password" type="password" placeholder="请输入登录密码" @keydown.13="submitForm"></el-input>
          </el-form-item>
        </el-form>
        <el-button type="primary" @click="submitForm">登录</el-button>
      </div>
    </div>
  </div>
</template>

<script>
import Vue from 'vue'
import element from 'element-ui'
Vue.use(element)
export default {
  name: 'login',
  data () {
    return {
      username: '',
      password: ''
    }
  },
  created () {
    let this_ = this
    document.onkeydown = function () {
      let key = window.event.keyCode
      if (key === 13) {
        this_.submitForm()
      }
    }
  },
  methods: {
    submitForm () {
      if (this.username === 'cctv' && this.password === 'cctv123456') {
        this.$router.push({path: '/account'})
      } else {
        document.getElementById('tip').innerHTML = '用户名或密码错误'
      }
    }
  }
}
</script>

<style lang='scss'>
@import url("../../node_modules/element-ui/lib/theme-chalk/index.css");
.firstpage {
  height: 767px;
  width: 1440px;
  background:url('../assets/imgs/index.jpg') no-repeat center fixed;
  background-size: cover;
  /*background-size: 25% !important;*/
  /*background-position-x: 90%;*/
  /*background-position-y: 100%;*/
  .container {
    position: absolute;
    top: 600px;
    z-index: 2;
    width: 980px;
    height: 0;
    margin: auto;
    .login {
      opacity:0.8;
      position: relative;
      width: 356px;
      height: 276px;
      background-color: #FFFFFF;
      left:550px;
      top:-400px;
      .reg-slogan {
        padding-top: 30px;
        padding-bottom: 20px;
        text-align: center;
        font-size: 16px;
      }
      .el-form {
        .el-form-item {
          margin-left: 55px;
          width: 250px;
        }
        #tip {
          color:red;
          font-size: 14px;
          margin-left: -120px;
        }
      }
      .el-button--primary {
        margin-top: 5px;
        margin-left: 5px;
        width: 250px;
        background-color: #19c2c8;
        border-color: #19c2c8;
        font-size: 16px;
      }
    }
  }
}
</style>
