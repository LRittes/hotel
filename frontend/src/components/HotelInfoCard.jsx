const HotelInfoCard = ({
  imageUrl,
  hotelName,
  rating,
  type,
  features,
  distance,
  score,
  reviews,
  totalImages,
}) => {
  return (
    <div className="bg-white rounded-xl shadow-lg overflow-hidden flex flex-col md:flex-row w-full max-w-3xl mx-auto my-8">
      <div className="relative w-full md:w-2/5 h-64 md:h-auto overflow-hidden">
        <img
          src={
            imageUrl ||
            "https://placehold.co/600x400/a0aec0/ffffff?text=Hotel+Image"
          }
          alt={hotelName}
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
        <div className="absolute bottom-4 right-4 bg-black bg-opacity-50 text-white px-3 py-1 rounded-full text-sm">
          {totalImages ? `1/${totalImages}` : "N/A"}
        </div>
      </div>
      <div className="p-6 flex-1">
        <h3 className="text-2xl font-bold text-gray-900 mb-1">{hotelName}</h3>
        <div className="flex items-center mb-2">
          <div className="text-yellow-500 text-lg mr-2">
            {Array.from({ length: rating }).map((_, i) => (
              <i key={i} className="fas fa-star"></i>
            ))}
          </div>
          <span className="text-gray-600">{type}</span>
        </div>
        <ul className="text-gray-700 text-base mb-4">
          {features.map((feature, index) => (
            <li key={index} className="flex items-center mb-1">
              <i className="fas fa-check-circle text-green-500 mr-2"></i>
              {feature}
            </li>
          ))}
        </ul>
        <p className="text-gray-600 text-base mb-4">{distance} até o Centro</p>
        <div className="flex items-center">
          <span className="bg-blue-700 text-white text-lg font-bold px-3 py-1 rounded-md mr-2">
            {score}
          </span>
          <span className="text-gray-800 font-semibold text-lg mr-1">
            Excelente
          </span>
          <span className="text-gray-600 text-base">
            ({reviews} pontuações)
          </span>
        </div>
      </div>
    </div>
  );
};

export default HotelInfoCard;
