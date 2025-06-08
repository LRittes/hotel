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
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

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
    camaExtra: isChecked,
    status: "pendente",
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

  const saveReservarRoom = async (e) => {
    e.preventDefault(); // Previne o recarregamento da página

    console.log(reservaDataToSave, user);
    if (user.email == "Convidado") {
      alert("Deve se cadastrar para reservar quartos!");
      setLoading(false);
      return;
    }

    try {
      reservaDataToSave.clienteId = user.id;
      await api.post("/reservas", reservaDataToSave);
      console.log("Reserva salva: ", reservaDataToSave);
    } catch (error) {
      // Se ocorrer um erro na requisição
      console.error("Erro ao enviar dados (POST):", error);
      // setMessage(
      //   `Erro ao fazer a reserva!: ${error.message || "Erro desconhecido"}`
      // );

      if (error.response.status == 409) {
        alert(error.response.data.message);
      }
      // Tratamento de erros mais específico:
      if (error.response) {
        // O servidor respondeu com um status de erro (ex: 400, 404, 500)
        console.error("Dados de erro do servidor:", error.response.data);
        console.error("Status de erro:", error.response.status);
        // setMessage(
        //   `Erro do servidor: ${error.response.status} - ${
        //     error.response.data.message || "Dados inválidos"
        //   }`
        // );
      } else if (error.request) {
        // A requisição foi feita, mas nenhuma resposta foi recebida
        console.error("Nenhuma resposta do servidor:", error.request);
        // setMessage("Erro de rede: Nenhuma resposta do servidor.");
      } else {
        // Algo mais aconteceu ao configurar a requisição que disparou um erro
        console.error("Erro na configuração da requisição:", error.message);
      }
    }
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
            <p className="text-4xl font-bold text-gray-900">R$ {preco_noite}</p>
            {tp_quarto != "single" && (
              <ToggleSwitch
                label="cama extra ? "
                initialChecked={isChecked}
                onToggle={handleDataToggle}
              />
            )}
          </div>
          <button
            className="bg-green-600 hover:bg-green-700 text-white px-6 py-3 rounded-md font-bold text-lg transition duration-300 cursor-pointer"
            onClick={saveReservarRoom}
          >
            Reservar <i className="fas fa-chevron-right ml-2"></i>
          </button>
        </div>
      </div>
    </div>
  );
}
