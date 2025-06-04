import HotelInfoCard from "./HotelInfoCard";

// HotelListings Component (NEW)
const HotelListings = ({ hotelsData }) => {
  return (
    <div className="grid grid-cols-1 gap-8">
      {hotelsData.map((hotel, index) => (
        <HotelInfoCard
          key={index} // Using index as key, consider a unique ID from data if available
          imageUrl={hotel.imageUrl}
          hotelName={hotel.hotelName}
          rating={hotel.rating}
          type={hotel.type}
          features={hotel.features}
          distance={hotel.distance}
          score={hotel.score}
          reviews={hotel.reviews}
          totalImages={hotel.totalImages}
        />
      ))}
    </div>
  );
};

export default HotelListings;
