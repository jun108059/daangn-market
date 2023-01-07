<template>
  <div class="box-join">
    <div class="box-child">
      <h3>로그인</h3>
      <form id="login" action="" method="post">
        <h4><label for="email">이메일(ID)</label></h4>
        <input type="email" id="email" placeholder="아이디를 입력해주세요" />
        <h4><label for="password">비밀번호(PW)</label></h4>
        <input
          type="password"
          id="password"
          placeholder="패스워드를 입력해주세요"
        />
      </form>
    </div>
  </div>

  <div class="box-under">
    <div class="box-under-child">
      <v-btn class="text-white" block href="/join" color="#ff931e"
        >로그인</v-btn
      >
      <input
        type="submit"
        form="login"
        class="btn-main"
        value="로그인"
      /><br /><br />
      비밀번호를 잊어버리셨나요? <br />
      <a href="#" id="toast" class="find-password">비밀번호 찾기</a>
    </div>
  </div>
</template>

<script>
export default {
  data: function () {
    return {
      email: "",
      password: "",
    };
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.box-join {
  position: relative;
  width: 100%;
  height: 475px;
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

.find-password {
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
