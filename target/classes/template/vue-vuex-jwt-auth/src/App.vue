<template>
  <div id="app">
    <font-awesome-icon icon="user-secret" />
    <nav class="navbar navbar-expand navbar-dark bg-dark">
      <a href class="navbar-brand" @click.prevent>Kiaisoft</a>
      <div class="navbar-nav mr-auto">
        <li class="nav-item"  v-if="currentUser">
          <router-link to="/home" class="nav-link">
            <font-awesome-icon icon="home" />Home
          </router-link>
        </li>
        <li class="nav-item" v-if="currentUser">
          <router-link v-if="!role" to="/thietbi" class="nav-link"
            >Thiết bị</router-link
          >
        </li>
        <li class="nav-item" v-if="currentUser">
          <router-link v-if="!role" to="/user" class="nav-link"
            >User</router-link
          >
        </li>
        <li class="nav-item" v-if="currentUser">
          <router-link
            v-if="role" 
            to="/thietbi/yeu-cau-thiet-bi"
            class="nav-link"
            >Yêu cầu thiết bị</router-link
          >
        </li>
        <li class="nav-item" v-if="currentUser">
          <router-link
            v-if="!role"
            to="/thietbi/yeu-cau-xu-ly"
            class="nav-link"
            >Yêu cầu xử lí</router-link
          >
        </li>
        <li class="nav-item" v-if="currentUser">
          <router-link
            v-if="role"
            to="/thietbi/danh-sach-yeu-cau"
            class="nav-link"
            >Danh sách yêu cầu</router-link
          >
        </li>
        <li class="nav-item" v-if="currentUser">
          <router-link
            v-if="role"
            to="/thietbi/danh-sach-thiet-bi"
            class="nav-link"
            >Danh sách thiết bị</router-link
          >
        </li>
      </div>
      <div v-if="!currentUser" class="navbar-nav ml-auto">
        <!-- <li class="nav-item">
          <router-link to="/register" class="nav-link">
            <font-awesome-icon icon="user-plus" />Sign Up
          </router-link>
        </li> -->
        <li class="nav-item">
          <router-link to="/login" class="nav-link">
            <font-awesome-icon icon="sign-in-alt" />Login
          </router-link>
        </li>
      </div>

      <div v-if="currentUser" class="navbar-nav ml-auto">
        <li class="nav-item">
          <router-link to="/profile" class="nav-link">
            <font-awesome-icon icon="user" />
            {{ currentUser.username }}
          </router-link>
        </li>
        <li class="nav-item">
          <a class="nav-link" href @click.prevent="logOut">
            <font-awesome-icon icon="sign-out-alt" />LogOut
          </a>
        </li>
      </div>
    </nav>


      <router-view @checkUser='checkUser' />
    
  </div>
</template>

<script>
import UserService from './services/user.service';
export default {
  data() {
    return {
      role: '',
    };
  },
  mounted() {
    UserService.checkRole().then((response) => {
      window.console.log(response.data);
      if (response.data == 'admin') {
        this.role = false;
        window.console.log(this.role)
      }
      if (response.data == 'user') {
        this.role = true;
      }
    });
  },
  computed: {
    currentUser() {
      window.console.log(this.$store.state.auth.user);
      return this.$store.state.auth.user;
    },
    showAdminBoard() {
      if (this.currentUser && this.currentUser.roles) {
        return this.currentUser.roles.includes('ROLE_ADMIN');
      }

      return false;
    },
    showModeratorBoard() {
      if (this.currentUser && this.currentUser.roles) {
        return this.currentUser.roles.includes('ROLE_MODERATOR');
      }

      return false;
    },
  },
  methods: {
    logOut() {
      this.role=''
      this.$store.dispatch('auth/logout');
      this.$router.push('/login');
      location.reload()
    },
    checkUser() {
       window.console.log("APP")
       UserService.checkRole().then((response) => {
      window.console.log(response.data);
      if (response.data == 'admin') {
        this.role = false;
        window.console.log(this.role)
      }
      if (response.data == 'user') {
        this.role = true;
      }
    });
    }
  },
};
</script>
<style >
.container{
      min-width: 100%;
}
</style>