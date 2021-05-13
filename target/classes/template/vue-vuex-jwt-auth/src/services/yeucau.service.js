import axios from 'axios';
import authHeader from './auth-header';

const API_URL = 'http://localhost:8080/api/';

class YeuCauService {
  searchThietBi(name) {
    let config = {
      headers: authHeader(),
      params: {
        valueSearch: name
      },
    }
    return axios.get(API_URL + 'thietbi/search', config);
  }
  guiYeuCau(yeucau){
    return axios.post(API_URL +'thietbi/muon-thiet-bi',yeucau, { headers: authHeader() })
  }
  listYeuCau(){
    return axios.get(API_URL + 'thietbi/yeu-cau-xu-ly',{ headers: authHeader() })
  }
  listThietBiUser(){
    return axios.get(API_URL + 'thietbi/danh-sach-thiet-bi-user',{ headers: authHeader() })
  }
  listYeuCauByUser(){
    return axios.get(API_URL + 'thietbi/danh-sach-yeu-cau',{ headers: authHeader() })
  }
  dongY(muonTra){
    return axios.post(API_URL + 'thietbi/yeu-cau-xu-ly/dongy',muonTra,{ headers: authHeader() })
  }
  huyBo(muonTra){
    return axios.post(API_URL + 'thietbi/yeu-cau-xu-ly/huybo',muonTra,{ headers: authHeader() })
  }
}

export default new YeuCauService();
