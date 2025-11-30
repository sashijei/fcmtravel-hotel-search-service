import { useEffect, useState } from "react";

const API_BASE = "http://localhost:8080/api/hotels/db";
// To use aggregated providers instead, change to:
// const API_BASE = "http://localhost:8080/api/hotels/aggregated";

function App() {
  const [hotels, setHotels] = useState([]);
  const [city, setCity] = useState("");
  const [state, setState] = useState("");
  const [hotelType, setHotelType] = useState("");
  const [roomType, setRoomType] = useState("");
  const [sortBy, setSortBy] = useState("price");
  const [sortDirection, setSortDirection] = useState("asc");

  const [page, setPage] = useState(0);
  const [size, setSize] = useState(5);
  const [totalPages, setTotalPages] = useState(0);
  const [totalElements, setTotalElements] = useState(0);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    const fetchHotels = async () => {
      setLoading(true);

      const params = new URLSearchParams();
      if (city) params.append("city", city);
      if (state) params.append("state", state);
      if (hotelType) params.append("hotelTypes", hotelType);
      if (roomType) params.append("roomTypes", roomType);
      params.append("sortBy", sortBy);
      params.append("sortDirection", sortDirection);
      params.append("page", page);
      params.append("size", size);

      const url = `${API_BASE}?${params.toString()}`;
      const res = await fetch(url);
      const data = await res.json();

      setHotels(data.content || []);
      setTotalPages(data.totalPages || 0);
      setTotalElements(data.totalElements || 0);
      setLoading(false);
    };

    fetchHotels();
  }, [city, state, hotelType, roomType, sortBy, sortDirection, page, size]);

  const resetFilters = () => {
    setCity("");
    setState("");
    setHotelType("");
    setRoomType("");
    setSortBy("price");
    setSortDirection("asc");
    setPage(0);
  };

  return (
    <div style={{ padding: "20px" }}>
      <h1>Hotel Search</h1>

      {/* Filters */}
      <div
        style={{
          display: "grid",
          gridTemplateColumns: "repeat(4, minmax(0, 1fr))",
          gap: "12px",
          marginBottom: "16px",
        }}
      >
        <input
          placeholder="City"
          value={city}
          onChange={(e) => {
            setCity(e.target.value);
            setPage(0);
          }}
        />
        <input
          placeholder="State"
          value={state}
          onChange={(e) => {
            setState(e.target.value);
            setPage(0);
          }}
        />
        <select
          value={hotelType}
          onChange={(e) => {
            setHotelType(e.target.value);
            setPage(0);
          }}
        >
          <option value="">All Hotel Types</option>
          <option value="FIVE_STAR">5 Star</option>
          <option value="FOUR_STAR">4 Star</option>
          <option value="THREE_STAR">3 Star</option>
          <option value="BUDGET">Budget</option>
        </select>

        <select
          value={roomType}
          onChange={(e) => {
            setRoomType(e.target.value);
            setPage(0);
          }}
        >
          <option value="">All Room Types</option>
          <option value="STANDARD">Standard</option>
          <option value="DELUXE">Deluxe</option>
          <option value="SUITE">Suite</option>
        </select>
      </div>

      {/* Sort + page size */}
      <div style={{ display: "flex", gap: "12px", marginBottom: "16px" }}>
        <select
          value={sortBy}
          onChange={(e) => {
            setSortBy(e.target.value);
            setPage(0);
          }}
        >
          <option value="price">Sort by Price</option>
          <option value="rating">Sort by Rating</option>
          <option value="name">Sort by Name</option>
        </select>

        <select
          value={sortDirection}
          onChange={(e) => {
            setSortDirection(e.target.value);
            setPage(0);
          }}
        >
          <option value="asc">Ascending</option>
          <option value="desc">Descending</option>
        </select>

        <select
          value={size}
          onChange={(e) => {
            setSize(Number(e.target.value));
            setPage(0);
          }}
        >
          <option value={5}>5 per page</option>
          <option value={10}>10 per page</option>
          <option value={20}>20 per page</option>
        </select>

        <button onClick={resetFilters}>Reset</button>
      </div>

      {/* Table + pagination */}
      {loading ? (
        <p>Loading...</p>
      ) : (
        <>
          <p>
            Showing page {page + 1} of {totalPages} (Total {totalElements} hotels)
          </p>
          <table
            border="1"
            cellPadding="8"
            style={{
              borderCollapse: "collapse",
              width: "100%",
              backgroundColor: "#fff",
            }}
          >
            <thead>
              <tr>
                <th>Name</th>
                <th>Hotel Type</th>
                <th>Room Type</th>
                <th>Rating</th>
                <th>City</th>
                <th>State</th>
                <th>Price / Night</th>
              </tr>
            </thead>
            <tbody>
              {hotels.map((h) => (
                <tr key={h.id}>
                  <td>{h.name}</td>
                  <td>{h.hotelType}</td>
                  <td>{h.roomType}</td>
                  <td>{h.rating}</td>
                  <td>{h.city}</td>
                  <td>{h.state}</td>
                  <td>{h.finalPricePerNight}</td>
                </tr>
              ))}
            </tbody>
          </table>

          <div
            style={{
              marginTop: "12px",
              display: "flex",
              gap: "8px",
              alignItems: "center",
            }}
          >
            <button
              onClick={() => setPage((p) => Math.max(p - 1, 0))}
              disabled={page === 0}
            >
              Prev
            </button>
            <span>
              Page {page + 1} / {totalPages}
            </span>
            <button
              onClick={() =>
                setPage((p) => (p + 1 < totalPages ? p + 1 : p))
              }
              disabled={page + 1 >= totalPages}
            >
              Next
            </button>
          </div>
        </>
      )}
    </div>
  );
}

export default App;
