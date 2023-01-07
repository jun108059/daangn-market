import {createRouter, createWebHistory} from "vue-router";

import Home from "@/components/Home.vue";
// import Login from '@/components/LoginPage.vue';
// import Join from '@/components/JoinPage.vue';

const router = createRouter({
  history: createWebHistory(),
  routes: [
    // 주소와 컴포넌트 매칭
    { path: "/", name: "Home", component: Home },
    // { path: "/login", name: "Login", component: Login },
    // { path: "/join", name: "Join", component: Join },
  ],
});

export default router;
