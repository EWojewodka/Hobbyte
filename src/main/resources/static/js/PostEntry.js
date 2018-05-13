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
						'<a class="thumb-button block small standard-btn" onclick="new AjaxRequest(\'/post/reaction/add?postId='+postEntry.id+'\').send(null, incrementLikes);"><i class="fa fa-thumbs-up"></i> <span id="post-entry-likes-'+postEntry.id+'">' + postEntry.reactions.length + '</span> Like!</a>'+
						'<a class="show-comments block small standard-btn">Comments</a>'+
					'</div>'+
				'</div>'+
			'</div>' +
	'</div>';
	jQuery(appendTo).append(result);
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