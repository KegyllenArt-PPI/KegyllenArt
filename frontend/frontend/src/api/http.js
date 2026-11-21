const API_URL = import.meta.env.VITE_API_URL;
const TOKEN_KEY = 'kegyllen_token';

export function getToken() {
    return localStorage.getItem(TOKEN_KEY);
}

export function setToken(token) {
    localStorage.setItem(TOKEN_KEY, token);
}

export function clearToken() {
    localStorage.removeItem(TOKEN_KEY);
}

export function hasSession() {
    return Boolean(getToken());
}

/**
 * Cliente HTTP centralizado para consumir el backend de Spring Boot.
 * @param {string} path - Ruta relativa, ej: '/comisiones/mis-comisiones'
 * @param {object} options - { method, body, auth, headers }
 */
export async function apiRequest(path, options = {}) {
    const {
        method = 'GET',
        body = null,
        auth = true,
        headers: customHeaders = {},
    } = options;

    if (!API_URL) {
        throw new Error('Falta configurar VITE_API_URL en el archivo .env');
    }

    const headers = {
        'Content-Type': 'application/json',
        ...customHeaders,
    };

    if (auth) {
        const token = getToken();
        if (token) {
            headers.Authorization = `Bearer ${token}`;
        }
    }

    const config = {
        method,
        headers,
    };

    if (body !== null && method !== 'GET' && method !== 'HEAD') {
        config.body = typeof body === 'string' ? body : JSON.stringify(body);
    }

    const response = await fetch(`${API_URL}${path}`, config);

    if (response.status === 401) {
        clearToken();
        window.dispatchEvent(new CustomEvent('auth:unauthorized'));
    }

    const contentType = response.headers.get('content-type') || '';
    const isJson = contentType.includes('application/json');
    const payload = isJson ? await response.json().catch(() => null) : await response.text().catch(() => '');

    if (!response.ok) {
        const message =
            (payload && typeof payload === 'object' && (payload.message || payload.error)) ||
            (typeof payload === 'string' && payload.trim()) ||
            `Error HTTP ${response.status}`;

        const error = new Error(message);
        error.status = response.status;
        error.data = payload;
        throw error;
    }

    return payload;
}