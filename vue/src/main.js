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

const app = createApp(App)
    .use(vuetify)
    .use(router)
    .use(Toast, options)
    .use(store)
    .provide('$axios', axios)
    .component("upload-media", UploadMedia)
    .component("update-media", UpdateMedia);

app.config.globalProperties.$money = {
    currency: function (value) {
        const num = Number(value);
        return num.toFixed(0).replace(/(\d)(?=(\d{3})+(?:\.\d+)?$)/g, "$1,")
    }
}

app.config.globalProperties.$time = {
    elapsedTime: function (date) {
        const start = new Date(date);
        const end = new Date();

        const diff = (end - start) / 1000;

        const times = [
            {name: '년', milliSeconds: 60 * 60 * 24 * 365},
            {name: '개월', milliSeconds: 60 * 60 * 24 * 30},
            {name: '일', milliSeconds: 60 * 60 * 24},
            {name: '시간', milliSeconds: 60 * 60},
            {name: '분', milliSeconds: 60},
        ];

        for (const value of times) {
            const betweenTime = Math.floor(diff / value.milliSeconds);

            if (betweenTime > 0) {
                return `${betweenTime}${value.name} 전`;
            }
        }
        return '방금 전';
    }
}

app.mount("#app");