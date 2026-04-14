const InfoComponents = () => {
  return (
    <div className='w-full p-4 bg-slate-200 rounded-lg shadow'>
      <h2 className='text-xl font-semibold'>Thông tin lưu ý</h2>
      <div className='space-y-4'>
        <div className='flex gap-4'>
          <div className='shrink-0 w-8 h-8 rounded-lg bg-primary-container/10 flex items-center justify-center text-primary-container'>
            <span className='text-sm font-bold'>1</span>
          </div>
          <p className='text-sm text-on-surface-variant font-medium leading-relaxed'>
            Đảm bảo hình ảnh rõ ràng, hiển thị được mức độ hư hỏng thực tế.
          </p>
        </div>
        <div className='flex gap-4'>
          <div className='shrink-0 w-8 h-8 rounded-lg bg-primary-container/10 flex items-center justify-center text-primary-container'>
            <span className='text-sm font-bold'>2</span>
          </div>
          <p className='text-sm text-on-surface-variant font-medium leading-relaxed'>
            Các đề xuất sửa chữa khẩn cấp vui lòng liên hệ hotline kỹ thuật sau
            khi gửi đơn.
          </p>
        </div>
      </div>
    </div>
  );
};

export default InfoComponents;
