import { useCallback, useEffect, useRef, useState } from "react";

const DestinationPicker = ({
  onSelectDestination,
  hotelFiltered,
  onClose,
  hotels,
}) => {
  const pickerRef = useRef(null);

  const getCityAndCountryFromAddress = (endereco) => {
    const enderecoArr = endereco.split(",");
    const city = enderecoArr[2] ? enderecoArr[2].trim() : "Cidade Desconhecida";
    const country = enderecoArr[3]
      ? enderecoArr[3].trim()
      : "País Desconhecido";
    return { city, country };
  };

  const uniqueFilteredDestinations = useCallback(() => {
    const uniqueMap = new Map();

    hotels.forEach((hotel) => {
      const { city, country } = getCityAndCountryFromAddress(hotel.endereco);
      const key = `${city},${country}`.toLowerCase();

      if (!uniqueMap.has(key)) {
        uniqueMap.set(key, { cidade: city, pais: country });
      }
    });

    return Array.from(uniqueMap.values());
  }, [hotels]);

  const destinationsToDisplay = uniqueFilteredDestinations();

  const handleChoice = useCallback(
    (dest) => {
      onSelectDestination(dest.cidade);

      if (hotelFiltered) {
        const filteredHotelsData = hotels.filter(
          (hotel) =>
            getCityAndCountryFromAddress(hotel.endereco).city.toLowerCase() ===
            dest.cidade.toLowerCase()
        );
        hotelFiltered(filteredHotelsData);
      }

      onClose();
    },
    [onSelectDestination, hotelFiltered, hotels, onClose]
  );

  useEffect(() => {
    const handleClickOutside = (event) => {
      if (pickerRef.current && !pickerRef.current.contains(event.target)) {
        onClose();
      }
    };

    document.addEventListener("mousedown", handleClickOutside);
    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, [onClose]);

  return (
    <div
      ref={pickerRef}
      className="absolute top-full left-0 mt-4 bg-white rounded-lg shadow-xl p-4 z-50 w-full md:w-96 max-h-80 overflow-y-auto border border-gray-200"
    >
      <h3 className="text-lg font-semibold text-gray-800 mb-3">
        Destinos em alta
      </h3>
      <ul>
        {destinationsToDisplay.map((dest, index) => (
          <li
            key={`${dest.cidade}-${dest.pais}-${index}`}
            className="flex items-center p-3 rounded-lg hover:bg-gray-100 cursor-pointer transition duration-200"
            onClick={() => handleChoice(dest)}
          >
            <i className="fas fa-map-marker-alt text-gray-500 mr-3"></i>
            <div>
              <p className="font-medium text-gray-800">{dest.cidade}</p>
              <p className="text-sm text-gray-600">{dest.pais}</p>
            </div>
          </li>
        ))}
        {destinationsToDisplay.length === 0 && (
          <li className="p-3 text-gray-500 text-sm">
            Nenhum destino encontrado.
          </li>
        )}
      </ul>
    </div>
  );
};

export default DestinationPicker;
