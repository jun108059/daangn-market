<template>
  <v-toolbar color="orange" elevation="2">
    <v-icon
        style="padding-left: 20px; margin-right: -10px"
        icon="mdi-arrow-u-left-top-bold"
        @click="goBack"></v-icon>
    <v-toolbar-title>내 물건 팔기</v-toolbar-title>
    <v-spacer></v-spacer>
    <v-btn class="submit-btn" @click="submitForm()"><h3>완료</h3></v-btn>
  </v-toolbar>
  <v-container fluid>
    <v-form v-on:submit.prevent="submitForm" id="join" lazy-validation>
      <div>
        <div class="image-upload-container">
          <div class="image-wrapper">
            <label class="input-button">
              <input type="file" multiple @change="handleImageUpload" style="display: none;" accept="image/*"/>
              <span class="mdi mdi-camera-enhance"></span>
            </label>
            <label class="input-image-text">
              사진 첨부
            </label>
          </div>
          <div v-for="(image, index) in images" :key="index" class="image-wrapper">
            <img :src="image.dataUrl" alt="Uploaded" class="preview-image"/>
            <button @click="removeImage(index)" class="remove-btn">X</button>
          </div>
        </div>
      </div>
      <v-col cols="12">
        <v-text-field
            v-model="state.title"
            type="text"
            placeholder="제목"
            required/>
        <v-select
            v-model="state.defaultSelected"
            label="카테고리"
            :items="state.categoryList"
            item-title="categoryName"
            item-value="categoryCode"
            return-object>
        </v-select>
        <v-text-field
            v-model="state.price"
            type="number"
            placeholder="가격(원)"/>
        <v-textarea
            v-model="state.content"
            type="text"
            placeholder="게시글 내용을 작성해주세요."/>
        가짜 품목 및 판매금지품목은 게시가 제한됩니다.
      </v-col>
    </v-form>
  </v-container>
</template>

<script>
import $axiosInst from "@/common/AxiosInst"
import {onMounted, reactive, ref} from "vue";
import router from "@/router";
import {useToast} from "vue-toastification";

export default {
  setup() {
    const toast = useToast();
    const images = ref([]);
    const state = reactive({
      title: "",
      price: "",
      content: "",
      defaultSelected: {
        categoryCode: "0",
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
    const updateSelectedCategory = (selectedCategory) => {
      state.defaultSelected = selectedCategory;
    };
    const handleImageUpload = (event) => {
      if (images.value.length >= 2) return;
      const uploadedFiles = event.target.files;

      for (let i = 0; i < uploadedFiles.length; i++) {
        const file = uploadedFiles[i];
        if (images.value.length >= 3) break;
        images.value.push({
          dataUrl: URL.createObjectURL(file),
          file: file
        });
      }
    };
    const removeImage = (index) => {
      images.value.splice(index, 1);
    };

    const getCategory = () => {
      const url = "api/v1/category/list";
      $axiosInst
          .get(url)
          .then(function (response) {
            console.log(response);
            state.categoryList = response.data;
            if (state.categoryList[0] && state.categoryList[0].categoryCode === "0") {
              state.defaultSelected = state.categoryList[0];
            }
          })
          .catch(function (error) {
            console.log(error);
            alert("서버 에러입니다. \n잠시 후 다시 시도해주세요.");
          });
    };
    const submitForm = () => {
      const formData = new FormData();

      images.value.forEach((image) => {
        formData.append('images', image.file);
      });
      formData.append('title', state.title);
      formData.append('price', state.price);
      formData.append('categoryCode', state.defaultSelected.categoryCode);
      formData.append('content', state.content);

      const url = "/api/v1/product";
      $axiosInst
          .post(url, formData, {
            headers: {
              'Content-Type': 'multipart/form-data'
            }
          })
          .then(function (response) {
            console.log(response);
            toast.success("물건 등록 완료!");
            router.push("/product/list");
          })
          .catch(function (error) {
            console.log(error);
            alert("서버 에러입니다. \n잠시 후 다시 시도해주세요.");
          });
    };
    const goBack = () => {
      router.back();
    };
    return {
      images,
      state,
      submitForm,
      handleImageUpload,
      removeImage,
      updateSelectedCategory,
      toast,
      goBack,
    };
  },
};
</script>

<style scoped>
.image-upload-container {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  margin-left: 13px;
}

.submit-btn:hover:not(:disabled) {
  background: #cccccc;
}

.image-wrapper {
  width: 100px;
  height: 100px;
  overflow: hidden;
  border-radius: 5px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  position: relative;
  margin-right: 6px;
}

.preview-image {
  width: 100%;
  height: auto;
  object-fit: cover;
}

.remove-btn {
  position: absolute;
  top: 2px;
  right: 2px;
  background: red;
  color: white;
  border: none;
  border-radius: 50%;
  width: 20px;
  height: 20px;
  font-size: 12px;
  cursor: pointer;
}

.input-button {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 100%;
  cursor: pointer;
  position: absolute;
  top: 0;
  left: 0;
  font-size: 24px;
  color: #333;
}

.input-image-text {
  margin-left: 20px;
  margin-top: 65px;
  display: flex;
  align-items: flex-end;
}

</style>