import { useEffect, useState } from 'react';
import axios from 'axios';

export default function Transportations() {
  const [locations, setLocations] = useState([]);
  const [transportations, setTransportations] = useState([]);
  const [form, setForm] = useState({
    origin: '',
    destination: '',
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
        setForm({ origin: '', destination: '', transportationType: 'FLIGHT' });
      });
  };

  const handleDelete = (id) => {
    axios.delete(`http://localhost:8080/api/transportations/${id}`)
      .then(fetchTransportations);
  };

  return (
    <div style={{ padding: '1rem' }}>
      <h2>Transportations</h2>
      <select name="origin" value={form.origin} onChange={handleChange}>
        <option value="">Select Origin</option>
        {locations.map(loc => (
          <option key={loc.code} value={loc.code}>{loc.name}</option>
        ))}
      </select>
      <select name="destination" value={form.destination} onChange={handleChange}>
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
            {t.origin} ➡ {t.destination} ({t.transportationType})
            <button onClick={() => handleDelete(t.id)}>Delete</button>
          </li>
        ))}
      </ul>
    </div>
  );
}
