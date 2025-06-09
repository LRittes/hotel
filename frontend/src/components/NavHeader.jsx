import { useState } from "react";

export default function NavHeader() {
  const [activeIndex, setActiveIndex] = useState(0);

  const navItems = [
    { icon: "fas fa-bed", text: "Hospedagens" },
    { icon: "fas fa-plane", text: "Voos" },
    { icon: "fas fa-car", text: "Aluguel de carros" },
    { icon: "fas fa-ticket-alt", text: "Atrações" },
    { icon: "fas fa-taxi", text: "Táxis (aeroporto)" },
  ];

  return (
    <nav className="bg-blue-800 text-white pb-4 px-4 md:px-8">
      <div className="container mx-auto flex flex-wrap justify-center items-center space-x-4 md:space-x-8">
        {navItems.map((item, index) => (
          <a
            key={index}
            className={`
              flex items-center space-x-2 py-2 px-4 rounded-full
              transition duration-300 cursor-pointer
              ${
                activeIndex === index
                  ? "border border-white bg-blue-700"
                  : "hover:bg-blue-700"
              }
            `}
            onClick={(e) => {
              e.preventDefault();
              setActiveIndex(index);
              console.log(`${item.text} clicado e ativado!`);
            }}
          >
            <i className={item.icon}></i>
            <span className="text-base">{item.text}</span>
          </a>
        ))}
      </div>
    </nav>
  );
}
