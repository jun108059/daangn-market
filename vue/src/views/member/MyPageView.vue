<template>
  <v-toolbar color="orange" elevation="2" opacity="100">
    <v-toolbar-title class="text-left">나의 당근</v-toolbar-title>
  </v-toolbar>
  <v-container fluid>
    <v-row class="mt-2 mb-2 profile-info">
      <div style="float: left; width: 25%">
        <img :src="state.memberInfo.imagePath" alt="판매자-프로필-사진" width="60" height="60"/>
      </div>
      <div class="member-name">
        <div>{{ state.memberInfo.nickname }}</div>
      </div>
      <div class="profile-setting-btn">
        <v-img class="mx-auto" width="80" :src="state.profileSettingBtnImg"></v-img>
      </div>
    </v-row>
  </v-container>
  <div class="orange-line"></div>
  <v-container class="ml-4 mt-4" fluid>
    <v-row class="mb-4" @click="goLikesList()">
      <div class="my-page-contents">
        <v-icon icon="mdi-heart"></v-icon>
        관심목록
      </div>
    </v-row>
    <v-row class="mb-4" @click="goMyProductList()">
      <div class="my-page-contents">
        <v-icon icon="mdi-clipboard-text"></v-icon>
        판매내역
      </div>
    </v-row>
    <v-row class="mb-4">
      <div class="my-page-contents">
        <v-icon icon="mdi-wechat"></v-icon>
        채팅목록
      </div>
    </v-row>
  </v-container>
  <div class="orange-line"></div>
  <v-row class="log-out">
    <div style="float: left;">
      로그아웃
      <v-icon icon="mdi-logout"></v-icon>
    </div>
  </v-row>
  <v-row class="my-footer">
    <div style="float: left; width: 48%" class="homeButton">
      <router-link to="/product/list">
        <v-img class="mx-auto" width="40" :src="state.homeBtnImg"></v-img>
      </router-link>
    </div>
    <div style="width: 48%">
      <v-img class="mx-auto" width="40" :src="state.userBtnImg"></v-img>
    </div>
  </v-row>
</template>

<script>
import {onMounted, reactive} from "vue";
import profileSettingBtnImg from "@/assets/img/profile-setting.png";
import homeBtnImg from "@/assets/img/home-blank.png";
import userBtnImg from "@/assets/img/user.png";
import $axiosInst from "@/common/AxiosInst";
import router from "@/router";

export default {
  setup() {
    const state = reactive({
      memberInfo:
          {
            nickname: "",
            imagePath: "",
          },
      profileSettingBtnImg: profileSettingBtnImg,
      homeBtnImg: homeBtnImg,
      userBtnImg: userBtnImg,
    });
    onMounted(() => {
      getMemberInfo();
    });
    const getMemberInfo = () => {
      const url = "/api/v1/member";
      $axiosInst
          .get(url)
          .then(function (response) {
            state.memberInfo = response.data;
          })
          .catch(function (error) {
            console.log(error);
            alert("서버 에러입니다. \n잠시 후 다시 시도해주세요.");
          });
    };
    const goLikesList = () => {
      router.push({
        name: "LikesProductListView",
      })
    };
    const goMyProductList = () => {
      router.push({
        name: "MyProductListView",
      })
    };
    return {
      state,
      goLikesList,
      goMyProductList,
    };
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.profile-info {
  display: inline;
  vertical-align: middle;
  position: relative;
  border-bottom: solid 1px #ff931e;
  padding: 10px 0;
}

.orange-line {
  border-bottom: solid 1px #ff931e;
}

.member-name {
  text-align: left;
  vertical-align: middle;
  margin-top: 15px;
  width: 40%;
  font-size: 17px;
  float: left;
}

.profile-setting-btn {
  width: 120px;
  vertical-align: middle;
  float: right;
  margin-top: 15px;
  margin-left: 20px;
  margin-right: -20px;
}

.log-out {
  display: inline;
  margin-left: 30px;
  margin-top: 20px;
}

.my-page-contents {
  font-size: 17px;
  clear: both;
  display: block;
}
</style>