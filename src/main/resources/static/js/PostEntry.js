function printPostEntry(appendTo, postEntry){
	var result = '';
	result += 
	'<div class="row">' +
		'<div class="col-lg-12 post-entry block">' +
			'<div class="header">' +
				'<div class="author"><a href="/profile/'+postEntry.author+'">' + postEntry.author + '</a></div>' +
				'<div class="date">' + postEntry.createdAt +'</div>' +
			'</div>' + 
			'<div class="content">' +postEntry.content + '</div>' +
		'</div>' +
	'</div>';
	jQuery(appendTo).append(result);
}