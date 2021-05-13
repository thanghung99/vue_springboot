<template>
  <div class="container">
    <header class="jumbotron">
      <div class="input-group" style="width: 28%; margin-bottom: 40px">
        <input
          type="search"
          class="form-control rounded"
          placeholder="Search"
          aria-label="Search"
          aria-describedby="search-addon"
          v-model="thietbiSearch"
        />
        <button type="button" class="btn btn-outline-primary" @click="search">search</button>
      </div>

      <table class="table">
        <thead>
          <tr>
            <th scope="col">Tên thiết bị</th>
            <th scope="col">Hãng sản xuất</th>
            <th scope="col">Loại</th>
            <th scope="col">Tổng số lượng</th>
            <th scope="col">Số lượng còn</th>
            <th scope="col">Mượn</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(thietbi, id) in listThietBi" :key="id">
            <th scope="row">{{ thietbi.tenThietBi }}</th>
            <td>{{ thietbi.hangSX }}</td>
            <td>{{ thietbi.loai.tenLoai }}</td>
            <td>{{ thietbi.loai.soLuong }}</td>
            <td>{{ thietbi.loai.soLuongCon }}</td>
            <td>
              <button type="button" class="btn btn-primary">
                <router-link
                  style="color: white"
                  :to="'/thietbi/yeu-cau-thiet-bi/' + thietbi.id"
                  >Mượn</router-link
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
import YeuCauService from '../services/yeucau.service';
import ThietBiService from '../services/thietbi.service';
export default {
  name: 'ThietBi',
  data() {
    return {
      listThietBi: [],
      thietbiSearch:'',
    };
  },
  mounted() {

    ThietBiService.getListThietBiMuon().then(
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
    search(){
        YeuCauService.searchThietBi(this.thietbiSearch).then(response =>{
           this.listThietBi = response.data;
        })
    }
  },
};
</script>
