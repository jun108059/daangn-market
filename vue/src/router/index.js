import { createRouter, createWebHistory } from "vue-router";

import HomeView from "@/views/HomeView.vue";
import LoginView from "@/views/member/LoginView.vue";
import JoinView from "@/views/member/JoinView.vue";
import JoinOkView from "@/views/member/JoinOkView.vue";

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: "/",
      name: "Home",
      component: HomeView,
    },
    {
      path: "/login",
      name: "Login",
      component: LoginView,
    },
    {
      path: "/join",
      name: "Join",
      component: JoinView,
    },
    {
      path: "/join-ok",
      name: "JoinOk",
      component: JoinOkView,
    },
  ],
});

export default router;
