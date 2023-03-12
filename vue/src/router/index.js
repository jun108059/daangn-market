import {createRouter, createWebHistory} from "vue-router";

import HomeView from "@/views/HomeView.vue";
import LoginView from "@/views/member/LoginView.vue";
import JoinView from "@/views/member/JoinView.vue";
import JoinOkView from "@/views/member/JoinOkView.vue";
import ProductListView from "@/views/product/ProductListView.vue";
import ProductRegisterView from "@/views/product/ProductRegisterView.vue";
import ProductDetailView from "@/views/product/ProductDetailView.vue";
import MyPageView from "@/views/member/MyPageView.vue";
import CategoryView from "@/views/product/CategoryView.vue";
import LikesProductListView from "@/views/product/LikesProductListView.vue";
import MyProductListView from "@/views/product/MyProductListView.vue";

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
        {
            path: "/product/list/:category_code?",
            name: "ProductList",
            component: ProductListView,
        },
        {
            path: "/product/detail/:productId",
            name: "ProductDetailView",
            component: ProductDetailView,
        },
        {
            path: "/product/register",
            name: "ProductRegister",
            component: ProductRegisterView,
        },
        {
            path: "/my-page",
            name: "MyPageView",
            component: MyPageView,
        },
        {
            path: "/category-list",
            name: "CategoryView",
            component: CategoryView,
        },
        {
            path: "/my-likes",
            name: "LikesProductListView",
            component: LikesProductListView,
        },
        {
            path: "/my-products",
            name: "MyProductListView",
            component: MyProductListView,
        },
    ],
});

export default router;
