<template>
  <v-app>
    <v-main>
      <Home />
    </v-main>
  </v-app>
</template>

<script>
import Home from "@/components/Home.vue";

export default {
  name: "App",
  components: {
    Home,
  },
};
</script>

<style>
#app {
  font-family: EliceDigitalBaeum-Bd, Avenir, Helvetica, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}

@font-face {
  font-family: "EliceDigitalBaeum-Bd";
  src: url("~@/assets/fonts/EliceDigitalBaeum_Bold.ttf") format("truetype");
  font-weight: normal;
  font-style: normal;
}

body {
  font-family: "EliceDigitalBaeum-Bd", serif;
}
</style>