import api from './axios';
import { errorToastify, successToastify } from './toastify-message';

export const getLocations = () => api.get('/locations')
    .catch((err) => errorToastify(err));
export const createLocation = (data) => api.post('/locations', data)
    .then(() => successToastify("Location created successfully"))
    .catch((err) => errorToastify(err));
export const updateLocation = (id, data) => api.put(`/locations/${id}`, data)
    .then(() => successToastify("Location updated successfully"))
    .catch((err) => errorToastify(err));
export const deleteLocation = (id) => api.delete(`/locations/${id}`)
    .then(() => successToastify("Location deleted successfully"))
    .catch((err) => errorToastify(err));
