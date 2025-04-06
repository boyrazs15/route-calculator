import api from './axios';

export const listRoutes = (originId, destinationId, date) =>
    api.get('/routes', {
        params: {
            originLocationId: originId,
            destinationLocationId: destinationId,
            date: date
        }
    });
