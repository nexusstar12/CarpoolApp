import Axios from "axios";
// const token = JSON.parse(localStorage.getItem("userInfo")).jwtToken;
// console.log("token", token);
const axiosInstance = Axios.create({
  baseURL: process.env.REACT_APP_BASE_URL,
  headers: {
    "Content-type": "application/json",
    // Authorization: `Bearer ${token}`,
  },
});

export default axiosInstance;
