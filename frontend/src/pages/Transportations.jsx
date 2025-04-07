import { useEffect, useState } from 'react';
import { getLocations} from '../api/locations';
import {
    getTransportations,
    createTransportation,
    updateTransportation,
    deleteTransportation,
    getEnabledTransportationTypes
} from '../api/transportations';
import { ToastContainer } from 'react-toastify';

export default function Transportations() {
    const [locations, setLocations] = useState([]);
    const [transportations, setTransportations] = useState([]);
    const [enabledTransportationTypes, setEnabledTransportationTypes] = useState([]);

    const [form, setForm] = useState({
        originLocationId: '',
        destinationLocationId: '',
        transportationType: 'FLIGHT',
        operatingDays: []
    });

    const [editing, setEditing] = useState(null);
    const [editForm, setEditForm] = useState({
        originLocationId: '',
        destinationLocationId: '',
        transportationType: 'FLIGHT',
        operatingDays: []
    });

    const daysOfWeek = [
        { id: 1, name: 'Monday' },
        { id: 2, name: 'Tuesday' },
        { id: 3, name: 'Wednesday' },
        { id: 4, name: 'Thursday' },
        { id: 5, name: 'Friday' },
        { id: 6, name: 'Saturday' },
        { id: 7, name: 'Sunday' }
    ];

    useEffect(() => {
        getLocations()
            .then(res => setLocations(res.data));
        fetchTransportations();
        getEnabledTransportationTypes().then(res => setEnabledTransportationTypes(res.data));
    }, []);

    const fetchTransportations = () => {
        getTransportations()
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
        createTransportation(form)
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
        deleteTransportation(id)
            .then(fetchTransportations);
    };

    const openEditModal = (t) => {
        setEditing(t.id);
        setEditForm({
            originLocationId: t.originLocation.id,
            destinationLocationId: t.destinationLocation.id,
            transportationType: t.transportationType,
            operatingDays: t.operatingDays
        });
    };

    const handleEditChange = (e) => {
        setEditForm({ ...editForm, [e.target.name]: e.target.value });
    };

    const toggleEditDay = (dayId) => {
        setEditForm(prev => ({
            ...prev,
            operatingDays: prev.operatingDays.includes(dayId)
                ? prev.operatingDays.filter(id => id !== dayId)
                : [...prev.operatingDays, dayId]
        }));
    };

    const handleUpdateSubmit = () => {
        updateTransportation(editing, editForm)
            .then(() => {
                fetchTransportations();
                setEditing(null);
            });
    };

    return (
        <div style={{ padding: '1rem' }}>
            <h2>Transportations</h2>

            <select className="select-input" name="originLocationId" value={form.originLocationId} onChange={handleChange}>
                <option value="">Select Origin</option>
                {locations.map(loc => (
                    <option key={loc.id} value={loc.id}>{loc.name}</option>
                ))}
            </select>

            <select className="select-input" name="destinationLocationId" value={form.destinationLocationId} onChange={handleChange}>
                <option value="">Select Destination</option>
                {locations.map(loc => (
                    <option key={loc.id} value={loc.id}>{loc.name}</option>
                ))}
            </select>

            <select className="select-input" name="transportationType" value={form.transportationType} onChange={handleChange}>
                <option value="">Select Transportation Type</option>
                {enabledTransportationTypes.map(type => (
                    <option key={type} value={type}>{type}</option>
                ))}
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

            <button className="button" onClick={handleSubmit}>Create</button>

            <ul style={{ marginTop: '2rem' }}>
                {transportations.map(t => (
                    <li key={t.id}>
                        {t.originLocation.name} ➡ {t.destinationLocation.name} ({t.transportationType})
                        <button className="update-button" onClick={() => openEditModal(t)} style={{ marginLeft: '0.5rem' }}>Update</button>
                        <button className="delete-button" onClick={() => handleDelete(t.id)}>Delete</button>
                        <br/><br/>
                    </li>
                ))}
            </ul>

            {editing !== null && (
                <div style={{
                    position: 'fixed', top: 0, left: 0, right: 0, bottom: 0,
                    backgroundColor: 'rgba(0,0,0,0.5)', display: 'flex',
                    alignItems: 'center', justifyContent: 'center'
                }}>
                    <div style={{
                        backgroundColor: 'white', padding: '2rem', borderRadius: '10px',
                        width: '400px'
                    }}>
                        <h3>Edit Transportation</h3>

                        <select name="originLocationId" value={editForm.originLocationId} onChange={handleEditChange}>
                            <option value="">Select Origin</option>
                            {locations.map(loc => (
                                <option key={loc.id} value={loc.id}>{loc.name}</option>
                            ))}
                        </select>

                        <select name="destinationLocationId" value={editForm.destinationLocationId} onChange={handleEditChange}>
                            <option value="">Select Destination</option>
                            {locations.map(loc => (
                                <option key={loc.id} value={loc.id}>{loc.name}</option>
                            ))}
                        </select>

                        <select name="transportationType" value={editForm.transportationType} onChange={handleEditChange}>
                            <option value="">Select Transportation Type</option>
                            {enabledTransportationTypes.map(type => (
                                <option key={type} value={type}>{type}</option>
                            ))}
                        </select>

                        <div style={{ display: 'flex', flexWrap: 'wrap', gap: '0.5rem', margin: '1rem 0' }}>
                            {daysOfWeek.map(day => (
                                <button
                                    key={day.id}
                                    type="button"
                                    onClick={() => toggleEditDay(day.id)}
                                    style={{
                                        padding: '0.5rem',
                                        border: '1px solid #aaa',
                                        borderRadius: '6px',
                                        backgroundColor: editForm.operatingDays.includes(day.id) ? '#007bff' : 'white',
                                        color: editForm.operatingDays.includes(day.id) ? 'white' : 'black',
                                        cursor: 'pointer'
                                    }}
                                >
                                    {day.name}
                                </button>
                            ))}
                        </div>

                        <div style={{ display: 'flex', justifyContent: 'flex-end', gap: '1rem' }}>
                            <button onClick={() => setEditing(null)}>Cancel</button>
                            <button onClick={handleUpdateSubmit} style={{ backgroundColor: '#1976d2', color: 'white', padding: '0.5rem 1rem' }}>Submit</button>
                        </div>
                    </div>
                </div>
            )}

            <ToastContainer position="bottom-right" autoClose={3000} />
        </div>
    );
}
