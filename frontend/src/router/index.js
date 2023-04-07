import { createRouter, createWebHistory } from "vue-router";
import Home from "../views/Home.vue";
import Login from "../views/user/Login.vue";
import Join from "../views/user/Join.vue";
import Modify from "../views/user/Modify.vue";
import PersonalInfo from "../views/personalInfo/PersonalInfo.vue";
import Test from "../views/test.vue";
import Verification from "../views/verification/Verification.vue";
import VerificationList from "../views/verification/VerificationList.vue";
import WalletInfo from '../views/wallet/WalletInfo.vue'

const routes = [
  {
    path: "/",
    name: "Home",
    component: Home,
  },
  {
    path: "/about",
    name: "About",
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/About.vue"),
  },
  {
    path: "/login",
    name: "Login",
    component: Login,
  },
  {
    path: "/join",
    name: "Join",
    component: Join,
  },
  {
    path: "/modify",
    name: "Modify",
    component: Modify,
  },
  {
    path: "/personalInfo",
    name: "PersonalInfo",
    component: PersonalInfo,
  },
  {
    path: "/walletInfo",
    name: "WalletInfo",
    component: WalletInfo,
    meta: { urlCheck: false }
  },
  {
    path: "/test",
    name: "test",
    component: Test,
  },
    {
    path: "/verificationList",
    name: "VerificationList",
    component: VerificationList,
  },
  {
    path: "/verification",
    name: "Verification",
    component: Verification,
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

// router 네비게이션 가드
// to : 이동할 URL 정보가 담긴 Router 객체
// from : 현재 URL 정보가 담긴 Router 객체
// next : to에서 지정한 URL로 이동하기 위해 반드시 호출해야 하는 함수

// router.beforeEach((to, from, next) => {
//   if (to.meta.urlCheck ) { 
//     alert("잘못된 접근입니다.")
//     next("/")
//   }
// })

export default router;
