export default function NavHeader() {
  return (
    <nav className="bg-blue-800 text-white pb-4 px-4 md:px-8">
      <div className="container mx-auto flex flex-wrap justify-center items-center space-x-4 md:space-x-8">
        <a
          href="#"
          className="flex items-center space-x-2 py-2 px-4 rounded-full border border-white bg-blue-700"
        >
          <i className="fas fa-bed"></i>
          <span className="text-base">Hospedagens</span>
        </a>
        <a
          href="#"
          className="flex items-center space-x-2 py-2 px-4 rounded-full hover:bg-blue-700 transition duration-300"
        >
          <i className="fas fa-plane"></i>
          <span className="text-base">Voos</span>
        </a>
        <a
          href="#"
          className="flex items-center space-x-2 py-2 px-4 rounded-full hover:bg-blue-700 transition duration-300"
        >
          <i className="fas fa-car"></i>
          <span className="text-base">Aluguel de carros</span>
        </a>
        <a
          href="#"
          className="flex items-center space-x-2 py-2 px-4 rounded-full hover:bg-blue-700 transition duration-300"
        >
          <i className="fas fa-ticket-alt"></i>
          <span className="text-base">Atrações</span>
        </a>
        <a
          href="#"
          className="flex items-center space-x-2 py-2 px-4 rounded-full hover:bg-blue-700 transition duration-300"
        >
          <i className="fas fa-taxi"></i>
          <span className="text-base">Táxis (aeroporto)</span>
        </a>
      </div>
    </nav>
  );
}
