document.addEventListener('DOMContentLoaded', () => {
    const sections = ['formulaire', 'encheres', 'articles', 'retraits'];
    const navLinks = document.querySelectorAll('.nav-link');

    function showSection(sectionId, clickedLink) {
        sections.forEach(id => {
            const el = document.getElementById(id);
            if (el) el.style.display = (id === sectionId) ? 'block' : 'none';
        });
        navLinks.forEach(link => link.classList.remove('active'));
        if (clickedLink) clickedLink.classList.add('active');
    }

    const defaultLink = document.querySelector('.nav-link.active');
    if (defaultLink) showSection('formulaire', defaultLink);

    window.showSection = showSection;

    const ouvrirModal = document.querySelector('main').dataset.ouvrirModal === 'true';
    if (ouvrirModal) {
        showSection('retraits', document.querySelector('[onclick*="retraits"]'));
        new bootstrap.Modal(document.getElementById('modalAjoutAdresse')).show();
    }
});