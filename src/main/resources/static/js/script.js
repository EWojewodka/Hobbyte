$(document).ready(function(){
   $('.navbar-left').hide();
   $('.header-obscure').hide();

   $('.header-obscure').click(function(){
       toggleMenu(false);
   });
});

function toggleMenu(show){
    if(show){
        $('.navbar-left').show("slide", { direction: "left" }, 100);
        $('.header-obscure').show();
    } else {
        $('.navbar-left').hide("slide", { direction: "left" }, 100);
        $('.header-obscure').hide();
    }
    moveHeaderImage(show);
}

function moveHeaderImage(toRight){
    if(toRight){
        $('.header-image').addClass('image-to-right');
    } else {
        $('.header-image').removeClass('image-to-right');
    }
}
