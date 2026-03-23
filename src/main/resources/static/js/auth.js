document.addEventListener('DOMContentLoaded', () => {

    function showTab(tab) {
        document.getElementById('tab-connexion').style.display = tab === 'connexion' ? 'block' : 'none';
        document.getElementById('tab-inscription').style.display = tab === 'inscription' ? 'block' : 'none';

        document.querySelectorAll('#authTabs .nav-link').forEach(btn => {
            btn.classList.toggle('active', btn.dataset.tab === tab);
        });

        // 💾 On sauvegarde l'onglet choisi
        localStorage.setItem('activeTab', tab);
    }

    // 🔁 On récupère l'onglet sauvegardé
    const savedTab = localStorage.getItem('activeTab') || 'connexion';
    showTab(savedTab);

    document.querySelectorAll('#authTabs .nav-link').forEach(btn => {
        btn.addEventListener('click', () => showTab(btn.dataset.tab));
    });
});