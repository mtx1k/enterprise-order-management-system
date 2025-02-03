document.addEventListener('DOMContentLoaded', () => {
    const showModalButtons = document.querySelectorAll('.show-modal');

    showModalButtons.forEach(button => {
        button.addEventListener('click', (e) => {
            const col = e.target.closest('.col');
            col.classList.toggle('modal-active');
        });
    });
});