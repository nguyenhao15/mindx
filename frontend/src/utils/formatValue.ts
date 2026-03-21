export const formatPrice = (amount: number, currency = 'VND') => {
  const numberAmount = Number(amount);
  if (isNaN(numberAmount)) return 'N/A';
  return new Intl.NumberFormat('vi-VN', {
    style: 'currency',
    currency: currency,
  }).format(numberAmount);
};

export const formatDate = (dateValue: string | number | Date) => {
  const handleDate = new Date(dateValue);
  if (isNaN(handleDate.getTime())) return 'N/A';
  return new Intl.DateTimeFormat('vi-VN', {
    day: '2-digit',
    month: '2-digit',
    year: 'numeric',
  }).format(handleDate);
};

export const formatDateTime = (dateString: string | number | Date) => {
  if (!dateString) return 'N/A';
  const date = new Date(dateString);
  if (isNaN(date.getTime())) return 'N/A';
  return new Intl.DateTimeFormat('vi-VN', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit',
    second: '2-digit',
  }).format(date);
};

export const formatFileSize = (bytes: string | number) => {
  const numberSize = Number(bytes);
  if (numberSize < 1024) return numberSize + ' B';
  if (numberSize < 1024 * 1024) return (numberSize / 1024).toFixed(1) + ' KB';
  return (numberSize / (1024 * 1024)).toFixed(1) + ' MB';
};

export const toArray = <T>(value: unknown): T[] => {
  if (Array.isArray(value)) {
    return value as T[];
  }

  if (!value || typeof value !== 'object') {
    return [];
  }

  const record = value as Record<string, unknown>;
  const candidates = ['content', 'items', 'result', 'results', 'data'];

  for (const key of candidates) {
    if (Array.isArray(record[key])) {
      return record[key] as T[];
    }
  }

  return [];
};

export const safeString = (value: unknown, fallback = '-') => {
  if (value === null || value === undefined) {
    return fallback;
  }

  const normalized = String(value).trim();
  return normalized.length > 0 ? normalized : fallback;
};
