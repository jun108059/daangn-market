<template>
  <v-toolbar color="orange" elevation="2">
    <v-icon v-if="categoryCode !== ''" icon="mdi-arrow-left" style="margin-left: 10px; margin-right: 40px"></v-icon>
    <v-toolbar-title v-if="categoryCode === ''" class="home-toolbar-title">
      {{ state?.userName }}님 동네
    </v-toolbar-title>
    <v-toolbar-title v-else>
      {{ state.categoryName }}
    </v-toolbar-title>
    <!--TODO("검색 페이지 연결")-->
    <v-icon icon="mdi-magnify"></v-icon>&nbsp; &nbsp;
    <v-btn icon id="no-background-hover" to="/category-list">
      <v-icon icon="mdi-format-list-bulleted"></v-icon>&nbsp; &nbsp;
    </v-btn>
  </v-toolbar>
  <v-container fluid>
    <v-row
        class="mt-2 mb-2 product-row"
        v-for="(item, index) in state.list"
        :key="index"
        @click="goDetailPage(item.id)">
      <div style="float: left; width: 33%">
        <img :src="item.imgUrl" alt="이미지" width="125" height="100"/>
      </div>
      <div style="text-align: left; margin-left: 10px; width: 60%">
        <div class="title">{{ item?.title }}</div>
        <div class="location">{{ item?.areaName }} • {{ $time.elapsedTime(item?.createdDateTime) }}</div>
        <div class="price">
          <div v-if="item?.status === 'RESERVED'" style="display:inline;">
            <img class="state-img" :src="state.reservedImg" alt="예약중" width="55"/></div>
          <div v-if="item?.status === 'COMPLETED'" style="display:inline;">
            <img class="state-img" :src="state.completedImg" alt="거래완료" width="55"/></div>
          {{ $money.currency(item?.price) }} 원
        </div>
      </div>
      <div style="position: absolute; bottom: 10px; right: 5px; width: 30%; font-size: 14px">
        <img class="chat-img" :src="state.chatImg" alt="채팅" width="15"/>
        {{ item?.chatCount }} &nbsp;
        <img class="like-img" :src="state.heartImg" alt="좋아요" width="15"/>&nbsp;
        {{ item?.likeCount }}
      </div>
    </v-row>
    <router-link to="/product/register" class="addButton">
      <v-img src="@/assets/img/add-button.png"></v-img>
    </router-link>
  </v-container>
  <v-row class="my-footer">
    <div style="float: left; width: 48%" class="homeButton">
      <v-img class="mx-auto" width="40" src="@/assets/img/home.png"></v-img>
    </div>
    <div style="width: 48%">
      <router-link to="/my-page">
        <v-img class="mx-auto" width="40" src="@/assets/img/user-blank.png"></v-img>
      </router-link>
    </div>
  </v-row>
</template>

<script>
import {onMounted, reactive} from "vue";
import $axiosInst from "@/common/AxiosInst"
import $store from "@/store/store"
import router from "@/router";
import chatImg from "@/assets/img/chat.png";
import heartImg from "@/assets/img/heart.png";
import addButton from "@/assets/img/add-button.png";
import reservedImg from "@/assets/img/reserved.png";
import completedImg from "@/assets/img/completed.png";
import {useRoute} from "vue-router";

export default {
  setup() {
    const route = useRoute();
    const categoryCode = route.params.category_code;
    const state = reactive({
      list: [
        {
          id: "",
          areaName: "",
          title: "",
          price: "",
          likeCount: "",
          chatCount: "",
          imgUrl: "",
          createdDateTime: "",
          status: "",
        },
      ],
      chatImg: chatImg,
      heartImg: heartImg,
      addButton: addButton,
      reservedImg: reservedImg,
      completedImg: completedImg,
      userName: "",
      categoryName: "",
    });
    onMounted(() => {
      getProductList();
      setUserName();
      setCategoryName();
    });
    const getProductList = () => {
      const url = "/api/v1/product/list";
      let params;
      if (categoryCode !== "") {
        params = {category_code: categoryCode}
      }
      $axiosInst
          .get(url, {params})
          .then(function (response) {
            state.list = response.data.content;
          })
          .catch(function (error) {
            console.log(error);
            alert("서버 에러입니다. \n잠시 후 다시 시도해주세요.");
          });
    };
    const setUserName = () => {
      state.userName = $store.getters.getUserName
    };
    const goDetailPage = (id) => {
      router.push({
        name: "ProductDetailView",
        params: {productId: id}
      })
    };
    const setCategoryName = () => {
      const url = "/api/v1/category/name";
      let params;
      if (categoryCode !== "") {
        params = {category_code: categoryCode}
        $axiosInst
            .get(url, {params})
            .then(function (response) {
              state.categoryName = response.data;
            })
            .catch(function (error) {
              console.log(error);
              alert("서버 에러입니다. \n잠시 후 다시 시도해주세요.");
            });
      }
    };
    return {
      categoryCode,
      state,
      goDetailPage,
      setCategoryName,
    };
  }
};
</script>

<style scoped>
.product-row {
  position: relative;
  border-bottom: solid 1px #ff931e;
  padding: 10px 0;
}

.addButton {
  position: absolute;
  bottom: 100px;
  right: 0;
  width: 30%;
  height: 60px;
}

.homeButton {
  margin-left: 1px;
}

.location {
  font-size: 14px;
  color: gray;
}

.state-img {
  vertical-align: middle;
  margin-top: -2px;
  margin-left: -10px;
  margin-right: -5px;
}

.chat-img {
  vertical-align: middle;
}

.like-img {
  vertical-align: middle;
  margin-right: -3px;
}

#no-background-hover::before {
  background-color: transparent !important;
}

.home-toolbar-title {
  font-size: 19px;
  white-space: nowrap;
  text-align: left;
}
</style>