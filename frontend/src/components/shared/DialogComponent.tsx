import {
  Dialog,
  DialogContent,
  DialogOverlay,
  DialogPortal,
  DialogTitle,
} from '@/components/ui/dialog';

interface DialogComponentProps {
  open?: boolean;
  onClose?: () => void;
  children?: React.ReactNode;
  title?: string;
}

export function DialogComponent({
  open,
  onClose,
  children,
  title,
}: DialogComponentProps) {
  return (
    <Dialog
      defaultOpen={false}
      open={open}
      onOpenChange={(isOpen) => !isOpen && onClose && onClose()}
    >
      <DialogPortal>
        <DialogOverlay className='DialogOverlay'>
          <DialogContent
            aria-describedby={undefined}
            className='DialogContent w-screen'
          >
            <DialogTitle className='DialogTitle'>{title}</DialogTitle>
            {children}
          </DialogContent>
        </DialogOverlay>
      </DialogPortal>
    </Dialog>
  );
}
