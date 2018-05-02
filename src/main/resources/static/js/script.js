$(document).ready(function() {
	$('.navbar-left').hide();
	$('.header-obscure').hide();
	$('.post-attachments-preview').hide();

	$('.header-obscure').click(function() {
		toggleMenu(false);
		minimizeImage();
	});

	handleDynamicForms();
	smoothScroll();
	onlyOneCheckbox();
	listenMenuTree();
});

function toggleMenu(show) {
	var leftNavbar = $('.navbar-left');
	var headerObscure = $('.header-obscure');

	if (show) {
		leftNavbar.show("slide", {
			direction : "left"
		}, 100);
		headerObscure.show();
	} else {
		leftNavbar.hide("slide", {
			direction : "left"
		}, 100);
		headerObscure.hide();
	}
	moveHeaderImage(show);
}

function moveHeaderImage(toRight) {
	var headerImage = $('.header-image');
	var classToAdd = 'image-to-right';

	if (toRight) {
		headerImage.addClass(classToAdd);
	} else {
		headerImage.removeClass(classToAdd);
	}
}

/**
 * Hide all dynamic forms on document load. Show them on click label.
 * 
 * @returns
 */
function handleDynamicForms() {
	$('.dynamic-form').each(function() {
		$(this).find('form').hide();
		console.log($(this))
	});

	$('.dynamic-form').click(function() {
		var toOpen = $(this);
		var toOpenForm = toOpen.find('form');
		$('.dynamic-form').each(function() {
			var other = $(this);
			var otherForm = other.find('form');

			if (other.attr('id') != toOpen.attr('id')) {
				other.removeClass('block');
				otherForm.hide();
			} else {
				// double click should hide form.
				toOpenForm.show();
				toOpen.addClass('block');
			}
		});
	});
}

function sendAjaxWithUrl(url, _form) {
	var form = $(_form);
	prepareFormDescription(form);
	var formData = new FormData(_form);
	$.post({
		data : formData,
		url : url + "?type=" + form.attr('id'),
		data: formData,
		processData: false,
	    contentType: false,
		success : function(data) {
			var jsonObj = JSON.parse(data);
			showResultAfterAjax(form, jsonObj.msg, true);
			if(jsonObj.redirect !== undefined)
				window.location.href = jsonObj.redirect;
		},
		error : function(xhr, ajaxOptions, thrownError) {
			if (xhr === undefined || xhr.responseJSON === undefined)
				return;
			showResultAfterAjax(form, xhr.responseJSON.message, false);
		}
	});
}

function sendAjax(_form) {
	sendAjaxWithUrl(window.location.href, _form);
}


function prepareFormDescription(form) {
	var formDescription = form.find('.form-description');
	formDescription.removeClass('success');
	formDescription.removeClass('fail');
}

function showResultAfterAjax(form, content, isSuccess){
	var formDescription = form.find('.form-description');
	formDescription.fadeIn(100);
	formDescription.removeClass('hidden');
	formDescription.html("<small>" + content + "</small>");
	formDescription.addClass(isSuccess ? 'success' : 'fail');
}

function smoothScroll() {
	$('a[href*="#"]').on('click',
		function(event) {
			if (location.pathname.replace(/^\//, '') == this.pathname
					.replace(/^\//, '')
					&& location.hostname == this.hostname) {
				var target = $(this.hash);
				target = target.length ? target : $('[name='
						+ this.hash.slice(1) + ']');
				if (target.length) {
					event.preventDefault();
					$('html, body').animate({
						scrollTop : target.offset().top
					}, 500, function() {
						var $target = $(target);
						$target.focus();
						if ($target.is(":focus")) {
							return false;
						} else {
							$target.attr('tabindex', '-1');
							$target.focus();
						}
						;
					});
				}
			}
		});
}

function onlyOneCheckbox() {
	$('.one-checkbox .checkbox').on('change', function(e) {
		$(this).siblings().each(function() {
			var toUncheck = $(this).find('input[type="checkbox"]').first();
			toUncheck.prop("checked", false);
			toUncheck.prop("disabled", false);
		});
		e.target.disabled = true;
	});
}

function showNewPostModal() {
	$('.header-obscure').slideDown(200);
}

function listenMenuTree(){
	$('.menu-tree-element-wrapper').hide();
	$('.menu-tree-element div').on('click', function(){
		var parent = $(this);
		parent.siblings('.menu-tree-element-wrapper').toggle();
	});
}

function loadIFrame(_source){
	var source = $(_source);
	var navigator = source.find('.mt-navigator').first();
	var src = navigator.attr('src');
	if(src === undefined || src.length == 0)
		return;
	$('iframe').prop('src', src);
}

var availablePhotoFormats = ['jpg','jpeg','gif','png'];

function removeFileFromInput(input){
	if(input === undefined)
		return;
	
	var file = input.files[0];
	if(file === undefined)
		return;
	
	input.files[0] = null;
}

function showMiniPhoto(_input) {
	
	//Check its correct file for upload
	var file = _input.files[0];
	console.log(availablePhotoFormats.indexOf(file.name.split('.').pop()));
	if(availablePhotoFormats.indexOf(file.name.split('.').pop()) == -1){
		removeFileFromInput(_input);
		return false;
	}

	var currentMiniPhoto = $('.attach-wrapper #photo-mini-preview');
	if(!currentMiniPhoto !== undefined) {
		currentMiniPhoto.remove();
	}
	
	var input = $(_input);
	var previewBlock = $('.post-attachments-preview');
	previewBlock.prepend(
			'<div class="attach-wrapper">'
			+'<img id="photo-mini-preview" src="" width="100px" height="100px" onclick="maximizePhoto(this)" />'
			+'</div>');
	var reader = new FileReader();
	
	reader.onloadend = function () {
		$('#photo-mini-preview').prop('src', reader.result);
		previewBlock.show(200);
    }
	
	if (file) {
        reader.readAsDataURL(file); //reads the data as a URL
    } else {
        $('#photo-mini-preview').prop('src', '');
    }
}

function setProportionalImageSize(image, maxSize){
	if(image === undefined)
		return;
	var width = image.width;
	var height = image.height;
	var toWidth = height > width;
	
	if(toWidth)
		image.width = maxSize;
	else
		image.height = maxSize;
	
	var ratio = maxSize / (toWidth ? width : height);
	
	if(toWidth)
		image.height = height * ratio;
	else
		image.width = width * ratio;
}

function maximizePhoto(photo){
	toggleHeaderObscure();
	var _photoPreview = document.getElementById('photo-mini-preview');
	var photoPreview = $('#photo-mini-preview');
	photoPreview.toggleClass('max-photo');
	if(!(_photoPreview.width == 300) && !(_photoPreview.height == 300)) {
		setProportionalImageSize(_photoPreview, 300);
	} else{
		_photoPreview.width = 100;
		_photoPreview.height = 100;
	}
}

function minimizeImage(){
	var photoPreview = $('#photo-mini-preview');
	photoPreview.toggleClass('max-photo');
}

function toggleHeaderObscure(){
	var headerObscure = $('.header-obscure');
	headerObscure.toggleClass('fullscreen');
	headerObscure.toggle();
}
