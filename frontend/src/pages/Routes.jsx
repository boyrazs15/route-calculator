import { useEffect, useState } from 'react';
import axios from 'axios';

export default function RoutesPage() {
  const [locations, setLocations] = useState([]);
  const [origin, setOrigin] = useState('');
  const [destination, setDestination] = useState('');
  const [date, setDate] = useState('');
  const [routes, setRoutes] = useState([]);
  const [selectedRoute, setSelectedRoute] = useState(null);

  useEffect(() => {
    axios.get('http://localhost:8080/api/locations')
      .then(res => setLocations(res.data))
      .catch(err => console.error('Error fetching locations:', err));
  }, []);

  const handleSearch = () => {
    if (!origin || !destination || !date) return;

    axios.get(`http://localhost:8080/api/routes?originLocationId=${origin}&destinationLocationId=${destination}&date=${date}`)
      .then(res => setRoutes(res.data))
      .catch(err => console.error('Error fetching routes:', err));
  };

  return (
    <div style={{ display: 'flex', padding: '1rem' }}>
      <div style={{ flex: 1 }}>
        <h2>Available Routes</h2>
        <div>
          <select onChange={(e) => setOrigin(e.target.value)} defaultValue="">
            <option value="">Select Origin</option>
            {locations.map((loc) => (
                <option key={loc.id} value={loc.id}>{loc.name}</option>
            ))}
          </select>
          <select onChange={(e) => setDestination(e.target.value)} defaultValue="">
            <option value="">Select Destination</option>
            {locations.map((loc) => (
                <option key={loc.id} value={loc.id}>{loc.name}</option>
            ))}
          </select>
          <input type="date" onChange={(e) => setDate(e.target.value)} />
          <button onClick={handleSearch}>Search</button>
        </div>

        <ul style={{ marginTop: '1rem', padding: 0 }} className="route-list">
          {routes.map((route, idx) => (
            <li key={idx} onClick={() => setSelectedRoute(route)}>
              {route.description}
            </li>
          ))}
        </ul>
      </div>

      {selectedRoute && (
        <div className="route-details">
          <h3>Route Details</h3>
          {selectedRoute.beforeFlightTransfer && (
            <>
              <div className="route-dot"><strong>{selectedRoute.beforeFlightTransfer.origin}</strong></div>
              <div className="route-dot">{selectedRoute.beforeFlightTransfer.transportationType}</div>
            </>
          )}
          <div className="route-dot"><strong>{selectedRoute.flight.origin}</strong></div>
          <div className="route-dot">{selectedRoute.flight.transportationType}</div>
          <div className="route-dot"><strong>{selectedRoute.flight.destination}</strong></div>
          {selectedRoute.afterFlightTransfer && (
            <>
              <div className="route-dot">{selectedRoute.afterFlightTransfer.transportationType}</div>
              <div className="route-dot"><strong>{selectedRoute.afterFlightTransfer.destination}</strong></div>
            </>
          )}
          <button onClick={() => setSelectedRoute(null)}>Close</button>
        </div>
      )}
    </div>
  );
}
