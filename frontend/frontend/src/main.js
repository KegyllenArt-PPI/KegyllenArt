import { hasActiveSession, getSessionUser, logout } from './api/auth.js';
import { renderLoginView } from './views/login.js';

const appEl = document.querySelector('#app');

function showLogin(expiredMessage = '') {
    renderLoginView(appEl, {
        onSuccess: () => {
            showDashboard();
        },
    });

    if (expiredMessage) {
        const errorEl = appEl.querySelector('#login-error');
        if (errorEl) {
            errorEl.textContent = expiredMessage;
            errorEl.hidden = false;
        }
    }
}

function showDashboard() {
    const user = getSessionUser();

    appEl.innerHTML = `
    <main class="dashboard-placeholder">
      <header>
        <h1>Dashboard de Comisiones</h1>
        <p>Usuario: <strong>${user?.correo ?? 'Sesión activa'}</strong> | Rol: <strong>${user?.rol ?? 'N/A'}</strong></p>
        <button id="logout-btn" type="button">Cerrar sesión</button>
      </header>
      <p>Sesión autenticada correctamente. Aquí conectaremos el listado de comisiones.</p>
    </main>
  `;

    appEl.querySelector('#logout-btn')?.addEventListener('click', () => {
        logout();
        showLogin();
    });
}

window.addEventListener('auth:unauthorized', () => {
    logout();
    showLogin('Tu sesión expiró o no es válida. Inicia sesión nuevamente.');
});

if (hasActiveSession()) {
    showDashboard();
} else {
    showLogin();
}