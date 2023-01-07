<template>
  <div id="join-view">
    <div class="box-join">
      <div class="box-child">
        <h2>회원가입</h2>
        <br />
        <form v-on:submit.prevent="submitForm" id="join">
          <h4><label for="email">이메일(ID)</label></h4>
          <input
            type="email"
            v-model="email"
            id="email"
            placeholder="아이디를 입력해주세요" />
          <h4><label for="password">비밀번호(PW)</label></h4>
          <input
            type="password"
            v-model="password"
            id="password"
            placeholder="패스워드를 입력해주세요" />
          <h4><label for="name">이름</label></h4>
          <input
            type="text"
            v-model="name"
            id="name"
            placeholder="이름을 입력해주세요" />
          <h4><label for="phone">핸드폰번호</label></h4>
          <input
            type="text"
            id="phone"
            v-model="phone"
            placeholder="핸드폰번호를 입력해주세요" />
          <h4><label for="nickname">닉네임</label></h4>
          <input
            type="text"
            id="nickname"
            v-model="nickname"
            placeholder="닉네임을 입력해주세요" />
          <br /><br />
        </form>
      </div>
    </div>

    <div class="box-under">
      <div class="box-under-child">
        <v-btn
          type="submit"
          form="join"
          class="text-white"
          block
          color="#ff931e">
          가입하기
        </v-btn>
      </div>
    </div>
  </div>
</template>

<script>
  import axios from "axios";

  export default {
    data: function () {
      return {
        email: "",
        password: "",
        name: "",
        phone: "",
        nickname: "",
      };
    },
    methods: {
      submitForm: function () {
        console.log(this.email);
        const url = "http://localhost:8081/api/v1/member";
        const memberJoinForm = {
          email: this.email,
          password: this.password,
          name: this.name,
          phone: this.phone,
          nickname: this.nickname,
        };
        axios
          .post(url, memberJoinForm)
          .then(function (response) {
            console.log(response);
          })
          .catch(function (error) {
            console.log(error);
          });
      },
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