import api from './axios';
import { errorToastify } from "./toastify-message";

export const listRoutes = (originId, destinationId, date) =>
    api.get('/routes', {
        params: {
            originLocationId: originId,
            destinationLocationId: destinationId,
            date: date
        }
    })
        .catch((err) => errorToastify(err));
