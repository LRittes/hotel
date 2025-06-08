import { useEffect, useState, useCallback } from "react"; // Importar useCallback
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
  const [hotelName, setName] = useState("");
  const [endereco, setEndereco] = useState("");
  const [numQuarto, setNumQuarto] = useState("");
  const [plano, setPlano] = useState("");
  const [tipoQuarto, setTipoQuarto] = useState("");
  const [statusState, setStatusState] = useState(status);

  const handleReserva = useCallback(
    async (newStatus) => {
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
          `Erro ao ${newStatus.slice(0, -2)}r reserva:`,
          err.response?.data?.message || err.message
        );
        setStatusState(status);
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
      status,
    ]
  );

  useEffect(() => {
    const getLeftData = async () => {
      try {
        const [responseHotel, responseNumQuarto, responsePlano] =
          await Promise.all([
            api.get(`/hoteis/${hotelId}`),
            api.get(`/quartos/${quartoId}`),
            api.get(`/tipos-quarto/${tipoQuartoId}`),
          ]);

        setName(responseHotel.data.nome);
        setEndereco(responseHotel.data.endereco);
        setNumQuarto(responseNumQuarto.data.numero);
        setPlano(responsePlano.data.plano);
        setTipoQuarto(responsePlano.data.tipoQuarto);
      } catch (err) {
        console.error("Erro ao buscar dados adicionais da reserva:", err);
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
                : statusState === "cancelada" // Adicionei o estilo para 'cancelada' aqui
                ? "bg-red-100 text-red-800"
                : "bg-gray-100 text-gray-800"
            }`}
        >
          {statusState.charAt(0).toUpperCase() + statusState.slice(1)}{" "}
        </span>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 gap-4 text-gray-700">
        <p>
          <strong>Data da Reserva:</strong> {dataReserva}
        </p>
        <p>
          <strong>Valor Total:</strong> R$ {valor.toFixed(2).replace(".", ",")}
        </p>{" "}
        <p>
          <strong>Check-in:</strong> {dataCheckinPrevista}
        </p>
        <p>
          <strong>Número do Quarto:</strong> {numQuarto} (Tipo: {tipoQuarto})
        </p>
        <p>
          <strong>Check-out:</strong> {dataCheckoutPrevisto}
        </p>
        <p>
          <strong>Nome do Hotel:</strong> {hotelName}
        </p>
        <p>
          <strong>Endereço: </strong> {endereco}
        </p>
        <p>
          <strong>Plano: </strong> {plano}
        </p>
        <p>
          <strong>Cama Extra:</strong> {camaExtra ? "Sim" : "Não"}
        </p>
      </div>

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
            case "confirmada": // Opcional: mostrar algo se confirmada
              return (
                <span
                  key="confirmado"
                  className="text-green-700 font-semibold mr-2"
                >
                  Reserva Confirmada
                </span>
              );
            case "cancelada": // Opcional: mostrar algo se cancelada
              return (
                <span
                  key="cancelado"
                  className="text-red-700 font-semibold mr-2"
                >
                  Reserva Cancelada
                </span>
              );
            default:
              return null; // Não renderiza nada por padrão
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
