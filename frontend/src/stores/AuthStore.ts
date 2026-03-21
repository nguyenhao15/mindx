import { queryClient } from '@/lib/queryClient';
import type { UserDTO } from '@/validations/userSchema';

import { create } from 'zustand';

type AuthState = {
  user: UserDTO | null;
  accessToken?: string | null;
  setToken: (token: string | null) => void;
  setUser: (user: UserDTO | null) => void;
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
  queryClient.clear();
  localStorage.clear();
};
