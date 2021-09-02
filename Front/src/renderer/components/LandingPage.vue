<template>
<div class="main">
  <div class="txt">
    运行日志：
    <div :class="{lin :item.number == 2}" v-for="(item,i) in message1">
      {{item.date}}{{item.message}}
    </div>
  </div>
  <div class="buttonbox">
    <img :class="{hidden :a == 0,bit :a == 1}" @click="begin" src="src/images/start1.png">
    <img :class="{hidden :a == 1,bit :a == 0}" @click="clo" src="src/images/start2.png">
    <img :class="{hidden :bt == 0 || a == 1,bot :bt == 1 && a == 0}" @click="start" src="/src/images/begin.png">
    <img :class="{hidden :bt == 1 || a == 1,bot :bt == 0 && a == 0}" @click="over" src="/src/images/stop.png">
    <img :class="{hidden :a == 1 || kt == 0,btt :a == 0 && kt == 1}" @mousedown="change1" src="/src/images/next1.png">
    <img :class="{hidden :a == 1 || kt == 1,btt :a == 0 && kt == 0}" @mouseup="change2" src="/src/images/next2.png">
  </div>
</div>
</template>

<script>
import axios from 'axios'
export default {
  data: function() {
    return {
      a: 1,
      bt: 1,
      kt: 1,
      timer: "",
      value: 0,
      i: 0,
      message1: [{
        number: 0,
        date: '',
        message: ''
      }]
    }
  },
  methods: {
    change1() {
      this.kt = 0
    },
    change2() {
      var start = (new Date()).getTime();
      while((new Date()).getTime() - start < 150) {
        continue;
      }
      this.kt = 1
      axios
          .post("http://localhost:8080/begin/next")
          .then((res) => {
                this.message1[this.i] = res.data
                this.i = this.i + 1
                this.$forceUpdate()
                console.log(this.message1)
              }
          )
    },
    begin() {
      this.a = 0;
      axios
          .post("http://localhost:8080/begin/login")
          .then((res) => {
              this.message1[this.i] = res.data
              this.i = this.i + 1
              this.$forceUpdate()
              console.log(this.message1)
            }
          )
    },
    start(){
      this.timer = setInterval(this.valChange, 10000); // 注意: 第一个参数为方法名的时候不要加括号;
      this.bt = 0;
    },
    valChange() {
      axios
          .post("http://localhost:8080/begin/run")
          .then((res) => {
                this.message1[this.i] = res.data
                this.i = this.i + 1
                this.$forceUpdate()
                console.log(res.data)
                console.log(this.message1)
              }
          )
    },
    clo() {
      this.a = 1;
      this.bt = 1;
      clearInterval(this.timer);
      axios
          .post("http://localhost:8080/begin/over")
          .then((res) => {
                this.message1[this.i] = res.data
                this.i = this.i + 1
                this.$forceUpdate()
                console.log(res.data)
                console.log(this.message1)
              }
          )
    },
    over() {
      this.bt = 1;
      clearInterval(this.timer);
      axios
          .post("http://localhost:8080/begin/stop")
          .then((res) => {
                this.message1[this.i] = res.data
                this.i = this.i + 1
                this.$forceUpdate()
                console.log(res.data)
                console.log(this.message1)
              }
          )
    }
  },
  mounted() {},
  beforeDestroy() {
    clearInterval(this.timer);
  }
}


</script>

<style>
* {
  box-sizing: border-box;
  margin: 0;
  padding: 0;
}
.main {
  position: absolute;
  top: 32px;
  height: 229px;
  width: 100%;
  display: flex;
  align-items: center;
}
.txt {
  background-color: white;
  position: absolute;
  border: 1.5px solid #51B9FF;
  border-radius: 8px;
  left: 10px;
  width: 250px;
  height: 200px;
  box-shadow: 0 0 9px grey;
  font-family: 楷体;
  z-index: 1;
  overflow:auto
}
.button {
  background-color: white;
  width: 52px;
  height: 32px;
  z-index: 2;
  border: 1px solid #fed6e3;
  border-radius: 5px;
  text-align: center;
  line-height: 32px;
  cursor: pointer;
  user-select: none;
}
.buttonbox {
  position: absolute;
  left: 280px;
  top: 20px;
  width: 200px;
  display: flex;
  justify-content: space-between;
  flex-wrap: wrap
}
.button:hover {
  background-color: #f0f0f0;
}

.lin {
  color: red;
}
.bot {
  width: 48px;
  height: 48px;
  cursor: pointer;
  position: absolute;
  left: 70px;
  z-index: 10;
}
.btt {
  width: 48px;
  height: 48px;
  cursor: pointer;
  position: absolute;
  left: 140px;
  z-index: 10;
}
.bit {
  width: 48px;
  height: 48px;
  cursor: pointer;
  z-index: 10;
}

.hidden {
  z-index: 0;
  width: 48px;
  height: 48px;
  position: absolute;
  opacity: 0;
}
</style>
