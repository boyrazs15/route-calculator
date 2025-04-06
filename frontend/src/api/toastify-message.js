import { toast } from 'react-toastify';

export const errorToastify = (err) => {
    const res = err.response?.data;

    // Field bazlı validation hatası varsa
    if (res?.data && typeof res.data === 'object') {
        Object.entries(res.data).forEach(([field, message]) => {
            const label = field.charAt(0).toUpperCase() + field.slice(1);
            toast.error(`${label}: ${message}`);
        });

        // Genel mesaj varsa
    } else if (res?.message) {
        toast.error(res.message);

        // Belirsiz backend hatası ama response var
    } else if (res) {
        toast.error("Something went wrong. Please try again.");

        // Network veya sunucu hiç dönmediyse
    } else {
        toast.error("Network error or no response from server");
    }
};

export const successToastify = (msg) => {
    toast.success(msg + " :tada:")
}
