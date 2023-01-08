<template>
  <v-container fluid>
    <v-row class="mb-8">
      <v-col cols="12"><h2>🥳회원가입 완료 🎉</h2></v-col>
    </v-row>
    <v-layout class="mb-16">
      <v-col cols="12" class="mb-16">
        <h3>
          입력하신 {{ email }} 로 <br />
          인증 메일을 전송했습니다.<br /><br />
          메일을 인증하고 <br />
          Karrot 마켓을 시작해보세요.
        </h3>
      </v-col>
    </v-layout>
    <v-row>
      <v-col cols="12" class="mt-16" style="color: red">구현 예정</v-col>
    </v-row>
    <v-row>
      <v-col cols="12" class="mt-16">
        <v-btn href="/login" class="text-white" color="#ff931e" block>
          로그인 하러가기
        </v-btn>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
  export default {
    data() {
      return {
        email: "이메일",
      };
    },
  };
</script>

<style scoped>
  .v-btn {
    font-family: "EliceDigitalBaeum-Bd", serif;
    font-size: 18px;
    width: 300px;
    height: 40px;
  }
</style>
