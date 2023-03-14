import Axios from "axios";
import store from "@/store/store";

const AxiosInst = Axios.create({
    baseURL: "http://localhost:8081"
})

AxiosInst.interceptors.request.use(
    (config) => {
        const access_token = store.getters.getToken;
        if (access_token) {
            config.headers.Authorization = `${access_token}`;
        }
        config.headers["Access-Control-Allow-Origin"] = "*";
        config.headers["Access-Control-Allow-Credentials"] = true;
        return config;
    }
)
export default AxiosInst;