export const hotelFeatures = [
  "Piscina aquecida",
  "Academia completa",
  "Café da manhã incluso",
  "Estacionamento gratuito",
  "Wi-Fi de alta velocidade",
  "Serviço de quarto 24h",
  "Restaurante no local",
  "Bar/lounge",
  "Spa e centro de bem-estar",
  "Salas de reunião/eventos",
  "Lavanderia self-service",
  "Translado para o aeroporto",
  "Clube infantil",
  "Acesso para cadeirantes",
  "Concierge 24h",
  "Cofre no quarto",
  "Mini bar",
  "TV de tela plana com canais a cabo",
  "Ar condicionado",
  "Varanda privativa",
];

export function capitalizeWords(str) {
  if (!str || typeof str !== "string") {
    return "";
  }

  return str.replace(/\b\w/g, (char) => char.toUpperCase());
}

export const formatDate = (date, sep = "-") => {
  if (!date) return "";
  const day = String(date.getDate()).padStart(2, "0");
  const month = String(date.getMonth() + 1).padStart(2, "0");
  const year = date.getFullYear();
  return `${year}${sep}${month}${sep}${day}`.toString();
};

export function getFormattedCurrentDateYMD() {
  const date = new Date();
  return formatDate(date);
}
