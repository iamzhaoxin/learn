<template>
  <router-view></router-view>
</template>

<script>
import {localGet, localRemove} from "@/utils";


export default {
  name: 'App',
  setup() {
    window.onbeforeunload = function (){
      if(localGet('autoLogin')===null){
        localRemove('token')
      }
    }
  }
}
</script>

<style scoped>

</style>

<style>
.GlobalHeader {
  height: 5%;
  padding-left: 0;
  padding-right: 0;
  /*background-image: url('../../assets/img.png');*/
  background-color: lightskyblue;
}
</style>
