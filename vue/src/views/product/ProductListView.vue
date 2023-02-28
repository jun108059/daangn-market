<template>
  <v-toolbar color="orange" elevation="2">
    <v-toolbar-title>{{ state?.userName }}님 동네</v-toolbar-title>
    <v-spacer></v-spacer>
    <!--TODO("검색 페이지 연결")-->
    <!--TODO("카테고리 조회 페이지 연결")-->
    <v-icon icon="mdi-magnify"></v-icon>&nbsp; &nbsp;
    <v-icon icon="mdi-format-list-bulleted"></v-icon>&nbsp; &nbsp;
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

export default {
  setup() {
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
      userName: ""
    });
    onMounted(() => {
      getProductList();
      setUserName();
    });
    const getProductList = () => {
      const url = "/api/v1/product/list";
      $axiosInst
          .get(url)
          .then(function (response) {
            console.log(response);
            state.list = response.data.content;
            console.log(state.list);
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
    return {
      state,
      goDetailPage,
    };
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
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
  /*border-right: solid 3px #2c3e50;*/
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
</style>