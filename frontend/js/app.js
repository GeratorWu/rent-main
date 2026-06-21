const API = {
    cars: '/api/cars',
    customers: '/api/customers',
};

const alertEl = document.getElementById('alert');

function showAlert(message, type = 'success') {
    alertEl.textContent = message;
    alertEl.className = `alert ${type}`;
    setTimeout(() => alertEl.classList.add('hidden'), 4000);
}

function hideAlert() {
    alertEl.classList.add('hidden');
}

async function apiFetch(url, options = {}) {
    const response = await fetch(url, {
        headers: { 'Content-Type': 'application/json', ...options.headers },
        ...options,
    });
    if (!response.ok) {
        throw new Error(`Erreur ${response.status}`);
    }
    const text = await response.text();
    return text ? JSON.parse(text) : null;
}

function formatPrice(price) {
    return new Intl.NumberFormat('fr-FR', { style: 'currency', currency: 'EUR' }).format(price);
}

// --- Tabs ---
document.querySelectorAll('.tab').forEach((tab) => {
    tab.addEventListener('click', () => {
        document.querySelectorAll('.tab').forEach((t) => {
            t.classList.remove('active');
            t.setAttribute('aria-selected', 'false');
        });
        document.querySelectorAll('.panel').forEach((p) => p.classList.remove('active'));

        tab.classList.add('active');
        tab.setAttribute('aria-selected', 'true');
        document.getElementById(`panel-${tab.dataset.tab}`).classList.add('active');
    });
});

// --- Voitures ---
const carsList = document.getElementById('cars-list');
const carsLoading = document.getElementById('cars-loading');
const carsEmpty = document.getElementById('cars-empty');

async function loadCars() {
    carsLoading.classList.remove('hidden');
    carsEmpty.classList.add('hidden');
    carsList.innerHTML = '';

    try {
        const cars = await apiFetch(API.cars);
        carsLoading.classList.add('hidden');

        if (!cars || cars.length === 0) {
            carsEmpty.classList.remove('hidden');
            return;
        }

        cars.forEach((car) => {
            const el = document.createElement('div');
            el.className = 'item';
            el.innerHTML = `
                <div class="item-info">
                    <h3>${escapeHtml(car.brand)}</h3>
                    <p>${escapeHtml(car.plateNumber)}</p>
                </div>
                <span class="item-badge">${formatPrice(car.price)}/jour</span>
            `;
            carsList.appendChild(el);
        });
    } catch (err) {
        carsLoading.classList.add('hidden');
        showAlert(`Impossible de charger les voitures : ${err.message}`, 'error');
    }
}

document.getElementById('car-form').addEventListener('submit', async (e) => {
    e.preventDefault();
    hideAlert();

    const form = e.target;
    const car = {
        plateNumber: form.plateNumber.value.trim(),
        brand: form.brand.value.trim(),
        price: parseFloat(form.price.value),
    };

    try {
        await apiFetch(API.cars, { method: 'POST', body: JSON.stringify(car) });
        form.reset();
        showAlert('Voiture ajoutée avec succès.');
        await loadCars();
    } catch (err) {
        showAlert(`Erreur lors de l'ajout : ${err.message}`, 'error');
    }
});

document.getElementById('refresh-cars').addEventListener('click', loadCars);

// --- Clients ---
const customersList = document.getElementById('customers-list');
const customersLoading = document.getElementById('customers-loading');
const customersEmpty = document.getElementById('customers-empty');

async function loadCustomers() {
    customersLoading.classList.remove('hidden');
    customersEmpty.classList.add('hidden');
    customersList.innerHTML = '';

    try {
        const customers = await apiFetch(API.customers);
        customersLoading.classList.add('hidden');

        if (!customers || customers.length === 0) {
            customersEmpty.classList.remove('hidden');
            return;
        }

        customers.forEach((customer) => {
            const el = document.createElement('div');
            el.className = 'item';
            el.innerHTML = `
                <div class="item-info">
                    <h3>${escapeHtml(customer.name)}</h3>
                    <p>${escapeHtml(customer.email)}</p>
                </div>
                <span class="item-badge">${escapeHtml(customer.id)}</span>
            `;
            customersList.appendChild(el);
        });
    } catch (err) {
        customersLoading.classList.add('hidden');
        showAlert(`Impossible de charger les clients : ${err.message}`, 'error');
    }
}

document.getElementById('customer-form').addEventListener('submit', async (e) => {
    e.preventDefault();
    hideAlert();

    const form = e.target;
    const customer = {
        id: form.id.value.trim(),
        name: form.name.value.trim(),
        email: form.email.value.trim(),
    };

    try {
        await apiFetch(API.customers, { method: 'POST', body: JSON.stringify(customer) });
        form.reset();
        showAlert('Client ajouté avec succès.');
        await loadCustomers();
    } catch (err) {
        showAlert(`Erreur lors de l'ajout : ${err.message}`, 'error');
    }
});

document.getElementById('refresh-customers').addEventListener('click', loadCustomers);

function escapeHtml(str) {
    const div = document.createElement('div');
    div.textContent = str;
    return div.innerHTML;
}

// Init
loadCars();
loadCustomers();
