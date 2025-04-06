import { useEffect, useState } from 'react';
import { getLocations, createLocation, updateLocation, deleteLocation } from '../api/locations';
import { ToastContainer } from 'react-toastify';

export default function Locations() {
    const [locations, setLocations] = useState([]);
    const [form, setForm] = useState({ name: '', city: '', country: '', locationCode: '' });
    const [editing, setEditing] = useState(null);
    const [editForm, setEditForm] = useState({ name: '', city: '', country: '', locationCode: '' });

    useEffect(() => {
        fetchLocations();
    }, []);

    const fetchLocations = () => {
        getLocations()
            .then(res => setLocations(res.data))
            .catch(err => console.error('Error fetching locations:', err));
    };

    const handleChange = (e) => {
        setForm({ ...form, [e.target.name]: e.target.value });
    };

    const handleSubmit = () => {
        createLocation(form)
            .then(() => {
                fetchLocations();
                setForm({ name: '', city: '', country: '', locationCode: '' });
            })
            .catch(err => console.error('Error creating location:', err));
    };

    const openEditModal = (location) => {
        setEditing(location.id);
        setEditForm({
            name: location.name,
            city: location.city,
            country: location.country,
            locationCode: location.locationCode
        });
    };

    const handleEditChange = (e) => {
        setEditForm({ ...editForm, [e.target.name]: e.target.value });
    };

    const handleUpdateSubmit = () => {
        updateLocation(editing, editForm)
            .then(() => {
                fetchLocations();
                setEditing(null);
            })
            .catch(err => console.error('Error updating location:', err));
    };

    const handleDelete = (id) => {
        deleteLocation(id)
            .then(fetchLocations)
            .catch(err => console.error('Error deleting location:', err));
    };

    const handleCancel = () => {
        setEditing(null);
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
                    <li key={loc.id}>
                        {loc.name} ({loc.city}, {loc.country}) - {loc.locationCode}
                        <button className="update-button" onClick={() => openEditModal(loc)}>Update</button>
                        <button className="delete-button" onClick={() => handleDelete(loc.id)}>Delete</button>
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
                        boxShadow: '0 2px 10px rgba(0,0,0,0.2)', width: '400px'
                    }}>
                        <h3>Edit Location</h3>
                        <input name="name" placeholder="Name" value={editForm.name} onChange={handleEditChange} />
                        <input name="city" placeholder="City" value={editForm.city} onChange={handleEditChange} />
                        <input name="country" placeholder="Country" value={editForm.country} onChange={handleEditChange} />
                        <input name="locationCode" placeholder="Code" value={editForm.locationCode} onChange={handleEditChange} />

                        <div style={{ marginTop: '1rem', display: 'flex', justifyContent: 'flex-end', gap: '1rem' }}>
                            <button onClick={handleCancel}>Cancel</button>
                            <button onClick={handleUpdateSubmit} style={{ backgroundColor: '#1976d2', color: 'white', padding: '0.5rem 1rem' }}>Submit</button>
                        </div>
                    </div>
                </div>
            )}

            <ToastContainer position="bottom-right" autoClose={3000} />
        </div>
    );
}
