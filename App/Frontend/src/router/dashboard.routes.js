export default [
  { name: 'dashboard',
    path: '/dashboard', 
    component: () => import('../views/Dashboard/dashboard.vue') }, 
    { name: 'userTasks', 
    path: '/user-tasks', 
    component: () => import('../views/Dashboard/listUserTasks.vue') 
  }];