<template>
  <div class="article">
    <div class="source">
      <el-row>
        <el-col :span="8">
          <span @click="change(0)"  v-bind:style="{ color: isOnClick0 }">微信
          </span>
        </el-col>
        <el-col :span="8">
         <span @click="change(1)"  v-bind:style="{ color: isOnClick1 }">微博
          </span>
        </el-col>
        <el-col :span="8">
         <span @click="change(2)"  v-bind:style="{ color: isOnClick2 }">新媒体品牌
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
        <!--<el-col :span="1"></el-col>-->
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
        <!--<el-col :span="1"></el-col>-->
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
        <el-col :span="1" style="margin-top: 18px; margin-left: 20px">关键词: </el-col>
        <el-col :span="2" style="margin-left: 10px">
          <el-select v-model="wx_sec_source"  placeholder="请选择">
            <el-option
              v-for="item in keyword"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-col>
        <el-col :span="4" style="margin-left: 10px">
          <el-input v-model="wx_sec_keyword" placeholder="请输入内容"></el-input>
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
        <el-col :span="2" style="margin-top: 18px">结束时间: </el-col>
        <el-col :span="3">
          <el-date-picker
            v-model="wx_sec_enddate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择日期时间">
          </el-date-picker>
        </el-col>
        <el-col :span="2" style="margin-left: 20px">
          <el-button type="primary" plain v-on:click="showWxData">确定</el-button>
        </el-col>
        <el-col :span="2" style="margin-left: 150px">
          <el-button type="success" plain v-on:click="downloadWxExcel">导出</el-button>
        </el-col>
        <el-col :span="4" style="margin-top: 18px">
          数据采集时间：{{wx_update}}
        </el-col>
      </el-row>
    </div>
    <div class="data-table" v-show="wx_show">
      <el-table
        v-loading="wxloading"
        :data="wx_tableData"
        style="padding: 10px 10px 0 30px">
        <el-table-column type="index" :index="wxindexMethod" label="序号" width="60"></el-table-column>
        <el-table-column prop="name" label="账号名" width="120"></el-table-column>
        <el-table-column prop="type" label="账号归属" width="100"></el-table-column>
        <el-table-column prop="level" label="账号级别" width="100"></el-table-column>
        <el-table-column prop="channel" label="所属频道" width="100"></el-table-column>
        <el-table-column prop="pub_date" label="发稿日期" width="100"></el-table-column>
        <!--<el-table-column prop="time" label="发稿时间" width="100"></el-table-column>-->
        <!--<el-table-column prop="name" label="认证情况" width="100"></el-table-column>-->
        <el-table-column prop="istop" label="是否头条" width="100"></el-table-column>
        <el-table-column prop="url" :show-overflow-tooltip="true" label="URL"  width="100"></el-table-column>
        <el-table-column prop="title" :show-overflow-tooltip="true" label="标题" width="100"></el-table-column>
        <el-table-column prop="read" label="阅读量" width="100"></el-table-column>
        <el-table-column prop="share" label="分享量" width="100"></el-table-column>
        <el-table-column prop="add" label="收藏数" width="100"></el-table-column>
        <el-table-column prop="like" label="点赞量" width="100"></el-table-column>
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
        <!--<el-col :span="1"></el-col>-->
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
        <!--<el-col :span="1"></el-col>-->
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
        <el-col :span="1" style="margin-top: 18px; margin-left: 20px">关键词: </el-col>
        <el-col :span="4" style="margin-left: 10px">
          <el-input v-model="wb_sec_keyword" placeholder="请输入内容"></el-input>
        </el-col>
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
        <el-col :span="2" style="margin-top: 18px">结束时间: </el-col>
        <el-col :span="3">
          <el-date-picker
            v-model="wb_sec_enddate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择日期时间">
          </el-date-picker>
        </el-col>
        <el-col :span="2" style="margin-left: 20px">
          <el-button type="primary" plain v-on:click="showWbData">确定</el-button>
        </el-col>
        <el-col :span="2" style="margin-left: 150px">
          <el-button type="success" plain v-on:click="downloadWbExcel">导出</el-button>
        </el-col>
      </el-row>
    </div>
    <div class="data-table" v-show="wb_show">
      <el-table
        v-loading="wbloading"
        :data="wb_tableData"
        style="padding: 10px 10px 0 30px">
        <el-table-column type="index" :index="wbindexMethod" label="序号" width="60"></el-table-column>
        <el-table-column prop="name" label="账号名" width="120"></el-table-column>
        <el-table-column prop="type" label="账号归属" width="100"></el-table-column>
        <el-table-column prop="level" label="账号级别" width="100"></el-table-column>
        <el-table-column prop="channel" label="所属频道" width="100"></el-table-column>
        <el-table-column prop="date" label="发稿日期" width="100"></el-table-column>
        <el-table-column prop="time" label="发稿时间" width="100"></el-table-column>
        <!--<el-table-column prop="name" label="认证情况" width="100"></el-table-column>-->
        <el-table-column prop="latest" label="更新时间" width="100"></el-table-column>
        <el-table-column prop="isori" label="是否原创" width="100"></el-table-column>
        <el-table-column prop="url" :show-overflow-tooltip="true" label="URL" width="100"></el-table-column>
        <el-table-column prop="weibo_text" :show-overflow-tooltip="true" label="内容" width="100"></el-table-column>
        <el-table-column prop="read" label="阅读量" width="100"></el-table-column>
        <el-table-column prop="repost" label="转发量" width="100"></el-table-column>
        <el-table-column prop="comment" label="评论量" width="100"></el-table-column>
        <el-table-column prop="like" label="点赞量" width="100"></el-table-column>
        <el-table-column prop="live" label="直播播放量" width="100"></el-table-column>
        <el-table-column prop="video" label="点播播放量" width="100"></el-table-column>
        <el-table-column prop="video_url" :show-overflow-tooltip="true" label="视频URL" width="100"></el-table-column>
      </el-table>
      <div class="block" style="text-align: center">
        <el-pagination layout="total,prev, pager, next" v-on:current-change="changeWbPage" :total="wb_total" :current-page.sync = "wb_page" :page-size="20"
                       :next-text="next" :prev-text="prev"> </el-pagination>
      </div>
    </div>
    <div class="choose" v-show="bd_show">
      <el-row>
        <el-col :span="2" style="margin-top: 18px">账号归属: </el-col>
        <el-col :span="3">
          <el-select v-model="bd_sec_type"  placeholder="请选择" v-on:change="changeBdLevelSelect">
            <el-option
              v-for="item in bd_belong"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-col>
        <el-col :span="1"></el-col>
        <el-col :span="2" style="margin-top: 18px">所属频道: </el-col>
        <el-col :span="3">
          <el-select
            v-model="bd_sec_channel"
            collapse-tags
            placeholder="请选择">
            <el-option
              v-for="item in bd_channel"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-col>
        <el-col :span="1"></el-col>
        <el-col :span="1" style="margin-top: 18px; margin-left: 20px">品牌: </el-col>
        <el-col :span="3" style="margin-left: 10px">
          <el-select v-model="bd_sec_keyword"  placeholder="请选择">
            <el-option
              v-for="item in brand"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-col>
        <el-col :span="1"></el-col>
        <el-col :span="2" style="margin-top: 18px">呈现方式: </el-col>
        <el-col :span="2">
          <el-select
            v-model="bd_sec_show"
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
      </el-row>
      <el-row>
        <el-col :span="2" style="margin-top: 18px">开始时间: </el-col>
        <el-col :span="3">
          <el-date-picker
            v-model="bd_sec_startdate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择日期时间" >
          </el-date-picker>
        </el-col>
        <el-col :span="1"></el-col>
        <el-col :span="2" style="margin-top: 18px">结束时间: </el-col>
        <el-col :span="3">
          <el-date-picker
            v-model="bd_sec_enddate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择日期时间">
          </el-date-picker>
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
    <div class="data-table" v-show="bd_show_part">
      <el-table
        v-loading="bdloading"
        :data="bd_tableData"
        style="padding: 10px 10px 0 30px">
        <el-table-column type="index" :index="bdindexMethod" label="序号" width="60"></el-table-column>
        <el-table-column prop="name" label="账号名" width="120"></el-table-column>
        <el-table-column prop="type" label="账号归属" width="100"></el-table-column>
        <el-table-column prop="channel" label="所属频道" width="100"></el-table-column>
        <el-table-column prop="pub_date" label="发稿日期" width="100"></el-table-column>
        <el-table-column prop="time" label="发稿时间" width="100"></el-table-column>
        <el-table-column prop="brand" label="品牌" width="140"></el-table-column>
        <el-table-column prop="source" label="来源" width="100"></el-table-column>
        <el-table-column prop="url" :show-overflow-tooltip="true" label="URL"  width="100"></el-table-column>
        <el-table-column prop="title" :show-overflow-tooltip="true" label="标题" width="100"></el-table-column>
        <el-table-column prop="read" label="阅读量" width="100"></el-table-column>
        <el-table-column prop="share" label="转发量/分享量" width="110"></el-table-column>
        <el-table-column prop="hd" label="互动量" width="100"></el-table-column>
        <el-table-column prop="videoNum" label="微博微视频播放量" width="140"></el-table-column>
      </el-table>
      <div class="block" style="text-align: center">
        <el-pagination layout="total,prev, pager, next" v-on:current-change="changeBdPage" :total="bd_total" :current-page.sync = "bd_page" :page-size="20"
                       :next-text="next" :prev-text="prev"> </el-pagination>
      </div>
    </div>
    <div class="data-table" v-show="bd_show_accu">
      <el-table
        v-loading="bd_acculoading"
        :data="bd_accutableData"
        style="padding: 10px 10px 0 40px">
        <el-table-column type="index" :index="bdaccuindexMethod" label="序号" width="60"></el-table-column>
        <el-table-column prop="brand" :show-overflow-tooltip="true" label="稿件名称" width="200"></el-table-column>
        <el-table-column prop="date" label="发稿日期" width="100"></el-table-column>
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
        <el-pagination layout="total,prev, pager, next" v-on:current-change="changeBdPage" :total="bd_accutotal" :current-page.sync = "bd_accupage" :page-size="20"
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
  name: 'articles',
  data () {
    return {
      wx_page: 1,
      wb_page: 1,
      bd_page: 1,
      bd_accupage: 1,
      wxloading: false,
      wbloading: false,
      bdloading: false,
      bd_acculoading: false,
      isOnClick0: '#19c2c8',
      isOnClick1: '#cccccc',
      isOnClick2: '#cccccc',
      wx_show: true,
      wb_show: false,
      bd_show: false,
      bd_show_part: false,
      bd_show_accu: false,
      bd_sec_show: '',
      show_data: [{
        value: '1',
        label: '分条'
      }, {
        value: '2',
        label: '分天'
      }],
      source: [{
        value: '1',
        label: '微信'
      }, {
        value: '2',
        label: '微博'
      }],
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
      bd_belong: [{
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
      keyword: [{
        value: '1',
        label: '标题'
      }, {
        value: '2',
        label: '正文'
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
      bd_channel: [{
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
      brand: [{
        value: '0',
        label: '全部'
      }, {
        value: '12',
        label: '时政快讯'
      }, {
        value: '13',
        label: '时政新闻眼'
      }, {
        value: '14',
        label: '央视快讯'
      }, {
        value: '1',
        label: '央视快评'
      }, {
        value: '2',
        label: '国际锐评'
      }, {
        value: '3',
        label: '独家V观'
      }, {
        value: '4',
        label: '习声回响'
      }, {
        value: '5',
        label: '早啊新闻来了'
      }, {
        value: '6',
        label: '港媒转发央视快评'
      }, {
        value: '7',
        label: '境外媒体广泛转发'
      }, {
        value: '8',
        label: '境外媒体关注'
      }, {
        value: '9',
        label: '央视财经评论'
      }, {
        value: '10',
        label: '陆家嘴观察'
      }, {
        value: '11',
        label: '公司行业深观察'
      }],
      wx_sec_type: '',
      wx_sec_level: '',
      wx_sec_channel: '',
      wx_sec_startdate: '',
      wx_sec_enddate: '',
      wx_sec_source: '',
      wx_sec_keyword: '',
      wx_update: '',
      wb_sec_type: '',
      wb_sec_level: '',
      wb_sec_channel: '',
      wb_sec_keyword: '',
      wb_sec_startdate: '',
      wb_sec_enddate: '',
      bd_sec_type: '',
      bd_sec_channel: '',
      bd_sec_keyword: '',
      bd_sec_startdate: '',
      bd_sec_enddate: '',
      wx_total: 0,
      wb_total: 0,
      bd_total: 0,
      bd_accutotal: 0,
      next: '下一页',
      prev: '上一页',
      wx_tableData: [],
      wb_tableData: [],
      bd_tableData: [],
      bd_accutableData: []
    }
  },
  created () {
    let this1 = this
    axios.get('/cctv/article/weixin/update', {
    // axios.get('http://localhost:8080/article/weixin/update', {
    }).then(function (response) {
      this1.wx_update = response.data.date
    })
  },
  methods: {
    change (flag) {
      if (flag === 0) {
        this.isOnClick0 = '#19c2c8'
        this.isOnClick1 = '#cccccc'
        this.isOnClick2 = '#cccccc'
        this.wx_show = true
        this.wb_show = false
        this.bd_show = false
        this.bd_show_part = false
        this.bd_show_accu = false
      }
      if (flag === 1) {
        this.isOnClick0 = '#cccccc'
        this.isOnClick2 = '#cccccc'
        this.isOnClick1 = '#19c2c8'
        this.wx_show = false
        this.bd_show = false
        this.bd_show_part = false
        this.bd_show_accu = false
        this.wb_show = true
      }
      if (flag === 2) {
        this.isOnClick0 = '#cccccc'
        this.isOnClick1 = '#cccccc'
        this.isOnClick2 = '#19c2c8'
        this.wx_show = false
        this.bd_show = true
        this.bd_show_part = true
        this.bd_show_accu = false
        this.wb_show = false
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
    changeBdLevelSelect () {
      this.bd_sec_channel = ''
      let type = this.bd_sec_type
      if (type === '1') {
        this.bd_channel = [{
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
        this.bd_channel = [{
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
        this.bd_channel = [{
          value: '18',
          label: '央广'
        }]
      }
      if (type === '5') {
        this.bd_channel = [{
          value: '19',
          label: '国广'
        }]
      }
    },
    changeShow () {
      let show = this.bd_sec_show
      if (show === '1') {
        this.bd_show_part = true
        this.bd_show_accu = false
      }
      if (show === '2') {
        this.bd_show_accu = true
        this.bd_show_part = false
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
      let keyword = this.wx_sec_keyword
      this.wxloading = true
      axios.get('/cctv/article/weixin', {
        params: {
          type: type,
          level: level,
          channel: channel,
          startdate: startdate,
          enddate: enddate,
          keyword: keyword,
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
      let keyword = this.wb_sec_keyword
      this.wbloading = true
      axios.get('/cctv/article/weibo', {
        params: {
          type: type,
          level: level,
          channel: channel,
          startdate: startdate,
          enddate: enddate,
          keyword: keyword,
          page: 1,
          size: 20
        }
      }).then(function (response) {
        // console.log(response.data)
        _this.wb_tableData = response.data.tableData
        _this.wb_total = response.data.total
        _this.wbloading = false
      })
    },
    showBdData () {
      let _this = this
      let accu = this.bd_sec_show
      if (accu === '1') {
        this.bd_page = 1
        this.bdloading = true
      } else {
        this.bd_accupage = 1
        this.bd_acculoading = true
      }
      let type = this.bd_sec_type
      let channel = this.bd_sec_channel
      let startdate = this.bd_sec_startdate
      let enddate = this.bd_sec_enddate
      let keyword = this.bd_sec_keyword
      // axios.get('/cctv/brand', {
      axios.get('http://localhost:8080/brand', {
        params: {
          type: type,
          channel: channel,
          startdate: startdate,
          enddate: enddate,
          key: keyword,
          accu: accu,
          page: 1,
          size: 20
        }
      }).then(function (response) {
        if (accu === '1') {
          _this.bd_tableData = response.data.tableData
          _this.bd_total = response.data.total
          _this.bdloading = false
        } else {
          _this.bd_accutableData = response.data.tableData
          _this.bd_accutotal = response.data.total
          _this.bd_acculoading = false
        }
      })
    },
    wxindexMethod (index) {
      return index + 20 * (this.wx_page - 1) + 1
    },
    wbindexMethod (index) {
      return index + 20 * (this.wb_page - 1) + 1
    },
    bdindexMethod (index) {
      return index + 20 * (this.bd_page - 1) + 1
    },
    bdaccuindexMethod (index) {
      return index + 20 * (this.bd_accupage - 1) + 1
    },
    changeWxPage (val) {
      let _this = this
      this.wx_page = val
      let type = this.wx_sec_type
      let level = this.wx_sec_level
      let channel = this.wx_sec_channel
      let startdate = this.wx_sec_startdate
      let enddate = this.wx_sec_enddate
      let keyword = this.wx_sec_keyword
      this.wxloading = true
      axios.get('/cctv/article/weixin', {
        params: {
          type: type,
          level: level,
          channel: channel,
          startdate: startdate,
          enddate: enddate,
          keyword: keyword,
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
      let _this = this
      this.wb_page = val
      let type = this.wb_sec_type
      let level = this.wb_sec_level
      let channel = this.wb_sec_channel
      let startdate = this.wb_sec_startdate
      let enddate = this.wb_sec_enddate
      let keyword = this.wb_sec_keyword
      this.wbloading = true
      axios.get('/cctv/article/weibo', {
        params: {
          type: type,
          level: level,
          channel: channel,
          startdate: startdate,
          enddate: enddate,
          keyword: keyword,
          page: val,
          size: 20
        }
      }).then(function (response) {
        _this.wb_tableData = response.data.tableData
        _this.wb_total = response.data.total
        _this.wbloading = false
      })
    },
    changeBdPage (val) {
      // console.log('change', val)
      let _this = this
      let accu = this.bd_sec_show
      if (accu === '1') {
        this.bd_page = val
        this.bdloading = true
      } else {
        this.bd_accupage = val
        this.bd_acculoading = true
      }
      let type = this.bd_sec_type
      let channel = this.bd_sec_channel
      let startdate = this.bd_sec_startdate
      let enddate = this.bd_sec_enddate
      let keyword = this.bd_sec_keyword
      axios.get('/cctv/brand', {
      // axios.get('http://localhost:8080/brand', {
        params: {
          type: type,
          channel: channel,
          startdate: startdate,
          enddate: enddate,
          key: keyword,
          accu: accu,
          page: val,
          size: 20
        }
      }).then(function (response) {
        if (accu === '1') {
          _this.bd_tableData = response.data.tableData
          _this.bd_total = response.data.total
          _this.bdloading = false
        } else {
          _this.bd_accutableData = response.data.tableData
          _this.bd_accutotal = response.data.total
          _this.bd_acculoading = false
        }
      })
    },
    downloadWxExcel () {
      let type = this.wx_sec_type
      let level = this.wx_sec_level
      let channel = this.wx_sec_channel
      let startdate = this.wx_sec_startdate
      let enddate = this.wx_sec_enddate
      let keyword = this.wx_sec_keyword
      this.wxloading = true
      let _this = this
      axios({
        method: 'get',
        url: '/cctv/article/weixin/download',
        params: {
          type: type,
          level: level,
          channel: channel,
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
        link.setAttribute('download', '微信' + startdate + '-' + enddate + '文章数据.xls')
        document.body.appendChild(link)
        _this.wxloading = false
        link.click()
      })
    },
    downloadWbExcel () {
      let type = this.wb_sec_type
      let level = this.wb_sec_level
      let channel = this.wb_sec_channel
      let startdate = this.wb_sec_startdate
      let enddate = this.wb_sec_enddate
      let keyword = this.wb_sec_keyword
      this.wbloading = true
      let _this = this
      axios({
        method: 'get',
        url: '/cctv/article/weibo/download',
        // url: 'http://localhost:8080/article/weibo/download',
        params: {
          type: type,
          level: level,
          channel: channel,
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
        link.setAttribute('download', '微博' + startdate + '-' + enddate + '文章数据.xls')
        document.body.appendChild(link)
        _this.wbloading = false
        link.click()
      })
    },
    downloadBdExcel () {
      let type = this.bd_sec_type
      let channel = this.bd_sec_channel
      let startdate = this.bd_sec_startdate
      let enddate = this.bd_sec_enddate
      let keyword = this.bd_sec_keyword
      let accu = this.bd_sec_show
      if (accu === '1') {
        this.bdloading = true
      } else {
        this.bd_acculoading = true
      }
      let _this = this
      axios({
        method: 'get',
        url: '/cctv/brand/download',
        // url: 'http://localhost:8080/brand/download',
        params: {
          type: type,
          channel: channel,
          startdate: startdate,
          enddate: enddate,
          key: keyword,
          accu: accu
        },
        responseType: 'blob'
      }).then(function (response) {
        let url = window.URL.createObjectURL(new Blob([response.data]))
        let link = document.createElement('a')
        link.style.display = 'none'
        link.href = url
        link.setAttribute('download', '新媒体品牌' + startdate + '-' + enddate + '数据.xls')
        document.body.appendChild(link)
        if (accu === '1') {
          _this.bdloading = false
        } else {
          _this.bd_acculoading = false
        }
        link.click()
      })
    }
  }
}
</script>

<style lang='scss'>
  @import url("../../node_modules/element-ui/lib/theme-chalk/index.css");
  .article {
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
