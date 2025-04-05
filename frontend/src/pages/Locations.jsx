import { useEffect, useState } from 'react';
import axios from 'axios';

export default function Locations() {
    const [locations, setLocations] = useState([]);
    const [form, setForm] = useState({ name: '', city: '', country: '', locationCode: '' });

    const baseUrl = 'http://localhost:8080/api/locations';
    useEffect(() => {
        fetchLocations();
    }, []);

    const fetchLocations = () => {
        axios.get(baseUrl)
            .then(res => setLocations(res.data))
            .catch(err => console.error('Error fetching locations:', err));
    };

    const handleChange = (e) => {
        setForm({ ...form, [e.target.name]: e.target.value });
    };

    const handleSubmit = () => {
        axios.post(baseUrl, form)
            .then(() => {
                fetchLocations();
                setForm({ name: '', city: '', country: '', locationCode: '' });
            })
            .catch(err => console.error('Error creating location:', err));
    };

    const handleUpdate = (id) => {

    };

    const handleDelete = (id) => {
        axios.delete(`${baseUrl}/${id}`)
            .then(fetchLocations)
            .catch(err => console.error('Error deleting location:', err));
    };

    return (
        <div style={{ padding: '1rem' }}>
            <h2>Locations</h2>
            <input name="name" placeholder="Name" value={form.name} onChange={handleChange} />
            <input name="city" placeholder="City" value={form.city} onChange={handleChange} />
            <input name="country" placeholder="Country" value={form.country} onChange={handleChange} />
            <input name="locationCode" placeholder="Location Code" value={form.locationCode} onChange={handleChange} />
            <button className="button" onClick={handleSubmit}>Create</button>

            <ul>
                {locations.map(loc => (
                    <li key={loc.locationCode}>
                        {loc.name} ({loc.city}, {loc.country}) - {loc.locationCode}
                        <button className="update-button" onClick={() => handleUpdate(loc.id)}>Update</button>
                        <button className="delete-button" onClick={() => handleDelete(loc.id)}>Delete</button>
                    </li>
                ))}
            </ul>
        </div>
    );
}
