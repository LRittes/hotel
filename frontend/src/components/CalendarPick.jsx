import React, { useState, useEffect, useRef } from "react";

// DateRangePicker Component
export default function DateRangePicker({ onDateChange }) {
  const [currentMonth, setCurrentMonth] = useState(new Date().getMonth());
  const [currentYear, setCurrentYear] = useState(new Date().getFullYear());
  const [checkInDate, setCheckInDate] = useState(null);
  const [checkOutDate, setCheckOutDate] = useState(null);
  const [isCalendarOpen, setIsCalendarOpen] = useState(false);

  const calendarRef = useRef(null);
  const inputRef = useRef(null);

  const monthNames = [
    "janeiro",
    "fevereiro",
    "março",
    "abril",
    "maio",
    "junho",
    "julho",
    "agosto",
    "setembro",
    "outubro",
    "novembro",
    "dezembro",
  ];
  const dayNames = ["Do.", "2ª", "3ª", "4ª", "5ª", "6ª", "Sa."];

  // Function to format date for display
  const formatDate = (date) => {
    if (!date) return "";
    const day = String(date.getDate()).padStart(2, "0");
    const month = String(date.getMonth() + 1).padStart(2, "0"); // Months are 0-indexed
    const year = date.getFullYear();
    return `${day}/${month}/${year}`;
  };

  // Function to render a single month calendar grid
  const renderMonthDays = (month, year) => {
    const days = [];
    const firstDay = new Date(year, month, 1);
    const lastDay = new Date(year, month + 1, 0);
    const daysInMonth = lastDay.getDate();
    const startDayOfWeek = firstDay.getDay(); // 0 for Sunday, 1 for Monday, etc.

    // Fill leading empty days
    for (let i = 0; i < startDayOfWeek; i++) {
      days.push(
        <div key={`empty-${month}-${i}`} className="calendar-day"></div>
      );
    }

    // Fill days of the month
    for (let day = 1; day <= daysInMonth; day++) {
      const date = new Date(year, month, day);
      const today = new Date();
      today.setHours(0, 0, 0, 0); // Normalize today's date for comparison

      let dayClasses =
        "calendar-day p-2 rounded-lg cursor-pointer transition-colors duration-200 font-medium text-center";

      // Disable past dates
      if (date < today) {
        dayClasses += " text-gray-400 cursor-not-allowed";
      } else {
        // Apply selection styles
        const isCheckIn =
          checkInDate && date.toDateString() === checkInDate.toDateString();
        const isCheckOut =
          checkOutDate && date.toDateString() === checkOutDate.toDateString();
        const isInRange =
          checkInDate &&
          checkOutDate &&
          date > checkInDate &&
          date < checkOutDate;

        if (isCheckIn && isCheckOut) {
          dayClasses += " bg-blue-600 text-white";
        } else if (isCheckIn) {
          dayClasses += " bg-blue-600 text-white rounded-r-none";
        } else if (isCheckOut) {
          dayClasses += " bg-blue-600 text-white rounded-l-none";
        } else if (isInRange) {
          dayClasses += " bg-blue-200 text-blue-800 rounded-none";
        } else {
          dayClasses += " hover:bg-blue-100 text-gray-700";
        }
      }

      days.push(
        <div
          key={`${month}-${day}`}
          className={dayClasses}
          onClick={() => date >= today && handleDateSelect(date)}
        >
          {day}
        </div>
      );
    }
    return days;
  };

  // Handle date selection logic
  const handleDateSelect = (date) => {
    if (!checkInDate || (checkInDate && checkOutDate)) {
      // Start new selection or reset if both are already selected
      setCheckInDate(date);
      setCheckOutDate(null);
    } else if (date < checkInDate) {
      // If selected date is before check-in, make it the new check-in
      setCheckInDate(date);
      setCheckOutDate(null); // Clear check-out as it's now invalid
    } else {
      // If selected date is after or equal to check-in, set as check-out
      setCheckOutDate(date);
      // Optionally close calendar after selecting both dates
      // setIsCalendarOpen(false);
    }
  };

  // Effect to call onDateChange prop when dates change
  useEffect(() => {
    onDateChange(checkInDate, checkOutDate);
  }, [checkInDate, checkOutDate, onDateChange]);

  // Handle month navigation
  const goToPrevMonth = () => {
    setCurrentMonth((prevMonth) => {
      if (prevMonth === 0) {
        setCurrentYear((prevYear) => prevYear - 1);
        return 11;
      }
      return prevMonth - 1;
    });
  };

  const goToNextMonth = () => {
    setCurrentMonth((prevMonth) => {
      if (prevMonth === 11) {
        setCurrentYear((prevYear) => prevYear + 1);
        return 0;
      }
      return prevMonth + 1;
    });
  };

  // Calculate next month for the second calendar
  const nextMonthDate = new Date(currentYear, currentMonth + 1, 1);

  // Close calendar when clicking outside
  useEffect(() => {
    const handleClickOutside = (event) => {
      if (
        calendarRef.current &&
        !calendarRef.current.contains(event.target) &&
        inputRef.current &&
        !inputRef.current.contains(event.target)
      ) {
        setIsCalendarOpen(false);
      }
    };

    document.addEventListener("mousedown", handleClickOutside);
    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, [calendarRef, inputRef]);

  const displayValue = `${
    checkInDate ? formatDate(checkInDate) : "Data de check-in"
  } — ${checkOutDate ? formatDate(checkOutDate) : "Data de check-out"}`;

  return (
    <div className="relative w-full md:w-1/3">
      <div
        className="flex items-center bg-white rounded-md p-3 w-full cursor-pointer"
        onClick={() => setIsCalendarOpen(!isCalendarOpen)}
        ref={inputRef}
      >
        <i className="fas fa-calendar-alt text-gray-500 mr-3 text-xl"></i>
        <input
          type="text"
          placeholder="Data de check-in — Data de check-out"
          className="flex-grow outline-none text-lg text-gray-800 cursor-pointer"
          value={displayValue}
          readOnly
        />
      </div>

      {isCalendarOpen && (
        <div
          ref={calendarRef}
          className="absolute top-full left-1/2 -translate-x-1/2 mt-4 bg-white rounded-lg shadow-xl p-6 z-50 w-2xl "
        >
          <div className="flex justify-between items-center mb-4">
            <h3 className="text-2xl font-bold text-gray-800">Calendário</h3>
            <div className="flex space-x-2">
              <button
                onClick={goToPrevMonth}
                className="p-2 rounded-full hover:bg-gray-200 transition duration-300"
              >
                <i className="fas fa-chevron-left text-gray-600"></i>
              </button>
              <button
                onClick={goToNextMonth}
                className="p-2 rounded-full hover:bg-gray-200 transition duration-300"
              >
                <i className="fas fa-chevron-right text-gray-600"></i>
              </button>
            </div>
          </div>

          <div className="flex flex-col md:flex-row space-y-6 md:space-y-0 md:space-x-8">
            {/* Calendar 1 */}
            <div className="flex-1">
              <div className="text-xl font-semibold text-gray-800 mb-4 text-center">
                {monthNames[currentMonth]} de {currentYear}
              </div>
              <div className="grid grid-cols-7 gap-2 text-gray-600 font-medium mb-2 text-center">
                {dayNames.map((day) => (
                  <span key={day}>{day}</span>
                ))}
              </div>
              <div className="grid grid-cols-7 gap-1">
                {renderMonthDays(currentMonth, currentYear)}
              </div>
            </div>

            {/* Calendar 2 */}
            <div className="flex-1">
              <div className="text-xl font-semibold text-gray-800 mb-4 text-center">
                {monthNames[nextMonthDate.getMonth()]} de{" "}
                {nextMonthDate.getFullYear()}
              </div>
              <div className="grid grid-cols-7 gap-1 text-gray-600 font-medium mb-2 text-center">
                {dayNames.map((day) => (
                  <span key={day}>{day}</span>
                ))}
              </div>
              <div className="grid grid-cols-7 gap-1">
                {renderMonthDays(
                  nextMonthDate.getMonth(),
                  nextMonthDate.getFullYear()
                )}
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}
