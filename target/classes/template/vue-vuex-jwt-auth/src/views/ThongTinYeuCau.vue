<template>
  <div class="container">
    <header class="jumbotron">
      <div class="form-group">
        <label for="tenThietBi">Tên thiết bị</label>
        <input
          type="text"
          class="form-control"
          id="tenThietBi"
          placeholder="Tên thiết bị"
          v-model="muonTra.thietBi.tenThietBi"
          disabled
        />
        <span style="color: red"> {{ ErrTenTB }}</span>
      </div>
      <div class="form-group">
        <label for="hangsx">Hãng sản xuất</label>
        <input
          type="text"
          class="form-control"
          id="hangsx"
          placeholder="Hãng sản xuất"
          v-model="muonTra.thietBi.hangSX"
          disabled
        />
      </div>
      <div class="form-group row">
        <div for="example-date-input" class="col-12 col-form-label">
          Loại thiết bị
        </div>
      </div>
      <select
        v-model="muonTra.thietBi.loai"
        style="width: 14%; height: 33px"
        disabled
      >
        <option :value="loai" v-for="(loai, id) in listLoai" :key="id">
          {{ loai.tenLoai }}
        </option>
      </select>
      <div class="form-group row" disabled>
        <div
          for="example-date-input"
          class="col-12 col-form-label"
          style="margin-bottom: -10px; margin-top: 17px"
        >
          Trạng thái
        </div>
      </div>
      <div class="form-check">
        <input
          class="form-check-input"
          type="radio"
          name="status"
          id="status1"
          checked
          v-model="muonTra.thietBi.status"
          value="0"
          disabled
        />
        <span class="form-check-label" for="flexRadioDefault1"> True </span>
      </div>
      <div class="form-check">
        <input
          class="form-check-input"
          type="radio"
          name="status"
          id="status2"
          v-model="muonTra.thietBi.status"
          value="1"
          disabled
        />
        <span class="form-check-label" for="flexRadioDefault2"> False </span>
      </div>
      <div class="form-group">
        <label for="ns">Ngày trả</label>
        <input
          type="Date"
          class="form-control"
          id="ns"
          v-model="muonTra.ngayTra"
        />
      </div>
      <div class="form-group">
        <label for="hangsx">Lí do mượn</label>
        <input
          type="text"
          class="form-control"
          id="hangsx"
          placeholder="lí do"
          v-model="muonTra.lyDo"
        />
      </div>
      <button @click="yeuCauTB" type="submit" class="btn btn-primary">
        Gửi yêu cầu
      </button>
    </header>
  </div>
</template>

<script>
import ThietBiService from '../services/thietbi.service';
import YeuCauService from '../services/yeucau.service';
export default {
  name: 'ThietBi',
  data() {
    return {
      listLoai: [],
      ErrTenTB: '',
      muonTra: {
        thietBi: { tenThietBi: '', hangSX: '', loai: {}, status: 1 },
        ngayTra: '',
        lyDo: '',
      },
    };
  },
  mounted() {
    ThietBiService.getThietBi(this.$route.params.id).then(
      (response) => {
        this.muonTra.thietBi = response.data;
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
    ThietBiService.getLoaiThietBi().then((response) => {
      this.listLoai = response.data;
    });
  },
  watch: {
    listLoai() {
      this.thietBi.loai = this.listLoai[0];
    },
  },
  methods: {
    yeuCauTB() {
      YeuCauService.guiYeuCau(this.muonTra).then((response) => {
        if (response.status == 204) {
          alert('Cần chọn ngày trả và phải lớn hơn hoặc bằng ngày hiện tại');
        } else alert('Đã gửi yêu cầu');
      });
    },
  },
};
</script>
