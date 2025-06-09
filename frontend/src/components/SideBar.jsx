import React, { useContext } from "react";
import { UserContext } from "../context/UserContext";
import { useNavigate } from "react-router-dom";

const Sidebar = ({ onClick }) => {
  const { logout } = useContext(UserContext);
  const navigate = useNavigate();

  const exit = () => {
    logout();
    navigate("/");
  };

  return (
    <div className="w-64 bg-white h-100% shadow-md p-4 flex flex-col">
      <nav className="flex-1">
        <ul className="space-y-2">
          <li>
            <a
              onClick={() => onClick("conta")}
              className="flex items-center p-3 rounded-lg text-gray-700 hover:bg-gray-200 transition
              cursor-pointer duration-200 font-semibold"
            >
              <i className="fas fa-home text-xl mr-4"></i>
              Conta
            </a>
          </li>
          <li>
            <a
              onClick={() => onClick("reservas")}
              className="flex items-center p-3 rounded-lg text-gray-700 hover:bg-gray-200 transition
              cursor-pointer duration-200 font-semibold"
            >
              <i className="fas fa-search text-xl mr-4"></i>
              Minhas Reservas
            </a>
          </li>
          <li>
            <div className="mt-auto pt-4 border-t border-gray-200">
              <button
                onClick={exit}
                className="w-full flex items-center justify-center p-3 rounded-lg
           bg-red-600 text-white hover:bg-red-700 transition 
           duration-200 font-semibold cursor-pointer"
              >
                <i className="fas fa-sign-out-alt text-xl mr-3"></i>
                Logout
              </button>
            </div>
          </li>
        </ul>
      </nav>
    </div>
  );
};

export default Sidebar;
