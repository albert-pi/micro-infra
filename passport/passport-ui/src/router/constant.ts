/* eslint-disable prefer-template */

import DefaultLayout from '@/layout/default-layout.vue';
import { RouteRecordRaw } from 'vue-router';

export default [
  {
    path: '/',
    redirect: `${import.meta.env.VITE_CONTEXT}login`,
  },
  {
    path: import.meta.env.VITE_CONTEXT,
    redirect: { path: `${import.meta.env.VITE_CONTEXT}login` },
  },
  {
    path: import.meta.env.VITE_CONTEXT + 'login',
    name: 'login',
    component: () => import('@/views/login/index.vue'),
    meta: {
      requiresAuth: false,
    },
  },
] as RouteRecordRaw[];
