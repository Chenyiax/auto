<template>
  <div id="app">
    <div class="topbar">
      <div class="logo">
        <img id="big" src="src/images/auto.png">
        <span>学习通自动化学习应用</span>
      </div>
      <div class="min_max_close" id="min" @click="min">
        <div class="img1">
          <img src="src/images/small.png" >
        </div>
        <div class="img2" id="close" @click="close">
          <img src="src/images/close.png" >
        </div>
      </div>
    </div>
    <div class="line"></div>
    <router-view></router-view>
  </div>
</template>

<script>
  export default {
    name: 'eleproject01',
    methods: {
      min () {
        let ipcRenderer = require('electron').ipcRenderer
        let min = document.getElementById('min')
        if (min) {
          min.addEventListener('click', () => {
            ipcRenderer.send('window-min')
          })
        }
      },
      close () {
        let ipcRenderer = require('electron').ipcRenderer
        let close = document.getElementById('close')
        if (close) {
          close.addEventListener('click', () => {
            ipcRenderer.send('window-close')
          })
        }
      }
    }
  }
</script>

<style>
.line {
  position: absolute;
  top: 32px;
  width: 100%;
  height: 1px;
  background-color: #b4b3b3;
  opacity: 50%;
}
.logo {
  display: flex;
  align-items: center;
  font-family: STSong;
  color: #51B9FF;
}
#big {
  width: 32px;
  height: 32px;
}
#app {
  background-image: linear-gradient(to top left, #a8edea 0%, #fed6e3 100%);
  opacity: 0.8;
  width: 100%;
  height: 263px;
  overflow: hidden;
}
body::-webkit-scrollbar {
  display: none;
}
.topbar {
  position: absolute;
  display: flex;
  align-items: center;
  -webkit-app-region: drag;
  -webkit-user-select: none;
  width: 100%;
}

.min_max_close {
  position: absolute;
  display: flex;
  right: 0px;
  justify-content: space-between;
  -webkit-app-region: no-drag;
}

img{
  width: 18px;
  height: 18px;
}

.img1 {
  width: 50px;
  height: 32px;
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 100;
}

.img1:hover {
  background-color: #f0f0f0;
}

.img2 {
  width: 50px;
  height: 32px;
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 100;
}

.img2:hover {
  background-color: #E46855;
}
</style>
