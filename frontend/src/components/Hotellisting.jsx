import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

import api from "../service/api";
import HotelInfoCard from "./HotelInfoCard";
import { hotelFeatures } from "../utils";

const HotelListings = () => {
  var [hotels, setHotels] = useState([]);
  const navigate = useNavigate();

  const getHotel = async () => {
    setHotels((await api.get("/hoteis")).data);
  };

  useEffect(() => {
    getHotel();
  }, []);

  function hotelNameLogo(name) {
    return name.split("\n").join("+");
  }

  function getFeatures(num) {
    let features = [];
    for (let i = 1; i < num; i++) {
      features.push(
        hotelFeatures[Math.floor(Math.random() * (hotelFeatures.length - 1))]
      );
    }

    return features;
  }

  function goToRoom(id, hotelData) {
    navigate(`/room/${id}`, { state: hotelData });
  }

  return (
    <div className="grid grid-cols-1 gap-8">
      {hotels.length > 0 ? (
        hotels.map((hotel, _) => (
          <HotelInfoCard
            key={hotel.id}
            hotelData={{
              id: hotel.id,
              imageUrl: `https://placehold.co/600x400/4a5568/ffffff?text=${hotelNameLogo(
                hotel.nome
              )}`,
              hotelName: hotel.nome,
              rating: Math.floor(Math.random() * 5) + 1,
              type: "Hotel",
              features: getFeatures(Math.floor(Math.random() * 7) + 3),
              distance: (Math.random() * 15 + 0.1).toFixed(1) + " km",
              score: hotel.nota,
              reviews: Math.floor(Math.random() * 5000) + 40,
              totalImages: Math.floor(Math.random() * 50) + 1,
              endereco: hotel.endereco,
            }}
            onClick={goToRoom}
          />
        ))
      ) : (
        <h3 className="text-2xl font-bold text-gray-500 mb-8 text-center">
          Não há Hotéis!
        </h3>
      )}
    </div>
  );
};

export default HotelListings;
