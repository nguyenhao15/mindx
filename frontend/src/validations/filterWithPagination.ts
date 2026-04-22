import z from 'zod';

export type FilterOperator =
  | 'LIKE'
  | 'EQUALS'
  | 'IN'
  | 'BETWEEN'
  | 'GTE'
  | 'LTE';
export interface FilterConfig {
  field: string;
  label: string;
  type: 'TEXT' | 'SELECT' | 'DATE';
  typeInput?: 'text' | 'email' | 'url' | 'number' | 'textarea';
  operator: FilterOperator;
  options?: { label: string; value: any }[]; // Cho SELECT
  isMultiple?: boolean;
}

const sortOrderSchema = z.object({
  property: z.string().default('id'),
  direction: z.enum(['ASC', 'DESC']).default('ASC'),
});

export const PaginationInput = z.object({
  page: z.number().default(0),
  size: z.number().default(12),
  sorts: z
    .array(sortOrderSchema)
    .default([{ property: 'createdDate', direction: 'ASC' }]),
});

export const Filter = z.object({
  field: z.string(),
  operator: z.enum(['LIKE', 'EQUALS', 'IN', 'BETWEEN', 'GTE', 'LTE']),
  value: z.any(),
});

export const FilterWithPagination = z.object({
  filters: z.array(Filter).default([]),
  pagination: PaginationInput,
});

export type FilterWithPaginationInput = z.infer<typeof FilterWithPagination>;
export type FilterInput = z.infer<typeof Filter>;
export type PaginationInput = z.infer<typeof PaginationInput>;
