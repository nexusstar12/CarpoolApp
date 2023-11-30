import Axios from "axios";
const axiosInstance = Axios.create({
  baseURL: process.env.REACT_APP_BASE_URL,
  headers: {
    "Content-type": "application/json",
  },
});

export default axiosInstance;
