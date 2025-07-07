import ReservaCard from "./ReservaCard";

const ReservaListing = ({ reservasData }) => {
  if (!reservasData || reservasData.length === 0) {
    return (
      <div className="text-center p-8 bg-white rounded-xl shadow-lg">
        <p className="text-gray-600 text-lg">Nenhuma reserva encontrada.</p>
      </div>
    );
  }

  console.log("Reser  list ", reservasData);

  return (
    <div className="space-y-6">
      {reservasData.map((reserva) => (
        <ReservaCard key={reserva.rid} {...reserva} />
      ))}
    </div>
  );
};

export default ReservaListing;
