<template>
  <div class="container">
    <header class="jumbotron">
      <div class="form-group">
        <label for="tenThietBi">Tên người dùng</label>
        <input
          @keyup="checkValidate"
          type="text"
          class="form-control"
          id="tenThietBi"
          placeholder="Tên người dùng"
          v-model="user.tenNguoiDung"
        />
        <diV>
          <span style="color: red">{{ errName }}</span>
        </diV>
      </div>
      <div class="form-group">
        <label for="dc">Địa chỉ</label>
        <input
          type="text"
          class="form-control"
          id="dc"
          placeholder=" Địa chỉ"
          v-model="user.diaChi"
        />
      </div>
      <div class="form-group">
        <label for="sdt">Số điện thoại</label>
        <input
          type="text"
          class="form-control"
          id="sdt"
          placeholder="Số điện thoại"
          v-model="user.sdt"
        />
      </div>
      <div class="form-group">
        <label for="ns">Ngày sinh</label>
        <input
          type="Date"
          class="form-control"
          id="ns"
          v-model="user.ngaySinh"
        />
      </div>
      <div class="form-group">
        <label for="qq">Quê quán</label>
        <input
          type="text"
          class="form-control"
          id="qq"
          placeholder="Quê quán"
          v-model="user.queQuan"
        />
      </div>
      <div class="form-group">
        <label for="cv">Chức vụ</label>
        <input
          type="text"
          class="form-control"
          id="cv"
          placeholder="Chức vụ"
          v-model="user.chucVu"
        />
      </div>
      <div class="form-group">
        <label for="role">Role</label><br />
        <select v-model="user.role" id="role" style="width: 14%; height: 33px">
          <option value="user" checked>User</option>
          <option value="admin">Admin</option>
        </select>
      </div>
      <div class="form-group">
        <label for="us">User name</label>
        <input
          @keyup="checkValidate"
          type="text"
          class="form-control"
          id="us"
          placeholder="User name"
          v-model="user.username"
        />
      </div>
      <diV>
        <span style="color: red">{{ errUserName }}</span>
      </diV>
      <div class="form-group">
        <label for="pass">Password</label>
        <input
          @keyup="checkValidate"
          type="password"
          class="form-control"
          id="pass"
          v-model="user.password"
        />
      </div>
      <diV>
        <span style="color: red">{{ errPassWord }}</span>
      </diV>
      <div class="form-group">
        <label for="cf">Confirm password</label>
        <input
          @keyup="checkValidate"
          type="password"
          class="form-control"
          id="cf"
          v-model="user.passwordConfirm"
        />
      </div>
      <diV>
        <span style="color: red">{{ errConfirmPassWord }}</span>
      </diV>
      <div class="form-group">
        <label for="anh">Ảnh</label>
        <input
          type="file"
          class="form-control-file"
          id="anh"
          v-on:change="onFileSelected"
          ref="file"
        />
      </div>
      <button @click="onUpload" type="button" class="btn btn-primary">
        Update
      </button>
    </header>
  </div>
</template>

<script>
import UserService from '../services/user.service';

export default {
  // name: 'ThietBi',
  data() {
    return {
      errName: '',
      errUserName: '',
      errPassWord: '',
      errConfirmPassWord: '',
      user: {
        tenNguoiDung: '',
        diaChi: '',
        sdt: '',
        ngaySinh: '',
        queQuan: '',
        chucVu: '',
        role: '',
        username: '',
        password: '',
        passwordConfirm: '',
      },
      ErrTenTB: '',
      selectedFile: '',
      message: '',
    };
  },
  mounted() {
    UserService.getUser(this.$route.params.id).then(
      (response) => {
        this.user = response.data;
        this.selectedFile = this.user.anh;
       // this.user.password = this.user.password;
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
    checkValidate() {
      if (this.user.username) this.errUserName = '';
      if (this.user.tenNguoiDung) this.errName = '';
      if (this.user.passwordConfirm == this.user.password)
        this.errConfirmPassWord = '';
    },
    onFileSelected(event) {
      this.selectedFile = event.target.files[0];
    },
    onUpload() {
      if (!this.user.tenNguoiDung) this.errName = 'Name is required!';
      else if (!this.user.username) this.errUserName = 'UserName is required!';
      else if (this.user.password!=null && this.user.passwordConfirm != this.user.password)
        this.errConfirmPassWord = 'Confirm password faild';
      else
        UserService.updateUser(
          this.$route.params.id,
          this.user,
          this.selectedFile
        ).then((response) => {
          if (response.status == 204) alert('User name trùng');
          else alert('Sửa thành công');
          location.reload();
        });
    },
  },
};
</script>
