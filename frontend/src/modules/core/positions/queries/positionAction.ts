import { positionApi } from '@/modules/core/positions/api/positionApi';
import type { FilterWithPaginationInput } from '@/validations/filterWithPagination';
import {
  PositionResponseArray,
  PositionResponseObj,
  type PositionFormData,
} from '@/modules/core/positions/schemas/positionSchema';

export const createPositionAction = async (data: PositionFormData) => {
  const response = await positionApi.createPosition(data);
  return response.data;
};

export const getPositionsAction = async (
  payload: FilterWithPaginationInput,
) => {
  const response = await positionApi.getAllPositions(payload);
  const { content, ...pagination } = response.data;
  const results = PositionResponseArray.safeParse(response.data.content);
  if (!results.success) {
    console.warn('API Data Mismatch:', results.error);
    return response.data; // Vẫn trả về data cũ nếu bạn muốn "sống chung với lũ"
  }
  const finalData = {
    pagination,
    content: results,
  };
  return finalData;
};

export const getPositionByActiveAction = async (active: boolean) => {
  const response = await positionApi.getPositionByActive(active);
  const results = PositionResponseArray.parse(response.data);
  return results;
};

export const getPositionByDepartmentIdAction = async (
  departmentCode: string,
) => {
  const response = await positionApi.getPositionByDepartmentiD(departmentCode);
  const results = PositionResponseArray.parse(response.data);
  return results;
};

export const getCurrentPositionAction = async () => {
  const response = await positionApi.getCurrentPosition();
  const results = PositionResponseArray.parse(response.data);
  return results;
};

export const getPositionByIdAction = async (id: string) => {
  const response = await positionApi.getPositionById(id);
  const results = PositionResponseObj.parse(response.data);
  return results;
};

export const updatePositionAction = async (
  id: string,
  data: PositionFormData,
) => {
  const response = await positionApi.updatePosition(id, data);
  const results = PositionResponseObj.parse(response.data);
  return results;
};

export const deletePositionAction = async (id: string) => {
  const response = await positionApi.deletePosition(id);
  const results = PositionResponseObj.parse(response.data);
  return results;
};
