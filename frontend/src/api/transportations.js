import api from './axios';
import {errorToastify, successToastify} from "./toastify-message";

export const getTransportations = () => api.get('/transportations')
    .then((res)=>res.data)
    .catch((err) => errorToastify(err));
export const createTransportation = (data) => api.post('/transportations', data)
    .then((res) => {
        successToastify("Transportation created successfully");
        return res.data;
    })
    .catch((err) => errorToastify(err));
export const updateTransportation = (id, data) => api.put(`/transportations/${id}`, data)
    .then((res) => {
        successToastify("Transportation updated successfully");
        return res.data;
    })
    .catch((err) => errorToastify(err));
export const deleteTransportation = (id) => api.delete(`/transportations/${id}`)
    .then((res) => {
        successToastify("Transportation deleted successfully");
        return res.data;
    })
    .catch((err) => errorToastify(err));
export const getEnabledTransportationTypes = () => api.get(`/transportations/enabled-types`)
    .then((res)=>res.data)
    .catch((err) => errorToastify(err));
