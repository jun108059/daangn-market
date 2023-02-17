<template>
  <div id="welcome" class="box-welcome">
    <div class="box-child">
      <v-img max-height="150" p src="~@/assets/carrot.png"/>
      <br/>
      <h2>당신 근처의 daangn 마켓</h2>
      <h3>당근이세요?</h3>
    </div>
  </div>
  <div class="box-under">
    <div class="box-under-child">
      <v-btn href="/join" class="text-white" color="#ff931e" block>
        시작하기
      </v-btn>
      <br/><br/>
      이미 계정이 있나요?
      <router-link to="/login" class="login-link">로그인</router-link>
    </div>
  </div>
</template>

<script></script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
  margin: 40px 0 0;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  display: inline-block;
  margin: 0 10px;
}

.box-welcome {
  position: relative;
  width: 100%;
  height: 500px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.box-child {
  position: absolute;
  text-align: center;
}

.box-under {
  width: 100%;
  height: 150px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.box-under-child {
  text-align: center;
}

.v-btn {
  font-family: "EliceDigitalBaeum-Bd", serif;
  font-size: 18px;
  width: 300px;
  height: 40px;
}

.login-link {
  color: #ff931e;
}

h4 {
  text-align: left;
  margin: 10px;
}

input {
  width: 280px;
  height: 20px;
}
</style>
