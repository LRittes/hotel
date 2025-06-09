import { useEffect, useState, useCallback } from "react";
import api from "../service/api";

const ReservaCard = ({
  id,
  dataReserva,
  dataCheckinPrevista,
  dataCheckoutPrevisto,
  tipoQuartoId,
  hotelId,
  quartoId,
  clienteId,
  camaExtra,
  valor,
  status,
}) => {
  const [hotel, setHotel] = useState(null);
  const [quarto, setQuarto] = useState(null);
  const [tipoQuarto, setTipoQuarto] = useState(null);
  const [statusState, setStatusState] = useState(status);
  const [isLoadingData, setIsLoadingData] = useState(true);

  const handleReserva = useCallback(
    async (newStatus) => {
      const previousStatus = statusState;
      setStatusState(newStatus);

      const data = {
        dataReserva,
        dataCheckinPrevista,
        dataCheckoutPrevisto,
        tipoQuartoId,
        quartoId,
        hotelId,
        camaExtra,
        clienteId,
        valor,
        status: newStatus,
      };

      try {
        const response = await api.put(`/reservas/${id}`, data);
        console.log(`Reserva ${newStatus} com sucesso:`, response.data);
      } catch (err) {
        console.error(
          `Erro ao ${newStatus.slice(0, -1)}r reserva:`,
          err.response?.data?.message ||
            err.message ||
            "Erro desconhecido ao atualizar reserva"
        );
        setStatusState(previousStatus);
      }
    },
    [
      id,
      dataReserva,
      dataCheckinPrevista,
      dataCheckoutPrevisto,
      tipoQuartoId,
      quartoId,
      hotelId,
      camaExtra,
      clienteId,
      valor,
      statusState,
    ]
  );

  useEffect(() => {
    const getLeftData = async () => {
      setIsLoadingData(true);
      try {
        const [responseHotel, responseQuarto, responseTipoQuarto] =
          await Promise.all([
            api.get(`/hoteis/${hotelId}`),
            api.get(`/quartos/${quartoId}`),
            api.get(`/tipos-quarto/${tipoQuartoId}`),
          ]);

        setHotel(responseHotel.data);
        setQuarto(responseQuarto.data);
        setTipoQuarto(responseTipoQuarto.data);
      } catch (err) {
        console.error("Erro ao buscar dados adicionais da reserva:", err);
      } finally {
        setIsLoadingData(false);
      }
    };

    getLeftData();
  }, [hotelId, quartoId, tipoQuartoId]);

  useEffect(() => {
    setStatusState(status);
  }, [status]);

  return (
    <div className="bg-white rounded-xl shadow-lg p-6 mb-4 border border-gray-200">
      <div className="flex justify-between items-center mb-4">
        <h3 className="text-2xl font-bold text-gray-900">Reserva #{id}</h3>
        <span
          className={`px-3 py-1 rounded-full text-sm font-semibold
            ${
              statusState === "confirmada"
                ? "bg-green-100 text-green-800"
                : statusState === "pendente"
                ? "bg-yellow-100 text-yellow-800"
                : statusState === "cancelada"
                ? "bg-red-100 text-red-800"
                : "bg-gray-100 text-gray-800"
            }`}
        >
          {statusState.charAt(0).toUpperCase() + statusState.slice(1)}{" "}
        </span>
      </div>

      {isLoadingData ? (
        <div className="text-center py-4 text-gray-500">
          Carregando detalhes do hotel/quarto...
        </div>
      ) : (
        <div className="grid grid-cols-1 md:grid-cols-2 gap-4 text-gray-700">
          <p>
            <strong>Data da Reserva:</strong> {dataReserva}
          </p>
          <p>
            <strong>Valor Total:</strong> R${" "}
            {valor.toFixed(2).replace(".", ",")}
          </p>{" "}
          <p>
            <strong>Check-in:</strong> {dataCheckinPrevista}
          </p>
          {/* Acessa propriedades APENAS se hotel, quarto, tipoQuarto não forem null */}
          <p>
            <strong>Número do Quarto:</strong> Andar {quarto?.andar}º - número{" "}
            {quarto?.numero} (Tipo: {tipoQuarto?.tipoQuarto})
          </p>
          <p>
            <strong>Check-out:</strong> {dataCheckoutPrevisto}
          </p>
          <p>
            <strong>Nome do Hotel:</strong> {hotel?.nome}
          </p>
          <p>
            <strong>Endereço: </strong> {hotel?.endereco}
          </p>
          <p>
            <strong>Plano: </strong> {tipoQuarto?.plano}
          </p>
          <p>
            <strong>Cama Extra:</strong> {camaExtra ? "Sim" : "Não"}
          </p>
        </div>
      )}

      <div className="mt-6 text-right">
        {(() => {
          switch (statusState) {
            case "pendente":
              return (
                <button
                  key="pagar"
                  onClick={() => handleReserva("confirmada")}
                  className="bg-green-600 text-white px-4 py-2 rounded-md hover:bg-green-700 transition duration-300 mr-2 cursor-pointer"
                >
                  Pagar
                </button>
              );
            case "confirmada":
              return (
                <span
                  key="confirmado"
                  className="text-green-700 font-semibold mr-2"
                >
                  Reserva Confirmada
                </span>
              );
            case "cancelada":
              return (
                <span
                  key="cancelado"
                  className="text-red-700 font-semibold mr-2"
                >
                  Reserva Cancelada
                </span>
              );
            default:
              return null;
          }
        })()}

        {/* O botão "Cancelar" só aparece se o status NÃO FOR "cancelada" */}
        {statusState !== "cancelada" && (
          <button
            onClick={() => handleReserva("cancelada")}
            className="bg-red-500 text-white px-4 py-2 rounded-md hover:bg-red-600 transition duration-300 cursor-pointer"
          >
            Cancelar
          </button>
        )}
      </div>
    </div>
  );
};

export default ReservaCard;
