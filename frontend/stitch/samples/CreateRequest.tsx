import React, { useState } from 'react';
import { UploadCloud, ChevronLeft } from 'lucide-react';

export default function CreateRequest() {
  const [dragActive, setDragActive] = useState(false);

  return (
    <div className="min-h-screen bg-slate-50 font-sans p-8 flex justify-center">
      <div className="max-w-3xl w-full">
        <a href="#" className="flex items-center gap-2 text-slate-500 hover:text-[#1d3557] mb-6 transition">
          <ChevronLeft size={20} /> Back to Dashboard
        </a>
        
        <h1 className="text-3xl font-bold text-slate-800 mb-8">New Repair Request</h1>
        
        <form className="bg-white p-8 rounded-2xl border border-slate-100 flex flex-col gap-6">
          
          <div className="grid grid-cols-2 gap-6">
            <div className="flex flex-col gap-2">
              <label className="text-sm font-medium text-slate-700">Category</label>
              <select className="p-3 bg-slate-50 border border-slate-200 rounded-lg text-slate-800 focus:outline-none focus:ring-2 focus:ring-[#1d3557]/20 transition">
                <option>Electrical</option>
                <option>Plumbing</option>
                <option>HVAC</option>
                <option>Furniture</option>
              </select>
            </div>
            <div className="flex flex-col gap-2">
              <label className="text-sm font-medium text-slate-700">Sub-Category</label>
              <select className="p-3 bg-slate-50 border border-slate-200 rounded-lg text-slate-800 focus:outline-none focus:ring-2 focus:ring-[#1d3557]/20 transition">
                <option>Lighting Fixture</option>
                <option>Power Outlet</option>
                <option>Wiring</option>
              </select>
            </div>
          </div>

          <div className="flex flex-col gap-2">
            <label className="text-sm font-medium text-slate-700">Facility / Location</label>
            <input type="text" placeholder="E.g. Main Office, Floor 3" className="p-3 bg-slate-50 border border-slate-200 rounded-lg text-slate-800 focus:outline-none focus:ring-2 focus:ring-[#1d3557]/20 transition" />
          </div>

          <div className="flex flex-col gap-2">
            <label className="text-sm font-medium text-slate-700">Description</label>
            <textarea rows={4} placeholder="Describe the issue in detail..." className="p-3 bg-slate-50 border border-slate-200 rounded-lg text-slate-800 focus:outline-none focus:ring-2 focus:ring-[#1d3557]/20 transition resize-none"></textarea>
          </div>

          <div className="flex flex-col gap-2">
            <label className="text-sm font-medium text-slate-700">Attachments (Images/Videos)</label>
            <div 
              className={`border-2 border-dashed rounded-2xl p-10 flex flex-col items-center justify-center transition-colors ${dragActive ? 'border-[#1d3557] bg-blue-50/50' : 'border-slate-200 bg-slate-50 hover:bg-slate-100/50'}`}
              onDragEnter={() => setDragActive(true)}
              onDragLeave={() => setDragActive(false)}
              onDrop={() => setDragActive(false)}
            >
              <UploadCloud size={40} className="text-slate-400 mb-4" />
              <p className="text-slate-600 font-medium">Drag & drop files here</p>
              <p className="text-slate-400 text-sm mt-1">or click to browse</p>
            </div>
          </div>

          <div className="pt-4 border-t border-slate-100 flex justify-end gap-4 mt-2">
            <button type="button" className="px-6 py-3 text-slate-600 font-medium hover:bg-slate-100 rounded-xl transition">
              Cancel
            </button>
            <button type="button" className="px-6 py-3 bg-[#1d3557] text-white font-medium rounded-xl hover:bg-[#152744] transition shadow-md">
              Submit Request
            </button>
          </div>

        </form>
      </div>
    </div>
  );
}
