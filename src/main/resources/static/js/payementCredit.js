document.addEventListener('DOMContentLoaded', () => {
    const paymentModal = new bootstrap.Modal(document.getElementById('paymentModal'));
    const paymentText = document.getElementById('paymentText');
    let currentAmount = 0;
    let currentPlan = '';
    let currentMJ = 0;

    // Boutons fixes plans (ex: “Basic”, “Pro”)
    document.querySelectorAll('.payment-btn-plan').forEach(btn => {
        btn.addEventListener('click', () => {
            currentAmount = btn.dataset.amount;
            currentPlan = btn.dataset.plan;
            currentMJ = 0; // pas de MJ ici
            paymentText.textContent = `Vous allez payer ${currentAmount}€ pour le plan ${currentPlan}.`;
            paymentModal.show();
        });
    });

    // Custom plan
    document.getElementById('customPaymentBtn')?.addEventListener('click', () => {
        const amount = document.getElementById('customAmount').value;
        if (!amount || amount <= 0) {
            alert('Veuillez entrer un montant valide.');
            return;
        }
        currentAmount = amount;
        currentPlan = 'Custom';
        currentMJ = 0;
        paymentText.textContent = `Vous allez payer ${currentAmount}€ pour le plan personnalisé.`;
        paymentModal.show();
    });

    // Boutons fixes MJ
    document.querySelectorAll('.payment-btn').forEach(btn => {
        btn.addEventListener('click', () => {
            currentAmount = btn.dataset.amount;
            currentMJ = btn.dataset.mj;
            currentPlan = '';
            paymentText.textContent = `Vous allez payer ${currentAmount}€ pour obtenir ${currentMJ} MJ.`;
            paymentModal.show();
        });
    });

    // Custom MJ
    document.getElementById('customMJBtn')?.addEventListener('click', () => {
        const mj = document.getElementById('customMJ').value;
        if (!mj || mj <= 0) {
            alert('Veuillez entrer un nombre valide de MJ.');
            return;
        }
        currentMJ = mj;
        currentAmount = mj * 1; // 1 MJ = 1€
        currentPlan = '';
        paymentText.textContent = `Vous allez payer ${currentAmount.toFixed(2)}€ pour obtenir ${currentMJ} MJ.`;
        paymentModal.show();
    });

    // Formulaire de paiement
    document.getElementById('paymentForm').addEventListener('submit', function(e) {
        e.preventDefault(); // Empêche l'envoi réel

        const cardName = document.getElementById('cardName').value;
        const cardNumber = document.getElementById('cardNumber').value;
        const expiry = document.getElementById('expiry').value;
        const cvv = document.getElementById('cvv').value;

        // Simulation de paiement
        paymentModal.hide();

        let msg = `Paiement simulé de $${currentAmount.toFixed(2)}`;
        if (currentMJ > 0) msg += ` pour ${currentMJ} MJ`;
        if (currentPlan) msg += ` pour le plan ${currentPlan}`;
        msg += ` réussi!\nCarte utilisée : ${cardName}, ${cardNumber}, Exp: ${expiry}, CVV: ${cvv}`;

        alert(msg);
    });
});