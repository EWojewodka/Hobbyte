function printPostEntry(appendTo, postEntry){
	var result = '';
	result += 
	'<div class="row">' +
		'<div class="col-lg-12 post-entry block">' +
			'<div class="row header">' +
				'<div class="col-lg-6 author">'+
					'<a href="/profile/'+postEntry.author.login+'">' +
						'<img class="post-profile-image block" src="' +postEntry.author.imageUrl +'" />'+
						postEntry.author.name + ' ' + postEntry.author.lastname + 
					'</a>'+
				'</div>' +
				'<div class="date col-lg-6 text-right small">' + postEntry.createdAt +'</div>' +
			'</div>' + 
			'<div class="row content block">' +postEntry.content + '</div>' +
			'<div class="row reaction-block">'+
				'<div class="col-lg-9">'+
					'<div class="row">'+
						'<a class="thumb-button block small standard-btn" onclick="executeThumb('+postEntry.id+')"><i class="fa fa-thumbs-up"></i> <span id="post-entry-likes-'+postEntry.id+'">' + postEntry.reactions.length + '</span> Like!</a>'+
						'<a class="show-comments block small standard-btn" onclick="getComments('+postEntry.id+')">Comments</a>'+
					'</div>'+
				'</div>'+
			'</div>' +
			'<div class="row" id="comment-block-'+postEntry.id+'" style="display:none;"></div>'+
	'</div>';
	jQuery(appendTo).append(result);
}

function executeThumb(postId) {
	var ajax = new AjaxRequest('/post/reaction/add?postId='+postId);
	ajax.send(null, incrementLikes);
}

var incrementLikes = function(json){
	var postEntryLikes = jQuery('#post-entry-likes-' + json.postId);
	if(postEntryLikes === undefined)
		return;
	var modifier = json.action == 'deleted' ? -1 : 1;
	postEntryLikes.text(parseInt(postEntryLikes.text()) + 1 * modifier);
}

function sendNewPost(form, onSuccess,onFailure) {
	var ajax = new AjaxRequest('/post/new');
	ajax.send(new FormData(form), onSuccess, onFailure);
}

function getComments(postId,params) {
	var postEntry = jQuery('#comment-block-' + postId);
	if(postEntry.is(':visible'))
		return;
	var ajax = new AjaxRequest('/comments?post-id=' + postId);
	ajax.method = 'GET';
	ajax.send(null,showComments,getMessage('internal.error'),{'postId':postId, 'postEntry':postEntry});
}

var showComments = function(jsonObject,params){
	var ren = renderCommentForm(params["postId"]);
	var postEntry = params['postEntry'];
	postEntry.append(ren);
	postEntry.slideDown(100);
}

function sendOnEnter(textArea) {
	console.log(textArea);
}

function renderCommentForm(postId,commentParent){
	return '<div class="offset-lg-1 col-lg-9 comment-form">' +
				'<textarea class="comment-'+postId+'" name="comment-content" onkeyup="sendOnEnter(this)" placeholder="'+getMessage('enter.content')+'"></textarea>' + 
			'</div>';
}