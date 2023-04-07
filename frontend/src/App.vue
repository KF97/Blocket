<template>
  <div id="nav" style="background-color: #F9F7F7; height: auto;">
    <Header></Header>
  <router-view id="view"/>
  </div>
</template>

<script>
import Header from "@/layout/Header.vue";
import * as pService from '@/utils/pService.js'
export default {
  components: {
    Header,
  },
  setup() {
    pService.UserCheck();
  },
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
}
#nav {
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}
</style>
