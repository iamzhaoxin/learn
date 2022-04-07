import {createStore} from 'vuex'

// Create a new store instance.
const store = createStore({
    state() {
        return {
            autoLogin: false
        }
    },
    mutations: {
        changeAutoLogin() {
            console.log("autoLogin has changed")
            !this.state.autoLogin
        }
    }
})

export default store