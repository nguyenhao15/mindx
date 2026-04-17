import React, { Fragment } from 'react';
import {
  Dialog,
  DialogPanel,
  Transition,
  TransitionChild,
} from '@headlessui/react';

type ModalRenderProps = {
  portalContainer: HTMLElement | null;
};

interface ModalComponentProps {
  open: boolean;
  onClose?: () => void;
  children: React.ReactNode | ((props: ModalRenderProps) => React.ReactNode);
}

const ModalComponent = ({ open, onClose, children }: ModalComponentProps) => {
  const [portalContainer, setPortalContainer] =
    React.useState<HTMLElement | null>(null);
  const renderedChildren =
    typeof children === 'function'
      ? (children as (props: ModalRenderProps) => React.ReactNode)({
          portalContainer,
        })
      : children;
  return (
    <Transition show={open} as={Fragment}>
      <Dialog
        as='div'
        className='relative '
        onClose={onClose ? onClose : () => {}}
        static
      >
        {/* Backdrop: Làm mờ nền */}
        <TransitionChild
          as={Fragment}
          enter='ease-out duration-300'
          enterFrom='opacity-0'
          enterTo='opacity-100'
          leave='ease-in duration-200'
          leaveFrom='opacity-100'
          leaveTo='opacity-0'
        >
          <div
            className='fixed inset-0 bg-black/30 backdrop-blur-sm'
            aria-hidden='true'
          />
        </TransitionChild>

        {/* Full-screen container */}
        <div className='fixed inset-0' ref={setPortalContainer}>
          <div className='flex h-full items-center justify-center p-4'>
            <TransitionChild as={Fragment}>
              <DialogPanel
                className={`h-fit max-h-[95vh] overflow-y-scroll rounded`}
              >
                {renderedChildren}
              </DialogPanel>
            </TransitionChild>
          </div>
        </div>
      </Dialog>
    </Transition>
  );
};

export default ModalComponent;
