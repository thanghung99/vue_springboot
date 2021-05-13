<template>
  <div class="container">
    <header class="jumbotron">
      <button type="button" class="btn btn-primary">
        <router-link to="/user/add" style="color: white">ADD</router-link>
      </button>
      <table class="table">
        <thead>
          <tr>
            <th scope="col">Tên người dùng</th>
            <th scope="col">Địa chỉ</th>
            <th scope="col">Số điện thoại</th>
            <th scope="col">Ngày sinh</th>
            <th scope="col">Quê quán</th>
            <th scope="col">Chức vụ</th>
            <th scope="col">Role</th>
            <th scope="col">Ảnh</th>
            <th scope="col">Update</th>
            <th scope="col">Delete</th>
            <th scope="col">Assign</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(user, id) in listUser" :key="id">
            <th scope="row">{{ user.tenNguoiDung }}</th>
            <td>{{ user.diaChi }}</td>
            <td>{{ user.sdt }}</td>
            <td v-if="user.ngaySinh">{{ user.ngaySinh |date }}</td>
            <td v-if="!user.ngaySinh"></td>
            <td>{{ user.queQuan }}</td>
            <td>{{ user.chucVu }}</td>
            <td>{{ user.role }}</td>
            <td>
              <img :src="user.anh ? require(`@/assets/${user.anh}`) : null" style="max-width: 41px" />
            </td>
            <td>
              <button type="button" class="btn btn-primary">
                <router-link
                  style="color: white"
                  :to="'/user/update/' + user.id"
                  >Update</router-link
                >
              </button>
            </td>
            <td>
              <button
                type="submit"
                @click="deleteUser(user.id)"
                class="btn btn-danger"
              >
                Delete
              </button>
            </td>
            <td>
              <button type="button" class="btn btn-success">
                <router-link
                  style="color: white"
                  :to="'/user/assign/' + user.id"
                  >Assign</router-link
                >
              </button>
            </td>
          </tr>
        </tbody>
      </table>
    </header>
  </div>
</template>

<script>
import UserService from '../services/user.service';

export default {
  name: 'ThietBi',
  data() {
    return {
      listUser: [],
    };
  },
  mounted() {
    UserService.getListUser().then(
      (response) => {
        this.listUser = response.data;
      },
      (error) => {
        this.content =
          (error.response &&
            error.response.data &&
            error.response.data.message) ||
          error.message ||
          error.toString();
      }
    );
  },

  methods: {  
    getImgUrl(anh) {
      return require('../assets/' + anh);
    },
    deleteUser: function (id) {
      UserService.deleteUser(id).then((response) => {
        if (response.status == 200) alert('Xóa thành công');
        else alert('Xóa thất bại');
        location.reload();
      });
    },
  },
};
</script>
