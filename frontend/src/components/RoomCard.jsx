export default function RoomInfoCard({
  imageUrl,
  roomName,
  location,
  distanceFromCenter,
  roomType,
  description,
  beds,
  nights,
  price,
  showOnMapLink,
}) {
  return (
    <div className="bg-white rounded-xl shadow-lg overflow-hidden flex flex-col md:flex-row w-full max-w-4xl mx-auto my-8">
      <div className="relative w-full md:w-2/5 h-64 md:h-auto overflow-hidden">
        <img
          src={
            imageUrl ||
            "https://placehold.co/600x400/a0aec0/ffffff?text=Room+Image"
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
          <span>{location}</span>
          {showOnMapLink && (
            <>
              <span className="mx-2">•</span>
              <a href="#" className="text-blue-600 hover:underline">
                Mostrar no mapa
              </a>
            </>
          )}
          <span className="mx-2">•</span>
          <span>{distanceFromCenter} do centro</span>
        </div>

        <p className="text-gray-700 font-semibold text-lg mb-1">{roomType}</p>
        <p className="text-gray-600 text-base mb-1">{description}</p>
        <p className="text-gray-600 text-base mb-4">{beds}</p>

        <div className="flex justify-between items-end mb-4">
          <div>
            <p className="text-gray-600 text-sm">{nights} diárias</p>
            <p className="text-4xl font-bold text-gray-900">R$ {price}</p>
          </div>
          <button className="bg-blue-600 hover:bg-blue-700 text-white px-6 py-3 rounded-md font-bold text-lg transition duration-300">
            Ver disponibilidade <i className="fas fa-chevron-right ml-2"></i>
          </button>
        </div>
      </div>
    </div>
  );
}
