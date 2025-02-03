document.getElementById('shiftButton').addEventListener('click', function() {
    const col = document.querySelector('.col');
    const modal = document.getElementById('modal');
    const modalContent = document.querySelector('.modal-content');

    // Сместить col влево
    col.style.transform = 'translateX(-600px)'; // Настройте значение по необходимости

    // Показать модальное окно с выездом вправо
    modal.style.display = 'block';
    setTimeout(() => {
        modal.style.transform = 'translateX(300px)'; // Сбросить трансформацию для показа
        modal.style.opacity = '1'; // Установить полную непрозрачность
        modalContent.style.transform = 'translateX(0)'; // Сбросить трансформацию для содержимого
        modalContent.style.opacity = '1'; // Установить полную непрозрачность для содержимого
    }, 10); // Небольшая задержка для активации перехода
});

// Обработчик события для кнопки закрытия
document.querySelector('.close-modal').addEventListener('click', function() {
    const col = document.querySelector('.col');
    const modal = document.getElementById('modal');
    const modalContent = document.querySelector('.modal-content');

    // Вернуть col на место
    col.style.transform = 'translateX(0)'; // Вернуть col на место

    // Скрыть модальное окно с анимацией
    modal.style.transform = 'translateX(-50%)'; // Сместить модальное окно влево
    modal.style.opacity = '0'; // Установить непрозрачность в 0
    modalContent.style.transform = 'translateX(100%)'; // Сместить содержимое вправо
    modalContent.style.opacity = '0'; // Установить непрозрачность в 0

    // Скрыть модальное окно после завершения анимации
    setTimeout(() => {
        modal.style.display = 'none'; // Скрыть модальное окно
    }, 1500); // Задержка должна совпадать с временем анимации
});