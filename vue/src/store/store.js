import {createStore} from "vuex";
import mutations from "@/store/mutations";
import getters from "@/store/getters";

const store = createStore({
    state: { // 변수 집합
        userName: null,
        token: null,
    },
    getters, // 변수 get
    mutations, // 변수 set
});

export default store;