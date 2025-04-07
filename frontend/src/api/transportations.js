import api from './axios';
import {errorToastify, successToastify} from "./toastify-message";

export const getTransportations = () => api.get('/transportations')
    .catch((err) => errorToastify(err));
export const createTransportation = (data) => api.post('/transportations', data)
    .then(() => successToastify("Transportation created successfully"))
    .catch((err) => errorToastify(err));
export const updateTransportation = (id, data) => api.put(`/transportations/${id}`, data)
    .then(() => successToastify("Transportation updated successfully"))
    .catch((err) => errorToastify(err));
export const deleteTransportation = (id) => api.delete(`/transportations/${id}`)
    .then(() => successToastify("Transportation deleted successfully"))
    .catch((err) => errorToastify(err));
export const getEnabledTransportationTypes = () => api.get(`/transportations/enabled-types`)
    .catch((err) => errorToastify(err));
