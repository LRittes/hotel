import { useContext, useState } from "react";
import ToggleSwitch from "./ToggleSwitch";
import {
  capitalizeWords,
  formatDate,
  getFormattedCurrentDateYMD,
} from "../utils";
import { UserContext } from "../context/UserContext";
import api from "../service/api";
import { data } from "react-router-dom";
import RoomServicesModal from "./RoomServicesModal";

export default function RoomInfoCard({
  imageUrl,
  showOnMapLink,
  hotelData,
  andar,
  numero,
  hotel_id,
  tipo_quarto_id,
  plano,
  preco_noite,
  id,
  tp_quarto,
}) {
  const { reservaData } = useContext(UserContext);
  const { user } = useContext(UserContext);

  const [isChecked, setIsChecked] = useState(false);
  const [priceServices, setPriceServices] = useState(0);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [loading, setLoading] = useState(false);

  let reservaDataToSave = {
    tipoQuartoId: tipo_quarto_id,
    quartoId: id,
    dataReserva: getFormattedCurrentDateYMD(),
    dataCheckinPrevista:
      reservaData != null ? formatDate(reservaData.checkIn) : "",
    dataCheckoutPrevisto:
      reservaData != null ? formatDate(reservaData.checkOut) : "",
    clienteId: null,
    hotelId: hotel_id,
    valor: 0,
    valor_servicos_extra: 0,
    camaExtra: isChecked,
    status: "pendente",
  };

  const roomData = {
    id: id,
    numero: numero,
    andar: andar,
    tipoQuarto: tp_quarto,
    price: preco_noite,
    description: "Quarto confortável com vista para a cidade.",
    hotelId: hotel_id,
  };

  const roomName = `${capitalizeWords(tp_quarto)} ${capitalizeWords(
    plano
  )} - nº ${numero} ${andar}º andar`;

  const imgText = `${tp_quarto}+${plano}`;
  const beds =
    tp_quarto == "single"
      ? "1 cama de casal"
      : "1 ou 2 camas (1 de casal + 1 cama extra de solteiro)";

  const handleDataToggle = (data) => {
    setIsChecked(data);
    reservaData.camaExtra = data;
  };

  const getPriceServices = (roomData, selectedServices, totalPrice) => {
    setPriceServices(totalPrice);
  };

  const saveReservarRoom = async (e) => {
    e.preventDefault();

    console.log(reservaDataToSave, user);
    if (user.email == "Convidado") {
      alert("Deve se cadastrar para reservar quartos!");
      setLoading(false);
      return;
    }

    if (reservaData.checkIn == null || reservaData.checkOut == null) {
      alert("Deve selecionar uma data de check-In e Check-Out!");
      setLoading(false);
      return;
    }

    try {
      reservaDataToSave.clienteId = user.id;
      reservaDataToSave.valor_servicos_extra = priceServices;

      await api.post("/reservas", reservaDataToSave);
      console.log("Reserva salva: ", reservaDataToSave);
      alert("Reserva feita com sucesso!");
    } catch (error) {
      console.error("Erro ao enviar dados (POST):", error);

      if (error.response.status == 409) {
        alert(error.response.data.message);
      }

      if (error.response) {
        console.error("Dados de erro do servidor:", error.response.data);
        console.error("Status de erro:", error.response.status);
      } else if (error.request) {
        console.error("Nenhuma resposta do servidor:", error.request);
      } else {
        console.error("Erro na configuração da requisição:", error.message);
      }
    }
  };

  const handleOpenModal = () => {
    setIsModalOpen(true);
    setPriceServices(0);
  };

  const handleCloseModal = () => {
    setIsModalOpen(false);
  };

  return (
    <div className="bg-white rounded-xl shadow-lg overflow-hidden flex flex-col md:flex-row w-full max-w-4xl mx-auto my-8">
      <div className="relative w-full md:w-2/5 h-64 md:h-auto overflow-hidden">
        <img
          src={
            imageUrl ||
            `https://placehold.co/600x400/a0aec0/ffffff?text=${imgText}`
          }
          alt={roomName}
          className="w-full h-full object-cover"
          onError={(e) => {
            e.target.onerror = null;
            e.target.src =
              "https://placehold.co/600x400/a0aec0/ffffff?text=Image+Not+Found";
          }}
        />
        <div className="absolute top-4 right-4 bg-white p-2 rounded-full shadow-md cursor-pointer hover:bg-gray-100 transition duration-200">
          <i className="far fa-heart text-red-500 text-xl"></i>
        </div>
      </div>
      <div className="p-6 flex-1">
        <h3 className="text-2xl font-bold text-gray-900 mb-1">{roomName}</h3>
        <div className="flex items-center text-gray-600 text-sm mb-2 flex-wrap">
          <span>{hotelData.endereco}</span>
          {showOnMapLink && (
            <>
              <span className="mx-2">•</span>
              <a href="#" className="text-blue-600 hover:underline">
                Mostrar no mapa
              </a>
            </>
          )}
        </div>

        <p className="text-gray-700 font-semibold text-lg mb-1">Quarto</p>
        {/* <p className="text-gray-600 text-base mb-1">{description}</p> */}
        <p className="text-gray-600 text-base mb-4">{beds}</p>

        <div className="flex justify-between items-end mb-4">
          <div>
            <p className="text-gray-600 text-sm">diária</p>
            <p className="text-4xl font-bold text-gray-900">
              R$ {preco_noite + priceServices}
            </p>
            {tp_quarto != "single" && (
              <ToggleSwitch
                label="cama extra ? "
                initialChecked={isChecked}
                onToggle={handleDataToggle}
              />
            )}
          </div>
          <button
            className="bg-amber-600 hover:bg-amber-700 text-white px-3 py-3 rounded-md font-bold text-sm transition duration-300 cursor-pointer"
            onClick={handleOpenModal}
          >
            Add Serviços <i className="fas fa-chevron-right ml-2"></i>
          </button>
          <button
            className="bg-green-600 hover:bg-green-700 text-white px-3 py-3 rounded-md font-bold text-sm transition duration-300 cursor-pointer"
            onClick={saveReservarRoom}
          >
            Reservar <i className="fas fa-chevron-right ml-2"></i>
          </button>
          <RoomServicesModal
            isOpen={isModalOpen}
            onClose={handleCloseModal}
            onConfirmReservation={getPriceServices}
            roomData={roomData}
          />
        </div>
      </div>
    </div>
  );
}
