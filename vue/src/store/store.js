import {createStore} from "vuex";
import mutations from "@/store/mutations";
import getters from "@/store/getters";
import actions from "@/store/actions";

const store = createStore({
    state: { // 변수 집합
        userName: localStorage.getItem('userName'),
        token: localStorage.getItem('token'),
    },
    getters, // 변수 get
    mutations, // 변수 set
    actions,
});

export default store;