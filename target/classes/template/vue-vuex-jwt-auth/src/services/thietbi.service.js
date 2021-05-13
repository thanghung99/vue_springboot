import axios from 'axios';
import authHeader from './auth-header';

const API_URL = 'http://localhost:8080/api/';

class ThietBiService {
  getListThietBi() {
    return axios.get(API_URL + 'thietbi',{ headers:authHeader()});
  }
  getListThietBiMuon() {
    return axios.get(API_URL + 'thietbi/muon',{ headers:authHeader()});
  }
  deleteThietBi(id){
    return axios.delete(API_URL + 'thietbi/'+id+'',{headers : authHeader()})
  }
  getLoaiThietBi(){
    return axios.get(API_URL +"loai", {headers : authHeader()})
  }
  addThietBi(thietBi){
    window.console.log("test :"+thietBi.loai.id)
    return axios.post(API_URL +"thietbi",thietBi, {headers : authHeader()})
  }
  getThietBi(id) {
    return axios.get(API_URL + 'thietbi/'+id+'',{ headers:authHeader()});
  }
  getThietBiByLoai(id) {
    return axios.get(API_URL + 'thietbi/loai/'+id+'',{ headers:authHeader()});
  }
  updateThietBi(id,thietBi) {
    return axios.put(API_URL + 'thietbi/'+id+'',thietBi,{ headers:authHeader()});
  }
  assignThietBi(muonTra){
    return axios.post(API_URL + 'thietbi/assign',muonTra,{headers:authHeader()});
  }
}

export default new ThietBiService();
