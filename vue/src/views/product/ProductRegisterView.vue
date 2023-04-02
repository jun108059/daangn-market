<template>
  <v-toolbar color="orange" elevation="2">
    <v-icon
        style="padding-left: 20px; margin-right: -10px"
        icon="mdi-arrow-u-left-top-bold"></v-icon>
    <v-toolbar-title>내 물건 팔기</v-toolbar-title>
    <v-spacer></v-spacer>
    <v-btn><h3>완료</h3></v-btn>
  </v-toolbar>
  <v-container fluid>
    <v-form v-on:submit.prevent="submitForm" id="join" lazy-validation>
      <div>
        <div class="image-upload-container">
          <div class="image-wrapper">
            <label class="input-button">
              <input type="file" rel="fileInput" multiple @change="onFileInputChange" style="display: none;">
              <span class="mdi mdi-camera-enhance"></span>
            </label>
            <label class="input-image-text">
              사진 첨부
            </label>
          </div>
          <div v-for="image in state.images" :key="image.name" class="image-wrapper">
            <img :src="image.dataURL" class="preview-image" alt="img">
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
            item-value="categoryCode">
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
import {onMounted, reactive} from "vue";

export default {
  setup() {
    const state = reactive({
      images: [],
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
      files: [],
      thumbnails: [],
    });
    onMounted(() => {
      getCategory();
    });
    const onFileInputChange = () => {
      const files = this.$refs.fileInput.files;
      for (let i = 0; i < files.length; i++) {
        const file = files[i];
        const reader = new FileReader();
        reader.onload = () => {
          this.images.push({
            name: file.name,
            dataURL: reader.result,
          });
        };
        reader.readAsDataURL(file);
      }
    };
    const getCategory = () => {
      const url = "api/v1/category/list";
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
    const submitForm = () => {
      const url = "/api/v1/product";
      const productDto = {
        title: state.title,
        price: state.price,
        categoryCode: state.defaultSelected.categoryCode,
        content: state.content,
        imageList: state.images,
      };
      $axiosInst
          .post(url, productDto)
          .then(function (response) {
            console.log(response);
          })
          .catch(function (error) {
            console.log(error);
            alert("서버 에러입니다. \n잠시 후 다시 시도해주세요.");
          });
    };
    const onFileChange = () => {
      this.files = this.$refs.fileInput.files
      this.thumbnails = []
      for (let i = 0; i < this.files.length; i++) {
        const reader = new FileReader()
        reader.onload = (event) => {
          this.thumbnails.push(event.target.result)
        }
        reader.readAsDataURL(this.files[i])
      }
    };
    const removeThumbnail = (index) => {
      this.thumbnails.splice(index, 1)
      this.files.splice(index, 1)
    };
    const uploadImages = () => {
      const formData = new FormData()
      for (let i = 0; i < this.files.length; i++) {
        formData.append('images', this.files[i])
      }
      $axiosInst.post('/api/upload', formData).then(response => {
        console.log(response.data)
      })
    };
    return {
      state,
      submitForm,
      onFileChange,
      removeThumbnail,
      uploadImages,
      onFileInputChange,
    };
  },
};
</script>

<style scoped>
.image-upload-container {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

.image-wrapper {
  width: 100px;
  height: 100px;
  overflow: hidden;
  border-radius: 5px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  position: relative;
  margin-right: 10px;
}

.preview-image {
  width: 100%;
  height: auto;
  object-fit: cover;
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

button:hover {
  background-color: #ff931e;
}
</style>