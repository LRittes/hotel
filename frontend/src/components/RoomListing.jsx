import RoomCard from "./RoomCard";

// HotelListings Component (NEW)
const RoomListings = ({ roomsData }) => {
  return (
    <div className="grid grid-cols-1 gap-8">
      {roomsData.map((room, index) => (
        <RoomCard
          key={index} // Using index as key, consider a unique ID from data if available
          {...room}
        />
      ))}
    </div>
  );
};

export default RoomListings;
