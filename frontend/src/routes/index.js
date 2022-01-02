import Vue from 'vue';
import VueRouter from 'vue-router';

Vue.use(VueRouter);

export default new VueRouter({
	mode: 'history',
	routes: [
		{
			path: '/',
			redirect: '/login',
		},
		{
			path: '/login',
			component: () => import('@/views/LoginPage'),
		},
		{
			path: '/signup',
			component: () => import('@/views/SignupPage'),
		},
		{
			path: '*',
			component: () => import('@/views/NotFoundPage'),
		},
	],
});
