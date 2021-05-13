var header = document.querySelector('.header');
var hamburger = document.querySelector('.hamburger-menu');

window.addEventListener('scroll', function(){
    var windowPosition = window.scrollY > 0;
    header.classList.toggle('active', windowPosition);
})
hamburger.addEventListener('click',function(){
    header.classList.toggle('menu-open');
})