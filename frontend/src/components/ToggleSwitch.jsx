import React, { useState } from "react";

const ToggleSwitch = ({ label, initialChecked = false, onToggle }) => {
  const [isChecked, setIsChecked] = useState(initialChecked);

  const handleToggle = () => {
    const newState = !isChecked;
    setIsChecked(newState);
    if (onToggle) {
      onToggle(newState);
    }
  };

  return (
    <div className="flex items-center justify-between py-2 px-4 bg-white rounded-lg shadow-sm">
      <span className="text-lg text-gray-800 mr-4 select-none">{label}</span>

      <div
        className={`relative inline-flex flex-shrink-0 h-6 w-11 border-2 border-transparent rounded-full cursor-pointer transition-colors ease-in-out duration-200 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500
          ${isChecked ? "bg-blue-600" : "bg-gray-400"}`}
        role="switch"
        aria-checked={isChecked}
        onClick={handleToggle}
      >
        <span
          aria-hidden="true"
          className={`pointer-events-none inline-block h-5 w-5 rounded-full bg-white shadow transform ring-0 transition ease-in-out duration-200
            ${isChecked ? "translate-x-5" : "translate-x-0"}`}
        ></span>
      </div>
    </div>
  );
};

export default ToggleSwitch;
