// src/contexts/UserContext.js
import React, { createContext, useState, useCallback } from "react";

// 1. Cria o contexto. Pode passar um valor padrão, mas geralmente é null ou um objeto com a estrutura esperada.
export const UserContext = createContext(null);

// 2. Cria um componente Provider que irá envolver sua aplicação (ou parte dela)
export const UserProvider = ({ children }) => {
  const [user, setUser] = useState({ email: "Convidado", isLoggedIn: false });
  const [reservaData, setDataReserva] = useState({
    checkIn: null, // Armazenará objetos Date ou null
    checkOut: null, // Armazenará objetos Date ou null
  });

  // Exemplo de função que pode ser passada para atualizar o estado do usuário
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

  // O valor passado para o `value` do Provider será acessível por todos os Consumers
  const contextValue = {
    user,
    login,
    logout,
    reserva,
    reservaData,
  };

  return (
    <UserContext.Provider value={contextValue}>
      {children}{" "}
    </UserContext.Provider>
  );
};
