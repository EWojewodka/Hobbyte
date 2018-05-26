function printPostEntry(appendTo,postEntry){
	var params = {
			'author.login': postEntry.author.login,
			'author.imageUrl': postEntry.author.imageUrl,
			'author.name': postEntry.author.name,
			'author.lastname': postEntry.author.lastname,
			'postEntry.createdAt': postEntry.createdAt,
			'postEntry.content': postEntry.content,
			'postEntry.id': postEntry.id,
			'reactions.length' : postEntry.reactions.length
		};
	var result = 
	'<div class="row">' +
		'<div class="col-lg-12 post-entry block">' +
			'<div class="row header">' +
				'<div class="col-lg-12 author">'+
					'<a href="/profile/{author.login}">' +
						'<div class="row">' + 
							'<div class="col-lg-2">' + 
								'<img class="post-profile-image block" src="{author.imageUrl}" />'+
							'</div>' +
							'<div class="col-lg-10">' + 
								'<div class="row">' +
									'{author.name} {author.lastname}' + 
								'</div>' + 
								'<div class="row small">' +
									'{postEntry.createdAt}' + 
								'</div>' + 
							'</div>'+
						'</div>' + 
					'</a>'+
				'</div>' +
//				'<div class="date col-lg-3 text-right small">{postEntry.createdAt}</div>' +
			'</div>' + 
			'<div class="row content block">{postEntry.content}</div>' +
			'<div class="row reaction-block">'+
				'<div class="col-lg-9">'+
					'<div class="row">'+
						'<a class="thumb-button block small standard-btn" onclick="executeThumb({postEntry.id})"><i class="fa fa-thumbs-up"></i> <span id="post-entry-likes-{postEntry.id}">{reactions.length}</span> Like!</a>'+
						'<a class="show-comments block small standard-btn" onclick="getComments({postEntry.id})">Comments</a>'+
					'</div>'+
				'</div>'+
			'</div>' +
			'<div class="row" id="comment-block-{postEntry.id}" style="display:none;"></div>'+
	'</div>';
	result = result.formatUnicorn(params);
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
	var ajax = new AjaxRequest('/comment/get?post-id=' + postId);
	ajax.method = 'GET';
	ajax.send(null,showComments,null,{'postId':postId, 'postEntry':postEntry});
}

var showComments = function(jsonObject,params){
	var postEntry = params['postEntry'];
	renderCommentsBlock(params["postId"],jsonObject,postEntry);
	postEntry.slideDown(100);
}

function sendOnEnter(event, textArea) {
	if(event.keyCode != 13)
		return;
	var ajax = new AjaxRequest('/comment/new');
	var content = textArea.value;
	if(isEmpty(content))
		return;
	var form = jQuery(textArea).parent();
	ajax.send(new FormData(form[0]),addCommentSuccess);
}

var addCommentSuccess = function(jsonObj) {
	console.log(jsonObj);
}

function renderCommentsBlock(postId,jsonObject,postEntryNode){
	var params = {
			'postId': postId,
			'placeholder': getMessage('enter.content'), 
			};
	
	var result = 
	'<div class="col-lg-9 comment-form">' +
		'<form id="postEntry-comment-{postId}">' + 
			'<input type="hidden" value="{postId}" name="postId"/>' + 
			'<textarea class="form-control block" name="content" onkeyup="sendOnEnter(event,this)" placeholder="{placeholder}"></textarea>' + 
		'</form>' + 
	'</div>' +
	'<div class="offset-lg-1 col-lg-10 comment-section">' +
		renderAllComment(jsonObject) + 
	'</div>';
	result = result.formatUnicorn(params);
	postEntryNode.append(result);
}

function renderAllComment(jsonComments){
	if(jsonComments === undefined || jsonComments.length === 0)
		return '';
	
	var len = jsonComments.length;
	var allComments = '';
	for(i=0;i<len;i++) {
		console.log(renderSingleComment(jsonComments[i]));
		allComments += renderSingleComment(jsonComments[i]);
	}
	
	return allComments;
		
}

function renderSingleComment(jsonSingleComment){
	console.log(jsonSingleComment);
	var params = {
		'comment.author.login': jsonSingleComment.author.login,
		'comment.author.img': jsonSingleComment.author.imageUrl,
		'comment.author.name': jsonSingleComment.author.name,
		'comment.author.lastname': jsonSingleComment.author.lastname,
		'comment.content': jsonSingleComment.content
	};
	var result = 
	'<div class="row single-comment">' + 
		'<div class="col-lg-12">' +
			'<div class="row">' +
				'<a href="/profile/{comment.author.login}" class="author">' +
					'<img src="{comment.author.img}" class="post-profile-image"/>' +
				'</a>' +
				'<div class="comment-content">'+
					'<a href="/profile/{comment.author.login}" class="hover-underline underline-color-red comment-author">' +
						'<span class="bold red">{comment.author.name}&nbsp;{comment.author.lastname}</span>' +
					'</a>' +
					'{comment.content}'+
				'</div>' +
			'</div>' +
		'</div>' + 
	'</div>';
	result = result.formatUnicorn(params);
	return result;
	
}
