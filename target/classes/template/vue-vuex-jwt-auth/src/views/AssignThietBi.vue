<template>
  <div class="container">
    <header class="jumbotron">
      <div class="form-group">
        <label for="tenThietBi">Tên người dùng</label>
        <input
          type="text"
          class="form-control"
          id="tenThietBi"
          placeholder="Tên người dùng"
          v-model="user.tenNguoiDung"
          disabled
        />
      </div>
      <div class="form-group">
        <label for="role">Loại thiết bị</label><br />
        <select style="width: 100%; height: 33px" @change="onChange">
          <option disabled selected>Chọn loại thiết bị...</option>
          <option :value="loai.id" v-for="(loai, id) in listLoai" :key="id">
            {{ loai.tenLoai }}
          </option>
        </select>
      </div>
      <div class="form-group">
        <label for="role">Tên thiết bị</label><br />
        <select
          v-model="muonTra.thietBi"
          id="name"
          style="width: 100%; height: 33px"
        >
          <option
            :value="thietbi"
            v-for="(thietbi, id) in listThietBi"
            :key="id"
          >
            {{ thietbi.tenThietBi }}
          </option>
        </select>
      </div>
      <button type="button" @click="assign" class="btn btn-primary">
        Assign
      </button>
    </header>
  </div>
</template>

<script>
import UserService from '../services/user.service';
import ThietBiService from '../services/thietbi.service';

export default {
  // name: 'ThietBi',
  data() {
    return {
      user: {},
      listLoai: [],
      listThietBi: [],
      muonTra: {},
    };
  },
  mounted() {
    UserService.getUser(this.$route.params.id).then(
      (response) => {
        this.user = response.data;
        this.muonTra.taiKhoan = response.data;
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
    ThietBiService.getLoaiThietBi().then(
      (response) => {
        window.console.log(response.data);
        this.listLoai = response.data;
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
  watch: {
    listLoai() {
      //   this.listLoai = this.listLoai[0]
      //  this.listThietBi = this.listThietBi[0]
    },
  },
  methods: {
    onChange(event) {
      ThietBiService.getThietBiByLoai(event.target.value).then((response) => {
        this.listThietBi = response.data;
      });
    },
    assign(){
         window.console.log("1 = "+this.muonTra.thietBi)
       ThietBiService.assignThietBi(this.muonTra).then((response) =>{
            window.console.log(response.status)
           if(response.status == 201){
                alert("Assign thành công")
                this.$router.push('/user');
                
           }
           if(response.status == 204){
                alert("Chọn thiết bị")
              //  location.reload()
           }
           // else  alert("Assign thất bại")
       })
    }
  },
};
</script>
