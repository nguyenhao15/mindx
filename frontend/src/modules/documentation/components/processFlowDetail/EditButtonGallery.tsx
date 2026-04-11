import { Button } from '../ui/button';

interface EditButtonGalleryProps {
  handleOnBack: () => void;
  handleEdit: () => void;
  handleUpdate: () => void;
  editMode: boolean;
}

const EditButtonGallery = ({
  handleOnBack,
  handleEdit,
  handleUpdate,
  editMode,
}: EditButtonGalleryProps) => {
  return (
    <div className='self-start w-full flex-row gap-2 flex justify-end mb-4'>
      <Button
        className='self-center cursor-pointer'
        variant={'default'}
        onClick={handleOnBack}
      >
        Quay lại
      </Button>
      <Button
        className='self-center cursor-pointer'
        variant={'secondary'}
        onClick={handleEdit}
      >
        {!editMode ? 'Chỉnh sửa' : 'Xem'}
      </Button>
      {editMode ? (
        <Button
          onClick={handleUpdate}
          className='self-center cursor-pointer'
          variant={'positive'}
        >
          Lưu
        </Button>
      ) : null}
    </div>
  );
};

export default EditButtonGallery;
