import { createStore } from 'vuex'

declare let SessionStorage: any;
const USER = "USER";

const store = createStore({
  state: {
    user: SessionStorage.get(USER) || {}
  },
  mutations: {
    setUser(state, user) {
      console.log("store user:" + user.name);
      state.user = user;
      SessionStorage.set(USER, user);
      console.log("state user:" + state.user.name);
    }
  },
  actions: {
  },
  modules: {
  }
})
export default store;
