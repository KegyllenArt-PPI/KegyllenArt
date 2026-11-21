import '../style.css'
import javascriptLogo from '../javascript.svg'
import viteLogo from '/vite.svg'
import { setupCounter } from '../counter.js'

document.querySelector('#app').innerHTML = `
  <div>
    <a href="https://vite.dev" target="_blank">
      <img src="${viteLogo}" class="logo" alt="Vite logo" />
    </a>
    <a href="https://developer.mozilla.org/en-US/docs/Web/JavaScript" target="_blank">
      <img src="${javascriptLogo}" class="logo vanilla" alt="JavaScript logo" />
    </a>
    <h1>Hello Vite!</h1>
    <div class="card">
      <button id="counter" type="button"></button>
    </div>
    <p class="read-the-docs">
      Click on the Vite logo to learn more
    </p>
  </div>
`
import { apiRequest, setToken, getToken, clearToken } from './api/http.js';

window.addEventListener('auth:unauthorized', () => {
    console.warn('Sesión no autorizada o expirada (401). Token limpiado.');
});

async function probarHttpClient() {
    // 1. Guarda un token de prueba (o un JWT real que tengas de Postman/Insomnia)
    setToken('eyJhbGciOiJIUzI1NiJ9.eyJyb2wiOlt7ImF1dGhvcml0eSI6ImFkbWluaXN0cmFkb3IifV0sInN1YiI6InRlc3RAZXhhbXBsZS5jb20iLCJpYXQiOjE3OTUwOTY4MTAsImV4cCI6MTc5NTE4MzIxMH0.hYFDjo_2nQ55SZM3Zyo8RhwmZR66573rJnW6RTqzVGc');
    console.log('Token guardado:', getToken());

    // 2. Cambia '/comisiones/mis-comisiones' por un endpoint real de tu backend
    try {
        const data = await apiRequest('/comisiones/mis-comisiones');
        console.log('Petición exitosa:', data);
    } catch (error) {
        console.error('Error en apiRequest:', {
            status: error.status,
            message: error.message,
            data: error.data,
        });
    }
}

probarHttpClient();

setupCounter(document.querySelector('#counter'))
