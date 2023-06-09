const tag = document.getElementById('message');
const button = document.querySelector('button');
button.addEventListener('click', function(){
    tag.classList.toggle('msg-btn');
})