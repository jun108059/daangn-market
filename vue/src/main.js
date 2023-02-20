import {createApp} from "vue";
import App from "./App.vue";
import router from "@/router";
import vuetify from "./plugins/vuetify";
import Toast, {POSITION} from "vue-toastification";
import "vue-toastification/dist/index.css";
import {UpdateMedia, UploadMedia} from "vue-media-upload";
import axios from "axios";
import store from "@/store/store.js";

const options = {
    position: POSITION.TOP_RIGHT,
    timeout: 2000,
};

createApp(App)
    .use(vuetify)
    .use(router)
    .use(Toast, options)
    .use(store)
    .provide('$axios', axios)
    .component("upload-media", UploadMedia)
    .component("update-media", UpdateMedia)
    .mount("#app");
