import axios from 'axios';
import authHeader from './auth-header';

const API_URL = 'http://localhost:8080/api/';

class UserService {
  getPublicContent() {
    return axios.get(API_URL + 'greeting', { headers: authHeader() });
  }
  getListUser() {
    return axios.get(API_URL + 'taikhoan', { headers: authHeader() });
  }
  getUser(id) {
    return axios.get(API_URL + 'taikhoan/' + id + '', { headers: authHeader() });
  }
  getProfile() {
    return axios.get(API_URL + 'profile' + '', { headers: authHeader() });
  }
  updateUser(id, user, selectedFile) {
    const fd = new FormData();
    if (selectedFile.name != null) {
      window.console.log("no")
      fd.append('image', selectedFile, selectedFile.name)
    }
    fd.append('taikhoan', JSON.stringify(user))
    return axios.put(API_URL + 'taikhoan/' + id + '', fd, { headers: authHeader() });
  }
  updateProfile(user, selectedFile) {
    const fd = new FormData();
    if (selectedFile.name != null) {
      window.console.log("no")
      fd.append('image', selectedFile, selectedFile.name)
    }
    fd.append('taikhoan', JSON.stringify(user))
    return axios.put(API_URL + 'profile' , fd, { headers: authHeader() });
  }
  upload(selectedFile, user) {
    const fd = new FormData();
    window.console.log("ys = "+selectedFile)
    if (selectedFile != null) {
      window.console.log("no")
      fd.append('image', selectedFile, selectedFile.name)
    }
    fd.append('taikhoan', JSON.stringify(user))
    let tk = JSON.parse(localStorage.getItem('token'));
    return axios.post(API_URL + 'taikhoan', fd, {
      headers: {
        'Authorization': 'Bearer ' + tk.token
      }
    });
  }
  deleteUser(id) {
    return axios.delete(API_URL + 'taikhoan/' + id + '', { headers: authHeader() });
  }
  checkRole(){
    return axios.get(API_URL + 'role', { headers: authHeader() });
  }
}

export default new UserService();
