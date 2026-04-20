import { queryClient } from '@/lib/queryClient';
import type { UserResponseObjectType } from '@/modules/core/auth/schemas/userSchema';

import { create } from 'zustand';

type AuthState = {
  user: UserResponseObjectType | null;
  accessToken?: string | null;
  setToken: (token: string | null) => void;
  setUser: (user: UserResponseObjectType | null) => void;
  logOut: () => void;
};

export const useAuthStore = create<AuthState>((set) => ({
  user: null,
  accessToken: null,
  setUser: (user) => set({ user }),
  setToken: (token) => set({ accessToken: token }),
  logOut: () => set({ user: null, accessToken: null }),
}));

export const handleLogout = () => {
  const { logOut } = useAuthStore.getState();
  logOut();
  localStorage.removeItem('isLoggedIn');
  localStorage.removeItem('profile_id');
  queryClient.clear();
  localStorage.clear();
};
