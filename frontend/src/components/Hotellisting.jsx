import { useEffect, useState } from "react";
import api from "../service/api";
import HotelInfoCard from "./HotelInfoCard";
import { hotelFeatures } from "../utils";

// HotelListings Component (NEW)
const HotelListings = () => {
  var [hotels, setHotels] = useState([]);
  const getHotel = async () => {
    //     {
    //     imageUrl:
    //       "https://placehold.co/600x400/4a5568/ffffff?text=Intercity+Portofino",
    //     hotelName: "Intercity Portofino Florianópolis",
    //     rating: 4,
    //     type: "Hotel",
    //     features: [
    //       "Limpeza excepcional",
    //       "Acomodações que aceitam animais de estimação",
    //     ],
    //     distance: "7.1 km",
    //     score: 8.9,
    //     reviews: 2666,
    //     totalImages: 40,
    //   },
    setHotels((await api.get("/hoteis")).data);
    console.log(hotels);
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

  return (
    <div className="grid grid-cols-1 gap-8">
      {hotels.map((hotel, _) => (
        <HotelInfoCard
          key={hotel.id} // Using index as key, consider a unique ID from data if available
          imageUrl={`https://placehold.co/600x400/4a5568/ffffff?text=${hotelNameLogo(
            hotel.nome
          )}`}
          hotelName={hotel.nome}
          rating={Math.floor(Math.random() * 5) + 1}
          type="Hotel"
          features={getFeatures(Math.floor(Math.random() * 5) + 1)}
          distance={(Math.random() * 15 + 0.1).toFixed(1) + " km"}
          score={hotel.nota}
          reviews={Math.floor(Math.random() * 5000) + 40}
          totalImages={Math.floor(Math.random() * 50) + 1}
        />
      ))}
    </div>
  );
};

export default HotelListings;
