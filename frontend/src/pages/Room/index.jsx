import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

import Footer from "../../components/Footer";
import Header from "../../components/Header";
import NavHeader from "../../components/NavHeader";
import RoomListings from "../../components/RoomListing";
import SearchBar from "../../components/SearchBar";
import api from "../../service/api";

function RoomPage() {
  const { id } = useParams();

  const [rooms, setRooms] = useState([]);
  const [loading, setLoading] = useState(true);

  const getRoomsByHotelId = async (id) => {
    let response = await api.get(`/quartos/hid/${id}`);
    setRooms(response.data);
    setLoading(false);
  };

  useEffect(() => {
    getRoomsByHotelId(id);
  }, []);

  return (
    <div className="flex flex-col min-h-screen w-screen">
      <Header />

      <NavHeader />

      <SearchBar />

      {loading ? (
        <h1>Loading</h1>
      ) : rooms.length > 0 ? (
        <RoomListings roomsData={rooms} />
      ) : (
        <h3 className="text-2xl font-bold text-gray-500 mb-8 mt-10 text-center">
          Não há Quartos para este Hotel!
        </h3>
      )}

      <Footer />
    </div>
  );
}

export default RoomPage;
