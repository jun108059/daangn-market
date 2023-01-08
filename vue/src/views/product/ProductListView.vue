<template>
  <v-toolbar color="orange" elevation="2">
    <v-toolbar-title>개발자님 동네</v-toolbar-title>
    <v-spacer></v-spacer>
    <v-icon icon="mdi-magnify"></v-icon>&nbsp; &nbsp;
    <v-icon icon="mdi-format-list-bulleted"></v-icon>&nbsp; &nbsp;
  </v-toolbar>
  <v-container fluid>
    <v-row
      class="mt-2 mb-2 product-row"
      v-for="(item, index) in state.list"
      :key="index">
      <div style="float: left; width: 33%">
        <img :src="item.imgUrl" alt="이미지" width="125" />
      </div>
      <div style="text-align: left; margin-left: 10px; width: 60%">
        <div class="title">{{ item?.title }}</div>
        <div class="location">{{ item?.areaName }}</div>
        <div class="price">{{ item?.price }}</div>
      </div>
      <div style="position: absolute; bottom: 10px; right: 30px; width: 30%">
        <img :src="state.chatImg" alt="채팅" width="20" />
        {{ item?.chatCount }} &nbsp;
        <img :src="state.heartImg" alt="좋아요" width="20" />&nbsp;
        {{ item?.likeCount }}
      </div>
    </v-row>
    <router-link to="/product/register" class="addButton">
      <v-img src="@/assets/img/addButton.png"></v-img>
    </router-link>
  </v-container>
  <v-row class="my-footer">
    <div style="float: left; width: 48%" class="homeButton">
      <router-link to="#">
        <v-img class="mx-auto" width="80" src="@/assets/img/home.png"></v-img>
      </router-link>
    </div>
    <div style="width: 48%">
      <router-link to="/my-page">
        <v-img class="mx-auto" width="80" src="@/assets/img/user.png"></v-img>
      </router-link>
    </div>
  </v-row>
</template>

<script>
  import axios from "axios";
  import { onMounted, reactive } from "vue";
  import chatImg from "@/assets/img/chat.png";
  import heartImg from "@/assets/img/heart.png";
  import addButton from "@/assets/img/addButton.png";

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
            productStatus: "",
            imgUrl: "",
          },
        ],
        chatImg: chatImg,
        heartImg: heartImg,
        addButton: addButton,
      });
      onMounted(() => {
        getProductList();
      });
      const getProductList = () => {
        const url = "http://localhost:8081/api/v1/product/list";
        const params = { area_id: 1 };
        axios
          .get(url, { params })
          .then(function (response) {
            console.log(response);
            state.list = response.data.data;
            console.log(state.list);
          })
          .catch(function (error) {
            console.log(error);
            alert("서버 에러입니다. \n잠시 후 다시 시도해주세요.");
          });
      };
      return {
        state,
      };
    },
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
    bottom: 150px;
    right: 20px;
    width: 30%;
    height: 80px;
  }

  .my-footer {
    position: absolute;
    bottom: 40px;
    right: 20px;
    width: 100%;
    padding: 20px 30px 0 30px;
    border-top: solid 3px #2c3e50;
  }

  .homeButton {
    border-right: solid 3px #2c3e50;
  }
</style>