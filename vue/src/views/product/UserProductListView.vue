<template>
  <v-toolbar color="orange" elevation="2">
    <v-icon icon="mdi-arrow-left" class="toolbar-icon"></v-icon>
    <v-toolbar-title class="user-product-title">{{ state.member.nickname }}님의 판매상품</v-toolbar-title>
    <div>
      <v-img :src="state.member.imagePath" class="toolbar-img ml-auto"/>
    </div>
  </v-toolbar>
  <v-container fluid>
    <v-tabs
        v-model="state.tab"
        fixed-tabs
        color="orange-accent-4"
        align-tabs="center"
        class="equal-width-tabs">
      <v-tab :value="1" @click="getProductList('ALL')">전체</v-tab>
      <v-tab :value="2" @click="getProductList('TRADING')">판매중</v-tab>
      <v-tab :value="3" @click="getProductList('COMPLETED')">거래완료</v-tab>
    </v-tabs>
  </v-container>
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
  </v-container>
</template>

<script>
import {onMounted, reactive} from "vue";
import $axiosInst from "@/common/AxiosInst"
import router from "@/router";
import chatImg from "@/assets/img/chat.png";
import heartImg from "@/assets/img/heart.png";
import reservedImg from "@/assets/img/reserved.png";
import completedImg from "@/assets/img/completed.png";
import {useRoute} from "vue-router";

export default {
  setup() {
    const route = useRoute();
    const memberId = route.params.memberId;
    const state = reactive({
      member: {
        nickname: "",
        imagePath: "",
      },
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
      reservedImg: reservedImg,
      completedImg: completedImg,
    });
    onMounted(() => {
      getMember();
      getProductList();
    });
    const getMember = () => {
      const url = "/api/v1/member";
      const params = {member_id: memberId}
      $axiosInst
          .get(url, {params})
          .then(function (response) {
            state.member = response.data;
          })
          .catch(function (error) {
            console.log(error);
            alert("서버 에러입니다. \n잠시 후 다시 시도해주세요.");
          });
    };
    const getProductList = (status) => {
      const url = "/api/v1/product/list";
      let params;

      if (status === "ALL") {
        params = {
          member_id: memberId
        }
      } else {
        params = {
          member_id: memberId,
          status: status
        }
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
    const goDetailPage = (id) => {
      router.push({
        name: "ProductDetailView",
        params: {productId: id}
      })
    };
    return {
      state,
      memberId,
      goDetailPage,
      getProductList,
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

.equal-width-tabs .v-tabs-bar {
  display: flex;
  justify-content: space-evenly;
  width: 100%;
}

.equal-width-tabs .v-tab {
  flex-basis: 0;
  flex-grow: 1;
  text-align: center;
}

.user-product-title {
  font-size: 18px;
  width: 40%;
  text-align: left;
}

.toolbar-icon {
  margin-left: 10px;
  width: 5%;
}

.toolbar-img {
  width: 50px;
  height: 50px;
  margin-right: 15px;
}
</style>