import Vue from 'vue';
import {createStore} from 'vuex';



const store = createStore({
    state: {
        cart: [],
    },
    mutations: {
        addCourseID(state, id) {
            state.cart.push(id);

            localStorage.setItem('cart', JSON.stringify(state.cart));
        }
    },
    getters: {
        cartItemCount: state => state.cart.length
    },
    plugins: [store => {
        const cart = localStorage.getItem('cart');
        if (cart) {
            store.commit('addCourseID', JSON.parse(cart));
        }
    }]
});

export default store;