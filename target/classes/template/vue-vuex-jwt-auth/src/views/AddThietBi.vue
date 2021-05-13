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
        <span class="form-check-label" for="flexRadioDefault1"> True </span>
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
        <span class="form-check-label" for="flexRadioDefault2"> False </span>
      </div>
      <button @click="addTB" type="submit" class="btn btn-primary">
        ADD
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
      this.thietBi.loai = this.listLoai[0];
    },
  },
  methods: {
    addTB: function () {
      if (!this.thietBi.tenThietBi)
        return (this.ErrTenTB = 'Enter name device');
      ThietBiService.addThietBi(this.thietBi).then((response) => {
        if (response.status == 201) alert('Thêm thành công');
        else alert('Thêm thất bại');
        location.reload();
      });
    },
  },
};
</script>
