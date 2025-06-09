import { useContext, useEffect, useState } from "react";
import Header from "../../components/Header";
import Sidebar from "../../components/SideBar";
import RegisterPage from "../Register";
import ReservaListing from "../../components/ReservaListing";
import api from "../../service/api";
import { UserContext } from "../../context/UserContext";
import { useNavigate } from "react-router-dom";

const ConfigPage = () => {
  const { user } = useContext(UserContext);
  const navigate = useNavigate();
  const [currentPage, setcurrentPage] = useState("conta");
  const [reservas, setReservas] = useState([]);

  const getReservasByClienteId = async (id) => {
    let response = await api.get(`/reservas/cr`, { params: { id: id } });
    setReservas(response.data);
  };

  useEffect(() => {
    if (user.email == "Convidado") {
      navigate("/");
    }
    getReservasByClienteId(user.id);
  }, []);

  const goTo = (page) => setcurrentPage(page);

  return (
    <>
      <Header />
      <div className="flex bg-gray-100 min-h-screen">
        <Sidebar onClick={goTo} />

        <main className="flex-1 p-8">
          {currentPage == "conta" ? (
            <RegisterPage register={false} />
          ) : (
            <div className="min-h-screen bg-gray-100 p-8">
              <h1 className="text-4xl font-bold text-gray-800 mb-8 text-center">
                Minhas Reservas
              </h1>
              <div className="max-w-3xl mx-auto">
                <ReservaListing reservasData={reservas} />
              </div>
            </div>
          )}
        </main>
      </div>
    </>
  );
};

export default ConfigPage;
