import { useContext } from "react";
import { UserContext } from "../context/UserContext";
import { useNavigate } from "react-router-dom";

export default function Header() {
  const { user } = useContext(UserContext);
  const navigate = useNavigate();

  const goToLogin = () => {
    navigate("/login");
  };

  const goToRegister = () => {
    navigate("/register");
  };

  const goToHome = () => {
    navigate("/");
  };

  const goToConfig = () => {
    navigate("/config");
  };

  return (
    <header className="bg-blue-800 text-white py-3 px-4 md:px-8">
      <div className="container mx-auto flex justify-between items-center flex-wrap">
        <a
          onClick={goToHome}
          className="text-3xl font-bold tracking-tight cursor-pointer"
        >
          FallAsleep
        </a>

        <div className="flex items-center space-x-4 md:space-x-6 mt-2 md:mt-0">
          <span className="text-lg font-medium">BRL</span>
          <a
            href="#"
            className="text-white hover:text-blue-200 transition duration-300"
          >
            <i className="fas fa-question-circle text-xl"></i>
          </a>
          {user.email != "Convidado" ? (
            <button
              className="bg-blue-600 hover:bg-blue-700 text-white px-4 py-1 rounded-md font-bold 
             transition duration-300 cursor-pointer"
              onClick={goToConfig}
            >
              {user.nome}
            </button>
          ) : (
            <>
              <button
                className="bg-white text-blue-800 px-4 py-2 rounded-md shadow-md hover:bg-blue-100 transition duration-300 text-base font-medium cursor-pointer"
                onClick={goToRegister}
              >
                Cadastre-se
              </button>
              <button
                className="bg-white text-blue-800 px-4 py-2 rounded-md shadow-md hover:bg-blue-100 transition duration-300 text-base font-medium cursor-pointer"
                onClick={goToLogin}
              >
                Login
              </button>
            </>
          )}
        </div>
      </div>
    </header>
  );
}
