import { useEffect, useState } from "react";
import Header from "../../components/Header";
import Sidebar from "../../components/SideBar";
import RegisterPage from "../Register";
import ReservaListing from "../../components/ReservaListing";
import { exampleReservas } from "../../utils";
import api from "../../service/api";

// Componente App para demonstrar a Sidebar
const ConfigPage = () => {
  const [currentPage, setcurrentPage] = useState(null);
  const [reservas, setReservas] = useState([]);

  useEffect(() => {
    const getReservars = async () => {
      const response = await api.get("/reservas");
      setReservas(response.data);
    };

    getReservars();
  }, []);

  return (
    <>
      <Header />
      <div className="flex bg-gray-100 min-h-screen">
        {/* A Sidebar fica à esquerda */}
        <Sidebar />

        {/* Conteúdo principal da página */}
        <main className="flex-1 p-8">
          {/* <RegisterPage register={false} /> */}
          <div className="min-h-screen bg-gray-100 p-8">
            <h1 className="text-4xl font-bold text-gray-800 mb-8 text-center">
              Minhas Reservas
            </h1>
            <div className="max-w-3xl mx-auto">
              <ReservaListing reservasData={reservas} />
            </div>
          </div>
        </main>
      </div>
    </>
  );
};

export default ConfigPage;
