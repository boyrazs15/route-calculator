import api from './axios';
import { errorToastify, successToastify } from './toastify-message';

export const getLocations = () => api.get('/locations').then((res)=>res.data)
    .catch((err) => errorToastify(err));
export const createLocation = (data) => api.post('/locations', data)
    .then((res) => {
        successToastify("Location created successfully");
        return res.data;
    })
    .catch((err) => errorToastify(err));
export const updateLocation = (id, data) => api.put(`/locations/${id}`, data)
    .then((res) => {
        successToastify("Location updated successfully");
        return res.data;
    })
    .catch((err) => errorToastify(err));
export const deleteLocation = (id) => api.delete(`/locations/${id}`)
    .then((res) => {
        successToastify("Location deleted successfully");
        return res.data;
    })
    .catch((err) => errorToastify(err));
