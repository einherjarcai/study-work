<template>
  <div class="account">
    <div class="source">
      <el-row>
        <el-col :span="12">
          <span @click="change(0)"  v-bind:style="{ color: isOnClick0 }">微信
          </span>
        </el-col>
        <el-col :span="12">
         <span @click="change(1)"  v-bind:style="{ color: isOnClick1 }">微博
          </span>
        </el-col>
      </el-row>
    </div>
    <div class="choose" v-show="wx_show">
      <el-row>
        <el-col :span="2" style="margin-top: 18px">账号归属: </el-col>
        <el-col :span="3">
          <el-select v-model="wx_sec_type"  placeholder="请选择" v-on:change="changeWxLevelSelect">
            <el-option
              v-for="item in belong"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-col>
        <el-col :span="2"></el-col>
        <el-col :span="2" style="margin-top: 18px">账号级别: </el-col>
        <el-col :span="3">
          <el-select
            v-model="wx_sec_level"
            collapse-tags
            placeholder="请选择"
            v-on:change="changeWxChannelSelect">
            <el-option
              v-for="item in wx_level"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-col>
        <el-col :span="2"></el-col>
        <el-col :span="2" style="margin-top: 18px">所属频道: </el-col>
        <el-col :span="3">
          <el-select
            v-model="wx_sec_channel"
            collapse-tags
            placeholder="请选择">
            <el-option
              v-for="item in wx_channel"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-col>
        <el-col :span="4" style="margin-top: 8px">
          自采更新时间：{{wxbd_update}}
        </el-col>
        <el-col :span="4" style="margin-top: 5px">
          清博更新时间：{{wxqb_update}}
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="2" style="margin-top: 18px">开始时间: </el-col>
        <el-col :span="3">
          <el-date-picker
            v-model="wx_sec_startdate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择日期时间" >
          </el-date-picker>
        </el-col>
        <el-col :span="2"></el-col>
        <el-col :span="2" style="margin-top: 18px">结束时间: </el-col>
        <el-col :span="3">
          <el-date-picker
            v-model="wx_sec_enddate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择日期时间">
          </el-date-picker>
        </el-col>
        <el-col :span="2"></el-col>
        <el-col :span="2" style="margin-top: 18px">呈现方式: </el-col>
        <el-col :span="3">
          <el-select
            v-model="wx_sec_show"
            collapse-tags
            placeholder="请选择">
            <el-option
              v-for="item in wx_showData"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-col>
        <el-col :span="2">
          <el-button type="primary" plain v-on:click="showWxData">确定</el-button>
        </el-col>
        <el-col :span="2">
          <el-button type="success" plain v-on:click="downloadWxExcel">导出</el-button>
        </el-col>
      </el-row>
      <!--<el-row>
        <el-col :span="4" style="margin-top: 18px">
          自采更新时间：{{wxbd_update}}
        </el-col>
        <el-col :span="3"></el-col>
        <el-col :span="4" style="margin-top: 18px">
          清博更新时间：{{wxqb_update}}
        </el-col>
      </el-row>-->
    </div>
    <div class="data-table" v-show="wx_show">
      <el-table
        v-loading="wxloading"
        :data="wx_tableData"
        style="padding: 10px 0 0 10px">
        <el-table-column type="index" :index="wxindexMethod" label="序号" width="100"></el-table-column>
        <el-table-column prop="name" label="账号名" width="140"></el-table-column>
        <el-table-column prop="type" label="账号归属" width="140"></el-table-column>
        <el-table-column prop="level" label="账号级别" width="140"></el-table-column>
        <el-table-column prop="channel" label="所属频道" width="140"></el-table-column>
        <el-table-column prop="date" label="时间" width="120"></el-table-column>
        <!--<el-table-column prop="name" label="认证情况" width="120"></el-table-column>-->
        <el-table-column prop="flowers" label="订阅数" width="140"></el-table-column>
        <el-table-column prop="article_count" label="发稿量" width="120"></el-table-column>
        <el-table-column prop="read" label="阅读量" width="140"></el-table-column>
        <!--<el-table-column prop="name" label="视频播放量" width="120"></el-table-column>-->
        <el-table-column prop="hd" label="互动量" width="140"></el-table-column>
      </el-table>
      <div class="block" style="text-align: center">
        <el-pagination layout="total,prev, pager, next" v-on:current-change="changeWxPage" :total="wx_total" :current-page.sync = "wx_page" :page-size="20"
                       :next-text="next" :prev-text="prev"> </el-pagination>
      </div>
    </div>
    <div class="choose" v-show="wb_show">
      <el-row>
        <el-col :span="2" style="margin-top: 18px">账号归属: </el-col>
        <el-col :span="3">
          <el-select v-model="wb_sec_type"  placeholder="请选择" v-on:change="changeWbLevelselect">
            <el-option
              v-for="item in belong"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-col>
        <el-col :span="2"></el-col>
        <el-col :span="2" style="margin-top: 18px">账号级别: </el-col>
        <el-col :span="3">
          <el-select
            v-model="wb_sec_level"
            collapse-tags
            placeholder="请选择"
            v-on:change="changeWbChannelSelect">
            <el-option
              v-for="item in wb_level"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-col>
        <el-col :span="2"></el-col>
        <el-col :span="2" style="margin-top: 18px">所属频道: </el-col>
        <el-col :span="3">
            <el-select
              v-model="wb_sec_channel"
              collapse-tags
              placeholder="请选择">
              <el-option
                v-for="item in wb_channel"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
        </el-col>
        <el-col :span="1"></el-col>
      </el-row>
      <el-row>
        <el-col :span="2" style="margin-top: 18px">开始时间: </el-col>
        <el-col :span="3">
          <el-date-picker
            v-model="wb_sec_startdate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择日期时间" >
          </el-date-picker>
        </el-col>
        <el-col :span="2"></el-col>
        <el-col :span="2" style="margin-top: 18px">结束时间: </el-col>
        <el-col :span="3">
          <el-date-picker
            v-model="wb_sec_enddate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择日期时间">
          </el-date-picker>
        </el-col>
        <el-col :span="2"></el-col>
        <el-col :span="2" style="margin-top: 18px">呈现方式: </el-col>
        <el-col :span="3">
          <el-select
            v-model="wb_sec_show"
            collapse-tags
            placeholder="请选择">
            <el-option
              v-for="item in wb_showData"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-col>
        <el-col :span="2">
          <el-button type="primary" plain v-on:click="showWbData">确定</el-button>
        </el-col>
        <el-col :span="2">
          <el-button type="success" plain v-on:click="downloadWbExcel">导出</el-button>
        </el-col>
      </el-row>
    </div>
    <div class="data-table" v-show="wb_show">
      <el-table
        v-loading="wbloading"
        :data="wb_tableData"
        style="padding: 10px 0 0 10px">
        <el-table-column type="index" :index="wbindexMethod" label="序号" width="80"></el-table-column>
        <el-table-column prop="name" label="账号名" width="120"></el-table-column>
        <el-table-column prop="type" label="账号归属" width="120"></el-table-column>
        <el-table-column prop="level" label="账号级别" width="120"></el-table-column>
        <el-table-column prop="channel" label="所属频道" width="140"></el-table-column>
        <el-table-column prop="date" label="时间" width="120"></el-table-column>
        <!--<el-table-column prop="name" label="认证情况" width="120"></el-table-column>-->
        <el-table-column prop="flowers" label="粉丝数" width="120"></el-table-column>
        <el-table-column prop="article_count" label="发稿量" width="120"></el-table-column>
        <el-table-column prop="read" label="阅读量" width="120"></el-table-column>
        <el-table-column prop="live" label="直播视频播放量" width="120"></el-table-column>
        <el-table-column prop="video" label="点播视频播放量" width="120"></el-table-column>
        <el-table-column prop="hd" label="互动量" width="120"></el-table-column>
      </el-table>
      <div class="block" style="text-align: center">
        <el-pagination layout="total,prev, pager, next" v-on:current-change="changeWbPage" :total="wb_total" :current-page.sync = "wb_page" :page-size="20"
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
  name: 'account',
  data () {
    return {
      wx_page: 1,
      wb_page: 1,
      wxloading: false,
      wbloading: false,
      isOnClick0: '#19c2c8',
      isOnClick1: '#cccccc',
      wx_show: true,
      wb_show: false,
      belong: [{
        value: '0',
        label: '全部'
      }, {
        value: '1',
        label: '总台'
      }, {
        value: '2',
        label: '竞品'
      }, {
        value: '3',
        label: '央视'
      }, {
        value: '4',
        label: '央广'
      }, {
        value: '5',
        label: '国广'
      }, {
        value: '6',
        label: '人民'
      }, {
        value: '7',
        label: '新华'
      }, {
        value: '8',
        label: '其他'
      }],
      wx_level: [{
        value: '0',
        label: '全部'
      }, {
        value: '1',
        label: '台级'
      }, {
        value: '2',
        label: '频道级'
      }, {
        value: '3',
        label: '栏目级'
      }, {
        value: '4',
        label: '央视网'
      }, {
        value: '5',
        label: '央视其他'
      }, {
        value: '6',
        label: '人民日报'
      }, {
        value: '7',
        label: '新华社'
      }, {
        value: '8',
        label: '其他'
      }],
      wb_level: [{
        value: '0',
        label: '全部'
      }, {
        value: '1',
        label: '台级'
      }, {
        value: '2',
        label: '频道级'
      }, {
        value: '3',
        label: '栏目级'
      }, {
        value: '4',
        label: '央视网'
      }, {
        value: '5',
        label: '央视其他'
      }, {
        value: '6',
        label: '人民日报'
      }, {
        value: '7',
        label: '新华社'
      }, {
        value: '8',
        label: '其他'
      }],
      wx_channel: [{
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
      }, {
        value: '20',
        label: '总编室'
      }, {
        value: '21',
        label: '央视其他'
      }, {
        value: '22',
        label: '人民'
      }, {
        value: '23',
        label: '新华'
      }, {
        value: '24',
        label: '其他'
      }],
      wb_channel: [{
        value: '17',
        label: '全部'
      }, {
        value: '18',
        label: '央广'
      }, {
        value: '19',
        label: '国广'
      }, {
        value: '20',
        label: '总编室'
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
        value: '25',
        label: 'CCTV-6电影'
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
      }, {
        value: '21',
        label: '央视其他'
      }, {
        value: '22',
        label: '人民'
      }, {
        value: '23',
        label: '新华'
      }, {
        value: '24',
        label: '其他'
      }],
      wx_showData: [{
        value: '0',
        label: '分天'
      }, {
        value: '1',
        label: '累计'
      }],
      wb_showData: [{
        value: '0',
        label: '分天'
      }, {
        value: '1',
        label: '累计'
      }],
      wx_sec_show: '',
      wb_sec_show: '',
      wx_sec_type: '',
      wx_sec_level: '',
      wx_sec_channel: '',
      wxbd_update: '',
      wxqb_update: '',
      wx_sec_startdate: '',
      wx_sec_enddate: '',
      wb_sec_startdate: '',
      wb_sec_enddate: '',
      wb_sec_type: '',
      wb_sec_level: '',
      wb_sec_channel: '',
      wb_sec_date: '',
      wx_total: 0,
      wb_total: 0,
      next: '下一页',
      prev: '上一页',
      wx_tableData: [],
      wb_tableData: []
    }
  },
  created () {
    let this1 = this
    axios.get('/cctv/account/weixin/update', {
    // axios.get('http://localhost:8080/account/weixin/update', {
    }).then(function (response) {
      // console.log(response.data)
      this1.wxbd_update = response.data.bd_date
      this1.wxqb_update = response.data.qb_date
    })
  },
  methods: {
    change (flag) {
      if (flag === 0) {
        this.isOnClick0 = '#19c2c8'
        this.isOnClick1 = '#cccccc'
        this.wx_show = true
        this.wb_show = false
      }
      if (flag === 1) {
        this.isOnClick0 = '#cccccc'
        this.isOnClick1 = '#19c2c8'
        this.wx_show = false
        this.wb_show = true
      }
    },
    changeWxLevelSelect () {
      this.wx_sec_level = ''
      this.wx_sec_channel = ''
      let type = this.wx_sec_type
      if (type === '0') {
        this.wx_level = [{
          value: '0',
          label: '全部'
        }, {
          value: '1',
          label: '台级'
        }, {
          value: '2',
          label: '频道级'
        }, {
          value: '3',
          label: '栏目级'
        }, {
          value: '4',
          label: '央视网'
        }, {
          value: '5',
          label: '央视其他'
        }, {
          value: '6',
          label: '人民日报'
        }, {
          value: '7',
          label: '新华社'
        }, {
          value: '8',
          label: '其他'
        }]
      }
      if (type === '1' || type === '3') {
        this.wx_level = [{
          value: '0',
          label: '全部'
        }, {
          value: '1',
          label: '台级'
        }, {
          value: '2',
          label: '频道级'
        }, {
          value: '3',
          label: '栏目级'
        }, {
          value: '4',
          label: '央视网'
        }, {
          value: '5',
          label: '央视其他'
        }]
      }
      if (type === '2') {
        this.wx_level = [{
          value: '0',
          label: '全部'
        }, {
          value: '6',
          label: '人民日报'
        }, {
          value: '7',
          label: '新华社'
        }, {
          value: '8',
          label: '其他'
        }]
      }
      if (type === '4') {
        this.wx_level = [{
          value: '1',
          label: '台级'
        }]
        this.wx_channel = [{
          value: '18',
          label: '央广'
        }]
      }
      if (type === '5') {
        this.wx_level = [{
          value: '1',
          label: '台级'
        }]
        this.wx_channel = [{
          value: '19',
          label: '国广'
        }]
      }
      if (type === '6') {
        this.wx_level = [{
          value: '6',
          label: '人民日报'
        }]
        this.wx_channel = [{
          value: '22',
          label: '人民'
        }]
      }
      if (type === '7') {
        this.wx_level = [{
          value: '7',
          label: '新华社'
        }]
        this.wx_channel = [{
          value: '23',
          label: '新华'
        }]
      }
      if (type === '8') {
        this.wx_level = [{
          value: '8',
          label: '其他'
        }]
        this.wx_channel = [{
          value: '24',
          label: '其他'
        }]
      }
    },
    changeWxChannelSelect () {
      this.wx_sec_channel = ''
      let type = this.wx_sec_type
      let level = this.wx_sec_level
      if (type === '0') {
        this.wx_channel = [{
          value: '17',
          label: '全部'
        }, {
          value: '18',
          label: '央广'
        }, {
          value: '19',
          label: '国广'
        }, {
          value: '20',
          label: '总编室'
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
        }, {
          value: '21',
          label: '央视其他'
        }, {
          value: '22',
          label: '人民'
        }, {
          value: '23',
          label: '新华'
        }, {
          value: '24',
          label: '其他'
        }]
      }
      if (type === '1' && level === '0') {
        this.wx_channel = [{
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
      if (type === '1' && level === '1') {
        this.wx_channel = [{
          value: '17',
          label: '全部'
        }, {
          value: '18',
          label: '央广'
        }, {
          value: '19',
          label: '国广'
        }, {
          value: '20',
          label: '总编室'
        }]
      }
      if (type === '1' && level === '2') {
        this.wx_channel = [{
          value: '17',
          label: '全部'
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
      if (type === '1' && level === '3') {
        this.wx_channel = [{
          value: '17',
          label: '全部'
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
      if (type === '1' && level === '4') {
        this.wx_channel = [{
          value: '0',
          label: '央视网'
        }]
      }
      if (type === '1' && level === '5') {
        this.wx_channel = [{
          value: '21',
          label: '央视其他'
        }]
      }
      if (type === '2' && level === '0') {
        this.wx_channel = [{
          value: '17',
          label: '全部'
        }, {
          value: '22',
          label: '人民'
        }, {
          value: '23',
          label: '新华'
        }, {
          value: '24',
          label: '其他'
        }]
      }
      if (type === '2' && level === '6') {
        this.wx_channel = [{
          value: '22',
          label: '人民'
        }]
      }
      if (type === '2' && level === '7') {
        this.wx_channel = [{
          value: '23',
          label: '新华'
        }]
      }
      if (type === '2' && level === '8') {
        this.wx_channel = [{
          value: '24',
          label: '其他'
        }]
      }
      if (type === '3' && level === '0') {
        this.wx_channel = [{
          value: '17',
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
      if (type === '3' && level === '1') {
        this.wx_channel = [{
          value: '20',
          label: '总编室'
        }]
      }
      if (type === '3' && level === '2') {
        this.wx_channel = [{
          value: '17',
          label: '全部'
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
      if (type === '3' && level === '3') {
        this.wx_channel = [{
          value: '17',
          label: '全部'
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
      if (type === '3' && level === '4') {
        this.wx_channel = [{
          value: '0',
          label: '央视网'
        }]
      }
      if (type === '3' && level === '5') {
        this.wx_channel = [{
          value: '21',
          label: '央视其他'
        }]
      }
    },
    changeWbLevelselect () {
      this.wb_sec_channel = ''
      this.wb_sec_level = ''
      // console.log(this.wb_sec_type)
      let type = this.wb_sec_type
      if (type === '0') {
        this.wb_level = [{
          value: '0',
          label: '全部'
        }, {
          value: '1',
          label: '台级'
        }, {
          value: '2',
          label: '频道级'
        }, {
          value: '3',
          label: '栏目级'
        }, {
          value: '4',
          label: '央视网'
        }, {
          value: '5',
          label: '央视其他'
        }, {
          value: '6',
          label: '人民日报'
        }, {
          value: '7',
          label: '新华社'
        }, {
          value: '8',
          label: '其他'
        }]
      }
      if (type === '1' || type === '3') {
        this.wb_level = [{
          value: '0',
          label: '全部'
        }, {
          value: '1',
          label: '台级'
        }, {
          value: '2',
          label: '频道级'
        }, {
          value: '3',
          label: '栏目级'
        }, {
          value: '4',
          label: '央视网'
        }, {
          value: '5',
          label: '央视其他'
        }]
      }
      if (type === '2') {
        this.wb_level = [{
          value: '0',
          label: '全部'
        }, {
          value: '6',
          label: '人民日报'
        }, {
          value: '7',
          label: '新华社'
        }, {
          value: '8',
          label: '其他'
        }]
      }
      if (type === '4') {
        this.wb_level = [{
          value: '1',
          label: '台级'
        }]
        this.wb_channel = [{
          value: '18',
          label: '央广'
        }]
      }
      if (type === '5') {
        this.wb_level = [{
          value: '1',
          label: '台级'
        }]
        this.wb_channel = [{
          value: '19',
          label: '国广'
        }]
      }
      if (type === '6') {
        this.wb_level = [{
          value: '6',
          label: '人民日报'
        }]
        this.wb_channel = [{
          value: '22',
          label: '人民'
        }]
      }
      if (type === '7') {
        this.wb_level = [{
          value: '7',
          label: '新华社'
        }]
        this.wb_channel = [{
          value: '23',
          label: '新华'
        }]
      }
      if (type === '8') {
        this.wb_level = [{
          value: '8',
          label: '其他'
        }]
        this.wb_channel = [{
          value: '24',
          label: '其他'
        }]
      }
    },
    changeWbChannelSelect () {
      this.wb_sec_channel = ''
      let level = this.wb_sec_level
      let type = this.wb_sec_type
      if (type === '0') {
        this.wb_channel = [{
          value: '17',
          label: '全部'
        }, {
          value: '18',
          label: '央广'
        }, {
          value: '19',
          label: '国广'
        }, {
          value: '20',
          label: '总编室'
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
        }, {
          value: '21',
          label: '央视其他'
        }, {
          value: '22',
          label: '人民'
        }, {
          value: '23',
          label: '新华'
        }, {
          value: '24',
          label: '其他'
        }]
      }
      if (type === '1' && level === '0') {
        this.wb_channel = [{
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
      if (type === '1' && level === '1') {
        this.wb_channel = [{
          value: '17',
          label: '全部'
        }, {
          value: '18',
          label: '央广'
        }, {
          value: '19',
          label: '国广'
        }, {
          value: '20',
          label: '总编室'
        }]
      }
      if (type === '1' && level === '2') {
        this.wb_channel = [{
          value: '17',
          label: '全部'
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
      if (type === '1' && level === '3') {
        this.wb_channel = [{
          value: '17',
          label: '全部'
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
      if (type === '1' && level === '4') {
        this.wb_channel = [{
          value: '0',
          label: '央视网'
        }]
      }
      if (type === '1' && level === '5') {
        this.wb_channel = [{
          value: '21',
          label: '央视其他'
        }]
      }
      if (type === '2' && level === '0') {
        this.wb_channel = [{
          value: '17',
          label: '全部'
        }, {
          value: '22',
          label: '人民'
        }, {
          value: '23',
          label: '新华'
        }, {
          value: '24',
          label: '其他'
        }]
      }
      if (type === '2' && level === '6') {
        this.wb_channel = [{
          value: '22',
          label: '人民'
        }]
      }
      if (type === '2' && level === '7') {
        this.wb_channel = [{
          value: '23',
          label: '新华'
        }]
      }
      if (type === '2' && level === '8') {
        this.wb_channel = [{
          value: '24',
          label: '其他'
        }]
      }
      if (type === '3' && level === '0') {
        this.wb_channel = [{
          value: '17',
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
      if (type === '3' && level === '1') {
        this.wb_channel = [{
          value: '17',
          label: '总编室'
        }]
      }
      if (type === '3' && level === '2') {
        this.wb_channel = [{
          value: '17',
          label: '全部'
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
      if (type === '3' && level === '3') {
        this.wb_channel = [{
          value: '17',
          label: '全部'
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
      if (type === '3' && level === '4') {
        this.wb_channel = [{
          value: '0',
          label: '央视网'
        }]
      }
      if (type === '3' && level === '5') {
        this.wb_channel = [{
          value: '21',
          label: '央视其他'
        }]
      }
    },
    showWxData () {
      let _this = this
      this.wx_page = 1
      let type = this.wx_sec_type
      let level = this.wx_sec_level
      let channel = this.wx_sec_channel
      let startdate = this.wx_sec_startdate
      let enddate = this.wx_sec_enddate
      let accu = this.wx_sec_show
      this.wxloading = true
      axios.get('/cctv/account/weixin', {
      // axios.get('http://localhost:8080/account/weixin', {
        params: {
          type: type,
          level: level,
          channel: channel,
          startdate: startdate,
          enddate: enddate,
          accu: accu,
          page: 1,
          size: 20
        }
      }).then(function (response) {
        _this.wx_tableData = response.data.tableData
        _this.wx_total = response.data.total
        _this.wxloading = false
      })
    },
    showWbData () {
      let _this = this
      this.wb_page = 1
      let type = this.wb_sec_type
      let level = this.wb_sec_level
      let channel = this.wb_sec_channel
      let startdate = this.wb_sec_startdate
      let enddate = this.wb_sec_enddate
      let accu = this.wb_sec_show
      this.wbloading = true
      axios.get('/cctv/account/weibo', {
      // axios.get('http://localhost:8080/account/weibo', {
        params: {
          type: type,
          level: level,
          channel: channel,
          startdate: startdate,
          enddate: enddate,
          accu: accu,
          page: 1,
          size: 20
        }
      }).then(function (response) {
        _this.wb_tableData = response.data.tableData
        _this.wb_total = response.data.total
        _this.wbloading = false
      })
    },
    changeWxPage (val) {
      this.wx_page = val
      let _this = this
      let type = this.wx_sec_type
      let level = this.wx_sec_level
      let channel = this.wx_sec_channel
      let startdate = this.wx_sec_startdate
      let enddate = this.wx_sec_enddate
      let accu = this.wx_sec_show
      this.wxloading = true
      axios.get('/cctv/account/weixin', {
        params: {
          type: type,
          level: level,
          channel: channel,
          startdate: startdate,
          enddate: enddate,
          accu: accu,
          page: val,
          size: 20
        }
      }).then(function (response) {
        _this.wx_tableData = response.data.tableData
        _this.wx_total = response.data.total
        _this.wxloading = false
      })
    },
    changeWbPage (val) {
      this.wb_page = val
      let _this = this
      let type = this.wb_sec_type
      let level = this.wb_sec_level
      let channel = this.wb_sec_channel
      let startdate = this.wb_sec_startdate
      let enddate = this.wb_sec_enddate
      let accu = this.wb_sec_show
      this.wbloading = true
      axios.get('/cctv/account/weibo', {
        params: {
          type: type,
          level: level,
          channel: channel,
          startdate: startdate,
          enddate: enddate,
          accu: accu,
          page: val,
          size: 20
        }
      }).then(function (response) {
        _this.wb_tableData = response.data.tableData
        _this.wb_total = response.data.total
        _this.wbloading = false
      })
    },
    wxindexMethod (index) {
      return index + 20 * (this.wx_page - 1) + 1
    },
    wbindexMethod (index) {
      return index + 20 * (this.wb_page - 1) + 1
    },
    downloadWxExcel () {
      let type = this.wx_sec_type
      let level = this.wx_sec_level
      let channel = this.wx_sec_channel
      let startdate = this.wx_sec_startdate
      let enddate = this.wx_sec_enddate
      let accu = this.wx_sec_show
      let show = null
      if (accu === '0') {
        show = '分天'
      } else if (accu === '1') {
        show = '累计'
      }
      this.wxloading = true
      let _this = this
      axios({
        method: 'get',
        url: '/cctv/account/weixin/download',
        params: {
          type: type,
          level: level,
          channel: channel,
          startdate: startdate,
          enddate: enddate,
          accu: accu
        },
        responseType: 'blob'
      }).then(function (response) {
        let url = window.URL.createObjectURL(new Blob([response.data]))
        let link = document.createElement('a')
        link.style.display = 'none'
        link.href = url
        link.setAttribute('download', '微信' + startdate + '-' + enddate + show + '账号数据.xls')
        document.body.appendChild(link)
        link.click()
        _this.wxloading = false
      })
    },
    downloadWbExcel () {
      let type = this.wb_sec_type
      let level = this.wb_sec_level
      let channel = this.wb_sec_channel
      let startdate = this.wb_sec_startdate
      let enddate = this.wb_sec_enddate
      let accu = this.wb_sec_show
      let show = null
      if (accu === '0') {
        show = '分天'
      } else if (accu === '1') {
        show = '累计'
      }
      this.wbloading = true
      let _this = this
      axios({
        method: 'get',
        url: '/cctv/account/weibo/download',
        params: {
          type: type,
          level: level,
          channel: channel,
          startdate: startdate,
          enddate: enddate,
          accu: accu
        },
        responseType: 'blob'
      }).then(function (response) {
        let url = window.URL.createObjectURL(new Blob([response.data]))
        let link = document.createElement('a')
        link.style.display = 'none'
        link.href = url
        link.setAttribute('download', '微博' + startdate + '-' + enddate + show + '账号数据.xls')
        document.body.appendChild(link)
        link.click()
        _this.wbloading = false
      })
    }
  }
}
</script>

<style lang='scss'>
  @import url("../../node_modules/element-ui/lib/theme-chalk/index.css");
  .account {
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
          .el-date-editor.el-input, .el-date-editor.el-input__inner{
            width: 170px;
          }
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
