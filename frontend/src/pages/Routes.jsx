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
          <select className="select-input" onChange={(e) => setOrigin(e.target.value)} defaultValue="">
            <option value="">Select Origin</option>
            {locations.map((loc) => (
                <option key={loc.id} value={loc.id}>{loc.name}</option>
            ))}
          </select>
          <select className="select-input" onChange={(e) => setDestination(e.target.value)} defaultValue="">
            <option value="">Select Destination</option>
            {locations.map((loc) => (
                <option key={loc.id} value={loc.id}>{loc.name}</option>
            ))}
          </select>
          <input className="select-input" type="date" onChange={(e) => setDate(e.target.value)} />
          <button className="button" onClick={handleSearch}>Search 🔍</button>
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
          <div style={{ marginLeft: '2rem', borderLeft: '1px dotted #ccc', paddingLeft: '1rem' }}>
            <h3>Route Details</h3>
            <ul style={{ listStyle: 'none', paddingLeft: 0 }}>
              {selectedRoute.beforeFlightTransfer && (
                  <>
                    <li><strong>{selectedRoute.beforeFlightTransfer.origin}</strong></li>
                    <li style={{ margin: '0.2rem 0' }}>↓ {selectedRoute.beforeFlightTransfer.transportationType}</li>
                  </>
              )}

              <li><strong>{selectedRoute.flight.origin}</strong></li>
              <li style={{ margin: '0.2rem 0' }}>↓ {selectedRoute.flight.transportationType}</li>
              <li><strong>{selectedRoute.flight.destination}</strong></li>

              {selectedRoute.afterFlightTransfer && (
                  <>
                    <li style={{ margin: '0.2rem 0' }}>↓ {selectedRoute.afterFlightTransfer.transportationType}</li>
                    <li><strong>{selectedRoute.afterFlightTransfer.destination}</strong></li>
                  </>
              )}
            </ul>
            <button onClick={() => setSelectedRoute(null)}>Close</button>
          </div>
      )}
    </div>
  );
}
