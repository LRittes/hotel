import { useLocation } from "react-router-dom";
import RoomCard from "./RoomCard";

const RoomListings = ({ roomsData }) => {
  const location = useLocation();
  const hotelData = location.state;

  function rc(room, index) {
    console.log(index, room);
    return <RoomCard key={index} {...room} hotelData={hotelData} />;
  }

  return <div className="grid grid-cols-1 gap-8">{roomsData.map(rc)}</div>;
};

export default RoomListings;
