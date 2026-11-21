import { login } from '../api/auth.js';

export function renderLoginView(container, { onSuccess } = {}) {
    container.innerHTML = `
    <section class="login-view">
      <div class="login-card">
        <h1>Kegyllen</h1>
        <p class="login-subtitle">Inicia sesión para gestionar tus comisiones</p>

        <form id="login-form" class="login-form" novalidate>
          <div class="form-group">
            <label for="correo">Correo electrónico</label>
            <input
              id="correo"
              name="correo"
              type="email"
              autocomplete="email"
              placeholder="tu@correo.com"
              required
            />
          </div>

          <div class="form-group">
            <label for="contrasenna">Contraseña</label>
            <input
              id="contrasenna"
              name="contrasenna"
              type="password"
              autocomplete="current-password"
              placeholder="••••••••"
              required
            />
          </div>

          <p id="login-error" class="login-error" role="alert" hidden></p>

          <button id="login-submit" type="submit" class="login-btn">
            Iniciar sesión
          </button>
        </form>
      </div>
    </section>
  `;

    const form = container.querySelector('#login-form');
    const correoInput = container.querySelector('#correo');
    const contrasennaInput = container.querySelector('#contrasenna');
    const errorEl = container.querySelector('#login-error');
    const submitBtn = container.querySelector('#login-submit');

    function showError(message) {
        errorEl.textContent = message;
        errorEl.hidden = false;
    }

    function clearError() {
        errorEl.textContent = '';
        errorEl.hidden = true;
    }

    function setLoading(isLoading) {
        submitBtn.disabled = isLoading;
        correoInput.disabled = isLoading;
        contrasennaInput.disabled = isLoading;
        submitBtn.textContent = isLoading ? 'Ingresando...' : 'Iniciar sesión';
    }

    form.addEventListener('submit', async (event) => {
        event.preventDefault();
        clearError();

        const correo = correoInput.value.trim();
        const contrasenna = contrasennaInput.value;

        if (!correo) {
            showError('Por favor ingresa tu correo electrónico.');
            correoInput.focus();
            return;
        }

        if (!contrasenna) {
            showError('Por favor ingresa tu contraseña.');
            contrasennaInput.focus();
            return;
        }

        setLoading(true);

        try {
            const session = await login(correo, contrasenna);
            if (typeof onSuccess === 'function') {
                onSuccess(session);
            }
        } catch (err) {
            if (err.status === 401 || err.status === 403) {
                showError('Correo o contraseña incorrectos.');
            } else if (err.status === 400) {
                showError(err.message || 'Verifica los datos ingresados.');
            } else {
                showError(err.message || 'No se pudo conectar con el servidor.');
            }
        } finally {
            setLoading(false);
        }
    });
}