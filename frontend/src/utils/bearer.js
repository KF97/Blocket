import axios from "axios";

// axios 객체 생성
export default axios.create({
  baseURL: "http://3.34.191.232:8000/",
  headers: {
    "Content-type": "application/json",
    Authorization : `Bearer ${localStorage.getItem('accessToken')}`
  },
});
