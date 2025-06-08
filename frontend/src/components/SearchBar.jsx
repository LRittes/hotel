import { useContext } from "react";
import CalendarPick from "./CalendarPick";
import { UserContext } from "../context/UserContext";

const SearchBar = () => {
  const { reserva } = useContext(UserContext);
  const handleDateChange = (checkIn, checkOut) => {
    reserva(checkIn, checkOut);
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

        <div className="bg-yellow-500 border-4 border-yellow-500 rounded-lg p-2 flex flex-col md:flex-row items-center justify-between space-y-4 md:space-y-0 md:space-x-4">
          <div className="flex items-center bg-white rounded-md p-3 w-full md:w-1/3">
            <i className="fas fa-bed text-gray-500 mr-3 text-xl"></i>
            <input
              type="text"
              placeholder="Para onde você vai?"
              className="flex-grow outline-none text-lg text-gray-800"
            />
          </div>
          <CalendarPick onDateChange={handleDateChange} />
          <button className="bg-blue-600 hover:bg-blue-700 text-white px-8 py-3 rounded-md font-bold text-lg w-full md:w-auto transition duration-300">
            Pesquisar
          </button>
        </div>
      </div>
    </section>
  );
};

export default SearchBar;
