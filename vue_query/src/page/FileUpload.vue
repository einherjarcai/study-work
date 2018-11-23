<template>
  <div class="uploadFile">
    <div class="choose">
      <el-row>
        <el-col :span="2" style="margin-top: 18px">账号归属: </el-col>
        <el-col :span="2">
          <el-select v-model="sec_type"  placeholder="请选择" v-on:change="changeBdLevelSelect">
            <el-option
              v-for="item in belong"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-col>
        <!--<el-col :span="1"></el-col>-->
        <el-col :span="2" style="margin-top: 18px">所属频道: </el-col>
        <el-col :span="3">
          <el-select
            v-model="sec_channel"
            collapse-tags
            placeholder="请选择">
            <el-option
              v-for="item in channel"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-col>
        <el-col :span="2" style="margin-top: 18px">开始时间: </el-col>
        <el-col :span="3">
          <el-date-picker
            v-model="sec_startdate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择日期时间" >
          </el-date-picker>
        </el-col>
        <!--<el-col :span="1"></el-col>-->
        <el-col :span="2" style="margin-top: 18px">结束时间: </el-col>
        <el-col :span="3">
          <el-date-picker
            v-model="sec_enddate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择日期时间">
          </el-date-picker>
        </el-col>
      </el-row>
      <el-row style="margin-top: 10px">
        <el-col :span="2" style="margin-top: 18px">呈现方式: </el-col>
        <el-col :span="2">
          <el-select
            v-model="sec_show"
            collapse-tags
            placeholder="请选择"
            v-on:change="changeShow">
            <el-option
              v-for="item in show_data"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-col>
        <el-col :span="7">
          <el-upload
            class="upload-demo"
            action="/cctv/fileupload"
            :headers="header"
            :limit="1"
            :on-success="uploadSuccess">
            <el-button size="primary" type="primary">点击上传</el-button>
          </el-upload>
          <!--<form method="POST" enctype="multipart/form-data" action="http://localhost:8080/fileupload">
            <p>
              文件：<input type="file" name="file" />
            </p>
            <p>
              <input type="submit" value="上传" />
            </p>
          </form>-->
        </el-col>
        <el-col :span="1"></el-col>
        <el-col :span="2" style="margin-left: 5px">
          <el-button type="primary" plain v-on:click="showBdData">确定</el-button>
        </el-col>
        <el-col :span="2" style="margin-left: 50px">
          <el-button type="success" plain v-on:click="downloadBdExcel">导出</el-button>
        </el-col>
      </el-row>
    </div>
    <div class="data-table" v-show="partshow">
      <el-table
        v-loading="loading"
        :data="tableData"
        style="padding: 10px 10px 0 30px">
        <el-table-column type="index" :index="bdindexMethod" label="序号" width="60"></el-table-column>
        <el-table-column prop="name" label="账号名" width="120"></el-table-column>
        <el-table-column prop="type" label="账号归属" width="100"></el-table-column>
        <el-table-column prop="channel" label="所属频道" width="100"></el-table-column>
        <el-table-column prop="pub_date" label="发稿日期" width="100"></el-table-column>
        <el-table-column prop="time" label="发稿时间" width="100"></el-table-column>
        <el-table-column prop="brand" :show-overflow-tooltip="true" label="稿件名称" width="140"></el-table-column>
        <el-table-column prop="source" label="来源" width="100"></el-table-column>
        <el-table-column prop="title" :show-overflow-tooltip="true" label="标题" width="100"></el-table-column>
        <el-table-column prop="url" :show-overflow-tooltip="true" label="URL"  width="100"></el-table-column>
        <el-table-column prop="read" label="阅读量" width="100"></el-table-column>
        <el-table-column prop="share" label="转发量/分享量" width="110"></el-table-column>
        <el-table-column prop="hd" label="互动量" width="100"></el-table-column>
        <el-table-column prop="videoNum" label="微博微视频播放量" width="140"></el-table-column>
      </el-table>
      <div class="block" style="text-align: center">
        <el-pagination layout="total,prev, pager, next" v-on:current-change="changeBdPage" :total="total" :current-page.sync = "page" :page-size="20"
                       :next-text="next" :prev-text="prev"> </el-pagination>
      </div>
    </div>
    <div class="data-table" v-show="accushow">
      <el-table
        v-loading="acculoading"
        :data="accutableData"
        style="padding: 10px 10px 0 40px">
        <el-table-column type="index" :index="accubdindexMethod" label="序号" width="60"></el-table-column>
        <el-table-column prop="brand" :show-overflow-tooltip="true" label="稿件名称" width="300"></el-table-column>
        <el-table-column prop="date" label="发稿日期" width="160"></el-table-column>
        <el-table-column prop="wx_count" label="微信发稿量" width="110"></el-table-column>
        <el-table-column prop="wx_read" label="微信阅读量" width="110"></el-table-column>
        <el-table-column prop="wx_share" label="微信分享量" width="110"></el-table-column>
        <el-table-column prop="wx_hd" label="微信互动量" width="110"></el-table-column>
        <el-table-column prop="wb_count" label="微博发稿量" width="110"></el-table-column>
        <el-table-column prop="wb_read" label="微博阅读量" width="110"></el-table-column>
        <el-table-column prop="wb_share" label="微博转发量" width="110"></el-table-column>
        <el-table-column prop="wb_hd" label="微博互动量" width="110"></el-table-column>
        <el-table-column prop="wb_video" label="微博微视频播放量" width="140"></el-table-column>
      </el-table>
      <div class="block" style="text-align: center">
        <el-pagination layout="total,prev, pager, next" v-on:current-change="changeBdPage" :total="accutotal" :current-page.sync = "accupage" :page-size="20"
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
  name: 'fileupload',
  data () {
    return {
      page: 1,
      accupage: 1,
      loading: false,
      acculoading: false,
      sec_show: '',
      accushow: false,
      partshow: true,
      header: {
        'enctype': 'multipart/form-data'
      },
      show_data: [{
        value: '1',
        label: '分天'
      }, {
        value: '2',
        label: '累计'
      }],
      belong: [{
        value: '1',
        label: '总台'
      }, {
        value: '3',
        label: '央视'
      }, {
        value: '4',
        label: '央广'
      }, {
        value: '5',
        label: '国广'
      }],
      channel: [{
        value: '17',
        label: '全部'
      }, {
        value: '18',
        label: '央广'
      }, {
        value: '19',
        label: '国广'
      }, {
        value: '0',
        label: '央视网'
      }, {
        value: '1',
        label: 'CGTN'
      }, {
        value: '2',
        label: 'CCTV-1综合'
      }, {
        value: '3',
        label: 'CCTV-2财经'
      }, {
        value: '4',
        label: 'CCTV-3综艺'
      }, {
        value: '5',
        label: 'CCTV-4中文国际'
      }, {
        value: '6',
        label: 'CCTV-5体育'
      }, {
        value: '7',
        label: 'CCTV-5+体育赛事'
      }, {
        value: '8',
        label: 'CCTV-7军事农业'
      }, {
        value: '9',
        label: 'CCTV-8电视剧'
      }, {
        value: '10',
        label: 'CCTV-9纪录'
      }, {
        value: '11',
        label: 'CCTV-10科教'
      }, {
        value: '12',
        label: 'CCTV-11戏曲'
      }, {
        value: '13',
        label: 'CCTV-12社会与法'
      }, {
        value: '14',
        label: 'CCTV-13新闻'
      }, {
        value: '15',
        label: 'CCTV-14少儿'
      }, {
        value: '16',
        label: 'CCTV-15音乐'
      }],
      sec_type: '',
      sec_channel: '',
      sec_startdate: '',
      sec_enddate: '',
      total: 0,
      accutotal: 0,
      next: '下一页',
      prev: '上一页',
      tableData: [],
      accutableData: [],
      keyword: []
    }
  },
  methods: {
    changeShow () {
      let show = this.sec_show
      if (show === '1') {
        this.partshow = true
        this.accushow = false
      }
      if (show === '2') {
        this.accushow = true
        this.partshow = false
      }
    },
    changeBdLevelSelect () {
      this.sec_channel = ''
      let type = this.sec_type
      if (type === '1') {
        this.channel = [{
          value: '17',
          label: '全部'
        }, {
          value: '18',
          label: '央广'
        }, {
          value: '19',
          label: '国广'
        }, {
          value: '0',
          label: '央视网'
        }, {
          value: '1',
          label: 'CGTN'
        }, {
          value: '2',
          label: 'CCTV-1综合'
        }, {
          value: '3',
          label: 'CCTV-2财经'
        }, {
          value: '4',
          label: 'CCTV-3综艺'
        }, {
          value: '5',
          label: 'CCTV-4中文国际'
        }, {
          value: '6',
          label: 'CCTV-5体育'
        }, {
          value: '7',
          label: 'CCTV-5+体育赛事'
        }, {
          value: '8',
          label: 'CCTV-7军事农业'
        }, {
          value: '9',
          label: 'CCTV-8电视剧'
        }, {
          value: '10',
          label: 'CCTV-9纪录'
        }, {
          value: '11',
          label: 'CCTV-10科教'
        }, {
          value: '12',
          label: 'CCTV-11戏曲'
        }, {
          value: '13',
          label: 'CCTV-12社会与法'
        }, {
          value: '14',
          label: 'CCTV-13新闻'
        }, {
          value: '15',
          label: 'CCTV-14少儿'
        }, {
          value: '16',
          label: 'CCTV-15音乐'
        }]
      }
      if (type === '3') {
        this.channel = [{
          value: '20',
          label: '全部'
        }, {
          value: '0',
          label: '央视网'
        }, {
          value: '1',
          label: 'CGTN'
        }, {
          value: '2',
          label: 'CCTV-1综合'
        }, {
          value: '3',
          label: 'CCTV-2财经'
        }, {
          value: '4',
          label: 'CCTV-3综艺'
        }, {
          value: '5',
          label: 'CCTV-4中文国际'
        }, {
          value: '6',
          label: 'CCTV-5体育'
        }, {
          value: '7',
          label: 'CCTV-5+体育赛事'
        }, {
          value: '8',
          label: 'CCTV-7军事农业'
        }, {
          value: '9',
          label: 'CCTV-8电视剧'
        }, {
          value: '10',
          label: 'CCTV-9纪录'
        }, {
          value: '11',
          label: 'CCTV-10科教'
        }, {
          value: '12',
          label: 'CCTV-11戏曲'
        }, {
          value: '13',
          label: 'CCTV-12社会与法'
        }, {
          value: '14',
          label: 'CCTV-13新闻'
        }, {
          value: '15',
          label: 'CCTV-14少儿'
        }, {
          value: '16',
          label: 'CCTV-15音乐'
        }]
      }
      if (type === '4') {
        this.channel = [{
          value: '18',
          label: '央广'
        }]
      }
      if (type === '5') {
        this.channel = [{
          value: '19',
          label: '国广'
        }]
      }
    },
    showBdData () {
      let _this = this
      let accu = this.sec_show
      if (accu === '1') {
        this.page = 1
        this.loading = true
      } else {
        this.accupage = 1
        this.acculoading = true
      }
      let type = this.sec_type
      let channel = this.sec_channel
      let startdate = this.sec_startdate
      let enddate = this.sec_enddate
      var params = new URLSearchParams()
      params.append('type', type)
      params.append('channel', channel)
      params.append('startdate', startdate)
      params.append('enddate', enddate)
      params.append('accu', accu)
      params.append('page', 1)
      params.append('size', 20)
      params.append('key', this.keyword)
      // axios.get('/cctv/filedata', {
      // axios.get('http://localhost:8080/filedata', {
      //   params: {
      //     type: type,
      //     channel: channel,
      //     startdate: startdate,
      //     enddate: enddate,
      //     accu: accu,
      //     page: 1,
      //     size: 20
      //   }
      // }
      // axios.post('http://localhost:8080/filedata', params).then(function (response) {
      axios.post('/cctv/filedata', params).then(function (response) {
        if (accu === '1') {
          _this.tableData = response.data.tableData
          _this.total = response.data.total
          _this.loading = false
        } else {
          _this.accutableData = response.data.tableData
          _this.accutotal = response.data.total
          _this.acculoading = false
        }
      })
    },
    bdindexMethod (index) {
      return index + 20 * (this.page - 1) + 1
    },
    accubdindexMethod (index) {
      return index + 20 * (this.accupage - 1) + 1
    },
    changeBdPage (val) {
      // console.log('change', val)
      let accu = this.sec_show
      let _this = this
      if (accu === '1') {
        this.page = val
        this.loading = true
      } else {
        this.accupage = val
        this.acculoading = true
      }
      let type = this.sec_type
      let channel = this.sec_channel
      let startdate = this.sec_startdate
      let enddate = this.sec_enddate
      var params = new URLSearchParams()
      params.append('type', type)
      params.append('channel', channel)
      params.append('startdate', startdate)
      params.append('enddate', enddate)
      params.append('accu', accu)
      params.append('page', val)
      params.append('size', 20)
      params.append('key', this.keyword)
      // axios.get('/cctv/filedata', {
      // axios.get('/filedata', {
      //   params: {
      //     type: type,
      //     channel: channel,
      //     startdate: startdate,
      //     enddate: enddate,
      //     accu: accu,
      //     page: val,
      //     size: 20
      //   }
      // }).then(function (response) {
      // axios.post('http://localhost:8080/filedata', params).then(function (response) {
      axios.post('/cctv/filedata', params).then(function (response) {
        if (accu === '1') {
          _this.tableData = response.data.tableData
          _this.total = response.data.total
          _this.loading = false
        } else {
          _this.accutableData = response.data.tableData
          _this.accutotal = response.data.total
          _this.acculoading = false
        }
      })
    },
    downloadBdExcel () {
      let accu = this.sec_show
      if (accu === '1') {
        this.loading = true
      } else {
        this.acculoading = true
      }
      let type = this.sec_type
      let channel = this.sec_channel
      let startdate = this.sec_startdate
      let enddate = this.sec_enddate
      let _this = this
      var params = new URLSearchParams()
      params.append('type', type)
      params.append('channel', channel)
      params.append('startdate', startdate)
      params.append('enddate', enddate)
      params.append('accu', accu)
      params.append('key', this.keyword)
      axios({
        method: 'post',
        url: '/cctv/file/download',
        // url: 'http://localhost:8080/file/download',
        data: params,
        responseType: 'blob'
      }).then(function (response) {
      // axios.post('http://localhost:8080/file/download', params).then(function (response) {
        let url = window.URL.createObjectURL(new Blob([response.data]))
        let link = document.createElement('a')
        link.style.display = 'none'
        link.href = url
        link.setAttribute('download', '稿件' + startdate + '-' + enddate + '数据.xls')
        document.body.appendChild(link)
        if (accu === '1') {
          _this.loading = false
        } else {
          _this.acculoading = false
        }
        link.click()
      })
    },
    uploadSuccess (data) {
      this.keyword = data
    }
  }
}
</script>

<style lang='scss'>
  @import url("../../node_modules/element-ui/lib/theme-chalk/index.css");
  .uploadFile {
    padding: 0px 10px;
    .choose {
      background: #FFFFFF;
      height: 140px;
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
