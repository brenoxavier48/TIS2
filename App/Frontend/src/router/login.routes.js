export default [
  {
    name: 'login',
    path: '/login',
    component: () => import('../views/Login/login.vue')
  },
  {
    name: 'signup',
    path: '/signup',
    component: () => import('../views/Signup/signup.vue')
  }
];