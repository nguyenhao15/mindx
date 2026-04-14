# UI/UX Design Prompt: Minimalist Internal Maintenance & Repair Management System

**Project Goal:** Design a minimalist, high-efficiency Internal Repair Management Dashboard. The system facilitates the end-to-end lifecycle of facility maintenance, from initial request to progress tracking, professional assessment, and comprehensive reporting.

## Visual Identity & Aesthetic

- **Primary Color:** `#1d3557` (Deep Navy - used for primary buttons, sidebar background, and critical status indicators).
- **Style:** Minimalist & Clean UI. Focus on high readability, generous whitespace (high-gap layout), and organized data structures.
- **Edge Styling:** Implement Rounded Corners (e.g., `rounded-2xl` for cards, `rounded-lg` for inputs) across all components for a modern, approachable feel.
- **Color Harmony:** Balance the deep navy with soft slate grays (`#f8fafc`) and functional accent colors (Success: Green, Warning: Amber, Info: Sky Blue).

## Core Module Requirements

### 1. Repair Request & Progress Tracking

- **Submission Flow:** A clean, multi-step form to submit repair requests with file attachment support (photos of the issue).
- **Live Tracker:** A visual Progress Stepper (e.g., Pending > In Review > Repairing > Quality Check > Completed) to follow the real-time status of any request.

### 2. Technical Update & Solution Implementation

- **Maintenance Logs:** A dedicated section for technicians to "Update Progress" and "Propose Repair Solutions" (Bổ sung phương án sửa chữa).
- **Asset Durability Assessment:** An interactive feature within each ticket to rate and record the Durability & Condition of individual assets involved in the repair.

### 3. Reporting & Fleet Health Overview

- **Asset Status Dashboard:** A minimalist overview display showing the "Health Status" of all company assets (e.g., Operational, Under Repair, Critical).
- **Reporting Suite:** Clean Bar Charts and Data Tables focused on repair frequency, costs, and asset lifespan analytics.

## Technical Specifications (React & Tailwind CSS Context)

- **Layout:** A sidebar-based navigation using `#1d3557` background. Main content area on a light gray background (`bg-slate-50`).
- **Card Components:** White containers (`bg-white`) with `rounded-2xl`, subtle borders (`border-slate-100`), and no heavy shadows.
- **Interactivity:** Use smooth transitions and clear hover states. Active focus rings should use a soft tint of the primary color (`ring-2 ring-[#1d3557]/20`).
