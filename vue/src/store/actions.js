import router from "@/router";

export default {
    login: function (state, payload) {
        state.commit('setUserId', payload.id)
        state.commit('setUsername', payload.name)
        state.commit('setToken', payload.grantType + " " + payload.accessToken)
        localStorage.setItem('userId', payload.id)
        localStorage.setItem('userName', payload.name)
        localStorage.setItem('token', payload.grantType + " " + payload.accessToken)
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
