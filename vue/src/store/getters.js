export default {
    getUserName: state => state.userName,
    getToken: state => state.token,
    isLogin(state) {
        return state.token != null;
    }
}