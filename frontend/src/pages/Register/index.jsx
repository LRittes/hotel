import React, { useContext, useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../../service/api";
import { UserContext } from "../../context/UserContext";

const RegisterPage = ({ register = true }) => {
  const { user, login } = useContext(UserContext);
  const navigate = useNavigate();

  const [cpf, setCpf] = useState(user.cpf || "");
  const [nome, setNome] = useState(user.nome || "");
  const [email, setEmail] = useState(register ? "" : user.email);
  const [password, setPassword] = useState(user.password || "");
  const [endereco, setEndereco] = useState(user.endereco || "");
  const [telefone, setTelefone] = useState(user.telefone || "");

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [successMessage, setSuccessMessage] = useState("");

  console.log(user);

  const handleSubmit = async (e) => {
    e.preventDefault();

    setError("");
    setSuccessMessage("");
    setLoading(true);

    if (!cpf || !nome || !email || !password || !endereco || !telefone) {
      setError("Por favor, preencha todos os campos obrigatórios.");
      setLoading(false);
      return;
    }

    if (!/\S+@\S+\.\S+/.test(email)) {
      setError("Por favor, insira um e-mail válido.");
      setLoading(false);
      return;
    }

    if (password.length < 3) {
      setError("A senha deve ter no mínimo 3 caracteres.");
      setLoading(false);
      return;
    }

    const userData = {
      cpf,
      nome,
      email,
      password,
      endereco,
      telefone,
    };

    try {
      const response = register
        ? await api.post("/clientes", userData)
        : await api.put(`/clientes/${user.id}`, userData);
      console.log(
        `Resposta da API (${register ? "Cadastro" : "Atualização"}):`,
        response.data
      );
      setSuccessMessage(
        response.data.message ||
          `${register ? "Cadastro" : "Atualização"} realizado com sucesso!`
      );

      if (register) {
        setCpf("");
        setNome("");
        setEmail("");
        setPassword("");
        setEndereco("");
        setTelefone("");
        goToLogin();
      } else {
        login(response.data);
      }
    } catch (err) {
      console.error(`Erro de ${register ? "cadastro" : "atualização"}:`, err);
      if (err.response && err.response.data && err.response.data.message) {
        setError(err.response.data.message);
      } else {
        setError(
          `Ocorreu um erro ao tentar ${
            register ? "cadastrar" : "atualizar"
          }. Tente novamente.`
        );
      }
    } finally {
      setLoading(false);
    }
  };

  const goToLogin = () => {
    navigate("/login");
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-100 p-4 sm:p-6 lg:p-8">
      <div className="bg-white p-8 rounded-xl shadow-2xl w-full max-w-md">
        <h2 className="text-4xl font-bold text-center text-gray-900 mb-8">
          {register ? "Cadastre-se" : "Atualize"}
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

        {successMessage && (
          <div
            className="bg-green-100 border border-green-400 text-green-700 px-4 py-3 rounded relative mb-6"
            role="alert"
          >
            <strong className="font-bold">Sucesso!</strong>
            <span className="block sm:inline ml-2">{successMessage}</span>
          </div>
        )}

        <form onSubmit={handleSubmit} className="space-y-6">
          <div>
            <label
              htmlFor="nome"
              className="block text-gray-700 text-sm font-semibold mb-2"
            >
              Nome Completo
            </label>
            <input
              type="text"
              id="nome"
              className="shadow-sm appearance-none border border-gray-300 rounded-lg w-full py-3 px-4 text-gray-700 leading-tight focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
              placeholder="Seu nome"
              value={nome}
              onChange={(e) => setNome(e.target.value)}
              disabled={loading}
            />
          </div>

          <div>
            <label
              htmlFor="cpf"
              className="block text-gray-700 text-sm font-semibold mb-2"
            >
              CPF
            </label>
            <input
              type="text"
              id="cpf"
              className="shadow-sm appearance-none border border-gray-300 rounded-lg w-full py-3 px-4 text-gray-700 leading-tight focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
              placeholder="Ex: 123.456.789-00"
              value={cpf}
              onChange={(e) => setCpf(e.target.value)}
              disabled={loading}
            />
          </div>

          <div>
            <label
              htmlFor="email"
              className="block text-gray-700 text-sm font-semibold mb-2"
            >
              Email
            </label>
            <input
              type="email"
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

          <div>
            <label
              htmlFor="telefone"
              className="block text-gray-700 text-sm font-semibold mb-2"
            >
              Telefone
            </label>
            <input
              type="tel"
              id="telefone"
              className="shadow-sm appearance-none border border-gray-300 rounded-lg w-full py-3 px-4 text-gray-700 leading-tight focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
              placeholder="Ex: (99) 99999-9999"
              value={telefone}
              onChange={(e) => setTelefone(e.target.value)}
              disabled={loading}
            />
          </div>

          <div>
            <label
              htmlFor="endereco"
              className="block text-gray-700 text-sm font-semibold mb-2"
            >
              Endereço
            </label>
            <input
              type="text"
              id="endereco"
              className="shadow-sm appearance-none border border-gray-300 rounded-lg w-full py-3 px-4 text-gray-700 leading-tight focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
              placeholder="Rua, número, cidade, estado"
              value={endereco}
              onChange={(e) => setEndereco(e.target.value)}
              disabled={loading}
            />
          </div>

          <button
            type="submit"
            className={`w-full flex justify-center py-3 px-4 border border-transparent 
                rounded-lg shadow-sm text-lg font-semibold text-white transition duration-300
                 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 cursor-pointer
              ${
                loading
                  ? "bg-blue-400 cursor-not-allowed"
                  : "bg-blue-600 hover:bg-blue-700"
              }`}
            disabled={loading}
          >
            {loading
              ? "Cadastrando..."
              : register
              ? "Criar Conta"
              : "Atualizar"}
          </button>
        </form>

        {register && (
          <div className="mt-8 text-center">
            <p className="text-sm text-gray-600">
              Já tem uma conta?{" "}
              <a
                href="/login"
                className="font-medium text-blue-600 hover:text-blue-500 transition duration-200"
              >
                Faça Login
              </a>
            </p>
          </div>
        )}
      </div>
    </div>
  );
};

export default RegisterPage;
