<template>
  <v-container>
    <v-form v-on:submit.prevent="submitForm" id="join" lazy-validation>
      <v-row class="mb-8">
        <v-col cols="12"><h2>๋ก๊ทธ์ธ ๐ฅ</h2></v-col>
      </v-row>
      <v-col cols="12">
        <v-text-field
            v-model="email"
            type="email"
            :rules="emailRule"
            placeholder="์ด๋ฉ์ผ์ ์๋ ฅํด์ฃผ์ธ์"
            required/>
      </v-col>
      <v-col cols="12">
        <v-text-field
            v-model="password"
            type="password"
            :rules="passwordRule"
            placeholder="ํจ์ค์๋๋ฅผ ์๋ ฅํด์ฃผ์ธ์"
            required/>
      </v-col>
      <v-row class="mt-16">
        <v-col cols="12">
          <v-btn type="submit" class="v-btn text-white" block color="#ff931e">
            ๋ก๊ทธ์ธ
          </v-btn>
        </v-col>
        <v-col cols="12">
          ๋น๋ฐ๋ฒํธ๋ฅผ ์์ด๋ฒ๋ฆฌ์จ๋์?<br/>
          <a @click.prevent="findPassword" href="#" class="find-password">
            ๋น๋ฐ๋ฒํธ ์ฐพ๊ธฐ
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
        (v) => !!v || "์ด๋ฉ์ผ์ ํ์ ์๋ ฅ์ฌํญ์๋๋ค.",
        (v) => {
          const replaceV = v.replace(/(\s*)/g, "");
          const pattern =
              /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/;
          return pattern.test(replaceV) || "์ด๋ฉ์ผ ํ์์ผ๋ก ์๋ ฅํด์ฃผ์ธ์.";
        },
      ],
      password: "",
      passwordRule: [
        (v) => !!v || "๋น๋ฐ๋ฒํธ๋ ํ์ ์๋ ฅ์ฌํญ์๋๋ค.",
        (v) => {
          const replaceV = v.replace(/(\s*)/g, "");
          return replaceV.length >= 4 || "4์๋ฆฌ ์ด์ ์๋ ฅํด์ฃผ์ธ์.";
        },
      ],
    };
  },
  methods: {
    findPassword: function () {
      this.toast.success("๊ตฌํ ์์ ์๋๋ค :D");
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
            alert("์คํจํ์ต๋๋ค. \n๋ค์ ์๋ํด์ฃผ์ธ์.");
          });
    },
    validationCheck: function () {
      if (!this.email) {
        this.toast.error("์ด๋ฉ์ผ์ ์๋ ฅํด์ฃผ์ธ์.");
        return false;
      } else if (!this.password) {
        this.toast.error("๋น๋ฐ๋ฒํธ๋ฅผ ์๋ ฅํด์ฃผ์ธ์.");
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