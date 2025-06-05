import Footer from "../../components/Footer";
import Header from "../../components/Header";
import HotelListings from "../../components/Hotellisting";
import NavHeader from "../../components/NavHeader";
import RoomListings from "../../components/RoomListing";
import SearchBar from "../../components/SearchBar";
import { hotelsData, roomData } from "../../utils";

function Login() {
  return (
    <div className="flex flex-col min-h-screen w-screen">
      <Header />

      <NavHeader />

      <SearchBar />

      <RoomListings roomsData={roomData} />

      <main className="container mx-auto my-12 px-4 flex-grow">
        {/* HotelListings Component renders all hotel cards  */}
        <h2 className="text-3xl font-bold text-gray-800 mb-8 text-center">
          Hotéis em Destaque
        </h2>
        <HotelListings hotelsData={hotelsData} />
      </main>

      <Footer />
    </div>
  );
}

export default Login;
