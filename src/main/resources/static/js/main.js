document.addEventListener("DOMContentLoaded", function() {
    setTimeout(function() {
        document.querySelector('.logo-left').classList.add('show-logo');
        document.querySelector('.logo-right').classList.add('show-logo');
    }, 2000); // Задержка в 2 секунды
});
