<template>
  <v-toolbar color="orange" elevation="2" opacity="100">
    <!--TODO("뒤로가기 기능")-->
    <v-icon icon="mdi-arrow-left" style="margin-left: 10px;"></v-icon>
    <v-toolbar-title class="text-left">카테고리</v-toolbar-title>
  </v-toolbar>
  <v-container fluid>
    <v-row
        class="mb-4"
        style="display: inline;"
        v-for="(item, index) in state.categoryList"
        :key="index"
        @click="goProductHomeByCategory(item.categoryCode)">
      <v-card class="category-card">
        <v-icon v-if="item?.categoryCode === '0'" icon="mdi-camera-gopro"></v-icon>
        <v-icon v-else-if="item?.categoryCode === '1'" icon="mdi-television-classic"></v-icon>
        <v-icon v-else-if="item?.categoryCode === '2'" icon="mdi-home-variant"></v-icon>
        <v-icon v-else-if="item?.categoryCode === '3'" icon="mdi-baby-buggy"></v-icon>
        <v-icon v-else-if="item?.categoryCode === '4'" icon="mdi-food-fork-drink"></v-icon>
        <v-icon v-else-if="item?.categoryCode === '5'" icon="mdi-book"></v-icon>
        <v-icon v-else-if="item?.categoryCode === '6'" icon="mdi-bike"></v-icon>
        <v-icon v-else-if="item?.categoryCode === '7'" icon="mdi-ring"></v-icon>
        <v-icon v-else-if="item?.categoryCode === '8'" icon="mdi-tshirt-crew"></v-icon>
        <v-icon v-else-if="item?.categoryCode === '9'" icon="mdi-tie"></v-icon>
        <v-icon v-else-if="item?.categoryCode === '10'" icon="mdi-gamepad-variant"></v-icon>
        <v-icon v-else-if="item?.categoryCode === '11'" icon="mdi-broom"></v-icon>
        <v-icon v-else-if="item?.categoryCode === '12'" icon="mdi-cat"></v-icon>
        <v-icon v-else-if="item?.categoryCode === '13'" icon="mdi-book-open-page-variant"></v-icon>
        <v-icon v-else-if="item?.categoryCode === '14'" icon="mdi-flower"></v-icon>
        <v-icon v-else-if="item?.categoryCode === '15'" icon="mdi-recycle"></v-icon>
        <v-icon v-else-if="item?.categoryCode === '16'" icon="mdi-car-hatchback"></v-icon>
        <br>
        {{ item?.categoryName }}
      </v-card>
    </v-row>
  </v-container>
</template>

<script>
import {onMounted, reactive} from "vue";
import $axiosInst from "@/common/AxiosInst"
import router from "@/router";

export default {
  setup() {
    const state = reactive({
      categoryList: [
        {
          categoryCode: "",
          categoryName: "",
        }
      ],
    });
    onMounted(() => {
      getCategoryList();
    });
    const getCategoryList = () => {
      const url = "/api/v1/category/list";
      $axiosInst
          .get(url)
          .then(function (response) {
            console.log(response);
            state.categoryList = response.data;
          })
          .catch(function (error) {
            console.log(error);
            alert("서버 에러입니다. \n잠시 후 다시 시도해주세요.");
          });
    };
    const goProductHomeByCategory = (categoryCode) => {
      router.push({
        name: "ProductList",
        params: {category_code: categoryCode}
      })
    };
    return {
      state,
      goProductHomeByCategory,
    };
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.category-card {
  display: inline-block;
  width: 30%;
  margin: 17px 17px;
}
</style>