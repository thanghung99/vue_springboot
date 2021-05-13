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
          v-model="thietBi.tenThietBi"
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
          v-model="thietBi.hangSX"
        />
      </div>
      <div class="form-group row">
        <div for="example-date-input" class="col-12 col-form-label">
          Loại thiết bị
        </div>
      </div>
      <select v-model="thietBi.loai" style="width: 14%; height: 33px">
        <option :value="loai" v-for="(loai, id) in listLoai" :key="id">
          {{ loai.tenLoai }}
        </option>
      </select>
      <div class="form-group row">
        <div for="example-date-input" class="col-12 col-form-label">
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
          v-model="thietBi.status"
          value="0"
        />
        <span class="form-check-label" for="flexRadioDefault1"> Chưa sử dụng </span>
      </div>
      <div class="form-check">
        <input
          class="form-check-input"
          type="radio"
          name="status"
          id="status2"
          v-model="thietBi.status"
          value="1"
        />
        <span class="form-check-label" for="flexRadioDefault2"> Đang sử dụng </span>
      </div>
      <button @click="updateTB" type="submit" class="btn btn-primary">
        UPDATE
      </button>
    </header>
  </div>
</template>

<script>
import ThietBiService from '../services/thietbi.service';

export default {
  name: 'ThietBi',
  data() {
    return {
      listLoai: [],
      thietBi: { tenThietBi: '', hangSX: '', loai: {}, status: 1 },
      ErrTenTB: '',
    };
  },
  mounted() {
    ThietBiService.getThietBi(this.$route.params.id).then(
      (response) => {
        this.thietBi = response.data;
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
    updateTB: function () {
      if (!this.thietBi.tenThietBi)
        return (this.ErrTenTB = 'Enter name device');
      ThietBiService.updateThietBi(this.$route.params.id, this.thietBi).then(
        (response) => {
          if (response.status == 200) alert('Update thành công');
          else alert('Update thất bại');
          location.reload();
        }
      );
    },
  },
};
</script>
