import {createStore} from 'vuex';

export default createStore({
    state: {
        cart: [],
    },
    mutations: {
        addCourseID(state, id) {
            state.cart.push(id);
        }
    },
    getters: {
        cartItemCount: state => state.cart.length
    }
});