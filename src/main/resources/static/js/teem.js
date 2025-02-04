document.getElementById('shiftButton').addEventListener('click', function() {
    const col = document.querySelector('.col');
    const modal = document.getElementById('modal');
    const modalContent = document.querySelector('.modal-content');

    col.style.transform = 'translateX(-600px)';

    modal.style.display = 'block';
    setTimeout(() => {
        modal.style.transform = 'translateX(300px)';
        modal.style.opacity = '1';
        modalContent.style.transform = 'translateX(0)';
        modalContent.style.opacity = '1';
    }, 10);
});

document.querySelector('.close-modal').addEventListener('click', function() {
    const col = document.querySelector('.col');
    const modal = document.getElementById('modal');
    const modalContent = document.querySelector('.modal-content');


    col.style.transform = 'translateX(0)';


    modal.style.transform = 'translateX(-50%)';
    modal.style.opacity = '0';
    modalContent.style.transform = 'translateX(100%)';
    modalContent.style.opacity = '0';


    setTimeout(() => {
        modal.style.display = 'none';
    }, 1500);
});