<template>
  <div class="container">
    <header class="jumbotron">
      <button type="button" class="btn btn-primary">
        <router-link to="/thietbi/add" style="color: white">ADD</router-link>
      </button>
      <table class="table">
        <thead>
          <tr>
            <th scope="col">Tên thiết bị</th>
            <th scope="col">Hãng sản xuất</th>
            <th scope="col">Loại</th>
            <th scope="col">Tổng số lượng</th>
            <th scope="col">Số lượng còn</th>
            <th scope="col">Trạng thái</th>
            <th scope="col">Update</th>
            <th scope="col">Delete</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(thietbi, id) in listThietBi" :key="id">
            <th scope="row">{{ thietbi.tenThietBi }}</th>
            <td>{{ thietbi.hangSX }}</td>
            <td>{{ thietbi.loai.tenLoai }}</td>
            <td>{{ thietbi.loai.soLuong }}</td>
            <td>{{ thietbi.loai.soLuongCon }}</td>
            <td v-if=" thietbi.status == 1"> Đang xử dụng</td>
            <td v-if=" thietbi.status == 0">Chưa sử dụng</td>
            <td>
              <button type="button" class="btn btn-primary">
                <router-link
                  style="color: white"
                  :to="'/thietbi/update/' + thietbi.id"
                  >Update</router-link
                >
              </button>
            </td>
            <td>
              <button
                type="submit"
                @click="deleteTB(thietbi.id)"
                class="btn btn-danger"
              >Delete</button>
            </td>
          </tr>
        </tbody>
      </table>
    </header>
  </div>
</template>

<script>
import ThietBiService from '../services/thietbi.service';

export default {
  name: 'ThietBi',
  data() {
    return {
      listThietBi: [],
    };
  },
  mounted() {
    ThietBiService.getListThietBi().then(
      (response) => {
        window.console.log(response.data);
        this.listThietBi = response.data;
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
    deleteTB: function (id) {
      ThietBiService.deleteThietBi(id).then(response => {
        if(response.status == 200)
          alert("Xóa thành công")
        else alert("Xóa thất bại")
          location.reload()
      });
    },
  },
};
</script>
