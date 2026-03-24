document.addEventListener('DOMContentLoaded', () => {
    const sections = ['formulaire', 'encheres', 'articles'];
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

    // Expose la fonction globalement pour onclick inline
    window.showSection = showSection;
});