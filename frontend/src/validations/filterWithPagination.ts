import z from 'zod';

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
  operator: z.enum(['EQUALS', 'IN', 'BETWEEN', 'GTE', 'LTE', 'LIKE']),
  value: z.any(),
});

export const FilterWithPagination = z.object({
  filters: z.array(Filter).default([]),
  pagination: PaginationInput,
});

export type FilterWithPaginationInput = z.infer<typeof FilterWithPagination>;
export type FilterInput = z.infer<typeof Filter>;
export type PaginationInput = z.infer<typeof PaginationInput>;
