import { useEffect, useState } from "react";
import { formatDate } from "../utils";
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

  const cancelReserva = async () => {
    setStatusState("cancelada");

    // Erro: status não modifica
    try {
      await api.put(`/reservas/${id}`, {
        dataReserva,
        dataCheckinPrevista,
        dataCheckoutPrevisto,
        tipoQuartoId,
        quartoId,
        hotelId,
        camaExtra,
        clienteId,
        valor,
        status: statusState,
      });
    } catch (err) {
      // Lida com erros da API
      //   console.error(`Erro de ${register ? "cadastro" : "atualização"}:`, err);
      console.log(err.response.data.message);
      //   if (err.response && err.response.data && err.response.data.message) {
      //   } else {
      //     setError(
      //       `Ocorreu um erro ao tentar ${
      //         register ? "cadastrar" : "atualizar"
      //       }. Tente novamente.`
      //     );
      //   }
    }
  };

  useEffect(() => {
    const getLeftData = async () => {
      const responseHotel = await api.get(`/hoteis/${hotelId}`);
      const responseNumQuarto = await api.get(`/quartos/${quartoId}`);
      const responsePlano = await api.get(`/tipos-quarto/${tipoQuartoId}`);

      setName(responseHotel.data.nome);
      setEndereco(responseHotel.data.endereco);
      setNumQuarto(responseNumQuarto.data.numero);
      setPlano(responsePlano.data.plano);
      setTipoQuarto(responsePlano.data.tipoQuarto);
    };

    getLeftData();
  }, []);

  console.log(statusState);

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
                <button className="bg-green-600 text-white px-4 py-2 rounded-md hover:bg-green-700 transition duration-300 mr-2 cursor-pointer">
                  Pagar
                </button>
              );
            default:
          }
        })()}

        {/* Ver Detalhes */}
        <button
          onClick={cancelReserva}
          className="bg-red-500 text-white px-4 py-2 rounded-md hover:bg-red-600 transition duration-300 cursor-pointer"
        >
          Cancelar
        </button>
      </div>
    </div>
  );
};

export default ReservaCard;
