import { useEffect, useState } from 'react';
import axios from 'axios';

export default function Transportations() {
    const [locations, setLocations] = useState([]);
    const [transportations, setTransportations] = useState([]);

    const [form, setForm] = useState({
        originLocationId: '',
        destinationLocationId: '',
        transportationType: 'FLIGHT',
        operatingDays: []
    });

    const daysOfWeek = [
        { id: 1, name: 'Pazartesi' },
        { id: 2, name: 'Salı' },
        { id: 3, name: 'Çarşamba' },
        { id: 4, name: 'Perşembe' },
        { id: 5, name: 'Cuma' },
        { id: 6, name: 'Cumartesi' },
        { id: 7, name: 'Pazar' }
    ];

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

    const toggleDay = (dayId) => {
        setForm(prev => ({
            ...prev,
            operatingDays: prev.operatingDays.includes(dayId)
                ? prev.operatingDays.filter(id => id !== dayId)
                : [...prev.operatingDays, dayId]
        }));
    };

    const handleSubmit = () => {
        axios.post('http://localhost:8080/api/transportations', form)
            .then(() => {
                fetchTransportations();
                setForm({
                    originLocationId: '',
                    destinationLocationId: '',
                    transportationType: 'FLIGHT',
                    operatingDays: []
                });
            });
    };

    const handleDelete = (id) => {
        axios.delete(`http://localhost:8080/api/transportations/${id}`)
            .then(fetchTransportations);
    };

    return (
        <div style={{ padding: '1rem' }}>
            <h2>Transportations</h2>

            <select name="originLocationId" value={form.originLocationId} onChange={handleChange}>
                <option value="">Select Origin</option>
                {locations.map(loc => (
                    <option key={loc.id} value={loc.id}>{loc.name}</option>
                ))}
            </select>

            <select name="destinationLocationId" value={form.destinationLocationId} onChange={handleChange}>
                <option value="">Select Destination</option>
                {locations.map(loc => (
                    <option key={loc.id} value={loc.id}>{loc.name}</option>
                ))}
            </select>

            <select name="transportationType" value={form.transportationType} onChange={handleChange}>
                <option value="FLIGHT">FLIGHT</option>
                <option value="BUS">BUS</option>
                <option value="SUBWAY">SUBWAY</option>
                <option value="UBER">UBER</option>
            </select>

            <div style={{ display: 'flex', gap: '0.5rem', margin: '1rem 0' }}>
                {daysOfWeek.map(day => (
                    <button
                        key={day.id}
                        type="button"
                        onClick={() => toggleDay(day.id)}
                        style={{
                            padding: '0.5rem',
                            border: '1px solid #aaa',
                            borderRadius: '6px',
                            backgroundColor: form.operatingDays.includes(day.id) ? '#007bff' : 'white',
                            color: form.operatingDays.includes(day.id) ? 'white' : 'black',
                            cursor: 'pointer'
                        }}
                    >
                        {day.name}
                    </button>
                ))}
            </div>

            <button onClick={handleSubmit}>Create</button>

            <ul style={{ marginTop: '2rem' }}>
                {transportations.map(t => (
                    <li key={t.id}>
                        {t.originLocation.name} ➡ {t.destinationLocation.name} ({t.transportationType})
                        <button onClick={() => handleDelete(t.id)} style={{ marginLeft: '0.5rem' }}>Delete</button>
                    </li>
                ))}
            </ul>
        </div>
    );
}
