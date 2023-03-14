<template>
  <v-toolbar color="orange" elevation="2">
    <v-icon icon="mdi-arrow-left" style="margin-left: 10px; margin-right: -40px"></v-icon>
    <v-toolbar-title>나의 판매내역</v-toolbar-title>
    <v-spacer></v-spacer>
  </v-toolbar>
  <v-container fluid>
    <v-tabs
        v-model="state.tab"
        fixed-tabs
        color="orange-accent-4"
        align-tabs="center">
      <v-tab :value="1" @click="getProductList('TRADING')">판매중</v-tab>
      <v-tab :value="2" @click="getProductList('COMPLETED')">거래완료</v-tab>
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
      <div style="position: absolute; bottom: 55px; right: 5px; width: 30%; font-size: 14px">
        <img class="chat-img" :src="state.chatImg" alt="채팅" width="15"/>
        {{ item?.chatCount }} &nbsp;
        <img class="like-img" :src="state.heartImg" alt="좋아요" width="15"/>&nbsp;
        {{ item?.likeCount }}
      </div>
      <hr/>
      <div @click.stop="changeStatusTrading(item.id, item.status)" class="state-choose"
           style="border-right: solid 2px #cccccc">
        판매중으로 변경
      </div>
      <div @click.stop="changeStatusReserved(item.id, item.status)" class="state-choose"
           style="border-right: solid 2px #cccccc">
        예약중으로 변경
      </div>
      <div @click.stop="changeStatusCompleted(item.id, item.status)" class="state-choose">
        거래완료로 변경
      </div>
    </v-row>
  </v-container>
</template>

<script>
import {onMounted, reactive} from "vue";
import {useToast} from "vue-toastification";
import $axiosInst from "@/common/AxiosInst"
import router from "@/router";
import chatImg from "@/assets/img/chat.png";
import heartImg from "@/assets/img/heart.png";
import reservedImg from "@/assets/img/reserved.png";
import completedImg from "@/assets/img/completed.png";
import $store from "@/store/store";

export default {
  setup() {
    const toast = useToast();
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
      reservedImg: reservedImg,
      completedImg: completedImg,
      tab: null,
    });
    onMounted(() => {
      getProductList("TRADING");
    });
    const getProductList = (status) => {
      const url = "/api/v1/product/list";
      const params = {
        member_id: $store.getters.getUserId,
        status: status
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
    const changeStatusReserved = (id, status) => {
      if (status === "RESERVED") {
        toast.warning("이미 예약중인 상태입니다");
        return
      }
      let tabStatus = "TRADING"
      if (state.tab === 2) {
        tabStatus = "COMPLETED"
      }
      const url = "/api/v1/product/status";
      const params = {
        id: id,
        status: "RESERVED"
      }
      $axiosInst
          .put(url, params)
          .then(function () {
            getProductList(tabStatus);
            toast.success("예약중으로 변경했습니다");
          })
          .catch(function (error) {
            console.log(error);
            alert("서버 에러입니다. \n잠시 후 다시 시도해주세요.");
          });
    };
    const changeStatusCompleted = (id, status) => {
      if (status === "COMPLETED") {
        toast.warning("이미 거래완료인 상태입니다")
        return
      }
      let tabStatus = "TRADING"
      if (state.tab === 2) {
        tabStatus = "COMPLETED"
      }
      const url = "/api/v1/product/status";
      const params = {
        id: id,
        status: "COMPLETED"
      }
      $axiosInst
          .put(url, params)
          .then(function () {
            getProductList(tabStatus);
            toast.success("거래완료로 변경했습니다");
          })
          .catch(function (error) {
            console.log(error);
            alert("서버 에러입니다. \n잠시 후 다시 시도해주세요.");
          });
    };
    const changeStatusTrading = (id, status) => {
      if (status === "TRADING") {
        toast.warning("이미 판매중인 상태입니다")
        return
      }
      let tabStatus = "TRADING"
      if (state.tab === 2) {
        tabStatus = "COMPLETED"
      }
      const url = "/api/v1/product/status";
      const params = {
        id: id,
        status: "TRADING"
      }
      $axiosInst
          .put(url, params)
          .then(function () {
            getProductList(tabStatus);
            toast.success("판매중으로 변경했습니다");
          })
          .catch(function (error) {
            console.log(error);
            alert("서버 에러입니다. \n잠시 후 다시 시도해주세요.");
          });
    };
    return {
      state,
      goDetailPage,
      getProductList,
      changeStatusReserved,
      changeStatusCompleted,
      changeStatusTrading,
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

.state-choose {
  width: 33%;
  font-size: 13px;
}

hr {
  margin-top: 5px;
  margin-bottom: 7px;
  width: 100%;
}

</style>