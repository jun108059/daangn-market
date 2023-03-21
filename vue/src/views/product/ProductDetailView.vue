<template>
  <v-toolbar color="orange" elevation="2" opacity="100">
    <v-icon
        icon="mdi-arrow-left"
        style="margin-left: 10px; margin-right: 10px"
        @click="goBack"></v-icon>
    <!--TODO("내 게시글일 때 수정/삭제 가능 추가")-->
  </v-toolbar>
  <v-container fluid>
    <v-carousel height="250px">
      <v-carousel-item
          v-for="(image, index) in state.productDetail.imageUrls"
          :key="index">
        <v-img :src="image" height="100%"/>
      </v-carousel-item>
      <v-carousel-item v-if="state.productDetail.imageUrls.length === 0">
        <v-img src="" height="100%"/>
      </v-carousel-item>
    </v-carousel>
    <v-row class="mt-2 mb-2 seller-info">
      <div style="float: left; width: 25%">
        <img :src="state.productDetail.sellerImageUrl" alt="판매자-프로필-사진" width="70" height="70"/>
      </div>
      <div style="text-align: left; margin-left: 10px; width: 70%">
        <div>{{ state.productDetail.nickname }}</div>
        <div class="location">{{ state.productDetail.areaName }}</div>
      </div>
    </v-row>
    <v-row class="mt-2 mb-2">
      <div v-if="state.productDetail.status === 'RESERVED'" style="display:inline;">
        <img class="state-img" :src="state.reservedImg" alt="예약중" width="80"/></div>
      <div v-if="state.productDetail.status === 'COMPLETED'" style="display:inline;">
        <img class="state-img" :src="state.completedImg" alt="거래완료" width="80"/></div>
      <div class="product-title">
        {{ state.productDetail.title }}
      </div>
    </v-row>
    <v-row class="mt-2 mb-2">
      <div class="small-gray">
        {{ state.productDetail.category }} • {{ $time.elapsedTime(state.productDetail.createdDateTime) }}
      </div>
    </v-row>
    <v-row class="product-content">
      {{ state.productDetail.content }}
    </v-row>
    <v-row class="chat-like-count">
      <div class="small-gray">
        채팅 {{ state.productDetail.chatCount }} • 관심 {{ state.productDetail.likesCount }}
      </div>
    </v-row>
  </v-container>
  <v-row v-if="state.sellersProducts.length !== 1" class="same-seller-info">
    <div style="float: left;">
      <span style="color: #ff931e">{{ state.productDetail.nickname }}</span>님의 판매상품
    </div>
    <v-icon
        icon="mdi-arrow-right"
        style="margin-right: 30px; float: right;"
        @click="goUserProductListView()"
    ></v-icon>
  </v-row>
  <v-container v-if="state.sellersProducts.length !== 1" class="mb-16">
    <v-row
        class="same-seller-products"
        v-for="product in state.sellersProducts"
        :key="product.id"
        @click="goDetailPage(product.id)"
        style="float: left; width: 55%; margin-top: 10px;"
    >
      <v-col v-if="productId !== product.id.toString()">
        <div>
          <img :src="product.imgUrl" alt="이미지" width="125" height="100"/>
        </div>
        <div class="more-products">{{ product.title }}</div>
        <div class="more-products">{{ product.areaName }} • {{ $time.elapsedTime(product.createdDateTime) }}</div>
        <div class="more-products">{{ $money.currency(product.price) }} 원</div>
      </v-col>
    </v-row>
  </v-container>
  <v-row class="my-footer">
    <div class="like-button" v-if="state.productDetail.isLikes === true">
      <v-img
          class="mx-auto"
          width="40"
          src="@/assets/img/is-like.png"
          @click="cancelLikesApi()"
      ></v-img>
    </div>
    <div class="like-button" v-if="state.productDetail.isLikes === false">
      <v-img
          class="mx-auto"
          width="40"
          src="@/assets/img/not-like.png"
          @click="likesApi()"
      ></v-img>

    </div>
    <div class="bold-text" style="width: 45%; float:left; text-align: left">
      {{ $money.currency(state.productDetail.price) }} 원
    </div>
    <!--TODO("채팅 페이지 연동하기")-->
    <div class="chatting-button" v-if="state.productDetail.status === 'COMPLETED'">
      <v-img class="mx-auto" width="80" src="@/assets/img/forbid-chat.png"></v-img>
    </div>
    <div class="chatting-button" v-if="state.productDetail.status !== 'COMPLETED'">
      <v-img class="mx-auto" width="80" src="@/assets/img/can-chat.png"></v-img>
    </div>
  </v-row>
</template>

<script>
import {onMounted, reactive} from "vue";
import $axiosInst from "@/common/AxiosInst"
import reservedImg from "@/assets/img/reserved.png";
import completedImg from "@/assets/img/completed.png";
import {useRoute} from "vue-router";
import router from "@/router";

export default {
  setup() {
    const route = useRoute();
    const productId = route.params.productId;
    const state = reactive({
      productDetail:
          {
            areaName: "",
            title: "",
            price: 0,
            likesCount: 0,
            chatCount: 0,
            memberId: 0,
            status: "",
            createdDateTime: "",
            imageUrls: [],
            nickname: "",
            category: "",
            content: "",
            sellerImageUrl: "",
            isLikes: false,
            isMine: false,
          },
      reservedImg: reservedImg,
      completedImg: completedImg,
      sellersProducts: [
        {
          id: 0,
          areaName: "",
          title: "",
          price: 0,
          imgUrl: "",
        },
      ],
    });
    onMounted(async () => {
      await getProduct();
      await getSellerProducts();
    });
    const getProduct = async () => {
      const url = "/api/v1/product";
      const params = {product_id: productId}
      await $axiosInst
          .get(url, {params})
          .then(function (response) {
            state.productDetail = response.data;
          })
          .catch(function (error) {
            console.log(error);
            alert("서버 에러입니다. \n잠시 후 다시 시도해주세요.");
          });
    };
    const getSellerProducts = async () => {
      const url = "/api/v1/product/list";
      const params = {
        member_id: state.productDetail.memberId,
        status: "TRADING",
      }
      await $axiosInst
          .get(url, {params})
          .then(function (response) {
            state.sellersProducts = response.data.content;
          })
          .catch(function (error) {
            console.log(error);
            alert("서버 에러입니다. \n잠시 후 다시 시도해주세요.");
          });
    };
    const likesApi = () => {
      const url = "api/v1/likes"
      const likesRequestDto = {productId: productId}
      $axiosInst
          .post(url, likesRequestDto)
          .then(function () {
            state.productDetail.isLikes = true;
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
    const cancelLikesApi = () => {
      const url = "api/v1/likes";
      const params = {product_id: productId};
      $axiosInst
          .delete(url, {params})
          .then(function () {
            state.productDetail.isLikes = false;
          })
          .catch(function (error) {
            console.log(error);
            alert("서버 에러입니다. \n잠시 후 다시 시도해주세요.");
          });
    };
    const goUserProductListView = () => {
      router.push({
        name: "UserProductListView",
        params: {
          memberId: state.productDetail.memberId
        }
      })
    };
    const goBack = () => {
      router.back();
    };
    return {
      productId,
      state,
      likesApi,
      cancelLikesApi,
      goDetailPage,
      goUserProductListView,
      goBack,
    };
  }
};
</script>

<style scoped>
.seller-info {
  position: relative;
  border-bottom: solid 1px #ff931e;
  padding: 10px 0;
}

.like-button {
  border-right: solid 1px dimgray;
  vertical-align: middle;
  float: left;
  width: 20%;
  display: inline;
  margin-left: -15px;
  margin-right: 15px;
  padding-right: 15px;
}

.more-products {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.location {
  font-size: 14px;
  color: gray;
}

.small-gray {
  font-size: 14px;
  margin-left: 5px;
  color: gray;
  float: left;
}

.product-title {
  margin-left: 5px;
  font-size: 20px;
  float: left;
}

.product-content {
  margin-left: 1px;
  margin-top: 30px;
  margin-bottom: 30px;
}

.chat-like-count {
  border-bottom: solid 1px #cccccc;
}

.same-seller-info {
  display: inline;
  margin-left: 15px;
  margin-top: 20px;
}

.same-seller-products {
  display: inline;
  margin-top: 10px;
  font-size: 12px;
}

.state-img {
  vertical-align: middle;
  margin-top: 4px;
  margin-left: -10px;
  margin-right: -5px;
}

.chatting-button {
  width: 130px;
  vertical-align: middle;
  float: right;
  margin-top: 1px;
  margin-left: 20px;
  margin-right: -35px;
}
</style>