import { useLocation } from "react-router-dom";
import RoomCard from "./RoomCard";

const RoomListings = ({ roomsData }) => {
  const location = useLocation();
  const hotelData = location.state;

  return (
    <div className="grid grid-cols-1 gap-8">
      {roomsData.map((room, index) => (
        <RoomCard key={index} {...room} hotelData={hotelData} />
      ))}
    </div>
  );
};

export default RoomListings;
