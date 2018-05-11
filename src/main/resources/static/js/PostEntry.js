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
				'<div class="col-lg-2">'+
					'<a class="thumb-button block" onclick="new AjaxRequest(\'/post/reaction/add?postId='+postEntry.id+'\').send();"><i class="fa fa-thumbs-up"></i> ' + postEntry.reactions.length + ' Like!</a>'+
				'</div>'+
				'<div class="col-lg-2">'+
					'<a class="show-comments block">Comments</a>'+
				'</div>'+
			'</div>'+
			'</div>' +
	'</div>';
	jQuery(appendTo).append(result);
}