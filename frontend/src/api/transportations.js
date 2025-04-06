import api from './axios';

export const getTransportations = () => api.get('/transportations');
export const createTransportation = (data) => api.post('/transportations', data);
export const updateTransportation = (id, data) => api.put(`/transportations/${id}`, data);
export const deleteTransportation = (id) => api.delete(`/transportations/${id}`);
