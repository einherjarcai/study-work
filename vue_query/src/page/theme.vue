<template>
  <div class="themeclass">
    <div class="choose">
      <el-row>
        <el-col :span="1"></el-col>
        <el-col :span="2" style="margin-top: 18px;">主题: </el-col>
        <el-col :span="3">
          <el-input v-model="themeValue" placeholder="请输入内容"></el-input>
        </el-col>
        <el-col :span="1"></el-col>
        <el-col :span="2" style="margin-top: 18px">系类: </el-col>
        <el-col :span="3">
          <el-input v-model="departValue" placeholder="请输入内容"></el-input>
        </el-col>
        <el-col :span="1"></el-col>
        <el-col :span="1" style="margin-top: 18px">关键词: </el-col>
        <el-col :span="2" style="margin-left: 10px">
          <el-select v-model="sourceValue"  placeholder="请选择">
            <el-option
              v-for="item in select_keyword"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-col>
        <el-col :span="4" style="margin-left: 10px">
          <el-input v-model="keyword" placeholder="请输入内容"></el-input>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="1"></el-col>
        <el-col :span="2" style="margin-top: 18px; margin-left: -12px">开始时间: </el-col>
        <el-col :span="3">
          <el-date-picker
            v-model="startdate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择日期时间">
          </el-date-picker>
        </el-col>
        <el-col :span="2" style="margin-top: 18px; margin-left: 60px">结束时间: </el-col>
        <el-col :span="3">
          <el-date-picker
            v-model="enddate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择日期时间">
          </el-date-picker>
        </el-col>
        <el-col :span="4">
          <el-button type="primary" plain v-on:click="showData">确定</el-button>
        </el-col>
        <el-col :span="4">
          <el-button type="success" plain v-on:click="downloadExcel">导出</el-button>
        </el-col>
      </el-row>
    </div>
    <div class="data-table">
      <el-table
        v-loading="loading"
        :data="tableData"
        style="padding: 10px 10px 0 10px">
        <el-table-column type="index" :index="indexMethod" label="序号" width="60"></el-table-column>
        <el-table-column prop="name" label="账号名" width="120"></el-table-column>
        <el-table-column prop="type" label="账号归属" width="100"></el-table-column>
        <el-table-column prop="channel" label="所属频道" width="100"></el-table-column>
        <el-table-column prop="theme" label="主题" width="120"></el-table-column>
        <el-table-column prop="department" label="系类" width="120"></el-table-column>
        <el-table-column prop="date" label="发稿日期" width="100"></el-table-column>
        <el-table-column prop="url" label="URL" :show-overflow-tooltip="true" width="140"></el-table-column>
        <el-table-column prop="title" label="标题/内容" :show-overflow-tooltip="true" width="140"></el-table-column>
        <el-table-column prop="read" label="阅读量" width="100"></el-table-column>
        <el-table-column prop="repost" label="转发量" width="100"></el-table-column>
        <el-table-column prop="comment" label="评论量" width="100"></el-table-column>
        <el-table-column prop="like" label="点赞量" width="100"></el-table-column>
      </el-table>
      <div class="block" style="text-align: center">
        <el-pagination layout="total,prev, pager, next" v-on:current-change="changePage" :total="total" :current-page.sync = "page" :page-size="20"
                       :next-text="next" :prev-text="prev"> </el-pagination>
      </div>
    </div>
  </div>
</template>

<script>
import Vue from 'vue'
import element from 'element-ui'
import axios from 'axios'
Vue.use(element)
export default {
  name: 'theme',
  data () {
    return {
      themeValue: '',
      departValue: '',
      sourceValue: '',
      keyword: '',
      select_keyword: [{
        value: '1',
        label: '标题'
      }, {
        value: '2',
        label: '正文'
      }],
      startdate: '',
      enddate: '',
      tableData: [],
      total: 0,
      next: '下一页',
      prev: '上一页',
      page: 1,
      loading: false
    }
  },
  methods: {
    showData () {
      let _this = this
      this.page = 1
      let theme = this.themeValue
      let part = this.departValue
      let keyword = this.keyword
      let startdate = this.startdate
      let enddate = this.enddate
      this.loading = true
      axios.get('/cctv/theme', {
        params: {
          theme: theme,
          part: part,
          startdate: startdate,
          enddate: enddate,
          keyword: keyword,
          page: 1,
          size: 20
        }
      }).then(function (response) {
        _this.tableData = response.data.tableData
        _this.total = response.data.total
        _this.loading = false
      })
    },
    changePage (val) {
      this.page = val
      let _this = this
      let theme = this.themeValue
      let part = this.departValue
      let keyword = this.keyword
      let startdate = this.startdate
      let enddate = this.enddate
      this.loading = true
      axios.get('/cctv/theme', {
        params: {
          theme: theme,
          part: part,
          startdate: startdate,
          enddate: enddate,
          keyword: keyword,
          page: val,
          size: 20
        }
      }).then(function (response) {
        _this.tableData = response.data.tableData
        _this.total = response.data.total
        _this.loading = false
      })
    },
    indexMethod (index) {
      return index + 20 * (this.page - 1) + 1
    },
    downloadExcel () {
      let theme = this.themeValue
      let part = this.departValue
      let keyword = this.keyword
      let startdate = this.startdate
      let enddate = this.enddate
      this.loading = true
      let _this = this
      axios({
        method: 'get',
        url: '/cctv/theme/download',
        params: {
          theme: theme,
          part: part,
          startdate: startdate,
          enddate: enddate,
          keyword: keyword
        },
        responseType: 'blob'
      }).then(function (response) {
        let url = window.URL.createObjectURL(new Blob([response.data]))
        let link = document.createElement('a')
        link.style.display = 'none'
        link.href = url
        link.setAttribute('download', '主题' + startdate + '-' + enddate + '数据.xls')
        document.body.appendChild(link)
        link.click()
        _this.loading = false
      })
    }
  }
}
</script>

<style lang='scss'>
  @import url("../../node_modules/element-ui/lib/theme-chalk/index.css");
  .themeclass {
    padding: 0px 10px;
    .choose {
      background: #FFFFFF;
      height: 120px;
      position: relative;
      margin-top: 10px;
      margin-bottom: 10px;
      .el-row {
        padding-left: 30px;
        .el-col {
          margin-top: 10px;
          .el-radio__label {
            font-size: 16px;
          }
        }
        .el-date-editor.el-input, .el-date-editor.el-input__inner{
          width: 170px;
        }
      }
    }
    .data-table {
      th {
        background: #19c2c8;
        color: #FFFFFF;
        text-align: center !important;
      }
    }
    .source {
      margin-top: 10px;
      padding: 0px 10px;
      height: 40px;
      background: #FFFFFF;
      .el-col {
        font-size: 18px;
        line-height: 50px;
        text-align: center;
        /*color: #19c2c8;*/
      }
    }
  }
</style>
