function AjaxRequest(url) {
	this.url = url;
	this.method = 'POST';
	this.ajaxRequestData = {};
}

AjaxRequest.prototype.addAjaxData =function (key,value){
	this.ajaxRequestData[key] = value;
}

AjaxRequest.prototype.send = function(data, onSuccess, onFailure, params) {
	this.addAjaxData('method', this.method);
	this.addAjaxData('url', this.url);
	this.addAjaxData('data', data);
	this.addAjaxData('success', function(data){onSuccess(data,params)});
	this.addAjaxData('error', function(xhr, ajaxOptions, thrownError){if(onFailure !== undefined)onFailure(xhr, ajaxOptions, thrownError, params);});
	console.log(this.ajaxRequestData);
	
	/*var ajaxRequestData = {
		url : this.url,
		method: this.method,
		data: data,
		async: true,
		success : function(data) {
			onSuccess(data, params);
		},
		error : function(xhr, ajaxOptions, thrownError) {
			if(typeof onFailure === 'function')
				onFailure(xhr, ajaxOptions, thrownError, params);
		}
	};*/

	jQuery.ajax(this.ajaxRequestData);
}