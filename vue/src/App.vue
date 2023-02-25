<template>
  <v-app id="app">
    <router-view/>
  </v-app>
</template>

<style>
#app {
  font-family: EliceDigitalBaeum-Bd, Avenir, Helvetica, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
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

.my-footer {
  background-color: lightgray;
  position: absolute;
  bottom: 12px;
  right: 20px;
  width: 100%;
  padding: 20px 30px 20px 30px;
  border-top: solid 3px #2c3e50;
  border-bottom: solid 3px #2c3e50;
  left: 12px;
}
</style>
