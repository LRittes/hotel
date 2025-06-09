import React, { useState, useEffect, useCallback } from "react";
import api from "../service/api";

const RoomServicesModal = ({
  isOpen,
  onClose,
  onConfirmReservation,
  roomData,
}) => {
  const [selectedServices, setSelectedServices] = useState([]);
  const [services, setServices] = useState([]);
  const [totalPrice, setTotalPrice] = useState(roomData ? roomData.price : 0);

  useEffect(() => {
    if (isOpen && roomData) {
      fetchServices();
      setSelectedServices([]);
      setTotalPrice(roomData.price || 0);
    }
  }, [isOpen, roomData]);

  const fetchServices = useCallback(async () => {
    try {
      const response = await api.get("/servicos-extra");
      setServices(response.data);
    } catch (error) {
      console.error("Erro ao carregar serviços extra:", error);
    }
  }, []);

  const handleServiceToggle = useCallback((service) => {
    setSelectedServices((prevSelected) => {
      const isSelected = prevSelected.some((s) => s.id === service.id);
      let newSelected;
      if (isSelected) {
        newSelected = prevSelected.filter((s) => s.id !== service.id);
      } else {
        newSelected = [...prevSelected, service];
      }
      return newSelected;
    });
  }, []);

  useEffect(() => {
    let currentTotal = roomData ? roomData.price : 0;
    selectedServices.forEach((service) => {
      currentTotal += service.preco;
    });
    setTotalPrice(currentTotal);
  }, [selectedServices, roomData]);

  if (!isOpen) return null;

  return (
    <div
      className="fixed inset-0 bg-opacity-50 flex items-center justify-center z-50 p-4 backdrop-filter backdrop-blur-sm"
      onClick={onClose}
    >
      <div
        className="bg-white rounded-lg shadow-2xl p-6 w-full max-w-md md:max-w-xl lg:max-w-2xl transform transition-all duration-300 scale-100 opacity-100"
        onClick={(e) => e.stopPropagation()}
      >
        <div className="flex justify-between items-center mb-4 border-b pb-3">
          <h2 className="text-2xl font-bold text-gray-800">
            Serviços de Quarto
          </h2>
          <button
            onClick={onClose}
            className="text-gray-500 hover:text-gray-700 text-3xl leading-none cursor-pointer"
          >
            &times;
          </button>
        </div>

        {roomData && (
          <p className="text-gray-600 mb-4 text-center">
            Quarto selecionado:{" "}
            <span className="font-semibold">{roomData.numero}</span> (Andar:{" "}
            {roomData.andar})
            <br />
            Preço da diária: R${" "}
            {roomData.price
              ? roomData.price.toFixed(2).replace(".", ",")
              : "0,00"}
          </p>
        )}

        <div className="max-h-80 overflow-y-auto mb-6 pr-2">
          {services.length > 0 ? (
            services.map((service) => (
              <div
                key={service.id}
                className="flex items-center justify-between py-3 border-b last:border-b-0"
              >
                <div>
                  <p className="font-medium text-gray-800">
                    {service.descricao}
                  </p>
                  <p className="text-sm text-gray-600">
                    R$ {service.preco.toFixed(2).replace(".", ",")}
                  </p>
                </div>
                <label className="relative inline-flex items-center cursor-pointer">
                  <input
                    type="checkbox"
                    value=""
                    className="sr-only peer"
                    checked={selectedServices.some((s) => s.id === service.id)}
                    onChange={() => handleServiceToggle(service)}
                  />
                  <div className="w-11 h-6 bg-gray-200 peer-focus:outline-none peer-focus:ring-4 peer-focus:ring-blue-300 dark:peer-focus:ring-blue-800 rounded-full peer dark:bg-gray-700 peer-checked:after:translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-[2px] after:left-[2px] after:bg-white after:border after:border-gray-300 after:rounded-full after:h-5 after:w-5 after:transition-all dark:border-gray-600 peer-checked:bg-blue-600"></div>
                </label>
              </div>
            ))
          ) : (
            <p className="text-center text-gray-500">
              Nenhum serviço disponível.
            </p>
          )}
        </div>

        <div className="border-t pt-4 flex flex-col items-center">
          <p className="text-xl font-bold text-gray-900 mb-4">
            Total: R$ {totalPrice.toFixed(2).replace(".", ",")}
          </p>
          <button
            onClick={() => {
              onConfirmReservation(
                roomData,
                selectedServices,
                totalPrice - roomData.price
              );
              onClose();
            }}
            className="bg-green-600 hover:bg-green-700 text-white font-bold py-3 px-8 
            rounded-lg transition duration-300 focus:outline-none focus:ring-4 focus:ring-green-300 cursor-pointer"
          >
            Confirmar
          </button>
        </div>
      </div>
    </div>
  );
};

export default RoomServicesModal;
