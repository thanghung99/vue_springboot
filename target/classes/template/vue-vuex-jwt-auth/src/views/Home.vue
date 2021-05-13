<template>
  <div class="container">
    <header class="jumbotron">
      <h3>{{content}}</h3>
    </header>
  </div>
</template>

<script>
import UserService from '../services/user.service';

export default {
  name: 'Home',
  data() {
    return {
      content: ''
    };
  },
  created(){
    let count = 0;
    while(count !=0){
      location.reload();
      count++
    }
   // location.reload()
  },
  mounted() {
    window.console.log("HOME")
    UserService.getPublicContent().then(
      response => {
        this.content = response.data; 

      },
      error => {
        this.content =
          (error.response && error.response.data && error.response.data.message) ||
          error.message ||
          error.toString();
      }
    );
  }
};
</script>
