jQuery(document).ready(function() {
	jQuery.noConflict();
	jQuery('.navbar-left').hide();
	jQuery('.header-obscure').hide();
	jQuery('.post-attachments-preview').hide();

	jQuery('.header-obscure').click(function() {
		toggleMenu(false);
		minimizeImage();
	});

	handleDynamicForms();
	smoothScroll();
	onlyOneCheckbox();
	listenMenuTree();
});

function toggleMenu(show) {
	var leftNavbar = jQuery('.navbar-left');
	var headerObscure = jQuery('.header-obscure');

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
	var headerImage = jQuery('.header-image');
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
	jQuery('.dynamic-form').each(function() {
		jQuery(this).find('form').hide();
		console.log(jQuery(this))
	});

	jQuery('.dynamic-form').click(function() {
		var toOpen = jQuery(this);
		var toOpenForm = toOpen.find('form');
		jQuery('.dynamic-form').each(function() {
			var other = jQuery(this);
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
	var form = jQuery(_form);
	var ajax = new AjaxRequest(url + "?type=" + form.attr('id'));
	var success = function(jsonObj){
		showResultAfterAjax(form, jsonObj.msg, true);
		if(jsonObj.redirect !== undefined)
			window.location.href = jsonObj.redirect;
	}

	var failure = function(xhr, ajaxOptions, thrownError) {
		if (xhr === undefined || xhr.responseJSON === undefined)
			return;
		showResultAfterAjax(form, xhr.responseJSON.message, false);
	}
	prepareFormDescription(form);
	ajax.send(new FormData(_form), success, failure);
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
	jQuery('a[href*="#"]').on('click',
		function(event) {
			if (location.pathname.replace(/^\//, '') == this.pathname
					.replace(/^\//, '')
					&& location.hostname == this.hostname) {
				var target = jQuery(this.hash);
				target = target.length ? target : jQuery('[name='
						+ this.hash.slice(1) + ']');
				if (target.length) {
					event.preventDefault();
					jQuery('html, body').animate({
						scrollTop : target.offset().top
					}, 500, function() {
						var jQuerytarget = jQuery(target);
						jQuerytarget.focus();
						if (jQuerytarget.is(":focus")) {
							return false;
						} else {
							jQuerytarget.attr('tabindex', '-1');
							jQuerytarget.focus();
						}
						;
					});
				}
			}
		});
}

function onlyOneCheckbox() {
	jQuery('.one-checkbox .checkbox').on('change', function(e) {
		jQuery(this).siblings().each(function() {
			var toUncheck = jQuery(this).find('input[type="checkbox"]').first();
			toUncheck.prop("checked", false);
			toUncheck.prop("disabled", false);
		});
		e.target.disabled = true;
	});
}

function showNewPostModal() {
	jQuery('.header-obscure').slideDown(200);
}

function listenMenuTree(){
	jQuery('.menu-tree-element-wrapper').hide();
	jQuery('.menu-tree-element div').on('click', function(){
		var parent = jQuery(this);
		parent.siblings('.menu-tree-element-wrapper').toggle();
	});
}

function loadIFrame(_source){
	var source = jQuery(_source);
	var navigator = source.find('.mt-navigator').first();
	var src = navigator.attr('src');
	if(src === undefined || src.length == 0)
		return;
	jQuery('iframe').prop('src', src);
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

	var currentMiniPhoto = jQuery('.attach-wrapper #photo-mini-preview');
	if(!currentMiniPhoto !== undefined) {
		currentMiniPhoto.remove();
	}
	
	var input = jQuery(_input);
	var previewBlock = jQuery('.post-attachments-preview');
	previewBlock.prepend(
			'<div class="attach-wrapper">'
			+'<img id="photo-mini-preview" src="" width="100px" height="100px" onclick="maximizePhoto(this)" />'
			+'</div>');
	var reader = new FileReader();
	
	reader.onloadend = function () {
		jQuery('#photo-mini-preview').prop('src', reader.result);
		previewBlock.show(200);
    }
	
	if (file) {
        reader.readAsDataURL(file); //reads the data as a URL
    } else {
        jQuery('#photo-mini-preview').prop('src', '');
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
	var photoPreview = jQuery('#photo-mini-preview');
	photoPreview.toggleClass('max-photo');
	if(!(_photoPreview.width == 300) && !(_photoPreview.height == 300)) {
		setProportionalImageSize(_photoPreview, 300);
	} else{
		_photoPreview.width = 100;
		_photoPreview.height = 100;
	}
}

function minimizeImage(){
	var photoPreview = jQuery('#photo-mini-preview');
	photoPreview.toggleClass('max-photo');
}

function toggleHeaderObscure(){
	var headerObscure = jQuery('.header-obscure');
	headerObscure.toggleClass('fullscreen');
	headerObscure.toggle();
}

function importScript(scriptName) {
	var head = jQuery('head');
	head.append('<script type="text/javascript" src="/js/'+scriptName+'.js"></script>');
	
}
