import { apiRequest, setToken, getToken, clearToken } from './http.js';

    const USER_KEY = 'kegyllen_user';

export function parseJwt(token) {
    if (!token || typeof token !== 'string') return null;

    try {
        const parts = token.split('.');
        if (parts.length !== 3) return null;

        const base64 = parts[1].replace(/-/g, '+').replace(/_/g, '/');
        const jsonPayload = decodeURIComponent(
            atob(base64)
                .split('')
                .map((c) => `%${`00${c.charCodeAt(0).toString(16)}`.slice(-2)}`)
                .join('')
        );

        return JSON.parse(jsonPayload);
    } catch {
        return null;
    }
}

export function extractSessionFromToken(token) {
    const payload = parseJwt(token);
    if (!payload) return null;

    const rol = Array.isArray(payload.rol)
        ? payload.rol[0]?.authority ?? null
        : payload.rol ?? null;

    return {
        correo: payload.sub ?? null,
        rol,
        exp: payload.exp ?? null,
    };
}

export function isTokenExpired(token) {
    const payload = parseJwt(token);
    if (!payload || typeof payload.exp !== 'number') return false;

    const nowInSeconds = Math.floor(Date.now() / 1000);
    return payload.exp <= nowInSeconds;
}

export function getSessionUser() {
    const raw = localStorage.getItem(USER_KEY);
    if (!raw) return null;

    try {
        return JSON.parse(raw);
    } catch {
        return null;
    }
}

export function hasActiveSession() {
    const token = getToken();
    if (!token) return false;

    if (isTokenExpired(token)) {
        logout();
        return false;
    }

    return true;
}

export async function login(correo, contrasenna) {
    const data = await apiRequest('/auth/login', {
        method: 'POST',
        auth: false,
        body: {
            correo: correo.trim(),
            contrasenna,
        },
    });

    if (!data || !data.token) {
        throw new Error('La respuesta del servidor no incluyó un token JWT válido.');
    }

    setToken(data.token);

    const sessionUser = extractSessionFromToken(data.token);
    if (sessionUser) {
        localStorage.setItem(USER_KEY, JSON.stringify(sessionUser));
    }

    return {
        token: data.token,
        user: sessionUser,
        raw: data,
    };
}

export function logout() {
    clearToken();
    localStorage.removeItem(USER_KEY);
}