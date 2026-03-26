document.addEventListener('DOMContentLoaded', () => {

    function showTab(tab) {
        const tabConnexion = document.getElementById('tab-connexion');
        const tabInscription = document.getElementById('tab-inscription');

        if (tabConnexion) tabConnexion.style.display = tab === 'connexion' ? 'block' : 'none';
        if (tabInscription) tabInscription.style.display = tab === 'inscription' ? 'block' : 'none';

        document.querySelectorAll('#authTabs .nav-link').forEach(btn => {
            btn.classList.toggle('active', btn.dataset.tab === tab);
        });

        localStorage.setItem('activeTab', tab);
    }

    const savedTab = localStorage.getItem('activeTab') || 'connexion';
    showTab(savedTab);

    document.querySelectorAll('#authTabs .nav-link').forEach(btn => {
        btn.addEventListener('click', () => showTab(btn.dataset.tab));
    });

    const modal = new bootstrap.Modal(document.getElementById('emailSentModal'));
    const closeBtn = document.getElementById("modalCloseBtn");

    // Listener fixe sur le bouton modal
    closeBtn.addEventListener("click", function() {
        modal.hide();
        window.location.href = "/auth";
    });

    // Formulaire “mot de passe oublié”
    document.getElementById("forgotPasswordForm").addEventListener("submit", function(e) {
        e.preventDefault();
        modal.show();
    });
});