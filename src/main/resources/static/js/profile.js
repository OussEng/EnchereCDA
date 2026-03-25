document.addEventListener('DOMContentLoaded', () => {
    const sections = ['formulaire', 'encheres', 'articles', 'retraits'];
    const navLinks = document.querySelectorAll('.nav-link');

    function showSection(sectionId, clickedLink) {
        // Afficher / cacher les sections
        sections.forEach(id => {
            const el = document.getElementById(id);
            if (el) el.style.display = (id === sectionId) ? 'block' : 'none';
        });

        // Mettre à jour le carré bleu (classe active)
        navLinks.forEach(link => link.classList.remove('active'));
        if (clickedLink) clickedLink.classList.add('active');
    }

    // Onglet par défaut
    const defaultLink = document.querySelector('.nav-link.active');
    if (defaultLink) showSection('formulaire', defaultLink);

    // Expose les fonctions globalement pour onclick inline
    window.showSection = showSection;

    function toggleForm(button, hide=false) {
        const cardBody = button.closest('.card-body');
        const displayDiv = cardBody.querySelector('.retrait-display');
        const form = cardBody.querySelector('.retrait-form');

        if(hide) {
            displayDiv.style.display = 'block';
            form.style.display = 'none';
        } else {
            displayDiv.style.display = 'none';
            form.style.display = 'block';
        }
    }

    window.toggleForm = toggleForm; // <-- exposer pour le HTML

    const ouvrirModal = document.querySelector('main').dataset.ouvrirModal === 'true';
    if (ouvrirModal) {
        showSection('retraits', document.querySelector('[onclick*="retraits"]'));
        new bootstrap.Modal(document.getElementById('modalAjoutAdresse')).show();
    }
});