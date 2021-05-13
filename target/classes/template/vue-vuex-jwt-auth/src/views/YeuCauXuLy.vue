<template>
  <div class="container">
    <header class="jumbotron">
      <!-- <div class="input-group" style="width: 28%; margin-bottom: 40px">
        <input
          type="search"
          class="form-control rounded"
          placeholder="Search"
          aria-label="Search"
          aria-describedby="search-addon"
        />
        <button type="button" class="btn btn-outline-primary">search</button>
      </div> -->

      <table class="table table-responsive  table-striped">
        <thead>
          <tr>
            <th scope="col">Tên thiết bị</th>
            <th scope="col">Hãng</th>
            <th scope="col">Loại</th>
            <th scope="col">Số lượng</th>
            <th scope="col">Số lượng còn</th>
            <th scope="col">Người mượn</th>
            <th scope="col">Ngày mượn</th>
            <th scope="col">Ngày trả</th>
            <th scope="col">Lý do</th>
            <th scope="col">Thao tác</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(yeucau, id) in listYeuCau" :key="id">
            <th scope="row">{{ yeucau.thietBi.tenThietBi }}</th>
            <td>{{ yeucau.thietBi.hangSX }}</td>
            <td>{{ yeucau.thietBi.loai.tenLoai }}</td>
            <td>{{ yeucau.thietBi.loai.soLuong }}</td>
            <td>{{ yeucau.thietBi.loai.soLuongCon }}</td>
            <td>{{ yeucau.taiKhoan.tenNguoiDung }}</td>
            <td>{{ yeucau.ngayMuon | date }}</td>
            <td>{{ yeucau.ngayTra | date }}</td>
            <td>{{ yeucau.lyDo }}</td>
            <td>
              <button
                type="button"
                class="btn btn-primary"
                @click="dongY(yeucau)"
              >
                Đồng ý
              </button>
              <button
                type="button"
                class="btn btn-danger"
                @click="huyBo(yeucau)"
              >
                Hủy bỏ
              </button>
            </td>
          </tr>
        </tbody>
      </table>
    </header>
  </div>
</template>

<script>
import YeuCauService from '../services/yeucau.service';
export default {
  name: 'ThietBi',
  data() {
    return {
      listYeuCau: [],
    };
  },
  mounted() {
    YeuCauService.listYeuCau().then((response) => {
      window.console.log(response.data);
      this.listYeuCau = response.data;
    });
  },

  methods: {
    dongY(yeucau) {
      YeuCauService.dongY(yeucau).then((response) => {
        if(response.status == 200)
            alert("Đồng ý yêu cầu")
            location.reload();
      });
    },
    huyBo(yeucau) {
      YeuCauService.huyBo(yeucau).then((response) => {
        if(response.status == 200)
            alert("Hủy bỏ yêu cầu")
            location.reload();
      });
    },
  },
};
</script>
