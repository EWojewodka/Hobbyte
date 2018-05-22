function AjaxRequest(url) {
	this.url = url;
	this.method = 'POST';
	this.processData = false;
	this.contentType = false;
	this.successCallback = function(){};
	this.failCallback = function(){};
}

AjaxRequest.prototype.send = function(data, onSuccess, onFailure, params) {
	jQuery.ajax({
		url : this.url,
		method: this.method,
		processData: this.processData,
	    contentType: this.contentType,
	    data: data,
		success : function(data) {
			onSuccess(JSON.parse(data), params);
		},
		error : function(xhr, ajaxOptions, thrownError) {
			if(typeof onFailure === 'function')
				onFailure(xhr, ajaxOptions, thrownError, params);
		}
	});
}