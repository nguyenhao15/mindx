import {
  createNewDepartmentAction,
  getAllDepartmentAction,
  getCurrentDepartmentAction,
  getDepartmentByIdAction,
  getActiveDepartmentAction,
  updateDepartmentAction,
  deleteDepartmentAction,
  getInSecureDepartmentAction,
  getCanAccessDepartmentsAction,
} from '@/actions/departmentAction';
import type { DepartmentFormData } from '@/validations/departmentSchema';
import type { FilterWithPaginationInput } from '@/validations/filterWithPagination';
import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import toast from 'react-hot-toast';

const DEPARTMENT_QUERY_KEY = ['departments'] as const;
const CURRENT_DEPARTMENT_QUERY_KEY = ['current-department'] as const;
const ACTIVE_DEPARTMENT_QUERY_KEY = ['active-departments'] as const;

export const useGetAllDepartments = (payload: FilterWithPaginationInput) => {
  return useQuery({
    queryKey: [...DEPARTMENT_QUERY_KEY, payload],
    queryFn: async () => {
      const response = await getAllDepartmentAction(payload);
      return response;
    },
    staleTime: 1000 * 60 * 15, // 15 minutes
    refetchOnMount: false,
    refetchOnWindowFocus: false,
    refetchOnReconnect: false,
    gcTime: 1000 * 60 * 30, // 30 minutes
    retry: false,
  });
};

export const useGetCurrentDepartment = () => {
  return useQuery({
    queryKey: CURRENT_DEPARTMENT_QUERY_KEY,
    queryFn: async () => {
      const response = await getCurrentDepartmentAction();
      return response;
    },
    staleTime: 1000 * 60 * 15, // 15 minutes
    refetchOnMount: false,
    refetchOnWindowFocus: false,
    refetchOnReconnect: false,
    gcTime: 1000 * 60 * 30, // 30 minutes
    retry: false,
  });
};

export const useGetDepartmentById = (id: string | null) => {
  return useQuery({
    queryKey: [...DEPARTMENT_QUERY_KEY, id],
    queryFn: async () => {
      if (!id) throw new Error('Department ID is required');
      const response = await getDepartmentByIdAction(id);
      return response;
    },
    enabled: !!id,
    staleTime: 1000 * 60 * 15,
    refetchOnMount: false,
    refetchOnWindowFocus: false,
    refetchOnReconnect: false,
    gcTime: 1000 * 60 * 30,
    retry: false,
  });
};

export const useGetActiveDepartments = () => {
  return useQuery({
    queryKey: ACTIVE_DEPARTMENT_QUERY_KEY,
    queryFn: async () => {
      const response = await getActiveDepartmentAction();
      return response;
    },
    staleTime: 1000 * 60 * 15, // 15 minutes
    refetchOnMount: false,
    refetchOnWindowFocus: false,
    refetchOnReconnect: false,
    gcTime: 1000 * 60 * 30, // 30 minutes
    retry: false,
  });
};

export const useCreateDepartment = () => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: async (data: DepartmentFormData) => {
      const response = await createNewDepartmentAction(data);
      return response;
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: DEPARTMENT_QUERY_KEY });
      queryClient.invalidateQueries({ queryKey: ACTIVE_DEPARTMENT_QUERY_KEY });
    },
    onError: (error) => {
      const errorMessage =
        (error as any)?.response?.data?.message ||
        'Failed to create department';
    },
  });
};

export const useUpdateDepartment = (id: string) => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: async (data: DepartmentFormData) => {
      const response = await updateDepartmentAction(id, data);
      return response;
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: DEPARTMENT_QUERY_KEY });
      queryClient.invalidateQueries({
        queryKey: [...DEPARTMENT_QUERY_KEY, id],
      });
      queryClient.invalidateQueries({ queryKey: ACTIVE_DEPARTMENT_QUERY_KEY });
      queryClient.invalidateQueries({
        queryKey: CURRENT_DEPARTMENT_QUERY_KEY,
      });
    },
    onError: (error) => {
      const errorMessage =
        (error as any)?.response?.data?.message ||
        'Failed to update department';
      toast.error(errorMessage);
    },
  });
};

export const useGetInSecureDepartments = (
  filterWithPagination: FilterWithPaginationInput,
) => {
  return useQuery({
    queryKey: [...DEPARTMENT_QUERY_KEY, 'insecure', filterWithPagination],
    queryFn: async () => {
      const response = await getInSecureDepartmentAction(filterWithPagination);
      return response;
    },
    staleTime: 1000 * 60 * 15, // 15 minutes
    refetchOnMount: false,
    refetchOnWindowFocus: false,
    refetchOnReconnect: false,
    gcTime: 1000 * 60 * 30, // 30 minutes
    retry: false,
  });
};

export const useDeleteDepartment = () => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: async (id: string) => {
      const response = await deleteDepartmentAction(id);
      return response;
    },
    onSuccess: (_, id) => {
      queryClient.invalidateQueries({ queryKey: DEPARTMENT_QUERY_KEY });
      queryClient.removeQueries({
        queryKey: [...DEPARTMENT_QUERY_KEY, id],
      });
      queryClient.invalidateQueries({ queryKey: ACTIVE_DEPARTMENT_QUERY_KEY });
      toast.success('Department deleted successfully');
    },
    onError: (error) => {
      const errorMessage =
        (error as any)?.response?.data?.message ||
        'Failed to delete department';
      toast.error(errorMessage);
    },
  });
};

export const useGetCanAccessDepartments = () => {
  return useQuery({
    queryKey: [...DEPARTMENT_QUERY_KEY, 'can-access'],
    queryFn: async () => {
      const response = await getCanAccessDepartmentsAction();
      return response;
    },
    staleTime: 1000 * 60 * 15, // 15 minutes
    refetchOnMount: false,
    refetchOnWindowFocus: false,
    refetchOnReconnect: false,
    gcTime: 1000 * 60 * 30, // 30 minutes
    retry: false,
  });
};
