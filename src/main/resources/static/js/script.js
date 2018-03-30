$(document).ready(function() {
	$('.navbar-left').hide();
	$('.header-obscure').hide();

	$('.header-obscure').click(function() {
		toggleMenu(false);
	});

	handleDynamicForms();
	smoothScroll();
	onlyOneCheckbox();
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
	$.post({
		data : form.serialize(),
		url : url + "?type=" + form.attr('id'),
		success : function(data) {
			var jsonObj = JSON.parse(data);
			showSuccessAfterAjax(form, jsonObj.msg);
		},
		error : function(xhr, ajaxOptions, thrownError) {
			if (xhr === undefined || xhr.responseJSON === undefined)
				return;
			showFailAfterAjax(form, xhr.responseJSON.message);
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

function showSuccessAfterAjax(form, content) {
	var formDescription = form.find('.form-description');
	formDescription.html("<small>" + content + "</small>");
	formDescription.addClass('success');
}

function showFailAfterAjax(form, content) {
	var formDescription = form.find('.form-description');
	formDescription.html("<small>" + content + "</small>");
	formDescription.addClass('fail');
}

function smoothScroll() {
	$('a[href*="#"]').on(
			'click',
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
		});
	});
}

function showNewPostModal() {
	$('.header-obscure').slideDown(200);
}