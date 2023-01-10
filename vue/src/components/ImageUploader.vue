<template>
  <div>
    <div class="">
      <div class="gallery width-100" :class="error ? 'red-border' : ''">
        <Loader
          color="#0275d8"
          :active="loading"
          spinner="line-scale"
          background-color="rgba(255, 255, 255, .4)" />
        <div class="elements-wrapper">
          <!--UPLOAD BUTTON-->
          <div class="button-container image-margin">
            <label for="images-upload" class="images-upload">
              <svg
                class="custom-icon"
                xmlns="http://www.w3.org/2000/svg"
                width="1em"
                height="1em"
                preserveAspectRatio="xMidYMid meet"
                viewBox="0 0 24 24">
                <g fill="none">
                  <path
                    d="M12 1C5.925 1 1 5.925 1 12s4.925 11 11 11s11-4.925 11-11S18.075 1 12 1zm1 15a1 1 0 1 1-2 0v-3H8a1 1 0 1 1 0-2h3V8a1 1 0 1 1 2 0v3h3a1 1 0 1 1 0 2h-3v3z"
                    fill="currentColor" />
                </g>
              </svg>
            </label>
            <input
              @change="fileChange"
              id="images-upload"
              type="file"
              accept="image/*"
              multiple
              hidden />
          </div>

          <!--IMAGES PREVIEW-->
          <div
            v-for="(image, index) in file"
            :key="index"
            class="image-container image-margin">
            <img :src="image.url" alt="" class="images-preview" />
            <button @click="remove(index)" class="close-btn" type="button">
              <svg
                class="times-icon"
                xmlns="http://www.w3.org/2000/svg"
                width="0.65em"
                height="0.65em"
                preserveAspectRatio="xMidYMid meet"
                viewBox="0 0 352 512">
                <path
                  d="m242.72 256l100.07-100.07c12.28-12.28 12.28-32.19 0-44.48l-22.24-22.24c-12.28-12.28-32.19-12.28-44.48 0L176 189.28L75.93 89.21c-12.28-12.28-32.19-12.28-44.48 0L9.21 111.45c-12.28 12.28-12.28 32.19 0 44.48L109.28 256L9.21 356.07c-12.28 12.28-12.28 32.19 0 44.48l22.24 22.24c12.28 12.28 32.2 12.28 44.48 0L176 322.72l100.07 100.07c12.28 12.28 32.2 12.28 44.48 0l22.24-22.24c12.28-12.28 12.28-32.19 0-44.48L242.72 256z"
                  fill="currentColor" />
              </svg>
            </button>
          </div>
        </div>
      </div>
      <div v-for="(image, index) in file" :key="index" class="m-top">
        <input type="text" name="file[]" :value="image.name" hidden />
      </div>
    </div>
  </div>
</template>

<script>
  import Loader from "@/components/line-scale";
  import axios from "axios";

  export default {
    data() {
      return {
        file: [],
        loading: false,
      };
    },
    methods: {
      async fileChange(event) {
        this.loading = true;
        let files = event.target.files;
        for (let i = 0; i < files.length; i++) {
          let formData = new FormData();
          let url = URL.createObjectURL(files[i]);
          formData.set("image", files[i]);
          const { data } = await axios.post(this.server, formData);

          this.file.push({
            url: url,
            name: data.name,
            size: files[i].size,
            type: files[i].type,
          });
        }
        this.loading = false;
        this.fileEmit();
      },
      remove(index) {
        this.file.splice(index, 1);
        this.fileEmit();
      },
      fileEmit() {
        this.$emit("file", this.file);
      },
    },
    props: {
      error: {
        type: String,
        default: null,
      },
      server: {
        type: String,
        default: "/api/upload",
      },
    },
    components: { Loader },
  };
</script>

<style scoped>
  .gallery {
    background-color: #fbfbfb !important;
    border-radius: 5px !important;
    border-style: solid !important;
    line-height: 1 !important;
    box-sizing: border-box !important;
    height: auto !important;
  }

  .image-container {
    display: inline-table !important;
    height: 90px !important;
    width: 90px !important;
  }

  .images-preview {
    border-radius: 5px !important;
    border: 1px solid #ccc !important;
    display: inline-block !important;
    width: 88px !important;
    height: 88px !important;
    transition: filter 0.1s linear;
    object-fit: cover;
    object-position: center;
  }

  .images-preview:hover {
    filter: brightness(90%);
  }

  .button-container {
    display: inline-flex !important;
    height: 90px !important;
    width: 90px !important;
    margin-right: 0.25rem !important;
    margin-left: 0.25rem !important;
  }

  .images-upload {
    background-color: #ffffff !important;
    border-radius: 5px !important;
    border: 1px dashed #ccc !important;
    display: inline-block !important;
    cursor: pointer !important;
    width: 88px !important;
    height: 88px !important;
    text-align: center;
  }

  .images-upload:hover {
    background-color: #f1f1f1 !important;
  }

  .close-btn {
    background: none !important;
    color: white !important;
    border: none !important;
    padding: 0 !important;
    margin: 0 !important;
    font: inherit !important;
    cursor: pointer !important;
    outline: inherit !important;
    position: relative !important;
    right: 34px !important;
    top: -27px !important;
    width: 0 !important;
  }

  .times-icon {
    font-size: 3rem !important;
    padding: 0 !important;
    margin: 0 !important;
    filter: drop-shadow(0px 0px 1px black);
  }

  .custom-icon {
    color: #00afca !important;
    font-size: 3rem !important;
    margin-top: 18px !important;
  }

  .custom-icon:hover {
    color: #29818f !important;
  }

  .close-btn:hover {
    color: red !important;
  }

  /* -------------------- */

  .width-100 {
    width: 100% !important;
  }

  .red-border {
    border: 1px solid #dc3545 !important;
  }

  .elements-wrapper {
    padding: 1rem !important;
    display: flex !important;
    flex-wrap: wrap !important;
  }

  .image-margin {
    margin: 0.25rem !important;
  }
</style>