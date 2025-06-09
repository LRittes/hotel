import { useCallback, useContext, useRef, useState } from "react";
import CalendarPick from "./CalendarPick";
import { UserContext } from "../context/UserContext";
import DestinationPicker from "./DestinoPicker";
import { useNavigate } from "react-router-dom";

const SearchBar = () => {
  const { reserva, reservaData, hotels, setFilteredHotels, resetHotelFilter } =
    useContext(UserContext);
  const navigate = useNavigate();

  const [isDestinationPickerOpen, setIsDestinationPickerOpen] = useState(false);
  const [selectedDestination, setSelectedDestination] = useState("");

  const destinationInputRef = useRef(null);

  const handleDateChange = useCallback(
    (checkIn, checkOut) => {
      reserva(checkIn, checkOut);
      console.log(
        "SearchBar: Datas de reserva atualizadas no contexto:",
        reservaData
      );
    },
    [reserva, reservaData]
  );

  const handleSelectDestination = useCallback((destination) => {
    setSelectedDestination(destination);
    console.log("Destino selecionado:", destination);
  }, []);

  const handleSearch = () => {
    if (selectedDestination) {
      const filtered = hotels.all.filter((hotel) => {
        const hotelCity = hotel.endereco.split(",")[2]?.trim();
        return (
          hotelCity &&
          hotelCity.toLowerCase().includes(selectedDestination.toLowerCase())
        );
      });
      setFilteredHotels(filtered);
      console.log(
        "Filtrando hotéis por:",
        selectedDestination,
        "Resultado:",
        filtered
      );
    } else {
      resetHotelFilter();
      console.log("Nenhum destino selecionado, mostrando todos os hotéis.");
    }
    navigate("/");
  };

  const clearInput = () => {
    setSelectedDestination("");
    setIsDestinationPickerOpen(false);
  };

  return (
    <section className="bg-blue-800 text-white py-12 md:py-20 px-4 md:px-8">
      <div className="container mx-auto text-center md:text-left">
        <h1 className="text-4xl md:text-5xl font-extrabold mb-4 leading-tight">
          Encontre sua próxima estadia
        </h1>
        <p className="text-lg md:text-xl font-light mb-8">
          Encontre ofertas em hotéis, casas, apartamentos e muito mais ...
        </p>

        <div
          className="bg-yellow-500 border-4 border-yellow-500 
        rounded-lg p-2 flex flex-col md:flex-row items-center
         justify-between space-y-4 md:space-y-0 md:space-x-4 relative"
        >
          <div
            className="flex items-center bg-white rounded-md 
            p-3 w-full md:w-1/2 cursor-pointer relative"
            ref={destinationInputRef}
          >
            <i className="fas fa-bed text-gray-500 mr-3 text-xl"></i>
            <input
              type="text"
              placeholder="Para onde você vai?"
              className="flex-grow outline-none text-lg text-gray-800 cursor-pointer"
              value={selectedDestination}
              onClick={() =>
                setIsDestinationPickerOpen(!isDestinationPickerOpen)
              }
              readOnly
            />
            {isDestinationPickerOpen && (
              <DestinationPicker
                hotels={hotels.all}
                onSelectDestination={handleSelectDestination}
                onClose={() => setIsDestinationPickerOpen(false)}
              />
            )}
            <button onClick={clearInput}>
              <i className="fas fa-close text-gray-500 mr-3 text-xl cursor-pointer"></i>
            </button>
          </div>

          <div className="flex items-center bg-white rounded-md p-1/2 w-full md:w-1/2 ">
            <CalendarPick onDateChange={handleDateChange} />
          </div>

          <button
            onClick={handleSearch}
            className="bg-blue-600 hover:bg-blue-700 text-white px-8 py-3 rounded-md font-bold text-lg 
            dw-full md:w-auto transition duration-300 cursor-pointer"
          >
            Pesquisar
          </button>
        </div>
      </div>
      {selectedDestination && (
        <div className="container mx-auto mt-8 p-4 bg-blue-50 text-blue-800 rounded-lg">
          <p>
            Destino selecionado para busca:{" "}
            <span className="font-semibold">{selectedDestination}</span>
          </p>
        </div>
      )}
    </section>
  );
};

export default SearchBar;
