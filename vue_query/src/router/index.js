import Vue from 'vue'
import Router from 'vue-router'

const login = r =>
  require.ensure(
    [],
    () => r(require('../page/login')),
    'login'
  )
const account = r =>
  require.ensure(
    [],
    () => r(require('../page/account')),
    'account'
  )
const article = r =>
  require.ensure(
    [],
    () => r(require('../page/article')),
    'article'
  )
const theme = r =>
  require.ensure(
    [],
    () => r(require('../page/theme')),
    'theme'
  )
const home = r =>
  require.ensure(
    [],
    () => r(require('../page/home')),
    'home'
  )

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/login',
      name: 'login',
      component: login
    },
    {
      path: '/home',
      name: 'home',
      component: home,
      redirect: '/home',
      children: [
        {
          path: '/account',
          name: 'account',
          component: account
        },
        {
          path: '/article',
          name: 'article',
          component: article
        },
        {
          path: '/theme',
          name: 'theme',
          component: theme
        }
      ]
    }
  ]
})
