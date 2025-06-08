// Example hotel data in JSON format
export const hotelsData = [
  {
    imageUrl:
      "https://placehold.co/600x400/4a5568/ffffff?text=Intercity+Portofino",
    hotelName: "Intercity Portofino Florianópolis",
    rating: 4,
    type: "Hotel",
    features: [
      "Limpeza excepcional",
      "Acomodações que aceitam animais de estimação",
    ],
    distance: "7.1 km",
    score: 8.9,
    reviews: 2666,
    totalImages: 40,
  },
  {
    imageUrl:
      "https://placehold.co/600x400/6b7280/ffffff?text=Grand+Palace+Hotel",
    hotelName: "Grand Palace Hotel",
    rating: 5,
    type: "Hotel",
    features: [
      "Piscina aquecida",
      "Café da manhã incluso",
      "Estacionamento gratuito",
    ],
    distance: "2.5 km",
    score: 9.2,
    reviews: 1500,
    totalImages: 25,
  },
  {
    imageUrl:
      "https://placehold.co/600x400/a0aec0/ffffff?text=Beachfront+Resort",
    hotelName: "Beachfront Paradise Resort",
    rating: 4,
    type: "Resort",
    features: [
      "Vista para o mar",
      "Acesso direto à praia",
      "Restaurante premiado",
    ],
    distance: "15 km",
    score: 8.7,
    reviews: 3120,
    totalImages: 50,
  },
  {
    imageUrl: "https://placehold.co/600x400/ed8936/ffffff?text=Cozy+Inn",
    hotelName: "Cozy Downtown Inn",
    rating: 3,
    type: "Pousada",
    features: ["Localização central", "Ambiente familiar", "Wi-Fi grátis"],
    distance: "0.5 km",
    score: 7.8,
    reviews: 890,
    totalImages: 15,
  },
];

// Example room data
export const roomData = [
  {
    imageUrl:
      "https://placehold.co/600x400/38b2ac/ffffff?text=Ap+Garden+Augusta",
    roomName: "Ap Garden Augusta Sp UA 102",
    location: "Consolação, São Paulo",
    distanceFromCenter: "1,8 km",
    nearbyMetro: true,
    roomType: "Apartamento",
    description: "Apartamento inteiro • 1 quarto • 1 cozinha",
    beds: "2 camas (1 de solteiro, 1 de casal)",
    nights: 2,
    guests: 2,
    price: 406,
    taxesAndFees: 185,
    availabilityMessage: "Resta 1 unidade por esse preço no nosso site",
    showOnMapLink: true,
  },
  {
    imageUrl:
      "https://placehold.co/600x400/38b2ac/ffffff?text=Ap+Garden+Augusta",
    roomName: "Ap Garden Augusta Sp UA 102",
    location: "Consolação, São Paulo",
    distanceFromCenter: "1,8 km",
    nearbyMetro: true,
    roomType: "Apartamento",
    description: "Apartamento inteiro • 1 quarto • 1 cozinha",
    beds: "2 camas (1 de solteiro, 1 de casal)",
    nights: 2,
    guests: 2,
    price: 406,
    taxesAndFees: 185,
    availabilityMessage: "Resta 1 unidade por esse preço no nosso site",
    showOnMapLink: true,
  },
];

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
  // Verifica se a string é válida e não está vazia
  if (!str || typeof str !== "string") {
    return "";
  }
  // Divide a string em palavras, capitaliza a primeira letra de cada uma
  // e junta as palavras de volta
  return str.replace(/\b\w/g, (char) => char.toUpperCase());
}

// Function to format date for display
export const formatDate = (date, sep = "-") => {
  if (!date) return "";
  const day = String(date.getDate()).padStart(2, "0");
  const month = String(date.getMonth() + 1).padStart(2, "0"); // Months are 0-indexed
  const year = date.getFullYear();
  return `${year}${sep}${month}${sep}${day}`.toString();
};

export function getFormattedCurrentDateYMD() {
  const date = new Date();
  return formatDate(date);
}

export const exampleReservas = [
  {
    id: 25,
    dataReserva: "2025-07-14",
    dataCheckinPrevista: "2025-11-07",
    dataCheckoutPrevisto: "2025-11-08",
    tipoQuartoId: 1,
    quartoId: 1,
    hotelId: 1,
    camaExtra: false,
    clienteId: 1,
    valor: 100.0,
    status: "confirmada",
  },
  {
    id: 26,
    dataReserva: "2025-07-15",
    dataCheckinPrevista: "2025-12-01",
    dataCheckoutPrevisto: "2025-12-05",
    tipoQuartoId: 2,
    quartoId: 3,
    hotelId: 1,
    camaExtra: true,
    clienteId: 2,
    valor: 450.5,
    status: "pendente",
  },
  {
    id: 27,
    dataReserva: "2025-07-16",
    dataCheckinPrevista: "2026-01-10",
    dataCheckoutPrevisto: "2026-01-12",
    tipoQuartoId: 1,
    quartoId: 2,
    hotelId: 2,
    camaExtra: false,
    clienteId: 1,
    valor: 220.0,
    status: "cancelada",
  },
];
