<template>
  <v-container>
    <v-form v-on:submit.prevent="submitForm" id="join" lazy-validation>
      <v-row>
        <v-col cols="12"><h2>회원가입 🥕</h2></v-col>
      </v-row>
      <v-row>
        <v-col cols="12">
          <v-select
              v-model="state.defaultSelected"
              label="지역"
              :items="state.areaList"
              item-title="areaName"
              item-value="areaCode">
          </v-select>
        </v-col>
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
        <v-col cols="12">
          <v-text-field
              v-model="name"
              type="text"
              placeholder="이름을 입력해주세요"/>
        </v-col>
        <v-col cols="12">
          <v-text-field
              v-model="phone"
              type="text"
              :rules="phoneRule"
              placeholder="핸드폰번호를 입력해주세요"/>
        </v-col>

        <v-col cols="12">
          <v-text-field
              v-model="nickname"
              type="text"
              placeholder="닉네임을 입력해주세요"/>
        </v-col>
      </v-row>
      <v-row>
        <v-col cols="12">
          <v-btn type="submit" class="text-white" block color="#ff931e">
            가입하기
          </v-btn>
        </v-col>
      </v-row>
    </v-form>
  </v-container>
</template>

<script>
import axios from "axios";
import {useToast} from "vue-toastification";
import {onMounted, reactive} from "vue";

export default {
  setup() {
    const toast = useToast();
    const state = reactive({
      defaultSelected: {
        areaCode: "1",
        areaName: "판교",
      },
      areaList: [
        {
          areaCode: "",
          areaName: "",
        },
      ],
    });
    onMounted(() => {
      getAreaList();
    });
    const getAreaList = () => {
      const url = "http://localhost:8081/api/v1/area/list";
      axios
          .get(url)
          .then(function (response) {
            state.areaList = response.data
          })
          .catch(function (error) {
            console.log(error)
            alert("서버 에러입니다. \n잠시 후 다시 시도해주세요.");
          });
    };
    return {
      toast,
      state
    };
  },
  data() {
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
      name: "",
      phone: "",
      phoneRule: [
        (v) => {
          const replaceV = v.replace(/(\s*)/g, "");
          const pattern = /^(01[016789])-[^0][0-9]{3,4}-[0-9]{4}$/;
          return (
              pattern.test(replaceV) || "01x-xxxx-xxxx 형식으로 입력해주세요."
          );
        },
      ],
      nickname: "",
    };
  },
  methods: {
    submitForm: function () {
      if (this.validationCheck() === false) {
        return false;
      }
      const url = "http://localhost:8081/api/v1/member";
      const memberJoinForm = {
        email: this.email,
        password: this.password,
        name: this.name,
        phone: this.phone,
        nickname: this.nickname,
        areaId: this.state.defaultSelected
      };
      axios
          .post(url, memberJoinForm)
          .then(function (response) {
            console.log(response);
            // router.push("/join-ok");
          })
          .catch(function (error) {
            console.log(error);
          });
    },
    validationCheck: function () {
      if (!this.email) {
        this.toast.error("이메일을 입력해주세요.");
        return false;
      } else if (!this.password) {
        this.toast.error("비밀번호를 입력해주세요.");
        return false;
      } else if (!this.name) {
        this.toast.error("이름을 입력해주세요.");
        return false;
      } else if (!this.phone) {
        this.toast.error("핸드폰 번호를 입력해주세요.");
        return false;
      } else if (!this.nickname) {
        this.toast.error("닉네임을 입력해주세요.");
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
</style>