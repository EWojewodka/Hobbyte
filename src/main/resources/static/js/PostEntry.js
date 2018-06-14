function printPostEntry(appendTo,postEntry, before){
	var params = {
			'author.login': postEntry.author.login,
			'author.imageUrl': postEntry.author.imageUrl,
			'author.name': postEntry.author.name,
			'author.lastname': postEntry.author.lastname,
			'postEntry.createdAt': postEntry.createdAt,
			'postEntry.content': postEntry.content,
			'postEntry.image': isEmpty(postEntry.imageUrl) ? "" : postEntry.imageUrl,
			'postEntry.id': postEntry.id,
			'reactions.length' : postEntry.reactions === null ? 0 :postEntry.reactions.length
		};
	var result = 
	'<div class="row">' +
		'<div class="col-lg-12 post-entry block">' +
			'<div class="row header">' +
				'<div class="col-lg-11 author">'+
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
				'<div class="col-lg-1 edit">' +
					'<a href="#" onclick="showPostOptions({postEntry.id})"><i class="fa fa-ellipsis-h"></i></a>' +
				'</div>'+
			'</div>' + 
			'<div class="row content block">{postEntry.content}'+
			'<img src="{postEntry.image}" class="post-entry-img" onclick="maximizePostEntryImage(this)" />' +
			'</div>' +
			'<div class="row reaction-block">'+
				'<div class="col-lg-9">'+
					'<div class="row">'+
						'<a class="thumb-button block small standard-btn" onclick="executeThumb({postEntry.id})"><i class="fa fa-thumbs-up"></i> <span id="post-entry-likes-{postEntry.id}">{reactions.length}</span> Like!</a>'+
						'<a class="show-comments block small standard-btn" onclick="getComments({postEntry.id})"><i class="fa fa-comments"></i> <span>Comments</span></a>'+
					'</div>'+
				'</div>'+
			'</div>' +
			'<div class="row" id="comment-block-{postEntry.id}" style="display:none;"></div>'+
	'</div>';
	result = result.formatUnicorn(params);
	if(before){
		jQuery(result).insertBefore(appendTo.parent());
	} else {
		jQuery(appendTo).append(result);
	}
	showComments(postEntry.comments, {'postId':postEntry.id,'postEntry':jQuery('#comment-block-' + postEntry.id)});
}

/**
 * Render post entry options after click menu button.
 * @param postId
 * @returns
 */
function showPostOptions(postId){
	var ajax = new AjaxRequest("/post/options?postId=" + postId);
	ajax.method = 'GET';
	ajax.send(null,function(jsonObj){
		console.log(jsonObj);
	});
}

function executeThumb(postId) {
	var ajax = new AjaxRequest('/post/reaction/add?postId='+postId);
	ajax.send(null, function(json){
		var postEntryLikes = jQuery('#post-entry-likes-' + json.postId);
		if(postEntryLikes === undefined)
			return;
		var modifier = json.action == 'deleted' ? -1 : 1;
		postEntryLikes.text(parseInt(postEntryLikes.text()) + 1 * modifier);
	});
}

function sendNewPost(form, onSuccess,onFailure) {
	var fileLen = form.photo.files.length;
	if(isEmpty(form.content.value) && fileLen === 0){
		return;
	}
	var ajax = new AjaxRequest('/post/new');
	ajax.addAjaxData('contentType',false);
	ajax.addAjaxData('processData', false);
	form = new FormData(form);

	ajax.send(form, onSuccess, onFailure);
}

function getComments(postId,size) {
	var postEntry = jQuery('#comment-block-' + postId);
	if(postEntry.is(':visible'))
		return;
	var url = '/comment/get';
	url = addUrlParam(url,'post-id',postId);
	
	if(size !== undefined)
		url = addUrlParam(url,'size', size);
	
	var ajax = new AjaxRequest(url);
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
	textArea.value = '';
}

var addCommentSuccess = function(jsonObj) {
	var newCommentBody = renderSingleComment(jsonObj);
	var commentBlock = jQuery('#comment-section-for-' + jsonObj.postEntryId);
	commentBlock.append(newCommentBody);
}

function renderCommentsBlock(postId,jsonObject,postEntryNode){
	if(jQuery('#postEntry-comment-' + postId).is(':visible'))
		return;
	var params = {
			'postId': postId,
			'placeholder': getMessage('enter.content'), 
			};
	
	var result = 
	'<div class="col-lg-9 comment-form">' +
		'<a class="red bold small" onclick="togglePostEntryForm({postId},this)">{placeholder}</a>' + 
		'<form id="postEntry-comment-{postId}" style="display: none;">' + 
			'<input type="hidden" value="{postId}" name="postId"/>' + 
			'<textarea class="form-control block" name="content" onkeyup="sendOnEnter(event,this)" placeholder="{placeholder}"></textarea>' + 
		'</form>' + 
	'</div>' +
	'<div class="col-lg-11 comment-section" id="comment-section-for-{postId}">' +
		renderAllComment(jsonObject) + 
	'</div>';
	result = result.formatUnicorn(params);
	postEntryNode.append(result);
}

function togglePostEntryForm(postEntryId,link){
	var formRef = 'form#postEntry-comment-' + postEntryId;
	jQuery(formRef).slideDown(100);
	jQuery(link).hide(100);
}

function renderAllComment(jsonComments){
	if(jsonComments === undefined || jsonComments === null ||jsonComments.length === 0)
		return '';
	
	var len = jsonComments.length;
	var allComments = '';
	for(var i=0;i<len;i++) {
		allComments += renderSingleComment(jsonComments[i]);
	}
	
	return allComments;
		
}

function renderSingleComment(comment){
	comment = comment.content === undefined ? comment.comment : comment;
	var params = {
		'comment.author.login': comment.author.login,		
		'comment.author.img': comment.author.imageUrl,
		'comment.author.name': comment.author.name,
		'comment.author.lastname': comment.author.lastname,
		'comment.content': comment.content
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

function fetchEntries(size,offset){
	var url = "/post/feed/news";
	url = addUrlParam(url, "size",size)
	if(offset !== undefined)
		url = addUrlParam(url, "offset",offset)
	var ajax = new AjaxRequest(url);
	ajax.method = 'GET';
	ajax.send(null, function(jsonObj){
		var len = jsonObj.length;
		for(var i=0; len>i; i++) {
			printPostEntry(jQuery('.post-container'), jsonObj[i])									
		}
	});
}
