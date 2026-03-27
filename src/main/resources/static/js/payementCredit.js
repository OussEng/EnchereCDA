document.addEventListener('DOMContentLoaded', () => {
    const paymentModal = new bootstrap.Modal(document.getElementById('paymentModal'));
    const paymentText = document.getElementById('paymentText');
    const inputMontant = document.getElementById('inputMontant');
    let currentAmount = 0;
    let currentPlan = '';
    let currentMJ = 0;

    document.querySelectorAll('.payment-btn-plan').forEach(btn => {
        btn.addEventListener('click', () => {
            currentAmount = btn.dataset.amount;
            currentPlan = btn.dataset.plan;
            currentMJ = 0;

            inputMontant.value = currentAmount;

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

        inputMontant.value = currentAmount;

        paymentText.textContent = `Vous allez payer ${currentAmount}€ pour le plan personnalisé.`;
        paymentModal.show();
    });

    // Boutons fixes MJ
    document.querySelectorAll('.payment-btn').forEach(btn => {
        btn.addEventListener('click', () => {
            currentAmount = btn.dataset.amount;
            currentMJ = btn.dataset.mj;
            currentPlan = '';

            inputMontant.value = currentAmount;

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

        inputMontant.value = currentAmount;

        paymentText.textContent = `Vous allez payer ${currentAmount.toFixed(2)}€ pour obtenir ${currentMJ} MJ.`;
        paymentModal.show();
    });

    document.getElementById('paymentForm').addEventListener('submit', function(e) {
        // 1. On empêche l'envoi immédiat pour simuler le chargement
        e.preventDefault();

        const form = this;
        const btnConfirm = document.getElementById('confirmBtn');
        const btnSpinner = document.getElementById('btnSpinner');
        const btnText = document.getElementById('btnText');

        // 2. On change l'apparence du bouton (Loading...)
        btnConfirm.disabled = true; // Évite les doubles clics
        btnSpinner.classList.remove('d-none'); // Affiche le spinner
        btnText.textContent = " Traitement en cours...";

        // 3. On attend 3 secondes (3000ms)
        setTimeout(() => {
            // Optionnel : Une petite alerte juste avant l'envoi
            console.log("Paiement validé par la banque fictive, envoi au serveur...");

            // 4. On envoie enfin le formulaire au Controller Java
            form.submit();
        }, 3000);
    });
});