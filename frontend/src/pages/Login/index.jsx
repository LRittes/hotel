import React, { useContext, useState } from "react";
import api from "../../service/api";
import { UserContext } from "../../context/UserContext";
import { useNavigate } from "react-router-dom";

const LoginPage = () => {
  const { user, login } = useContext(UserContext);
  const navigate = useNavigate();

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [rememberMe, setRememberMe] = useState(false);

  let dataLogin = { email: email, password: password };

  const handleSubmit = async (e) => {
    e.preventDefault(); // Previne o recarregamento da página

    setError(""); // Limpa erros anteriores
    setLoading(true); // Ativa o estado de carregamento

    if (!email || !password) {
      setError("Por favor, preencha todos os campos.");
      setLoading(false);
      return;
    }

    // Simulação de uma chamada de API para login
    try {
      // Aqui você integraria sua lógica de autenticação real (ex: axios.post('/api/login', { email, password }))
      console.log("Tentando fazer login com:", { email, password, rememberMe });
      const response = (await api.get("/clientes/login", { params: dataLogin }))
        .data;

      if (user.name != "convidado") {
        // Login bem-sucedido
        alert("Login bem-sucedido! Redirecionando...");
        login(response);
        gotoHome();
        // Em uma aplicação real, você faria um navigate('/dashboard') ou algo similar
      } else {
        setError("Email ou senha inválidos.");
      }
    } catch (err) {
      // Lida com erros da API
      console.error("Erro de login:", err);
      setError("Ocorreu um erro ao tentar fazer login. Tente novamente.");
    } finally {
      setLoading(false); // Desativa o estado de carregamento
    }
  };

  const gotoHome = () => {
    navigate("/");
  };

  const goToRegister = () => {
    navigate("/register");
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-100 p-4 sm:p-6 lg:p-8">
      <div className="bg-white p-8 rounded-xl shadow-2xl w-full max-w-md">
        <h2 className="text-4xl font-bold text-center text-gray-900 mb-8">
          Login
        </h2>

        {error && (
          <div
            className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded relative mb-6"
            role="alert"
          >
            <strong className="font-bold">Erro!</strong>
            <span className="block sm:inline ml-2">{error}</span>
          </div>
        )}

        <form onSubmit={handleSubmit} className="space-y-6">
          <div>
            <label
              htmlFor="email"
              className="block text-gray-700 text-sm font-semibold mb-2"
            >
              Email
            </label>
            <input
              type="text"
              id="email"
              className="shadow-sm appearance-none border border-gray-300 rounded-lg w-full py-3 px-4 text-gray-700 leading-tight focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
              placeholder="seu.email@example.com"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              disabled={loading}
            />
          </div>

          <div>
            <label
              htmlFor="password"
              className="block text-gray-700 text-sm font-semibold mb-2"
            >
              Senha
            </label>
            <input
              type="password"
              id="password"
              className="shadow-sm appearance-none border border-gray-300 rounded-lg w-full py-3 px-4 text-gray-700 leading-tight focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
              placeholder="••••••••"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              disabled={loading}
            />
          </div>

          <div className="flex items-center justify-between">
            <div className="flex items-center">
              <input
                id="remember_me"
                name="remember_me"
                type="checkbox"
                className="h-4 w-4 text-blue-600 focus:ring-blue-500 border-gray-300 rounded"
                checked={rememberMe}
                onChange={(e) => setRememberMe(e.target.checked)}
                disabled={loading}
              />
              <label
                htmlFor="remember_me"
                className="ml-2 block text-sm text-gray-900 select-none"
              >
                Lembrar-me
              </label>
            </div>
            <a
              href="#"
              className="font-medium text-blue-600 hover:text-blue-500 text-sm transition duration-200"
            >
              Esqueceu a senha?
            </a>
          </div>

          <button
            type="submit"
            className={`w-full flex justify-center py-3 px-4 border border-transparent rounded-lg shadow-sm text-lg 
              font-semibold text-white transition duration-300 focus:outline-none focus:ring-2 focus:ring-offset-2
               focus:ring-blue-500 cursor-pointer
              ${
                loading
                  ? "bg-blue-400 cursor-not-allowed"
                  : "bg-blue-600 hover:bg-blue-700"
              }`}
            disabled={loading}
          >
            {loading ? "Entrando..." : "Entrar"}
          </button>
        </form>

        <div className="mt-8 text-center">
          <p className="text-sm text-gray-600">
            Não tem uma conta?{" "}
            <a
              href="/register"
              onClick={goToRegister}
              className="font-medium text-blue-600 hover:text-blue-500 transition duration-200"
            >
              Cadastre-se
            </a>
          </p>
        </div>

        <div className="mt-6">
          <div className="relative">
            <div className="absolute inset-0 flex items-center">
              <div className="w-full border-t border-gray-300"></div>
            </div>
            <div className="relative flex justify-center text-sm">
              <span className="px-2 bg-white text-gray-500">Ou entre com</span>
            </div>
          </div>

          <div className="mt-6 grid grid-cols-2 gap-3">
            <div>
              <a
                href="#"
                className="w-full flex items-center justify-center py-3 px-4 border border-gray-300 rounded-lg shadow-sm text-sm font-medium text-gray-700 bg-white hover:bg-gray-50 transition duration-200"
              >
                <img
                  src="https://www.google.com/favicon.ico" // Ícone do Google
                  alt="Google"
                  className="h-5 w-5 mr-3"
                  onError={(e) => {
                    e.target.onerror = null;
                    e.target.src =
                      "https://placehold.co/20x20/cccccc/ffffff?text=G";
                  }}
                />
                Google
              </a>
            </div>
            <div>
              <a
                href="#"
                className="w-full flex items-center justify-center py-3 px-4 border border-gray-300 rounded-lg shadow-sm text-sm font-medium text-gray-700 bg-white hover:bg-gray-50 transition duration-200"
              >
                <img
                  src="https://static.xx.fbcdn.net/rsrc.php/yb/r/hL5c9C9_6yW.ico" // Ícone do Facebook (pode ser necessário um fallback ou SVG)
                  alt="Facebook"
                  className="h-5 w-5 mr-3"
                  onError={(e) => {
                    e.target.onerror = null;
                    e.target.src =
                      "https://placehold.co/20x20/cccccc/ffffff?text=F";
                  }}
                />
                Facebook
              </a>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default LoginPage;
