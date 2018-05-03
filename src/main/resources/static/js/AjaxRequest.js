function AjaxRequest(url) {
	this.url = url;
	this.method = 'POST';
	this.processData = false;
	this.contentType = false;
	this.successCallback = function(){};
	this.failCallback = function(){};
}

AjaxRequest.prototype.send = function(data, onSuccess, onFailure) {
	jQuery.ajax({
		url : this.url,
		method: this.method,
		processData: this.processData,
	    contentType: this.contentType,
	    data: data,
		success : function(data) {
			var jsonObj = JSON.parse(data);
			if(typeof onSuccess === 'function')
				onSuccess(jsonObj);
		},
		error : function(xhr, ajaxOptions, thrownError) {
			console.log(this.failCallback)
			if(typeof onFailure === 'function')
				onFailure(xhr, ajaxOptions, thrownError);
		}
	});
}