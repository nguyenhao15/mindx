import React from 'react';
import {
  ChevronLeft,
  CheckCircle2,
  Clock,
  MessageSquare,
  ToolCase,
} from 'lucide-react';

export default function TicketDetail() {
  return (
    <div className='min-h-screen bg-slate-50 font-sans p-8 flex justify-center'>
      <div className='max-w-5xl w-full'>
        {/* Header */}
        <div className='flex items-center justify-between mb-8'>
          <div className='flex items-center gap-4'>
            <a
              href='#'
              className='p-2 bg-white rounded-lg border border-slate-200 text-slate-500 hover:text-[#1d3557] transition'
            >
              <ChevronLeft size={20} />
            </a>
            <div>
              <h1 className='text-2xl font-bold text-slate-800'>
                AC Unit Malfunction
              </h1>
              <p className='text-slate-500 text-sm mt-1'>
                REQ-001 • Main Office • Oct 24, 2026
              </p>
            </div>
          </div>
          <span className='px-4 py-2 bg-blue-100 text-blue-700 rounded-full text-sm font-semibold flex items-center gap-2'>
            <ToolCase size={16} /> In Progress
          </span>
        </div>

        {/* Progress Stepper */}
        <div className='bg-white p-6 rounded-2xl border border-slate-100 mb-8 flex justify-between items-center relative'>
          <div className='absolute top-1/2 left-10 right-10 h-0.5 bg-slate-100 -z-10'></div>
          <div className='absolute top-1/2 left-10 w-1/2 h-0.5 bg-green-500 -z-10'></div>

          {[
            'Pending',
            'In Review',
            'Repairing',
            'Quality Check',
            'Completed',
          ].map((step, i) => (
            <div
              key={i}
              className='flex flex-col items-center gap-2 bg-white px-4'
            >
              <div
                className={`w-8 h-8 rounded-full flex items-center justify-center border-2 ${i < 2 ? 'bg-green-500 border-green-500 text-white' : i === 2 ? 'bg-blue-500 border-blue-500 text-white' : 'bg-white border-slate-200 text-slate-400'}`}
              >
                {i < 2 ? <CheckCircle2 size={16} /> : i + 1}
              </div>
              <span
                className={`text-sm font-medium ${i <= 2 ? 'text-slate-800' : 'text-slate-400'}`}
              >
                {step}
              </span>
            </div>
          ))}
        </div>

        <div className='grid grid-cols-3 gap-8'>
          {/* Main Column */}
          <div className='col-span-2 space-y-8'>
            {/* Description & Media */}
            <div className='bg-white p-6 rounded-2xl border border-slate-100'>
              <h2 className='text-lg font-bold text-slate-800 mb-4'>
                Issue Details
              </h2>
              <p className='text-slate-600 mb-6 leading-relaxed'>
                The main AC unit on the 3rd floor is making a loud grinding
                noise and stops blowing cold air after 15 minutes of operation.
                Water is also slightly leaking from the bottom tray.
              </p>

              <div className='grid grid-cols-2 gap-4'>
                <div>
                  <h3 className='text-sm font-medium text-slate-500 mb-2'>
                    Before (Reported)
                  </h3>
                  <div className='grid grid-cols-2 gap-2'>
                    <div className='h-32 bg-slate-200 rounded-xl'></div>
                    <div className='h-32 bg-slate-200 rounded-xl'></div>
                  </div>
                </div>
                <div>
                  <h3 className='text-sm font-medium text-slate-500 mb-2'>
                    After (Acceptance)
                  </h3>
                  <div className='h-32 border-2 border-dashed border-slate-200 rounded-xl flex items-center justify-center text-slate-400'>
                    Awaiting updates
                  </div>
                </div>
              </div>
            </div>

            {/* Repair Solutions */}
            <div className='bg-white p-6 rounded-2xl border border-slate-100'>
              <h2 className='text-lg font-bold text-slate-800 mb-4'>
                Proposed Solutions
              </h2>
              <div className='p-4 bg-slate-50 border border-slate-100 rounded-xl flex gap-4'>
                <div className='p-3 bg-[#1d3557]/10 text-[#1d3557] rounded-lg self-start'>
                  <ToolCase size={20} />
                </div>
                <div>
                  <h3 className='font-bold text-slate-800'>
                    Motor Replacement & Tray Cleaning
                  </h3>
                  <p className='text-slate-600 text-sm mt-1'>
                    The internal fan motor is worn out causing the grinding
                    noise. The drainage tray is clogged causing the leak.
                  </p>
                  <div className='flex gap-4 mt-3'>
                    <span className='text-sm text-slate-500 flex items-center gap-1'>
                      <Clock size={16} /> Est. 2 Hours
                    </span>
                    <span className='text-sm text-slate-500 font-medium bg-white px-2 py-1 rounded border border-slate-200'>
                      1x AC Motor 500W
                    </span>
                  </div>
                </div>
              </div>
            </div>
          </div>

          {/* Activity Timeline Column */}
          <div className='col-span-1'>
            <div className='bg-white p-6 rounded-2xl border border-slate-100 sticky top-8'>
              <h2 className='text-lg font-bold text-slate-800 mb-6 flex items-center gap-2'>
                <MessageSquare size={20} className='text-slate-400' /> Activity
                Timeline
              </h2>

              <div className='relative border-l-2 border-slate-100 ml-3 space-y-8'>
                <div className='relative pl-6'>
                  <div className='absolute -left-[9px] top-1 w-4 h-4 rounded-full bg-blue-500 ring-4 ring-white'></div>
                  <div className='flex justify-between items-start mb-1'>
                    <span className='font-bold text-slate-800 text-sm'>
                      Tech Team
                    </span>
                    <span className='text-xs text-slate-400'>
                      Today, 09:30 AM
                    </span>
                  </div>
                  <p className='text-sm text-slate-600'>
                    Started repairing the unit. Motor has been removed.
                  </p>
                </div>

                <div className='relative pl-6'>
                  <div className='absolute -left-[9px] top-1 w-4 h-4 rounded-full bg-slate-300 ring-4 ring-white'></div>
                  <div className='flex justify-between items-start mb-1'>
                    <span className='font-bold text-slate-800 text-sm'>
                      Facility Manager
                    </span>
                    <span className='text-xs text-slate-400'>
                      Yesterday, 14:15 PM
                    </span>
                  </div>
                  <p className='text-sm text-slate-600'>
                    Approved the proposed solution. Proceed with repair.
                  </p>
                </div>

                <div className='relative pl-6'>
                  <div className='absolute -left-[9px] top-1 w-4 h-4 rounded-full bg-slate-300 ring-4 ring-white'></div>
                  <div className='flex justify-between items-start mb-1'>
                    <span className='font-bold text-slate-800 text-sm'>
                      Sarah (Reporter)
                    </span>
                    <span className='text-xs text-slate-400'>
                      Yesterday, 10:00 AM
                    </span>
                  </div>
                  <p className='text-sm text-slate-600'>
                    Submitted the repair request with 2 photos.
                  </p>
                </div>
              </div>

              <div className='mt-6 flex gap-2'>
                <input
                  type='text'
                  placeholder='Add an update...'
                  className='w-full p-3 bg-slate-50 border border-slate-200 rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-[#1d3557]/20'
                />
                <button className='p-3 bg-[#1d3557] text-white rounded-lg hover:bg-[#152744] transition'>
                  <MessageSquare size={18} />
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
