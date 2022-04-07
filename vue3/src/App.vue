<template style="height: 100%">
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
