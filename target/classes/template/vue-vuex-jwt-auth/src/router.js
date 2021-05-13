import Vue from 'vue';
import Router from 'vue-router';
import Home from './views/Home.vue';
import Login from './views/Login.vue';
import User from './views/User.vue';
import AddUser from './views/AddUser.vue';
import Register from './views/Register.vue';
import ThietBi from './views/ThietBi.vue';
import AddThietBi from './views/AddThietBi.vue';
import UpdateThietBi from './views/UpdateThietBi.vue';
import AssignThietBi from './views/AssignThietBi.vue';
import UpdateUser from './views/UpdateUser.vue';
import YeuCauThietBi from './views/YeuCauThietBi.vue';
import ThongTinYeuCau from './views/ThongTinYeuCau.vue';
import YeuCauXuLy from './views/YeuCauXuLy.vue';
import Profile from './views/Profile.vue';
import DanhSachYeuCau from './views/DanhSachYeuCau.vue';
import DanhSachThietBi from './views/DanhSachThietBi.vue';
Vue.use(Router);

export const router = new Router({
  mode: 'history',
  routes: [
   
    {
      path: '/',
      component: Login
    },
    {
      path: '/home',
      component: Home
    },
    {
      path: '/user',
      component: User
    },
    {
      path: '/profile',
      component: Profile
    },
    {
      path: '/user/add',
      component: AddUser
    },
    {
      path: '/user/update/:id',
      component: UpdateUser
    },
    {
      path: '/login',
      component: Login
    },
    {
      path: '/register',
      component: Register
    },
    {
      path: '/thietbi',
      component: ThietBi
    },
    {
      path: '/thietbi/add',
      component: AddThietBi
    },
    {
      path: '/thietbi/update/:id',
      component: UpdateThietBi
    },
    {
      path: '/user/assign/:id',
      component: AssignThietBi
    },
    {
      path: '/thietbi/yeu-cau-thiet-bi',
      component: YeuCauThietBi
    },
    {
      path: '/thietbi/danh-sach-thiet-bi',
      component: DanhSachThietBi
    },
    {
      path: '/thietbi/yeu-cau-thiet-bi/:id',
      component: ThongTinYeuCau
    },
    {
      path: '/thietbi/yeu-cau-xu-ly',
      component: YeuCauXuLy
    },
    {
      path: '/thietbi/danh-sach-yeu-cau',
      component: DanhSachYeuCau
    },
  ]
});

// router.beforeEach((to, from, next) => {
//   const publicPages = ['/login', '/register', '/home'];
//   const authRequired = !publicPages.includes(to.path);
//   const loggedIn = localStorage.getItem('user');

//   // trying to access a restricted page + not logged in
//   // redirect to login page
//   if (authRequired && !loggedIn) {
//     next('/login');
//   } else {
//     next();
//   }
// });
