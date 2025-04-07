import axios from 'axios';

const api = axios.create({
    baseURL: 'http://localhost:8080/api',
    headers: {
        'Content-Type': 'application/json',
        Accept: 'application/json'
    }
});

api.interceptors.request.use((config) => {
    config.headers["X-API-KEY"] = process.env.REACT_APP_API_KEY;
    return config;
});

api.interceptors.response.use(
    (response) => response,
    (error) => {
        console.log(error.response.data)
        return Promise.reject(error);
    }
);

export default api;
