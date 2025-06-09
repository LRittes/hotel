import React, { createContext, useState, useCallback } from "react";

export const UserContext = createContext(null);

export const UserProvider = ({ children }) => {
  const [user, setUser] = useState({ email: "Convidado", isLoggedIn: false });
  const [reservaData, setDataReserva] = useState({
    checkIn: null,
    checkOut: null,
  });
  const [hotels, setHotels] = useState({
    all: [],
    filtered: [],
    firstLoadCompleted: false,
    showAllHotels: true,
  });

  const login = useCallback((data) => {
    setUser({ ...data, isLoggedIn: true });
  }, []);

  const logout = useCallback(() => {
    setUser({ email: "Convidado", isLoggedIn: false });
  }, []);

  const reserva = useCallback((checkIn, checkOut) => {
    setDataReserva({
      checkIn: checkIn,
      checkOut: checkOut,
    });
  }, []);

  const setAllHotels = useCallback((allHotelsData) => {
    setHotels((prevHotels) => ({
      ...prevHotels,
      all: allHotelsData,
      filtered: allHotelsData,
      firstLoadCompleted: true,
      showAllHotels: true,
    }));
  }, []);

  const setFilteredHotels = useCallback((filteredHotelsData) => {
    setHotels((prevHotels) => ({
      ...prevHotels,
      filtered: filteredHotelsData,
      showAllHotels: false,
    }));
  }, []);

  const resetHotelFilter = useCallback(() => {
    setHotels((prevHotels) => ({
      ...prevHotels,
      showAllHotels: true,
    }));
  }, []);

  const contextValue = {
    user,
    login,
    logout,
    reserva,
    reservaData,
    hotels,
    setAllHotels,
    setFilteredHotels,
    resetHotelFilter,
  };

  return (
    <UserContext.Provider value={contextValue}>
      {children}{" "}
    </UserContext.Provider>
  );
};
