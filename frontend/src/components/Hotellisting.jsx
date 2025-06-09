import { useCallback, useContext, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

import { UserContext } from "../context/UserContext";
import api from "../service/api";
import HotelInfoCard from "./HotelInfoCard";
import { hotelFeatures } from "../utils";

const HotelListings = () => {
  const { hotels, setAllHotels } = useContext(UserContext);
  const [currentDisplayHotels, setCurrentDisplayHotels] = useState([]);
  const [currentDisplayFeatures, setCurrentDisplayFeatures] = useState([]);
  const [isLoading, setIsLoading] = useState(true);

  const navigate = useNavigate();

  const fetchInitialHotels = useCallback(async () => {
    if (!hotels.firstLoadCompleted) {
      setIsLoading(true);
      try {
        const response = await api.get("/hoteis");
        setAllHotels(response.data);
      } catch (error) {
        console.error("Erro ao carregar hotéis iniciais:", error);
      } finally {
        setIsLoading(false);
      }
    }
  }, [hotels.firstLoadCompleted, setAllHotels]);

  useEffect(() => {
    fetchInitialHotels();

    if (hotels.showAllHotels) {
      setCurrentDisplayHotels(hotels.all);
    } else {
      setCurrentDisplayHotels(hotels.filtered);
    }
    setIsLoading(false);
  }, [hotels.all, hotels.filtered, hotels.showAllHotels, fetchInitialHotels]);

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
      {isLoading ? (
        <h3 className="text-2xl font-bold text-gray-500 mb-8 text-center">
          Loading!
        </h3>
      ) : currentDisplayHotels.length > 0 ? (
        currentDisplayHotels.map((hotel, _) => (
          <HotelInfoCard
            key={hotel.id}
            hotelData={{
              id: hotel.id,
              nameLogo: hotelNameLogo(hotel.nome),
              hotelName: hotel.nome,
              rating: Math.floor(hotel.nota / 2) + 1,
              type: "Hotel",
              features: getFeatures(5),
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
