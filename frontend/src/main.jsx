import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import "./main.css";

import { createBrowserRouter, RouterProvider } from "react-router";

import Home from "./pages/Home";
import RoomPage from "./pages/Room";

let router = createBrowserRouter([
  {
    path: "/",
    Component: Home,
    // loader: loadRootData,
  },
  {
    path: "/room/:id",
    Component: RoomPage,
  },
]);

createRoot(document.getElementById("root")).render(
  <StrictMode>
    <RouterProvider router={router} />
  </StrictMode>
);
