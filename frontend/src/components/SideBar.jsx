import React, { useContext } from "react";
import { UserContext } from "../context/UserContext";
import { useNavigate } from "react-router-dom";

const Sidebar = () => {
  const { logout } = useContext(UserContext);
  const navigate = useNavigate();

  const exit = () => {
    logout();
    navigate("/");
  };
  return (
    <div className="w-64 bg-white h-screen shadow-md p-4 flex flex-col">
      <nav className="flex-1">
        <ul className="space-y-2">
          {/* Item de navegação: Início */}
          <li>
            <a
              href="#"
              className="flex items-center p-3 rounded-lg text-gray-700 hover:bg-gray-200 transition duration-200 font-semibold"
            >
              {/* Ícone de casa (Font Awesome) */}
              <i className="fas fa-home text-xl mr-4"></i>
              Conta
            </a>
          </li>
          {/* Item de navegação: Suporte */}
          <li>
            <a
              href="#"
              className="flex items-center p-3 rounded-lg text-gray-700 hover:bg-gray-200 transition duration-200 font-semibold"
            >
              {/* Ícone de lupa (Font Awesome - representa suporte/busca) */}
              <i className="fas fa-search text-xl mr-4"></i>
              Minhas Reservas
            </a>
          </li>
        </ul>
      </nav>

      <div className="mt-auto pt-4 border-t border-gray-200">
        <button
          onClick={exit} // Adicione sua lógica de logout aqui
          className="w-full flex items-center justify-center p-3 rounded-lg
           bg-red-600 text-white hover:bg-red-700 transition 
           duration-200 font-semibold cursor-pointer"
        >
          {/* Ícone de sair (Font Awesome) */}
          <i className="fas fa-sign-out-alt text-xl mr-3"></i>
          Logout
        </button>
      </div>
    </div>
  );
};

export default Sidebar;
