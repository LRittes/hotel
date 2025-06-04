export default function Footer() {
  return (
    <footer className="bg-gray-800 text-white py-8 px-6 md:px-10 mt-auto">
      <div className="container mx-auto text-center">
        <p>&copy; 2025 LuxStay. All rights reserved.</p>
        <div className="flex justify-center space-x-6 mt-4">
          <a href="#" className="hover:text-blue-400 transition duration-300">
            Privacy Policy
          </a>
          <a href="#" className="hover:text-blue-400 transition duration-300">
            Terms of Service
          </a>
        </div>
      </div>
    </footer>
  );
}
