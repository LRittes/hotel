export default function Header() {
  return (
    <header className="bg-blue-800 text-white py-3 px-4 md:px-8">
      <div className="container mx-auto flex justify-between items-center flex-wrap">
        <a href="#" className="text-3xl font-bold tracking-tight">
          FallAsleep
        </a>

        <div className="flex items-center space-x-4 md:space-x-6 mt-2 md:mt-0">
          <span className="text-lg font-medium">BRL</span>
          <a
            href="#"
            className="text-white hover:text-blue-200 transition duration-300"
          >
            <i className="fas fa-question-circle text-xl"></i>
          </a>
          <a
            href="register.html"
            className="bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded-md shadow-md transition duration-300 text-base font-medium"
          >
            Cadastre-se
          </a>
          <a
            href="login.html"
            className="bg-white text-blue-800 px-4 py-2 rounded-md shadow-md hover:bg-blue-100 transition duration-300 text-base font-medium"
          >
            Login
          </a>
        </div>
      </div>
    </header>
  );
}
