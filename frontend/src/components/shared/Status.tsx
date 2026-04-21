const Status = ({
  status,
  className = '',
}: {
  status: string;
  className?: string;
}) => {
  const getStatusStyle = (s: string) => {
    switch (s) {
      case 'WAITING':
        return 'bg-yellow-50 text-yellow-700 border-yellow-200';
      case 'APPROVED':
        return 'bg-green-50 text-green-700 border-green-200';
      case 'ACTIVE':
        return 'bg-emerald-50 text-emerald-700 border-emerald-200';
      case 'EXPORTED':
        return 'bg-green-100 text-green-600 border-green-200';
      case 'PENDING':
        return 'bg-amber-50 text-amber-700 border-amber-200';
      case 'INACTIVE':
        return 'bg-red-50 text-red-700 border-red-200';
      case 'LOCKED':
        return 'bg-red-50 text-red-700 border-red-200';
      case 'CONFIRMED':
        return 'bg-green-50 text-green-700 border-green-200';
      case 'DRAFT':
        return 'bg-blue-50 text-blue-700 border-blue-200';
      case 'INITIAL':
        return 'bg-slate-100 text-slate-600 border-slate-200';
      case 'COMPLETED':
        return 'bg-green-100 text-green-700 border-green-200';
      case 'PAID':
        return 'bg-green-100 text-green-700 border-green-200';
      default:
        return 'bg-primary/10 text-primary border-primary/20';
    }
  };

  return (
    <span
      className={`
        inline-flex items-center px-2.5 py-0.5 rounded-md text-xs font-medium border
        ${getStatusStyle(status.toUpperCase())}
        ${className}
      `}
    >
      {status}
    </span>
  );
};

export default Status;
