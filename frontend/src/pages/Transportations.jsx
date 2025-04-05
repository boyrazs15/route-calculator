import { useEffect, useState } from 'react';
import axios from 'axios';

export default function Transportations() {
  const [locations, setLocations] = useState([]);
  const [transportations, setTransportations] = useState([]);
  const [form, setForm] = useState({
    originLocation: '',
    destinationLocation: '',
    transportationType: 'FLIGHT'
  });

  useEffect(() => {
    axios.get('http://localhost:8080/api/locations')
      .then(res => setLocations(res.data));
    fetchTransportations();
  }, []);

  const fetchTransportations = () => {
    axios.get('http://localhost:8080/api/transportations')
      .then(res => setTransportations(res.data));
  };

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = () => {
    axios.post('http://localhost:8080/api/transportations', form)
      .then(() => {
        fetchTransportations();
        setForm({ originLocation: '', destinationLocation: '', transportationType: 'FLIGHT' });
      });
  };

  const handleDelete = (id) => {
    axios.delete(`http://localhost:8080/api/transportations/${id}`)
      .then(fetchTransportations);
  };

  return (
    <div style={{ padding: '1rem' }}>
      <h2>Transportations</h2>
      <select name="originLocation" value={form.originLocation} onChange={handleChange}>
        <option value="">Select Origin</option>
        {locations.map(loc => (
          <option key={loc.code} value={loc.code}>{loc.name}</option>
        ))}
      </select>
      <select name="destinationLocation" value={form.destinationLocation} onChange={handleChange}>
        <option value="">Select Destination</option>
        {locations.map(loc => (
          <option key={loc.code} value={loc.code}>{loc.name}</option>
        ))}
      </select>
      <select name="transportationType" value={form.transportationType} onChange={handleChange}>
        <option value="FLIGHT">FLIGHT</option>
        <option value="BUS">BUS</option>
        <option value="SUBWAY">SUBWAY</option>
        <option value="UBER">UBER</option>
      </select>
      <button onClick={handleSubmit}>Create</button>

      <ul>
        {transportations.map(t => (
          <li key={t.id}>
            {t.originLocation.name} ➡ {t.destinationLocation.name} ({t.transportationType})
            <button onClick={() => handleDelete(t.id)}>Delete</button>
          </li>
        ))}
      </ul>
    </div>
  );
}
