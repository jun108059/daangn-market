<template>
  <v-container>
    <v-form v-on:submit.prevent="submitForm" id="join" lazy-validation>
      <v-row class="mb-8">
        <v-col cols="12"><h2>로그인 🥕</h2></v-col>
      </v-row>
      <v-col cols="12">
        <v-text-field
            v-model="email"
            type="email"
            :rules="emailRule"
            placeholder="이메일을 입력해주세요"
            required/>
      </v-col>
      <v-col cols="12">
        <v-text-field
            v-model="password"
            type="password"
            :rules="passwordRule"
            placeholder="패스워드를 입력해주세요"
            required/>
      </v-col>
      <v-row class="mt-16">
        <v-col cols="12">
          <v-btn type="submit" class="v-btn text-white" block color="#ff931e">
            로그인
          </v-btn>
        </v-col>
        <v-col cols="12">
          비밀번호를 잊어버리셨나요?<br/>
          <a @click.prevent="findPassword" href="#" class="find-password">
            비밀번호 찾기
          </a>
        </v-col>
        <v-col cols="12"></v-col>
      </v-row>
    </v-form>
  </v-container>
</template>

<script>
import {useToast} from "vue-toastification";
import $axiosInst from "@/common/AxiosInst"
import router from "@/router";

export default {
  setup() {
    const toast = useToast();
    return {toast};
  },
  data: function () {
    return {
      email: "",
      emailRule: [
        (v) => !!v || "이메일은 필수 입력사항입니다.",
        (v) => {
          const replaceV = v.replace(/(\s*)/g, "");
          const pattern =
              /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/;
          return pattern.test(replaceV) || "이메일 형식으로 입력해주세요.";
        },
      ],
      password: "",
      passwordRule: [
        (v) => !!v || "비밀번호는 필수 입력사항입니다.",
        (v) => {
          const replaceV = v.replace(/(\s*)/g, "");
          return replaceV.length >= 4 || "4자리 이상 입력해주세요.";
        },
      ],
    };
  },
  methods: {
    findPassword: function () {
      this.toast.success("구현 예정입니다 :D");
    },
    submitForm: function () {
      const that = this;
      if (this.validationCheck() === false) {
        return false;
      }
      const url = "/api/v1/login";
      const loginForm = {
        email: this.email,
        password: this.password
      };
      $axiosInst
          .post(url, loginForm)
          .then(function (response) {
            console.log(response);
            that.$store.dispatch("login", response.data);
            router.push("/product/list");
          })
          .catch(function (error) {
            console.log(error);
            alert("실패했습니다. \n다시 시도해주세요.");
          });
    },
    validationCheck: function () {
      if (!this.email) {
        this.toast.error("이메일을 입력해주세요.");
        return false;
      } else if (!this.password) {
        this.toast.error("비밀번호를 입력해주세요.");
        return false;
      }
      return true;
    },
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.v-btn {
  font-family: "EliceDigitalBaeum-Bd", serif;
  font-size: 18px;
  width: 300px;
  height: 40px;
}

.find-password {
  color: #ff931e;
}
</style>