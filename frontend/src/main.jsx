import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import "./main.css";

import { createBrowserRouter, RouterProvider } from "react-router";

import Home from "./pages/Home";
import RoomPage from "./pages/Room";
import { UserProvider } from "./context/UserContext";
import LoginPage from "./pages/Login";
import RegisterPage from "./pages/Register";
import ConfigPage from "./pages/Config";

let router = createBrowserRouter([
  {
    path: "/",
    Component: Home,
  },
  {
    path: "/room/:id",
    Component: RoomPage,
  },
  {
    path: "/login",
    Component: LoginPage,
  },
  {
    path: "/register",
    Component: RegisterPage,
  },
  {
    path: "/config",
    Component: ConfigPage,
  },
]);

createRoot(document.getElementById("root")).render(
  <StrictMode>
    <UserProvider>
      <RouterProvider router={router} />
    </UserProvider>
  </StrictMode>
);
