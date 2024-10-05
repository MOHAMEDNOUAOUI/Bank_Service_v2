const montantHolder = document.querySelector('#montant_endh_text_aread');
const montantRange = document.querySelector('#montant_range');
const dureHolder = document.querySelector('#dure');
const dureRange = document.querySelector('#dure_range');
const mensualiteHolder = document.querySelector('#mensualite');
const mensualiteRange = document.querySelector('#mensualite_range');

const ANNUAL_INTEREST_RATE = 0.05;
const MONTHLY_INTEREST_RATE = ANNUAL_INTEREST_RATE / 12;
const MIN_DURATION = 6;
const MAX_DURATION = 120;
const DURATION_STEP = 6;


montantRange.addEventListener('input', (e) => {
    montantHolder.value = e.target.value;
    montantRange.setAttribute('value', e.target.value);
    updateMensualiteRange();
    calculateMensualiteByMontant();
});

dureRange.addEventListener('input', (e) => {
    dureHolder.value = e.target.value;
    dureRange.setAttribute('value', e.target.value);
    updateMensualiteRange();
    calculateMensualiteByMontant();
});

mensualiteRange.addEventListener('input', (e) => {
    mensualiteHolder.value = e.target.value;
    mensualiteRange.setAttribute('value', e.target.value);
    calculateDure();
});


function calculatePayment(montant, dureSpecifique) {
    return (montant * MONTHLY_INTEREST_RATE) / (1 - Math.pow(1 + MONTHLY_INTEREST_RATE, -dureSpecifique));
}

function calculateDuration(montant, mensualite) {
    return Math.log((mensualite - montant * MONTHLY_INTEREST_RATE) / mensualite) / -Math.log(1 + MONTHLY_INTEREST_RATE);
}


function updateMensualiteRange() {
    const montant = parseFloat(montantHolder.value);
    const maxMensualite = calculatePayment(montant, MIN_DURATION);
    const minMensualite = calculatePayment(montant, MAX_DURATION);

    mensualiteRange.setAttribute('min', minMensualite.toFixed(2));
    mensualiteRange.setAttribute('max', maxMensualite.toFixed(2));
}

function calculateMensualiteByMontant() {
    const montant = parseFloat(montantHolder.value);
    const dure = parseInt(dureHolder.value);

    // if (isNaN(montant) || isNaN(dure) || montant <= 0 || dure < MIN_DURATION || dure > MAX_DURATION) {
    //     console.error("Invalid input values");
    //     return;
    // }

    const mensualite = calculatePayment(montant, dure);
    mensualiteHolder.value = mensualite.toFixed(2);
    mensualiteRange.value = mensualite.toFixed(2);
    mensualiteRange.setAttribute('value', mensualite.toFixed(2));
}

function calculateDure() {
    const mensualite = parseFloat(mensualiteHolder.value);
    const montant = parseFloat(montantHolder.value);

    if (isNaN(mensualite) || isNaN(montant) || mensualite <= 0 || montant <= 0) {
        console.error("Invalid input values");
        return;
    }

    let dure = calculateDuration(montant, mensualite);
    dure = Math.max(MIN_DURATION, Math.min(MAX_DURATION, dure));
    dure = Math.round(dure / DURATION_STEP) * DURATION_STEP;

    dureHolder.value = dure;
    dureRange.value = dure;
    dureRange.setAttribute('value', dure);
}

// Initial setup
updateMensualiteRange();
calculateMensualiteByMontant();