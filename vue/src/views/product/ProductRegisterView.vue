<template>
  <v-toolbar color="orange" elevation="2">
    <v-icon
      style="padding-left: 20px"
      icon="mdi-arrow-u-left-top-bold"></v-icon>
    <v-toolbar-title>중고거래 글쓰기</v-toolbar-title>
    <v-spacer></v-spacer>
    <v-btn><h3>완료</h3></v-btn>
  </v-toolbar>
  <v-container fluid>
    <v-form v-on:submit.prevent="submitForm" id="join" lazy-validation>
      <v-row>
        <v-col cols="12"><h3>사진첨부 🥕</h3></v-col>
      </v-row>
      <v-row>
        <v-col cols="12">
          <ImageUploader server="/api/v1/image">
            <!--            error="@error('media'){{$message}}@enderror">-->
          </ImageUploader>
          <!--          <update-media-->
          <!--            server="/api/upload"-->
          <!--            media-file-path="/storage/post_images"-->
          <!--            media-server="/api/media/{{$post->id}}"-->
          <!--            error="@error('media'){{$message}}@enderror">-->
          <!--          </update-media>-->
        </v-col>
        <v-col cols="12">
          <v-text-field
            v-model="state.title"
            type="text"
            placeholder="제목"
            required />
          <v-select
            v-model="state.defaultSelected"
            label="카테고리"
            :items="state.categoryList"
            item-title="categoryName"
            item-value="categoryCode">
          </v-select>
          <v-text-field
            v-model="state.price"
            type="number"
            placeholder="가격(원)" />
          <v-textarea
            v-model="state.content"
            type="text"
            placeholder="게시글 내용을 작성해주세요." />
          가짜 품목 및 판매금지품목은 게시가 제한됩니다.
        </v-col>
      </v-row>
    </v-form>
  </v-container>
</template>

<script>
  import axios from "axios";
  import { onMounted, reactive } from "vue";
  import ImageUploader from "@/components/ImageUploader.vue";

  export default {
    setup() {
      const state = reactive({
        title: "",
        price: "",
        content: "",
        defaultSelected: {
          categoryCode: "1",
          categoryName: "디지털기기",
        },
        categoryList: [
          {
            categoryCode: "",
            categoryName: "",
          },
        ],
      });
      onMounted(() => {
        getCategory();
      });
      const getCategory = () => {
        const url = "http://localhost:8081/api/v1/category/list";
        axios
          .get(url)
          .then(function (response) {
            console.log(response);
            state.categoryList = response.data.data;
          })
          .catch(function (error) {
            console.log(error);
            alert("서버 에러입니다. \n잠시 후 다시 시도해주세요.");
          });
      };
      const submitForm = () => {
        const url = "http://localhost:8081/api/v1/product/register";
        axios
          .post(url)
          .then(function (response) {
            console.log(response);
            state.categoryList = response.data.data;
          })
          .catch(function (error) {
            console.log(error);
            alert("서버 에러입니다. \n잠시 후 다시 시도해주세요.");
          });
      };
      return {
        state,
        submitForm,
      };
    },
    components: {
      ImageUploader,
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
    bottom: 40px;
    right: 20px;
    width: 30%;
    height: 80px;
  }

  label.images-upload {
    height: 90px !important;
    width: 90px !important;
  }
</style>