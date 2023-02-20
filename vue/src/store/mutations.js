import router from "@/router";

export default {
    setUsername(state, userName) {
        state.userName = userName
    },
    setToken(state, token) {
        state.token = token;
    },
    login: function (state, payload) {
        state.userName = payload.name
        state.token = payload.grantType + " " + payload.accessToken
    },
    loginCheck: function (state) {
        if (!state.token) {
            router.push("/login")
                .catch(error => {
                    console.log(error)
                })
        }
    }
}
